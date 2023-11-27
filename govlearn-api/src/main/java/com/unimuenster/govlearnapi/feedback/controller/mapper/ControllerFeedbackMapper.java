package com.unimuenster.govlearnapi.feedback.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.user.controller.wsto.UserWsTo;

import org.springframework.stereotype.Service;

@Service
public class ControllerFeedbackMapper {

    public FeedbackCreationDTO map(FeedbackCreationWsTo feedbackCreationWsTo){
        return new FeedbackCreationDTO(feedbackCreationWsTo.title(), feedbackCreationWsTo.description(), feedbackCreationWsTo.rating(), feedbackCreationWsTo.courseID());
    }

    public FeedbackWsTo map(FeedbackDTO feedbackDTO, String username){
        return FeedbackWsTo
                .builder()
                .feedbackID(feedbackDTO.id())
                .title(feedbackDTO.title())
                .description(feedbackDTO.description())
                .rating(feedbackDTO.rating())
                .courseID(feedbackDTO.coursID())
                .userID(feedbackDTO.coursID())
                .username(username)
                .build();
    }
    public List<FeedbackWsTo> mapList(List<FeedbackDTO> feedbackDTOList, List<UserWsTo> users) {
        // Assuming the sizes of feedbackDTOList and usernames are the same
        return IntStream.range(0, feedbackDTOList.size())
                .mapToObj(i -> map(feedbackDTOList.get(i), users.get(i).name()))
                .collect(Collectors.toList());
    }
}