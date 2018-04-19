package com.mycompany.auth;

import com.mycompany.entities.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RequestUser extends org.springframework.security.core.userdetails.User{

    private User user;

    public RequestUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(), user.getPasswordHash(), authorities);
        this.user = user;
    }

    public RequestUser(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(), user.getPasswordHash(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}