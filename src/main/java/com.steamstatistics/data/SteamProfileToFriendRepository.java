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

    List<SteamProfileToFriendEntity> findByFriendsinceGreaterThan(long epoch);

    List<SteamProfileToFriendEntity> findByRemoveDateGreaterThan(long epoch);

    List<SteamProfileToFriendEntity> findAllBySteamprofileid(long steamprofileid);

    @Query("SELECT s FROM SteamProfileToFriendEntity s WHERE NOT s.steamprofileid = 0")
    List<SteamProfileToFriendEntity> findAllUsers();
}
