package dataAccessor;

import java.sql.ResultSet;
import java.sql.Statement;

public class CommonAccessor {
	protected String insertStmt;
	protected Statement stmt;
	protected String query;
	protected ResultSet rs;
	
	protected String getInsertStmt() {
		return insertStmt;
	}
	protected void setInsertStmt(String insertStmt) {
		this.insertStmt = insertStmt;
	}
	protected Statement getStmt() {
		return stmt;
	}
	protected void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	protected String getQuery() {
		return query;
	}
	protected void setQuery(String query) {
		this.query = query;
	}
	protected ResultSet getRs() {
		return rs;
	}
	protected void setRs(ResultSet rs) {
		this.rs = rs;
	}
}
