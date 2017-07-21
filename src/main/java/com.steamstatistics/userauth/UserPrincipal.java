package com.steamstatistics.userauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private User user;

    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    public UserPrincipal(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return "NOTUSED";
    }

    public String getUsername() {
        return user.getUserToken();
    }

    public long getSteamId() {
        return user.getSteamId();
    }

    // TODO: Have a look at the boolean checks

    public boolean isAccountNonExpired() {
        // Always true for now, might change in future updates!
        return true;
    }

    public boolean isAccountNonLocked() {
        // Always true for now, might change in future updates!
        return true;
    }

    public boolean isCredentialsNonExpired() {
        // Always true as we don't have a password
        return true;
    }

    public boolean isEnabled() {
        // Always true for now, might change in future updates!
        return true;
    }

}
