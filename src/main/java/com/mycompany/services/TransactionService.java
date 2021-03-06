package com.mycompany.services;

import com.mycompany.daos.TransactionDao;
import com.mycompany.entities.Place;
import com.mycompany.entities.Transaction;
import com.mycompany.entities.User;
import com.mycompany.pojos.IdPOJO;
import com.mycompany.pojos.TransactionPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService{

    @Autowired
    TransactionDao transactionDao;


    public Transaction find(String id){
        return this.transactionDao.findById(id);
    }

    public Transaction create(User user, Place place, TransactionPOJO payload){
        Transaction trn = new Transaction();

        trn.setCost(payload.cost);
        trn.setTitle(payload.title);
        trn.setImage(payload.image);
        trn.setQuantity(payload.quantity);
        trn.setDescription(payload.description);

        trn.setUser(user);
        trn.setPlace(place);

        trn.setCreatedAt(System.currentTimeMillis());

        return  this.transactionDao.saveTransaction(trn);
    }

    public String delete(IdPOJO payload){
        Transaction transaction = this.find(payload.id);

        if(transaction == null){
            throw new Error("Empty transaction");
        }

        return this.transactionDao.deleteTransaction(transaction);
    }

}


