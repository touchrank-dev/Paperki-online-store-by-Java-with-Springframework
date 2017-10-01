package com.kushnir.paperki.service;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.calculation.PriceCalculator;
import com.kushnir.paperki.model.payment.Payment;
import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.service.exceptions.NotEnoughQuantityAvailableException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    @Autowired
    private PriceCalculator priceCalculator;

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

    public void deleteFromCart(Cart cart, Integer pnt) {
        LOGGER.debug("deleteFromCart({}) >>>", pnt);
        if(cart != null) {
            HashMap<Integer, CartProduct> items = cart.getItems();
            if (items != null) {
                items.remove(pnt);
            }
        }
        calculate(cart);
    }

    private void calculate(Cart cart) {
        double total =                          0d;
        double totalWithVAT =                   0d;
        double totalDiscount =                  0d;
        double totalVAT =                       0d;
        Coupon coupon =                         cart.getCoupon();
        Present present =                       cart.getPresent();
        Payment payment =                       cart.getPayment();
        Shipment shipment =                     cart.getShipment();
        double paymentCost =                    0d;
        double shipmentCost =                   0d;
        double finalTotal =                     0d;
        double finalTotalWithVAT =              0d;

        HashMap<Integer, CartProduct> items = cart.getItems();
        if(items != null || items.size() > 0) {
            for(Map.Entry<Integer, CartProduct> entry : items.entrySet()) {

                CartProduct cartProduct = entry.getValue();

                if (cartProduct != null) {
                    total += cartProduct.getTotal();
                    totalWithVAT += cartProduct.getTotalWithVAT();
                    totalDiscount += cartProduct.getTotalDiscount();
                    totalVAT += cartProduct.getTotalVAT();
                }
            }
        }

        finalTotal =        priceCalculator.getDouble(total
                            + paymentCost
                            + shipmentCost);

        finalTotalWithVAT = priceCalculator.getDouble(totalWithVAT
                            + paymentCost
                            + shipmentCost);

        cart.setTotal(total);
        cart.setTotalWithVAT(priceCalculator.getDouble(totalWithVAT));
        cart.setTotalDiscount(totalDiscount);
        cart.setFinalTotal(finalTotal);
        cart.setFinalTotalWithVAT(priceCalculator.getDouble(finalTotalWithVAT));
        cart.setTotalVAT(totalVAT);
    }


    private void calculateCartProduct(CartProduct inCartProduct,
                                      AddProductRequest addProductRequest,
                                      AvailableProduct availableProduct) throws NotEnoughQuantityAvailableException {

        int quantityOld =                   inCartProduct.getQuantity();
        int requestedQuantity =             addProductRequest.getQuantity();
        int newQuantity =                   quantityOld + requestedQuantity;

        /*if(     availableProduct.getQuantityAvailable() < 1 ||
                newQuantity > availableProduct.getQuantityAvailable()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }*/

        int id =                            availableProduct.getId();
        int pnt =                           availableProduct.getPnt();
        String fullName =                   availableProduct.getFullName();
        String shortName =                  availableProduct.getShortName();
        String link =                       availableProduct.getLink();
        int VAT =                           availableProduct.getVAT();
        int quantity =                      newQuantity;
        double currentPrice =               availableProduct.getBasePrice();
        double currentPriceWithVAT =        priceCalculator.getPriceWithVat(currentPrice, VAT);
        double discountAmount =             0d;
        double finalPrice =                 0d;
        double finalPriceWithVAT =          0d;
        double total =                      0d;
        double totalWithVAT =               0d;
        double totalDiscount =              0d;
        double totalVAT =                   0d;

        Discount discount =                 availableProduct.getDiscount();
        HashMap<Integer, Price> prices =    availableProduct.getPrices();

        // СКИДКИ =================================================================
        if (discount != null) {
            finalPrice =                    priceCalculator.calculateDiscountedPrice(discount, currentPrice);
        } else if(prices != null && prices.size() > 0) {
            finalPrice =                    priceCalculator.calculateQuantityPrice(prices, quantity);
        } else {
            finalPrice =                    currentPrice;
        }

        finalPriceWithVAT =                 priceCalculator.getPriceWithVat(finalPrice, VAT);
        discountAmount =                    currentPrice - finalPrice;

        // TOTAL ==================================================================

        total =                             finalPrice * quantity;
        totalWithVAT =                      priceCalculator.getPriceWithVat(total, VAT);
        totalDiscount =                     discountAmount * quantity;
        totalVAT =                          priceCalculator.getDouble(totalWithVAT - total);

        // ========================================================================

        inCartProduct.setId(id);
        inCartProduct.setPnt(pnt);
        inCartProduct.setFullName(fullName);
        inCartProduct.setShortName(shortName);
        inCartProduct.setLink(link);
        inCartProduct.setVAT(VAT);
        inCartProduct.setQuantity(quantity);
        inCartProduct.setCurrentPrice(currentPrice);
        inCartProduct.setCurrentPriceWithVAT(currentPriceWithVAT);
        inCartProduct.setDiscountAmount(discountAmount);
        inCartProduct.setFinalPrice(finalPrice);
        inCartProduct.setFinalPriceWithVAT(finalPriceWithVAT);
        inCartProduct.setTotal(total);
        inCartProduct.setTotalWithVAT(totalWithVAT);
        inCartProduct.setTotalDiscount(totalDiscount);
        inCartProduct.setTotalVAT(totalVAT);
    }

    private CartProduct createCartProduct (AvailableProduct availableProduct, AddProductRequest addProductRequest)
            throws NotEnoughQuantityAvailableException {

        /*if(availableProduct.getQuantityAvailable() < 1 ||
                availableProduct.getQuantityAvailable() < addProductRequest.getQuantity()) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }*/

        int id =                            availableProduct.getId();
        int pnt =                           availableProduct.getPnt();
        String fullName =                   availableProduct.getFullName();
        String shortName =                  availableProduct.getShortName();
        String link =                       availableProduct.getLink();
        int VAT =                           availableProduct.getVAT();
        int quantity =                      addProductRequest.getQuantity();
        double currentPrice =               availableProduct.getPrices().get(1).getBasePrice();
        double currentPriceWithVAT =        priceCalculator.getPriceWithVat(currentPrice, VAT);
        double discountAmount =             0d;
        double finalPrice =                 0d;
        double finalPriceWithVAT =          0d;
        double total =                      0d;
        double totalWithVAT =               0d;
        double totalDiscount =              0d;
        double totalVAT =                   0d;

        Discount discount =                 availableProduct.getDiscount();
        HashMap<Integer, Price> prices =    availableProduct.getPrices();

        // СКИДКИ =================================================================
        if (discount != null) {
            finalPrice =                    priceCalculator.calculateDiscountedPrice(discount, currentPrice);
        } else if(prices != null && prices.size() > 0) {
            finalPrice =                    priceCalculator.calculateQuantityPrice(prices, quantity);
        } else {
            finalPrice =                    currentPrice;
        }

        finalPriceWithVAT =                 priceCalculator.getPriceWithVat(finalPrice, VAT);
        discountAmount =                    currentPrice - finalPrice;

        // TOTAL ==================================================================

        total =                             finalPrice * quantity;
        totalWithVAT =                      priceCalculator.getPriceWithVat(total, VAT);
        totalDiscount =                     discountAmount * quantity;
        totalVAT =                          priceCalculator.getDouble(totalWithVAT - total);

        // ========================================================================

        return new CartProduct(
                id,
                pnt,
                fullName,
                shortName,
                link,
                VAT,
                quantity,
                currentPrice,
                currentPriceWithVAT,
                discountAmount,
                finalPrice,
                finalPriceWithVAT,
                total,
                totalWithVAT,
                totalDiscount,
                totalVAT,
                prices
        );
    }

}
