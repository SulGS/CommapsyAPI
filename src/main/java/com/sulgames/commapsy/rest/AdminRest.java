package com.sulgames.commapsy.rest;

import java.util.Date;
import java.util.List;
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
	
	@RequestMapping(value="getAdmins", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Admin>> getAdmins(@RequestBody String jsonBody) 
	{
		System.out.println(jsonBody);
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			List<Admin> admins = adminDAO.findAll(PageRequest.of(Integer.parseInt(jsonValues.getString("Page"))*25, 25)).toList();
			
			return ResponseEntity.ok(admins);

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
		}

	}
	
	@RequestMapping(value="getUsers", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsers(@RequestBody String jsonBody) 
	{
		System.out.println(jsonBody);
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			List<User> users = userDAO.findAll(PageRequest.of(Integer.parseInt(jsonValues.getString("Page"))*25, 25)).toList();
			
			return ResponseEntity.ok(users);

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
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
					return ResponseEntity.ok(null);
				}
				
				if(!admin.isEnable()) 
				{
					return ResponseEntity.ok(null);
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
	
	@RequestMapping(value="manage", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> manage(@RequestBody String jsonBody) 
	{
		System.out.println(jsonBody);
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			Admin admin = getAdmin(jsonValues.getString("Mail"));
			
			if(admin==null) 
			{
				
				admin = new Admin();
				
				admin.setUserMail(jsonValues.getString("Mail"));
				admin.setAdminBy(jsonValues.getString("AdminMail"));
				admin.setEnable(true);
				admin.setDate(new Date(System.currentTimeMillis()));
				
				if(admin.getUserMail().equals("")||admin.getAdminBy().equals("")) 
				{
					throw new Exception();
				}
				
			}else 
			{
				
				admin.setEnable(!admin.isEnable());
				
			}
			
			adminDAO.save(admin);
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	




}
