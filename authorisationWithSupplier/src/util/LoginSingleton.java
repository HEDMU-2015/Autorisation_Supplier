package util;

import domain.User;

public class LoginSingleton {
	private static LoginSingleton userloggedin = new LoginSingleton();
	private User user = null;
	
	private LoginSingleton() {
	}
	
	public static LoginSingleton instance() {
		return userloggedin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
