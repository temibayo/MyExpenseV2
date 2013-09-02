package controller;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dataAccessor.DataAccessResult;
import dataAccessor.UserExpenseRecordAccessor;
import dataAccessor.UserIncomeRecordAccessor;

import model.UserHistory;

@ManagedBean
public class UserHistoryBean {
	
	private List<UserHistory> userIncomeHistory;
	private List<UserHistory> userExpenseHistory;
	private String username;
	
	
	public UserHistoryBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		userIncomeHistory = new LinkedList<UserHistory>();
		userExpenseHistory = new LinkedList<UserHistory>();
		DataAccessResult dar = uirAccessor.getUserIncomeHistory(userIncomeHistory, username);
		DataAccessResult dar2 = uerAccessor.getUserExpenseHistory(userExpenseHistory, username);
				
		
		
	}

	public List<UserHistory> getUserIncomeHistory() {
		return userIncomeHistory;
	}

	public void setUserIncomeHistory(List<UserHistory> userIncomeHistory) {
		this.userIncomeHistory = userIncomeHistory;
	}

	public List<UserHistory> getUserExpenseHistory() {
		return userExpenseHistory;
	}

	public void setUserExpenseHistory(List<UserHistory> userExpenseHistory) {
		this.userExpenseHistory = userExpenseHistory;
	}

}
