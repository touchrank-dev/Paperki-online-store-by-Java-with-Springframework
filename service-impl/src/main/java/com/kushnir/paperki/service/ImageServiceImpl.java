package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ImageDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LogManager.getLogger(ImageServiceImpl.class);

    @Autowired
    ImageDao imageDao;

    @Value("${product.image.prefix}")
    String imgPref;

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
}
