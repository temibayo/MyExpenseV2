package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.model.chart.MeterGaugeChartModel;

import restImpl.serviceResponse.CurrentMonthAllExpenseWSResponse;
import restImpl.serviceResponse.UserExpenseWSResponse;
import restImpl.serviceResponse.UserIncomeWSResponse;
import restImpl.serviceResponse.UserSavingsWSResponse;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import utils.DateUtil;
import utils.SummaryType;
import utils.WSClientUtil;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import model.UserExpensePerCategory;

@ManagedBean
@RequestScoped
public class HomeBean{
	private String username;
	private LastMonthSummary lastMonthSummary;
	private CurrentMonthSummary currentMonthSummary;
	private CurrentYearSummary currentYearSummary;
	private List<UserExpensePerCategory> userExpensePerCategory;
	private String renderBarChart;
	private MeterGaugeChartModel meterGaugeModel;  
	private BigDecimal expense;
	
	private int userProfileID;
	
	private static final String INCOME_WS_URI = 
						"http://localhost/MyExpenseVersion2/restImpl/incomeWebService";
	private static final String EXPENSE_WS_URI = 
						"http://localhost/MyExpenseVersion2/restImpl/expenseWebService";
	private static final String SAVINGS_WS_URI = 
						"http://localhost/MyExpenseVersion2/restImpl/savingsWebService";	
	private static final String LAST_MONTH_RESOURCE = "lastMonthTotal";
	private static final String CURRENT_MONTH_RESOURCE = "currentMonthTotal";
	private static final String CURRENT_YEAR_RESOURCE = "currentYearTotal";
	private static final String CURRENT_MONTH_ALL_EXPENSE_RESOURCE  =
														"currentMonthAllExpense";
	
	@ManagedProperty(value="#{userProfileBean}")
	private UserProfileBean upBean;
	
