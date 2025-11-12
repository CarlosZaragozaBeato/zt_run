package com.carloszaragoza.ztrun.domain.model.auth;


import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private LocalDateTime createdAt;
    private Set<Role> roles;

}