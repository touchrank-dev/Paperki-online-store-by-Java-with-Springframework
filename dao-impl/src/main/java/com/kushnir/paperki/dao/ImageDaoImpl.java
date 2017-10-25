package com.kushnir.paperki.dao;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ImageDaoImpl implements ImageDao {

    private static final Logger LOGGER = LogManager.getLogger(ImageDaoImpl.class);

    @Override
    public HashMap<Integer, ArrayList<String>> getAllOldImages() {
        LOGGER.debug("getAllOldImages()");

        HashMap<Integer, ArrayList<String>> oldImages = new HashMap<>();

        try {
            File[] files = new File("/home/paperki.by/www/images/catalog").listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String fileName = file.getName();

                        String name = fileName.replaceAll(".jpg", "");
                        String strPnt = name.replaceFirst("0", "");

                        if (strPnt.endsWith("-1")
                                || strPnt.endsWith("-2")
                                || strPnt.endsWith("-3")
                                || strPnt.endsWith("-4")
                                || strPnt.endsWith("-5")) {

                            strPnt = strPnt.substring(0, strPnt.length() - 2);
                        }

                        Integer pnt = Integer.parseInt(strPnt);

                        ArrayList<String> images = oldImages.get(pnt);
                        if (images == null) {
                            images = new ArrayList<String>();
                            images.add(fileName);
                            oldImages.put(pnt, images);
                        } else {
                            images.add(fileName);
                            Collections.sort(images, Collections.reverseOrder());
                        }

                    } catch (Exception e) { }
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR getAllOldImages()>>> {}, MESSAGE: {}", e, ExceptionUtils.getStackTrace(e));
            return null;
        }
        return oldImages;
    }
}
