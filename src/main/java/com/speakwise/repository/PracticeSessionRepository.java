package com.speakwise.repository;

import com.speakwise.entity.PracticeSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

}