package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.DataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryNoteDatabaseUtils extends BaseDatabaseUtils
{
	private static final String DELETE_QUERY_NOTE_PREPARED_STATEMENT_STRING = "DELETE FROM query_note WHERE text_part = ? and created_by = ?";
	
	/**
	 * Runs the delete query note query. This query does not commit the changes so you must do that
	 * inside your test.
	 *
	 * @param connection
	 * @param textPart
	 * @param createUser
	 */
	public static void deleteQueryNote(Connection connection, String textPart, String createUser)
	{
		logger.information(String.format("Deleting query note %s made by %s", textPart, createUser));
		
		try
		{
			PreparedStatement deleteQueryNoteStatement = connection.prepareStatement(DELETE_QUERY_NOTE_PREPARED_STATEMENT_STRING);
			deleteQueryNoteStatement.setString(1, textPart);
			deleteQueryNoteStatement.setString(2, createUser);
			
			deleteQueryNoteStatement.executeUpdate();
			deleteQueryNoteStatement.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Deleting query note with text part %s failed: %s", textPart, e));
		}
	}

	public static String insertQueryNote(Connection connection, String createUser, String nodeUUID, String contentSetID)
	{
		logger.information(String.format("Inserting query note with node %s made by %s with with a content set ID of %s", nodeUUID, createUser, contentSetID));
		String noteUUID = DataMocking.generateUUID();
		try
		{
			PreparedStatement insertQueryNotePreparedStatement = connection.prepareStatement(QueryNoteDatabaseConstants.INSERT_DATE_QUERY_NOTE_PREPARED_STATEMENT_STRING);
			insertQueryNotePreparedStatement.setString(1, noteUUID);
			insertQueryNotePreparedStatement.setString(2, createUser);
			insertQueryNotePreparedStatement.setString(3, nodeUUID);
			insertQueryNotePreparedStatement.setInt(4, Integer.parseInt(contentSetID));

			insertQueryNotePreparedStatement.executeUpdate();
			commit(connection);
			insertQueryNotePreparedStatement.close();
		}
		catch(SQLException e)
		{
			Assertions.fail(String.format("Inserting query note with node %s made by %s failed: %s", nodeUUID, createUser, e));
		}
		return noteUUID;
	}

	public static void deleteQueryNote(Connection connection, String noteUUID)
	{
		logger.information(String.format("Deleting query note with noteUUID %s", noteUUID));
		try
		{
			PreparedStatement insertQueryNotePreparedStatement = connection.prepareStatement(QueryNoteDatabaseConstants.DELETE_QUERY_NOTE_UUID_PREPARED_STATEMENT_STRING);
			insertQueryNotePreparedStatement.setString(1, noteUUID);

			insertQueryNotePreparedStatement.executeUpdate();
			commit(connection);
			insertQueryNotePreparedStatement.close();
		}
		catch(SQLException e)
		{
			Assertions.fail(String.format("Deleting query note with note UUID %s failed: %s", noteUUID, e));
		}
	}

	public static void updateQueryNoteText(Connection connection, String noteUUID, String noteText)
	{
		logger.information(String.format("Updating query note  text with noteUUID %s", noteUUID));
		try
		{
			PreparedStatement updateQueryNoteTextPreparedStatement = connection.prepareStatement(QueryNoteDatabaseConstants.UPDATE_QUERY_NOTE_TEXT_PREPARED_STATEMENT_STRING);
			updateQueryNoteTextPreparedStatement.setString(1, noteText);
			updateQueryNoteTextPreparedStatement.setString(2, noteText);
			updateQueryNoteTextPreparedStatement.setString(3, noteUUID);
			updateQueryNoteTextPreparedStatement.executeUpdate();
			commit(connection);
			updateQueryNoteTextPreparedStatement.close();
		}
		catch(SQLException e)
		{
			Assertions.fail(String.format("Updating query note with note UUID %s failed: %s", noteUUID, e));
		}
	}

	public static String getNoteUuid(String nodeUuid, String contentSet, Connection connection)
	{
		logger.information(String.format("Getting current note Uuid for note with nodeUuid: %s", nodeUuid));
		String noteUuid = "";
		try
		{
			PreparedStatement getNoteUuidQuery = connection.prepareStatement(QueryNoteDatabaseConstants.GET_NOTE_UUID_QUERY);
			getNoteUuidQuery.setString(1, nodeUuid);
			getNoteUuidQuery.setInt(2, Integer.parseInt(contentSet));
			ResultSet result = getNoteUuidQuery.executeQuery();
			while (result.next())
			{
				noteUuid = result.getString("NOTE_UUID");
			}
			result.close();
			getNoteUuidQuery.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("There was an error with selecting a node using the data: nodeUUID = %s", nodeUuid));
		}
		return noteUuid;
	}
}
