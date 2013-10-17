package model;

import java.util.Date;

public class UserSavingsRecord implements CommonRecord{
	private String date;
	private String source;
	private String notes;
	private String amount;
	private String userProfileID;
	private String username;
	
	private Date jDate;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
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
	public String getUserProfileID() {
		return userProfileID;
	}
	public void setUserProfileID(String userProfileID) {
		this.userProfileID = userProfileID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getjDate() {
		return jDate;
	}
	public void setjDate(Date jDate) {
		this.jDate = jDate;
	}

}
