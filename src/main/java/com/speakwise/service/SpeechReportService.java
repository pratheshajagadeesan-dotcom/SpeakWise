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

        speechReport.setWpm(120.0);

        speechReport.setPauseCount(5);

        speechReport.setTipMessage("Try to speak more confidently and reduce filler words.");

        speechReport.setMissingKeyPoints("No missing key points.");

        speechReportRepository.save(speechReport);

        System.out.println("Saved WPM = " + speechReport.getWpm());
        System.out.println("Saved Pause = " + speechReport.getPauseCount());
        System.out.println("Saved Tip = " + speechReport.getTipMessage());

        practiceSessionRepository.save(practiceSession);

        return speechReportRepository.findById(speechReport.getId()).orElseThrow();
    }

    public SpeechReport getReport(Long sessionId) {

        PracticeSession practiceSession =
                practiceSessionRepository.findById(sessionId)
                        .orElseThrow();

        return practiceSession.getSpeechReport();

    }

}