package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dataAccessor.UserProfileAccessor;

import model.UserProfile;

@ManagedBean
@SessionScoped
public class SettingsBean {
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String firstNameUpdate;
	private UserProfileAccessor upAccessor;
	
	
	public SettingsBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		UserProfile up = new UserProfile();
		upAccessor = new UserProfileAccessor();
		up.setUsername(username);
		upAccessor.getUserProfile(up);
		email = up.getEmail();
		firstName = up.getFirstName();
		lastName = up.getLastName();
	}
	
	public String updateFirstName(){
		firstName = firstNameUpdate;
		//code to update the firstname in the database
		return null;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFirstNameUpdate() {
		return firstNameUpdate;
	}

	public void setFirstNameUpdate(String firstNameUpdate) {
		this.firstNameUpdate = firstNameUpdate;
	}



}
