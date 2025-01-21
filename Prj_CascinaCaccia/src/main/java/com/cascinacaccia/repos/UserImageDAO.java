package com.cascinacaccia.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.UserImage;

/*
 * The UserImageDAO interface extends JpaRepository to provide standard CRUD operations for the UserImage entity.
 * It includes methods for saving, deleting, and querying user images in the database. The repository allows querying by:
 * - Id (primary key)
 * - ImgPath (image path)
 */
public interface UserImageDAO extends JpaRepository<UserImage, Long>{
	
	public List<UserImage> findAll();
}
