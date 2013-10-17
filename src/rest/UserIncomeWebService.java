package rest;

import java.util.Date;

import model.CurrentYearSummary;
import restImpl.serviceResponse.AddIncomeWSResponse;
import restImpl.serviceResponse.UserIncomeWSResponse;

public interface UserIncomeWebService {
	
	public UserIncomeWSResponse getLastMonthTotalIncome(int userProfileId,
														int month,
														int year);
	
	public UserIncomeWSResponse getCurrentMonthTotalIncome(int userProfileId, 
														   int month,
														   int year);
	
	public UserIncomeWSResponse getCurrentYearTotalIncome(int userProfileId, int year);
	
	
	public AddIncomeWSResponse addUserIncome(String date,
											 String username,
											 String source,
											 String notes,
											 String amount);
	

}
