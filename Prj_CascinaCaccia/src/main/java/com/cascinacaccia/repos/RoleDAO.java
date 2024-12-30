package com.cascinacaccia.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;

/*
 * The RoleDAO interface extends JpaRepository to provide standard CRUD operations for the Role entity.
 * It includes methods for saving, deleting, and querying roles in the database by using:
 * - Name
 * - Id
 */
public interface RoleDAO extends JpaRepository<Role, String>{
	
	Optional<User> findByName(String name);
}
