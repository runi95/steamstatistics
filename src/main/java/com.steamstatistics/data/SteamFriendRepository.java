package com.steamstatistics.data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamFriendRepository extends CrudRepository<SteamFriendEntity, SteamFriendEntityKey>{
    List<SteamFriendEntity> findBySteamid(long steamid);
    SteamFriendEntity findBySteamidAndSteamfriendid(long steamid, long steamfriendid);
    List<SteamFriendEntity> findBySteamfriendid(long steamfriendid);

    @Query("SELECT s FROM SteamFriendEntity s WHERE s.steamid = ?1 AND s.removeDate = 0")
    List<SteamFriendEntity> findAllUnremovedFriends(long steamid);

    @Query("SELECT s FROM SteamFriendEntity s WHERE s.steamid = ?1 AND NOT s.removeDate = 0")
    List<SteamFriendEntity> findByRemoveDateNotNullAndSteamidOrderByRemoveDateDesc(long steamid);
}
