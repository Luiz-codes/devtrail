package com.luiz.devtrail.study;

import com.luiz.devtrail.auth.AuthenticatedUser;
import com.luiz.devtrail.user.AppUser;
import com.luiz.devtrail.user.AppUserRepository;
import com.luiz.devtrail.workspace.Workspace;
import com.luiz.devtrail.workspace.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyLogService {

    private final StudyLogRepository studyLogRepository;
    private final WorkspaceRepository workspaceRepository;
    private final AppUserRepository userRepository;

    @Transactional
    public StudyLogResponse create(CreateStudyLogRequest request, AuthenticatedUser currentUser) {
        Workspace workspace = workspaceRepository.findById(currentUser.workspaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace não encontrado"));

        AppUser user = userRepository.findById(currentUser.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        StudyLog log = new StudyLog();
        log.setWorkspace(workspace);
        log.setUser(user);
        log.setTopic(request.topic().trim());
        log.setDescription(request.description() != null ? request.description().trim() : null);
        log.setDurationMinutes(request.durationMinutes());
        log.setStudyDate(request.studyDate());

        StudyLog saved = studyLogRepository.save(log);
        return StudyLogResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<StudyLogResponse> listAll(AuthenticatedUser currentUser) {
        return studyLogRepository.findAllByWorkspaceIdOrderByStudyDateDescCreatedAtDesc(currentUser.workspaceId())
                .stream()
                .map(StudyLogResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudyLogResponse getById(UUID id, AuthenticatedUser currentUser) {
        StudyLog log = studyLogRepository.findByIdAndWorkspaceId(id, currentUser.workspaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de estudo não encontrado"));
        return StudyLogResponse.from(log);
    }

    @Transactional
    public void delete(UUID id, AuthenticatedUser currentUser) {
        StudyLog log = studyLogRepository.findByIdAndWorkspaceId(id, currentUser.workspaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de estudo não encontrado"));
        studyLogRepository.delete(log);
    }
}