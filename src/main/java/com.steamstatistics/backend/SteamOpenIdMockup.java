package com.steamstatistics.backend;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SteamOpenIdMockup {

    public String login(String callbackUrl) {

        return callbackUrl;
    }

    public String verify(String url, Map responseMap) {
        return "100";
    }
}
