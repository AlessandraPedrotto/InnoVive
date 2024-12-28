package com.cascinacaccia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="information_form")
public class Informationform {
	
	//characteristics of the entity InformationForm
	@Id
	private String id;
	
	@ManyToOne
	private Generalform generalform;
	
	private String content;
	
	//default constructor
    public Informationform() { 
    	
    }

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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}