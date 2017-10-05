package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;

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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CatalogDaoImpl implements CatalogDao {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDaoImpl.class);

    private static final String P_CATEGORY_T_NAME = "p_category_t_name";
    private static final String P_ID = "id";
    private static final String P_PAP_ID = "papId";
    private static final String P_NAME = "name";
    private static final String P_TRANSLIT_NAME = "translitName";
    private static final String P_LINK = "link";
    private static final String P_ORDER = "order";
    private static final String P_PARENT_ID = "parent";

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

    @Value("${csv.file.catalog}")
    private String csvFileCatalog;


    /* SQL Scripts */
    @Value("${catalog.getAll}")
    private String getAllSqlQuery;

    @Value("${catalog.getAllByStock}")
    private String getAllByStockSqlQuery;

    @Value("${catalog.getByTName}")
    private String getByTNameSqlQuery;

    @Value("${catalog.batch.add}")
    private String addCategoriesSqlQuery;

    @Value("${catalog.batch.addRef}")
    private String addCategoriesRefSqlQuery;

    @Value("${catalog.batch.update}")
    private String updateCategoriesSqlQuery;

    @Value("${catalog.batch.updateRef}")
    private String updateCategoriesRefSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() throws DataAccessException {
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
    public CategoryContainer getCategoriesFromCSVToContainer() throws IOException, DataAccessException {
        String file = csvFilesPath + csvFileCatalog;
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);

        CategoryContainer cats = new CategoryContainer();
        HashMap<Integer, Category> parents = cats.getParents();
        HashMap<Integer, Category> children = cats.getChildren();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));

            for (CSVRecord record : records) {

                try{
                    Integer papId =             Integer.parseInt(record.get(0));
                    String name =               record.get(1);
                    String metadesc =           record.get(2);
                    String metakey =            record.get(3);
                    String customtitle =        record.get(4);
                    Integer order =             Integer.parseInt(record.get(5));
                    Integer parent =            Integer.parseInt(record.get(6));
                    String shortDescription =   record.get(7);
                    String fullDescription =    record.get(8);

                    Category category = new Category(
                            papId,
                            name,
                            metadesc,
                            metakey,
                            customtitle,
                            order,
                            parent,
                            shortDescription,
                            fullDescription
                    );

                    if (parent.equals(0)) {
                        parents.put(papId, category);
                    } else {
                        children.put(papId, category);
                    }

                } catch (Exception e) {
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }
        LOGGER.debug(">>> FINISH");
        return cats;
    }

    @Override
    public CategoryContainer getCategoriesToContainer() {
        LOGGER.debug("getCategoriesToContainer() >>>");
        CategoryContainer cats =
                (CategoryContainer)jdbcTemplate.query(getAllSqlQuery, new CategoryContainerResultExtractor());
        return cats;
    }

    @Override
    public int[] addCategories(Object[] categories) {
        LOGGER.debug("addCategories({}) >>>", categories);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(addCategoriesSqlQuery, batch);
    }

    @Override
    public int[] addCategoriesRef(Object[] categories) {
        LOGGER.debug("addCategoriesRef({}) >>>", categories);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(addCategoriesRefSqlQuery, batch);
    }

    @Override
    public int addCategory(Category category) {
        LOGGER.debug("addCategory({}) >>>", category);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_PAP_ID, category.getPapId());
        parameterSource.addValue(P_NAME, category.getName());
        parameterSource.addValue(P_TRANSLIT_NAME, category.getTranslitName());
        parameterSource.addValue(P_LINK, category.getLink());
        parameterSource.addValue(P_ORDER, category.getOrder());
        namedParameterJdbcTemplate.update(addCategoriesSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int addRefCategory(Category category) {
        LOGGER.debug("addRefCategory({}) >>>", category);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ID, category.getId());
        parameterSource.addValue(P_PARENT_ID, category.getParent());
        namedParameterJdbcTemplate.update(addCategoriesRefSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int[] updateCategories(Object[] categories) {
        LOGGER.debug("updateCategories({}) >>>", categories);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(updateCategoriesSqlQuery, batch);
    }

    @Override
    public int[] updateCategoriesRef(Object[] categories) {
        LOGGER.debug("updateCategoriesRef({}) >>>", categories);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(categories);
        return namedParameterJdbcTemplate.batchUpdate(updateCategoriesRefSqlQuery, batch);
    }


    private class CategoryContainerResultExtractor implements ResultSetExtractor {

        @Override
        public CategoryContainer extractData(ResultSet rs) throws SQLException, DataAccessException {
            CategoryContainer container = new CategoryContainer();
            while (rs.next()) {

                Integer papId =             rs.getInt("pap_id");
                Integer id =                rs.getInt("id_catalog");
                String name =               rs.getString("name");
                String translitName =       rs.getString("translit_name");
                String link =               rs.getString("link");
                String icon =               rs.getString("icon");
                String metadesk =           rs.getString("metadesk");
                String metakey =            rs.getString("metakey");
                String customtitle =        rs.getString("customtitle");
                Boolean isPublished =       rs.getBoolean("is_published");
                Boolean isVisible =         rs.getBoolean("is_visible");
                int order =                 rs.getInt("order_catalog");
                int parent =                rs.getInt("parent");

                Category category = new Category(
                        id,
                        papId,
                        name,
                        translitName,
                        link,
                        icon,
                        metadesk,
                        metakey,
                        customtitle,
                        isPublished,
                        isVisible,
                        order,
                        parent
                );

                if (parent == 0) {
                    container.getParents().put(id, category);
                } else {
                    container.getChildren().put(id, category);
                }
            }
            return container;
        }
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
