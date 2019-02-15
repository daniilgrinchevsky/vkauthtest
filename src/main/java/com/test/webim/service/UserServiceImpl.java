package com.test.webim.service;

import com.test.webim.AuthorizedUser;
import com.test.webim.repository.UserDetailsRepo;
import com.test.webim.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("userService")
public class UserServiceImpl implements UserDetailsService {

    private final UserDetailsRepo repo;

    @Autowired
    public UserServiceImpl(UserDetailsRepo repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = repo.getByUsername(username);
            if(user == null)
                throw new UsernameNotFoundException("User " + username + "is not found");
            return new AuthorizedUser(user);
    }

    public User create(User user) {
        Assert.notNull(user, "User must not be null");
        return repo.save(user);
    }

    public User getById(int id) {
        return repo.getById(id);
    }

    public User getByVkId(String id) {
        if (id != null && !id.isEmpty())
            return repo.getByVkId(id);
        else
            return null;

    }
}
