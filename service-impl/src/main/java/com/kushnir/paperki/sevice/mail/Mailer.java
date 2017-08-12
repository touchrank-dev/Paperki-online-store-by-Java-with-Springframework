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

    private String[] supportRecipients = new String[] {
            SUPPORT_SERVICE_EMAIL_ADDRES,
            DEVELOPER_EMAIL_ADDRESS
    };

    public void fromUserServiceEmail(String toEmail, String text) {
        sendMail(toEmail, USER_SERVICE_EMAIL_ADDRESS, text);
    }

    public void fromSystemEmail(String toEmail, String text) {
        sendMail(toEmail, SYSTEM_EMAIL_ADDRESS, text);
    }

    public void fromSupportEmail(String toEmail, String text) {
        sendMail(toEmail, SUPPORT_SERVICE_EMAIL_ADDRES, text);
    }

    public void toSupportMail(String text) {
        sendMail(supportRecipients, SYSTEM_EMAIL_ADDRESS, text);
    }

    private void sendMail(String to, String from, String text) {
        LOGGER.debug("Sending text email from({}) to ({}) >>> ", from, to);
        templateMessage.setTo(to);
        templateMessage.setFrom(from);
        templateMessage.setText(text);
        try {
            mailSender.send(templateMessage);
        } catch (Exception e) {
            LOGGER.error(">>> FAIL SENDING EMAIL TO: {} \n{}", to, e.getMessage());
        }
        LOGGER.debug(">>> E-MAIL SUCCESSFULLY SENT");

    }

    private void sendMail(String[] to, String from, String text) {
        LOGGER.debug("Sending text email from({}) to ({}) >>> ", from, to);
        templateMessage.setTo(to);
        templateMessage.setFrom(from);
        templateMessage.setText(text);
        try {
            mailSender.send(templateMessage);
        } catch (Exception e) {
            LOGGER.error(">>> FAIL SENDING EMAIL TO: {} \n{}", to, e.getMessage());
        }
        LOGGER.debug(">>> E-MAIL SUCCESSFULLY SENT");

    }
}
