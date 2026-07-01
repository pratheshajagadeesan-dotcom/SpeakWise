package com.speakwise.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.List;
import jakarta.persistence.OneToMany;

/**
 * Represents an interview question.
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "question")
    private List<ExpectedKeyPoint> expectedKeyPoints;

    public Question() {
    }

    public Question(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExpectedKeyPoint> getExpectedKeyPoints() {
        return expectedKeyPoints;
    }

    public void setExpectedKeyPoints(List<ExpectedKeyPoint> expectedKeyPoints) {
        this.expectedKeyPoints = expectedKeyPoints;
    }
}