package controller;

import javax.faces.bean.ManagedBean;


import model.UserProfile;

import dataAccessor.DataAccessResult;
import dataAccessor.UserProfileAccessor;

@ManagedBean
public class RegisterUserBean {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String createUser(){
		DataAccessResult dar = new DataAccessResult();
		UserProfile up = new UserProfile();
		up.setUsername(username);
		up.setPassword(password);
		up.setEmail(email);
		up.setFirstName(firstName);
		up.setLastName(lastName);
		UserProfileAccessor upa = new UserProfileAccessor();
		dar = upa.createUser(up);
		
		
		if(dar.getStatus().contentEquals("Success")){

			return "home";
		}
		else{
			return "index";
		}
	
	}
	
	
	

}
