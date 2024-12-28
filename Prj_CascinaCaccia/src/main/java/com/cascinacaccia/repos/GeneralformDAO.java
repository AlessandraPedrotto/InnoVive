package com.cascinacaccia.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Category;
import com.cascinacaccia.entities.Generalform;

public interface GeneralformDAO extends JpaRepository<Generalform,String>{
	
	List<Generalform> findByEmailAndCategoryAndNameAndSurname(String email, Category category, String name, String surname);
}
