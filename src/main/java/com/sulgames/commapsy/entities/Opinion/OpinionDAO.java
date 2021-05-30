package com.sulgames.commapsy.entities.Opinion;


import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface OpinionDAO  extends JpaRepository<Opinion, Integer>{

	@Query(value = "SELECT o FROM Opinion o WHERE o.User_Mail = ?1 and o.PlaceID = ?2")
	public Opinion exists(String n, int ID);
	
	@Query(value = "SELECT p FROM Opinion p WHERE p.PlaceID = ?1")
	public ArrayList<Opinion> getOpinionsByPlaces(int id, Pageable pageable);
}
