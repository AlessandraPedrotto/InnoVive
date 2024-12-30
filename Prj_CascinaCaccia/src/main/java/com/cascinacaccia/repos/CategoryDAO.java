package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Category;

/*
 * The CategoryDAO interface extends JpaRepository to provide standard CRUD operations for the Category entity.
 * It also includes custom query methods for searching categories based on their:
 * - Name
 * - Id
 */
public interface CategoryDAO extends JpaRepository<Category, String>{
	
    List<Category> findByName(String name);
    Optional<Category> findById(String id);
}
