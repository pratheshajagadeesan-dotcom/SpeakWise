package com.speakwise.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Represents an expected key point for a question.
 */
@Entity
@Table(name = "expected_key_points")
public class ExpectedKeyPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String keyPoint;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public ExpectedKeyPoint() {
    }

    public ExpectedKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}