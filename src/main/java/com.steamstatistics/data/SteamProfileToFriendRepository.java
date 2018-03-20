package com.steamstatistics.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamProfileToFriendRepository  extends CrudRepository<SteamProfileToFriendEntity, SteamProfileToFriendEntityKey> {

    SteamProfileToFriendEntity findBySteamprofileidAndSteamfriendid(long steamprofileid, long steamfriendid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND s.removeDate = 0 ORDER BY s.friendsince DESC")
    List<SteamProfileToFriendEntity> findAllUnremovedFriends(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND s.removeDate = 0 AND s IN :steamidList ORDER BY s.friendsince DESC")
    List<SteamProfileToFriendEntity> compareFriendsList(List<SteamProfileToFriendEntity> steamidList);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND NOT s.removeDate = 0")
    List<SteamProfileToFriendEntity> findByRemoveDateNotNullAndSteamidOrderByRemoveDateDesc(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE NOT s.steamprofileid = 0 AND s.removeDate = 0 ORDER BY s.friendsince ASC")
    List<SteamProfileToFriendEntity> findByFriendsinceNotNullAndDescOrder();

    @Query("SELECT s.steamprofileid FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND s.removeDate = 0 AND NOT s.friendsince = 0 ORDER BY s.friendsince DESC")
    List<Long> findByFriendsinceNotNullAndDescOrderAndReturnLong();

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE NOT s.friendsince = 0 ORDER BY s.friendsince")
    List<SteamProfileToFriendEntity> findByFriendsinceNotNullOrderByFriendsince();

    @Query("SELECT DISTINCT(s.steamprofileid), MIN(s.friendsince) FROM SteamProfileToFriendEntity s GROUP BY s.steamprofileid ORDER BY MIN(s.friendsince)")
    Object[][] findByFriendsinceNotNullOrderByFriendsinceTwo();

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND NOT s.friendsince = 0 ORDER BY s.friendsince ASC")
    List<SteamProfileToFriendEntity> findAllAddedFriends(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND NOT s.friendsince = 0 ORDER BY s.friendsince DESC")
    List<SteamProfileToFriendEntity> findAllAddedFriendsDesc(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1 AND NOT s.friendsince = 0 AND s.removeDate = 0 ORDER BY s.friendsince DESC")
    List<SteamProfileToFriendEntity> findAllAddedFriendsDescAndRemoveDateIsNull(long steamprofileid);

    @Query("SELECT DISTINCT(s.steamprofileid), COUNT(s) FROM SteamProfileToFriendEntity s WHERE s.friendsince > ?1 AND NOT s.removeDate > 0 GROUP BY s.steamprofileid ORDER BY COUNT(s) DESC")
    Object[][] findByFriendsinceGreaterThanTwo(long epoch);

    List<SteamProfileToFriendEntity> findByFriendsinceGreaterThan(long epoch);

    List<SteamProfileToFriendEntity> findByFriendsinceGreaterThanAndSteamprofileid(long epoch, long profileid);

    List<SteamProfileToFriendEntity> findByRemoveDateGreaterThan(long epoch);

    List<SteamProfileToFriendEntity> findByRemoveDateGreaterThanAndSteamprofileid(long epoch, long profileid);

    List<SteamProfileToFriendEntity> findAllBySteamprofileid(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE NOT s.steamprofileid = 0")
    List<SteamProfileToFriendEntity> findAllUsers();

    @Query("SELECT s.steamfriendid FROM SteamProfileToFriendEntity s WHERE s.steamprofileid = ?1")
    List<Long> findAllFriendsSteamIds(long steamid);
}
