package com.unimuenster.govlearnapi.tags.service.dto;

import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.entity.VectorTag;
import lombok.Getter;

import java.util.List;

@Getter
public class TagRatingVector {

    public TagRatingVector(int size){
        initialize(size);
    }

    double[] vector;

    private void initialize(int size){
        vector = new double[size];
    }

    public void setRating(int i, int rating) {
        vector[i] = rating;
    }


    public void computeUserTagVector(List<VectorTag> vectorTags, List<TagDTO> allTags){
        vector = new double[allTags.size()];

        for (int i = 0; i < allTags.size(); i++) {

            TagDTO currentTag = allTags.get(i);

            final int rating
                    = vectorTags.stream()
                    .filter(vectorTag -> vectorTag.getTag().getId().equals(currentTag.id()))
                    .map(vectorTag -> vectorTag.getRating())
                    .findFirst()
                    .orElse(0);

            setRating(i, rating);

        }
    }
}
