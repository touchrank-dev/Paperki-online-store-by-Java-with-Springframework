package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.AvailableProduct;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;

public interface ProductDao {

    HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) throws DataAccessException;
    Product getProductByPNT(Integer pnt) throws DataAccessException;
    Product getProductByTName(String TName) throws DataAccessException;
    AvailableProduct getAvailableProductByPNT(Integer pnt) throws DataAccessException;
}
