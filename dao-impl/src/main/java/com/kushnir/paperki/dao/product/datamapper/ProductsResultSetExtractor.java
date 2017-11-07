package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.DiscountType;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.product.Product;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static com.kushnir.paperki.model.calculation.PriceCalculator.calculateDiscountedPrice;
import static com.kushnir.paperki.model.calculation.PriceCalculator.getPriceWithVAT;

public class ProductsResultSetExtractor implements ResultSetExtractor {


    @Override
    public Object extractData(ResultSet rs) throws SQLException {

        HashMap<Integer, Product> products = new HashMap<Integer ,Product>();

        while (rs.next()) {

            int idProduct =             rs.getInt("id_product");
            int availableDay =          rs.getInt("available_day");
            int quantityAvailable =     rs.getInt("quantity_available");

            if (quantityAvailable < 1 && availableDay < 1) continue;

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

            // скидка =====================================================================
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

            double finalPrice =         calculateDiscountedPrice(discount, basePrice);
            double finalPriceWithVAT =  getPriceWithVAT(finalPrice, VAT);

            Product product = products.get(idProduct);

            if(product == null) {
                product = new Product(
                        idProduct,
                        rs.getInt("pap_id"),
                        rs.getInt("pnt"),
                        rs.getString("full_name"),
                        rs.getString("short_name"),
                        rs.getString("translit_name"),
                        rs.getString("link"),
                        rs.getString("country_from"),
                        rs.getString("country_made"),
                        rs.getString("measure"),
                        availableDay,
                        quantityAvailable,

                        basePrice,
                        basePriceWithVAT,
                        finalPrice,
                        finalPriceWithVAT,

                        VAT,
                        rs.getBoolean("is_published"),
                        rs.getBoolean("is_visible"),
                        new Brand(rs.getString("bname"), rs.getString("btname")),
                        new HashMap<Integer, Price>()
                );

                if (price != null) {
                    product.getPrices().put(quantityStart, price);
                }
                product.setDiscount(discount);
                products.put(idProduct, product);

            } else {
                if (price != null) {
                    product.getPrices().put(quantityStart, price);
                }
            }
        }
        return products;
    }
}
