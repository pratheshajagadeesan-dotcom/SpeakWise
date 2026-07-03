package com.speakwise.controller;

import com.speakwise.repository.QuestionRepository;
import com.speakwise.repository.ExpectedKeyPointRepository;
import org.springframework.web.bind.annotation.*;
import com.speakwise.entity.Question;
import org.springframework.http.ResponseEntity;
import com.speakwise.entity.ExpectedKeyPoint;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final QuestionRepository questionRepository;
    private final ExpectedKeyPointRepository expectedKeyPointRepository;

    public AdminController(
            QuestionRepository questionRepository,
            ExpectedKeyPointRepository expectedKeyPointRepository) {

        this.questionRepository = questionRepository;
        this.expectedKeyPointRepository = expectedKeyPointRepository;
    }

    @PostMapping("/questions")
    public ResponseEntity<Question> addQuestion(
            @RequestBody Question question) {

        Question savedQuestion =
                questionRepository.save(question);

        for (ExpectedKeyPoint keyPoint :
                question.getExpectedKeyPoints()) {

            keyPoint.setQuestion(savedQuestion);

            expectedKeyPointRepository.save(keyPoint);
        }

        return ResponseEntity.ok(savedQuestion);
    }

}
