package com.unimuenster.govlearnapi.feedback.service.dto;

public record FeedbackDTO (Long id, String title, String description, Integer rating, Long coursID, Long userID){}