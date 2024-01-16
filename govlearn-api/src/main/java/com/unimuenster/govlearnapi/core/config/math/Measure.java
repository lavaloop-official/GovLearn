package com.unimuenster.govlearnapi.core.config.math;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Measure {

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static double euclidianDistance(double[] vectorA, double[] vectorB) {
        double toRet = 0;
        if (vectorA.length != vectorB.length) {
            toRet = -1;
        } else {
            for (int i = 0; i < vectorA.length; ++i) {
                toRet += (vectorA[i] - vectorB[i]) * (vectorA[i] - vectorB[i]);
            }
            toRet = Math.sqrt(toRet);
        }
        return toRet;
    }
}
