package rest;

import model.CurrentYearSummary;
import restImpl.serviceResponse.UserIncomeWSResponse;

public interface UserIncomeWebService {
	
	public UserIncomeWSResponse getLastMonthTotalIncome(int userProfileId,
														int month,
														int year);
	
	public UserIncomeWSResponse getCurrentMonthTotalIncome(int userProfileId, 
														   int month,
														   int year);
	
	public UserIncomeWSResponse getCurrentYearTotalIncome(int userProfileId, int year);
	

}
