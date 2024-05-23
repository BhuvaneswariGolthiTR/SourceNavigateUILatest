package com.thomsonreuters.codes.codesbench.quality.utilities.database;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import oracle.jdbc.driver.OracleDriver;
import org.junit.jupiter.api.Assertions;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDatabaseUtils extends TestService
{
	private static Properties dbProps = new Properties();

	/**global statment to be filled in the test**/
	Statement stmt = null;

	private static final String UAT_HOST = "uatHost";
	private static final String UAT_USR = "uatUsername";
	private static final String UAT_PWD = "uatPassword";

	private static final String POPNAMES_UAT_HOST = "popnamesUatHost";
	private static final String POPNAMES_UAT_USR = "popnamesUatUsername";
	private static final String POPNAMES_UAT_PWD = "popnamesUatPassword";

	private static final String DEV_HOST = "devHost";
	private static final String DEV_USR = "devUsername";
	private static final String DEV_PWD = "devPassword";

	private static final String BTS_UAT_HOST = "btsUatHost";
	private static final String BTS_UAT_USR = "btsUatUsername";
	private static final String BTS_UAT_PWD = "btsUatPassword";

	private static Connection connection = null;

	private static Connection connectToDatabase(String username, String password, String hostname)
	{
		logger.information(String.format("Connecting to %s", hostname));

		try
		{
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname, username, password);
			
			connection.setAutoCommit(false);

			return connection;
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("There was an error connecting to database %s: %s", hostname, e.toString()));
		}
		return null;
	}

	public static Connection connectToDatabasePopnamesUAT()
	{
		loadProperties();
		return connectToDatabase(dbProps.getProperty(POPNAMES_UAT_USR), dbProps.getProperty(POPNAMES_UAT_PWD), dbProps.getProperty(POPNAMES_UAT_HOST));
	}

	public static Connection connectToDatabaseUAT()
	{
		loadProperties();
		return connectToDatabase(dbProps.getProperty(UAT_USR), dbProps.getProperty(UAT_PWD), dbProps.getProperty(UAT_HOST));
	}

	public static Connection connectToDatabaseDEV()
	{
		loadProperties();
		return connectToDatabase(dbProps.getProperty(DEV_USR), dbProps.getProperty(DEV_PWD), dbProps.getProperty(DEV_HOST));
	}

	public static Connection connectToDatabaseBtsUAT()
	{
		loadProperties();
		return connectToDatabase(dbProps.getProperty(BTS_UAT_USR), dbProps.getProperty(BTS_UAT_PWD), dbProps.getProperty(BTS_UAT_HOST));
	}


	public static void disconnect(Connection connection)
	{
		if(connection != null && !isClosed(connection))
		{
			try
			{
				connection.close();
				logger.information("Disconnection from database successful");
			}
			catch (SQLException e)
			{
				logger.warning("Error when disconnecting from database: " + e.toString());
			}
		}
	}

	public static void disconnect()
	{
		if(connection != null && !isClosed(connection))
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				logger.warning("Error when disconnecting from database: " + e.toString());
			}
		}
	}

	public static boolean isClosed(Connection connection)
	{
		boolean isClosed = false;
		try
		{
			isClosed = connection.isClosed();
		}
		catch (SQLException e)
		{
			logger.warning("Error when checking if the connection is already closed");
		}
		return isClosed;
	}

	public static void commit(Connection connection)
	{
		try
		{
			connection.commit();
		}
		catch (SQLException e)
		{
			Assertions.fail("Error when commiting changes: " + e.toString());
		}
	}

	public static void rollback(Connection connection)
	{
		try
		{
			connection.rollback();
		}
		catch (SQLException e)
		{
			Assertions.fail("Error when rolling back changes: " + e.toString());
		}
	}

	private static void loadProperties()
	{
		try
		{
			dbProps.load(new FileInputStream("commonFiles/properties/database.properties"));
		}
		catch (Exception e)
		{
			Assertions.fail("Database properties were not loaded: " + e.toString());
		}
	}

	/**
	 * Executes the passed in query on the database and returns the results.
	 *
	 * @author Selvedin Alic
	 * @param query query to be executed
	 * @return the results of the query
	 * @throws SQLException encountered exception during query execution
	 */
	public  ResultSet executeQuery(Connection conn, String query)
	{
		ResultSet queryResultSet = null;

		try
		{
			stmt = conn.createStatement();
			queryResultSet = stmt.executeQuery(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return queryResultSet;
	}

	/**
	 * Executes the passed in query on the database and returns the results.
	 *
	 * @author Selvedin Alic
	 * @return the results of the query
	 * @throws SQLException encountered exception during query execution
	 */
	public void executeUpdate(Connection conn, String update)
	{
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(update);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}