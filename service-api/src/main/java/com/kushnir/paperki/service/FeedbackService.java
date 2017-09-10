package com.kushnir.paperki.service;

import com.kushnir.paperki.model.feedback.FeedbackRequest;
import com.kushnir.paperki.service.exceptions.ServiceException;

public interface FeedbackService {

    Object addFeedback (FeedbackRequest feedbackRequest) throws ServiceException;
}
