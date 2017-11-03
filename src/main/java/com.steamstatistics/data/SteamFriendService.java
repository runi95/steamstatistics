package com.steamstatistics.data;

import com.steamstatistics.backend.LongestFriendship;
import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SteamFriendService {

    @Autowired
    SteamFriendRepository steamFriendRepository;

    public void save(SteamFriendEntity steamFriendEntity) {
        if(steamFriendEntity != null)
            steamFriendRepository.save(steamFriendEntity);
    }

    public void saveAll(Collection<SteamFriendEntity> steamFriendEntityList) {
        steamFriendRepository.save(steamFriendEntityList);
//        steamFriendEntityList.forEach((steamFriendEntity -> save(steamFriendEntity)));
    }

    public SteamFriendEntity get(long steamid) {
        return steamFriendRepository.findOne(steamid);
    }

    public List<SteamFriendEntity> getAllInListUpdatedSince(List<Long> steamidList, long updatetime) {
        return steamFriendRepository.findBySteamidIsInAndUpdatetimeLessThan(steamidList, updatetime);
    }

    public List<SteamFriendEntity> getAllInList(List<Long> steamidList) {
        return steamFriendRepository.findBySteamidIsIn(steamidList);
    }

    public Iterable<SteamFriendEntity> getAll() {
        return steamFriendRepository.findAll();
    }
}
