package dataAccessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import model.UserExpensePerCategory;
import model.UserExpenseRecord;
import model.UserHistory;

/**
 * @author Owner
 *
 */
public class UserExpenseRecordAccessor {
	private String insertStmt;
	private Statement stmt;
	private String query;
	private ResultSet rs;

	public DataAccessResult addNewUserExpenseRecord(UserExpenseRecord uer){
		insertStmt = "Insert into UserExpenseRecord"+
					"(UserProfileID, Date, CategoryID, Vendor, "+
					"Description, Amount) Values"+
					"('"+getUserProfileID(uer.getUsername())+"', '"+getSqlDate(uer.getJDate())+"', '"+
					uer.getCategoryID()+"', '"+uer.getVendor()+"', '"+
					uer.getDescription()+"', '"+uer.getAmount()+"');";
		
		
		DataAccessResult dar = new DataAccessResult();
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{			
			stmt = conn.createStatement();
			stmt.execute(insertStmt);
			dar.setStatus("Success");
		}catch(Exception e){
			e.printStackTrace();
			dar.setStatus("Fail");
		}
		dbConnect.releaseConnection();
		
		return dar;
	}
	
	public DataAccessResult getUserExpenseHistory(List<UserHistory> userHistory, String username){
		query = "Select * from UserExpenseRecord where UserProfileID = " +
				"(select UserProfileID from UserProfile where username = '"+username+"');";
		DataAccessResult dar = new DataAccessResult();
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setCategoryID(rs.getString("CategoryID"));
				oneRecord.setVendor(rs.getString("Vendor"));
				oneRecord.setDescription(rs.getString("Description"));
				oneRecord.setDate(rs.getString("Date"));
				userHistory.add(oneRecord);
			}
			dar.setStatus("Success");
		}catch(Exception e){
			e.printStackTrace();
			dar.setStatus("Fail");
		}
		dbConnect.releaseConnection();
		
		
		return dar;
	}
	
	public void getUserExpenseHistoryByYear(Map<String,String> m, String username, String year){
		query = "Select MONTH(Date) as Month, SUM(Amount) as Total from UserExpenseRecord where UserProfileID = " +
			"(select UserProfileID from UserProfile where username = '"+username+"') and Year(Date) = '"+year+"' group by MONTH(Date);";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				m.put(rs.getString("Month"), rs.getString("Total"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getUserExpenseHistoryByYearAndMonth(Map<String,String> m, String username, String year, String month){
		query = "Select CategoryID, SUM(Amount) as Total from UserExpenseRecord where UserProfileID = " +
			"(select UserProfileID from UserProfile where username = '"+username+"') and Year(Date) = '"+year+"' "+
			"and Month(Date) = '"+month+"' group by CategoryID;";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				m.put(rs.getString("CategoryID"), rs.getString("Total"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
	}
	
	public List<UserHistory> getExpenseForSelectedMonth(int userProfileID, String year, String month){
		List<UserHistory> expenseForSelectedMonth = new ArrayList<UserHistory>();
		query = "Select * from UserExpenseRecord where UserProfileID = '"+userProfileID+"'" +
				"and Year(Date) = '"+year+"' and Month(Date) = '"+month+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setCategoryID(rs.getString("CategoryID"));
				oneRecord.setVendor(rs.getString("Vendor"));
				oneRecord.setDescription(rs.getString("Description"));
				oneRecord.setDate(rs.getString("Date"));
				expenseForSelectedMonth.add(oneRecord);
			}	
		
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
		return expenseForSelectedMonth;
		
	}
	
	public List<UserHistory> getExpenseForSelectedMonthAndCategory(int userProfileID, String year, String month, String categoryID){
		List<UserHistory> expenseForSelectedMonth = new ArrayList<UserHistory>();
		query = "Select * from UserExpenseRecord where UserProfileID = '"+userProfileID+"'" +
				"and Year(Date) = '"+year+"' and Month(Date) = '"+month+"'"+
				" and CategoryID = '"+categoryID+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setCategoryID(rs.getString("CategoryID"));
				oneRecord.setVendor(rs.getString("Vendor"));
				oneRecord.setDescription(rs.getString("Description"));
				oneRecord.setDate(rs.getString("Date"));
				expenseForSelectedMonth.add(oneRecord);
			}	
		
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
		return expenseForSelectedMonth;
		
	}

	
	public DataAccessResult getLastMonthTotalExpense(LastMonthSummary lastMonthSummary, int userID){
		query = "Select SUM(Amount) as totalExpense from UserExpenseRecord where" +
				" UserProfileID = " +
				"  '"+userID+"' and MONTH(Date) = '"+lastMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+lastMonthSummary.getYear()+"'";
				DataAccessResult dar = new DataAccessResult();
				DbConnect dbConnect = new DbConnect();
				Connection conn = dbConnect.establishConnection();
				try{
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					if(rs.next()){
						lastMonthSummary.setExpense(rs.getString("totalExpense"));
						dar.setStatus("Success");
					}
				}catch(Exception e){
					e.printStackTrace();
					dar.setStatus("Fail");
				}
				dbConnect.releaseConnection();

				return dar;
	}
	
	public void getCurrentMonthTotalExpense(CurrentMonthSummary currentMonthSummary, int userProfileID){
		query = "Select SUM(Amount) as totalExpense from UserExpenseRecord where" +
				" UserProfileID = '"+userProfileID+"'"+
				" and MONTH(Date) = '"+currentMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+currentMonthSummary.getYear()+"'";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				currentMonthSummary.setExpense(rs.getString("totalExpense"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getCurrentYearTotalExpense(CurrentYearSummary currentYearSummary, int userProfileID){
		query = "Select Sum(Amount) as totalYearExpense from UserExpenseRecord where "+
				"UserProfileID = '"+userProfileID+"'"+
				"and YEAR(Date) = '"+currentYearSummary.getYear()+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				currentYearSummary.setExpense(rs.getString("totalYearExpense"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getCurrentMonthAllExpense(List<UserExpensePerCategory> userExpensePerCategory,
						int month, int year, int userProfileID){
		query = "Select CategoryID, SUM(Amount) as Total from UserExpenseRecord"+
				" where UserProfileID = '"+userProfileID+"'"+
				" and  MONTH(Date) = '"+month+"' "+
				"and YEAR(Date) = '"+year+"' group  by CategoryID";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserExpensePerCategory oneRecord = new UserExpensePerCategory();
				oneRecord.setCategoryID(rs.getInt("CategoryID"));
				oneRecord.setExpense(rs.getString("Total"));
				userExpensePerCategory.add(oneRecord);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	private int getUserProfileID(String username){
		query = "Select UserProfileID from UserProfile where Username = '"+username+"';";
		int UserProfileID = 0;
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				UserProfileID = rs.getInt("UserProfileID");			
			}
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
		return UserProfileID;
	}
	
	private Date getSqlDate(Date date){
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		return sqlDate;
		
	}
}
