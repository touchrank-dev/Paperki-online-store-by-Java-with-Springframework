package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.FeedbackDao;
import com.kushnir.paperki.model.feedback.Feedback;
import com.kushnir.paperki.model.feedback.FeedbackErrorResponse;
import com.kushnir.paperki.model.feedback.FeedbackRequest;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger LOGGER = LogManager.getLogger(FeedbackServiceImpl.class);

    @Autowired
    FeedbackDao feedbackDao;

    @Autowired
    Mailer mailer;

    @Override
    public Object addFeedback(FeedbackRequest feedbackRequest, String ip, int userId) throws ServiceException {
        LOGGER.debug("addFeedback ({})", feedbackRequest);
        FeedbackErrorResponse feedbackErrorResponse = new FeedbackErrorResponse();
        try {
            Assert.notNull(feedbackRequest.getUserName(), "Имя не должно быть пустым");
            Assert.hasText(feedbackRequest.getUserName(), "Имя не должно быть пустым");
        } catch (Exception e) {
            feedbackErrorResponse.setUserName(e.getMessage());
        }
        try {
            Assert.notNull(feedbackRequest.getEmail(),"email не должно быть пустым");
            Assert.isTrue(validateEmail(feedbackRequest.getEmail()),
                    "Введен некорректный адрес электронной почты");
        } catch (Exception e) {
            feedbackErrorResponse.setEmail(e.getMessage());
        }
        try {
            Assert.notNull(feedbackRequest.getText(),"Не заполнен текст отзыва");
            Assert.hasText(feedbackRequest.getText(),"Не заполнен текст отзыва");
        } catch (Exception e) {
            feedbackErrorResponse.setText(e.getMessage());
        }
        // end validation
        if (feedbackErrorResponse.isErrors()) {
            LOGGER.error("ОШИБКА В ПАРАМЕТРАХ ОТЗЫВА: {}", feedbackErrorResponse);
            return feedbackErrorResponse;
        } else {
            try {
                Integer id = feedbackDao.addFeedback(
                        new Feedback(
                                userId,
                                feedbackRequest.getUserName(),
                                feedbackRequest.getEmail(),
                                ip,
                                feedbackRequest.getText()
                        )
                );
                LOGGER.debug("ОТЗЫВ УСПЕШНО ЗАРЕГИСТРИРОВАН, id: {}", id);
                mailer.fromUserServiceEmail(feedbackRequest.getEmail(),
                        "Благодарим за оставленный отзыв № "+id,
                        "paperki.by, Благодарим за отзыв");
                mailer.toCustomerService("Пользователь оставил новый отзыв ("+id+")",
                        "Зарегистрирован новый отзыв клиента ("+id+")");
                return id;
            } catch (Exception e) {
                LOGGER.error("ОШИБКА ОТЗЫВА", e.getMessage());
                throw new ServiceException(e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Feedback> getAllFeedback() {
        LOGGER.debug("getAllFeedback()");
        try {
            ArrayList<Feedback> feedbacks = feedbackDao.getAllFeedback();
            return feedbacks;
        } catch (Exception e) {
            LOGGER.error("getAllFeedback() ERROR >>> {},\nERROR MESSAGE: {}", e, e.getMessage());
            return null;
        }
    }

    private boolean validateEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
