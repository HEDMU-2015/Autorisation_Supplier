package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.PersistenceCommitFailureException;
import exception.PersistenceConnectionFailureException;

public class ConnectionHandler {

	private static ConnectionHandler handler = new ConnectionHandler();
	private Connection conn;
	private int level = 0;

	private ConnectionHandler() {

	}

	public static ConnectionHandler instance() {
		return handler;
	}

	public Connection getConnection() throws PersistenceConnectionFailureException {
		if (level == 0) {
			try {
				this.conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydatabase", "SA", "");
				this.conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new PersistenceConnectionFailureException("Failed to connect to database");
			}
		}
		level++;
		return this.conn;
	}

	public void commit() throws PersistenceCommitFailureException {
		if (level == 1) {
			try {
				this.conn.commit();
			} catch (SQLException e) {
				throw new PersistenceCommitFailureException("Failed to commit transaction");
			}
		}
	}

	public void rollback() {
		if (level == 1) {
			try {
				this.conn.rollback();
			} catch (SQLException e) {
				throw new RuntimeException("Exception caught", e);
			}
		}
	}

	public void close() {
		if (level == 1) {
			try {
				this.conn.close();
				this.conn = null;
			} catch (SQLException e) {
				throw new RuntimeException("Exception caught", e);
			}
		}
		level--;
	}

}
