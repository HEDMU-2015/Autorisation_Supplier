package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserImpl implements User {

	private String email;
	private String encryptedPassword;
	private boolean login = false;

	@Override
	public boolean isLogin() {
		return login;
	}

	@Override
	public void setLogin(boolean login) {
		this.login = login;
	}

	public static String encrypt(String txt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] mdbytes = md.digest(txt.getBytes());
		return Base64.getEncoder().encodeToString(mdbytes);
	}
	
	@Override
	public String getEmail() {
		return email;
	}

	
	@Override
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	@Override
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	@Override
	public void setAndEncryptPassword(String password) throws NoSuchAlgorithmException {
		this.encryptedPassword = encrypt(password);
	}
	
	@Override
	public String toString() {
		return "UserImpl [email=" + email + ", password=" + encryptedPassword + "]";
	}
	
	

}
