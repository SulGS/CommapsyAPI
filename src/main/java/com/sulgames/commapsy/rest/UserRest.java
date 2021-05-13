package com.sulgames.commapsy.rest;

import java.util.NoSuchElementException;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sulgames.commapsy.entities.User.User;
import com.sulgames.commapsy.entities.User.UserDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("User")
public class UserRest {

	@Autowired
	private UserDAO userDAO;

	public User getUser(String userMail) 
	{
		try {
			User user = userDAO.findById(userMail).get();

			return user;
		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return null;
		}
	}



	@RequestMapping(value="login", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> login(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			if(user==null) 
			{
				return ResponseEntity.ok(false);
			}
			
			if(user.getPassword().equals(jsonValues.getString("Password"))) 
			{
				return ResponseEntity.ok(true);
			}else 
			{
				return ResponseEntity.ok(false);
			}
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(false);
		}

	}






}
