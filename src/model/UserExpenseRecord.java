package model;

import java.util.Date;

import utils.Cat;


public class UserExpenseRecord {
	
	private String userProfileID;
	private String date; 			//Format of date is yyyy-mm-dd
	private String category;
	private String vendor;
	private String description;
	private String amount;
	private String username;
	
		
	private Date jDate;				//Java Date object

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public Date getJDate() {
		return jDate;
	}

	public void setJDate(Date jDate) {
		this.jDate = jDate;
	}

	public String getUserProfileID() {
		return userProfileID;
	}

	public void setUserProfileID(String userProfileID) {
		this.userProfileID = userProfileID;
	}
	
	public String getCategoryID(){
		String result = null;
		if(category.equals("Food")){
			result = "1";
		}
		else if(category.equals("Rent")){
			result = "2";
		}
		else if(category.equals("Gas")){
			result = "3";
		}
		else if(category.equals("Transport")){
			result = "4";
		}
		else if(category.equals("Car Note")){
			result = "5";
		}
		else if(category.equals("Phone")){
			result = "6";
		}
		else if(category.equals("Misc")){
			result = "7";
		}
		else if(category.equals("Entertainment")){
			result = "8";
		}
		else if(category.equals("Electricity")){
			result = "9";
		}
		else if(category.equals("Internet")){
			result = "10";
		}
		
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
