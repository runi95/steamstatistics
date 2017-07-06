package com.steamstatistics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SteamProfileService {

    @Autowired
    SteamProfileRepository steamProfileRepository;

    public void save(SteamProfileEntity steamProfileEntity) {
        steamProfileRepository.save(steamProfileEntity);
    }

    public SteamProfileEntity get(Long steamid) {
        return steamProfileRepository.findOne(steamid);
    }

    public Iterable<SteamProfileEntity> getAll() {
        return steamProfileRepository.findAll();
    }
}
