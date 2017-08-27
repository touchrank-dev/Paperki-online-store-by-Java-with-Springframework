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
        LOGGER.debug("addToCart() >>>\nPRODUCT TO ADD: {}", addProductRequest);

        int PNT = addProductRequest.getPnt();

        AvailableProduct availableProduct = productBean.getAvailableproductByPNT(PNT);

        if(cart != null) {
            HashMap<Integer, CartProduct> items = cart.getItems();
            if(items != null) {
                if(availableProduct != null) {
                    CartProduct inCartProduct = items.get(PNT);
                    if (inCartProduct != null) {
                        calculateCartProduct(inCartProduct, addProductRequest);
                    } else {
                        items.put(PNT, createCartProduct(availableProduct, addProductRequest));
                    }
                }
            }
        }
    }

    private void calculate(Cart cart) {}

    private void calculateCartProduct(CartProduct cartProduct, AddProductRequest addProductRequest) {}

    private CartProduct createCartProduct (AvailableProduct availableProduct, AddProductRequest addProductRequest)
            throws NotEnoughQuantityAvailableException {
        // ========================================================================
        if(availableProduct.getQuantityAvailable() < addProductRequest.getQuantity()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }
        // ========================================================================
        Integer VAT = availableProduct.getVAT();
        // ========================================================================
        Double vatAmount = 1 + (VAT/100.0);
        // ========================================================================
        Double currentPrice = availableProduct.getPrices().get(1).getBasePrice();
        // ========================================================================
        Double currentPriceWithVAT =  new BigDecimal(currentPrice * vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
        // ========================================================================
        Discount discount = availableProduct.getDiscount();
        if (discount != null) {
            // есть скидки на товар
            // рассчет скидки в зависимости от типа Скидки
            if(discount.getDiscountType().equals(DiscountType.OVERRIDE)) {

            } else if(discount.getDiscountType().equals(DiscountType.PROCENT)) {

            } else if (discount.getDiscountType().equals(DiscountType.SUBSTRACT)) {

            } else {}
        } else {
            // скидок на товар нет
        }


        return null;
    }
}
