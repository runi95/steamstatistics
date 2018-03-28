package com.steamstatistics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SteamFriendService {

    @Autowired
    SteamFriendRepository steamFriendRepository;

    public void save(SteamFriendEntity steamFriendEntity) {
        if(steamFriendEntity != null)
            steamFriendRepository.save(steamFriendEntity);
    }

    public void saveAll(Collection<SteamFriendEntity> steamFriendEntityList) {
        steamFriendRepository.saveAll(steamFriendEntityList);
        //steamFriendRepository.save(steamFriendEntityList);
//        steamFriendEntityList.forEach((steamFriendEntity -> save(steamFriendEntity)));
    }

    public SteamFriendEntity get(long steamid) {
        return steamFriendRepository.findBySteamid(steamid);
    }

    public List<SteamFriendEntity> getAllInListUpdatedSince(List<Long> steamidList, long updatetime) {
        return steamFriendRepository.findBySteamidIsInAndUpdatetimeLessThan(steamidList, updatetime);
    }

    public List<SteamFriendEntity> getAllInList(List<Long> steamidList) {
        return steamFriendRepository.findBySteamidIsIn(steamidList);
    }

    public List<SteamFriendEntity> getAllInList(Long[] steamidList) {
        return steamFriendRepository.findBySteamidIsIn(steamidList);
    }

    public List<SteamFriendEntity> findAllByUpdatetimeLessThan(long epoch) {
        return steamFriendRepository.findAllByUpdatetimeLessThan(epoch);
    }

    public List<Long> findAllByUpdatetimeLessThanEpochAndReturnAsId(long epoch) {
        return steamFriendRepository.findAllByUpdatetimeGreaterThanAndReturnId(epoch);
    }

    public Iterable<SteamFriendEntity> getAll() {
        return steamFriendRepository.findAll();
    }

    public List<SteamFriendEntity> search(String expr) {
        return steamFriendRepository.findByPersonanameMatchesRegex(expr);
    }
}
