package com.unimuenster.govlearnapi.feedback.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.feedback.exception.NotFoundException;
import com.unimuenster.govlearnapi.feedback.controller.mapper.ControllerFeedbackMapper;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackCreationWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackWsTo;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackRepository;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.feedback.service.mapper.ServiceFeedbackMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import java.lang.NullPointerException;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final ServiceFeedbackMapper serviceFeedbackMapper;
    private final ControllerFeedbackMapper controllerFeedbackMapper;
    private final EntityManager entityManager;

    @Transactional
    public void createFeedback(FeedbackCreationWsTo feedbackCreationWsTo, UserEntity currentUser)
    {
        FeedbackCreationDTO feedbackCreationDTO = controllerFeedbackMapper.map(feedbackCreationWsTo);

        Feedback feedback = serviceFeedbackMapper.mapFeedbackCreationDTOToFeedback(feedbackCreationDTO, currentUser);

        Course course = entityManager.find(Course.class, feedbackCreationDTO.courseID());

        feedback.setCourse(course);

        feedbackRepository.save(feedback);
    }

    public List<FeedbackWsTo> getFeedbackByCourseAndUser(Long courseId, Long userID){

        List<Feedback> feedbacks = feedbackRepository.findFeedbackByCourseAndUser(courseId, userID);

        List<FeedbackDTO> feedbackDTOs = serviceFeedbackMapper.mapFeedback(feedbacks);

        List<FeedbackWsTo> feedbackWsTos = controllerFeedbackMapper.mapList(feedbackDTOs);

        return feedbackWsTos;
    }

    public List<FeedbackWsTo> getFeedbackByCourseWithLimitAndOffset(Long courseId, Optional<Long> limit, Optional<Long> offset){

        Long limitQuery = 100L;

        if (limit.isPresent())
        {
            limitQuery = limit.get();
        }

        Long offsetQuery = 0L;

        if (offset.isPresent())
        {
            offsetQuery = offset.get();
        }

        List<Feedback> feedbacks = feedbackRepository.findAllFeedbackByCourseIdWithLimitAndOffset(courseId, limitQuery, offsetQuery);

        List<FeedbackDTO> feedbackDTOs = serviceFeedbackMapper.mapFeedback(feedbacks);

        List<FeedbackWsTo> feedbackWsTos = controllerFeedbackMapper.mapList(feedbackDTOs);

        return feedbackWsTos;
    }


    public Feedback getFeedbackEntityById(Long feedbackID){
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackID);

        if ( optionalFeedback.isEmpty() ) {
            throw new NotFoundException();
        }

        Feedback feedback = Feedback
                .builder()
                .id(optionalFeedback.get().getId())
                .title(optionalFeedback.get().getTitle())
                .description(optionalFeedback.get().getDescription())
                .course(optionalFeedback.get().getCourse())
                .user(optionalFeedback.get().getUser())
                .createdAt(optionalFeedback.get().getCreatedAt())
                .build();

        return feedback;
    }

    public FeedbackWsTo getFeedbackWsToById(Long feedbackID){
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackID);

        if ( feedback.isEmpty() ) {
            throw new NotFoundException();
        }

            FeedbackDTO map = new FeedbackDTO(
                feedback.get().getId(), 
                feedback.get().getTitle(), 
                feedback.get().getDescription(),
                feedback.get().getRating(),
                feedback.get().getCourse().getId(),
                feedback.get().getUser().getId()
            );

        FeedbackWsTo feedbackWsTo = controllerFeedbackMapper.map(map);

        return feedbackWsTo;
    }

    @Transactional
    public void deleteFeedbackFromCourse(Long feedbackID){
        feedbackRepository.deleteFeedbackFromCourse(feedbackID);
    }

    @Transactional
    public void updateFeedbackFromCourse(FeedbackUpdateWsTo feedback){
        feedbackRepository.updateFeedbackFromCourse(feedback.feedbackID(), feedback.title(), feedback.description(), feedback.rating());
    }

    private float round(float value, int decimalPoints) {
        float d = (float)Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
     }

    public Float getAverageFeedbackByCourseID(Long courseID) throws NotFoundException{
        try {
            return round(feedbackRepository.findAverageFeedbackByCourseId(courseID), 1);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Long getAmountFeedbackByCourseID(Long courseID) throws NotFoundException{
        try {
            return feedbackRepository.findAmountFeedbackByCourseId(courseID);
        } catch (NullPointerException e) {
            return null;
        }
    }
}