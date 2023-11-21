package com.unimuenster.govlearnapi.feedback.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackWsTo {
    Long feedbackID;
    String title;
    String description;
    Integer rating;
    Long courseID;
    Long userID;
}