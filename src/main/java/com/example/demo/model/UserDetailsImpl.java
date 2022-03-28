package com.example.demo.model;

import com.example.demo.entity.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@ToString
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UserEntity userEntity;

    public UserDetailsImpl(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(userEntity.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userEntity.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userEntity.isActive();

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userEntity.isActive();

    }

    @Override
    public boolean isEnabled() {
        return userEntity.isActive();

    }
}
