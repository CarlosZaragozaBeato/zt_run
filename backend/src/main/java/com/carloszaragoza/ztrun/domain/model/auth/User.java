package com.carloszaragoza.ztrun.domain.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;

}