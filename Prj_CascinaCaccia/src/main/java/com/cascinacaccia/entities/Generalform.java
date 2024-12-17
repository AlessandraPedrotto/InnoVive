package com.cascinacaccia.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "general_form")
public class Generalform {

	@Id
	@Column(name = "id")
	private String id;
	
	
	@Column(name = "name")
	private String name; 
	
	@Column(name = "surname")
	private String surname; 
	
	@Column(name = "email")
	private String email; 
	
	
	@ManyToOne
	@JoinColumn(name="category_id",nullable = false)
	private Category category;
	
	@OneToOne(mappedBy = "generalForm", cascade = CascadeType.ALL)
    private InformationForm informationForm;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Generalform [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", surname=");
		builder.append(surname);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}
	
	
}
