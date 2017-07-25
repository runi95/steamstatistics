package com.steamstatistics.data;

import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public Map<Long, SteamFriendEntity> getAllMap(long steamid) {
        Iterable<SteamFriendEntity> all = steamFriendRepository.findBySteamid(steamid);

        return convertToMap(all);
    }

    public Map<Long, SteamFriendEntity> getUnremovedMap(long steamid) {
        Iterable<SteamFriendEntity> unremoved = steamFriendRepository.findAllUnremovedFriends(steamid);

        return convertToMap(unremoved);
    }

    public List<SteamFriendEntity> getRemovedFriends(long steamid) {
        List<SteamFriendEntity> steamFriendEntities = steamFriendRepository.findByRemoveDateNotNullAndSteamidOrderByRemoveDateDesc(steamid);

        return steamFriendEntities;
    }

    public Map<Long, SteamFriendEntity>[] updateFriendsList(Map<Long, SteamFriendEntity> updatedFriendsList, long steamid) {
        Map<Long, SteamFriendEntity>[] maps = new Map[]{new HashMap(), new HashMap()};
        Map<Long, SteamFriendEntity> oldFriendsList = getUnremovedMap(steamid);

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

    private Map<Long, SteamFriendEntity> convertToMap(Iterable<SteamFriendEntity> list) {
        HashMap<Long, SteamFriendEntity> map = new HashMap<>();
        for(SteamFriendEntity steamFriendEntity : list) {
            map.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity);
        }

        return map;
    }
}
