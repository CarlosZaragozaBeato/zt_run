package com.carloszaragoza.ztrun.domain.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private boolean enabled;
    private Set<Role> roles;

}
