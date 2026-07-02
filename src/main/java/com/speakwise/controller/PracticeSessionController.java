package com.speakwise.controller;

import com.speakwise.entity.PracticeSession;
import com.speakwise.service.PracticeSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/sessions")
public class PracticeSessionController {

    private final PracticeSessionService practiceSessionService;

    public PracticeSessionController(
            PracticeSessionService practiceSessionService) {

        this.practiceSessionService = practiceSessionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PracticeSession> uploadAudio(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        PracticeSession practiceSession =
                practiceSessionService.uploadAudio(file);

        return ResponseEntity.ok(practiceSession);
    }

}
