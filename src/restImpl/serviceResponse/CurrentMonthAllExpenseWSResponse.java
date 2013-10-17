package restImpl.serviceResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import model.UserExpensePerCategory;

@XmlRootElement
public class CurrentMonthAllExpenseWSResponse extends CommonWSResponse {

	private List<UserExpensePerCategory> userExpensePerCategory;

	public void setUserExpensePerCategory(List<UserExpensePerCategory> userExpensePerCategory) {
		this.userExpensePerCategory = userExpensePerCategory;
	}

	public List<UserExpensePerCategory> getUserExpensePerCategory() {
		return userExpensePerCategory;
	}
}
