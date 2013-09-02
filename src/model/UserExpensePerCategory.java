package model;

import utils.Constants;

public class UserExpensePerCategory {
	private String expense;
	private int categoryID;
	private String categoryString;
	private Constants constants;
	
	public UserExpensePerCategory(){
		constants = new Constants();
	}

	public String getExpense() {
		return expense;
	}
	public void setExpense(String expense) {
		this.expense = expense;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryString(){
		categoryString = constants.getCategory(categoryID);
		return categoryString;
	}
	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}
}
