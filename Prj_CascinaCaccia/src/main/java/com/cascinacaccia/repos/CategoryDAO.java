package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Category;

//the CategoryDAO interface provides standard methods for CRUD operations (e.g., save, delete)
public interface CategoryDAO extends JpaRepository<Category, String>{
	
    List<Category> findByName(String name);
    Optional<Category> findById(String id);
}
