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

@ManagedBean
public class AnalysisExpenseRecordsBean {

	private String username;
	private String year;
	private String month;
	private String[] allCategories = {"Food","Rent","Gas","Transport","Car Note",
									  "Phone","Misc","Entertainment","Electricity",
									  "Internet"};
	
	
	private CartesianChartModel categoryModel;
	
	public AnalysisExpenseRecordsBean(){
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		createCategoryModel();
		
	}
	
	public void createCategoryModel(){
		categoryModel = new CartesianChartModel();
		ChartSeries expenseCategories = new ChartSeries();
		expenseCategories.setLabel("Expense By Category");
		
		for(int i=0; i<allCategories.length; i++){
			expenseCategories.set(allCategories[i], 0);
		}
		categoryModel.addSeries(expenseCategories);
		
		
	}
	
	public void refreshChartData(ActionEvent e){
		HashMap<String,String> chartData = new HashMap<String,String>();
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		uerAccessor.getUserExpenseHistoryByYearAndMonth(chartData, username, year, month);
		
		populateChartData(chartData);
		refreshCategoryModel(chartData);
		
	}
	
	private void populateChartData(Map<String,String> m){
		
		List<String> activeCategories = new ArrayList<String>(m.keySet());
		for(int i=0; i<allCategories.length; i++){
			String key = Integer.toString(i+1);
			if(!activeCategories.contains(key)){
				m.put(key, "0");
			}
		}
		
		
	}
	
	public void refreshCategoryModel(Map<String,String> m){
		categoryModel = new CartesianChartModel();
		ChartSeries expenseSeries = new ChartSeries();
		expenseSeries.setLabel("Categories");
		
		for(int i=0; i<allCategories.length; i++){
			String key = Integer.toString(i+1);
			expenseSeries.set(allCategories[i], new BigDecimal(m.get(key)));
		}
		
		categoryModel.addSeries(expenseSeries);
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}
}
