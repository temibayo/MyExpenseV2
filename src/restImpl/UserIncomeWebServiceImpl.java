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
	public UserIncomeWSResponse getLastMonthTotalIncome(@QueryParam("userID") final int userProfileId,
														@QueryParam("month") final int month,
														@QueryParam("year") final int year) {
		UserIncomeWSResponse response = new UserIncomeWSResponse();
		LastMonthSummary summary = new LastMonthSummary();
		UserIncomeRecordAccessor accessor = new UserIncomeRecordAccessor();
		
		summary.setMonth(month);
		summary.setYear(year);
		
		accessor.getLastMonthTotalIncome(summary, userProfileId);
		response.setLastMonthSummary(summary);
		
		return response;
	}
	
	@GET
	@Path("/currentMonthTotalIncome")
	@Produces(MediaType.APPLICATION_JSON)
	public UserIncomeWSResponse getCurrentMonthTotalIncome(@QueryParam("userID") final int userProfileId,
														   @QueryParam("month") final int month,
														   @QueryParam("year") final int year) {
		
		UserIncomeWSResponse response = new UserIncomeWSResponse();
		CurrentMonthSummary summary = new CurrentMonthSummary();
		UserIncomeRecordAccessor accessor = new UserIncomeRecordAccessor();
		
		summary.setMonth(month);
		summary.setYear(year);
		
		accessor.getCurrentMonthTotalIncome(summary, userProfileId);
		response.setCurrentMonthSummary(summary);
		
		
		return response;
	}

	@GET
	@Path("/currentYearTotalIncome")
	@Produces(MediaType.APPLICATION_JSON)
	public UserIncomeWSResponse getCurrentYearTotalIncome(@QueryParam("userID") final int userProfileId, 
														  @QueryParam("year") final int year) {
		UserIncomeWSResponse response = new UserIncomeWSResponse();
		CurrentYearSummary summary = new CurrentYearSummary();
		UserIncomeRecordAccessor accessor = new UserIncomeRecordAccessor();
		
		summary.setYear(year);
		
		//Get the Total income for the year  and store it in the summary
		accessor.getCurrentYearTotalIncome(summary, userProfileId);
		
		response.setCurrentYearSummary(summary);
		return response;
	}

}
