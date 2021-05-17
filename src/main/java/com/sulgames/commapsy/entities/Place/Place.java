package com.sulgames.commapsy.entities.Place;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Place")
public class Place {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer ID;
	
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

	public static double toRad(double d) 
	{
		return (Math.PI / 180) * d;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
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
	
}
