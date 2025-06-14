package com.example.demo.repository;

import com.example.demo.model.GameRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRecruitmentRepository extends JpaRepository<GameRecruitment, Long> {
    List<GameRecruitment> findAllByOrderByCreatedAtDesc();
    List<GameRecruitment> findByGameNameOrderByCreatedAtDesc(String gameName);
    List<GameRecruitment> findByPlatformOrderByCreatedAtDesc(String platform);
    List<GameRecruitment> findByGameNameAndPlatformOrderByCreatedAtDesc(String gameName, String platform);
} 