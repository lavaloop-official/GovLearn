package com.unimuenster.govlearnapi.feedback.service.dto;

public record FeedbackCreationDTO (String title, String description, Integer rating, Long courseID){}