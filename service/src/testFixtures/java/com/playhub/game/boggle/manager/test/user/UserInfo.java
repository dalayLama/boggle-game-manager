package com.playhub.game.boggle.manager.test.user;

import java.util.Set;
import java.util.UUID;

public record UserInfo(
        UUID id,
        String name,
        Set<String> roles,
        String jwtToken
) {
}
