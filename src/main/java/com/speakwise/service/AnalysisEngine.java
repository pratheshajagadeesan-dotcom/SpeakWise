package com.speakwise.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import com.speakwise.entity.FillerWord;
import com.speakwise.repository.FillerWordRepository;
import java.util.List;

@Service
public class AnalysisEngine {

    private final FillerWordRepository fillerWordRepository;

    public double calculateWPM(int totalWords, double durationInSeconds) {

        double durationInMinutes = durationInSeconds / 60;

        double wpm = totalWords / durationInMinutes;

        return wpm;

    }

    public int detectPauses(JsonNode words) {

        int pauseCount = 0;

        for (int i = 1; i < words.size(); i++) {

            JsonNode previousWord = words.get(i - 1);

            JsonNode currentWord = words.get(i);

            long previousEnd = previousWord.get("end").asLong();

            long currentStart = currentWord.get("start").asLong();

            long gap = currentStart - previousEnd;

            if (gap > 2000) {

                pauseCount++;

            }

        }

        return pauseCount;

    }

    public int countFillerWords(String transcript) {

        List<FillerWord> fillerWords =
                fillerWordRepository.findAll();

        int count = 0;

        String lowerTranscript =
                transcript.toLowerCase();

        for (FillerWord fillerWord : fillerWords) {

            String word =
                    fillerWord.getWord().toLowerCase();

            if (lowerTranscript.contains(word)) {

                count++;

            }

        }

        return count;

    }

    public AnalysisEngine(FillerWordRepository fillerWordRepository) {

        this.fillerWordRepository = fillerWordRepository;

    }
}