package controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;

import dataAccessor.UserExpenseRecordAccessor;
import dataAccessor.UserIncomeRecordAccessor;

import model.UserHistory;

@ManagedBean

public class ReportsBean{
	
	private String year;
	private String month;
	private String categoryID;
	private BigDecimal currentTotalIncome;
	private BigDecimal currentTotalExpense;
	private List<UserHistory> incomeForSelectedMonth;
	private List<UserHistory> expenseForSelectedMonth;
	private List<UserHistory> savingsForSelectedMonth;
	
	@ManagedProperty(value="#{userProfileBean}")
	private UserProfileBean upBean;
	
	

	public ReportsBean(){
		incomeForSelectedMonth = null;
		expenseForSelectedMonth = null;
		savingsForSelectedMonth = null;
		currentTotalIncome = new BigDecimal(0);
		currentTotalExpense = new BigDecimal(0);
		
	}
	
	public void updateIncomeTable(ActionEvent e){
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		incomeForSelectedMonth = uirAccessor.getIncomeForSelectedMonth(upBean.getUserID(), year, month);
		Collections.sort(incomeForSelectedMonth);
		currentTotalIncome = computeRunningTotal(incomeForSelectedMonth);
	}
	
	public void updateExpenseTable(ActionEvent e){
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		if(categoryID.equals("0")){
			expenseForSelectedMonth = uerAccessor.getExpenseForSelectedMonth(upBean.getUserID(), year, month);
		}
		else{
			expenseForSelectedMonth = uerAccessor.getExpenseForSelectedMonthAndCategory(upBean.getUserID(), year, month, categoryID);
		}
		Collections.sort(expenseForSelectedMonth);
		currentTotalExpense = computeRunningTotal(expenseForSelectedMonth);
	}
	
	/**
	 * @param report
	 * @return Total
	 * This method takes a collection of records, calculates the running total for
	 * each row and returns the grand Total value
	 */
	private BigDecimal computeRunningTotal(List<UserHistory> report){
		BigDecimal runningTotal = new BigDecimal(0);
		for(UserHistory uh : report){
			runningTotal = runningTotal.add(new BigDecimal(uh.getAmount()));
			uh.setRowTotal(runningTotal);
		}
		return runningTotal;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<UserHistory> getIncomeForSelectedMonth() {
		return incomeForSelectedMonth;
	}

	public void setIncomeForSelectedMonth(List<UserHistory> incomeForSelectedMonth) {
		this.incomeForSelectedMonth = incomeForSelectedMonth;
	}

	public List<UserHistory> getExpenseForSelectedMonth() {
		return expenseForSelectedMonth;
	}

	public void setExpenseForSelectedMonth(List<UserHistory> expenseForSelectedMonth) {
		this.expenseForSelectedMonth = expenseForSelectedMonth;
	}

	public List<UserHistory> getSavingsForSelectedMonth() {
		return savingsForSelectedMonth;
	}

	public void setSavingsForSelectedMonth(List<UserHistory> savingsForSelectedMonth) {
		this.savingsForSelectedMonth = savingsForSelectedMonth;
	}

	public BigDecimal getCurrentTotalIncome() {
		return currentTotalIncome;
	}

	public void setCurrentTotalIncome(BigDecimal currentTotalIncome) {
		this.currentTotalIncome = currentTotalIncome;
	}

	public BigDecimal getCurrentTotalExpense() {
		return currentTotalExpense;
	}

	public void setUpBean(UserProfileBean upBean) {
		this.upBean = upBean;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
}
