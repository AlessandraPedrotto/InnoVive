package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.User;

/*
 * The UserDAO interface extends JpaRepository to provide standard CRUD operations for the User entity.
 * This interface facilitates interaction with the User entity, offering various methods to perform CRUD operations
 * and custom queries based on user attributes such as name, surname, and email.
 */
public interface UserDAO extends JpaRepository<User, String>{
	
	public List<User> findAll();
	public boolean existsById(String Id);
	public boolean existsByEmail(String email);
	
	List<User> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);
	List<User> findByEmailContainingIgnoreCase(String email);
	List<User> findByNameContainingIgnoreCase(String name);
    List<User> findBySurnameContainingIgnoreCase(String surname);
    
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	Optional<User> findById(String Id);
}
