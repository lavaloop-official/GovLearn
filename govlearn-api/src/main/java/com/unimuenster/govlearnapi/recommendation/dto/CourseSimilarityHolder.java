package com.unimuenster.govlearnapi.recommendation.dto;

import com.unimuenster.govlearnapi.course.entity.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseSimilarityHolder {
    protected Course course;
    protected double similarity;
}
