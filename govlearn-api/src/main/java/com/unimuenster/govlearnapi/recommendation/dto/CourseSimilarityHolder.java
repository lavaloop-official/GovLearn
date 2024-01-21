package com.unimuenster.govlearnapi.recommendation.dto;

import com.unimuenster.govlearnapi.course.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class CourseSimilarityHolder {
    protected Course course;
    protected double similarity;

    private CourseSimilarityHolder(){}

    public static Optional<CourseSimilarityHolder> build(double similarity, Course course) {
        if ( ! Double.isNaN(similarity)){
            CourseSimilarityHolder courseSimilarityHolder = new CourseSimilarityHolder();
            courseSimilarityHolder.setCourse(course);
            courseSimilarityHolder.setSimilarity(similarity);

            return Optional.of(courseSimilarityHolder);
        }

        return Optional.empty();
    }
}
