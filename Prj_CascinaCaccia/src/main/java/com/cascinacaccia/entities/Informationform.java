package com.cascinacaccia.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="information_form")
public class Informationform {

	@Id
	@Column(name = "id")
	private String id;
	
	@OneToOne
	@JoinColumn(name="general_form_id", nullable = false)
	private Generalform generalform;
	
	@Column(name="content")
	private String content;

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

	public Informationform(String id, Generalform generalform, String content) {
		this.id = id;
		this.generalform = generalform;
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Informationform [id=");
		builder.append(id);
		builder.append(", generalform=");
		builder.append(generalform);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
