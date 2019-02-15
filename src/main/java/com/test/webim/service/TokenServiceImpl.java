package com.test.webim.service;


import com.test.webim.model.Token;
import com.test.webim.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenServiceImpl {

    private final TokenRepo repo;

    @Autowired
    public TokenServiceImpl(TokenRepo repo) {
        this.repo = repo;
    }


    @Transactional
    public Token getById(String id) {
        Token token =  repo.getOne(id);
        if(isExpired(token)){
            repo.delete(token);
            return null;
        }
        return token;
    }

    @Transactional
    public boolean isExpired(Token token) {
        return token.getExpires().isBefore(LocalDateTime.now());
    }

    public void save(Token token) {
        repo.save(token);
    }
}
