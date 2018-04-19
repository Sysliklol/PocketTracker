package com.mycompany.services;

import com.mycompany.auth.RequestUser;
import com.mycompany.daos.PlaceDao;
import com.mycompany.daos.UserDao;
import com.mycompany.entities.Place;
import com.mycompany.entities.User;
import com.mycompany.pojos.AuthPOJO;
import com.mycompany.pojos.IdPOJO;
import com.mycompany.pojos.PlacePOJO;
import com.mycompany.pojos.UserPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {

    @Autowired
    PlaceDao placeDao;

    public Place find(String id){
        return this.placeDao.findById(id);
    }

    public Place create(User user, PlacePOJO payload){
        Place place = new Place(payload.title, payload.latitude, payload.longitude);
        place.setUser(user);

        return this.placeDao.savePlace(place);
    }

    public String delete(IdPOJO payload){
        Place place = this.find(payload.id);

        if(place == null){
            throw new Error("Empty place");
        }

        this.placeDao.deletePlace(place);

        return place.getId();
    }

}


