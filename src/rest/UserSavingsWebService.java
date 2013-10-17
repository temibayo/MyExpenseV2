package rest;

import restImpl.serviceResponse.UserSavingsWSResponse;

public interface UserSavingsWebService {
	
	public UserSavingsWSResponse getLastMonthTotalSavings(int userProfileID,
														  int month,
														  int year);
	
	public UserSavingsWSResponse getCurrentMonthTotalSavings(int userProfileID,
															 int month,
															 int year);
	
	public UserSavingsWSResponse getCurrentYearTotalSavings(int userProfileID,
															int year);
	
	

}
