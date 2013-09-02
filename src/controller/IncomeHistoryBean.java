package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;

import dataAccessor.DataAccessResult;
import dataAccessor.UserIncomeRecordAccessor;

import model.UserHistory;

@ManagedBean
public class IncomeHistoryBean {
	private String username;
	private List<UserHistory> userIncomeHistory;
	private List<UserHistory> filteredUserIncomeHistory;
	
	//These fields only used for the Select filters
	private String year;
	private String month;
	
	public IncomeHistoryBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		userIncomeHistory = new ArrayList<UserHistory>();
		DataAccessResult dar = uirAccessor.getUserIncomeHistory(userIncomeHistory, username);
		
		//Sort the list by Date
		Collections.sort(userIncomeHistory);
	}
	
	public void updateUserIncomeHistory(ActionEvent event){
		userIncomeHistory = new ArrayList<UserHistory>();
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		uirAccessor.getUserIncomeHistoryByYearAndMonth(userIncomeHistory, year, month, username);
		
		Collections.sort(userIncomeHistory);
	
		
	}
	
	 public void onEdit(RowEditEvent event) {  
	    FacesContext context = FacesContext.getCurrentInstance();
		String amount = ((UserHistory)event.getObject()).getAmount();
		String recordID = ((UserHistory)event.getObject()).getId();
		BigDecimal formattedAmount = new BigDecimal(0);
		try{
			formattedAmount = new BigDecimal(amount);
		}
		catch (NumberFormatException e){
			context.addMessage(null, new FacesMessage("Amount is invalid please re-enter"));
		}
		//if the new amount entered was successfully formatted, update the record
		if(context.getMessageList().size()==0){
			UserHistory incomeRecord = new UserHistory();
			incomeRecord.setAmount(formattedAmount.toString());
			incomeRecord.setId(recordID);
			
			UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor(); 
			uirAccessor.updateUserIncomeRecord(incomeRecord, username); 
		}
	 }  
	      
    public void onCancel(RowEditEvent event) {  
	      //  FacesMessage msg = new FacesMessage("Car Cancelled", ((Car) event.getObject()).getModel());  
	      //FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
    public void onRowToggle(ToggleEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,  
                                            "Row State " + event.getVisibility(),  
                                            "Model:" );  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
	public List<UserHistory> getUserIncomeHistory() {
		return userIncomeHistory;
	}

	public void setUserIncomeHistory(List<UserHistory> userIncomeHistory) {
		this.userIncomeHistory = userIncomeHistory;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<UserHistory> getFilteredUserIncomeHistory() {
		return filteredUserIncomeHistory;
	}

	public void setFilteredUserIncomeHistory(
			List<UserHistory> filteredUserIncomeHistory) {
		this.filteredUserIncomeHistory = filteredUserIncomeHistory;
	}
	
}
