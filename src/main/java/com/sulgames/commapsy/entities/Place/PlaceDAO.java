package com.sulgames.commapsy.entities.Place;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PlaceDAO extends JpaRepository<Place, Integer>{

	@Query(value = "SELECT p FROM Place p WHERE abs(p.Latitude - ?1) < 0.01 and abs(p.Longitude - ?2) < 0.01")
	public ArrayList<Place> getShortestPlaces(double lat1,double lon1);
	
	@Query(value = "SELECT p FROM Place p WHERE p.Name like %?1%")
	public ArrayList<Place> getPlacesByName(String n);

}
