package com.steamstatistics.data;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SteamProfileRepository extends CrudRepository<SteamProfileEntity, Long>{
    Optional<SteamProfileEntity> findBySteamurl(String steamurl);
}
