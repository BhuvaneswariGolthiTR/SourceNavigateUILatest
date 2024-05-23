package com.thomsonreuters.codes.codesbench.quality.utilities.database.source;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BtsDatabaseUtils extends BaseDatabaseUtils
{
	private static final String DELETE_BTS_RECORD_PREPARED_STATEMENT_STRING = "DELETE FROM STATEBILL WHERE ADDUSERID = ? AND STATEABBREVIATION = ?  AND BILLNUMBER = ? ";

	/**
	 * Runs the delete bts Record query. This query does not commit the changes so you must do that
	 * inside your test.
	 *
	 * @param connection
	 * @param addUserId
	 * @param stateAbbreviation
	 * @param billNumber
	 */
	public static void deleteBtsRecord(Connection connection, String addUserId , String stateAbbreviation, String billNumber)
	{
		logger.information(String.format("Deleting bts record which has the specific information: %s, %s,  %s", addUserId, stateAbbreviation,  billNumber));
		
		try
		{
			PreparedStatement deleteBtsRecordStatement = connection.prepareStatement(DELETE_BTS_RECORD_PREPARED_STATEMENT_STRING);
			deleteBtsRecordStatement.setString(1, addUserId);
			deleteBtsRecordStatement.setString(2, stateAbbreviation);
			deleteBtsRecordStatement.setString(3, billNumber);
			
			boolean rowsWereUpdated =  deleteBtsRecordStatement.executeUpdate() >= 1;
			deleteBtsRecordStatement.close();
			Assertions.assertTrue(rowsWereUpdated, "The rows were not updated and the query is incorrect");
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Deleting bts record failed for the specific information: %s, %s,  %s", addUserId, stateAbbreviation, billNumber,  e.toString()));
		}
	}
}
