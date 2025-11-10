package com.carloszaragoza.ztrun.domain.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Role {
    private Long id;
    private String name;
    private Set<Permission> permissions;

}
