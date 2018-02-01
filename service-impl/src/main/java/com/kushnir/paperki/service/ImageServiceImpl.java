package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ImageDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LogManager.getLogger(ImageServiceImpl.class);

    @Autowired
    private ImageDao imageDao;

    @Value("${product.image.prefix}")
    private String imgPref;

    @Value("${brand.image.prefix}")
    private String brandImgPrefPref;

    @Override
    public HashMap<Integer, ArrayList<String>> getAllOldImages() {
        LOGGER.debug("getAllOldImages()");
        return imageDao.getAllOldImages();
    }

    @Override
    public String generateImageName(Integer pnt) {
        String pntStr = String.valueOf(pnt);
        switch(pntStr.length()) {
            case 1: return "0000000"+pntStr+imgPref;
            case 2: return "000000"+pntStr+imgPref;
            case 3: return "00000"+pntStr+imgPref;
            case 4: return "0000"+pntStr+imgPref;
            case 5: return "000"+pntStr+imgPref;
            case 6: return "00"+pntStr+imgPref;
            case 7: return "0"+pntStr+imgPref;
            default: return pntStr+imgPref;
        }
    }

    @Override
    public String generateBrandImageName(Integer brandId) {
        String pntStr = String.valueOf(brandId);
        switch(pntStr.length()) {
            case 1: return "00000"+pntStr+brandImgPrefPref;
            case 2: return "0000"+pntStr+brandImgPrefPref;
            case 3: return "000"+pntStr+brandImgPrefPref;
            case 4: return "00"+pntStr+brandImgPrefPref;
            case 5: return "0"+pntStr+brandImgPrefPref;
            default: return pntStr+brandImgPrefPref;
        }
    }
}
