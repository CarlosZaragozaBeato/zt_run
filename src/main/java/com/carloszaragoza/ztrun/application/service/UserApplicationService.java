package com.carloszaragoza.ztrun.application.service;


import com.carloszaragoza.ztrun.application.dto.UserLoginRequest;
import com.carloszaragoza.ztrun.application.dto.UserLoginResponse;
import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.domain.service.UserDomainService;
import com.carloszaragoza.ztrun.infrastructure.configuration.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private final UserDomainService userDomainService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserApplicationService(UserDomainService userDomainService, JwtTokenProvider jwtTokenProvider){
        this.userDomainService = userDomainService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserLoginResponse login(UserLoginRequest request){
        User user = userDomainService.validateLogin(request.getEmail(), request.getPassword());
        String token =  jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
        return new UserLoginResponse(token);
    }


}
