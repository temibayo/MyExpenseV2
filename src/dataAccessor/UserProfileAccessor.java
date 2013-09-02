package dataAccessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import model.UserProfile;

public class UserProfileAccessor {
	private ResultSet rs;
	private String query;
	private Statement statement;
	private String doesRecordExist;
	private String dbInsert;
	private String insertRoles;
	private String dbSelect;
	 
	
	public DataAccessResult authenticateUser(UserProfile up){
		DataAccessResult dar = new DataAccessResult();
		query = "select count(*) as authResult from UserProfile where UserName = '"+up.getUsername()+
		            "'and Password = '"+up.getPassword()+"';";
		try{
			DbConnect conn = new DbConnect();
			Connection connection = conn.establishConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			if(rs.next()){
				if(rs.getInt("authResult") > 0){
					dar.setStatus("Success");
				}
				else{
					dar.setStatus("Failed");
				}
			}
			conn.releaseConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
				
		return dar;
	}
	
	public DataAccessResult createUser(UserProfile up){
		
		doesRecordExist = "select * from UserProfile  where username = '"+up.getUsername()+
						  "' or email = '"+up.getEmail()+"';";
		dbInsert = "Insert into UserProfile(Username, Password, Email, FirstName, LastName) "
					+"Values('"+up.getUsername()+"', '"+up.getPassword()+"', '"+up.getEmail()+"', '" 
					+up.getFirstName()+"',	'"+up.getLastName()+"');";
		insertRoles = "Insert into user_roles(Username, Rolename) Values"+
					  "('"+up.getUsername()+"', 'user');";
		DataAccessResult dar = new DataAccessResult();
	
		try{
			DbConnect conn = new DbConnect();
			Connection connection = conn.establishConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(doesRecordExist);
			if(rs.next()){
				System.out.println(rs.getString("Email"));
				dar.setStatus("Failed");
			}
			else{
				statement.execute(dbInsert);
				statement.execute(insertRoles);
				dar.setStatus("Success");
			}
			conn.releaseConnection();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return dar;
	}
	
	
	
	public boolean isExistingEmail(String email){		
		query = "Select * from UserProfile where Email = '"+email+"';";
		boolean result = false;
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			if(rs.next()){
				result = true;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
		return result;
	}
	
	public void updateUserPassword(String email, String password){
		query = "Update UserProfile set Password = '"+password+"'"+
				" where Email = '"+email+"';";
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			statement = conn.createStatement();
			statement.executeUpdate(query);
		}catch(Exception e){
			e.printStackTrace();
			dbConnect.releaseConnection();
		}
		dbConnect.releaseConnection();
	}
	
	public void getUserProfile(UserProfile up){
		dbSelect = "select * from UserProfile  where username = '"+up.getUsername()+"';";
		DbConnect conn = new DbConnect();
		Connection connection = conn.establishConnection();
		try{
			statement = connection.createStatement();
			rs = statement.executeQuery(dbSelect);
			if(rs.next()){
				up.setEmail(rs.getString("Email"));
				up.setFirstName(rs.getString("firstName"));
				up.setLastName(rs.getString("lastName"));
				up.setPassword(rs.getString("Password"));
			}
			conn.releaseConnection();
		}
		catch(Exception e){
			e.printStackTrace();
			conn.releaseConnection();
		}
	}
	
	public int getUserProfileID(String username){
		query = "Select UserProfileID from UserProfile where Username = '"+username+"';";
		int UserProfileID = 0;
		DbConnect dbConnect = new DbConnect();
		Connection conn = dbConnect.establishConnection();
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
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

}
