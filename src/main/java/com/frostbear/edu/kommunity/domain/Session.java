package com.frostbear.edu.kommunity.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class Session extends User {

    private final UUID idAccount;

    public Session(UUID idAccount, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idAccount = idAccount;
    }
}