	public HomeBean(){		
		lastMonthSummary = new LastMonthSummary();
		currentMonthSummary = new CurrentMonthSummary();
		currentYearSummary = new CurrentYearSummary();
		userExpensePerCategory = new ArrayList<UserExpensePerCategory>();
	}
	
	
	/**
	 * HomeBean is instantiated
	 * ManagedProperty is injected and instantiated
	 * PostConstruct method is executed
	 * 
	 */
	@PostConstruct 
	public void init(){
		userProfileID = upBean.getUserID();
		String userIdAsString = Integer.toString(userProfileID);
		
		UserIncomeWSResponse lastMonthIncome = new UserIncomeWSResponse();
		UserExpenseWSResponse lastMonthExpense = new UserExpenseWSResponse();
		UserSavingsWSResponse lastMonthSavings = new UserSavingsWSResponse();

		UserIncomeWSResponse currentMonthIncome = new UserIncomeWSResponse();
		UserExpenseWSResponse currentMonthExpense = new UserExpenseWSResponse();
		UserSavingsWSResponse currentMonthSavings = new UserSavingsWSResponse();
		CurrentMonthAllExpenseWSResponse currentMonthAllExpense = 
											new CurrentMonthAllExpenseWSResponse();
		
		UserIncomeWSResponse currentYearIncome = new UserIncomeWSResponse();
		UserExpenseWSResponse currentYearExpense = new UserExpenseWSResponse();
		UserSavingsWSResponse currentYearSavings = new UserSavingsWSResponse();
		
		
		//HINT: COnsider making dateUtil an Abstract or static class
		DateUtil dateUtil = new DateUtil();  
		String lastMonthDate = Integer.toString(dateUtil.getLastMonth());
		String lastMonthYearDate = Integer.toString(dateUtil.getLastMonthYear());
		String currentMonthDate = Integer.toString(dateUtil.getCurrentMonth());
		String currentYearDate = Integer.toString(dateUtil.getCurrentYear());
		
		WSClientUtil clientUtil = new WSClientUtil();		
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		
		//Get Last Months data from WebService	
		queryParams.add("month", lastMonthDate);
		queryParams.add("year", lastMonthYearDate);
		queryParams.add("userID", userIdAsString);
		String totalIncomeResponse = clientUtil.getWSResponse(queryParams, 
													INCOME_WS_URI, LAST_MONTH_RESOURCE);
		String lastMonthExpenseResponse = clientUtil.getWSResponse(queryParams, 
													EXPENSE_WS_URI, LAST_MONTH_RESOURCE);
		String lastMonthSavingsResponse = clientUtil.getWSResponse(queryParams, 
													SAVINGS_WS_URI, LAST_MONTH_RESOURCE);
		
		//Get Current Months data from WebService
		queryParams.clear();
		queryParams.add("month", currentMonthDate);
		queryParams.add("year", currentYearDate);
		queryParams.add("userID", userIdAsString);
		String currentMonthIncomeResponse = clientUtil.getWSResponse(queryParams, 
													INCOME_WS_URI, CURRENT_MONTH_RESOURCE);
		String currentMonthExpenseResponse = clientUtil.getWSResponse(queryParams, 
													EXPENSE_WS_URI, CURRENT_MONTH_RESOURCE);
		String currentMonthSavingsResponse = clientUtil.getWSResponse(queryParams,
													SAVINGS_WS_URI, CURRENT_MONTH_RESOURCE);													
		String currentMonthAllExpenseResponse = clientUtil.getWSResponse(queryParams,
													EXPENSE_WS_URI,
													CURRENT_MONTH_ALL_EXPENSE_RESOURCE);
													
		//Get Current Year data from webservice
		queryParams.clear();
		queryParams.add("year", Integer.toString(dateUtil.getCurrentYear()));
		queryParams.add("userID", Integer.toString(userProfileID));
		String currentYearIncomeResponse = clientUtil.getWSResponse(queryParams,
													INCOME_WS_URI, CURRENT_YEAR_RESOURCE);
		String currentYearExpenseResponse = clientUtil.getWSResponse(queryParams,
													EXPENSE_WS_URI, CURRENT_YEAR_RESOURCE);
		String currentYearSavingsResponse = clientUtil.getWSResponse(queryParams,
													SAVINGS_WS_URI, CURRENT_YEAR_RESOURCE);
				
		
		try 
		{	
			//Deserialize String Web Service responses back to Java Objects
			ObjectMapper mapper = new ObjectMapper();
			lastMonthIncome = mapper.readValue(totalIncomeResponse, UserIncomeWSResponse.class);
			lastMonthExpense = mapper.readValue(lastMonthExpenseResponse, UserExpenseWSResponse.class);
			lastMonthSavings = mapper.readValue(lastMonthSavingsResponse, UserSavingsWSResponse.class);
			
			currentMonthIncome = mapper.readValue(currentMonthIncomeResponse, UserIncomeWSResponse.class);			
			currentMonthExpense = mapper.readValue(currentMonthExpenseResponse, UserExpenseWSResponse.class);
			currentMonthSavings = mapper.readValue(currentMonthSavingsResponse, UserSavingsWSResponse.class);
			
			//This line does not work when you have records for only one category. Need to fix
			currentMonthAllExpense = mapper.readValue(currentMonthAllExpenseResponse, CurrentMonthAllExpenseWSResponse.class);
			
			currentYearIncome = mapper.readValue(currentYearIncomeResponse, UserIncomeWSResponse.class);
			currentYearExpense = mapper.readValue(currentYearExpenseResponse, UserExpenseWSResponse.class);
			currentYearSavings = mapper.readValue(currentYearSavingsResponse, UserSavingsWSResponse.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Catch null reference exception on all widget values
		//Finally, set the values in the last Month Widget 
		if(validateWSResponseObjects(lastMonthIncome, lastMonthExpense, 
										lastMonthSavings, SummaryType.LAST_MONTH)){
			lastMonthSummary.setMonthString(dateUtil.getLastMonthString());
			lastMonthSummary.setIncome(lastMonthIncome.getLastMonthSummary().getIncome());
			lastMonthSummary.setExpense(lastMonthExpense.getLastMonthSummary().getExpense());
			lastMonthSummary.setSavings(lastMonthSavings.getLastMonthSummary().getSavings());
		}
		
		//Finally, set the values in the Current Month Widget
		if(validateWSResponseObjects(currentMonthIncome, currentMonthExpense,
									 currentMonthSavings, SummaryType.CURRENT_MONTH)){
			currentMonthSummary.setMonthString(dateUtil.getCurrentMonthString());
			currentMonthSummary.setIncome(currentMonthIncome.getCurrentMonthSummary().getIncome());
			currentMonthSummary.setExpense(currentMonthExpense.getCurrentMonthSummary().getExpense());
			currentMonthSummary.setSavings(currentMonthSavings.getCurrentMonthSummary().getSavings());
		}
		
		//Finally set the values in the Current Year Widget. This widget is currently not used
		if(validateWSResponseObjects(currentYearIncome, currentYearExpense,
									 currentYearSavings, SummaryType.CURRENT_YEAR)){
			currentYearSummary.setYear(dateUtil.getCurrentYear());
			currentYearSummary.setIncome(currentYearIncome.getCurrentYearSummary().getIncome());
			currentYearSummary.setExpense(currentYearExpense.getCurrentYearSummary().getExpense());
			currentYearSummary.setSavings(currentYearSavings.getCurrentYearSummary().getSavings());
		}
		
		//Set the values for the Homepage Expense Table
		//BUG- catch null ref exception - FIXED!
		if(currentMonthAllExpense.getUserExpensePerCategory() != null){
			userExpensePerCategory.addAll(currentMonthAllExpense.getUserExpensePerCategory());
		}
		//Set the values in the Expense Meter
		createMeterGaugeModel();
		
		if(userExpensePerCategory.size() > 0){
			renderBarChart = "YES";
		}
		else{
			renderBarChart = "NO";
		}
	}
	
	private boolean validateWSResponseObjects(UserIncomeWSResponse income,
											  UserExpenseWSResponse expense,
											  UserSavingsWSResponse savings,
											  SummaryType type){
		boolean result = true;
		if(type.equals(SummaryType.LAST_MONTH)){
			if(income.getLastMonthSummary() == null || 
					expense.getLastMonthSummary() == null ||
					savings.getLastMonthSummary() == null){
				result = false;
			}
		}
		else if(type.equals(SummaryType.CURRENT_MONTH)){
			if(income.getCurrentMonthSummary() == null ||
					expense.getCurrentMonthSummary() == null ||
					savings.getCurrentMonthSummary() == null){
				result = false;
			}
		}
		else if(type.equals(SummaryType.CURRENT_YEAR)){
			if(income.getCurrentYearSummary() == null ||
					expense.getCurrentYearSummary() == null ||
					savings.getCurrentYearSummary() == null){
				result = false;
			}
		}

		return result;

	}
	
    private void createMeterGaugeModel() {  
  	  
        List<Number> intervals = new ArrayList<Number>(){{  
            add(600);  
            add(1200);  
            add(2500);  
            add(5000);  
        }};  
        
        String expenseString = currentMonthSummary.getExpense();
        if(expenseString == null){
        	expenseString = "0";	// Fixes a bug where BigDecimal init threw null pointer exception
        }
        expense = new BigDecimal(expenseString);
        meterGaugeModel = new MeterGaugeChartModel(expense, intervals);  
    }  
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LastMonthSummary getLastMonthSummary() {
		return lastMonthSummary;
	}
	public void setLastMonthSummary(LastMonthSummary lastMonthSummary) {
		this.lastMonthSummary = lastMonthSummary;
	}
	public CurrentMonthSummary getCurrentMonthSummary() {
		return currentMonthSummary;
	}
	public void setCurrentMonthSummary(CurrentMonthSummary currentMonthSummary) {
		this.currentMonthSummary = currentMonthSummary;
	}
	public CurrentYearSummary getCurrentYearSummary() {
		return currentYearSummary;
	}
	public void setCurrentYearSummary(CurrentYearSummary currentYearSummary) {
		this.currentYearSummary = currentYearSummary;
	}
	public List<UserExpensePerCategory> getUserExpensePerCategory() {
		return userExpensePerCategory;
	}
	public void setUserExpensePerCategory(
			List<UserExpensePerCategory> userExpensePerCategory) {
		this.userExpensePerCategory = userExpensePerCategory;
	}
	public String getRenderBarChart() {
		return renderBarChart;
	}
    public MeterGaugeChartModel getMeterGaugeModel() {
    	return meterGaugeModel;
   	}
	public BigDecimal getExpense() {
		return expense;
	}
	public void setUpBean(UserProfileBean upBean) {
		this.upBean = upBean;
	}

}
