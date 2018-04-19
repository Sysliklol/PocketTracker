package com.mycompany.entities;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    private long createdAt;

    @NotNull
    private short quantity;

    @NotNull
    @Size(max = 60)
    private String title;


    @NotNull private TransactionImage image;
    @NotNull private long cost;

    @Null
    @Size(max = 255)
    private String description;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="placeId")
    private Place place;



    public Transaction(){}

    public Transaction(String title, long cost, TransactionImage image){
        this.title = title;
        this.cost = cost;
        this.image = image;

        this.createdAt = System.currentTimeMillis();
        this.quantity = 1;
    }

    public Transaction(String title, String description, long cost, TransactionImage image){
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.image = image;

        this.createdAt = System.currentTimeMillis();
        this.quantity = 1;
    }



    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public long getCost() {
        return this.cost * this.quantity;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public TransactionImage getImage() {
        return this.image;
    }

    public String getDescription() {
        return this.description;
    }



    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(TransactionImage image){
        this.image = image;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


    public Transaction.Json toJson(){
        Transaction.Json transaction = new Transaction.Json();
        transaction.id = id;
        transaction.title = title;
        transaction.description = description;
        transaction.cost = cost;
        transaction.createdAt = createdAt;
        transaction.quantity = quantity;
        transaction.image = image;
        transaction.placeId = place.getId();

        return transaction;
    }


    public class Json{
        String id;
        String title;
        String description;
        String placeId;
        long cost;
        long createdAt;
        short quantity;
        TransactionImage image;
    }

}
