package com.sulgames.commapsy.entities.Contactform;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ContactFormDAO extends JpaRepository<ContactForm, Integer>{
	
	@Query(value = "SELECT c FROM ContactForm c WHERE c.Admin_Mail is null")
	public List<ContactForm> getNoReplyContactForms(Pageable pageable);
	
}
