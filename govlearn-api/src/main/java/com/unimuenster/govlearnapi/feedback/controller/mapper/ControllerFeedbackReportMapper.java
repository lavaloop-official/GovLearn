package com.unimuenster.govlearnapi.feedback.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportWsTo;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControllerFeedbackReportMapper {

    public FeedbackReportCreationDTO map(FeedbackReportCreationWsTo feedbackReportCreationWsTo){
        return new FeedbackReportCreationDTO(feedbackReportCreationWsTo.report_message(), feedbackReportCreationWsTo.report_reason());
    }

    public FeedbackReportWsTo map(FeedbackReportDTO feedbackReportDTO){
        return FeedbackReportWsTo
                .builder()
                .feedbackReportID(feedbackReportDTO.id())
                .report_message(feedbackReportDTO.report_message())
                .report_reason(feedbackReportDTO.reportReason())
                .feedbackID(feedbackReportDTO.feedbackID())
                .userID(feedbackReportDTO.userID())
                .build();
    }
    
    public List<FeedbackReportWsTo> map(List<FeedbackReportDTO> feedbackReportDTOList){
        return feedbackReportDTOList
                .stream()
                .map(feedbackReportDTO -> map(feedbackReportDTO))
                .collect(Collectors.toList());
    }
}