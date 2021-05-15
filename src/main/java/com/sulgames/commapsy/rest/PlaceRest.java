package com.sulgames.commapsy.rest;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.json.JsonObject;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sulgames.commapsy.entities.Place.Place;
import com.sulgames.commapsy.entities.Place.PlaceDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("Place")
public class PlaceRest {

	
	@Autowired
	private PlaceDAO placeDAO;

	public Place getPlaceByID(int placeID) 
	{
		try {
			Place place = placeDAO.findById(placeID).get();
			

			return place;
		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return null;
		}
	}
	
	
	
	
	@RequestMapping(value="returnShortestPlace", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Place> returnShortestPlace(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			Place place = null;
			double shortest = 0;
			double lon = Double.parseDouble(jsonValues.getString("Longitude"));
			double lat = Double.parseDouble(jsonValues.getString("Latitude"));
			
			
			ArrayList<Place> places = placeDAO.getShortestPlaces(lat, lon);
			
			for(Place p : places) 
			{
				double diferenciaLatitud = Place.toRad(p.getLatitude() - lat);
	            double diferenciaLongitud = Place.toRad(p.getLongitude() - lon);

	            double l1 = Place.toRad(lat);
	            double l2 = Place.toRad(p.getLatitude());

	            double a = Math.pow(Math.sin(diferenciaLatitud / 2),2) + Math.cos(l1) * Math.cos(l2) * Math.pow(Math.sin(diferenciaLongitud / 2), 2);
	            double c = Math.asin(Math.sqrt(a));
	            
	            double result = 2 * 6371 * c;
	            
	            if(place!=null) 
	            {
	            	if(shortest>result) 
	            	{
	            		place = p;
		            	shortest = result;
	            	}
	            	
	            }else 
	            {
	            	place = p;
	            	shortest = result;
	            }
	            
	            
			}
			
			
			return ResponseEntity.ok(place);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(null);
		}

	}
	
	@RequestMapping(value="returnPlacesByName", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Place>> returnPlaceByName(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			ArrayList<Place> places = placeDAO.getPlacesByName(jsonValues.getString("Name"));
			
			
			
			
			return ResponseEntity.ok(places);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(null);
		}

	}
	
}
