package com.speakwise.service;

import com.speakwise.entity.PracticeSession;
import com.speakwise.repository.PracticeSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PracticeSessionService {

    private final PracticeSessionRepository practiceSessionRepository;

    private final FileStorageService fileStorageService;

    private final SpeechToTextService speechToTextService;

    public PracticeSessionService(
            PracticeSessionRepository practiceSessionRepository,
            FileStorageService fileStorageService,
            SpeechToTextService speechToTextService) {

        this.practiceSessionRepository = practiceSessionRepository;
        this.fileStorageService = fileStorageService;
        this.speechToTextService = speechToTextService;
    }

    public PracticeSession uploadAudio(MultipartFile file)
            throws Exception {

        String filePath = fileStorageService.uploadAudio(file);

        String uploadResponse =
                speechToTextService.uploadAudio(
                        new java.io.File(filePath));

        String uploadUrl =
                speechToTextService.getUploadUrl(uploadResponse);

        String transcriptResponse =
                speechToTextService.createTranscript(uploadUrl);

        speechToTextService.printWordTimings(transcriptResponse);

        String transcriptId =
                speechToTextService.getTranscriptId(transcriptResponse);

        String result =
                speechToTextService.waitForCompletion(transcriptId);

        String transcript =
                speechToTextService.getTranscriptText(result);

        System.out.println("Transcript: " + transcript);

        PracticeSession practiceSession = new PracticeSession();

        practiceSession.setAudioFilePath(filePath);

        practiceSession.setTranscript(transcript);

        speechToTextService.printWordTimings(result);

        practiceSessionRepository.save(practiceSession);

        return practiceSession;

    }
}
