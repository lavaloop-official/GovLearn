package com.unimuenster.govlearnapi.feedback.service.mapper;

import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceFeedbackMapper {
    public FeedbackDTO map(Feedback feedback){
        return new FeedbackDTO(feedback.getId(), feedback.getTitle(), feedback.getDescription(), feedback.getRating(),feedback.getCourse().getId(), feedback.getUser().getId());
    }
}
