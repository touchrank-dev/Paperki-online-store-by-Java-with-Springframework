package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog.filter;


import java.util.LinkedList;

public interface ProductFilter {

    LinkedList<String> getValues();
    void addValue(String value);

}
