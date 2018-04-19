package com.mycompany.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Place {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @NotNull private String title;

    @NotNull private double latitude;
    @NotNull private double longitude;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Transaction> transactions;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;


    public Place(){}

    public Place(String title, double latitude, double longitude){
        this.title = title;

        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public JsonPlace toJson(){
        JsonPlace place = new JsonPlace();
        place.id = id;
        place.title = title;
        place.latitude = latitude;
        place.longitude = longitude;

        return place;
    }


    public class JsonPlace{
        String id;
        String title;
        double latitude;
        double longitude;
    }
}




