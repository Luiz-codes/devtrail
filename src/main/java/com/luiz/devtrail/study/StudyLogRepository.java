package com.luiz.devtrail.study;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudyLogRepository extends JpaRepository<StudyLog, UUID> {

    List<StudyLog> findAllByWorkspaceIdOrderByStudyDateDescCreatedAtDesc(UUID workspaceId);

    Optional<StudyLog> findByIdAndWorkspaceId(UUID id, UUID workspaceId);

    List<StudyLog> findAllByWorkspaceIdAndStudyDateBetweenOrderByStudyDateAsc(
            UUID workspaceId, LocalDate startDate, LocalDate endDate);
}