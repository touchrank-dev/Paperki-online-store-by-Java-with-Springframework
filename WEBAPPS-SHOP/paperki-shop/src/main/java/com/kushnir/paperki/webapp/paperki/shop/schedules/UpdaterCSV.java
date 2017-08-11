package com.kushnir.paperki.webapp.paperki.shop.schedules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UpdaterCSV {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    // 2 second.
    @Scheduled(fixedDelay = 2 * 1000)
    public void writeCurrentTime() {

        Date now = new Date();
        String nowString = df.format(now);

        LOGGER.debug("Now is: {}", nowString);
        System.out.println("Now is: "+ nowString);

    }
}
