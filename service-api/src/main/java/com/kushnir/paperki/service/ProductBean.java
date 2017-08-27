package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AvailableProduct;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;

import java.util.HashMap;

public interface ProductBean {

    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName);
    Product getProductByPNT(Integer pnt);
    Product getProductByTName(String TName);
    CartProduct getCartProductByPNT(Integer pnt);
    AvailableProduct getAvailableproductByPNT(Integer pnt);
}
