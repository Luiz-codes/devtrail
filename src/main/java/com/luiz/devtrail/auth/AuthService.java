package com.luiz.devtrail.auth;

import com.luiz.devtrail.user.AppUser;
import com.luiz.devtrail.user.AppUserRepository;
import com.luiz.devtrail.workspace.Workspace;
import com.luiz.devtrail.workspace.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        Workspace workspace = workspaceRepository.save(
                new Workspace("Espaço de " + request.name().trim()));

        AppUser user = new AppUser();
        user.setWorkspace(workspace);
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(AppUser.Role.OWNER);
        user = userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getName(), user.getEmail(), workspace.getId());
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        AppUser user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        String token = jwtService.generateToken(
                user.getId(), user.getEmail(), user.getWorkspace().getId());

        return new LoginResponse(token, "Bearer");
    }

    public record RegisterResponse(UUID id, String name, String email, UUID workspaceId) {
    }

    public record LoginResponse(String token, String type) {
    }
}