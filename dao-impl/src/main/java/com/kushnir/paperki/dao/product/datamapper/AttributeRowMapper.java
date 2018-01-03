package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.product.Attribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttributeRowMapper implements RowMapper<Attribute> {

    @Override
    public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attribute attribute = new Attribute(
                rs.getString("name"),
                rs.getString("value"),
                rs.getInt("order_attr")
        );
        return attribute;
    }
}
