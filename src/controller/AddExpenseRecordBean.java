package controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dataAccessor.DataAccessResult;
import dataAccessor.UserExpenseRecordAccessor;
import model.UserExpenseRecord;

@ManagedBean
public class AddExpenseRecordBean {
	
	private String category;
	private String vendor;
	private String description;
	private String amount;
	private String username;
	private BigDecimal numericAmount;
	
	private Date date;
	
	public AddExpenseRecordBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	public String addUserExpenseRecord(){
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
			UserExpenseRecord uer = new UserExpenseRecord();
			UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
			DataAccessResult dar = new DataAccessResult();
			uer.setJDate(date);
			uer.setCategory(category);
			uer.setVendor(vendor);
			uer.setDescription(description);
			uer.setAmount(amount);
			uer.setUsername(username);
			dar = uerAccessor.addNewUserExpenseRecord(uer);
			if(dar.getStatus().contentEquals("Success")){
				context.addMessage(null, new FacesMessage("SUCCESS: Expense record has been added"));
				return (null);
			}
			else {
				return "addRecord";
			}
		}
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
