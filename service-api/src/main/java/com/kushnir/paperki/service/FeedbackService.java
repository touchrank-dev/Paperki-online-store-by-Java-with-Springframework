package com.kushnir.paperki.service;

import com.kushnir.paperki.model.feedback.FeedbackRequest;
import com.kushnir.paperki.service.exceptions.ServiceException;
import org.springframework.http.HttpRequest;

public interface FeedbackService {

    Object addFeedback (FeedbackRequest feedbackRequest, String ip, int userId) throws ServiceException;
}
