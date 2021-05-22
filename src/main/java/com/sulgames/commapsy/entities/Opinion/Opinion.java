package com.sulgames.commapsy.entities.Opinion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Opinion")
public class Opinion {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer ID;
	
	@Column(name = "User_Mail", nullable = false, length = 320)
	private String User_Mail;
	
	@Column(name = "PlaceID", nullable = false)
	private Integer PlaceID;
	
	@Column(name = "Rating", nullable = false, length = 10)
	private Integer Rating;
	
	@Column(name = "Comment", nullable = false, length = 320)
	private String Comment;
	
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

	public Integer getPlaceID() {
		return PlaceID;
	}

	public void setPlaceID(Integer placeID) {
		PlaceID = placeID;
	}

	public Integer getRating() {
		return Rating;
	}

	public void setRating(Integer rating) {
		Rating = rating;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	
	
	
	
}

