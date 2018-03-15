package com.steamstatistics.userauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private User user;

    final private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.user = user;
        this.authorities = getAuthorities(user);
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

    public String getUserToken() { return user.getUserToken(); }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * @return true if the user's account is valid (ie non-expired), false if no longer valid (ie expired)
     */
    public boolean isAccountNonExpired() {
        // Always true for now, might change in future updates!
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * @return true if the user is not locked, false otherwise
     */
    public boolean isAccountNonLocked() {
        // Always true for now, might change in future updates!
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     * @return true if the user's credentials are valid (ie non-expired), false if no longer valid (ie expired)
     */
    public boolean isCredentialsNonExpired() {
        // Always true as we don't have a password
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * @return true if the user is enabled, false otherwise
     */
    public boolean isEnabled() {
        // Always true for now, might change in future updates!
        return true;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach((role) -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        //user.getRoles().forEach((role) -> role.getPrivileges().forEach((privilege) -> grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()))));

        return grantedAuthorities;
    }
}
