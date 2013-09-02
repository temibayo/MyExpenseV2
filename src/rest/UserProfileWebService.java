package rest;

import restImpl.serviceResponse.UserProfileWSResponse;

public interface UserProfileWebService {
	
	public UserProfileWSResponse registerUser(String username,
											  String password,
											  String firstName,
											  String lastName,
											  String email);
	
}
