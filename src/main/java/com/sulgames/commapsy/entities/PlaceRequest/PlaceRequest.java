package com.sulgames.commapsy.entities.PlaceRequest;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PlaceRequest")
public class PlaceRequest {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer ID;
	
	@Column(name = "UserMail", nullable = false, length = 320)
	private String UserMail;
	
	@Column(name = "SendDate")
	private Date SendDate;
	
	@Column(name = "PlaceID")
	private Integer PlaceID;
	
	@Column(name = "Latitude", nullable = false)
	private Double Latitude;
	
	@Column(name = "Longitude", nullable = false)
	private Double Longitude;
	
	@Column(name = "Name", nullable = false, length = 200)
	private String Name;
	
	@Column(name = "Photo", nullable = false, length = 200)
	private String Photo;
	
	@Column(name = "Description", nullable = false, length = 320)
	private String Description;
	
	@Column(name = "Category", nullable = false, length = 40)
	private String Category;
	
	@Column(name = "Admin_Mail", nullable = true, length = 320)
	private String Admin_Mail;
	
	@Column(name = "IsAccepted", nullable = true)
	private boolean IsAccepted;
	
	@Column(name = "ReplyBody", nullable = true, length = 320)
	private String ReplyBody;
	
	@Column(name = "ReplyDate", nullable = true)
	private Date ReplyDate;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUserMail() {
		return UserMail;
	}

	public void setUserMail(String userMail) {
		UserMail = userMail;
	}

	public Date getSendDate() {
		return SendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		SendDate = new Date(sendDate.getTime());
	}

	public Integer getPlaceID() {
		return PlaceID;
	}

	public void setPlaceID(Integer placeID) {
		PlaceID = placeID;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getAdmin_Mail() {
		return Admin_Mail;
	}

	public void setAdmin_Mail(String admin_Mail) {
		Admin_Mail = admin_Mail;
	}

	public boolean isIsAccepted() {
		return IsAccepted;
	}

	public void setIsAccepted(boolean isAccepted) {
		IsAccepted = isAccepted;
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
