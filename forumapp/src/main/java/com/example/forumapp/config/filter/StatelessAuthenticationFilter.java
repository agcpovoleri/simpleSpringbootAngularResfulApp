package com.example.forumapp.config.filter;

import com.example.forumapp.dto.AuthenticatedUserResponse;
import com.example.forumapp.exception.UserNotLoggedException;
import com.example.forumapp.service.UserLoginService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

    public static final String TOKEN_KEY = "X-Auth-Token";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserLoginService service;

    @Autowired
    public StatelessAuthenticationFilter(UserLoginService service) {
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String token = httpRequest.getHeader(TOKEN_KEY);
        if (!Strings.isNullOrEmpty(token)) {
            final AuthenticatedUserResponse user;
            try {
                user = this.service.getAuthenticatedUserFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(user);

                service.refreshToken(token);

            } catch (UserNotLoggedException e) {
                logger.debug(String.format("> %s not logged: %s.", TOKEN_KEY, token));
            }
        }
        chain.doFilter(request, response);
    }
}