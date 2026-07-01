package com.speakwise.repository;

import com.speakwise.entity.SpeechReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeechReportRepository extends JpaRepository<SpeechReport, Long> {

}
