package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.User;

//the UserDAO interface provides standard methods for CRUD operations (e.g., save, delete)
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
