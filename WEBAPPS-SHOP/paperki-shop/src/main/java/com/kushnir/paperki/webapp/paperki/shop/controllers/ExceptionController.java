package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.service.mail.Mailer;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    @Autowired
    Mailer mailer;

    @Value("${webapp.host}")
    String hostMame;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Запрашиваемая cтраница не найдена")
    @ExceptionHandler(PageNotFound.class)
    public void pageNotFoundHandler(HttpServletRequest req, HttpSession session,  PageNotFound e) {
        logException(req, session, e);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Ошибка сервера")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest req, HttpSession session, Exception e) {
        logException(req, session, e);
    }

    private void logException(HttpServletRequest req, HttpSession session, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        User user = (session.getAttribute("user") != null)? (User)session.getAttribute("user"):null;
        stringBuilder.append("\nSessionUser: ").append(user)
                     .append("\nRequestURL: ").append(req.getRequestURL())
                     .append("\nRequestedSessionId: ").append(req.getRequestedSessionId())
                     .append("\nMethod: ").append(req.getMethod())
                     .append("\nHeaderNames: ").append(req.getHeaderNames())
                     .append("\nRemoteHost: ").append(req.getRemoteHost())
                     .append("\nRemoteAddr: ").append(req.getRemoteAddr())
                     .append("\nAuthType: ").append(req.getAuthType())
                     .append("\nContentType: ").append(req.getContentType())
                     .append("\nException class: ").append(e)
                     .append("\nError Message: ").append(e.getMessage())
                     .append("\nStack Trace:").append(ExceptionUtils.getStackTrace(e));
        LOGGER.error(stringBuilder);
        mailer.toSupportMail(stringBuilder.toString(), hostMame+", Ошибка сервера!");
    }
}
