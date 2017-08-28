package com.kushnir.paperki.service;

import com.kushnir.paperki.model.*;

import com.kushnir.paperki.service.exceptions.NotEnoughQuantityAvailableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    public void addToCart (Cart cart, AddProductRequest addProductRequest) throws NotEnoughQuantityAvailableException {
        LOGGER.debug("addToCart() >>>\nPRODUCT TO ADD REQUEST: {}", addProductRequest);

        int PNT = addProductRequest.getPnt();

        AvailableProduct availableProduct = productBean.getAvailableproductByPNT(PNT);

        if(cart != null) {
            HashMap<Integer, CartProduct> items = cart.getItems();
            if(items != null) {
                if(availableProduct != null) {
                    CartProduct inCartProduct = items.get(PNT);
                    if (inCartProduct != null) {
                        calculateCartProduct(inCartProduct, addProductRequest, availableProduct);
                    } else {
                        items.put(PNT, createCartProduct(availableProduct, addProductRequest));
                    }
                }
            }
        }
    }

    private void calculate(Cart cart) {}

    private void calculateCartProduct(CartProduct cartProduct,
                                      AddProductRequest addProductRequest,
                                      AvailableProduct availableProduct) {

    }

    private CartProduct createCartProduct (AvailableProduct availableProduct, AddProductRequest addProductRequest)
            throws NotEnoughQuantityAvailableException {
        // ========================================================================
        if(availableProduct.getQuantityAvailable() < addProductRequest.getQuantity()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }
        Integer quantity;
        Integer VAT;
        Double vatAmount;
        Double currentPrice;
        Double currentPriceWithVAT;
        Double discountAmount = 0.0;
        Double discountedPrice = 0.0;
        Double discountedPriceWithVAT = 0.0;
        Double totalPrice;
        Double totalPriceWithVAT;
        Double totalDiscountedPrice;
        Double totalDiscountedPriceWithVAT;
        Discount discount = availableProduct.getDiscount();

        // ========================================================================
        quantity = addProductRequest.getQuantity();
        // ========================================================================
        VAT = availableProduct.getVAT();
        // ========================================================================
        vatAmount = 1 + (VAT/100.0);
        // ========================================================================
        currentPrice = availableProduct.getPrices().get(1).getBasePrice();
        // ========================================================================
        currentPriceWithVAT =  new BigDecimal(currentPrice * vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        if (discount != null) {
            // есть скидки на товар
            // рассчет скидки в зависимости от типа Скидки
            if(discount.getDiscountType().equals(DiscountType.OVERRIDE)) {
                discountAmount = currentPrice - discount.getValueDouble();
            } else if(discount.getDiscountType().equals(DiscountType.PROCENT)) {
                discountAmount = currentPrice * (discount.getValueInt()/100.0);
            } else if (discount.getDiscountType().equals(DiscountType.SUBSTRACT)) {
                discountAmount = discount.getValueDouble();
            } else {}

            discountedPrice = currentPrice - discountAmount;
            discountedPriceWithVAT = new BigDecimal(discountedPrice * vatAmount)
                    .setScale(2, RoundingMode.UP).doubleValue();
        }
        // ========================================================================
        totalPrice = new BigDecimal(currentPrice * quantity)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        totalPriceWithVAT = new BigDecimal(totalPrice * vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        totalDiscountedPrice = new BigDecimal(discountedPrice * quantity)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        totalDiscountedPriceWithVAT = new BigDecimal(totalDiscountedPrice * vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        return new CartProduct(
                addProductRequest.getPnt(),
                "",
                "",
                VAT,
                vatAmount,
                quantity,
                currentPrice,
                currentPriceWithVAT,
                discountAmount,
                discountedPrice,
                discountedPriceWithVAT,
                totalPrice,
                totalPriceWithVAT,
                totalDiscountedPrice,
                totalDiscountedPriceWithVAT
        );
    }
}
