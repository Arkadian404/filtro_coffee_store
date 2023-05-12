package com.data.filtro.service;

import com.data.filtro.model.Feedback;
import com.data.filtro.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedBackByProductId(int id) {
        return feedbackRepository.findAllByProductId(id);
    }

    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
}
