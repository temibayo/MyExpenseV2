package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jackson.map.ObjectMapper;
import org.primefaces.model.chart.MeterGaugeChartModel;

import restImpl.serviceResponse.UserIncomeWSResponse;
import restImpl.serviceResponse.UserProfileWSResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utils.DateUtil;

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
	private final String INCOME_WS_PATH = "/lastMonthTotalIncome";
	
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
		UserIncomeWSResponse wsResponse = new UserIncomeWSResponse();
		String idAsString = Integer.toString(userProfileID);
		
		DateUtil date = new DateUtil();
		String month = Integer.toString(date.getLastMonth());
		String year = Integer.toString(date.getLastMonthYear());
		
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource(INCOME_WS_URI);
	 
			ClientResponse response = webResource.path(INCOME_WS_PATH).
											queryParam("month", month).
											queryParam("year", year).
											queryParam("userID", idAsString).
											accept("application/json").get(ClientResponse.class);
	 
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	 
			String output = response.getEntity(String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			wsResponse = mapper.readValue(output, UserIncomeWSResponse.class);
			System.out.println("Output from Server .... \n");
			System.out.println(wsResponse.getLastMonthSummary().getIncome());
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
		
		UserIncomeRecordAccessor uirAccessor = new UserIncomeRecordAccessor();
		UserExpenseRecordAccessor uerAccessor = new UserExpenseRecordAccessor();
		UserSavingsRecordAccessor usrAccessor = new UserSavingsRecordAccessor();
		
		DataAccessResult dar = uirAccessor.getLastMonthTotalIncome(lastMonthSummary, userProfileID);
		dar = uerAccessor.getLastMonthTotalExpense(lastMonthSummary, userProfileID);
		usrAccessor.getLastMonthTotalSavings(lastMonthSummary, userProfileID);
		
		uerAccessor.getCurrentMonthTotalExpense(currentMonthSummary, userProfileID);
		uirAccessor.getCurrentMonthTotalIncome(currentMonthSummary, userProfileID);
		usrAccessor.getCurrentMonthTotalSavings(currentMonthSummary, userProfileID);
		
		uerAccessor.getCurrentYearTotalExpense(currentYearSummary, userProfileID);
		uirAccessor.getCurrentYearTotalIncome(currentYearSummary, userProfileID);
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
