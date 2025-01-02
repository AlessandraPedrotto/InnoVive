package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.entities.User;

/*
 * The InformationformDAO interface extends JpaRepository to provide standard CRUD operations for the Informationform entity.
 * This interface allows interaction with the Informationform entity, providing basic operations like saving, deleting, and querying information forms.
 */
public interface InformationformDAO extends JpaRepository<Informationform,String>{
	
	Optional<Informationform> findById(String id);
	
	List<Informationform> findByAssignedUser(User assignedUser);
}
