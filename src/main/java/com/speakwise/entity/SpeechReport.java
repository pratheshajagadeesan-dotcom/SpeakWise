package com.speakwise.entity;

import jakarta.persistence.*;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Stores the analysis report of a speech.
 */
@Entity
@Table(name = "speech_reports")
public class SpeechReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @JsonIgnore

    @OneToOne
    @JoinColumn(name = "practice_session_id")
    private PracticeSession practiceSession;

    @OneToMany(mappedBy = "speechReport")
    private List<FillerWord> fillerWords;

    public SpeechReport() {
    }

    public SpeechReport(Double score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public PracticeSession getPracticeSession() {
        return practiceSession;
    }

    public void setPracticeSession(PracticeSession practiceSession) {
        this.practiceSession = practiceSession;
    }

    public List<FillerWord> getFillerWords() {
        return fillerWords;
    }

    public void setFillerWords(List<FillerWord> fillerWords) {
        this.fillerWords = fillerWords;
    }
}
