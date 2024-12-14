package com.cascinacaccia.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Role;

public interface RoleDAO extends JpaRepository<Role, String>{

}
