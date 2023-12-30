package com.unimuenster.govlearnapi.feedback.service.dto;

import com.unimuenster.govlearnapi.core.config.enums.ReportReason;

public record FeedbackReportCreationDTO (String report_message, ReportReason reportReason){}