package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataAccessor.DataAccessResult;
import dataAccessor.UserProfileAccessor;
import model.UserProfile;
/**
 * Servlet implementation class RegisterUserHandler
 */
public class RegisterUserHandler extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserProfile up = new UserProfile();
		String p = request.getParameter("password");
		up.setUsername(request.getParameter("username"));
		up.setPassword(p); //A bug is setting the Userprofile password to null. fixed by declaring String p
		up.setFirstName(request.getParameter("firstName"));
		up.setLastName(request.getParameter("lastName"));
		up.setEmail(request.getParameter("email"));
		if(validateRequestObject(up)){
			UserProfileAccessor upa = new UserProfileAccessor();
			DataAccessResult dar = new DataAccessResult();
			dar = upa.createUser(up);
			if(dar.getStatus().equals("Success")){
				response.sendRedirect("/MyExpenseVersion2/ui/home.jsf");
			}
			else {
				response.sendRedirect("/MyExpenseVersion2/security/registerUser.jsp");
			}
		}
		else {
			response.sendRedirect("/MyExpenseVersion2/security/registerUser.jsp");
		}
	}
	
	private boolean validateRequestObject(UserProfile up){
		if(up.getPassword() == "" || up.getEmail() == "" || up.getUsername() == ""){
			return false;
		}
		else{
			return true;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
