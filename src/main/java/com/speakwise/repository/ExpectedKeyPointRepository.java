package com.speakwise.repository;

import com.speakwise.entity.ExpectedKeyPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpectedKeyPointRepository extends JpaRepository<ExpectedKeyPoint, Long> {

    List<ExpectedKeyPoint> findByQuestionId(Long questionId);

}