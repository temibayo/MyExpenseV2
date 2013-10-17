package restImpl.serviceResponse;

import javax.xml.bind.annotation.XmlRootElement;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;

@XmlRootElement
public class UserSavingsWSResponse extends CommonWSResponse{
	
	private LastMonthSummary lastMonthSummary;
	private CurrentMonthSummary currentMonthSummary;
	private CurrentYearSummary currentYearSummary;
	
	public LastMonthSummary getLastMonthSummary() {
		return lastMonthSummary;
	}
	public void setLastMonthSummary(LastMonthSummary lastMonthSummary) {
		this.lastMonthSummary = lastMonthSummary;
	}
	public CurrentMonthSummary getCurrentMonthSummary() {
		return currentMonthSummary;
	}
	public void setCurrentMonthSummary(CurrentMonthSummary currentMonthSummary) {
		this.currentMonthSummary = currentMonthSummary;
	}
	public CurrentYearSummary getCurrentYearSummary() {
		return currentYearSummary;
	}
	public void setCurrentYearSummary(CurrentYearSummary currentYearSummary) {
		this.currentYearSummary = currentYearSummary;
	}
	
}
