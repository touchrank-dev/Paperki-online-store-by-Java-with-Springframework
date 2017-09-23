package com.kushnir.paperki.service;

import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.model.product.Attribute;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductBean {

    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName);
    Product getProductByPNT(Integer pnt);
    Product getProductByTName(String TName);
    AvailableProduct getAvailableproductByPNT(Integer pnt);
    ArrayList<Attribute> getAttributesByPNT(Integer pnt);

}
