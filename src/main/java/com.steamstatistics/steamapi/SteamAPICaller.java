package com.steamstatistics.steamapi;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SteamAPICaller {

    JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();

    public List<Map<String, Object>> getFriendList(String apikey, long steamid) {
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

    public List<Map<String, Object>> getPlayerSummaries(String apikey, Map<Long, ?> steamids) {
        if (steamids == null || steamids.isEmpty())
            return new ArrayList<>();

        List<Map<String, Object>> friendsList = null;
        Iterator<Long> iterator = steamids.keySet().iterator();

        while(iterator.hasNext()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(iterator.next());

            int i = 0;
            while (iterator.hasNext() && i < 99) {
                stringBuilder.append("," + iterator.next());
                i++;
            }

            if(friendsList == null) {
                friendsList = getPlayerSummaries(apikey, stringBuilder.toString());
            } else {
                friendsList.addAll(getPlayerSummaries(apikey, stringBuilder.toString()));
            }
        }

        return friendsList;
    }

    public List<Map<String, Object>> getPlayerSummaries(String apikey, List<Long> steamids) {
        if (steamids == null || steamids.isEmpty())
            return new ArrayList<>();

        List<Map<String, Object>> friendsList = null;
        int j = 0;
        while(j < steamids.size()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 100 && j < steamids.size(); i++) {
                if(i == 0)
                    stringBuilder.append(steamids.get(j));
                else
                    stringBuilder.append("," + steamids.get(j));

                j++;
            }

            if(friendsList == null) {
                friendsList = getPlayerSummaries(apikey, stringBuilder.toString());
            } else {
                friendsList.addAll(getPlayerSummaries(apikey, stringBuilder.toString()));
            }
        }

        return friendsList;
    }

    public List<Map<String, Object>> getPlayerSummaries(String apikey, String steamid) {
        if (steamid == null || steamid.isEmpty())
            return new ArrayList<>();

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
