package com.cascinacaccia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascinacaccia.entities.Generalform;
import com.cascinacaccia.repos.GeneralformDAO;

@Service
public class GeneralformService {

	@Autowired
	private GeneralformDAO generaldao;
	
	public List<Generalform> getAllGeneralForms(){
		return generaldao.findAll();
	}
	
	public Generalform saveGeneralform(Generalform generalform) {
		return generaldao.save(generalform);
	}
	
	
	
}
