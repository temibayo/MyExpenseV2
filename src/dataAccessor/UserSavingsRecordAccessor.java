package dataAccessor;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.CurrentMonthSummary;
import model.CurrentYearSummary;
import model.LastMonthSummary;
import model.UserSavingsRecord;
import model.UserHistory;



public class UserSavingsRecordAccessor extends CommonAccessor{
	
	public DataAccessResult addUserSavingsRecord(UserSavingsRecord usr){
		insertStmt = "Insert into UserSavingsRecord"+
					"(UserProfileID, Date, Source, Notes, Amount)"+
					" Values"+
					"('"+getUserProfileID(usr.getUsername())+"', '"+getSqlDate(usr.getjDate())+"', '"+
					usr.getSource()+"', '"+usr.getNotes()+"', '"+
					usr.getAmount()+"');"; 
		DataAccessResult dar = new DataAccessResult();
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			stmt.execute(insertStmt);
			dar.setStatus("Success");
		}
		catch(Exception e){
			e.printStackTrace();
			dar.setStatus("Fail");
		}
		dbConnect.releaseConnection();
		
		return dar;
	}
	
	public void getUserSavingsHistory(List<UserHistory> userSavingsHistory, String username){
		query = "Select * from UserSavingsRecord where UserProfileID = "+
				"(Select UserProfileID from UserProfile where username = '"+username+"');";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UserHistory oneRecord = new UserHistory();
				oneRecord.setDate(rs.getString("Date"));
				oneRecord.setSource(rs.getString("Source"));
				oneRecord.setNotes(rs.getString("Notes"));
				oneRecord.setAmount(rs.getString("Amount"));
				userSavingsHistory.add(oneRecord);
						
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getUserSavingsHistoryByYear(Map<String,String> m, String username, String year){
		query = "Select MONTH(Date) as Month, SUM(Amount) as Total from UserSavingsRecord where UserProfileID = " +
			"(select UserProfileID from UserProfile where username = '"+username+"') and Year(Date) = '"+year+"'  group by MONTH(Date);";
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
	
	public void getLastMonthTotalSavings(LastMonthSummary lastMonthSummary, int userProfileID){
		query = "Select SUM(Amount) as totalSavings from UserSavingsRecord where" +
				" UserProfileID = '"+userProfileID+"'"+
				" and MONTH(Date) = '"+lastMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+lastMonthSummary.getYear()+"'";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				lastMonthSummary.setSavings(rs.getString("totalSavings"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getCurrentMonthTotalSavings(CurrentMonthSummary currentMonthSummary, int userProfileID){
		query = "Select SUM(Amount) as totalSavings from UserSavingsRecord where" +
				" UserProfileID = '"+userProfileID+"'"+
				" and MONTH(Date) = '"+currentMonthSummary.getMonth()+"'" +
				" and YEAR(Date) = '"+currentMonthSummary.getYear()+"'";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				currentMonthSummary.setSavings(rs.getString("totalSavings"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		dbConnect.releaseConnection();
	}
	
	public void getCurrentYearTotalSavings(CurrentYearSummary currentYearSummary, int userProfileID){
		query = "Select Sum(Amount) as totalYearSavings from UserSavingsRecord where "+
				"UserProfileID = '"+userProfileID+"'"+
				"and YEAR(Date) = '"+currentYearSummary.getYear()+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				currentYearSummary.setSavings(rs.getString("totalYearSavings"));
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
