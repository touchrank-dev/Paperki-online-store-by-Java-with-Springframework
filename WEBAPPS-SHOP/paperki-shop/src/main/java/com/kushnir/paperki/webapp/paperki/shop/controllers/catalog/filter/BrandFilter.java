package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog.filter;

import java.util.LinkedList;

class BrandFilter extends BaseProductFilter implements ProductFilter {

    BrandFilter(LinkedList<String> values) {
        super.values = values;
    }

    @Override
    public LinkedList<String> getValues() {
        return values;
    }

    @Override
    public void addValue(String value) {
        values.add(value);
    }
}
