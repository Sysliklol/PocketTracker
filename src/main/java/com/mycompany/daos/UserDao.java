package com.mycompany.daos;

import com.mycompany.entities.User;
import com.mycompany.pojos.UserPOJO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class UserDao{

    @PersistenceContext
    private EntityManager em;

    public void refreshUser(User user){
        this.em.refresh(user);
    }

    public User saveUser(User user){
        this.em.persist(user);
        return user;
    }

    public User findById(String id){
        return em.find(User.class, id);
    }

    public User findByEmail(String email){
        User user = null;

        Query query = em.createQuery("SELECT user From User user WHERE user.email = :email", User.class);
        query.setParameter("email", email);
        List list= query.getResultList();

        if(list.size() > 0){
            user = (User) list.get(0);
        }

        return user;
    }


}
