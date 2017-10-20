package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.feedback.Feedback;

import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

public interface FeedbackDao {
    Integer addFeedback(Feedback feedback) throws DataAccessException;
    ArrayList<Feedback> getAllFeedback();
}
