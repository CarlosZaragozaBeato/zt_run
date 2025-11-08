package com.carloszaragoza.ztrun.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String email;
    private String password;

    // TODO - cambio por entidad o enum
    private String role;

}
