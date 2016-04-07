package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.PersistenceCommitFailureException;
import exception.PersistenceConnectionFailureException;

public class DataAccessImpl implements DataAccess {
	private static final String CONNECTION_URL = "jdbc:hsqldb:hsql://localhost/mydatabase";
	private static final String DB_USER = "SA";
	private static final String DB_PASSWORD = "";
	private Connection connection = null;

	public DataAccessImpl(){
		try {
			this.connection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to connect to database");		
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void commit()  {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to connect to database");		
		}
	}

	@Override
	public void close() {
		if(connection != null) {
			try{
				connection.close();
			} catch(SQLException exc) {
				// Should already be closed
			}
		}
	}

	@Override
	public void rollback() {
		if(connection != null) {
			try{
				connection.rollback();
			} catch(SQLException exc) {
				// This should never happen
			}
		}

	}

}
