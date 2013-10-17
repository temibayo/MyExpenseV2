package rest;

import restImpl.serviceResponse.CurrentMonthAllExpenseWSResponse;
import restImpl.serviceResponse.UserExpenseWSResponse;

public interface UserExpenseWebService {
	
	public UserExpenseWSResponse getLastMonthTotalExpense(int userProfileId,
															int month,
															int year);

	public UserExpenseWSResponse getCurrentMonthTotalExpense(int userProfileId, 
			   													int month,
			   													int year);

	public UserExpenseWSResponse getCurrentYearTotalExpense(int userProfileId, int year);
	
	public CurrentMonthAllExpenseWSResponse getCurrentMonthAllExpense(int userProfileId,
																	int month,
																	int year);
		
	
	

}
