package com.unimuenster.govlearnapi.feedback.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unimuenster.govlearnapi.feedback.exception.NotFoundException;
import com.unimuenster.govlearnapi.feedback.controller.mapper.ControllerFeedbackReportMapper;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportWsTo;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackReportRepository;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportDTO;
import com.unimuenster.govlearnapi.feedback.service.mapper.ServiceFeedbackReportMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackReportService {
    private final FeedbackReportRepository feedbackReportRepository;
    private final ServiceFeedbackReportMapper serviceFeedbackReportMapper;
    private final ControllerFeedbackReportMapper controllerFeedbackReportMapper;
    private final EntityManager entityManager;

    @Transactional
    public void createFeedbackReport(FeedbackReportCreationWsTo feedbackReportCreationWsTo, UserEntity currentUser, long feedbackId){
        FeedbackReportCreationDTO feedbackReportCreationDTO = controllerFeedbackReportMapper.map(feedbackReportCreationWsTo);

        try {
            Feedback_Report feedbackReport = new Feedback_Report();
            feedbackReport.setReport_message(feedbackReportCreationDTO.report_message());
            feedbackReport.setReport_reason(feedbackReportCreationDTO.reportReason());
            feedbackReport.setUser(currentUser);
            
            Feedback feedback = entityManager.find(Feedback.class, feedbackId);
            
            feedbackReport.setFeedback(feedback);
            
            feedbackReportRepository.save(feedbackReport);
            
            feedback.addFeedbackReport(feedbackReport);
            
            entityManager.merge(feedback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FeedbackReportWsTo getFeedbackReport(long feedbackreport_id){
        Optional<Feedback_Report> feedback_report = feedbackReportRepository.findFeedbackReportByID(feedbackreport_id);

        if(feedback_report.isEmpty())
            throw new NotFoundException();

        
        FeedbackReportDTO feedbackReportDTO = serviceFeedbackReportMapper.map(feedback_report.get());

        FeedbackReportWsTo feedbackReportWsTo = controllerFeedbackReportMapper.map(feedbackReportDTO);

        return feedbackReportWsTo;
    }

    public List<FeedbackReportWsTo> getAllFeedbackReports(){
        List<Feedback_Report> feedback_report = feedbackReportRepository.findAllFeedbackReport();

        List<FeedbackReportDTO> feedbackReportDTO = serviceFeedbackReportMapper.map(feedback_report);

        List<FeedbackReportWsTo> feedbackReportWsTos = controllerFeedbackReportMapper.map(feedbackReportDTO);

        return feedbackReportWsTos;
    }
}