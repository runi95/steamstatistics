package com.steamstatistics.userauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserToken(String userToken);
    User findBySteamid(long steamid);
}