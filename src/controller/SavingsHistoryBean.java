package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import model.UserHistory;

import dataAccessor.UserSavingsRecordAccessor;


@ManagedBean
public class SavingsHistoryBean {
	
	private List<UserHistory> userSavingsHistory;
	private String username;
	
	
	public SavingsHistoryBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		userSavingsHistory = new ArrayList<UserHistory>();
		UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
		usrAccessor.getUserSavingsHistory(userSavingsHistory, username);
		
		//Sort the list by Date
		Collections.sort(userSavingsHistory);
		
	}

	public List<UserHistory> getUserSavingsHistory() {
		return userSavingsHistory;
	}

	public void setUserSavingsHistory(List<UserHistory> userSavingsHistory) {
		this.userSavingsHistory = userSavingsHistory;
	}
	
}
