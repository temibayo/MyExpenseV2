package model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserHistory implements Comparable<UserHistory> {
//Region IncomeHistory, SavingsHistory and ExpenseHistory
	private String date; //date format is yyyy-mm-dd
	private String amount;
	private String id;
	
//Region IncomeHistory and SavingsHistory only
	private String source;
	private String notes;

//Region ExpenseHistory only
	private String categoryID;
	private String vendor;
	private String description;

	//Only used for sorting on the History pages
	private Calendar javaDate;
	
//Only used on the Report Page
	private BigDecimal rowTotal;
	
	public String getDate() throws ParseException {
		Date plainDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		String formattedDate = new SimpleDateFormat("dd MMM, yyyy").format(plainDate);
		return formattedDate;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String Source) {
		this.source = Source;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String Notes) {
		this.notes = Notes;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
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
	
	public Calendar getJavaDate(){
		Date aDate = null;
		javaDate = Calendar.getInstance();
		try {
			aDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			javaDate.setTime(aDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return javaDate;
		
	}
	public void setJavaDate(Calendar javaDate) {
		this.javaDate = javaDate;
	}
	
	//Override compareTo method needed to sort a collection of this object
	@Override
	public int compareTo(UserHistory o) {
		
		// Handle null ref exceptions. I don't know why. Just copied this from the net
		if (getJavaDate() == null || o.getJavaDate() == null){
		      return 0;
		}
		return getJavaDate().compareTo(o.getJavaDate());
	}
	
	
	public BigDecimal getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(BigDecimal runningTotal) {
		this.rowTotal = runningTotal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
