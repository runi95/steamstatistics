package com.steamstatistics.data;

import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SteamFriendService {

    @Autowired
    SteamFriendRepository steamFriendRepository;

    TimeService timeService = new TimeService();

    public void save(SteamFriendEntity steamFriendEntity) {
        steamFriendRepository.save(steamFriendEntity);
    }

    public SteamFriendEntity get(SteamFriendEntityKey steamFriendEntityKey) {
        return steamFriendRepository.findOne(steamFriendEntityKey);
    }

    public Iterable<SteamFriendEntity> getAll() {
        return steamFriendRepository.findAll();
    }

    public HashMap<Long, SteamFriendEntity> getAllMap(long steamid) {
        Iterable<SteamFriendEntity> all = steamFriendRepository.findBySteamid(steamid);

        HashMap<Long, SteamFriendEntity> map = new HashMap<>();
        for(SteamFriendEntity steamFriendEntity : all) {
            map.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity);
        }

        return map;
    }

    public Map<Long, SteamFriendEntity>[] updateFriendsList(Map<Long, SteamFriendEntity> updatedFriendsList, long steamid) {
        Map<Long, SteamFriendEntity>[] maps = new Map[]{new HashMap(), new HashMap()};
        HashMap<Long, SteamFriendEntity> oldFriendsList = getAllMap(steamid);

        for(Long l : updatedFriendsList.keySet()) {
            if(!oldFriendsList.containsKey(l)) {
                SteamFriendEntity addedFriend = updatedFriendsList.get(l);
                addedFriend.setAddDate(addedFriend.getFriendsince());
                maps[0].put(l, addedFriend);

                save(updatedFriendsList.get(l));
            }
        }

        for(Long l : oldFriendsList.keySet()) {
            if(!updatedFriendsList.containsKey(l)) {
                SteamFriendEntity removedFriend = oldFriendsList.get(l);
                removedFriend.setRemoveDate(timeService.getCurrentUnixTime());
                maps[1].put(l, removedFriend);

                save(removedFriend);
            }
        }

        return maps;
    }
}
