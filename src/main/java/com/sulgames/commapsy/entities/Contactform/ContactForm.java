package com.sulgames.commapsy.entities.Contactform;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Contactform")
public class ContactForm {

	@Id
	@Column(name = "ID", nullable = true)
	@GeneratedValue
	private Integer ID;
	
	@Column(name = "User_Mail", nullable = false, length = 320)
	private String User_Mail;
	
	@Column(name = "Subject", nullable = false, length = 40)
	private String Subject;
	
	@Column(name = "Body", nullable = false, length = 320)
	private String Body;
	
	@Column(name = "Senddate", nullable = false)
	private Date SendDate;
	
	@Column(name = "Admin_Mail", nullable = true, length = 320)
	private String Admin_Mail;
	
	@Column(name = "Replybody", nullable = true, length = 320)
	private String ReplyBody;
	
	@Column(name = "Replydate", nullable = true)
	private Date ReplyDate;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUser_Mail() {
		return User_Mail;
	}

	public void setUser_Mail(String user_Mail) {
		User_Mail = user_Mail;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public Date getSendDate() {
		return SendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		SendDate = new Date(sendDate.getTime());
	}

	public String getAdmin_Mail() {
		return Admin_Mail;
	}

	public void setAdmin_Mail(String admin_Mail) {
		Admin_Mail = admin_Mail;
	}

	public String getReplyBody() {
		return ReplyBody;
	}

	public void setReplyBody(String replyBody) {
		ReplyBody = replyBody;
	}

	public Date getReplyDate() {
		return ReplyDate;
	}

	public void setReplyDate(java.util.Date replyDate) {
		ReplyDate = new Date(replyDate.getTime());
	}
	
	
	
	
	
	
	
	
	
	
	
}
