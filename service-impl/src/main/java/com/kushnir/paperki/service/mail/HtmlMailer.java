package com.kushnir.paperki.service.mail;

import com.kushnir.paperki.model.callback.Callback;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.user.PasswordRecoveryRequest;

import com.kushnir.paperki.model.user.User;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class HtmlMailer {

    private static final Logger LOGGER = LogManager.getLogger(HtmlMailer.class);

    @Value("${mail.user.sender}")
    private String USER_SERVICE_EMAIL_ADDRESS;

    @Value("${mail.support.recipient}")
    private String SUPPORT_SERVICE_EMAIL_ADDRESS;

    @Value("${mail.manager.recipient}")
    private String MANAGER_EMAIL_ADDRESS;

    @Value("${mail.callback.recipient}")
    private String CALLBACK_SERVICE_EMAIL_ADDRESS;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${webapp.host}")
    String host;

    @Value("${webapp.rootUrl}")
    String rootUrl;

    public void sendOrderConfirmEmail(Order order, String email) throws MessagingException {
        LOGGER.debug("sendOrderConfirmEmail(to: {})", email);
        try {
            Context ctx = new Context();
            ctx.setVariable("order", order);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            String htmlContent = this.templateEngine.process("email/order", ctx);

            message.setSubject("Благодарим за заказ на paperki.by");
            message.setFrom(USER_SERVICE_EMAIL_ADDRESS);
            message.setTo(new String[] {email});
            message.setBcc(new String[] {SUPPORT_SERVICE_EMAIL_ADDRESS, MANAGER_EMAIL_ADDRESS});
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение для: {}", email);
        }
    }

    public void sendPasswordRestoreRequestMessage (PasswordRecoveryRequest passwordRecoveryRequest) {
        LOGGER.debug("sendPasswordRestoreRequestMessage(to: {})", passwordRecoveryRequest.getEmail());
        try {
            Context ctx = new Context();
            ctx.setVariable("request", passwordRecoveryRequest);
            ctx.setVariable("rootUrl", rootUrl);
            ctx.setVariable("link", rootUrl+"/password/"+passwordRecoveryRequest.getToken());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            String htmlContent = this.templateEngine.process("email/change-password-request", ctx);

            message.setSubject("Запрос на смену пароля от аккаунта на paperki.by");
            message.setFrom(USER_SERVICE_EMAIL_ADDRESS);
            message.setTo(new String[] {passwordRecoveryRequest.getEmail()});
            message.setBcc(SUPPORT_SERVICE_EMAIL_ADDRESS);
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение для: {}", passwordRecoveryRequest.getEmail());
        }
    }

    public void sendCallbackAcceptMessage(Callback callback) {
        LOGGER.debug("sendCallbackAcceptMessage(to: {})", CALLBACK_SERVICE_EMAIL_ADDRESS);
        try {
            Context ctx = new Context();
            ctx.setVariable("callback", callback);
            ctx.setVariable("rootUrl", rootUrl);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            String htmlContent = this.templateEngine.process("email/callback_request", ctx);

            message.setSubject("Запрос на обратный звонок paperki.by");
            message.setFrom(USER_SERVICE_EMAIL_ADDRESS);
            message.setTo(new String[] {CALLBACK_SERVICE_EMAIL_ADDRESS});
            message.setBcc(SUPPORT_SERVICE_EMAIL_ADDRESS);
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение для: {}", CALLBACK_SERVICE_EMAIL_ADDRESS);
        }
    }

    public void sendNewUserRegistrationEmail(User newUser) {
        LOGGER.debug("sendNewUserRegistrationEmail(to: {})", MANAGER_EMAIL_ADDRESS);
        try {
            Context ctx = new Context();
            ctx.setVariable("user", newUser);
            ctx.setVariable("rootUrl", rootUrl);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            String htmlContent = this.templateEngine.process("email/user_create", ctx);

            message.setSubject("Регистрация нового пользователя ("+ newUser.getLogin() +") на paperki.by");
            message.setFrom(USER_SERVICE_EMAIL_ADDRESS);
            message.setTo(new String[] {MANAGER_EMAIL_ADDRESS});
            message.setBcc(SUPPORT_SERVICE_EMAIL_ADDRESS);
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение для: {},\nERROR: {}", MANAGER_EMAIL_ADDRESS, ExceptionUtils.getStackTrace(e));
        }
    }

}
