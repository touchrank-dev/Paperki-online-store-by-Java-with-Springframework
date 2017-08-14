package com.kushnir.paperki.sevice.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Mailer {

    private static final Logger LOGGER = LogManager.getLogger(Mailer.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Value("${mail.system.sender}")
    private String SYSTEM_EMAIL_ADDRESS;

    @Value("${mail.developer.recipient}")
    private String DEVELOPER_EMAIL_ADDRESS;

    @Value("${mail.support.recipient}")
    private String SUPPORT_SERVICE_EMAIL_ADDRES;

    @Value("${mail.user.sender}")
    private String USER_SERVICE_EMAIL_ADDRESS;

    @Value("${mail.notification.sender}")
    private String NOTIFICATION_SERVICE_EMAIL_ADDRESS;

    public void fromUserServiceEmail(String toEmail, String text, String theme) {
        sendMail(toEmail, USER_SERVICE_EMAIL_ADDRESS, text, theme);
    }

    public void fromSystemEmail(String toEmail, String text, String theme) {
        sendMail(toEmail, SYSTEM_EMAIL_ADDRESS, text, theme);
    }

    public void fromSupportEmail(String toEmail, String text, String theme) {
        sendMail(toEmail, SUPPORT_SERVICE_EMAIL_ADDRES, text, theme);
    }

    public void toSupportMail(String text, String theme) {
        sendMail(new String[] {SUPPORT_SERVICE_EMAIL_ADDRES, DEVELOPER_EMAIL_ADDRESS}, SYSTEM_EMAIL_ADDRESS, text, theme);
    }

    private void sendMail(String to, String from, String text, String theme) {
        LOGGER.debug("Sending text email from({}) to ({}) >>> ", from, to);
        templateMessage.setTo(to);
        templateMessage.setFrom(from);
        templateMessage.setText(text);
        templateMessage.setSubject(theme);
        try {
            mailSender.send(templateMessage);
            LOGGER.debug(">>> E-MAIL SUCCESSFULLY SENT");
        } catch (Exception e) {
            LOGGER.error(">>> FAIL SENDING EMAIL TO: {} \n{}", to, e.getMessage());
        }

    }

    private void sendMail(String[] to, String from, String text, String theme) {
        LOGGER.debug("Sending text email from({}) to ({}) >>> ", from, to);
        templateMessage.setTo(to);
        templateMessage.setFrom(from);
        templateMessage.setText(text);
        templateMessage.setSubject(theme);
        try {
            mailSender.send(templateMessage);
            LOGGER.debug(">>> E-MAIL SUCCESSFULLY SENT");
        } catch (Exception e) {
            LOGGER.error(">>> FAIL SENDING EMAIL TO: {} \n{}", to, e.getMessage());
        }

    }
}
