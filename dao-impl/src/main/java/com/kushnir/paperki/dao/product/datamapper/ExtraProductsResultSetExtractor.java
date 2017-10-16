package com.kushnir.paperki.dao.product.datamapper;

import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.DiscountType;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.product.Product;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static com.kushnir.paperki.model.calculation.PriceCalculator.calculateDiscountedPrice;
import static com.kushnir.paperki.model.calculation.PriceCalculator.getPriceWithVAT;

public class ExtraProductsResultSetExtractor implements ResultSetExtractor<HashMap<Integer, HashMap<Integer, Product>>> {


    @Override
    public HashMap<Integer, HashMap<Integer, Product>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, HashMap<Integer, Product>> products = new HashMap();

        while (rs.next()) {
            int idProduct =                 rs.getInt("id_product");
            Integer pnt =                   rs.getInt("pnt");
            Integer papId =                 rs.getInt("pap_id");
            Integer extraType =             rs.getInt("extra_type");
            String fullName =               rs.getString("full_name");
            String shortName =              rs.getString("short_name");
            String translitName =           rs.getString("translit_name");
            String catalogTranslitName =    rs.getString("cat_translit_name");
            String link =                   rs.getString("link");
            String countryFrom =            rs.getString("country_from");
            String countryMade =            rs.getString("country_made");
            String measure =                rs.getString("measure");
            Integer availableDay =          rs.getInt("available_day");
            Integer quantityAvailable =     rs.getInt("quantity_available");
            Boolean isPublished =           rs.getBoolean("is_published");
            Boolean isVisible =             rs.getBoolean("is_visible");
            Brand brand =                   new Brand(rs.getString("bname"), rs.getString("btname"));

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


            HashMap<Integer, Product> prds = products.get(extraType);

            if (prds == null || prds.size() < 1) {
                prds = new HashMap<>();
                Product product = new Product(
                        idProduct,
                        papId,
                        pnt,
                        fullName,
                        shortName,
                        translitName,
                        link,
                        countryFrom,
                        countryMade,
                        measure,
                        availableDay,
                        quantityAvailable,
                        basePrice,
                        basePriceWithVAT,
                        finalPrice,
                        finalPriceWithVAT,
                        VAT,
                        isPublished,
                        isVisible,
                        brand,
                        new HashMap()
                );
                product.setCategoryTranslitName(catalogTranslitName);

                if (price != null) {
                    product.getPrices().put(quantityStart, price);
                }

                prds.put(pnt, product);

                products.put(extraType, prds);

            } else {
                Product product = prds.get(pnt);
                if (product == null) {
                    product = new Product(
                            idProduct,
                            papId,
                            pnt,
                            fullName,
                            shortName,
                            translitName,
                            link,
                            countryFrom,
                            countryMade,
                            measure,
                            availableDay,
                            quantityAvailable,
                            basePrice,
                            basePriceWithVAT,
                            finalPrice,
                            finalPriceWithVAT,
                            VAT,
                            isPublished,
                            isVisible,
                            brand,
                            new HashMap()
                    );
                    product.setCategoryTranslitName(catalogTranslitName);

                    if (price != null) {
                        product.getPrices().put(quantityStart, price);
                    }

                    product.setDiscount(discount);
                    prds.put(pnt, product);
                } else {
                    if (price != null) {
                        product.getPrices().put(quantityStart, price);
                    }
                }
            }
        }
        return products;
    }
}
