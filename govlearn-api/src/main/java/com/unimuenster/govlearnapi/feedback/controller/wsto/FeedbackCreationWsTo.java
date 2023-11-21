package com.unimuenster.govlearnapi.feedback.controller.wsto;

public record FeedbackCreationWsTo(String title, String description, Integer rating, Long courseID) {}