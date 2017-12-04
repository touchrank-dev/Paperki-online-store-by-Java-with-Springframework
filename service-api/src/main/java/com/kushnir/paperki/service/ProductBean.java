package com.kushnir.paperki.service;

import com.kushnir.paperki.model.product.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductBean {

    HashMap<Integer, ProductSimple> getAll();
    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName, Integer sortType);

    HashMap<Integer, HashMap<Integer, Product>> getAllExtraTypeProducts();

    Product getProductByPNT(Integer pnt);
    Product getProductByTName(String TName);
    AvailableProduct getAvailableProductByPNT(Integer pnt);
    ArrayList<Attribute> getAttributesByPNT(Integer pnt);
    String updateProducts();
    String updateStock();
    String updateProductAttributes();
    String updateProductPrices();

    ArrayList searchProducts(String str);
}
