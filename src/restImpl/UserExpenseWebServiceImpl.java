package restImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dataAccessor.UserExpenseRecordAccessor;
import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import rest.UserExpenseWebService;
import restImpl.serviceResponse.UserExpenseWSResponse;

@Path("expenseWebService")
public class UserExpenseWebServiceImpl implements UserExpenseWebService{


	@GET
	@Path("/lastMonthTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserExpenseWSResponse getLastMonthTotalExpense(@QueryParam("userID") final int userProfileId,
														  @QueryParam("month") final int month, 
														  @QueryParam("year") final	int year) {
		
		UserExpenseWSResponse response = new UserExpenseWSResponse();
		
		LastMonthSummary summary = new LastMonthSummary();
		summary.setMonth(month);
		summary.setYear(year);
		
		UserExpenseRecordAccessor accessor = new UserExpenseRecordAccessor();		
		accessor.getLastMonthTotalExpense(summary, userProfileId);
		
		response.setLastMonthSummary(summary);
		
		
		
		return response;
	}

	@GET
	@Path("/currentMonthTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserExpenseWSResponse getCurrentMonthTotalExpense(@QueryParam("userID") final int userProfileId,
															 @QueryParam("month") final int month, 
															 @QueryParam("year") final int year) {
		
		UserExpenseWSResponse response = new UserExpenseWSResponse();
		
		CurrentMonthSummary summary = new CurrentMonthSummary();
		summary.setMonth(month);
		summary.setYear(year);
		
		UserExpenseRecordAccessor accessor = new UserExpenseRecordAccessor();
		accessor.getCurrentMonthTotalExpense(summary, userProfileId);
		
		response.setCurrentMonthSummary(summary);
		return response;
	}

	@GET
	@Path("/currentYearTotal")
	@Produces(MediaType.APPLICATION_JSON)
	public UserExpenseWSResponse getCurrentYearTotalExpense(@QueryParam("userID") int userProfileId,
															@QueryParam("year") int year) {
		
		UserExpenseWSResponse response = new UserExpenseWSResponse();
		
		CurrentYearSummary summary = new CurrentYearSummary();
		summary.setYear(year);
		
		UserExpenseRecordAccessor accessor = new UserExpenseRecordAccessor();
		accessor.getCurrentYearTotalExpense(summary, userProfileId);
		
		response.setCurrentYearSummary(summary);
		return response;
	}

}
