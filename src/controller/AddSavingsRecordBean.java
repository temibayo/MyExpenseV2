package controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dataAccessor.DataAccessResult;
import dataAccessor.UserSavingsRecordAccessor;
import model.UserSavingsRecord;

@ManagedBean
public class AddSavingsRecordBean {
	
	private Date date;
	private String source;
	private String notes;
	private String amount;
	private String username;
	private BigDecimal numericAmount;
	
	public AddSavingsRecordBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}
	
	
	public String addUserSavingsRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		try{
			numericAmount = new BigDecimal(amount); //Check if the entered value is of form x.xx
		}
		catch(NumberFormatException e){
			context.addMessage(null, new FacesMessage("Amount is invalid please re-enter"));
		}
		if(context.getMessageList().size()>0){
			return(null); //redisplay the jsf form with the error message
		}
		else{
			UserSavingsRecord usr = new UserSavingsRecord();
			UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
			usr.setAmount(amount);
			usr.setNotes(notes);
			usr.setSource(source);
			usr.setjDate(date);
			usr.setUsername(username);
			DataAccessResult dar = usrAccessor.addUserSavingsRecord(usr);
			if(dar.getStatus().equals("Success")){
				context.addMessage(null, new FacesMessage("SUCCESS: Savings record successfully added"));
				return (null);
			}else {
				return "addRecord";
			}
		}
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

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getNumericAmount(){
		return numericAmount;
	}
	
	
	

}
