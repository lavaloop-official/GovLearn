package com.unimuenster.govlearnapi.feedback.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.exception.NotFoundException;
import com.unimuenster.govlearnapi.feedback.controller.wsto.FeedbackUpdateWsTo;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.feedback.repository.FeedbackRepository;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackCreationDTO;
import com.unimuenster.govlearnapi.feedback.service.dto.FeedbackDTO;
import com.unimuenster.govlearnapi.feedback.service.mapper.ServiceFeedbackMapper;
import com.unimuenster.govlearnapi.user.entity.UserEntity;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final ServiceFeedbackMapper serviceFeedbackMapper;
    private final EntityManager entityManager;

    @Transactional
    public void createFeedback(FeedbackCreationDTO feedbackCreationDTO, UserEntity currentUser)
    {
        try {
            Feedback feedback = new Feedback();
            feedback.setRating(feedbackCreationDTO.rating());
            feedback.setDescription(feedbackCreationDTO.description());
            feedback.setTitle(feedbackCreationDTO.title());
            feedback.setUser(currentUser);

            Course course = entityManager.find(Course.class, feedbackCreationDTO.courseID());

            feedback.setCourse(course); // Set the Course in the Feedback entity

            feedbackRepository.save(feedback); // Persist the Feedback entity

            course.addFeedback(feedback);

            entityManager.merge(course); // Use merge instead of persist for a detached entity

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FeedbackDTO> getFeedbackByCourseAndUser(Long courseId, Long userID){

        List<Feedback> feedbacks = feedbackRepository.findFeedbackByCourseAndUser(courseId, userID);

        return mapFeedback(feedbacks);
    }

    private List<FeedbackDTO> mapFeedback(List<Feedback> feedbacks) {
        return feedbacks
                .stream()
                .map(feedback -> serviceFeedbackMapper.map(feedback))
                .collect(Collectors.toList());
    }

    public List<FeedbackDTO> getFeedbackByCourseWithLimitAndOffset(Long courseId, Optional<Long> limit, Optional<Long> offset){

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

        return mapFeedback(feedbacks);
    }


    public Feedback getFeedbackEntityById(Long feedbackID){
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackID);

        if ( feedback.isEmpty() ) {
            throw new NotFoundException();
        }

        Feedback map = Feedback
                .builder()
                .id(feedback.get().getId())
                .title(feedback.get().getTitle())
                .description(feedback.get().getDescription())
                .course(feedback.get().getCourse())
                .user(feedback.get().getUser())
                .createdAt(feedback.get().getCreatedAt())
                .build();

        return map;
    }

    public FeedbackDTO getFeedbackDTOById(Long feedbackID){
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

        return map;
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