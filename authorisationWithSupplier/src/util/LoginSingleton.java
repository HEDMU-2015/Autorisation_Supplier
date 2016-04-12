package util;

public class LoginSingleton {
	private static LoginSingleton userloggedin = new LoginSingleton();
	private String userEmail = null;
	
	private LoginSingleton() {
	}
	
	public static LoginSingleton instance() {
		return userloggedin;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
