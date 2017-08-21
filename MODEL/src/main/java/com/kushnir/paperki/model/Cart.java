package com.kushnir.paperki.model;

import com.kushnir.paperki.model.Product;

import java.util.HashMap;

public class Cart {

    private Double totalToDiscount;
    private Double totalWithoutVat;
    private Double totalWithVat;

    private HashMap<Integer, Product> items = new HashMap<Integer, Product>();

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
