package com.speakwise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speakwise.entity.FillerWord;
import com.speakwise.repository.FillerWordRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AnalysisEngineTest {

    @Test
    void testCalculateWPM() {

        FillerWordRepository repository =
                mock(FillerWordRepository.class);

        AnalysisEngine engine =
                new AnalysisEngine(repository);

        FillerWord f1 = new FillerWord("um");
        FillerWord f2 = new FillerWord("like");
        FillerWord f3 = new FillerWord("actually");

        when(repository.findAll())
                .thenReturn(List.of(f1, f2, f3));

        int count =
                engine.countFillerWords(
                        "Um this is actually like a demo");

        assertEquals(3, count);

        double result =
                engine.calculateWPM(120, 60);

        assertEquals(120, result);

    }

    @Test
    void testDetectPauses() throws Exception {

        FillerWordRepository repository =
                mock(FillerWordRepository.class);

        AnalysisEngine engine =
                new AnalysisEngine(repository);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode words = mapper.readTree("""
        [
          {"text":"Hello","start":0,"end":500},
          {"text":"World","start":3000,"end":3500}
        ]
        """);

        int pauses = engine.detectPauses(words);

        assertEquals(1, pauses);

    }

    @Test
    void testCountFillerWords() {

        FillerWordRepository repository =
                mock(FillerWordRepository.class);

        AnalysisEngine engine =
                new AnalysisEngine(repository);

    }
}

