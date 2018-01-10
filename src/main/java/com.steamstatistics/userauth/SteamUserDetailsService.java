package com.steamstatistics.userauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;

@Service
public class SteamUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${auth.admins}")
    private String[] admins;

    SecureRandom rng = new SecureRandom();

    @Override
    public UserPrincipal loadUserByUsername(String userToken) {
        User user = userRepository.findByUserToken(userToken);
        if (user == null) {
            throw new UsernameNotFoundException(userToken);
        }
        return new UserPrincipal(user);
    }

    public UserPrincipal findUserByToken(String userToken) {
        User user = userRepository.findByUserToken(userToken);
        if (user == null)
            return null;

        return new UserPrincipal(user);
    }

    public UserPrincipal findUserBySteamId(long steamId) {
        User user = userRepository.findBySteamId(steamId).orElse(null);
        if (user == null)
            return null;

        return new UserPrincipal(user);
    }

    public User registerNewUserAccount(long steamid) {
        User user = new User();
        String userToken = createUserToken();
        user.setSteamId(steamid);
        user.setUserToken(userToken);

        boolean isAdmin = false;
        for (String adminId : admins) {
            if (adminId.equals(Long.toString(steamid))) {
                isAdmin = true;
            }
        }

        if (isAdmin)
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        else
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        saveUser(user);

        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public String createUserToken() {
        byte bytes[] = new byte[128];
        rng.nextBytes(bytes);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
            stringBuffer.append(Integer.toHexString(bytes[i] & 0xFF));

        User taken = userRepository.findByUserToken(stringBuffer.toString());
        if (taken != null) {
            return createUserToken();
        } else {
            return stringBuffer.toString();
        }
    }
}
