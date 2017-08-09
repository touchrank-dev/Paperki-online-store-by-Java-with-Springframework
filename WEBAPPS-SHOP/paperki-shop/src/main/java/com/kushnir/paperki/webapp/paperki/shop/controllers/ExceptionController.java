package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Запрашиваемая cтраница не найдена")
    @ExceptionHandler(PageNotFound.class)
    public void pageNotFoundHandler(HttpServletRequest req, PageNotFound e) {
        LOGGER.error("Запрос URL: " + req.getRequestURL() + ", Exception class: " + e + "\n Message: "+e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Ошибка сервера")
    @ExceptionHandler(NestedServletException.class)
    public void thymeleafExceptionHandler(HttpServletRequest req, NestedServletException e) {
        LOGGER.error("Запрос URL: " + req.getRequestURL() + ", Exception class: " + e + "\n Message: "+e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Ошибка сервера")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest req, Exception e) {
        LOGGER.error("Запрос URL: " + req.getRequestURL() + ", Exception class: " + e + "\nMessage: "+e.getMessage());
    }
}
