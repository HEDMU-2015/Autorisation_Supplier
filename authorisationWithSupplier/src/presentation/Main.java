package presentation;


import java.security.NoSuchAlgorithmException;

import api.OrganisationAPI;
import api.OrganisationAPIImpl;
import api.SecurityAPI;
import api.SecurityAPIImpl;
import domain.User;
import domain.UserImpl;
import logic.Controller;
import logic.ControllerImpl;

public class Main {

	public static void main(String[] args) {

	
	
	
	OrganisationAPI organisationAPI = new OrganisationAPIImpl();
	System.out.println(organisationAPI.getAllChildren(0));
	System.out.println(organisationAPI.getOrganisationWithNoParents());
	System.out.println(organisationAPI.searchOrganization("sko"));
	
	SecurityAPI securityAPI = new SecurityAPIImpl();
	System.out.println(securityAPI.hasUserAccessToOrganisationUnit("juy@me.com", 0, 5));
	
	Controller controller = new ControllerImpl();
	User user = new UserImpl();
//	
//	user.setEmail("jesp@me.com");
//	try {
//		user.setAndEncryptPassword("123");
//	} catch (NoSuchAlgorithmException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	System.out.println(user);
//	System.out.println(user.getEncryptedPassword());	
//	controller.createUser(user);
	
	
	
	
	}
}
