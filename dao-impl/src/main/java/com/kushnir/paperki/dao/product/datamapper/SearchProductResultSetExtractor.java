package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.DiscountType;
import com.kushnir.paperki.model.product.SearchProduct;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.kushnir.paperki.model.calculation.PriceCalculator.calculateDiscountedPrice;
import static com.kushnir.paperki.model.calculation.PriceCalculator.getPriceWithVAT;


public class SearchProductResultSetExtractor implements ResultSetExtractor<ArrayList<SearchProduct>> {

    @Override
    public ArrayList<SearchProduct> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<SearchProduct> products = new ArrayList<>();
        while(rs.next()) {
            int id =                            rs.getInt("id_product");
            int pnt =                           rs.getInt("pnt");
            String personalGroupName =          rs.getString("personal_group_name");
            String fullName =                   rs.getString("full_name");
            String shortName =                  rs.getString("short_name");
            String link =                       rs.getString("link");
            String categoryTranslitName =       rs.getString("cat_translit_name");

            String measure =                    rs.getString("measure");
            String countryFrom =                rs.getString("country_from");
            String countryMade =                rs.getString("country_made");

            // цена =======================================================================
            int VAT =                   rs.getInt("vat");
            double basePrice =          rs.getDouble("base_price");
            double basePriceWithVAT =   getPriceWithVAT(basePrice, VAT);

            // скидки =====================================================================
            Discount discount =         null;
            String discountTypeValue =  rs.getString("dtype");

            if(discountTypeValue != null) {
                DiscountType discountType = DiscountType.valueOf(discountTypeValue);
                double dValue =         rs.getDouble("value_double");
                int iValue =            rs.getInt("value_int");

                discount = new Discount(
                        discountType,
                        dValue,
                        iValue
                );
            }

            double finalPrice = calculateDiscountedPrice(discount, basePrice);
            double finalPriceWithVAT = getPriceWithVAT(finalPrice, VAT);

            products.add(new SearchProduct(
                    id,
                    pnt,
                    fullName,
                    shortName,
                    link,
                    categoryTranslitName,
                    measure,
                    countryFrom,
                    countryMade,
                    basePrice,
                    basePriceWithVAT,
                    finalPrice,
                    finalPriceWithVAT,
                    VAT
            ));
        }
        return products;
    }
}
