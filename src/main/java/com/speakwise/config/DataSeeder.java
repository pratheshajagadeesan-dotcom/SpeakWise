package com.speakwise.config;

import com.speakwise.entity.Question;
import com.speakwise.repository.ExpectedKeyPointRepository;
import com.speakwise.repository.FillerWordRepository;
import com.speakwise.repository.PracticeSessionRepository;
import com.speakwise.repository.QuestionRepository;
import com.speakwise.repository.SpeechReportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.speakwise.entity.ExpectedKeyPoint;
import com.speakwise.entity.FillerWord;

@Component
public class DataSeeder implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final ExpectedKeyPointRepository expectedKeyPointRepository;
    private final FillerWordRepository fillerWordRepository;
    private final PracticeSessionRepository practiceSessionRepository;
    private final SpeechReportRepository speechReportRepository;

    public DataSeeder(
            QuestionRepository questionRepository,
            ExpectedKeyPointRepository expectedKeyPointRepository,
            FillerWordRepository fillerWordRepository,
            PracticeSessionRepository practiceSessionRepository,
            SpeechReportRepository speechReportRepository) {

        this.questionRepository = questionRepository;
        this.expectedKeyPointRepository = expectedKeyPointRepository;
        this.fillerWordRepository = fillerWordRepository;
        this.practiceSessionRepository = practiceSessionRepository;
        this.speechReportRepository = speechReportRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(questionRepository.count() == 0) {
            Question q1 = new Question(
                    "Tell me about yourself",
                    "Introduce yourself briefly."
            );

            Question q2 = new Question(
                    "What are your strengths?",
                    "Explain your strengths with examples."
            );

            Question q3 = new Question(
                    "Why should we hire you?",
                    "Explain why you are suitable for the role."
            );

            Question q4 = new Question(
                    "Describe a challenging situation.",
                    "Describe a problem and how you solved it."
            );

            Question q5 = new Question(
                    "Where do you see yourself in five years?",
                    "Talk about your future goals."
            );

            questionRepository.save(q1);
            questionRepository.save(q2);
            questionRepository.save(q3);
            questionRepository.save(q4);
            questionRepository.save(q5);

            expectedKeyPointRepository.save(new ExpectedKeyPoint("Education"));
            expectedKeyPointRepository.save(new ExpectedKeyPoint("Skills"));
            expectedKeyPointRepository.save(new ExpectedKeyPoint("Experience"));
            expectedKeyPointRepository.save(new ExpectedKeyPoint("Achievements"));
            expectedKeyPointRepository.save(new ExpectedKeyPoint("Career Goals"));

            fillerWordRepository.save(new FillerWord("um"));
            fillerWordRepository.save(new FillerWord("like"));
            fillerWordRepository.save(new FillerWord("actually"));
            fillerWordRepository.save(new FillerWord("basically"));
            fillerWordRepository.save(new FillerWord("you know"));

        }

    }
}
