package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.product.*;

import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ProductDao {

    HashMap<Integer, ProductSimple> getAll();
    HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) throws DataAccessException;
    Product getProductByPNT(Integer pnt) throws DataAccessException;
    Product getProductByTName(String TName) throws DataAccessException;
    AvailableProduct getAvailableProductByPNT(Integer pnt) throws DataAccessException;
    ArrayList<Attribute> getAttributesByPNT(Integer pnt) throws DataAccessException;

    HashMap<Integer, ExtraType> getAllExtraTypeProducts();

    HashMap<Integer, CSVProduct> getProductsFromCSV(StringBuilder sb) throws IOException;
    HashMap<Integer, StockItem> getStockItemsFromCSV(StringBuilder sb) throws IOException;
    ArrayList<Attribute> getAttributesFromCSV(StringBuilder sb) throws IOException;
    ArrayList<Price> getQuantityPricesFromCSV(StringBuilder sb) throws IOException;

    void unpublishAllProducts();
    int addProduct(CSVProduct product);
    int addProductCatalogRef(CSVProduct product);

    int[] batchUpdateProducts(Object[] categories);
    int[] batchUpdateProductsCatalogRef(Object[] categories);

    void clearStock(Integer id);
    int[] addItemsToStock(Object[] items);

    void deleteAllAttributes();
    int[] batchAddAttributes(Object[] attributes);

    void deleteAllQuantityPrices();
    int[] batchAddQuantityPrices(Object[] prices);
}
