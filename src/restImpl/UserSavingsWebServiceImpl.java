package restImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dataAccessor.UserSavingsRecordAccessor;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;

import rest.UserSavingsWebService;
import restImpl.serviceResponse.UserSavingsWSResponse;

@Path("savingsWebService")
public class UserSavingsWebServiceImpl implements UserSavingsWebService {

	@GET
	@Path("lastMonthTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserSavingsWSResponse getLastMonthTotalSavings(@QueryParam("userID") final int userProfileID,
														  @QueryParam("month") final int month, 
														  @QueryParam("year") int year) {
		UserSavingsWSResponse response = new UserSavingsWSResponse();
		LastMonthSummary summary = new LastMonthSummary();
		summary.setMonth(month);
		summary.setYear(year);
		UserSavingsRecordAccessor accessor = new UserSavingsRecordAccessor();
		accessor.getLastMonthTotalSavings(summary, userProfileID);
		
		response.setLastMonthSummary(summary);
		
		return response;
	}

	@GET
	@Path("currentMonthTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserSavingsWSResponse getCurrentMonthTotalSavings(@QueryParam("userID") final int userProfileID,
															 @QueryParam("month") final int month, 
															 @QueryParam("year") final int year) {
		
		UserSavingsWSResponse response = new UserSavingsWSResponse();
		CurrentMonthSummary summary = new CurrentMonthSummary();
		summary.setMonth(month);
		summary.setYear(year);
		UserSavingsRecordAccessor accessor = new UserSavingsRecordAccessor();
		accessor.getCurrentMonthTotalSavings(summary, userProfileID);
		
		response.setCurrentMonthSummary(summary);
		return response;
	}

	@GET
	@Path("currentYearTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserSavingsWSResponse getCurrentYearTotalSavings(@QueryParam("userID") final int userProfileID,
															@QueryParam("year") final int year) {
	
		UserSavingsWSResponse response = new UserSavingsWSResponse();
		CurrentYearSummary summary = new CurrentYearSummary();
		summary.setYear(year);
		UserSavingsRecordAccessor accessor = new UserSavingsRecordAccessor();
		accessor.getCurrentYearTotalSavings(summary, userProfileID);
		response.setCurrentYearSummary(summary);
		return response;
	}

}
