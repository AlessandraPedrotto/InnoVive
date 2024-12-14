package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, String> {
	
	//custom queries for database operations (employees table)
	public List<Employee> findAll();
	
	Optional<Employee> findByEmail(String email);
	List<Employee> findByContainingIgnoreCase(String email);
	Optional<Employee> findBySurname(String surname);
	Optional<Employee> findById(String id);
	
	public boolean existsById(String id);
}
