package com.test.webim;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserTo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String vkId;

    private String firstName;

    private String lastName;

    public UserTo() {
    }

    public UserTo(Integer id, String username, String password, String vkId, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
