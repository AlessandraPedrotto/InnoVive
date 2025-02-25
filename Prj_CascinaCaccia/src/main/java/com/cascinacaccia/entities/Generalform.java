package com.cascinacaccia.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * The Generalform entity represents the common information shared between the 
 * BookingForm and InformationForm entities. By centralizing these common fields,
 * data duplication is avoided in the database tables.
 */
@Entity
@Table(name = "general_form")
public class Generalform {
	
	//characteristics of the entity GeneralForm
	@Id
	private String id;
	private String name; 
	private String surname; 
	private String email; 
	
	//relationship with the Category entity
	@ManyToOne
	private Category category;
	
	//relationship with the InformationForm entity
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "generalform_id")
	public List<Informationform> informationForms;
	
	//relationship with the BookingForm entity
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "generalform_id")
	public List<BookingForm> bookingForms;
	
	private LocalDateTime submissionDate;
	
	//default constructor
    public Generalform() {
    }
	
    //parameterized constructor to initialize the fields.
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
    
    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

	public List<Informationform> getInformationForms() {
		return informationForms;
	}

	public void setInformationForms(List<Informationform> informationForms) {
		this.informationForms = informationForms;
	}

	public List<BookingForm> getBookingForms() {
		return bookingForms;
	}

	public void setBookingForms(List<BookingForm> bookingForms) {
		this.bookingForms = bookingForms;
	}
}