package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dataAccessor.UserProfileAccessor;

@ManagedBean
@SessionScoped
public class UserProfileBean implements Serializable {
	
	private String username;
	private int userID;
	
	public UserProfileBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		userID = getUserID(username);
	}
	
	private int getUserID(String username){
		
		UserProfileAccessor upaAccessor = new UserProfileAccessor();
		return upaAccessor.getUserProfileID(username);
	}

	public String getUsername() {
		return username;
	}

	public int getUserID() {
		return userID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
