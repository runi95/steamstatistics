package com.steamstatistics.controller;

import com.steamstatistics.data.SuggestionEntity;
import com.steamstatistics.data.SuggestionService;
import com.steamstatistics.steamapi.TimeService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpHeaders.USER_AGENT;

@Controller
public class IndexController {

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    @Autowired
    ControllerService controllerService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SuggestionService suggestionService;

    @Autowired
    TimeService timeService;

    @GetMapping(value = "/profile")
    public String getHomepage(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model) {
        controllerService.checkLogin(request, token, principal, model);

        return "profile";
    }

    @GetMapping(value = "/profile/{steamid}")
    public String getUserProfile(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model, @PathVariable String steamid) {
        controllerService.checkLogin(request, token, principal, model);
        model.addAttribute("request_steamid", steamid);

        return "profile";
    }

    @GetMapping(value = "/")
    public String getFrontpage(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model) {
        controllerService.checkLogin(request, token, principal, model);

        return "frontpage";
    }

    @GetMapping("/friends")
    public String getFriendslist(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model) {
        controllerService.checkLogin(request, token, principal, model);

        return "friendslist";
    }

    /*
    @GetMapping(value = "/ipn")
    public String testIPN() {
        return "ipn";
    }
    */

    @PostMapping("/suggestions")
    public String suggestions(Principal principal, @ModelAttribute("suggestionForm") SuggestionForm suggestionForm, BindingResult bindingResult) {

        if(principal != null) {
            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(principal.getName());

            if (suggestionForm.getCategory() != null && suggestionForm.getDescription() != null && !suggestionForm.getCategory().isEmpty() && !suggestionForm.getDescription().isEmpty()) {
                SuggestionEntity suggestionEntity = new SuggestionEntity();
                suggestionEntity.setSteamid(Long.toString(userPrincipal.getSteamId()));
                suggestionEntity.setTitle(suggestionForm.getTitle());
                suggestionEntity.setCategory(suggestionForm.getCategory());
                suggestionEntity.setDescription(suggestionForm.getDescription());
                suggestionEntity.setCreationDate(Long.toString(timeService.getCurrentUnixTime()));
                suggestionService.save(suggestionEntity);
            }
        }

        return "home";
    }

    /*
    @PostMapping(value = "/ipn")
    @ResponseStatus(value = HttpStatus.OK)
    public void getInstantPaymentNotification(@RequestParam Map<String,String> allRequestParam) throws Exception {
        // Return 200 OK.
        // Return addr https://ipnpb.paypal.com/cgi-bin/webscr

        //String url = "https://ipnpb.paypal.com/cgi-bin/webscr";
        String url = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        StringBuilder stringBuilder = new StringBuilder();
        for(String s : allRequestParam.keySet()) {
            if(!s.equals("_csrf"))
                stringBuilder.append("&" + s + "=" + allRequestParam.get(s));
        }

        String urlParameters = "cmd=_notify-validate" + stringBuilder.toString();

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        DataInputStream streamIn = new DataInputStream(con.getInputStream());
        int count = con.getInputStream().available();
        byte[] bs = new byte[count];
        streamIn.read(bs);
        streamIn.close();
        System.out.print("Verification : ");
        for (byte b:bs) {

            // convert byte into character
            char c = (char)b;

            // print the character
            System.out.print(c+" ");
        }
        System.out.println();
//        String verification = streamIn.readUTF();

        int responseCode = con.getResponseCode();
        String msg = con.getResponseMessage();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        System.out.println("Response Message : " + msg);
//        System.out.println("Verification : " + verification);
    }
    */
}