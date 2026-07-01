package com.speakwise.entity;

import jakarta.persistence.*;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Stores filler words detected in a speech.
 */
@Entity
@Table(name = "filler_words")
public class FillerWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "speech_report_id")
    private SpeechReport speechReport;

    public FillerWord() {
    }

    public FillerWord(String word) {
        this.word = word;
        this.count = 0;
    }

    public FillerWord(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public SpeechReport getSpeechReport() {
        return speechReport;
    }

    public void setSpeechReport(SpeechReport speechReport) {
        this.speechReport = speechReport;
    }
}
