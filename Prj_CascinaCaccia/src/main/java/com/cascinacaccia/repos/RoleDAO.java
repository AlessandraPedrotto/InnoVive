package com.cascinacaccia.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Role;
import com.cascinacaccia.entities.User;

//the RoleDAO interface provides standard methods for CRUD operations (e.g., save, delete)
public interface RoleDAO extends JpaRepository<Role, String>{
	
	Optional<User> findByName(String name);
}
