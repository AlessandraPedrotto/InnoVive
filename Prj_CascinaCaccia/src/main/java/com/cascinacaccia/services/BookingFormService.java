package com.cascinacaccia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Bookingform;
import com.cascinacaccia.repos.BookingformDAO;

@Service
public class BookingFormService {

	
	@Autowired
	private BookingformDAO bookingdao;
	
	public List<Bookingform> getAllBookingforms(){
		return bookingdao.findAll();
	}
	
	public Optional<Bookingform> getBookingFormById(String id){
		return bookingdao.findById(id);
	}
	
	
	public Bookingform saveBookingform(Bookingform bookingform) {
		return bookingdao.save(bookingform);
	}
	
	public void deleteBookingform(String id) {
		bookingdao.deleteById(id);
	}
}
