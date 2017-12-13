package com.kushnir.paperki.admin.controllers.app;

import org.h2.util.IOUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public String mainPage() {
        return "index";
    }

    @GetMapping("/favicon.ico")
    public void favicon (HttpServletResponse response) throws IOException {
        String filePathToBeServed =
                "/papsource/WEBAPPS-SHOP/paperki-shop/src/main/webapp/WEB-INF/view/resources/img/favicons/favicon.ico";
        InputStream is = new FileInputStream(new File(filePathToBeServed));
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
        is.close();
    }
}
