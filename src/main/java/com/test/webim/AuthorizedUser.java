package com.test.webim;

import com.test.webim.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user){
        super(user.getUsername(), user.getPassword(), true, true, true, true, user.getRoles());
        this.userTo = new UserTo(user.getId(),user.getUsername(),user.getPassword(),user.getVkId(), user.getFirstName(), user.getLastName());
    }

    public int getId(){
        return userTo.getId();
    }

    public void update(UserTo newTo){
        userTo = newTo;
    }

    public UserTo getUserTo(){
        return userTo;
    }

    @Override
    public String toString(){
        return userTo.toString();
    }
}
