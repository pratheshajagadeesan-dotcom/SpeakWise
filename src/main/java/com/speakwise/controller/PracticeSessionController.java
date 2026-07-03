package com.speakwise.controller;

import com.speakwise.entity.PracticeSession;
import com.speakwise.service.PracticeSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.speakwise.service.SpeechReportService;
import com.speakwise.entity.SpeechReport;
import com.speakwise.repository.PracticeSessionRepository;
import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("/api/sessions")
public class PracticeSessionController {

    private final PracticeSessionService practiceSessionService;

    private final SpeechReportService speechReportService;

    private final PracticeSessionRepository practiceSessionRepository;

    public PracticeSessionController(
            PracticeSessionService practiceSessionService,
            SpeechReportService speechReportService,PracticeSessionRepository practiceSessionRepository) {

        this.practiceSessionService = practiceSessionService;
        this.speechReportService = speechReportService;
        this.practiceSessionRepository = practiceSessionRepository;
    }

    @PostMapping
    public ResponseEntity<SpeechReport> createSession(
            @RequestParam("file") MultipartFile file,
            @RequestParam("questionId") Long questionId)
            throws Exception {

        PracticeSession practiceSession =
                practiceSessionService.uploadAudio(
                        file,
                        questionId);

        SpeechReport speechReport =
                speechReportService.generateReport(
                        practiceSession.getId());

        return ResponseEntity.ok(speechReport);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<SpeechReport> getReport(
            @PathVariable Long id) {

        SpeechReport speechReport =
                speechReportService.getReport(id);

        return ResponseEntity.ok(speechReport);
    }

    @GetMapping
    public ResponseEntity<List<PracticeSession>> getSessions() {

        List<PracticeSession> sessions =
                practiceSessionRepository.findAll();

        return ResponseEntity.ok(sessions);
    }



}
