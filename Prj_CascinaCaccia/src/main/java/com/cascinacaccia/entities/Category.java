package com.cascinacaccia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * The Category entity represents a table containing all possible categories
 * that can be associated with the booking form and information form.
 * Each category has a unique identifier (ID) and a name.
 */
@Entity
@Table(name="category")
public class Category {

	//characteristics of the entity category
	@Id
	private String id;
	private String name;
	
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
}
