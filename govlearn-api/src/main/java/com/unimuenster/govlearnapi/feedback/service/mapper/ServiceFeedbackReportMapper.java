package com.unimuenster.govlearnapi.feedback.service.mapper;

import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ServiceFeedbackReportMapper {
    public FeedbackReportDTO map(Feedback_Report feedbackReport){
        return new FeedbackReportDTO(feedbackReport.getId(), feedbackReport.getReport_message(), feedbackReport.getReport_reason(), feedbackReport.getFeedback().getId() ,feedbackReport.getUser().getId());
    }

    public List<FeedbackReportDTO> map(List<Feedback_Report> feedbackReportList){
        return feedbackReportList
                .stream()
                .map(feedbackReport -> map(feedbackReport))
                .collect(Collectors.toList());
    }
}
