<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="/MyExpenseVersion2/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/MyExpenseVersion2/resources/css/commonStyles.css">
</head>
<body>
	<div id="container">
		<div id="header">
			
		</div>
		<div id=signUpButton">
				<input class="btn btn-primary" type="button" value="Sign Up" 
					onClick="window.location='/MyExpenseVersion2/security/registerUser.jsp'">
		</div>
		<div id="content">
			<div id="loginMessage">
				<p>Manage your finances in the following simple steps</p>
				<div class="paragraphBreak"> </div><br />
				<ul>
					<li>Sign Up!</li>
					<li>Login with the form on the right</li>
					<li>Add Income, Expense or Savings Record</li>
					<li>Review all transaction history for the current year </li>
					<li>Analyze records by year and month </li>
				</ul>
			
			</div>
			
			<div id="loginForm">
				
				<form class="form-horizontal" method="post" action="j_security_check">
				<fieldset>
					<legend>Log In</legend>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Username</label>
							<div class="controls">
					  		  <input id ="inputEmail" type="text" name="j_username">
							</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputPassword">Password</label>
						<div class="controls">
							 <input id="inputPassword" type="password" name= "j_password"><br />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<a href="/MyExpenseVersion2/security/forgotPassword.jsf">Forgot Password</a>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input class="btn btn-primary" type="submit" value="Login">
						</div>
					</div>
					</fieldset>
  				</form> 
  			</div>
			
		</div>
		<div id="footer"></div>
	</div>		
		
</body>
</html>