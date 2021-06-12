package com.sulgames.commapsy.rest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import com.sulgames.commapsy.entities.Place.Place;
import com.sulgames.commapsy.entities.Place.PlaceDAO;
import com.sulgames.commapsy.entities.Report.Report;
import com.sulgames.commapsy.entities.Report.ReportDAO;
import com.sulgames.commapsy.entities.Report.ReportID;
import com.sulgames.commapsy.entities.User.User;
import com.sulgames.commapsy.entities.User.UserDAO;
import com.sulgames.commapsy.utils.Utils;

@RestController
@RequestMapping("Report")
public class ReportRest {

	@Autowired
	private ReportDAO reportDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private OpinionDAO opinionDAO;
	
	@Autowired
	private PlaceDAO placeDAO;
	
	@RequestMapping(value="register", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> register(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
				Report report = new Report();
				
				report.setMail(jsonValues.getString("Mail"));
				report.setOpinionID(Integer.parseInt(jsonValues.getString("OpinionID")));
				report.setReportComment(jsonValues.getString("Comment"));
				report.setSendDate(new Date(System.currentTimeMillis()));
				
				if(report.getMail().equals("")||report.getReportComment().equals("")) 
				{
					throw new Exception();
				}
				
				reportDAO.save(report);
			
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(false);
		}

	}
	
	@RequestMapping(value="get", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			List<Report> reports = reportDAO.findAll(PageRequest.of(Integer.parseInt(jsonValues.getString("Page"))*25, 25)).toList();
			
			String array = "[";
			
			
			int i = 0;
			
			for(Report report : reports) 
			{
				LocalDate ld = report.getSendDate().toLocalDate();
				array += "{";
				array += "\"mail\":\"" + report.getMail() + "\",";
				array += "\"opinionID\":\"" + report.getOpinionID() + "\",";
				array += "\"reportComment\":\"" + report.getReportComment() + "\",";
				array += "\"sendDate\":\"" + ld.getYear() + "-" + ld.getMonthValue() + "-" + ld.getDayOfMonth() + "\"}";
				
				if(i!=reports.size()-1) 
				{
					array += ",";
				}
				
				i++;
			}
			
			array += "]";
			
			System.out.println(array);
			
			return ResponseEntity.ok(array);
			

		}catch(Exception ex) 
		{
			ex.printStackTrace();
			return ResponseEntity.ok(null);
		}

	}
	
	@RequestMapping(value="manage", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> manage(@RequestBody String jsonBody) 
	{
		JsonObject jsonValues = Utils.stringToJson(jsonBody);
		
		try {
			
			ReportID rID = new ReportID();
			
			rID.setMail(jsonValues.getString("UserMail"));
			rID.setOpinionID(Integer.parseInt(jsonValues.getString("OpinionID")));
			
			if(rID.getMail().equals("")) 
			{
				throw new Exception();
			}
			
			Report report = reportDAO.getOne(rID);
			
			
			if(Boolean.parseBoolean(jsonValues.getString("Delete"))) 
			{
				Opinion opinion = opinionDAO.getOne(report.getOpinionID());
				Place place = placeDAO.getOne(opinion.getPlaceID());
				User user = userDAO.getOne(opinion.getUser_Mail());
				
				Utils.sendMail(user, "Valoracion eliminada", "Debido a diferentes quejas, su valoracion "
						+ "a " + place.getName() + " ha sido retirada");
				
				opinionDAO.delete(opinion);
				
			}
			
			
			reportDAO.delete(report);
			
			return ResponseEntity.ok(true);
			

		}catch(Exception ex) 
		{
			return ResponseEntity.ok(false);
		}

	}
	
	
	
	
}
