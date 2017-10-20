package com.kushnir.paperki.service;

import com.kushnir.paperki.model.feedback.Feedback;
import com.kushnir.paperki.model.feedback.FeedbackRequest;
import com.kushnir.paperki.service.exceptions.ServiceException;
import org.springframework.http.HttpRequest;

import java.util.ArrayList;

public interface FeedbackService {

    Object addFeedback (FeedbackRequest feedbackRequest, String ip, int userId) throws ServiceException;
    ArrayList<Feedback> getAllFeedback();
}
