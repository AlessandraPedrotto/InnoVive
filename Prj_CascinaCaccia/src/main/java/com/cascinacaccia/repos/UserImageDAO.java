package com.cascinacaccia.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.UserImage;

//the UserImageDAO interface provides standard methods for CRUD operations (e.g., save, delete)
public interface UserImageDAO extends JpaRepository<UserImage, Long>{
	
	public List<UserImage> findAll();
}
