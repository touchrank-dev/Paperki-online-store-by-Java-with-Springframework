package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.calculation.PriceCalculator;
import com.kushnir.paperki.model.product.Attribute;
import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.Product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDaoImpl implements ProductDao {

    private static final Logger LOGGER = LogManager.getLogger(ProductDaoImpl.class);

    private static final String P_PNT = "p_pnt";
    private static final String P_PRODUCT_T_NAME = "p_product_t_name";
    private static final String P_CATEGORY_T_NAME = "p_category_t_name";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private PriceCalculator priceCalculator;

    @Value("${product.getByPNT}")
    private String getByPNTSqlQuery;

    @Value("${product.getProductByTName}")
    private String getProductByTNameSqlQuery;

    @Value("${product.getProductsByCategoryTName}")
    private String getProductsByCategoryTNameSqlQuery;

    @Value("${product.getAvailableProductByPNT}")
    private String getAvailableProductByPNTSqlQuery;

    @Value("${product.getAttributesByProductPNT}")
    private String getAttributesByPNTSqlQuery;


    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) throws DataAccessException {
        LOGGER.debug("getProductListByCategoryTName({}) >>>", categoryTName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_CATEGORY_T_NAME, categoryTName);
        HashMap<Integer, Product> products =
                (HashMap<Integer, Product>) namedParameterJdbcTemplate.query(
                        getProductsByCategoryTNameSqlQuery,
                        parameterSource,
                        new ProductsResultSetExtractor());
        return products;
    }

    @Override
    public Product getProductByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getProductByPNT({}) >>>", pnt);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
        Product product = (Product) namedParameterJdbcTemplate.query(
                getByPNTSqlQuery,
                parameterSource,
                new ProductResultSetExtractor());
        return product;
    }

    @Override
    public Product getProductByTName(String TName) throws DataAccessException {
        LOGGER.debug("getProductByTName({}) >>>", TName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PRODUCT_T_NAME, TName);
        Product product = (Product) namedParameterJdbcTemplate.query(
                getProductByTNameSqlQuery,
                parameterSource,
                new ProductResultSetExtractor()
        );
        return product;
    }

    @Override
    public AvailableProduct getAvailableProductByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getAvailableProductByPNT({}) >>>", pnt);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
        AvailableProduct availableProduct = (AvailableProduct) namedParameterJdbcTemplate.query(
                getAvailableProductByPNTSqlQuery,
                parameterSource,
                new AvailableProductResultSetExtractor());
        return availableProduct;
    }

    @Override
    public ArrayList<Attribute> getAttributesByPNT(Integer pnt) throws DataAccessException {
        LOGGER.debug("getAttributesByPNT({}) >>>", pnt);
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PNT, pnt);
            ArrayList<Attribute> attributes = (ArrayList<Attribute>)
                    namedParameterJdbcTemplate.query(
                            getAttributesByPNTSqlQuery,
                            parameterSource,
                            new AttributeRowMapper());
            return attributes;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private class ProductsResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException {

            HashMap<Integer, Product> products = new HashMap<Integer ,Product>();

            while (rs.next()) {

                int idProduct =             rs.getInt("id_product");

                // цена =======================================================================
                int VAT =                   rs.getInt("vat");
                double basePrice =          rs.getDouble("base_price");
                double basePriceWithVAT =   priceCalculator.getPriceWithVat(basePrice, VAT);

                // цена от количества =========================================================
                Price price =               null;
                int quantityStart =         rs.getInt("quantity_start");
                double value =              rs.getDouble("value");
                double valueWithVAT =       priceCalculator.getPriceWithVat(value, VAT);

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

                double finalPrice =         priceCalculator.calculateDiscountedPrice(discount, basePrice);
                double finalPriceWithVAT =  priceCalculator.getPriceWithVat(finalPrice, VAT);

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
                            rs.getInt("available_day"),
                            rs.getInt("quantity_available"),

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

    private class ProductResultSetExtractor implements ResultSetExtractor {

        @Override
        public Product extractData(ResultSet rs) throws SQLException {
            Product product = null;

            while (rs.next()) {
                int idProduct =             rs.getInt("id_product");

                // цена =======================================================================
                int VAT =                   rs.getInt("vat");
                double basePrice =          rs.getDouble("base_price");
                double basePriceWithVAT =   priceCalculator.getPriceWithVat(basePrice, VAT);

                // цена от количества =========================================================
                Price price =               null;
                int quantityStart =         rs.getInt("quantity_start");
                double value =              rs.getDouble("value");
                double valueWithVAT =       priceCalculator.getPriceWithVat(value, VAT);

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

                double finalPrice = priceCalculator.calculateDiscountedPrice(discount, basePrice);
                double finalPriceWithVAT = priceCalculator.getPriceWithVat(finalPrice, VAT);

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
                            rs.getInt("available_day"),
                            rs.getInt("quantity_available"),

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

                } else {
                    if (price != null) {
                        product.getPrices().put(quantityStart, price);
                    }
                }
            }
            return product;
        }
    }

    private class AvailableProductResultSetExtractor implements ResultSetExtractor {

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
                double basePriceWithVAT =   priceCalculator.getPriceWithVat(basePrice, VAT);

                // цена от количества =========================================================
                Price price =               null;
                int quantityStart =         rs.getInt("quantity_start");
                double value =              rs.getDouble("value");
                double valueWithVAT =       priceCalculator.getPriceWithVat(value, VAT);

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

                double finalPrice = priceCalculator.calculateDiscountedPrice(discount, basePrice);
                double finalPriceWithVAT = priceCalculator.getPriceWithVat(finalPrice, VAT);

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
                    availableProduct.getPrices().put(quantityStart, price);
                } else {
                    availableProduct.getPrices().put(quantityStart, price);
                }
            }
            return availableProduct;
        }
    }

    private class AttributeRowMapper implements RowMapper<Attribute> {

        @Override
        public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
            Attribute attribute = new Attribute(
                    rs.getInt("id_product_attributes"),
                    rs.getString("name"),
                    rs.getString("value"),
                    rs.getInt("order_attr")
            );
            return attribute;
        }
    }

}
