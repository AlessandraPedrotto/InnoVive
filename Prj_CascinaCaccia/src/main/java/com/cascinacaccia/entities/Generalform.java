package com.cascinacaccia.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "general_form")
public class Generalform {
	
	//characteristics of the entity GeneralForm
	@Id
	private String id;
	private String name; 
	private String surname; 
	private String email; 
	
	@ManyToOne
	private Category category;
	
	private LocalDateTime submissionDate;
	
	//default constructor
    public Generalform() {
    }
	
	public Generalform(String id, String name, String surname, String email, Category category) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.category = category;
		this.submissionDate = LocalDateTime.now();
	}
	
	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public LocalDateTime getsubmissionDate() {
        return submissionDate;
    }

    public void setsubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}