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
	
	private Date start_date;
	
	private Date end_date;

	
	
	
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
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	
	
	
}
