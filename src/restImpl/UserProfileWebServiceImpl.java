package restImpl;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dataAccessor.DataAccessResult;
import dataAccessor.UserProfileAccessor;

import model.UserProfile;

import rest.UserProfileWebService;
import restImpl.serviceResponse.UserProfileWSResponse;

@Path("/userProfileWebServiceImpl")
public class UserProfileWebServiceImpl implements UserProfileWebService {

	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerUser/{username}/{password}/{firstName}/{lastName}/{email}")
	public UserProfileWSResponse registerUser(@PathParam("username") String username,
											  @PathParam("password") String password,
											  @PathParam("firstName") String firstName,
											  @PathParam("lastName") String lastName,
											  @PathParam("email") String email) {
		
		UserProfileWSResponse wsResponse = new UserProfileWSResponse();

		UserProfile up = new UserProfile();
		up.setEmail(email);
		up.setFirstName(firstName);
		up.setFirstName(lastName);
		up.setPassword(password);
		up.setPassword(username);
		

		UserProfileAccessor upa = new UserProfileAccessor();
		DataAccessResult dar = new DataAccessResult();
		
		dar = upa.createUser(up);
		if(dar.getStatus().equals("Success")){
			wsResponse.setStatus("SUCCESS");
		}
		else{
			wsResponse.setStatus("FAIL");
		}
		
		
		return wsResponse;
		
	}

}
