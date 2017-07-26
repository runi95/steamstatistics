package com.steamstatistics.data;

import com.steamstatistics.backend.SteamFriendWithDateComparator;
import com.steamstatistics.backend.SteamFriendsSinceComparator;
import com.steamstatistics.steamapi.SteamRemovedFriends;
import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

    public SteamRemovedFriends getRemovedSteamFriends(long steamid) {
        List<SteamFriendEntity> list = getRemovedFriends(steamid);
        SteamRemovedFriends steamRemovedFriends = new SteamRemovedFriends();

        int monthIndex = 0, weekIndex = 0;
        for(SteamFriendEntity steamFriendEntity : list) {
            if(steamFriendEntity.getRemoveDate() > timeService.getLastWeekUnixTime()) {
                weekIndex++;
                monthIndex++;
            } else if(steamFriendEntity.getRemoveDate() > timeService.getLastMonthUnixTime()) {
                monthIndex++;
            }

            steamRemovedFriends.addSteamFriend(new SteamFriendWithDate(steamFriendEntity, steamFriendEntity.getRemoveDate(), timeService));
        }
        steamRemovedFriends.setFriendsLostLastMonth(monthIndex);
        steamRemovedFriends.setFriendsLostLastWeek(weekIndex);

        return steamRemovedFriends;
    }

    public TreeSet<SteamFriendWithDate> getRemovedFriendsSet(long steamid) {
        List<SteamFriendEntity> list = getRemovedFriends(steamid);
        TreeSet<SteamFriendWithDate> treeSet = new TreeSet<>(new SteamFriendWithDateComparator());

        for(SteamFriendEntity steamFriendEntity : list) {
            treeSet.add(new SteamFriendWithDate(steamFriendEntity, steamFriendEntity.getRemoveDate(), timeService));
        }

        return treeSet;
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