package com.carloszaragoza.ztrun.domain.repository;


import com.carloszaragoza.ztrun.domain.model.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
