package com.sulgames.commapsy.entities.Admin;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {
	
	@Id
	@Column(name = "User_Mail", length = 320)
	private String UserMail;

	@Column(name = "Admin_By", length = 320)
	private String AdminBy;

	@Column(name = "Date", length = 320)
	private Date date;

	@Column(name = "Is_Enable", length = 320)
	private boolean enable;

	public String getUserMail() {
		return UserMail;
	}

	public void setUserMail(String userMail) {
		UserMail = userMail;
	}

	public String getAdminBy() {
		return AdminBy;
	}

	public void setAdminBy(String adminBy) {
		AdminBy = adminBy;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(java.util.Date replyDate) {
		date = new Date(replyDate.getTime());
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
