package com.steamstatistics.data;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamFriendRepository extends CrudRepository<SteamFriendEntity, SteamFriendEntityKey>{
    List<SteamFriendEntity> findBySteamid(long steamid);
}
