package restImpl.serviceResponse;

import javax.xml.bind.annotation.XmlRootElement;

import dataAccessor.DataAccessResult;

@XmlRootElement
public class AddIncomeWSResponse extends CommonWSResponse{
	
	private DataAccessResult dataAccessResult;

	public DataAccessResult getDataAccessResult() {
		return dataAccessResult;
	}

	public void setDataAccessResult(DataAccessResult dar) {
		this.dataAccessResult = dar;
	}

}
