package com.cascinacaccia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * The UserImage entity is used to store the images available for the profile of the user.
 * It contains its unique identifier (ID)
 * and the file path (imgPath) where the image link is stored.
 */
@Entity
public class UserImage {
	
	//characteristics of the entity userImage
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String imgPath;
	
	//getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
