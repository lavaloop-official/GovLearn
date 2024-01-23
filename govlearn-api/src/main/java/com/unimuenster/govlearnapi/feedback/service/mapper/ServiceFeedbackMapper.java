package com.unimuenster.govlearnapi.feedback.service.mapper;

import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ServiceFeedbackMapper {
    public FeedbackDTO map(Feedback feedback){
        return new FeedbackDTO(feedback.getId(), feedback.getTitle(), feedback.getDescription(), feedback.getRating(),feedback.getCourse().getId(), feedback.getUser().getId());
    }

    public List<FeedbackDTO> mapFeedback(List<Feedback> feedbacks) {
        return feedbacks
                .stream()
                .map(feedback -> map(feedback))
                .collect(Collectors.toList());
    }

    public Feedback mapFeedbackCreationDTOToFeedback(FeedbackCreationDTO feedbackDTO, UserEntity currentUser) {

        Feedback feedback = new Feedback();

        feedback.setRating(feedbackDTO.rating());
        feedback.setDescription(feedbackDTO.description());
        feedback.setTitle(feedbackDTO.title());
        feedback.setUser(currentUser);

        return feedback;
    }
}
