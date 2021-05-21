package com.sulgames.commapsy.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sulgames.commapsy.entities.Place.Place;
import com.sulgames.commapsy.entities.Place.PlaceDAO;
import com.sulgames.commapsy.entities.PlaceRequest.PlaceRequest;
import com.sulgames.commapsy.entities.PlaceRequest.PlaceRequestDAO;
import com.sulgames.commapsy.entities.User.User;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("PlaceRequest")
public class PlaceRequestRest {

	
	@Autowired
	private PlaceRequestDAO placeRequestDAO;

	public PlaceRequest getPlaceByID(int placeID) 
	{
		try {
			PlaceRequest place = placeRequestDAO.findById(placeID).get();
			

			return place;
		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return null;
		}
	}
	
	
	@RequestMapping(value="register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> register(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
				PlaceRequest pReq = new PlaceRequest();

				pReq.setUserMail(jsonValues.getString("UserMail"));
				pReq.setSendDate(new Date(System.currentTimeMillis()));
				pReq.setPlaceID(Integer.parseInt(jsonValues.getString("PlaceID")));
				pReq.setLatitude(Double.parseDouble(jsonValues.getString("Latitude")));
				pReq.setLongitude(Double.parseDouble(jsonValues.getString("Longitude")));
				pReq.setName(jsonValues.getString("Name"));
				pReq.setPhoto("images/places/default.png");
				pReq.setDescription(jsonValues.getString("Description"));
				pReq.setCategory(jsonValues.getString("Category"));
				pReq.setIsAccepted(false);
				
				placeRequestDAO.customSave(pReq.getUserMail(), pReq.getSendDate(),
						pReq.getPlaceID(),pReq.getLatitude(), pReq.getLongitude(),
						pReq.getPhoto(), pReq.getName(), pReq.getDescription(), pReq.getCategory());
				
				
			
			
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	
	
	
	
}