package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog.filter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ProductFilterFactory {

    private final String brandKey = "brand";

    private Map<String, String> filterParams;

    private HashMap<String, ProductFilter> availableFilters;
    private HashMap<String, ProductFilter> activeFilters;

    public boolean isFilterActive() {
        return this.filterParams != null && this.filterParams.size() > 0;
    }

    public ProductFilterFactory(Map<String, String> filterParams) {
        this.filterParams = filterParams;
    }

    private void createActiveFilters() {
        for (Map.Entry<String, String> entry: filterParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // CHECK BRAND FILTER
            if (key.startsWith(brandKey)) {
                ProductFilter brandFilter = activeFilters.get(brandKey);
                LinkedList<String> values;
                if (brandFilter == null) {
                    values = new LinkedList<>();
                    values.add(value);
                    brandFilter = new BrandFilter(values);
                    activeFilters.put(brandKey, brandFilter);
                } else brandFilter.addValue(value);

                
            // OTHER FILTERS
            } else {




            }
        }
    }

    public HashMap<String, ProductFilter> getActivatedFilters() {
        if (isFilterActive()) {
            createActiveFilters();
            return this.activeFilters;
        } else return null;
    }

    private ProductFilter activateBrandsFilter() {
        LinkedList<String> values = null;
        for (Map.Entry<String, String> entry: filterParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith("brand")) {
                if (values == null) values = new LinkedList<>();
                values.add(value);
            }
        }
        return (values != null && values.size() > 0) ?  new BrandFilter(values):null;
    }
}
