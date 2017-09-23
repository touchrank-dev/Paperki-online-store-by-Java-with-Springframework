package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.model.product.Attribute;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductDao {

    HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) throws DataAccessException;
    Product getProductByPNT(Integer pnt) throws DataAccessException;
    Product getProductByTName(String TName) throws DataAccessException;
    AvailableProduct getAvailableProductByPNT(Integer pnt) throws DataAccessException;
    ArrayList<Attribute> getAttributesByPNT(Integer pnt) throws DataAccessException;
}
