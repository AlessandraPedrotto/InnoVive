package com.cascinacaccia.repos;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.entities.User;

/*
 * GeneralformDAO is the repository interface for accessing the Generalform entity in the database.
 * It extends JpaRepository to provide standard CRUD operations.
 * Additionally, it contains custom query methods to retrieve Generalform records based on specific criteria:
 * - Email
 * - Category
 * - Name
 * - Surname
 */
public interface GeneralformDAO extends JpaRepository<Generalform,String>{
	
	List<Generalform> findByEmailAndCategoryAndNameAndSurname(String email, Category category, String name, String surname);
	List<Generalform> findAll(Sort sort);
	List<Generalform> findAllById(Iterable<String> ids); 
	List<Generalform> findAllByInformationForms_AssignedUser(User user);
}
