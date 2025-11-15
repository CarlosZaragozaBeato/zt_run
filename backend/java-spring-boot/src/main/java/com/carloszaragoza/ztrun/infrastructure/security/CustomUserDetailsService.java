package com.carloszaragoza.ztrun.infrastructure.security;

import com.carloszaragoza.ztrun.domain.port.UserRepositoryPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepo;

    public CustomUserDetailsService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var authorities = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream()
                        .map(p -> new SimpleGrantedAuthority("PERM_" + p.getName())))
                .collect(Collectors.toSet());

        // También añadir ROLE_*
        authorities.addAll(user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toSet()));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .disabled(!user.isEnabled())
                .build();
    }
}
