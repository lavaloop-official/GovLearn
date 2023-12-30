package com.unimuenster.govlearnapi.feedback.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.ReportReason;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackReportWsTo {
    Long feedbackReportID;
    String report_message;
    ReportReason report_reason;
    Long feedbackID;
    Long userID;
}