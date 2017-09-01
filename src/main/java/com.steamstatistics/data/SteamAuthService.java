package com.steamstatistics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SteamAuthService {

    @Autowired
    SteamAuthRepository steamAuthRepository;

    public SteamAuthEntity findBySteamid(long steamid) {
        return steamAuthRepository.findBySteamid(steamid);
    }

    public SteamAuthEntity findByAuthtoken(String authtoken) {
        return steamAuthRepository.findByAuthtoken(authtoken);
    }
}
