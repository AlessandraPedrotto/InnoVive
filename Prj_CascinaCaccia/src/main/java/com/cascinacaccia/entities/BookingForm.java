package com.cascinacaccia.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/*
 * The BookingForm entity represents the parts of a form specific to booking-related details, 
 * distinguishing it from the InformationForm entity. It includes fields for managing 
 * booking-specific data such as check-in and check-out dates, while sharing common 
 * data with the Generalform entity.
 */
@Entity
@Table(name="booking_form")
public class BookingForm {

	//characteristics of the entity BookingForm
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
	
	@Temporal(TemporalType.DATE)
	private Date checkIn;
	
	@Temporal(TemporalType.DATE)
	private Date checkOut;
	
	//default constructor
    public BookingForm() { 
    	
    }
    
    //parameterized constructor to initialize the fields
	public BookingForm(String id, Generalform generalform, String content) {
		super();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
}
