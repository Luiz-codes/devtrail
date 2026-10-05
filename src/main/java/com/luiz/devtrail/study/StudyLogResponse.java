package com.luiz.devtrail.study;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record StudyLogResponse(
        UUID id,
        UUID userId,
        String topic,
        String description,
        Integer durationMinutes,
        LocalDate studyDate,
        Instant createdAt
) {
    public static StudyLogResponse from(StudyLog log) {
        return new StudyLogResponse(
                log.getId(),
                log.getUser().getId(),
                log.getTopic(),
                log.getDescription(),
                log.getDurationMinutes(),
                log.getStudyDate(),
                log.getCreatedAt()
        );
    }
}