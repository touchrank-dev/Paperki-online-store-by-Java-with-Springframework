package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Brand;

import java.io.IOException;
import java.util.HashMap;

public interface BrandDao {
    HashMap<Integer, Brand> getAll();
    HashMap<Integer, Brand> getFromCSV (StringBuilder sb) throws IOException;
    Brand getBrandByID();
    Brand getBrandByPapID();
    int addBrand(Brand brand);
    int updateBrand(Brand brand);
    int[] addBrands(Object[] brands);
    int[] updateBrands(Object[] brands);
    void deleteAll();
}
