package com.sulgames.commapsy.entities.Penalise;

import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PenaliseDAO extends JpaRepository<Penalise, Integer>{

	@Query(value = "SELECT p FROM Penalise p WHERE p.User = ?1")
	public ArrayList<Penalise> getPenalisesFromUser(String mail, Pageable pageable);
	
}
