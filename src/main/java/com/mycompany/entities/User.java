package com.mycompany.entities;

import com.mycompany.utils.Validations;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@NotNull private String passwordHash;

	@NotNull
	@Size(max = 255)
	@Pattern(regexp = Validations.emailRegex)
	private String email;

	@NotNull
	@Size(max = 255)
	private String firstName;

	@NotNull
	@Size(max = 255)
	private String lastName;

	@NotNull private UserRole role;


	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Transaction> transactions;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Place> places;



	public User(){}
	
	public User(String email){
		this.email = email;
		this.role = UserRole.User;
	}

	public User(String email, String passwordHash, UserRole role){
		this.email = email;
		this.role = role;
		this.passwordHash = passwordHash;
	}



	public String getName(){
		return this.firstName + ' ' + this.lastName;
	}
	
	public String getEmail(){
		return this.email;
	}

	public UserRole getRole() {
		return role;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
}
