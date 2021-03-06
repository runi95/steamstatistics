package com.steamstatistics.userauth;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String userToken;

    @Column(name = "steam_id", nullable = false, unique = true)
    private long steamid;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public long getSteamid() {
        return steamid;
    }

    public void setSteamid(long steamId) {
        this.steamid = steamId;
    }

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_token", referencedColumnName = "userToken"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}