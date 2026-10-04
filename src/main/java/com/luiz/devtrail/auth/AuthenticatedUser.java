package com.luiz.devtrail.auth;

import java.util.UUID;

public record AuthenticatedUser(UUID id, String email, UUID workspaceId) {
}