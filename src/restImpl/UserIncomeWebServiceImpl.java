package restImpl;

import javax.ws.rs.Path;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import rest.UserIncomeWebService;
import restImpl.serviceResponse.UserIncomeWSResponse;

@Path("incomeWebService")
public class UserIncomeWebServiceImpl implements UserIncomeWebService {

	public UserIncomeWSResponse getLastMonthTotalIncome(
			LastMonthSummary summary, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserIncomeWSResponse getCurrentMonthTotalIncome(
			CurrentMonthSummary summary, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserIncomeWSResponse getCurrentYearTotalIncome(
			CurrentYearSummary summary, int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
