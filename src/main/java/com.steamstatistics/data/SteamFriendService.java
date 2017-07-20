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

    public HashMap<Long, SteamFriendEntity> getAllMap() {
        Iterable<SteamFriendEntity> all = steamFriendRepository.findAll();

        HashMap<Long, SteamFriendEntity> map = new HashMap<>();
        for(SteamFriendEntity steamFriendEntity : all) {
            map.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity);
        }

        return map;
    }

    public void updateFriendsList(Map<Long, SteamFriendEntity> updatedFriendsList) {
        HashMap<Long, SteamFriendEntity> oldFriendsList = getAllMap();

        for(Long l : updatedFriendsList.keySet()) {
            if(!oldFriendsList.containsKey(l)) {
                SteamFriendEntity addedFriend = updatedFriendsList.get(l);
                addedFriend.setAddDate(addedFriend.getFriendsince());

                System.out.println(addedFriend.getPersonaname() + " is a new friend!");
                save(updatedFriendsList.get(l));
            }
        }

        for(Long l : oldFriendsList.keySet()) {
            if(!updatedFriendsList.containsKey(l)) {
                SteamFriendEntity removedFriend = oldFriendsList.get(l);
                removedFriend.setRemoveDate(timeService.getCurrentUnixTime());

                System.out.println(removedFriend.getPersonaname() + " has removed you!");
                save(removedFriend);
            }
        }

    }
}
