package com.steamstatistics.userauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserToken(String userToken);
    User findBySteamid(long steamid);
}