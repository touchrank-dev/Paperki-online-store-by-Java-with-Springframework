package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.Category;

import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CatalogDaoImpl implements CatalogDao {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDaoImpl.class);

    private static final String P_CATEGORY_T_NAME = "p_category_t_name";
    private static final String P_PRODUCT_T_NAME = "p_product_t_name";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* CSV */
    @Value("${path.csv.files}")
    private String csvFilesPath;
    @Value("${csv.file.catalog}")
    private String csvFileCatalog;

    /* SQL Scripts */
    @Value("${catalog.getAll}")
    private String getAllSqlQuery;

    @Value("${catalog.getProductsByCategoryTName}")
    private String getProductsByCategoryTNameSqlQuery;

    @Value("${catalog.getProductByTName}")
    private String getProductByTNameSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() {
        HashMap<Integer, HashMap<Integer, Category>> map =
                (HashMap) jdbcTemplate.query(getAllSqlQuery, new CategoryResultSetExtractor());
        LOGGER.debug("getAll() >>>\nCATALOG MENU MAP: {}", map);
        return map;
    }

    @Override
    public HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_CATEGORY_T_NAME, categoryTName);
        HashMap<Integer ,Product> products =
                (HashMap<Integer ,Product>) namedParameterJdbcTemplate
                        .query(getProductsByCategoryTNameSqlQuery, parameterSource, new ProductResultSetExtractor());
        LOGGER.debug("getProductListByCategoryTName({}) >>>\nPRODUCTS MAP: {}", categoryTName, products);
        return products;
    }

    @Override
    public Product getProductByTName(String productTName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_PRODUCT_T_NAME, productTName);
        Product product = namedParameterJdbcTemplate.queryForObject(getProductByTNameSqlQuery , parameterSource, new ProductRowMapper());
        LOGGER.debug("getProductByTName({}) >>>\nPRODUCT: {}", productTName, product);
        return null;
    }

    @Override
    public ArrayList<Category> getCategoriesFromCSV() throws IOException {
        LOGGER.debug("Starting retrieve data from CSV file: {}", csvFilesPath+csvFileCatalog);
        LOGGER.debug(">>> PROGRESS ...");
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .parse(new FileReader(csvFilesPath + csvFileCatalog));
            for (CSVRecord record : records) {
                try {
                    categories.add(new Category(
                            Integer.parseInt(record.get(0)),
                            record.get(1),
                            record.get(2),
                            record.get(3),
                            record.get(4),
                            Integer.parseInt(record.get(5)),
                            Integer.parseInt(record.get(6)),
                            record.get(7),
                            record.get(8)
                    ));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(">>> File ({}) Not Found! >>> {}",
                    csvFilesPath+csvFileCatalog, e.getMessage());
            return null;
        }
        LOGGER.debug("DATA: {}\n>>> FINISH", categories);
        return categories;
    }

    private class CategoryResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, HashMap<Integer, Category>> map =
                    new HashMap<Integer, HashMap<Integer, Category>>();
            // Инициализируем главную ветку категории под ключом 0
            map.put(0, new HashMap<Integer, Category>());

            Category category;
            int parent;
            while (rs.next()) {
                parent = rs.getInt("parent");
                category = new Category(
                        rs.getInt("id_catalog"),
                        rs.getString("name"),
                        rs.getString("translit_name"),
                        rs.getString("link"),
                        rs.getString("icon"),
                        rs.getInt("order_catalog")
                );

                HashMap<Integer, Category> mapCategory = new HashMap<Integer, Category>();
                // добавляем себя же как ключ = 0
                // mapCategory.put(0, category);
                // добавляем в список главной ветки
                map.put(category.getId(), mapCategory);

                // ищем родителя
                HashMap<Integer, Category> parentCategory = map.get(parent);
                // если родитель найден добавляем к нему в child
                if(parentCategory != null) {
                    parentCategory.put(category.getId(), category);
                }
            }
            return map;
        }
    }

    private class ProductResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer ,Product> products = new HashMap<Integer ,Product>();
            HashMap<Integer, Price> prices = new LinkedHashMap<Integer, Price>();
            Product product;
            Price price;
            while (rs.next()) {
                price = new Price(rs.getInt("quatity_start"),
                                  rs.getInt("quatity_end"),
                                  rs.getDouble("value")
                );
                product = new Product(
                        rs.getInt("id_product"),
                        rs.getInt("pap_id"),
                        rs.getInt("pnt"),
                        rs.getString("full_name"),
                        rs.getString("short_name"),
                        rs.getString("translit_name"),
                        rs.getString("link"),
                        rs.getString("country_from"),
                        rs.getString("country_made"),
                        rs.getString("measure"),
                        rs.getInt("available_day"),
                        rs.getInt("quantity"),
                        rs.getInt("vat"),
                        rs.getBoolean("is_published"),
                        rs.getBoolean("is_visible"),
                        new Brand(rs.getString("bname"), rs.getString("btname")),
                        null
                );
                if(products.get(product.getId()) == null) {
                    prices.put(price.getQuantityStart(), price);
                    product.setPrices(prices);
                    products.put(product.getId(), product);
                } else {
                    Product p = products.get(product.getId());
                    if(p.getPnt() == product.getPnt()) {
                        product.getPrices().put(price.getQuantityStart(), price);
                        products.put(product.getId(), p);
                    }
                }
            }
            return products;
        }
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product(
                    rs.getInt("id_product"),
                    rs.getInt("pap_id"),
                    rs.getInt("pnt"),
                    rs.getString("full_name"),
                    rs.getString("short_name"),
                    rs.getString("translit_name"),
                    rs.getString("link"),
                    rs.getString("country_from"),
                    rs.getString("country_made"),
                    rs.getString("measure"),
                    rs.getInt("available_day"),
                    rs.getInt("quantity"),
                    rs.getInt("vat"),
                    rs.getBoolean("is_published"),
                    rs.getBoolean("is_visible"),
                    new Brand(rs.getString("bname"), rs.getString("btname")),
                    prices
            );
            return product;
        }
    }

}