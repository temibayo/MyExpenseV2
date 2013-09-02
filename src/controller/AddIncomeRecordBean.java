package controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dataAccessor.DataAccessResult;
import dataAccessor.UserIncomeRecordAccessor;

import model.UserIncomeRecord;

@ManagedBean
public class AddIncomeRecordBean {
	
	private Date date;
	private String source;
	private String notes;
	private String amount;
	private String username;
	private BigDecimal numericAmount;
	
	public AddIncomeRecordBean(){
		
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getNumericAmount(){
		return numericAmount;
	}
	public String addUserIncomeRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		try{
			numericAmount = new BigDecimal(amount);
		}
		catch(NumberFormatException e){
				context.addMessage(null, new FacesMessage("Amount is invalid please re-enter"));
		}
		if(context.getMessageList().size()>0){
			return(null); //redisplay the form with the error message
		}
		else{
			UserIncomeRecord uir = new UserIncomeRecord();
			UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
			DataAccessResult dar = new DataAccessResult();
			uir.setjDate(date);
			uir.setSource(source);
			uir.setNotes(notes);
			uir.setAmount(amount);
			uir.setUsername(username);
			dar = uirAccessor.addNewUserIncomeRecord(uir);
		
			if(dar.getStatus().equals("Success")){
				context.addMessage(null, new FacesMessage("SUCCESS: Income record has been added"));
				return (null);
			}
			else {
				return "addRecord";
			}
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
