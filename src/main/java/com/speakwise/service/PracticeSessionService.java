package com.speakwise.service;

import com.speakwise.entity.PracticeSession;
import com.speakwise.repository.PracticeSessionRepository;
import com.speakwise.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.speakwise.entity.Question;

import java.io.IOException;

@Service
public class PracticeSessionService {

    private final PracticeSessionRepository practiceSessionRepository;

    private final FileStorageService fileStorageService;

    private final SpeechToTextService speechToTextService;

    private final QuestionRepository questionRepository;

    public PracticeSessionService(
            PracticeSessionRepository practiceSessionRepository,
            FileStorageService fileStorageService,
            SpeechToTextService speechToTextService,QuestionRepository questionRepository) {

        this.practiceSessionRepository = practiceSessionRepository;
        this.fileStorageService = fileStorageService;
        this.speechToTextService = speechToTextService;
        this.questionRepository = questionRepository;
    }

    public PracticeSession uploadAudio(
            MultipartFile file,
            Long questionId)
            throws Exception {

        Question question =
                questionRepository.findById(questionId)
                        .orElseThrow();

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

        practiceSession.setQuestion(question);

        speechToTextService.printWordTimings(result);

        practiceSessionRepository.save(practiceSession);

        return practiceSession;

    }
}
