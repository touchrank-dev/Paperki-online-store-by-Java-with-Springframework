package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.MenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository("menuDao")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MenuDaoImpl implements MenuDao {

    private static final Logger LOGGER = LogManager.getLogger(MenuDaoImpl.class);

    private final String P_NAME_MENU = "p_translit_name_menu";
    private final String P_TRANSLIT_NAME = "p_translit_name";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${menu.getAll}")
    private String getAllSqlQuery;

    @Value("${menu.getByTName}")
    private String getByTNameSqlQuery;

    @Override
    public ArrayList<MenuItem> getAll(String nameMenu) throws DataAccessException {
        LOGGER.debug("getAll(nameMenu = {}) >>>", nameMenu);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_NAME_MENU, nameMenu);
        ArrayList<MenuItem> menuItemsList =
                (ArrayList<MenuItem>) namedParameterJdbcTemplate.query(getAllSqlQuery,
                        parameterSource, new MenuRowMapper());
        return menuItemsList;
    }

    @Override
    public MenuItem getItemByTName(String nameMenu, String translitName) throws DataAccessException {
        LOGGER.debug("getItemByTName(nameMenu - {}, translitName - {}) >>>", nameMenu, translitName);
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(P_NAME_MENU, nameMenu);
            parameterSource.addValue(P_TRANSLIT_NAME, translitName);
            MenuItem menuItem = namedParameterJdbcTemplate.queryForObject(getByTNameSqlQuery, parameterSource, new MenuRowMapper());
            return menuItem;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Запрос getItemByTName({}, {}) не вернул результата >>> {}", nameMenu, translitName, e.getMessage());
            return null;
        }
    }

    private class MenuRowMapper implements RowMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
