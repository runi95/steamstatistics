package com.steamstatistics.data;

import com.steamstatistics.backend.LongestFriendship;
import com.steamstatistics.backend.SteamProfileToFriendsEntityComparator;
import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SteamProfileToFriendService {

    @Autowired
    SteamProfileToFriendRepository steamProfileToFriendRepository;

    @Autowired
    SteamFriendRepository steamFriendRepository;

    TimeService timeService = new TimeService();

    public SteamProfileToFriendEntity getLongestFriendship() {
        List<SteamProfileToFriendEntity> steamidListSortedByFriendSinceDate = steamProfileToFriendRepository.findByFriendsinceNotNullAndDescOrder();
        if(steamidListSortedByFriendSinceDate.isEmpty())
            return null;

        return steamidListSortedByFriendSinceDate.get(0);


        //LocalDateTime localTimeDate = timeService.getLocalDateTimeFromUnix(steamidListSortedByFriendSinceDate.get(0).getFriendsince());
        //List<Long> longestFriendshipList = new ArrayList<>();
        //longestFriendshipList.add(steamidListSortedByFriendSinceDate.get(0).getSteamprofileid());
        //longestFriendshipList.add(steamidListSortedByFriendSinceDate.get(0).getSteamfriendid());

        //List<SteamFriendEntity> list = steamFriendRepository.findBySteamidIsIn(longestFriendshipList);

        //LongestFriendship longestFriendship = new LongestFriendship(list.get(0), list.get(1), (localTimeDate.getDayOfMonth() <= 9 ? "0" + localTimeDate.getDayOfMonth() : localTimeDate.getDayOfMonth()) + "/" + (localTimeDate.getMonthValue() <= 9 ? "0" + localTimeDate.getMonthValue() : localTimeDate.getMonthValue()) + "/" + localTimeDate.getYear());

        //return longestFriendship;
    }

    public List<SteamProfileToFriendEntity> getAll() {
        return steamProfileToFriendRepository.findAllUsers();
    }

    public void save(SteamProfileToFriendEntity steamProfileToFriendEntity) {
        steamProfileToFriendRepository.save(steamProfileToFriendEntity);
    }

    public void saveAll(Collection<SteamProfileToFriendEntity> steamProfileToFriendEntityCollection) {
        steamProfileToFriendRepository.saveAll(steamProfileToFriendEntityCollection);
    }

    public List<SteamProfileToFriendEntity> findByRemoveDateGreaterThan(long epoch) {
        return steamProfileToFriendRepository.findByRemoveDateGreaterThan(epoch);
    }

    public List<SteamProfileToFriendEntity> findByFriendsinceGreaterThan(long epoch) {
        return steamProfileToFriendRepository.findByFriendsinceGreaterThan(epoch);
    }

    public Object[][] findByFriendsinceGreaterThanTwo(long epoch) {
        return steamProfileToFriendRepository.findByFriendsinceGreaterThanTwo(epoch);
    }

    public List<SteamProfileToFriendEntity> findByFriendsinceGreaterThanAndProfileid(long epoch, long steamid) {
        return steamProfileToFriendRepository.findByFriendsinceGreaterThanAndSteamprofileid(epoch, steamid);
    }

    public List<SteamProfileToFriendEntity> findByRemoveDateGreaterThanAndProfileid(long epoch, long steamid) {
        return steamProfileToFriendRepository.findByRemoveDateGreaterThanAndSteamprofileid(epoch, steamid);
    }

    public List<Long> getAllFriendsSteamIds(long steamid) {
        return steamProfileToFriendRepository.findAllFriendsSteamIds(steamid);
    }

    public Map<Long, SteamProfileToFriendEntity> getAllFriends(long steamid) {
        Iterable<SteamProfileToFriendEntity> allFriends = steamProfileToFriendRepository.findAllBySteamprofileid(steamid);
        return convertToMap(allFriends);
    }

    public List<SteamProfileToFriendEntity> getUnremovedFriends(long steamid) {
        return steamProfileToFriendRepository.findAllUnremovedFriends(steamid);
    }

    public Map<Long, SteamProfileToFriendEntity> getUnremovedMap(long steamid) {
        Iterable<SteamProfileToFriendEntity> unremoved = steamProfileToFriendRepository.findAllUnremovedFriends(steamid);
        return convertToMap(unremoved);
    }

    public List<SteamProfileToFriendEntity> getRemovedFriends(long steamid) {
        List<SteamProfileToFriendEntity> steamFriendEntities = steamProfileToFriendRepository.findByRemoveDateNotNullAndSteamidOrderByRemoveDateDesc(steamid);
        return steamFriendEntities;
    }

    public TreeSet<SteamProfileToFriendEntity> getRemovedFriendsSet(long steamid) {
        List<SteamProfileToFriendEntity> list = getRemovedFriends(steamid);
        TreeSet<SteamProfileToFriendEntity> treeSet = new TreeSet<>(new SteamProfileToFriendsEntityComparator());

        for (SteamProfileToFriendEntity steamProfileToFriendEntity : list) {
            treeSet.add(steamProfileToFriendEntity);
        }

        return treeSet;
    }

    public Map<Long, SteamProfileToFriendEntity>[] updateFriendsList(Map<Long, SteamProfileToFriendEntity> updatedFriendsList, long steamid) {
        Map<Long, SteamProfileToFriendEntity>[] maps = new Map[]{new HashMap(), new HashMap()};
        Map<Long, SteamProfileToFriendEntity> oldFriendsList = getUnremovedMap(steamid);

        for (Long l : updatedFriendsList.keySet()) {
            if (!oldFriendsList.containsKey(l)) {
                SteamProfileToFriendEntity addedFriend = updatedFriendsList.get(l);
                maps[0].put(l, addedFriend);

                save(addedFriend);
            }
        }

        for (Long l : oldFriendsList.keySet()) {
            if (!updatedFriendsList.containsKey(l)) {
                System.out.println("new list doesn't contain " + l);
                SteamProfileToFriendEntity removedFriend = oldFriendsList.get(l);
                removedFriend.setRemoveDate(timeService.getCurrentUnixTime());
                maps[1].put(l, removedFriend);

                save(removedFriend);
            }
        }

        return maps;
    }

    public List<SteamProfileToFriendEntity> findAllAddedFriends(long steamid) {
        return steamProfileToFriendRepository.findAllAddedFriends(steamid);
    }

    public List<SteamProfileToFriendEntity> findAllAddedFriendsDesc(long steamid) {
        return steamProfileToFriendRepository.findAllAddedFriendsDesc(steamid);
    }


    public List<SteamProfileToFriendEntity> getSteamProfileToFriendOrderedByFriendsinceDate() {
        List<SteamProfileToFriendEntity> orderedList = steamProfileToFriendRepository.findByFriendsinceNotNullOrderByFriendsince();

        return orderedList;
    }

    public Object[][] getSteamProfileToFriendOrderedByFriendsinceDateTwo() {
        return steamProfileToFriendRepository.findByFriendsinceNotNullOrderByFriendsinceTwo();
    }

    /*
    public Object[] getSteamProfileToFriendOrderedByFriendsinceDateTwo() {
        return steamProfileToFriendRepository.findByFriendsinceNotNullOrderByFriendsinceTwo();
    }
    */

//    public int countAllRegisteredUsers() {
//        return steamProfileToFriendRepository.countAllBySteamprofileid();
//    }

    private Map<Long, SteamProfileToFriendEntity> convertToMap(Iterable<SteamProfileToFriendEntity> list) {
        HashMap<Long, SteamProfileToFriendEntity> map = new HashMap<>();
        for (SteamProfileToFriendEntity steamFriendEntity : list) {
            map.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity);
        }

        return map;
    }
}
