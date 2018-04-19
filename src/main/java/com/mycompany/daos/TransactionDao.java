package com.mycompany.daos;

import com.mycompany.entities.Place;
import com.mycompany.entities.Transaction;
import com.mycompany.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class TransactionDao{

    @PersistenceContext
    private EntityManager em;

    public Transaction findById(String id){
        return this.em.find(Transaction.class, id);
    }

    public Transaction saveTransaction(Transaction transaction){
        this.em.persist(transaction);
        return transaction;
    }


    public String deleteTransaction(Transaction entity){
        this.em.remove(em.contains(entity) ? entity : em.merge(entity));
        return entity.getId();
    }
}
