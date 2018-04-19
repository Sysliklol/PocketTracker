package com.mycompany.daos;

import com.mycompany.entities.Place;
import com.mycompany.entities.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class PlaceDao{

    @PersistenceContext
    private EntityManager em;

    public Place findById(String id){
        return this.em.find(Place.class, id);
    }

    public Place savePlace(Place place){
        this.em.persist(place);
        return place;
    }

    public String deletePlace(Place entity){
        this.em.remove(em.contains(entity) ? entity : em.merge(entity));
        return entity.getId();
    }
}
