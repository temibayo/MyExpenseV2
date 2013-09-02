package controller.v2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import restImpl.serviceResponse.UserProfileWSResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import dataAccessor.DataAccessResult;
import dataAccessor.UserProfileAccessor;
import model.UserProfile;
/**
 * This is version 2 of the RegisterUserHandler servlet in the controller package
 * This servlet does not make a direct call to the DataAccessor 
 * Instead it calls the RegisterUser method of the UserProfile Web Service
 */
public class RegisterUserHandler extends HttpServlet {
	
	private final String URI = "http://localhost/MyExpenseVersion2/restImpl/userProfileWebServiceImpl";
	private final String PATH = "registerUser";
	private final int HTTP_STATUS_OK = 200;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserProfileWSResponse upWSResponse = new UserProfileWSResponse();
		
		UserProfile up = new UserProfile();
		String p = request.getParameter("password");
		up.setUsername(request.getParameter("username"));
		up.setPassword(p); //A bug is setting the Userprofile password to null. fixed by declaring String p
		up.setFirstName(request.getParameter("firstName"));
		up.setLastName(request.getParameter("lastName"));
		up.setEmail(request.getParameter("email"));
		
		if(validateRequestObject(up)){
			
			try{
				//Create a client object
				Client client = Client.create();
				WebResource webResource = client.resource(URI);
				
				//Make the WebService call
				ClientResponse clientResponse = webResource.path(PATH).path(up.getUsername()).path(up.getPassword()).path(up.getFirstName()).path(up.getLastName()).path(up.getEmail()).accept("application/json").post(ClientResponse.class);
				
				if (clientResponse.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ clientResponse.getStatus());
				}
				String output = clientResponse.getEntity(String.class);
				
				//Convert json output back to POJO
				ObjectMapper mapper = new ObjectMapper();
				upWSResponse = mapper.readValue(output, UserProfileWSResponse.class);
				

			}catch(Exception e) {
				e.printStackTrace();
			}
			if(upWSResponse.getStatus().equals("SUCCESS")){
				response.sendRedirect("/MyExpenseVersion2/ui/home.jsf");
			}
			else {
				response.sendRedirect("/MyExpenseVersion2/security/registerUserV2.jsp");
			}
		}
		else {
			response.sendRedirect("/MyExpenseVersion2/security/registerUserV2.jsp");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

}
