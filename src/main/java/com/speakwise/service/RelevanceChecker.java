package com.speakwise.service;

import com.speakwise.entity.ExpectedKeyPoint;
import com.speakwise.repository.ExpectedKeyPointRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelevanceChecker {

    private final ExpectedKeyPointRepository expectedKeyPointRepository;

    public RelevanceChecker(ExpectedKeyPointRepository expectedKeyPointRepository) {
        this.expectedKeyPointRepository = expectedKeyPointRepository;
    }

    public double calculateRelevanceScore(Long questionId, String transcript) {

        List<ExpectedKeyPoint> keyPoints =
                expectedKeyPointRepository.findByQuestionId(questionId);

        int mentioned = 0;

        for (ExpectedKeyPoint keyPoint : keyPoints) {

            if (transcript.toLowerCase()
                    .contains(keyPoint.getKeyPoint().toLowerCase())) {

                mentioned++;
            }
        }

        if (keyPoints.isEmpty()) {
            return 0.0;
        }

        double score =
                (mentioned * 100.0) / keyPoints.size();

        return score;
    }


    public List<String> getMissingKeyPoints(Long questionId, String transcript) {

        List<ExpectedKeyPoint> keyPoints =
                expectedKeyPointRepository.findByQuestionId(questionId);

        List<String> missing = new ArrayList<>();

        for (ExpectedKeyPoint keyPoint : keyPoints) {

            if (!transcript.toLowerCase()
                    .contains(keyPoint.getKeyPoint().toLowerCase())) {

                missing.add(keyPoint.getKeyPoint());
            }
        }

        return missing;
    }

    public boolean isOffTopic(double relevanceScore, double threshold) {

        return relevanceScore < threshold;
    }

}
