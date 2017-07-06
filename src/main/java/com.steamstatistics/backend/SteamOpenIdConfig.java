package com.steamstatistics.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SteamOpenIdConfig {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.accessTokenUri}")
    private String accessTokenUri;

    @Value("${auth.userAuthorizationUri}")
    private String userAuthorizationUri;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessTokenUri() { return accessTokenUri; }

    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }
}
