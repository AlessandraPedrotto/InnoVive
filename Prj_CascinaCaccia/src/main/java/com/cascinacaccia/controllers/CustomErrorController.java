package com.cascinacaccia.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class CustomErrorController {
	
	//handles requests to the URL ("/error")
	@GetMapping("/error")
	public String handleError() {
		
		return "Error";
	}
}
