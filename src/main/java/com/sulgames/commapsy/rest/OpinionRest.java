package com.sulgames.commapsy.rest;

import java.util.ArrayList;
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

import com.sulgames.commapsy.entities.Opinion.Opinion;
import com.sulgames.commapsy.entities.Opinion.OpinionDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("Opinion")
public class OpinionRest {

	@Autowired
	private OpinionDAO opinionDAO;

	public Opinion getOpinionByID(int id) 
	{
		try {
			Opinion opinion = opinionDAO.findById(id).get();


			return opinion;
		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return null;
		}
	}

	@RequestMapping(value="exists", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> exists(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);

		try {
			Opinion opinion = opinionDAO.exists(jsonValues.getString("Mail"),Integer.parseInt(jsonValues.getString("PlaceID")));
			
			System.out.println(opinion);
			
			if(opinion==null) 
			{
				return ResponseEntity.ok(true);
			}else 
			{
				return ResponseEntity.ok(false);
			}
		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="returnOpinions", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Opinion>> returnOpinions(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			ArrayList<Opinion> opinions = opinionDAO.getOpinionsByPlaces(Integer.parseInt(jsonValues.getString("PlaceID")),PageRequest.of(Integer.parseInt(jsonValues.getString("Page"))*25, 25));
			
			return ResponseEntity.ok(opinions);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(null);
		}

	}


	@RequestMapping(value="register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> register(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);

		try {
			Opinion opinion = new Opinion();

			opinion.setUser_Mail(jsonValues.getString("UserMail"));
			opinion.setPlaceID(Integer.parseInt(jsonValues.getString("PlaceID")));
			opinion.setRating((int)(Float.parseFloat(jsonValues.getString("Rating"))*2));
			opinion.setComment(jsonValues.getString("Comment"));

			opinionDAO.save(opinion);



			return ResponseEntity.ok(true);


		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}



}
