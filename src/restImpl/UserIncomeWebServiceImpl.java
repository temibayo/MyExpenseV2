package restImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dataAccessor.UserIncomeRecordAccessor;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import rest.UserIncomeWebService;
import restImpl.serviceResponse.UserIncomeWSResponse;
import restImpl.serviceResponse.UserProfileWSResponse;

@Path("incomeWebService")
public class UserIncomeWebServiceImpl implements UserIncomeWebService {

	@GET
	@Path("/lastMonthTotalIncome")
	@Produces(MediaType.APPLICATION_JSON)
	public UserIncomeWSResponse getLastMonthTotalIncome(@QueryParam("userID") int userProfileId,
														@QueryParam("month") int month,
														@QueryParam("year") int year) {
		UserIncomeWSResponse response = new UserIncomeWSResponse();
		LastMonthSummary summary = new LastMonthSummary();
		UserIncomeRecordAccessor accessor = new UserIncomeRecordAccessor();
		
		summary.setMonth(month);
		summary.setYear(year);
		
		accessor.getLastMonthTotalIncome(summary, userProfileId);
		response.setLastMonthSummary(summary);
		
		return response;
	}

	public UserIncomeWSResponse getCurrentMonthTotalIncome(CurrentMonthSummary summary,
															int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserIncomeWSResponse getCurrentYearTotalIncome(
			CurrentYearSummary summary, int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
