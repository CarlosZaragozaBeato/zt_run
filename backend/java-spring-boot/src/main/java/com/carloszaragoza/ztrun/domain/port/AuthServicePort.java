package com.carloszaragoza.ztrun.domain.port;

import com.carloszaragoza.ztrun.domain.model.User;

public interface AuthServicePort {
    String login(String usernameOrEmail, String passwordPlain);
    User register(String username, String email, String passwordPlain);
}
