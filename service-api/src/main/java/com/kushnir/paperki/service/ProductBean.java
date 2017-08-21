package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Product;

import java.util.HashMap;

public interface ProductBean {

    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName);
    Product getProductBuPNT(Integer pnt);
    Product getProductByTName(String TName);
}
