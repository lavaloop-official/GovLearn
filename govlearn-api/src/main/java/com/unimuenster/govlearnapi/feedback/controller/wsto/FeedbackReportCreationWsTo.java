package com.unimuenster.govlearnapi.feedback.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.ReportReason;

public record FeedbackReportCreationWsTo(String report_message, ReportReason report_reason) {}