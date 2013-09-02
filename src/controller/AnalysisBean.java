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

public class AnalysisBean {
	private String username;
	private String recordType;
	private String year;

	private CartesianChartModel categoryModel;
	
	public AnalysisBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		createCategoryModel();
	}
	
	public void refreshChartData(ActionEvent e){
		
		HashMap<String,String> chartData = new HashMap<String,String>();
		if(recordType.equals("Expense")){
			UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
			uerAccessor.getUserExpenseHistoryByYear(chartData, username, year);
		}
		else if(recordType.equals("Income")){
			UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
			uirAccessor.getUserIncomeHistoryByYear(chartData, username, year);
		}
		else if(recordType.equals("Savings")){
			UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
			usrAccessor.getUserSavingsHistoryByYear(chartData, username, year);
		}
				
		//Get the list of months that have values greater than 0
		List<String> months = new ArrayList<String>(chartData.keySet());
		
		//For each month that has no record entry in the chartData map, add an entry in chartData
		//with a corresponding value of zero
		for(int i=1; i<=12; i++){
			String month = Integer.toString(i);
			if(!months.contains(month)){
				chartData.put(month, "0");
			}
		}
		
		//Add the new chart Data to the the Category model and render the chart
		refreshCategoryModel(chartData);
	}
	
	
	public CartesianChartModel getCategoryModel(){  
	    return categoryModel;  
	}  
	
	/**
	 * @param m
	 */
	private void refreshCategoryModel(Map<String,String> m) {  
        categoryModel = new CartesianChartModel();  
        
        ChartSeries records = new ChartSeries();  
        records.setLabel("Records By Month");  
       
        records.set("Jan", new BigDecimal(m.get("1")));  
        records.set("Feb", new BigDecimal(m.get("2")));  
        records.set("Mar", new BigDecimal(m.get("3")));  
        records.set("Apr", new BigDecimal(m.get("4")));
        records.set("May", new BigDecimal(m.get("5")));
        records.set("Jun", new BigDecimal(m.get("6")));
        records.set("Jul", new BigDecimal(m.get("7")));
        records.set("Aug", new BigDecimal(m.get("8")));
        records.set("Sep", new BigDecimal(m.get("9")));
        records.set("Oct", new BigDecimal(m.get("10")));
        records.set("Nov", new BigDecimal(m.get("11")));
        records.set("Dec", new BigDecimal(m.get("12")));
    
        categoryModel.addSeries(records);  
	}
	  
	private void createCategoryModel() {  
	        categoryModel = new CartesianChartModel();  
	  
	        ChartSeries records = new ChartSeries();  
	        records.setLabel("Records By Month");  
	  
	        records.set("Jan", 0);  
	        records.set("Feb", 0);  
	        records.set("Mar", 0);  
	        records.set("Apr", 0);  
	        records.set("May", 0);  
	        records.set("Jun", 0);
	        records.set("Jul", 0);
	        records.set("Aug", 0);
	        records.set("Sep", 0);
	        records.set("Oct", 0);
	        records.set("Nov", 0);
	        records.set("Dec", 0);
	  
	        categoryModel.addSeries(records);  
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}



	
	
}
