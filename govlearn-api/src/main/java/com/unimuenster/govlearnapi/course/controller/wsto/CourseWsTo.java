package com.unimuenster.govlearnapi.course.controller.wsto;

import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CourseWsTo {

    Long id;
    String name;
    String image;
    String description;
    Date createdAt;
    String provider;
    String instructor;
    Boolean certificate;
    Skilllevel skilllevel;
    String durationInHours;
    Format format;
    Date startDate;
    Boolean costFree;
    Boolean domainSpecific;
    String link;

    Float ratingAverage;
    Long ratingAmount;
}
