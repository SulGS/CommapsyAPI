package com.sulgames.commapsy.entities.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {

	@Id
	@Column(name = "Mail", length = 320)
	private String Mail;
	
	@Column(name = "Name", nullable = false, length = 60)
	private String Name;
	
	@Column(name = "Surname", nullable = false, length = 300)
	private String Surname;
	
	@Column(name = "Password", nullable = false, length = 64)
	private String Password;
	
	@Column(name = "_Key", nullable = false, length = 64)
	private String _Key;
	
	@Column(name = "Is_Enable", nullable = false)
	private boolean Is_Enable;
	
	@Column(name = "Profile_Photo", nullable = false, length = 200)
	private String Profile_Photo;
	
	@Column(name = "Gender", nullable = false, length = 6)
	private String Gender;

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String get_Key() {
		return _Key;
	}

	public void set_Key(String _Key) {
		this._Key = _Key;
	}

	public boolean isIs_Enable() {
		return Is_Enable;
	}

	public void setIs_Enable(boolean is_Enable) {
		Is_Enable = is_Enable;
	}

	public String getProfile_Photo() {
		return Profile_Photo;
	}

	public void setProfile_Photo(String profile_Photo) {
		Profile_Photo = profile_Photo;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}
	
	
}
