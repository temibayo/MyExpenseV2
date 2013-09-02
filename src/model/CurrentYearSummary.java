package model;



public class CurrentYearSummary {
	
	private int year;
	private String yearString;
	private String income;
	private String expense;
	private String savings;
	

	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getExpense() {
		return expense;
	}
	public void setExpense(String expense) {
		this.expense = expense;
	}
	public String getSavings() {
		return savings;
	}
	public void setSavings(String savings) {
		this.savings = savings;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public String getYearString(){
		this.yearString = Integer.toString(year);
		return yearString;
	}
	

}
