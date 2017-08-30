package com.kushnir.paperki.service;

import com.kushnir.paperki.model.*;

import com.kushnir.paperki.service.exceptions.NotEnoughQuantityAvailableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Transactional
public class CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    @Transactional
    public void addToCart (Cart cart, AddProductRequest addProductRequest) throws NotEnoughQuantityAvailableException {
        LOGGER.debug("addToCart({}) >>>", addProductRequest);

        if(addProductRequest.getQuantity() < 1)
            throw new NotEnoughQuantityAvailableException("Запрошенное количество меньше нуля");

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
        calculate(cart);
    }

    @Transactional
    public void deleteFromCart(Cart cart, Integer pnt) {
        LOGGER.debug("deleteFromCart({}) >>>", pnt);
        if(cart != null) {
            cart.getItems().remove(pnt);
        }
        calculate(cart);
    }

    @Transactional
    private void calculate(Cart cart) {
        double total =                          0d;
        double totalWithVAT =                   0d;
        double totalWithDiscount =              0d;
        double totalWithDiscountWithVAT =       0d;
        double totalDiscount =                  0d;
        double totalVAT =                       0d;
        Coupon coupon =                         null;
        Present present =                       null;
        Payment payment =                       null;
        Shipment shipment =                     null;
        double paymentCost =                    0d;
        double shipmentCost =                   0d;
        double finalTotal =                     0d;
        double finalTotalWithVAT =              0d;

        HashMap<Integer, CartProduct> items = cart.getItems();
        if(items != null || items.size() > 0) {
            for(Map.Entry<Integer, CartProduct> entry : items.entrySet()) {
                CartProduct cartProduct = entry.getValue();

                total +=                        cartProduct.getTotal();
                totalWithVAT +=                 cartProduct.getTotalWithVAT();
                totalWithDiscount +=            cartProduct.getTotalWithDiscount();
                totalWithDiscountWithVAT +=     cartProduct.getTotalWithDiscountWithVAT();
                totalDiscount +=                cartProduct.getTotalDiscount();
                totalVAT +=                     cartProduct.getTotalVAT();
            }
        }

        finalTotalWithVAT = totalWithDiscountWithVAT
                            + paymentCost
                            + shipmentCost;

        cart.setTotal(total);
        cart.setTotalWithVAT(totalWithVAT);
        cart.setTotalWithDiscount(totalWithDiscount);
        cart.setTotalWithDiscountWithVAT(totalWithDiscountWithVAT);
        cart.setTotalDiscount(totalDiscount);
        cart.setFinalTotalWithVAT(finalTotalWithVAT);
        cart.setTotalVAT(totalVAT);
    }

    @Transactional
    private void calculateCartProduct(CartProduct inCartProduct,
                                      AddProductRequest addProductRequest,
                                      AvailableProduct availableProduct) throws NotEnoughQuantityAvailableException {

        Integer quantityOld = inCartProduct.getQuantity();

        if(     availableProduct.getQuantityAvailable() < 1 ||
                (addProductRequest.getQuantity() + quantityOld) > availableProduct.getQuantityAvailable()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }

        Integer pnt =                           availableProduct.getPnt();
        String fullName =                       availableProduct.getFullName();
        String shortName =                      availableProduct.getShortName();
        Integer VAT =                           availableProduct.getVAT();
        Double vatValue =                       getDouble(1 + (VAT/100.0));
        Double vatAmount =                      0d;
        Integer quantity =                      addProductRequest.getQuantity() + quantityOld;
        Double currentPrice =                   availableProduct.getPrices().get(1).getBasePrice();
        Double currentPriceWithVAT =            getDouble(currentPrice * vatValue);
        Double discountAmount = 0d;
        Double discountedPrice = 0d;
        Double discountedPriceWithVAT = 0d;
        Double finallyPrice = 0d;
        Double finallyPriceWithVAT = 0d;
        Double total = 0d;
        Double totalWithVAT = 0d;
        Discount discount =                     availableProduct.getDiscount();
        // ========================================================================
        if (discount != null) {
            if(discount.getDiscountType().equals(DiscountType.OVERRIDE)) {

                discountedPrice =               discount.getValueDouble();
                discountAmount =                currentPrice - discountedPrice;
            } else if(discount.getDiscountType().equals(DiscountType.PROCENT)) {

                discountAmount =                getDouble(currentPrice * (discount.getValueInt()/100.0));
                discountedPrice =               currentPrice - discountAmount;
            } else if (discount.getDiscountType().equals(DiscountType.SUBSTRACT)) {

                discountAmount =                discount.getValueDouble();
                discountedPrice =               currentPrice - discountAmount;
            } else {}

            discountedPriceWithVAT =            getDouble(discountedPrice * vatValue);
        }
        // ========================================================================
        finallyPrice =                          currentPrice - discountAmount;
        // ========================================================================
        finallyPriceWithVAT =                   getDouble(finallyPrice * vatValue);
        // ========================================================================
        total =                                 getDouble(finallyPrice * quantity);
        // ========================================================================
        totalWithVAT =                          getDouble(total * vatValue);
        // ========================================================================
        vatAmount =                             getDouble(totalWithVAT - total);
        // ========================================================================

        inCartProduct.setFullName(fullName);
        inCartProduct.setShortName(shortName);
        inCartProduct.setVAT(VAT);
        inCartProduct.setVatValue(vatValue);
        inCartProduct.setVatAmount(vatAmount);
        inCartProduct.setQuantity(quantity);
        inCartProduct.setCurrentPrice(currentPrice);
        inCartProduct.setCurrentPriceWithVAT(currentPriceWithVAT);
        inCartProduct.setDiscountAmount(discountAmount);
        inCartProduct.setDiscountedPrice(discountedPrice);
        inCartProduct.setDiscountedPriceWithVAT(discountedPriceWithVAT);
        inCartProduct.setFinallyPrice(finallyPrice);
        inCartProduct.setFinallyPriceWithVAT(finallyPriceWithVAT);
        inCartProduct.setTotal(total);
        inCartProduct.setTotalWithVAT(totalWithVAT);
    }

    @Transactional
    private CartProduct createCartProduct (AvailableProduct availableProduct, AddProductRequest addProductRequest)
            throws NotEnoughQuantityAvailableException {
        // ========================================================================
        if(availableProduct.getQuantityAvailable() < 1 ||
                availableProduct.getQuantityAvailable() < addProductRequest.getQuantity()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }

        int pnt =                           availableProduct.getPnt();
        String fullName =                   availableProduct.getFullName();
        String shortName =                  availableProduct.getShortName();
        String link =                       availableProduct.getLink();
        int VAT =                           availableProduct.getVAT();
        double vatValue =                   getDouble(1 + (VAT/100.0));
        int quantity =                      addProductRequest.getQuantity();
        double currentPrice =               availableProduct.getPrices().get(1).getBasePrice();
        double currentPriceWithVAT =        getDouble(currentPrice * vatValue);
        double discountedPrice =            0d;
        double discountedPriceWithVAT =     0d;
        double discountAmount =             0d;
        double finalPrice =                 0d;
        double finalPriceWithVAT =          0d;
        double total =                      0d;
        double totalWithVAT =               0d;
        double totalWithDiscount =          0d;
        double totalWithDiscountWithVAT =   0d;
        double totalDiscount =              0d;
        double finalTotal =                 0d;
        double finalTotalWithVAT =          0d;
        double totalVAT =                   0d;

        Discount discount =                 availableProduct.getDiscount();
        // ========================================================================
        if (discount != null) {
            if(discount.getDiscountType().equals(DiscountType.OVERRIDE)) {

                discountedPrice =           discount.getValueDouble();
                discountAmount =            currentPrice - discountedPrice;
            } else if(discount.getDiscountType().equals(DiscountType.PROCENT)) {

                discountAmount =            getDouble(currentPrice * (discount.getValueInt()/100.0));
                discountedPrice =           currentPrice - discountAmount;
            } else if (discount.getDiscountType().equals(DiscountType.SUBSTRACT)) {

                discountAmount =            discount.getValueDouble();
                discountedPrice =           currentPrice - discountAmount;
            } else {}

            discountedPriceWithVAT =        getDouble(discountedPrice * vatValue);
        }
        // ========================================================================
        finalPrice =                        currentPrice - discountAmount;
        // ========================================================================
        finalPriceWithVAT =                 getDouble(finalPrice * vatValue);
        // ========================================================================
        total =                             getDouble(finalPrice * quantity);
        // ========================================================================
        totalWithVAT =                      getDouble(total * vatValue);
        // ========================================================================
        totalWithDiscount =                 getDouble(discountedPrice + quantity);
        // ========================================================================
        totalWithDiscountWithVAT =          getDouble(totalWithDiscount * vatValue);
        // ========================================================================
        totalDiscount =                     getDouble(totalWithVAT - totalWithDiscountWithVAT);
        // ========================================================================
        finalTotal
        return new CartProduct(
                pnt,
                fullName,
                shortName,
                link,

        );






        /*
        Integer pnt =                           availableProduct.getPnt();
        String fullName =                       availableProduct.getFullName();
        String shortName =                      availableProduct.getShortName();
        Integer VAT =                           availableProduct.getVAT();
        Double vatValue =                       getDouble(1 + (VAT/100.0));
        Double vatAmount = 0d;
        Integer quantity =                      addProductRequest.getQuantity();
        Double currentPrice =                   availableProduct.getPrices().get(1).getBasePrice();
        Double currentPriceWithVAT =            getDouble(currentPrice * vatValue);
        Double discountAmount = 0d;
        Double discountedPrice = 0d;
        Double discountedPriceWithVAT = 0d;
        Double finallyPrice = 0d;
        Double finallyPriceWithVAT = 0d;
        Double total = 0d;
        Double totalWithVAT = 0d;
        Discount discount =                     availableProduct.getDiscount();
        // ========================================================================
        if (discount != null) {
            if(discount.getDiscountType().equals(DiscountType.OVERRIDE)) {

                discountedPrice =               discount.getValueDouble();
                discountAmount =                currentPrice - discountedPrice;
            } else if(discount.getDiscountType().equals(DiscountType.PROCENT)) {

                discountAmount =                getDouble(currentPrice * (discount.getValueInt()/100.0));
                discountedPrice =               currentPrice - discountAmount;
            } else if (discount.getDiscountType().equals(DiscountType.SUBSTRACT)) {

                discountAmount =                discount.getValueDouble();
                discountedPrice =               currentPrice - discountAmount;
            } else {}

            discountedPriceWithVAT =            getDouble(discountedPrice * vatValue);
        }
        // ========================================================================
        finallyPrice =                          currentPrice - discountAmount;
        // ========================================================================
        finallyPriceWithVAT =                   getDouble(finallyPrice * vatValue);
        // ========================================================================
        total =                                 getDouble(finallyPrice * quantity);
        // ========================================================================
        totalWithVAT =                          getDouble(total * vatValue);
        // ========================================================================
        vatAmount =                             getDouble(totalWithVAT - total);
        // ========================================================================

        return new CartProduct(
                pnt,
                fullName,
                shortName,
                VAT,
                vatValue,
                vatAmount,
                quantity,
                currentPrice,
                currentPriceWithVAT,
                discountAmount,
                discountedPrice,
                discountedPriceWithVAT,
                finallyPrice,
                finallyPriceWithVAT,
                total,
                totalWithVAT
        );*/
    }



    private Double getDouble(Double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.UP).doubleValue();
    }
}
