package com.unimuenster.govlearnapi.feedback.service.dto;

import com.unimuenster.govlearnapi.core.config.enums.ReportReason;

public record FeedbackReportDTO (Long id, String report_message, ReportReason reportReason, Long feedbackID, Long userID){}