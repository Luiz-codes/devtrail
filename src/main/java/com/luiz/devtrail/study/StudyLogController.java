package com.luiz.devtrail.study;

import com.luiz.devtrail.auth.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyLogController {

    private final StudyLogService studyLogService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudyLogResponse create(
            @Valid @RequestBody CreateStudyLogRequest request,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return studyLogService.create(request, user);
    }

    @GetMapping
    public List<StudyLogResponse> listAll(@AuthenticationPrincipal AuthenticatedUser user) {
        return studyLogService.listAll(user);
    }

    @GetMapping("/{id}")
    public StudyLogResponse getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return studyLogService.getById(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        studyLogService.delete(id, user);
    }
}