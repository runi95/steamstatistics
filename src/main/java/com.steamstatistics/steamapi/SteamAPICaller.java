package com.steamstatistics.steamapi;

import org.springframework.boot.json.JacksonJsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SteamAPICaller {

    JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();

    public List<Map<String, Object>> getFriendList(String apikey, String steamid) {
        String read = readUrl("http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=" + apikey + "&steamid=" + steamid + "&relationship=all");

        Map<String, Object> json = parseJson(read);
        Map<String, Object> friendslist = (Map) json.get("friendslist");
        List<Map<String, Object>> friends = (List) friendslist.get("friends");

        /*
        for(Map<String, Object> map : friends) {
            for(String s : map.keySet()) {
                System.out.println(s + ": " + map.get(s));
            }
        }
        */

        return friends;
    }

    public List<Map<String, Object>> getPlayerSummaries(String apikey, String[] steamids) {
        if (steamids == null || steamids.length < 1)
            return null;

        int index = 0;
        List<Map<String, Object>> friendsList = null;

        while(index < steamids.length) {
            String parsedSteamids = steamids[index];

            for (int i = 1 + index; i < steamids.length && i < 100 + index; i++) {
                parsedSteamids += "," + steamids[i];
            }
            if(friendsList == null) {
                friendsList = getPlayerSummaries(apikey, parsedSteamids);
            } else {
                friendsList.addAll(getPlayerSummaries(apikey, parsedSteamids));
            }

            index += 100;
        }

        return friendsList;
    }

    public List<Map<String, Object>> getPlayerSummaries(String apikey, String steamid) {
        if (steamid == null || steamid.isEmpty())
            return null;

        String read = readUrl("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + apikey + "&steamids=" + steamid);

        Map<String, Object> json = parseJson(read);
        Map<String, Object> response = (Map) json.get("response");
        List<Map<String, Object>> players = (List) response.get("players");

        /*
        for(Map<String, Object> map : players) {
            for(String s : map.keySet()) {
                System.out.println(s + ": " + map.get(s));
            }
        }
        */

        return players;
    }

    public Map<String, Object> parseJson(String json) {
        return jacksonJsonParser.parseMap(json);
    }

    public String readUrl(String link) {
        StringBuilder stringBuilder = new StringBuilder();

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
        ) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine + '\r');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
