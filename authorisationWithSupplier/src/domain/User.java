package domain;

public interface User {

	String getEmail();

	void setEmail(String email);
	
	boolean isLogin();
	
	void setLogin(boolean login);

	String getEncryptedPassword();

	void setEncryptedPassword(String password);

}