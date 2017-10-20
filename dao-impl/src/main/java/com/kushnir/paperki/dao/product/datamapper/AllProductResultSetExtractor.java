package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.ProductSimple;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AllProductResultSetExtractor implements ResultSetExtractor<HashMap<Integer, ProductSimple>> {

    @Override
    public HashMap<Integer, ProductSimple> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, ProductSimple> products = new HashMap<>();

        while(rs.next()) {
            Integer id =                    rs.getInt("id_product");
            Integer papId =                 rs.getInt("pap_id");
            Integer pnt =                   rs.getInt("pnt");
            String translitName =           rs.getString("translit_name");

            Integer catalogId =             rs.getInt("id_catalog");
            Integer catalogPapID =          rs.getInt("catpapid");
            String catalogTranslitName =    rs.getString("cattransname");

            CategorySimple categorySimple = new CategorySimple(
                    catalogId,
                    catalogPapID,
                    catalogTranslitName
            );

            ProductSimple product = new ProductSimple(
                    id,
                    papId,
                    pnt,
                    translitName,
                    categorySimple
            );

            products.put(pnt, product);
        }

        return products;
    }
}
