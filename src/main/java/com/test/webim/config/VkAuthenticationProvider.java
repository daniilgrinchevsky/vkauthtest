package com.test.webim.config;

import com.test.webim.model.Token;
import com.test.webim.model.User;
import com.test.webim.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class VkAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    public String authenticate(String tokenId) {
        Token token = tokenService.getById(tokenId);
        String username = null;
        if(token != null){
           username = token.getUser().getUsername();
        }
        return username;
    }
}
