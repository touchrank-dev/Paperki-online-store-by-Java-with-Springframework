package com.kushnir.paperki.model.product;

import java.util.HashMap;

public enum ExtraType {

    HIT(1),
    SUPER_PRICE(2),
    NEW(3),
    SPECIAL(4);

    private String name;
    private Integer id;
    private HashMap<Integer, Product> products;

    ExtraType() {
    }

    ExtraType(Integer id) {
        this.id = id;
    }

    ExtraType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    ExtraType(String name, Integer id, HashMap<Integer, Product> products) {
        this.name = name;
        this.id = id;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Integer, Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ExtraType{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", products=" + products +
                '}';
    }
}
