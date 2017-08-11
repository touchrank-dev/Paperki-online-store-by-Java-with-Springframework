package com.kushnir.paperki.webapp.paperki.shop.schedules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdaterCSV {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    public void writeCurrentTime() {

        Date now = new Date();
        String nowString = df.format(now);
        LOGGER.debug("Now is: {}", nowString);

    }
}
