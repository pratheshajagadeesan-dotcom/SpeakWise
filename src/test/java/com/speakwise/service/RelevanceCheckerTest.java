package com.speakwise.service;

import com.speakwise.entity.ExpectedKeyPoint;
import com.speakwise.repository.ExpectedKeyPointRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RelevanceCheckerTest {

    @Test
    void testCalculateRelevanceScore() {

        ExpectedKeyPointRepository repository =
                mock(ExpectedKeyPointRepository.class);

        RelevanceChecker checker =
                new RelevanceChecker(repository);

        ExpectedKeyPoint p1 = new ExpectedKeyPoint("java");
        ExpectedKeyPoint p2 = new ExpectedKeyPoint("spring");
        ExpectedKeyPoint p3 = new ExpectedKeyPoint("mysql");
        ExpectedKeyPoint p4 = new ExpectedKeyPoint("react");

        when(repository.findByQuestionId(1L))
                .thenReturn(List.of(p1, p2, p3, p4));

        double score =
                checker.calculateRelevanceScore(
                        1L,
                        "Java Spring");

        assertEquals(50.0, score);
    }

    @Test
    void testMissingKeyPoints() {

        ExpectedKeyPointRepository repository =
                mock(ExpectedKeyPointRepository.class);

        RelevanceChecker checker =
                new RelevanceChecker(repository);

        ExpectedKeyPoint p1 = new ExpectedKeyPoint("java");
        ExpectedKeyPoint p2 = new ExpectedKeyPoint("spring");
        ExpectedKeyPoint p3 = new ExpectedKeyPoint("mysql");
        ExpectedKeyPoint p4 = new ExpectedKeyPoint("react");

        when(repository.findByQuestionId(1L))
                .thenReturn(List.of(p1, p2, p3, p4));

        List<String> missing =
                checker.getMissingKeyPoints(
                        1L,
                        "Java Spring");

        assertEquals(2, missing.size());

        assertTrue(missing.contains("mysql"));
        assertTrue(missing.contains("react"));
    }

    @Test
    void testIsOffTopic() {

        ExpectedKeyPointRepository repository =
                mock(ExpectedKeyPointRepository.class);

        RelevanceChecker checker =
                new RelevanceChecker(repository);

        assertTrue(checker.isOffTopic(40, 60));

        assertFalse(checker.isOffTopic(80, 60));
    }
}
