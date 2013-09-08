package restImpl.serviceResponse;

import javax.xml.bind.annotation.XmlRootElement;

import model.LastMonthSummary;

@XmlRootElement
public class UserIncomeWSResponse extends CommonWSResponse {
	
	
	private LastMonthSummary lastMonthSummary;

	public LastMonthSummary getLastMonthSummary() {
		return lastMonthSummary;
	}

	public void setLastMonthSummary(LastMonthSummary lastMonthSummary) {
		this.lastMonthSummary = lastMonthSummary;
	}

}
