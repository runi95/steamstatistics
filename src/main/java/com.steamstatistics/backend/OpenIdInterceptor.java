package com.steamstatistics.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OpenIdInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ServletContext context;

    @Autowired
    private OpenIdSession openIdSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String curretUserId = openIdSession.getSteamId();
        if(curretUserId != null && !curretUserId.isEmpty()) {
            //Session is set so we can proceed
            return true;
        }
        else {
            // Stop the chain and return a redirect
            String contextPath = context.getContextPath();
            response.sendRedirect(contextPath+"/auth/login");
            return false;
        }
    }
}
