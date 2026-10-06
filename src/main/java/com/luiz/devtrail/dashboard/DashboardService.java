package com.luiz.devtrail.dashboard;

import com.luiz.devtrail.auth.AuthenticatedUser;
import com.luiz.devtrail.study.StudyLog;
import com.luiz.devtrail.study.StudyLogRepository;
import com.luiz.devtrail.study.StudyLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudyLogRepository studyLogRepository;

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary(AuthenticatedUser currentUser) {
        List<StudyLog> logs = studyLogRepository
                .findAllByWorkspaceIdOrderByStudyDateDescCreatedAtDesc(currentUser.workspaceId());

        long totalMinutes = logs.stream()
                .mapToLong(StudyLog::getDurationMinutes)
                .sum();

        // Converte minutos para horas com 1 casa decimal (ex: 1.5 horas)
        double totalHours = Math.round((totalMinutes / 60.0) * 10.0) / 10.0;
        long totalSessions = logs.size();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        long last7DaysMinutes = logs.stream()
                .filter(l -> !l.getStudyDate().isBefore(sevenDaysAgo) && !l.getStudyDate().isAfter(today))
                .mapToLong(StudyLog::getDurationMinutes)
                .sum();

        int currentStreak = calculateStreak(logs, today);

        List<StudyLogResponse> recentStudies = logs.stream()
                .limit(5)
                .map(StudyLogResponse::from)
                .toList();

        return new DashboardSummaryResponse(
                totalMinutes,
                totalHours,
                totalSessions,
                currentStreak,
                last7DaysMinutes,
                recentStudies
        );
    }

    private int calculateStreak(List<StudyLog> logs, LocalDate today) {
        Set<LocalDate> dates = logs.stream()
                .map(StudyLog::getStudyDate)
                .collect(Collectors.toSet());

        if (dates.isEmpty()) {
            return 0;
        }

        LocalDate checkDate = today;
        // Se ainda não registrou estudo hoje, verifica se estudou ontem para manter a sequência ativa
        if (!dates.contains(checkDate)) {
            checkDate = today.minusDays(1);
            if (!dates.contains(checkDate)) {
                return 0;
            }
        }

        int streak = 0;
        while (dates.contains(checkDate)) {
            streak++;
            checkDate = checkDate.minusDays(1);
        }

        return streak;
    }
}