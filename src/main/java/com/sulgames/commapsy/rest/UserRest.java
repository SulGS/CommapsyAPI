package com.sulgames.commapsy.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.NoSuchElementException;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping(value="getUser", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserRequest(@RequestBody String jsonBody) 
	{
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			return ResponseEntity.ok(user);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
		}

	}



	@RequestMapping(value="login", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody String jsonBody) 
	{
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			if(user==null) 
			{
				return ResponseEntity.ok(null);
			}
			
			if(user.getPassword().equals(jsonValues.getString("Password"))) 
			{
				return ResponseEntity.ok(user);
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
	
	@RequestMapping(value="adminLogin", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> adminLogin(@RequestBody String jsonBody) 
	{
		System.out.println(jsonBody);
		
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			if(user==null) 
			{
				return ResponseEntity.ok(null);
			}
			
			if(user.getPassword().equals(jsonValues.getString("Password"))) 
			{
				return ResponseEntity.ok(user);
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
	
	@RequestMapping(value="register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> register(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			
			if(user!=null) 
			{
				return ResponseEntity.ok(false);
			}else 
			{
				user = new User();
				
				user.setMail(jsonValues.getString("Mail"));
				user.setName(jsonValues.getString("Name"));
				user.setSurname(jsonValues.getString("Surname"));
				user.setPassword(jsonValues.getString("Password"));
				user.setIs_Enable(false);
				user.setProfile_Photo(jsonValues.getString("Profile_Photo"));
				user.setGender(jsonValues.getString("Gender"));
				
				String key = Utils.generateRandomKey();
				
				
				user.set_Key(Utils.hashString(key));
				
				userDAO.save(user);
				
				Utils.sendMail(user, "Clave de activacion de la cuenta", "Esta es su clave de activacion: " + key);
				
				
				
				
			}
			
			
			return ResponseEntity.ok(true);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="validate", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validate(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			
			if(user==null) 
			{
				return ResponseEntity.ok(false);
			}else 
			{
				if(user.get_Key().equals(jsonValues.getString("Key"))) 
				{
					user.setIs_Enable(true);
					userDAO.save(user);
					Utils.sendMail(user, "Activacion completada", "Su cuenta ha sido activada. Gracias por confiar en nosotros");
				}else
				{
					return ResponseEntity.ok(false);
				}
				
				
			}
			
			
			return ResponseEntity.ok(true);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="requestPasswordChange", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> requestPasswordChange(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			
			if(user==null) 
			{
				return ResponseEntity.ok(false);
			}else 
			{
				String key = Utils.generateRandomKey();
				
				user.set_Key(Utils.hashString(key));
				
				userDAO.save(user);
				
				Utils.sendMail(user, "Peticion de cambio de contraseña", "La clave para el cambio de contraseña es la siguiente: " + key);

				
			}
			
			
			return ResponseEntity.ok(true);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="changePassword", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> changePassword(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			User user = getUser(jsonValues.getString("Mail"));
			
			
			if(user==null) 
			{
				return ResponseEntity.ok(false);
			}else 
			{
				if(user.get_Key().equals(jsonValues.getString("Key"))) 
				{
					user.setPassword(jsonValues.getString("Password"));
					
					userDAO.save(user);
					
					Utils.sendMail(user, "Operacion realizada con exito", "La contraseña se ha cambiado con exito");
					return ResponseEntity.ok(true);
				}else 
				{
					return ResponseEntity.ok(false);
				}
				
				
				
			}
			
			
			
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(false);
		}

	}
	
	
	
	@RequestMapping(value="savePhoto", method=RequestMethod.POST, consumes = "multipart/form-data",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> savePhoto(@RequestPart("Name") String fileName, @RequestPart("File") MultipartFile file) 
	{
		
		
		try {
			
			file.transferTo(new File("C:\\xampp\\htdocs\\images\\profiles\\" + fileName + ".png"));
			
			User user = getUser(fileName);
			
			user.setProfile_Photo("images/profiles/" + fileName + ".png");
			
			userDAO.save(user);
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}






}
