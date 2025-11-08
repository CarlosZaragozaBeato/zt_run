package com.carloszaragoza.ztrun.domain.service;

import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.domain.repository.UserRepository;
import com.carloszaragoza.ztrun.shared.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

@Service
public class UserDomainService {

    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User validateLogin(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!PasswordEncoderUtil.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid Credentials.");
        }
        return user;
    }




}
