package com.kushnir.paperki.dao;

import com.kushnir.paperki.dao.product.datamapper.*;
import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.product.*;
import com.kushnir.paperki.model.util.Transliterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProductDaoImpl implements ProductDao {

    private static final Logger LOGGER = LogManager.getLogger(ProductDaoImpl.class);

    private static final String P_ID = "p_id";
    private static final String P_PAP_ID = "p_pap_id";
    private static final String P_PNT = "p_pnt";
    private static final String P_PERSONAL_GROUP_NAME = "p_personal_group_name";
    private static final String P_FULL_NAME = "p_full_name";
    private static final String P_SHORT_NAME = "p_short_name";
    private static final String P_PRODUCT_T_NAME = "p_product_t_name";
    private static final String P_CATEGORY_T_NAME = "p_category_t_name";
    private static final String P_LINK = "p_link";
    private static final String P_BRAND_ID = "p_id_brand";
    private static final String P_COUNTRY_FROM = "p_country_from";
    private static final String P_COUNTRY_MADE = "p_country_made";
    private static final String P_BAR_CODE = "p_bar_code";
    private static final String P_MEASURE = "p_measure";
    private static final String P_AVAILABLE_DAY = "p_available_day";
    private static final String P_BASE_PRICE = "p_base_price";
    private static final String P_VAT = "p_vat";

    private static final String P_CATEGORY_ID = "p_id_catalog";
    private static final String P_ORDER = "p_order";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* CSV */
    @Value("${csv.delimiter}")
    private char delimiter;

    @Value("${csv.escape}")
    private char escape;

    @Value("${path.csv.files}")
    private String csvFilesPath;

    @Value("${csv.file.products}")
    private String csvFileProducts;

    @Value("${csv.file.stock}")
    private String csvFileStockItems;

    @Value("${csv.file.attributes}")
    private String csvFileProductAttributes;

    @Value("${csv.file.prices}")
    private String csvFileQuantityPrices;

    @Value("${csv.file.descriptions}")
    private String csvFileProductDescriptions;


    /*SQL Scripts*/
    @Value("${product.getAll}")
    private String getAllSqlQuery;

    @Value("${product.getByPNT}")
    private String getByPNTSqlQuery;

    @Value("${product.getProductByTName}")
    private String getProductByTNameSqlQuery;

    @Value("${product.getProductsByCategoryTName}")
    private String getProductsByCategoryTNameSqlQuery;

    @Value("${product.extraTypes.getAll}")
    private String getAllExtraTypesProductsSqlQuery;

    @Value("${product.getAvailableProductByPNT}")
    private String getAvailableProductByPNTSqlQuery;

    @Value("${product.attributes.getByPNT}")
    private String getAttributesByPNTSqlQuery;

    @Value("${product.attributes.deleteAll}")
    private String deleteAllAttributesSqlQuery;

    @Value("${product.attributes.batch.add}")
    private String addAttributeSqlQuery;

    @Value("${product.unpublish}")
    private String unpublishSqlQuery;

    @Value("${product.clearStock}")
    private String clearStockSqlQuery;

    @Value("${product.batch.addStockItem}")
    private String addStockItemSqlQuery;

    @Value("${product.add}")
    private String addProductSqlQuery;

    @Value("${product.addCatRef}")
    private String addProductCatalogRefSqlQuery;

    @Value("${product.batch.update}")
    private String updateProductsSqlQuery;

    @Value("${product.batch.updateCAtRef}")
    private String updateProductsCatalogRefSqlQuery;

    @Value("${product.prices.deleteAll}")
    private String deleteAllPricesSqlQuery;

    @Value("${product.prices.add}")
    private String addPriceSqlQuery;

    @Value("${product.descriptions.deleteAll}")
    private String deleteAllDescriptionsSqlQuery;

    @Value("${product.descriptions.add}")
    private String addDescriptionSqlQuery;

    @Value("${product.search}")
    private String searchProductSqlQuery;

    @Override
    public HashMap<Integer, ProductSimple> getAll() {
        LOGGER.debug("getAll() >>>");
        HashMap<Integer, ProductSimple> products =
                namedParameterJdbcTemplate.query(getAllSqlQuery, new AllProductResultSetExtractor());
        return products;
    }

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName, Integer sortType) throws DataAccessException {
        LOGGER.debug("getProductListByCategoryTName({}) >>>", categoryTName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_CATEGORY_T_NAME, categoryTName);

        HashMap<Integer, Product> products = namedParameterJdbcTemplate.query(
                        getSQLQueryWithSortType(getProductsByCategoryTNameSqlQuery, sortType),
                        parameterSource,
                        new ProductsResultSetExtractor());
        return products;
    }

    private String getSQLQueryWithSortType(String SqlQuery, Integer sortType) {
        switch (sortType) {
            // По умолчанию
            case 1: return SqlQuery+" ORDER BY pc.order_product, p.full_name, pp.quantity_start;";
            // Сначала популярные
            case 2: return SqlQuery+" ORDER BY pc.order_product, p.full_name, pp.quantity_start;";
            // Сначала дешевые
            case 3: return SqlQuery+" ORDER BY p.base_price ASC, pc.order_product, p.full_name, pp.quantity_start;";
            // Сначала дорогие
            case 4: return SqlQuery+" ORDER BY p.base_price DESC, pc.order_product, p.full_name, pp.quantity_start;";
            // По умолчанию
            default: return SqlQuery+" ORDER BY pc.order_product, p.full_name, pp.quantity_start;";
        }
    }

    @Override
    public Product getProductByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getProductByPNT({}) >>>", pnt);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
        Product product = (Product) namedParameterJdbcTemplate.query(
                getByPNTSqlQuery,
                parameterSource,
                new ProductResultSetExtractor());
        return product;
    }

    @Override
    public Product getProductByTName(String TName) throws DataAccessException {
        LOGGER.debug("getProductByTName({}) >>>", TName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PRODUCT_T_NAME, TName);
        Product product = (Product) namedParameterJdbcTemplate.query(
                getProductByTNameSqlQuery,
                parameterSource,
                new ProductResultSetExtractor());
        return product;
    }

    @Override
    public AvailableProduct getAvailableProductByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getAvailableProductByPNT({}) >>>", pnt);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
        AvailableProduct availableProduct = (AvailableProduct) namedParameterJdbcTemplate.query(
                getAvailableProductByPNTSqlQuery,
                parameterSource,
                new AvailableProductResultSetExtractor());
        return availableProduct;
    }

    @Override
    public ArrayList<Attribute> getAttributesByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getAttributesByPNT({}) >>>", pnt);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
        ArrayList<Attribute> attributes = (ArrayList<Attribute>)
                    namedParameterJdbcTemplate.query(
                            getAttributesByPNTSqlQuery,
                            parameterSource,
                            new AttributeRowMapper());
        return attributes;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, Product>> getAllExtraTypeProducts() {
        LOGGER.debug("getAllExtraTypeProducts() >>>");
        HashMap<Integer, HashMap<Integer, Product>> products =
                namedParameterJdbcTemplate.query(
                        getAllExtraTypesProductsSqlQuery,
                        new ExtraProductsResultSetExtractor());
        return products;
    }


    @Override
    public HashMap<Integer, CSVProduct> getProductsFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileProducts;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);
        HashMap<Integer, CSVProduct> products = new LinkedHashMap<>();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));

            for (CSVRecord record : records) {
                try {
                    Integer pnt =                   Integer.parseInt(record.get(0));
                    Integer groupPapId =            Integer.parseInt(record.get(1));
                    String personalGroupName =      StringEscapeUtils.unescapeHtml(record.get(2));
                    String fullName =               StringEscapeUtils.unescapeHtml(record.get(3));
                    String translitName =           Transliterator.cyr2lat(pnt+" "+personalGroupName);
                    String shortName =              StringEscapeUtils.unescapeHtml(record.get(4));
                    Integer brandId =               Integer.parseInt(record.get(5));
                    String countryFrom =            record.get(6);
                    String countryMade =            record.get(7);
                    String barCode =                record.get(8);
                    String measure =                record.get(9);
                    Integer availableDay =          Integer.parseInt(record.get(10));
                    double basePrice =              Double.parseDouble(record.get(11));
                    Integer VAT =                   Integer.parseInt(record.get(12));

                    //TODO VALIDATE ===========================================================
                    Assert.notNull(pnt, "pnt is null");
                    Assert.isTrue(pnt > 0, "pnt <= 0");

                    Assert.notNull(groupPapId, "groupPapId is null");
                    Assert.isTrue(groupPapId > 0, "groupPapId <= 0");

                    Assert.notNull(translitName, "translitName is null");
                    Assert.hasText(translitName, "translitName is blank");

                    Assert.isTrue(basePrice > 0, "basePrice <= 0");
                    // TODO ===================================================================

                    CSVProduct csvProduct = new CSVProduct(
                            pnt,
                            groupPapId,
                            personalGroupName,
                            fullName,
                            shortName,
                            translitName,
                            brandId,
                            countryFrom,
                            countryMade,
                            barCode,
                            measure,
                            availableDay,
                            basePrice,
                            VAT
                    );

                    csvProduct.setOrder(100);

                    products.put(pnt, csvProduct);

                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber()+1, e.getMessage());
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }
        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return products;
    }

    @Override
    public HashMap<Integer, StockItem> getStockItemsFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileStockItems;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);
        HashMap<Integer, StockItem> items = new LinkedHashMap<>();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));
            for (CSVRecord record : records) {
                try {
                    Integer pnt =                   Integer.parseInt(record.get(0));
                    Integer quantityAvailable =     Integer.parseInt(record.get(1));

                    StockItem stockItem = new StockItem(
                            pnt,
                            quantityAvailable
                    );

                    items.put(pnt, stockItem);

                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }

        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return items;
    }

    @Override
    public ArrayList<Attribute> getAttributesFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileProductAttributes;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);

        ArrayList<Attribute> attributes = new ArrayList<>();
        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));
            for (CSVRecord record : records) {
                try {
                    Integer pnt =       Integer.parseInt(record.get(0));
                    String name =       StringEscapeUtils.unescapeHtml(record.get(1));
                    String value =      StringEscapeUtils.unescapeHtml(record.get(2));
                    Integer order =     Integer.parseInt(record.get(3));

                    Attribute attribute = new Attribute(
                            name,
                            value ,
                            order,
                            pnt
                    );

                    attributes.add(attribute);

                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }

        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return attributes;
    }

    @Override
    public ArrayList<Price> getQuantityPricesFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileQuantityPrices;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);

        ArrayList<Price> prices = new ArrayList<>();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));

            for (CSVRecord record : records) {
                try {
                    Integer pnt =               Integer.parseInt(record.get(0));
                    Assert.isTrue(pnt > 0, "pnt <= 0");

                    int quantityStart =         Integer.parseInt(record.get(1));
                    double basePrice =          Double.parseDouble(record.get(2));

                    prices.add(new Price(
                            pnt,
                            quantityStart,
                            basePrice
                    ));

                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }

        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return prices;
    }

    @Override
    public ArrayList<Description> getProductDescriptionsFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileProductDescriptions;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);

        ArrayList<Description> descriptions = new ArrayList<>();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));

            for (CSVRecord record : records) {
                try {
                    Integer pnt =               Integer.parseInt(record.get(0));
                    Assert.isTrue(pnt > 0, "pnt <= 0");

                    String fullDescription = record.get(1);
                    Assert.isTrue(!StringUtils.isBlank(fullDescription), "fullDescription is blank");

                    String shortDescription = record.get(2);

                    String metadesk;
                    String metakey;
                    String customtitle;

                    descriptions.add(new Description(
                            pnt,
                            fullDescription,
                            shortDescription
                    ));

                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }

        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return descriptions;
    }

    @Override
    public ArrayList<AvailableProduct> searchProducts(String str) {
        LOGGER.debug("searchProducts() >>>");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("p_str", '%'+str+'%');
        parameterSource.addValue("p_pnt", str);
        ArrayList products = namedParameterJdbcTemplate.query(
                searchProductSqlQuery,
                parameterSource,
                new SearchProductResultSetExtractor());
        return products;
    }


    @Override
    public void unpublishAllProducts() {
        LOGGER.debug("unpublishAllProducts() >>>");
        int count = jdbcTemplate.update(unpublishSqlQuery);
    }

    @Override
    public int addProduct(CSVProduct product) {
        LOGGER.debug("addProduct() >>>");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(P_PNT, product.getPnt());
        parameterSource.addValue(P_PERSONAL_GROUP_NAME, product.getPersonalGroupName());
        parameterSource.addValue(P_FULL_NAME, product.getFullName());
        parameterSource.addValue(P_SHORT_NAME, product.getShortName());
        parameterSource.addValue(P_PRODUCT_T_NAME, product.getTranslitName());
        parameterSource.addValue(P_LINK, product.getLink());
        parameterSource.addValue(P_BRAND_ID, product.getBrandId());
        parameterSource.addValue(P_COUNTRY_FROM, product.getCountryFrom());
        parameterSource.addValue(P_COUNTRY_MADE, product.getCountryMade());
        parameterSource.addValue(P_BAR_CODE, product.getBarCode());
        parameterSource.addValue(P_MEASURE, product.getMeasure());
        parameterSource.addValue(P_AVAILABLE_DAY, product.getAvailableDay());
        parameterSource.addValue(P_BASE_PRICE, product.getBasePrice());
        parameterSource.addValue(P_VAT, product.getVAT());

        namedParameterJdbcTemplate.update(addProductSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int addProductCatalogRef(CSVProduct product) {
        LOGGER.debug("addProductCatalogRef() >>>");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(P_ID, product.getId());
        parameterSource.addValue(P_CATEGORY_ID, product.getCategoryId());
        parameterSource.addValue(P_ORDER, product.getOrder());

        namedParameterJdbcTemplate.update(addProductCatalogRefSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int[] batchUpdateProducts(Object[] categories) {
        LOGGER.debug("batchUpdateProducts() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(updateProductsSqlQuery, batch);
    }

    @Override
    public int[] batchUpdateProductsCatalogRef(Object[] categories) {
        LOGGER.debug("batchUpdateProductsCatalogRef() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(updateProductsCatalogRefSqlQuery, batch);
    }

    @Override
    public void clearStock(Integer id) {
        LOGGER.debug("clearStock({}) >>>", id);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_ID, id);
        int count = namedParameterJdbcTemplate.update(clearStockSqlQuery, parameterSource);
    }

    @Override
    public int[] addItemsToStock(Object[] items) {
        LOGGER.debug("addItemsToStock() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(items);
        return namedParameterJdbcTemplate.batchUpdate(addStockItemSqlQuery, batch);
    }

    @Override
    public void deleteAllAttributes() {
        LOGGER.debug("deleteAllAttributes() >>>");
        int count = jdbcTemplate.update(deleteAllAttributesSqlQuery);
    }

    @Override
    public int[] batchAddAttributes(Object[] attributes) {
        LOGGER.debug("batchAddAttributes() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(attributes);
        return namedParameterJdbcTemplate.batchUpdate(addAttributeSqlQuery, batch);
    }

    @Override
    public void deleteAllQuantityPrices() {
        LOGGER.debug("deleteAllQuantityPrices() >>>");
        int count = jdbcTemplate.update(deleteAllPricesSqlQuery);
    }

    @Override
    public int[] batchAddQuantityPrices(Object[] prices) {
        LOGGER.debug("batchAddQuantityPrices() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(prices);
        return namedParameterJdbcTemplate.batchUpdate(addPriceSqlQuery, batch);
    }

    @Override
    public void deleteAllDescriptions() {
        LOGGER.debug("deleteAllDescriptions() >>>");
        int count = jdbcTemplate.update(deleteAllDescriptionsSqlQuery);
    }

    @Override
    public int[] batchAddDescriptions(Object[] description) {
        LOGGER.debug("batchAddDescriptions() >>>");
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(description);
        return namedParameterJdbcTemplate.batchUpdate(addDescriptionSqlQuery, batch);
    }

}
