<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
<link rel="stylesheet" type="text/css" href="/MyExpenseVersion2/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/MyExpenseVersion2/resources/css/commonStyles.css">
</head>
<body>

	<div id="container">
		<div id="header">
			
		</div>
		<div id=signUpButton">
				<input class="btn btn-primary" type="button" value="Login" 
					onClick="window.location='/MyExpenseVersion2/ui/home.jsf'">
		</div>
		<div id="content">
			<div id="registerMessage">
				
				<div id="gettingStartedText">GETTING STARTED!</div>
				<p>	</p>		
				<div class="paragraphBreak"> </div><br />
				<p align="left"> 
					Being in control of your finances is not easy. Our modern society is designed in such 
					a way that without care, we end  up spending much more than we make. With just a 
					little bit of effort and discipline, you can become the boss of your finances. You can 
					develop habits that help you stay out or get out of debt by following a few simple 
					practices. 
					<br /><br />
				</p>
				
				<p align="left">
					By keeping track of your incoming versus outgoing cashflow you can avoid getting your 
					finances in the red and attain a new level of awareness of how you are faring at any 
					point in time. 
					<br /><br />
				</p>
				<p align="left">
					Our site was designed to ease the process of recording cash activities and generating 
					meaningful reports that guide, inform and help you make better decisions about your 
					financial life in the present and for the future. And the good news, it's all FREE! 
					<br />
					Getting started is as easy as filling up and submitting the Sign Up form on the right.
				</p>
				
			</div>
			
			<div id="registerForm">
			
				<form class="form-horizontal" method="post" action="/MyExpenseVersion2/servlet/controller.v2.RegisterUserHandler">
					<div class="control-group">
						<label class="control-label" for="username">Username:</label>
							<div class="controls">
					  		  <input id="username" type="text" name="username">
							</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">Password:</label>
						<div class="controls">
							 <input id="password" type="password" name="password"><br />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password2">Retype Password:</label>
						<div class="controls">
							 <input id="password2" type="password" name="password2"><br />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="firstName">First Name:</label>
						<div class="controls">
							 <input id="firstName" type="text" name= "firstName"><br />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="lastName">Last Name:</label>
						<div class="controls">
							 <input id="lastName" type="text" name= "lastName"><br />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">Email:</label>
						<div class="controls">
							 <input id="email" type="text" name= "email"><br />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input class="btn btn-primary" type="submit" value="Sign Up">
						</div>
					</div>
  				</form>
  			
			</div>
		</div>
		<div id="footer"></div>
	</div>		

</body>
</html>