package com.steamstatistics.data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SteamProfileRepository extends CrudRepository<SteamProfileEntity, Long>{
    List<SteamProfileEntity> findByCreationdateGreaterThan(long epoch);

    @Query("SELECT s FROM SteamProfileEntity s WHERE NOT s.donationdate = 0 ORDER BY s.donationdate ASC")
    List<SteamProfileEntity> findSortedDonationList();

    SteamProfileEntity findBySteamid(long steamid);
}
