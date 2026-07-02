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

    public PracticeSessionService(
            PracticeSessionRepository practiceSessionRepository,
            FileStorageService fileStorageService) {

        this.practiceSessionRepository = practiceSessionRepository;
        this.fileStorageService = fileStorageService;
    }

    public PracticeSession uploadAudio(MultipartFile file)
            throws IOException {

        String filePath = fileStorageService.uploadAudio(file);

        PracticeSession practiceSession = new PracticeSession();

        practiceSession.setAudioFilePath(filePath);

        practiceSessionRepository.save(practiceSession);

        return practiceSession;

    }
}
