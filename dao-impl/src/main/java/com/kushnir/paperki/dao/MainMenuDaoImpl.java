package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.MenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainMenuDaoImpl implements MenuDao {

    private static final Logger LOGGER = LogManager.getLogger(MainMenuDaoImpl.class);

    private final String P_FINAL_NAME = "p_translit_name";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${menu.main.getAll}")
    private String getAllSqlQuery;

    @Value("${menu.main.getByTName}")
    private String getByTNameSqlQuery;

    @Override
    public ArrayList<MenuItem> getAll() throws DataAccessException {
        ArrayList<MenuItem> menuItemsList =
                (ArrayList<MenuItem>) namedParameterJdbcTemplate.query(getAllSqlQuery, new MenuRowMapper());
        LOGGER.debug("getAll() >>> {}", menuItemsList);
        return menuItemsList;
    }

    @Override
    public MenuItem getItemByTName(String translitName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_FINAL_NAME, translitName);
        MenuItem menuItem = namedParameterJdbcTemplate.queryForObject(getByTNameSqlQuery, parameterSource, new MenuRowMapper());
        LOGGER.debug("getItemByTName() >>> {}", menuItem);
        return menuItem;
    }

    private class MenuRowMapper implements RowMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet resultSet, int i) throws SQLException {
            MenuItem menuItem = new MenuItem(
                    resultSet.getInt("id_menu_item"),
                    resultSet.getInt("id_menu"),
                    resultSet.getString("name"),
                    resultSet.getString("translit_name"),
                    resultSet.getString("link"),
                    resultSet.getTimestamp("create_date").toLocalDateTime(),
                    resultSet.getTimestamp("edit_date").toLocalDateTime(),
                    resultSet.getBoolean("is_published")
            );
            return menuItem;
        }
    }
}
