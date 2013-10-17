package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import restImpl.serviceResponse.AddIncomeWSResponse;

import utils.WSClientUtil;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import dataAccessor.DataAccessResult;
import dataAccessor.UserIncomeRecordAccessor;

import model.UserIncomeRecord;

@ManagedBean
public class AddIncomeRecordBean {
	
	private Date date;
	private String source;
	private String notes;
	private String amount;
	private String username;
	private BigDecimal numericAmount;
	
	private static final String ADD_INCOME_RESOURCE = "addRecord";
	private static final String INCOME_WS_URI =
									"http://localhost/MyExpenseVersion2/restImpl/incomeWebService";
	
	
	public AddIncomeRecordBean(){
		
		username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getNumericAmount(){
		return numericAmount;
	}
	public String addUserIncomeRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		try{
			numericAmount = new BigDecimal(amount);
		}
		catch(NumberFormatException e){
				context.addMessage(null, new FacesMessage("Amount is invalid please re-enter"));
		}
		if(context.getMessageList().size()>0){
			return(null); //redisplay the form with the error message
		}
		else{
			AddIncomeWSResponse response = new AddIncomeWSResponse();
			MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
			
			String dateAsString = date.toString();
			//date will be in this format Mon Oct 14 00:00:00 EDT 2013
			
			StringBuilder sb = new StringBuilder();
			sb.append(dateAsString.substring(0, 10));
			sb.append(",");
			sb.append(dateAsString.substring(23, 28));
			String formattedDateString = sb.toString();
			formattedDateString.trim();
			
			queryParams.add("date", formattedDateString);
			queryParams.add("source", source);
			queryParams.add("notes", notes);
			queryParams.add("amount", amount);
			queryParams.add("username", username);
			
			WSClientUtil client = new WSClientUtil();
			String addIncomeResponse = client.getWSPostResponse(queryParams, INCOME_WS_URI,
																ADD_INCOME_RESOURCE);
			
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				response = mapper.readValue(addIncomeResponse, AddIncomeWSResponse.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(response.getDataAccessResult().getStatus().equals("Success")){
				context.addMessage(null, new FacesMessage("SUCCESS: Income record has been added"));
				return (null);
			}
			else{
				return "addRecord"; //This will simply redisplay the form
			}
			
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
