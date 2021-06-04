package com.sulgames.commapsy.entities.Report;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "Report")
@IdClass(ReportID.class)
public class Report {
	
	

	@Id
	@Column(name = "User_Mail", length = 320)
	private String Mail;
	
	@Id
	@Column(name = "Opinionid")
	private Integer OpinionID;
	
	@Column(name = "Reportcomment", length = 320)
	private String ReportComment;
	
	@Column(name = "Senddate")
	private Date SendDate;

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public Integer getOpinionID() {
		return OpinionID;
	}

	public void setOpinionID(Integer opinionID) {
		OpinionID = opinionID;
	}

	public String getReportComment() {
		return ReportComment;
	}

	public void setReportComment(String reportComment) {
		ReportComment = reportComment;
	}

	public Date getSendDate() {
		return SendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		SendDate = new Date(sendDate.getTime());
	}
	
	
	
}



