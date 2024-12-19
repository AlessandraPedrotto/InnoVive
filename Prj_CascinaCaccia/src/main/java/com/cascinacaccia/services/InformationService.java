package com.cascinacaccia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Informationform;
import com.cascinacaccia.repos.InformationformDAO;

@Service
public class InformationService {

	@Autowired
	private InformationformDAO infodao;
	
	public List<Informationform> getAllInformationForms(){
		return infodao.findAll();
	}
	
	public Informationform saveInformationForm(Informationform informationform) {
		return infodao.save(informationform);
	}
}
