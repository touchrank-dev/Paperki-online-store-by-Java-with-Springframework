package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.mail.Mailer;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    @Autowired
    Mailer mailer;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Запрашиваемая cтраница не найдена")
    @ExceptionHandler(PageNotFound.class)
    public void pageNotFoundHandler(HttpServletRequest req, PageNotFound e) {
        LOGGER.error("Запрос URL: " + req.getRequestURL() + ", Exception class: " + e + "\n Message: "+e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Ошибка сервера")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest req, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Запрос URL: ")
                .append(req.getRequestURL())
                .append(", Exception class: ")
                .append(e)
                .append("\nMessage: ")
                .append(e.getMessage());
        LOGGER.error(stringBuilder);
        mailer.toSupportMail(stringBuilder.toString());
    }
}
