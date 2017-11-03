package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ImageDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LogManager.getLogger(ImageServiceImpl.class);

    @Autowired
    ImageDao imageDao;

    @Override
    public HashMap<Integer, ArrayList<String>> getAllOldImages() {
        LOGGER.debug("getAllOldImages()");
        return imageDao.getAllOldImages();
    }
}
