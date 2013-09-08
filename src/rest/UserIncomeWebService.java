package rest;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import restImpl.serviceResponse.UserIncomeWSResponse;

public interface UserIncomeWebService {
	
	public UserIncomeWSResponse getLastMonthTotalIncome(int userProfileId,
														int month,
														int year);
	
	public UserIncomeWSResponse getCurrentMonthTotalIncome(CurrentMonthSummary summary, 
																int id);
	public UserIncomeWSResponse getCurrentYearTotalIncome(CurrentYearSummary summary, int id);
	

}
