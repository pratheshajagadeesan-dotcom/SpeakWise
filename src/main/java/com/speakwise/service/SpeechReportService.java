package com.speakwise.service;

import org.springframework.stereotype.Service;
import com.speakwise.entity.PracticeSession;
import com.speakwise.entity.SpeechReport;

import com.speakwise.entity.Question;
import com.speakwise.repository.QuestionRepository;

import com.speakwise.repository.PracticeSessionRepository;
import com.speakwise.repository.SpeechReportRepository;



import org.springframework.stereotype.Service;

@Service
public class SpeechReportService {

    private final PracticeSessionRepository practiceSessionRepository;

    private final SpeechReportRepository speechReportRepository;

    private final AnalysisEngine analysisEngine;

    private final RelevanceChecker relevanceChecker;

    private final QuestionRepository questionRepository;

    private final EmailService emailService;

    public SpeechReportService(
            PracticeSessionRepository practiceSessionRepository,
            SpeechReportRepository speechReportRepository,
            AnalysisEngine analysisEngine,
            RelevanceChecker relevanceChecker,QuestionRepository questionRepository,EmailService emailService) {

        this.practiceSessionRepository = practiceSessionRepository;
        this.speechReportRepository = speechReportRepository;
        this.analysisEngine = analysisEngine;
        this.relevanceChecker = relevanceChecker;
        this.questionRepository = questionRepository;
        this.emailService = emailService;
    }

    public SpeechReport generateReport(Long sessionId) {

        PracticeSession practiceSession =
                practiceSessionRepository.findById(sessionId)
                        .orElseThrow();

        SpeechReport speechReport = new SpeechReport();

        speechReport.setPracticeSession(practiceSession);

        practiceSession.setSpeechReport(speechReport);

        String transcript = practiceSession.getTranscript();

        double relevanceScore =
                relevanceChecker.calculateRelevanceScore(
                        practiceSession.getQuestion().getId(),
                        transcript);

        speechReport.setScore(relevanceScore);

        speechReportRepository.save(speechReport);

        practiceSessionRepository.save(practiceSession);

        return speechReport;
    }

    public SpeechReport getReport(Long sessionId) {

        PracticeSession practiceSession =
                practiceSessionRepository.findById(sessionId)
                        .orElseThrow();

        return practiceSession.getSpeechReport();

    }

}