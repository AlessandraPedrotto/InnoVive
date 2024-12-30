package com.cascinacaccia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	//characteristics of the entity role
	@Id
	private String id;
	private String name;
	
	//default constructor
	public Role() {}
	
	//constructor that initializes the 'id' and 'name' fields
	public Role(String id, String name) {
        this.id = id;
        this.name = name;
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
}
