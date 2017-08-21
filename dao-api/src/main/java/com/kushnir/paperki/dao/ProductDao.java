package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Product;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;

public interface ProductDao {

    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName) throws DataAccessException;
    Product getProductBuPNT(Integer pnt) throws DataAccessException;
    Product getProductByTName(String TName) throws DataAccessException;
}
