package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CategoryDaoImpl implements CategoryDao {

    private static final Logger LOGGER = LogManager.getLogger(CategoryDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${category.getAll}")
    private String getAllSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() {
        HashMap<Integer, HashMap<Integer, Category>> map =
                (HashMap) jdbcTemplate.query(getAllSqlQuery, new CategoryResultSetExtractor());
        LOGGER.debug("getAll() >>> {}", map);
        return map;
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
                        rs.getInt("id_category"),
                        rs.getString("name"),
                        rs.getString("translit_name"),
                        rs.getString("link"),
                        rs.getString("icon"),
                        rs.getInt("order_category")
                );

                HashMap<Integer, Category> mapCategory = new HashMap<Integer, Category>();
                //  добавляем себя же как ключ = 0
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

}