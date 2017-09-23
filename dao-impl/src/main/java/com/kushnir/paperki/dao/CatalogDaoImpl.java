package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;

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

public class CatalogDaoImpl implements CatalogDao {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDaoImpl.class);

    private static final String P_CATEGORY_T_NAME = "p_category_t_name";

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

    @Value("${path.csv.files.test}")
    private String csvFilesPathTest;

    @Value("${csv.file.catalog}")
    private String csvFileCatalog;

    /* SQL Scripts */
    @Value("${catalog.getAll}")
    private String getAllSqlQuery;

    @Value("${catalog.getAllByStock}")
    private String getAllByStockSqlQuery;

    @Value("${catalog.getByTName}")
    private String getByTNameSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() throws DataAccessException {
        LOGGER.debug("getAll() >>>");
        HashMap<Integer, HashMap<Integer, Category>> map =
                (HashMap) jdbcTemplate.query(getAllSqlQuery, new CategoryResultSetExtractor());
        return map;
    }

    @Override
    public Category getCategoryByTName(String categoryTName) throws DataAccessException {
        LOGGER.debug("getCategoryByTName({}) >>>", categoryTName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_CATEGORY_T_NAME, categoryTName);
        Category category = namedParameterJdbcTemplate
                .queryForObject(getByTNameSqlQuery, parameterSource, new CategoryRowMapper());
        return category;
    }

    @Override
    public ArrayList<Category> getCategoriesFromCSV() throws IOException, DataAccessException {
        LOGGER.debug("Starting retrieve data from CSV file: {}", csvFilesPathTest+csvFileCatalog);
        LOGGER.debug(">>> PROGRESS ...");
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(csvFilesPathTest + csvFileCatalog));

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
                    LOGGER.error("ERROR >>> {}", e.getMessage());
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(">>> File ({}) Not Found! >>> {}",
                    csvFilesPathTest+csvFileCatalog, e.getMessage());
            return null;
        }
        LOGGER.debug(">>> FINISH");
        return categories;
    }



    private class CategoryResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException {
            HashMap<Integer, HashMap<Integer, Category>> map =
                    new HashMap<Integer, HashMap<Integer, Category>>();
            // Инициализируем главную ветку категории под ключом 0
            map.put(0, new HashMap<Integer, Category>());

            while (rs.next()) {
                int parent = rs.getInt("parent");
                Category category = new Category(
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

    private class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category(
                    rs.getInt("id_catalog"),
                    rs.getString("name"),
                    rs.getString("translit_name"),
                    rs.getString("link"),
                    rs.getString("icon"),
                    rs.getInt("order_catalog")
            );
            return category;
        }
    }

}
