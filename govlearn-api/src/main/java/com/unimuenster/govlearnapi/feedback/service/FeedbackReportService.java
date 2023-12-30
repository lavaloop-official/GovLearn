package com.unimuenster.govlearnapi.feedback.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.hibernate.LockMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.course.service.CourseService;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.feedback.controller.mapper.ControllerFeedbackReportMapper;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackReportWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.entity.Feedback_Report;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackReportRepository;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackRepository;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackReportDTO;
import com.unimuenster.govlearnapi.feedback.service.mapper.ServiceFeedbackMapper;
import com.unimuenster.govlearnapi.feedback.service.mapper.ServiceFeedbackReportMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class FeedbackReportService {
    
    private final FeedbackReportRepository feedbackReportRepository;
    private final ServiceFeedbackReportMapper serviceFeedbackReportMapper;
    private final ControllerFeedbackReportMapper controllerFeedbackReportMapper;
    private final CourseService courseService;
    private final EntityManager entityManager;

    @Transactional
    public void createFeedbackReport(FeedbackReportCreationDTO feedbackReportCreationDTO, UserEntity currentUser, long feedbackId)
    {
        try {
                    Feedback_Report feedbackReport = new Feedback_Report();
                    feedbackReport.setReport_message(feedbackReportCreationDTO.report_message());
                    feedbackReport.setReport_reason(feedbackReportCreationDTO.reportReason());
                    feedbackReport.setUser(currentUser);
                    
                    Feedback feedback = entityManager.find(Feedback.class, feedbackId);
                    
                    feedbackReport.setFeedback(feedback); // Set the Course in the Feedback entity
                    
                    feedbackReportRepository.save(feedbackReport); // Persist the Feedback entity
                    
                    feedback.addFeedbackReport(feedbackReport);
                    
                    entityManager.merge(feedback); // Use merge instead of persist for a detached entity
            // entityManager.persist(feedback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FeedbackReportWsTo getFeedbackReport(long feedbackreport_id){
        Optional<Feedback_Report> feedback_report = feedbackReportRepository.findFeedbackReportByID(feedbackreport_id);

        FeedbackReportDTO feedbackReportDTO = new FeedbackReportDTO(null, null, null, null, null);

        if(feedback_report.isPresent())
            feedbackReportDTO = serviceFeedbackReportMapper.map(feedback_report.get());
        else
            throw new NotFoundException();

        return controllerFeedbackReportMapper.map(feedbackReportDTO);
    }

    public List<FeedbackReportWsTo> getAllFeedbackReports(){
        List<Feedback_Report> feedback_report = feedbackReportRepository.findAllFeedbackReport();

        List<FeedbackReportDTO> feedbackReportDTO = serviceFeedbackReportMapper.map(feedback_report);

        return controllerFeedbackReportMapper.map(feedbackReportDTO);
    }
}