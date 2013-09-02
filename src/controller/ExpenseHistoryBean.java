package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dataAccessor.DataAccessResult;
import dataAccessor.UserExpenseRecordAccessor;

import model.UserHistory;

@ManagedBean
public class ExpenseHistoryBean {
	
	private String username;
	private List<UserHistory> userExpenseHistory;
	private List<UserHistory> filteredUserExpenseHistory;

	
	public ExpenseHistoryBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		userExpenseHistory = new ArrayList<UserHistory>();
		DataAccessResult dar = uerAccessor.getUserExpenseHistory(userExpenseHistory, username);
		
		//Sort the result by Date
		Collections.sort(userExpenseHistory);
	}


	public List<UserHistory> getUserExpenseHistory() {
		return userExpenseHistory;
	}


	public void setUserExpenseHistory(List<UserHistory> userExpenseHistory) {
		this.userExpenseHistory = userExpenseHistory;
	}


	public List<UserHistory> getFilteredUserExpenseHistory() {
		return filteredUserExpenseHistory;
	}


	public void setFilteredUserExpenseHistory(
			List<UserHistory> filteredUserExpenseHistory) {
		this.filteredUserExpenseHistory = filteredUserExpenseHistory;
	}

}
