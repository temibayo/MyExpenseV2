package controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.model.chart.MeterGaugeChartModel;

import restImpl.serviceResponse.UserIncomeWSResponse;
import restImpl.serviceResponse.UserProfileWSResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import utils.DateUtil;
import utils.WSClientUtil;

import dataAccessor.DataAccessResult;
import dataAccessor.UserExpenseRecordAccessor;
import dataAccessor.UserIncomeRecordAccessor;
import dataAccessor.UserSavingsRecordAccessor;

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
	private DateUtil dateUtil;
	private CurrentYearSummary currentYearSummary;
	private List<UserExpensePerCategory> userExpensePerCategory;
	private String renderBarChart;
	private MeterGaugeChartModel meterGaugeModel;  
	private BigDecimal expense;
	
	private int userProfileID;
	
	private final String INCOME_WS_URI = 
						"http://localhost/MyExpenseVersion2/restImpl/incomeWebService";
	private final String INCOME_WS_PATH = "lastMonthTotalIncome";
	private final String CURRENT_MONTH_PATH = "currentMonthTotalIncome";
	private final String CURRENT_YEAR_PATH = "currentYearTotalIncome";
	
	@ManagedProperty(value="#{userProfileBean}")
	private UserProfileBean upBean;
	
	public HomeBean(){		
		dateUtil = new DateUtil();
		lastMonthSummary = new LastMonthSummary();
		currentMonthSummary = new CurrentMonthSummary();
		currentYearSummary = new CurrentYearSummary();
		userExpensePerCategory = new ArrayList<UserExpensePerCategory>();
		
		lastMonthSummary.setYear(dateUtil.getLastMonthYear());
		lastMonthSummary.setMonth(dateUtil.getLastMonth());
		lastMonthSummary.setMonthString(dateUtil.getLastMonthString());
		
		currentMonthSummary.setYear(dateUtil.getCurrentYear());
		currentMonthSummary.setMonth(dateUtil.getCurrentMonth());
		currentMonthSummary.setMonthString(dateUtil.getCurrentMonthString());
		
		currentYearSummary.setYear(dateUtil.getCurrentYear());
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
		
		UserIncomeWSResponse wsResponse = new UserIncomeWSResponse();
		UserIncomeWSResponse currentMonthIncome = new UserIncomeWSResponse();
		UserIncomeWSResponse currentYearIncome = new UserIncomeWSResponse();
		ObjectMapper mapper = new ObjectMapper();
				
		DateUtil dateUtil = new DateUtil();  
		String lastMonthDate = Integer.toString(dateUtil.getLastMonth());
		String lastMonthYearDate = Integer.toString(dateUtil.getLastMonthYear());
		String currentMonthDate = Integer.toString(dateUtil.getCurrentMonth());
		String currentYearDate = Integer.toString(dateUtil.getCurrentYear());
		
		WSClientUtil clientUtil = new WSClientUtil();		
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		
		queryParams.add("month", lastMonthDate);
		queryParams.add("year", lastMonthYearDate);
		queryParams.add("userID", userIdAsString);
		String totalIncomeResponse = clientUtil.getWSResponse(queryParams, 
													INCOME_WS_URI, INCOME_WS_PATH);
		
		queryParams.clear();
		queryParams.add("month", currentMonthDate);
		queryParams.add("year", currentYearDate);
		queryParams.add("userID", userIdAsString);
		String currentMonthIncomeResponse = clientUtil.getWSResponse(queryParams, 
													INCOME_WS_URI, CURRENT_MONTH_PATH);
		
		queryParams.clear();
		queryParams.add("year", Integer.toString(dateUtil.getCurrentYear()));
		queryParams.add("userID", Integer.toString(userProfileID));
		String currentYearIncomeResponse = clientUtil.getWSResponse(queryParams,
													INCOME_WS_URI, CURRENT_YEAR_PATH);
		
		
		try 
		{
			wsResponse = mapper.readValue(totalIncomeResponse, UserIncomeWSResponse.class);			
			currentMonthIncome = mapper.readValue(currentMonthIncomeResponse, UserIncomeWSResponse.class);			
			currentYearIncome = mapper.readValue(currentYearIncomeResponse, UserIncomeWSResponse.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lastMonthSummary.setIncome(wsResponse.getLastMonthSummary().getIncome());
		currentMonthSummary.setIncome(currentMonthIncome.getCurrentMonthSummary().getIncome());
		currentYearSummary.setIncome(currentYearIncome.getCurrentYearSummary().getIncome());
		

		  
	
		
		
		
		
		
		
		
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
		
	//	DataAccessResult dar = uirAccessor.getLastMonthTotalIncome(lastMonthSummary, userProfileID);
		DataAccessResult dar = uerAccessor.getLastMonthTotalExpense(lastMonthSummary, userProfileID);
		usrAccessor.getLastMonthTotalSavings(lastMonthSummary, userProfileID);
		
		uerAccessor.getCurrentMonthTotalExpense(currentMonthSummary, userProfileID);
	//	uirAccessor.getCurrentMonthTotalIncome(currentMonthSummary, userProfileID);
		usrAccessor.getCurrentMonthTotalSavings(currentMonthSummary, userProfileID);
		
		uerAccessor.getCurrentYearTotalExpense(currentYearSummary, userProfileID);
	//	uirAccessor.getCurrentYearTotalIncome(currentYearSummary, userProfileID);
		usrAccessor.getCurrentYearTotalSavings(currentYearSummary, userProfileID);
		
		uerAccessor.getCurrentMonthAllExpense(userExpensePerCategory, dateUtil.getCurrentMonth(), 
												dateUtil.getCurrentYear(), userProfileID);
		
		
		createMeterGaugeModel();
		
		if(userExpensePerCategory.size() > 0){
			renderBarChart = "YES";
		}
		else{
			renderBarChart = "NO";
		}
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
