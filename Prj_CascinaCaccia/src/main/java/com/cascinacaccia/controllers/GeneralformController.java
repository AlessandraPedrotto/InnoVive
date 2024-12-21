package com.cascinacaccia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.services.GeneralformService;


@Controller
public class GeneralformController {
	
	@Autowired
	private GeneralformService generalservice;
	
	@GetMapping("/generalform")
	public String generalForm(){
		return "GeneralForm";
	}
	/*
	public List<Generalform> getAllGeneralForms(){
		return generalservice.getAllGeneralForms();
	}
	
	public Generalform saveGeneralform(Generalform generalform) {
		return generalservice.saveGeneralform(generalform);
	}
	*/
	
	

}
