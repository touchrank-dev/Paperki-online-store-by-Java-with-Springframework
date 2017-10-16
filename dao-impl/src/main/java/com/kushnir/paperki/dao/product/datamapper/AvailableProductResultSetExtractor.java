package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.DiscountType;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.product.AvailableProduct;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.kushnir.paperki.model.calculation.PriceCalculator.calculateDiscountedPrice;
import static com.kushnir.paperki.model.calculation.PriceCalculator.getPriceWithVAT;

public class AvailableProductResultSetExtractor implements ResultSetExtractor {


    @Override
    public AvailableProduct extractData(ResultSet rs) throws SQLException {
        AvailableProduct availableProduct = null;
        while(rs.next()) {

            int id =                    rs.getInt("id_product");
            int pnt =                   rs.getInt("pnt");
            String fullName =           rs.getString("full_name");
            String shortName =          rs.getString("short_name");
            int quantityAvailable =     rs.getInt("quantity_available");

            // цена =======================================================================
            int VAT =                   rs.getInt("vat");
            double basePrice =          rs.getDouble("base_price");
            double basePriceWithVAT =   getPriceWithVAT(basePrice, VAT);

            // цена от количества =========================================================
            Price price =               null;
            int quantityStart =         rs.getInt("quantity_start");
            double value =              rs.getDouble("value");
            double valueWithVAT =       getPriceWithVAT(value, VAT);

            if (quantityStart > 0 && value > 0d) {
                price = new Price(quantityStart, value, valueWithVAT);
            }

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

            // ============================================================================

            double finalPrice = calculateDiscountedPrice(discount, basePrice);
            double finalPriceWithVAT = getPriceWithVAT(finalPrice, VAT);

            if(availableProduct == null) {
                availableProduct = new AvailableProduct(
                        id,
                        pnt,
                        fullName,
                        shortName,
                        basePrice,
                        basePriceWithVAT,
                        finalPrice,
                        finalPriceWithVAT,
                        VAT,
                        quantityAvailable,
                        discount
                );
                if (price != null) {
                    availableProduct.getPrices().put(quantityStart, price);
                }
            } else {
                if (price != null) {
                    availableProduct.getPrices().put(quantityStart, price);
                }
            }
        }
        return availableProduct;
    }
}
