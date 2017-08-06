package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamFriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SteamHandlerMockup {

    public static final int N = 100;

    @Autowired
    TimeService timeService;

    @Autowired
    SteamAPICaller steamAPICaller;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Cacheable("friends")
    public Map<Long, SteamFriendEntity> getProfile(String apikey, long steamid) {
        Map<Long, SteamFriendEntity> map = new HashMap<>();

        for(int i = 0; i < N; i++) {
            SteamFriendEntity steamFriendEntity = createRandomSteamFriend(steamid);
            map.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity);
        }

        return map;
    }

    public Map<Long, SteamFriendEntity> processSteamProfiles(Long steamid, Map<Long, SteamFriendEntity> steamFriends) {
        for(SteamFriendEntity steamFriendEntity : steamFriends.values()) {
            processSteamProfile(steamid, steamFriendEntity);
        }

        return steamFriends;
    }

    public void processSteamProfile(Long steamid, SteamFriendEntity steamFriendEntity) {
        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setPersonaname("BOT" + steamFriendEntity.getSteamid());
        steamFriendEntity.setProfileurl("http://steamcommunity.com/profile/" + steamFriendEntity.getSteamid());
        steamFriendEntity.setAvatar("/css/img/defaultImage.jpg");
        steamFriendEntity.setAvatarmedium("/css/img/defaultImage.jpg");
        steamFriendEntity.setAvatarfull("/css/img/defaultImage.jpg");
        steamFriendEntity.setCommunityvisibilitystate("1");
        steamFriendEntity.setProfilestate("1");
        steamFriendEntity.setLastlogoff(1);
        steamFriendEntity.setLoccountrycode("NO");
        steamFriendEntity.setPersonastate("offline");
    }

    public String getCountryNameFromISO(String alpha2code) {
        Locale locale = new Locale("", alpha2code);

        return locale.getDisplayCountry();
    }

    private SteamFriendEntity createRandomSteamFriend(long steamid) {
        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        Random r = new Random();
        steamFriendEntity.setSteamfriendid(r.nextInt(1000000) + 101);
        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setFriendsince(r.nextInt(2073600) + 1499378214);

        return steamFriendEntity;
    }

}
