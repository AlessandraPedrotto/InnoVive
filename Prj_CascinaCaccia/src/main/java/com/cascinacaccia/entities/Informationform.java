package com.cascinacaccia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * The Informationform entity represents the specific parts of a form that are unique 
 * to information-related details, distinguishing it from the BookingForm entity. 
 * This entity contains the additional fields and relationships not shared with 
 * the Generalform, allowing flexibility and separation of concerns.
 */
@Entity
@Table(name="information_form")
public class Informationform {
	
	//characteristics of the entity InformationForm
	@Id
	private String id;
	
	//relationship with the Generalform entity
	@ManyToOne
	private Generalform generalform;
	
	private String content;
	
	//relationship with the User entity
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User assignedUser;
	
	private String status;
	
	//default constructor
    public Informationform() { 
    	
    }

    //parameterized constructor to initialize the fields
    public Informationform(String id, Generalform generalform, String content) {
        this.id = id;
        this.generalform = generalform;
        this.content = content;
    }
    
    //getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Generalform getGeneralform() {
        return generalform;
    }

    public void setGeneralform(Generalform generalform) {
        this.generalform = generalform;
    }
	
    public String getGeneralFormId() {
        return generalform != null ? generalform.getId() : null;
    }
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}