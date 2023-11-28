package com.unimuenster.govlearnapi.recommendation.controller.wsto;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategorizedWsTo {
    List<CourseWsTo> items = new ArrayList<>();
    String category;
}
