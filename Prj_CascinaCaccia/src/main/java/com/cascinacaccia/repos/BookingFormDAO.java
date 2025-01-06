package com.cascinacaccia.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cascinacaccia.entities.BookingForm;
import com.cascinacaccia.entities.User;

/*
 * The BookingFormDAO interface extends JpaRepository to provide standard CRUD operations for the BookingForm entity.
 * This interface allows interaction with the BookingForm entity, providing basic operations like saving, deleting, and querying information forms.
 */
public interface BookingFormDAO extends JpaRepository<BookingForm,String> {

	Optional<BookingForm> findById(String id);
	
	List<BookingForm> findByAssignedUser(User assignedUser);
}
