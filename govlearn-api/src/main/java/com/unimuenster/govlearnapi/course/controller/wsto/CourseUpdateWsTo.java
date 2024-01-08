package com.unimuenster.govlearnapi.course.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;

import java.util.Date;

public record CourseUpdateWsTo(Long id, String name, String image, String description, String provider, String instructor, Boolean certificate, Skilllevel skilllevel, String durationInHours, Format format, Date startDate, Boolean costFree, Boolean domainSpecific, String link){
}
