package com.mycompany.services;

import com.mycompany.auth.RequestUser;
import com.mycompany.daos.UserDao;
import com.mycompany.entities.Place;
import com.mycompany.entities.Transaction;
import com.mycompany.entities.User;
import com.mycompany.pojos.AuthPOJO;
import com.mycompany.pojos.UserPOJO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    @Qualifier("mailService")
    MailService mailService;

    public User authenticateUser(AuthPOJO payload){
        User user = this.userDao.findByEmail(payload.email);

        if(user == null){
            throw new Error("No user with such email");
        }

        if(!new BCryptPasswordEncoder().matches(payload.password, user.getPasswordHash())){
            throw new Error("Password is incorrect");
        }

        return user;
    }

    public void verifyById(String userId){
        User user = this.userDao.findById(userId);

        if(user == null){
            throw new Error("No user with such id");
        }

        if(user.isEnabled()){
            throw new Error("User is enabled");
        }

        user.setEnabled(true);

        this.userDao.update(user);
    }


    public User createUser(UserPOJO payload){
        User user = this.userDao.findByEmail(payload.email);

        if(user != null){
            throw new Error("User with such email already exists");
        }

        User toCreate = new User(payload.email);
        toCreate.setEnabled(false);
        toCreate.setFirstName(payload.firstName);
        toCreate.setLastName(payload.lastName);
        toCreate.setPasswordHash(this.encryptPassword(payload.password));

        this.userDao.saveUser(toCreate);

        mailService.sendMail(
                "Vkharko1@gmail.com",
                payload.email,
                "Pocket Tracker Verification",
                "Welcome to Pocket Tracker ! "+
                 "Please follow this link for verification !"+
                 "https://pocketracker.herokuapp.com/user/verify/"+ toCreate.getId()
        );

        return toCreate;
    }

    @Transactional
    public List<Place.JsonPlace> loadPlaces(User user){
        user = this.userDao.findById(user.getId());

        Hibernate.initialize(user.getPlaces());

        ArrayList<Place.JsonPlace> result = new ArrayList<>();
        for(Place place: user.getPlaces()) {
            result.add(place.toJson());
        }

        return result;
    }

    @Transactional
    public List<Transaction.Json> loadTransactions(User user){
        user = this.userDao.findById(user.getId());

        Hibernate.initialize(user.getTransactions());

        ArrayList<Transaction.Json> result = new ArrayList<>();
        for(Transaction trn: user.getTransactions()) {
            result.add(trn.toJson());
        }

        return result;
    }


    private String encryptPassword(String password){
        return  new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);

        return buildUserForAuthentication(user);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new RequestUser(user,user.isEnabled(), true, true, true,authorities);
    }


}


