package com.kushnir.paperki.service.mail;

import com.kushnir.paperki.model.order.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
    private String SUPPORT_SERVICE_EMAIL_ADDRES;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private TemplateEngine templateEngine;

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
            message.setTo(new String[] {email, SUPPORT_SERVICE_EMAIL_ADDRES});
            message.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение для: {}", email);
        }
    }

}
