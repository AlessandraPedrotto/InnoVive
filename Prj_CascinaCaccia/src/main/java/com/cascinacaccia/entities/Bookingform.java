package com.cascinacaccia.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="booking_form")
public class Bookingform {

	@Id
	private String id;
	
	@ManyToOne
	private Generalform generalform;
	
	private String content;
	
	private String status;
	
	private Date date;
	

	
	
	
	public Bookingform(String id, Generalform generalform, String content) {
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

	public Date getStart_date() {
		return date;
	}

	public void setStart_date(Date start_date) {
		this.date = start_date;
	}

	
	
	
	
	
}
