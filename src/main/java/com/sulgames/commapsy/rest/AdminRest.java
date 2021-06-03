package com.sulgames.commapsy.rest;

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

import com.sulgames.commapsy.entities.Admin.Admin;
import com.sulgames.commapsy.entities.Admin.AdminDAO;
import com.sulgames.commapsy.entities.Penalise.PenaliseDAO;
import com.sulgames.commapsy.entities.User.User;
import com.sulgames.commapsy.entities.User.UserDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("Admin")
public class AdminRest {

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PenaliseDAO penaliseDAO;

	public Admin getAdmin(String userMail) 
	{
		try {
			Admin admin = adminDAO.findById(userMail).get();

			return admin;
		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return null;
		}
	}



	@RequestMapping(value="login", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> login(@RequestBody String jsonBody) 
	{
		System.out.println(jsonBody);
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			Admin admin = getAdmin(jsonValues.getString("Mail"));
			User user = userDAO.getOne(jsonValues.getString("Mail"));
			
			if(admin==null) 
			{
				return ResponseEntity.ok(null);
			}
			
			if(user.getPassword().equals(jsonValues.getString("Password"))) 
			{
				if(penaliseDAO.getPenalisesFromUser(user.getMail(), PageRequest.of(0, 25)).size()>=3) 
				{
					admin.setUserMail("0");;
				}
				return ResponseEntity.ok(admin);
			}else 
			{
				return ResponseEntity.ok(null);
			}
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
		}

	}
	




}
