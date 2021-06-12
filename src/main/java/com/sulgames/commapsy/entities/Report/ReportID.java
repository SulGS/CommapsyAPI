package com.sulgames.commapsy.entities.Report;

import java.io.Serializable;

public class ReportID implements Serializable{
	   String Mail;
	   Integer OpinionID;
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
}