package com.kushnir.paperki.webapp.paperki.shop.session;

import com.kushnir.paperki.model.Product;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.Serializable;
import java.util.HashMap;

public class Cart {

    private HashMap<Integer, Product> items;

    public HashMap<Integer, Product> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, Product> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
