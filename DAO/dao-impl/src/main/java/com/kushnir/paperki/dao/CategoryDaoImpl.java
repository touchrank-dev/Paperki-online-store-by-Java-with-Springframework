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
    public HashMap<Integer, Category> getAll() {
        HashMap<Integer, Category> map =
                (HashMap) jdbcTemplate.query(getAllSqlQuery, new CategoryResultSetExtractor());
        LOGGER.debug("getAll() {}", map);
        return map;
    }


    private class CategoryResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, Category> map = new HashMap<Integer, Category>();
            Category category;
            int parent;
            while (rs.next()) {
                System.out.println(rs.getInt("parent"));
                parent = rs.getInt("parent");
                category = new Category(
                        rs.getInt("id_category"),
                        rs.getString("name"),
                        rs.getString("translit_name_category"),
                        rs.getString("link"),
                        rs.getString("icon"),
                        rs.getInt("order_category")
                );

                if(parent == 0) {
                    if (map.get(category.getId()) == null) {
                        map.put(category.getId(), category);
                    }
                } else if(parent > 0) {
                    map.get(parent).getChilds().put(category.getId(), category);
                }
            }
            return map;
        }
    }

}