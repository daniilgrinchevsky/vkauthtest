package com.test.webim.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @Column(name = "id", nullable = false)
    @NotBlank
    private String id;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Token(){}

    public Token(String id, LocalDateTime expires, User user) {
        this.id = id;
        this.expires = expires;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", expires=" + expires +
                '}';
    }
}
