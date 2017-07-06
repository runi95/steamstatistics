package com.steamstatistics.services;

import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SteamProfileHandler {
    private static final String steamcommunity = "http://steamcommunity.com/profiles/", querystring = "?xml=1";

    public SteamProfileModel getProfile(String steamid) {
        SteamProfileModel steamProfileEntity = new SteamProfileModel();

        Element elem = parseProfile(steamid);

        NodeList nodeList = elem.getChildNodes();

        if(nodeList.item(0).getNodeName().equals("error"))
            return null;

        String steamName = nodeList.item(1).getTextContent();
        String privacyState = nodeList.item(4).getTextContent();
        String visibilityState = nodeList.item(5).getTextContent();
        String avatarFull = nodeList.item(8).getTextContent();

        steamProfileEntity.setSteamid(steamid);
        steamProfileEntity.setSteamName(steamName);
        steamProfileEntity.setPrivacyState(privacyState);
        steamProfileEntity.setVisibilityState(visibilityState);
        steamProfileEntity.setAvatarFull(avatarFull);

        return steamProfileEntity;
    }

    private Element parseProfile(String steamid) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
             builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(readProfile(steamid));

        ByteArrayInputStream input = null;
        try {
             input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(input);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return document.getDocumentElement();
    }

    private String readProfile(String steamid) {
        StringBuilder stringBuilder = new StringBuilder();

        URL url = null;
        try {
            url = new URL(steamcommunity + steamid + "/" + querystring);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try(
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
