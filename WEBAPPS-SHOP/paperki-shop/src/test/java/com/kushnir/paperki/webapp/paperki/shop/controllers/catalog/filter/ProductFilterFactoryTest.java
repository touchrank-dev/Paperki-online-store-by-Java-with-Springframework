package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog.filter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ProductFilterFactoryTest {

    @BeforeClass
    public static void beforeTest() {

    }

    @Test
    public void isFilterActiveTest() {
        ProductFilterFactory productFilterFactory = new ProductFilterFactory(null);
        Assert.assertFalse(productFilterFactory.isFilterActive());
        Map<String, String> filterParams = new HashMap<>();
        productFilterFactory = new ProductFilterFactory(filterParams);
        Assert.assertFalse(productFilterFactory.isFilterActive());
        filterParams.put("brand0", "Xerox");
        Assert.assertTrue(productFilterFactory.isFilterActive());
    }
}
