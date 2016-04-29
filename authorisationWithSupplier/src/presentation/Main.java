package presentation;


import java.security.NoSuchAlgorithmException;

import api.OrganisationAPI;
import api.OrganisationAPIImpl;
import api.SecurityAPI;
import api.SecurityAPIImpl;
import domain.Organisation;
import domain.OrganisationImpl;
import domain.User;
import domain.UserImpl;
import logic.Controller;
import logic.ControllerImpl;

public class Main {

	public static void main(String[] args) {




		OrganisationAPI organisationAPI = new OrganisationAPIImpl();
		System.out.println("******* get all children of organisation id - 0 : \n" + organisationAPI.getAllChildren(0));
		System.out.println("******* organisation without parents : \n" + organisationAPI.getOrganisationWithNoParents());
		System.out.println("******* organisation with name - sko \n" + organisationAPI.searchOrganization("sko"));

		SecurityAPI securityAPI = new SecurityAPIImpl();
		System.out.println("******* juy@me.com : has Access : \n" + securityAPI.hasUserAccessToOrganisationUnit("juy@me.com", 0, 5));

		Controller controller = new ControllerImpl();
		User user = new UserImpl();
		//	
		user.setEmail("jesp@me.com");
		try {
			user.setAndEncryptPassword("123");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("******* encrypted password : \n" + user.getEncryptedPassword());
		//	System.out.println(user);
		//	System.out.println(user.getEncryptedPassword());	
		//	controller.createUser(user);

		System.out.println("******* login : \n" + securityAPI.login(user.getEmail(), user.getEncryptedPassword()));	
		System.out.println("******* check logged in user id : \n" + securityAPI.getIdOfUserLoggedIn());
		
		System.out.println( "******* " + user.getEmail() + " has access? : \n"  + securityAPI.hasUserAccessToOrganisationUnit("juy@me.com", 5, 0));
	
		
		Organisation organisation = new OrganisationImpl();
//		organisation.setName("ParkSkolen");
//		organisation.setParentID(2);
//		controller.createOrganisation(organisation);
//		System.out.println("******* create organisation : \n" + controller.readOrganisation(4));

		System.out.println(organisationAPI.getAllOrganisation());
	}
}
