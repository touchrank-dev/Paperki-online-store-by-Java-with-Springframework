package com.kushnir.paperki.service;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.calculation.PriceCalculator;
import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.service.exceptions.BadAttributeValueException;
import com.kushnir.paperki.service.exceptions.NotEnoughQuantityAvailableException;
import com.kushnir.paperki.service.exceptions.ProductUnavailableException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service("cartBean")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CartBeanImpl implements CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    @Autowired
    ImageService imageService;

    @Autowired
    private PriceCalculator priceCalculator;

    @Override
    @Transactional
    public Integer[] addToCart (Cart cart, AddProductRequest addProductRequest, Boolean isUpdate) throws BadAttributeValueException,
            ProductUnavailableException {
        LOGGER.debug("addToCart({}) >>>", addProductRequest);

        if(addProductRequest.getQuantity() < 1d)
            throw new BadAttributeValueException("Запрошенное количество меньше одного");

        int PNT = addProductRequest.getPnt();
        AvailableProduct availableProduct = productBean.getAvailableProductByPNT(PNT);
        if (availableProduct == null) throw new ProductUnavailableException("Запрошенный товар недоступен");

        if(cart != null) {
            HashMap<Integer, CartProduct> items = cart.getItems();
            CartProduct inCartProduct = null;
            try {
                if (items != null) {
                    inCartProduct = items.get(PNT);
                    if (inCartProduct != null) {
                        calculateCartProduct(inCartProduct, addProductRequest, availableProduct, isUpdate);
                    } else {
                        inCartProduct = new CartProduct();
                        calculateCartProduct(inCartProduct, addProductRequest, availableProduct, isUpdate);
                        items.put(PNT, inCartProduct);
                    }
                } else {
                    items = new LinkedHashMap();
                    inCartProduct = new CartProduct();
                    calculateCartProduct(inCartProduct, addProductRequest, availableProduct, isUpdate);
                    items.put(PNT, inCartProduct);
                    cart.setItems(items);
                }
            } catch (NotEnoughQuantityAvailableException e) {
                return new Integer[]{availableProduct.getQuantityAvailable(), inCartProduct.getQuantity()};
            }
        }
        calculate(cart);
        return null;
    }

    @Override
    @Transactional
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
        // Coupon coupon =                         cart.getCoupon();
        // Present present =                       cart.getPresent();
        // Payment payment =                       cart.getPayment();
        // Shipment shipment =                     cart.getShipment();
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
        cart.setFinalTotalWithVAT(finalTotalWithVAT);
        cart.setTotalVAT(totalVAT);
    }


    private void calculateCartProduct(CartProduct inCartProduct,
                                      AddProductRequest addProductRequest,
                                      AvailableProduct availableProduct, Boolean isUpdate) throws NotEnoughQuantityAvailableException {

        int newQuantity;
        int requestedQuantity =             addProductRequest.getQuantity();

        if (isUpdate) {
            newQuantity =                   requestedQuantity;
        } else {
            int quantityOld =               inCartProduct.getQuantity();
            newQuantity =                   quantityOld + requestedQuantity;
        }

        int availableQuantity =             availableProduct.getQuantityAvailable();
        int availableDay =                  availableProduct.getAvailableDay();

        if((availableQuantity < 1 || newQuantity > availableQuantity) && availableDay < 1) {
            throw new NotEnoughQuantityAvailableException("На складе недостаточно запрашиваемого количества товара");
        }

        int id =                            availableProduct.getId();
        int pnt =                           availableProduct.getPnt();
        String fullName =                   availableProduct.getFullName();
        String shortName =                  availableProduct.getShortName();
        String link =                       availableProduct.getLink();
        int VAT =                           availableProduct.getVAT();
        int quantity =                      newQuantity;
        double currentPrice =               availableProduct.getBasePrice();
        double currentPriceWithVAT =        priceCalculator.getPriceWithVAT(currentPrice, VAT);
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
            finalPrice =                    priceCalculator.calculateQuantityPrice(prices, quantity, currentPrice);
        } else {
            finalPrice =                    currentPrice;
        }
        // ========================================================================

        finalPriceWithVAT =                 priceCalculator.getPriceWithVAT(finalPrice, VAT);
        discountAmount =                    priceCalculator.getDouble(currentPrice - finalPrice);

        // TOTAL ==================================================================

        total =                             priceCalculator.getDouble(finalPrice * quantity);
        totalWithVAT =                      priceCalculator.calculateTotalWithVAT(total, quantity, VAT);
        totalDiscount =                     priceCalculator.getDouble(discountAmount * quantity);
        totalVAT =                          priceCalculator.getVatValue(total, VAT);

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

        inCartProduct.setImageName(imageService.generateImageName(pnt));
    }

}
