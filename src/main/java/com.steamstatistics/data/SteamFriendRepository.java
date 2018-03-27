package com.steamstatistics.data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamFriendRepository extends CrudRepository<SteamFriendEntity, Long>{
    SteamFriendEntity findBySteamid(long steamid);

    List<SteamFriendEntity> findBySteamidIsIn(Long[] steamidList);
    List<SteamFriendEntity> findBySteamidIsIn(List<Long> steamidList);
    List<SteamFriendEntity> findBySteamidIsInAndUpdatetimeLessThan(List<Long> steamidList, long updatetime);
    List<SteamFriendEntity> findBySteamidIsInAndUpdatetimeGreaterThan(List<Long> steamidList, long updatetime);
    List<SteamFriendEntity> findAllByUpdatetimeLessThan(long updatetime);

    @Query("SELECT s.steamid FROM SteamFriendEntity s WHERE s.updatetime > ?1")
    List<Long> findAllByUpdatetimeGreaterThanAndReturnId(long updatetime);

    @Query(nativeQuery = true, value = "SELECT * FROM steamfriend WHERE personname ~* ?1")
    List<SteamFriendEntity> findByPersonanameMatchesRegex(String match);
}
