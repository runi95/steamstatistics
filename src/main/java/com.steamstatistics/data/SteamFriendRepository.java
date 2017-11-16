package com.steamstatistics.data;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamFriendRepository extends CrudRepository<SteamFriendEntity, Long>{
    SteamFriendEntity findBySteamid(long steamid);

    List<SteamFriendEntity> findBySteamidIsIn(Long[] steamidList);
    List<SteamFriendEntity> findBySteamidIsIn(List<Long> steamidList);
    List<SteamFriendEntity> findBySteamidIsInAndUpdatetimeLessThan(List<Long> steamidList, long updatetime);
    List<SteamFriendEntity> findBySteamidIsInAndUpdatetimeGreaterThan(List<Long> steamidList, long updatetime);
}
