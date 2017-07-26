package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping()
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        errorPage.addObject("errorMsg", getErrorMessage(getErrorCode(httpRequest)));
        return errorPage;
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
