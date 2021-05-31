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

import com.sulgames.commapsy.entities.Contactform.ContactForm;
import com.sulgames.commapsy.entities.Contactform.ContactFormDAO;
import com.sulgames.commapsy.entities.User.UserDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("ContactForm")
public class ContactFormRest {

	@Autowired
	private ContactFormDAO cfDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> register(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			ContactForm cf = new ContactForm();
			cf.setUser_Mail(jsonValues.getString("UserMail"));
			cf.setSubject(jsonValues.getString("Subject"));
			cf.setBody(jsonValues.getString("Body"));
			cf.setSendDate(new Date(System.currentTimeMillis()));
			
			cfDAO.save(cf);
			
			
			return ResponseEntity.ok(true);
			

		}catch(NoSuchElementException | NullPointerException ex) 
		{
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="get", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactForm>> get(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			List<ContactForm> prs = cfDAO.getNoReplyContactForms(PageRequest.of(Integer.parseInt(jsonValues.getString("Page"))*25, 25));
			
			
			
			return ResponseEntity.ok(prs);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
		}

	}
	
	@RequestMapping(value="answer", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> answer(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			ContactForm cf = cfDAO.getOne(Integer.parseInt(jsonValues.getString("ID")));
			
			if(cf==null) 
			{
				return ResponseEntity.ok(false);
			}else 
			{
				cf.setAdmin_Mail(jsonValues.getString("Mail"));
				cf.setReplyDate(new Date(System.currentTimeMillis()));
				
				if(Boolean.parseBoolean(jsonValues.getString("SendMail"))) 
				{
					cf.setReplyBody(jsonValues.getString("Body"));
					
					Utils.sendMail(userDAO.getOne(cf.getUser_Mail()), "RE: " + cf.getSubject(),
							"Respecto a su duda: " + cf.getReplyBody());
					
				}
				
				cfDAO.save(cf);
			}
			
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	
	
}
