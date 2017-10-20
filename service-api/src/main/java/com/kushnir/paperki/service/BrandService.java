package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Brand;

import java.io.IOException;
import java.util.HashMap;

public interface BrandService {
    HashMap<Integer, Brand> getAll();
    HashMap<Integer, Brand> getFromCSV (StringBuilder sb) throws IOException;
    Brand getBrandByID();
    Brand getBrandByPapID();

    String updateBrands();

    int addBrand(Brand brand);
    int updateBrand(Brand brand);
    int[] addBrands(Object[] brands);
    int[] updateBrands(Object[] brands);
    void deleteAll() throws Exception;
}
