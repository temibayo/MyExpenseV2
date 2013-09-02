package dataAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
	
	private Connection conn = null;
	private String url = "jdbc:mysql://localhost/MyExpenseV2";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root"; 
	private String password = "adepoju83";
	
	
	
	public Connection establishConnection(){
		
		try{
			 Class.forName(this.driver).newInstance();
			 this.conn = DriverManager.getConnection(url,userName,password);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return this.conn;
		
	}
	
	public void releaseConnection(){
		
		try{
			this.conn.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
