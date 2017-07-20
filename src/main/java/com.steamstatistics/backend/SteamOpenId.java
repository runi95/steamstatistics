package com.steamstatistics.backend;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SteamOpenId {

    private static final String OPENID_PROVIDER = "http://steamcommunity.com/openid";
    private final ConsumerManager manager = new ConsumerManager();
    private final Pattern REGEX_PATTERN = Pattern.compile("(\\d+)");
    private DiscoveryInformation discoveryInformation = null;

    @PostConstruct
    public void initializeDiscoveryInformation() throws DiscoveryException {
        manager.setMaxAssocAttempts(0);
        discoveryInformation = manager.associate(manager.discover(OPENID_PROVIDER));
    }

    public AuthRequest login(String callbackUrl) {
        if (this.discoveryInformation == null)
            return null;

        try {
            AuthRequest authReq = manager.authenticate(discoveryInformation, callbackUrl);

            return authReq;
        } catch (MessageException | ConsumerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String verify(String url, Map responseMap) {
        if (discoveryInformation == null)
            return null;

        ParameterList responseList = new ParameterList(responseMap);
        try {
            VerificationResult verification = manager.verify(url, responseList, discoveryInformation);
            Identifier verifiedId = verification.getVerifiedId();
            if (verifiedId != null) {
                String id = verifiedId.getIdentifier();
                Matcher matcher = REGEX_PATTERN.matcher(id);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        } catch (MessageException | DiscoveryException | AssociationException e) {
            //TODO: When steam servers are down this returns "org.openid4java.discovery.yadis.YadisException: 0x704: I/O transport error: Read timed out" on manager.verify
            e.printStackTrace();
        }
        return null;
    }
}
