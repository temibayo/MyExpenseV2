package utils;


import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WSClientUtil {
	
	public String getWSResponse(final MultivaluedMap<String, String> params,
								final String URI,
								final String path){
		
		String result = null;
		try
		{			
			Client client = Client.create();		 
			WebResource webResource = client.resource(URI); 
			ClientResponse response = webResource.path(path).
									  queryParams(params).
									  accept("application/json").get(ClientResponse.class);
 
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
 			result = response.getEntity(String.class);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result; 
	}
	
}
