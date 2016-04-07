package exception;

public class PersistenceConnectionFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersistenceConnectionFailureException(String message) {
		super(message);
	}

}
