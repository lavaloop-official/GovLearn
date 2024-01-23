package com.unimuenster.govlearnapi.feedback.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.user.service.CustomUserCrudService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControllerFeedbackMapper {

    private final CustomUserCrudService customUserCrudService;

    public FeedbackCreationDTO map(FeedbackCreationWsTo feedbackCreationWsTo){
        return new FeedbackCreationDTO(feedbackCreationWsTo.title(), feedbackCreationWsTo.description(), feedbackCreationWsTo.rating(), feedbackCreationWsTo.courseID());
    }

    public FeedbackWsTo map(FeedbackDTO feedbackDTO){
        return FeedbackWsTo
                .builder()
                .feedbackID(feedbackDTO.id())
                .title(feedbackDTO.title())
                .description(feedbackDTO.description())
                .rating(feedbackDTO.rating())
                .courseID(feedbackDTO.courseID())
                .userID(feedbackDTO.userID())
                .username(customUserCrudService.getUserByID(feedbackDTO.userID()).name())
                .build();
    }
    
    public List<FeedbackWsTo> mapList(List<FeedbackDTO> feedbackDTOList){
        return feedbackDTOList
                .stream()
                .map(feedbackDTO -> map(feedbackDTO))
                .collect(Collectors.toList());
    }
}