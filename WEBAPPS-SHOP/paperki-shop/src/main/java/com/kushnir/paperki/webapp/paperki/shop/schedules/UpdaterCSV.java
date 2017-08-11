package com.kushnir.paperki.webapp.paperki.shop.schedules;

import com.kushnir.paperki.sevice.CategoryBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdaterCSV {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    @Autowired
    CategoryBean categoryBean;

    public void writeCurrentTime() {
        Date now = new Date();
        String nowString = df.format(now);
        LOGGER.debug("Now is: {}", nowString);
    }

    public void catalogUpdate() throws IOException {
        LOGGER.debug("===== Started Catalog update ==== >>> ");
        categoryBean.getCategoriesFromCSV();
    }
}
