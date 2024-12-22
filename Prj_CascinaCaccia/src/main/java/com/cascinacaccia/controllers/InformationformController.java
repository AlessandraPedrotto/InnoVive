package com.cascinacaccia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.services.InformationService;

@Controller
public class InformationformController {

	@Autowired
	private InformationService informationservice;

		@GetMapping("/informationform")
		public String informationForm(){
			return "InformationForm";
		}
}
		/*
		@GetMapping
		public List<Informationform> getAllInformationForms(){
			return informationservice.getAllInformationForms();
		}
		
			
}
   */

