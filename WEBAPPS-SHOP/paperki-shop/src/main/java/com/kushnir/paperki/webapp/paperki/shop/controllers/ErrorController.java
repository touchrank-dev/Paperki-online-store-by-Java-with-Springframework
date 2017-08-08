package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
// @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Ошибка сервера")
@RequestMapping("/error")
public class ErrorController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @GetMapping()
    public void errorPage() {
        LOGGER.debug("error() >>>");
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    private String getErrorMessage(int httpErrorCode) {
        switch (httpErrorCode) {
            case 400: {
                return "Http Error Code: 400. Bad Request";
            }
            case 401: {
                return "Http Error Code: 401. Unauthorized";
            }
            case 404: {
                return "Http Error Code: 404. Resource not found";
            }
            case 500: {
                return "Http Error Code: 500. Internal Server Error";
            }
            default: return "Unknown "+httpErrorCode;
        }
    }
}

