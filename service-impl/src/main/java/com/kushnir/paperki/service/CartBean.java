package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    public void addToCart (Cart cart, AddProductRequest addProductRequest) {
        LOGGER.debug("addToCart() >>>\nPRODUCT TO ADD: {}", addProductRequest);

        int PNT = addProductRequest.getPnt();
        int quantityToAdd = addProductRequest.getQuantity();

        CartProduct productForAddToCart = productBean.getCartProductByPNT(PNT);

        if(cart != null) {
            HashMap<Integer, CartProduct> items = cart.getItems();
            if(items != null) {
                if(productForAddToCart != null) {
                    CartProduct inCartProduct = items.get(PNT);
                    if (inCartProduct != null) {
                        // если в корзине уже есть такой товар, то просто увеличиваем количество
                        // TODO также необходимо все пересчитать
                        inCartProduct.setQuantity(inCartProduct.getQuantity() + quantityToAdd);
                        // items.put(inCartProduct.getPnt(), inCartProduct);
                    } else {
                        // если в корзине еще нет такого товара, то просто добавляем
                        // TODO также необходимо все пересчитать
                        productForAddToCart.setQuantity(quantityToAdd);
                        items.put(productForAddToCart.getPnt(), productForAddToCart);
                    }
                    // cart.setItems(items);
                }
            }
        }
    }

    private void calculate(Cart cart) {

    }
}
