package presentation;


import api.OrganisationAPI;
import api.OrganisationAPIImpl;
import api.SecurityAPI;
import api.SecurityAPIImpl;

public class Main {

	public static void main(String[] args) {

	
	
	
	OrganisationAPI organisationAPI = new OrganisationAPIImpl();
	System.out.println(organisationAPI.getAllChildren(0));
	System.out.println(organisationAPI.getOrganisationWithNoParents());
	System.out.println(organisationAPI.searchOrganization("sko"));
	
	SecurityAPI securityAPI = new SecurityAPIImpl();
	System.out.println(securityAPI.hasUserAccessToOrganisationUnit("juy@me.com", 0, 5));
	
	
	}
}
