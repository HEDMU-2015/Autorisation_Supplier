package persistence;

import java.sql.Connection;

import exception.PersistenceCommitFailureException;

public interface DataAccess {

	public Connection getConnection();

	public void commit();

	public void close();

	public void rollback();

}