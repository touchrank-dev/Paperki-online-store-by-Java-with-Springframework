package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.BrandDao;
import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

    private static final Logger LOGGER = LogManager.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private Mailer mailer;

    @Override
    public HashMap<Integer, Brand> getAll() {
        LOGGER.debug("getAll()");
        HashMap<Integer, Brand> brands = brandDao.getAll();
        return brands;
    }

    @Override
    public HashMap<Integer, Brand> getFromCSV(StringBuilder sb) throws IOException {
        LOGGER.debug("getFromCSV()");
        HashMap<Integer, Brand> brands = brandDao.getFromCSV(sb);
        return brands;
    }

    @Override
    public Brand getBrandByID() {
        return null;
    }

    @Override
    public Brand getBrandByPapID() {
        return null;
    }

    @Override
    public String updateBrands() {
        StringBuilder sb = new StringBuilder();
        List<Brand> updBrands = new ArrayList<>();

        try {
            HashMap<Integer, Brand> brands = getAll();
            Assert.notNull(brands, "brands = null");

            HashMap<Integer, Brand> CSVBrands = getFromCSV(sb);
            Assert.notNull(CSVBrands, "CSVBrands = null");
            Assert.isTrue(CSVBrands.size() > 0, "CSVBrands <= 0");

            for (Map.Entry<Integer, Brand> entry : CSVBrands.entrySet()) {
                try {
                    Brand csvBrand = entry.getValue();

                    Brand brand = brands.get(csvBrand.getPapId());

                    validateBrand(csvBrand);

                    if (brand != null) {
                        int id = brand.getId();
                        Assert.isTrue(id > 0, "brand id <= 0");
                        csvBrand.setId(id);
                        updBrands.add(csvBrand);
                    } else {
                        int id = addBrand(csvBrand);
                        sb.append("NEW BRAND SUCCESSFULLY ADDED: id: ").append(id).append('\n');
                        LOGGER.debug("NEW BRAND SUCCESSFULLY ADDED: id: {}", id);
                    }
                } catch (Exception e) {
                    sb.append("ERROR :").append(e).append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR: {}, {}", e, e.getMessage());
                }
            }

            if (updBrands.size() > 0) {
                updateBrands(updBrands.toArray());
            }

        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }
        mailer.toSupportMail(sb.toString(), "UPDATE BRANDS REPORT");
        return sb.toString();
    }

    @Override
    public int addBrand(Brand brand) {
        LOGGER.debug("addBrand({})", brand);
        return brandDao.addBrand(brand);
    }

    @Override
    public int updateBrand(Brand brand) {
        return 0;
    }

    @Override
    public int[] addBrands(Object[] brands) {
        return new int[0];
    }

    @Override
    public int[] updateBrands(Object[] brands) {
        LOGGER.debug("updateBrands()");
        return brandDao.updateBrands(brands);
    }

    @Override
    public void deleteAll() throws Exception {
        throw new Exception("updateCategory() IS NOT IMPLEMENTED");
    }

    private void validateBrand(Brand brand) {

    }
}
