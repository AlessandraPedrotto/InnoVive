package com.cascinacaccia.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.Bookingform;

public interface BookingformDAO extends JpaRepository<Bookingform,String> {

	
	Optional<Bookingform> findById(String id);
}
