package com.steamstatistics.data;

import org.springframework.data.repository.CrudRepository;

public interface SteamAuthRepository  extends CrudRepository<SteamAuthEntity, SteamAuthEntityKey> {
    SteamAuthEntity findBySteamid(long steamid);

    SteamAuthEntity findByAuthtoken(String authtoken);
}
