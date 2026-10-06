package com.luiz.devtrail.dashboard;

import com.luiz.devtrail.study.StudyLogResponse;

import java.util.List;

public record DashboardSummaryResponse(
        long totalMinutes,
        double totalHours,
        long totalSessions,
        int currentStreak,
        long last7DaysMinutes,
        List<StudyLogResponse> recentStudies
) {
}