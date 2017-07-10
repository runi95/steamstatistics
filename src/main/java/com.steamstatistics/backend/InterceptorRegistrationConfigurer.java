package com.steamstatistics.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.openid.OpenIDAuthenticationToken;

@Configuration
public class InterceptorRegistrationConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().openidLogin().loginPage("/login").permitAll().authenticationUserDetailsService(
                new AutoProvisioningUserDetailsService()).attributeExchange("http://steamcommunity.com/openid/id/.*").attribute("steamid")
                .type("http://steamcommunity.com/openid").required(true);
        */

        /*
        http.csrf().disable().authorizeRequests()
                .antMatchers("/**")
                .hasRole("USER")
                .and()
                .openidLogin()
                .loginPage("/auth/login")
                .permitAll()
                .authenticationUserDetailsService(
                        new AutoProvisioningUserDetailsService())
                .attributeExchange("http://steamcommunity.com/openid/id/.*").attribute("id").type("http://steamcommunity.com/openid/");
                */
    }
}

class AutoProvisioningUserDetailsService implements
        AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
            throws UsernameNotFoundException {
        return new User(token.getName(), "NOTUSED",
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}