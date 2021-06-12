package com.sulgames.commapsy.entities.Penalise;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Penalise")
public class Penalise {

	
	@Id
	@Column(name = "ID", nullable = true)
	@GeneratedValue
	private Integer ID;
	
	@Column(name = "Usermail", nullable = false, length = 320)
	private String User;
	
	@Column(name = "Adminmail", nullable = false, length = 320)
	private String Admin;

	@Column(name = "Reason", nullable = false, length = 320)
	private String Reply;
	
	@Column(name = "Ban_Date", nullable = false)
	private Date SendDate;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getAdmin() {
		return Admin;
	}

	public void setAdmin(String admin) {
		Admin = admin;
	}

	public String getReply() {
		return Reply;
	}

	public void setReply(String reply) {
		Reply = reply;
	}

	public Date getSendDate() {
		return SendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		SendDate = new Date(sendDate.getTime());
	}
	
	
}
