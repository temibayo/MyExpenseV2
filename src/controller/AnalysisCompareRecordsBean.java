package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import dataAccessor.UserExpenseRecordAccessor;
import dataAccessor.UserIncomeRecordAccessor;
import dataAccessor.UserSavingsRecordAccessor;

@ManagedBean
public class AnalysisCompareRecordsBean {
	private String username;
	private String year;
	private CartesianChartModel categoryModel;
	
	private Boolean isExpenseSelected;
	private Boolean isIncomeSelected;
	private Boolean isSavingsSelected;
	
	private String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
							   "Jul","Aug","Sep","Oct","Nov","Dec"};

	public AnalysisCompareRecordsBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		
		//Initialize Bar Chart with all zero values
		createCategoryModel();
	}
	
	private void createCategoryModel() {  
        categoryModel = new CartesianChartModel();  
  
        ChartSeries records = new ChartSeries();  
        records.setLabel("No records selected");  
  
        for(int i=0; i<months.length; i++){
           	records.set(months[i], 0);
        }
        categoryModel.addSeries(records);  
	}
	
	public void refreshChartData(ActionEvent e){
		HashMap<String,String> expenseChartData = new HashMap<String,String>();
		HashMap<String,String> incomeChartData = new HashMap<String,String>();
		HashMap<String,String> savingsChartData = new HashMap<String,String>();
		if(isExpenseSelected){
			UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
			uerAccessor.getUserExpenseHistoryByYear(expenseChartData, username, year);
			populateChartData(expenseChartData);
		}
		if(isIncomeSelected){
			UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
			uirAccessor.getUserIncomeHistoryByYear(incomeChartData, username, year);
			populateChartData(incomeChartData);
		}
		if(isSavingsSelected){
			UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
			usrAccessor.getUserSavingsHistoryByYear(savingsChartData, username, year);
			populateChartData(savingsChartData);
		}
		
		refreshCategoryModel(expenseChartData, incomeChartData, savingsChartData);
		
	}
	
	/**
	 * @param m
	 * This method takes the Map of chart data as a parameter, it creates entries for calendar months
	 * that are not already present in the Map
	 */
	private void populateChartData(Map<String,String> m){
		//Get the list of months that have values greater than 0
		List<String> activeMonths = new ArrayList<String>(m.keySet());
		
		//For each month that has no record entry in the chartData map, add an entry in chartData
		//with a corresponding value of zero to avoid getting a null ref exception
		for(int i=0; i<=months.length; i++){
			String key = Integer.toString(i+1);
			if(!activeMonths.contains(key)){
				m.put(key, "0");
			}
		}
	}
	
	private void refreshCategoryModel(Map<String,String> expense, Map<String,String> income, Map<String,String> savings){
		categoryModel = new CartesianChartModel();  
		
		//If the the expense checkbox was selected, populate the expense bar chart series
		if(isExpenseSelected){		
			ChartSeries expenseSeries = new ChartSeries();  
			expenseSeries.setLabel("Total Expense By Month");  
			
			for(int i=0; i<months.length; i++){
				String key = Integer.toString(i+1);
				expenseSeries.set(months[i], new BigDecimal(expense.get(key)));
			}
    
			categoryModel.addSeries(expenseSeries); 
		}
		
		//if the income checkbox was selected, populate the income bar chart series
		if(isIncomeSelected){
	        ChartSeries incomeSeries = new ChartSeries();  
	        incomeSeries.setLabel("Total Income By Month");  
	        
	        for(int i=0; i<months.length; i++){
	        	String key = Integer.toString(i+1);
	        	incomeSeries.set(months[i], new BigDecimal(income.get(key)));
	        }
	  
	        categoryModel.addSeries(incomeSeries); 
		}
		
		//if the savings checkbox was selected, populate the savings bar chart series
		if(isSavingsSelected){
			ChartSeries savingsSeries = new ChartSeries();  
	        savingsSeries.setLabel("Total Savings By Month");  
	        
	        for(int i=0; i<months.length; i++){
	        	
	        	//initialize the map key
	        	String key = Integer.toString(i+1);
	        	
	        	savingsSeries.set(months[i], new BigDecimal(savings.get(key)));
	        }
	  
	        categoryModel.addSeries(savingsSeries); 
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Boolean getIsExpenseSelected() {
		return isExpenseSelected;
	}

	public void setIsExpenseSelected(Boolean isExpenseSelected) {
		this.isExpenseSelected = isExpenseSelected;
	}

	public Boolean getIsIncomeSelected() {
		return isIncomeSelected;
	}

	public void setIsIncomeSelected(Boolean isIncomeSelected) {
		this.isIncomeSelected = isIncomeSelected;
	}

	public Boolean getIsSavingsSelected() {
		return isSavingsSelected;
	}

	public void setIsSavingsSelected(Boolean isSavingsSelected) {
		this.isSavingsSelected = isSavingsSelected;
	}
}
