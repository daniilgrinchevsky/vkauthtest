package com.test.webim.service;

import com.test.webim.AuthorizedUser;
import com.test.webim.Util;
import com.test.webim.repository.UserDetailsRepo;
import com.test.webim.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.test.webim.Util.send;
import static com.test.webim.Util.serialized;

@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepo repo;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepo repo){
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

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repo.getByUsername(authentication.getName());
    }

    public List<String> getVkFriends(String vkId, String accessToken) throws Exception{
        String getFriends = send(String.format(Util.GET_FRIENDS, vkId, accessToken));
        JSONObject serialized = serialized(getFriends);
        String response2 = serialized.get("response").toString();
        JSONObject serialized2 = serialized(response2);
        JSONArray items = (JSONArray) serialized2.get("items");
        List<String> friends = new ArrayList<>();
        items.forEach(obj -> friends.add(((JSONObject)obj).get("first_name").toString() + " "
                + ((JSONObject)obj).get("last_name").toString()));
        return friends;
    }
}
