package com.speakwise.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents one practice session by a user.
 */
@Entity
@Table(name = "practice_sessions")
public class PracticeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String audioFilePath;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore

    @OneToOne(mappedBy = "practiceSession")
    private SpeechReport speechReport;

    private String transcript;

    public PracticeSession() {
    }

    public PracticeSession(String topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SpeechReport getSpeechReport() {
        return speechReport;
    }

    public void setSpeechReport(SpeechReport speechReport) {
        this.speechReport = speechReport;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}
