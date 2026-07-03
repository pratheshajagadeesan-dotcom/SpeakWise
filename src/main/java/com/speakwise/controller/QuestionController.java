package com.speakwise.controller;

import com.speakwise.entity.Question;
import com.speakwise.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getQuestions() {

        List<Question> questions =
                questionRepository.findAll();

        return ResponseEntity.ok(questions);
    }

}
