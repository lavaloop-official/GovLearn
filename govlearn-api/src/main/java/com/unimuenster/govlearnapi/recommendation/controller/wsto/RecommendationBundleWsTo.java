package com.unimuenster.govlearnapi.recommendation.controller.wsto;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendationBundleWsTo {

    List<CourseWsTo> featured = new ArrayList<>();
    List<CategorizedWsTo> categorized = new ArrayList<>();
}
