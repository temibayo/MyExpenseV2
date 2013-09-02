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
import model.UserHistory;
import model.UserIncomeRecord;

public class UserIncomeRecordAccessor {
	
	private String insertStmt;
	private String query;
	private Statement stmt;
	private ResultSet rs;

	
	public DataAccessResult addNewUserIncomeRecord(UserIncomeRecord uir){
		insertStmt = "Insert into UserIncomeRecord"+
					"(UserProfileID, Date, Source, Notes, Amount) Values"+
					"('"+getUserProfileID(uir.getUsername())+"', '"+getSqlDate(uir.getjDate())+"', '"+
					uir.getSource()+"', '"+uir.getNotes()+"', '"+uir.getAmount()+"');";

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
	
	public void updateUserIncomeRecord(UserHistory record, String username){
		insertStmt = "Update UserIncomeRecord "+
					 "set Amount = '"+record.getAmount()+"' "+
					 "where UserIncomeID = '"+record.getId()+"' "+
					 "and UserProfileID = '"+getUserProfileID(username)+"';";
		DbConnect dbConnect = new DbConnect();
		try{			
			Connection conn = dbConnect.establishConnection();
			stmt = conn.createStatement();
			stmt.execute(insertStmt);
			dbConnect.releaseConnection();
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
	}
			
	
	/**
	 * @param m
	 * @param username
	 * @param year
	 * This method is currently used on the Analysis pages
	 */
	public void getUserIncomeHistoryByYear(Map<String,String> m, String username, String year){
		query = "Select MONTH(Date) as Month, SUM(Amount) as Total from UserIncomeRecord where UserProfileID = " +
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
	
	/**
	 * @param userHistory
	 * @param username
	 * @return
	 * 
	 * This method is used on the History page - income tab to refresh the table
	 */
	public void getUserIncomeHistoryByYearAndMonth(List<UserHistory> userHistory, 
																String year, String month, String username){
		
		query = "Select * from UserIncomeRecord where UserProfileID = " +
				"(select UserProfileID from UserProfile where username = '"+username+"') and " +
						"Year(Date) = '"+year+"' and Month(Date) = '"+month+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setNotes(rs.getString("Notes"));
				oneRecord.setSource(rs.getString("Source"));
				oneRecord.setDate(rs.getString("Date"));
				userHistory.add(oneRecord);
			}
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
	}
	
	
	
	public DataAccessResult getUserIncomeHistory(List<UserHistory> userHistory, String username){
		
		query = "Select * from UserIncomeRecord where UserProfileID = " +
				"(select UserProfileID from UserProfile where username = '"+username+"');";
		DataAccessResult dar = new DataAccessResult();
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setId(rs.getString("UserIncomeID"));
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setNotes(rs.getString("Notes"));
				oneRecord.setSource(rs.getString("Source"));
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
	
	public DataAccessResult getLastMonthTotalIncome(LastMonthSummary lastMonthSummary, int userProfileID){
		query = "Select SUM(Amount) as totalIncome from UserIncomeRecord where" +
				" UserProfileID = '"+userProfileID+"'"+
				" and MONTH(Date) = '"+lastMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+lastMonthSummary.getYear()+"'";
		DataAccessResult dar = new DataAccessResult();
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				lastMonthSummary.setIncome(rs.getString("totalIncome"));
				dar.setStatus("Success");
			}
		}catch(Exception e){
			e.printStackTrace();
			dar.setStatus("Fail");
		}
		dbConnect.releaseConnection();
		
		return dar;
	}
	
	/**
	 * @return
	 * 
	 * This method is used by the income table on the Reports page
	 */
	public List<UserHistory> getIncomeForSelectedMonth(int userProfileID, String year, String month){
		List<UserHistory> incomeForSelectedMonth = new ArrayList<UserHistory>();
		
		query = "Select * from UserIncomeRecord where UserProfileID = '"+userProfileID+"'"+
				"and Year(Date) = '"+year+"' and Month(Date) = '"+month+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setAmount(rs.getString("Amount"));
				oneRecord.setNotes(rs.getString("Notes"));
				oneRecord.setSource(rs.getString("Source"));
				oneRecord.setDate(rs.getString("Date"));
				incomeForSelectedMonth.add(oneRecord);
			}
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
		return incomeForSelectedMonth;
	}
	
	public void getCurrentMonthTotalIncome(CurrentMonthSummary currentMonthSummary, int userProfileID){
		query = "Select SUM(Amount) as totalIncome from UserIncomeRecord where" +
				" UserProfileID = '"+userProfileID+"'"+
				" and MONTH(Date) = '"+currentMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+currentMonthSummary.getYear()+"'";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				currentMonthSummary.setIncome(rs.getString("totalIncome"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getCurrentYearTotalIncome(CurrentYearSummary currentYearSummary, int userProfileID){
		query = "Select Sum(Amount) as totalYearIncome from UserIncomeRecord where "+
				"UserProfileID = '"+userProfileID+"'"+
				"and YEAR(Date) = '"+currentYearSummary.getYear()+"';";
				DbConnect dbConnect = new DbConnect();
				Connection conn = dbConnect.establishConnection();
				try{
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					if(rs.next()){
						currentYearSummary.setIncome(rs.getString("totalYearIncome"));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				dbConnect.releaseConnection();
	}
	
	private int getUserProfileID(String username){
		query ="Select UserProfileID from userprofile where username = '"+username+"';";
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
		}
		dbConnect.releaseConnection();
		return UserProfileID;
	}
	
	private Date getSqlDate(Date date){
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
}
