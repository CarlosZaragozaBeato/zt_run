package com.carloszaragoza.ztrun.domain.model.auth;
import java.util.HashSet;
import java.util.Set;

public class Role {
    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;

    public Role() {
        this.permissions = new HashSet<>();
    }

    public Role(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Permission> getPermissions() { return new HashSet<>(permissions); }
    public void setPermissions(Set<Permission> permissions) { this.permissions = permissions; }
}