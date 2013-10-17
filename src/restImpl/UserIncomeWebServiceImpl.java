package restImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dataAccessor.DataAccessResult;
import dataAccessor.UserIncomeRecordAccessor;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import model.UserIncomeRecord;
import rest.UserIncomeWebService;
import restImpl.serviceResponse.AddIncomeWSResponse;
import restImpl.serviceResponse.UserIncomeWSResponse;
import restImpl.serviceResponse.UserProfileWSResponse;

@Path("incomeWebService")
public class UserIncomeWebServiceImpl implements UserIncomeWebService {

	@GET
	@Path("/lastMonthTotal")
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
	@Path("/currentMonthTotal")
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
	@Path("/currentYearTotal")
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

	@POST
	@Path("/addRecord")
	@Produces(MediaType.APPLICATION_JSON)
	public AddIncomeWSResponse addUserIncome(@QueryParam("date") final String dateAsString,
											 @QueryParam("username") final String username, 
											 @QueryParam("source") final String source,
											 @QueryParam("notes") final String notes, 
											 @QueryParam("amount") final String amount) {
		AddIncomeWSResponse response = new AddIncomeWSResponse();
		Date date = null;
		try {
			 date = new SimpleDateFormat("EEE MMM dd, yyyy", Locale.US).parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(date != null){
			
			UserIncomeRecord uir = new UserIncomeRecord();
			uir.setjDate(date);
			uir.setUsername(username);
			uir.setSource(source);
			uir.setNotes(notes);
			uir.setAmount(amount);
		
			UserIncomeRecordAccessor accessor = new UserIncomeRecordAccessor();
			DataAccessResult dar = accessor.addNewUserIncomeRecord(uir);
			response.setDataAccessResult(dar);
			response.setStatus("SUCCESS");
		}
		else{
			DataAccessResult dar = new DataAccessResult();
			dar.setStatus("Fail");
			response.setDataAccessResult(dar);
			response.setStatus("Fail");
		}
		return response;
	}

}
