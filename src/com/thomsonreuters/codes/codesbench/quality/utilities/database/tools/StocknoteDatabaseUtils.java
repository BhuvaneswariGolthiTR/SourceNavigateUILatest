package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StocknoteDatabaseUtils extends BaseDatabaseUtils
{
	private static final String DELETE_STOCKNOTE_PREPARED_STATEMENT_STRING = "DELETE FROM stocknote WHERE name = ? and create_user = ?";
	private static final String GET_HOTKEYS_AND_STOCKNOTE_NAMES_FOR_CONTENT_SET_ID_STATEMENT_STRING = "select HOTKEY, NAME from STOCKNOTE where JS_CONTENT_SET_ID = ? and HOTKEY IS NOT NULL order by HOTKEY";
	
	/**
	 * Runs the delete stocknote query. This query does not commit the changes so you must do that
	 * inside your test.
	 *
	 * @param connection
	 * @param stocknoteName
	 * @param createUser
	 */
	public static void deleteStocknote(Connection connection, String stocknoteName, String createUser)
	{
		logger.information(String.format("Deleting stocknote %s made by %s", stocknoteName, createUser));
		
		try
		{
			PreparedStatement deleteStocknoteStatement = connection.prepareStatement(DELETE_STOCKNOTE_PREPARED_STATEMENT_STRING);
			deleteStocknoteStatement.setString(1, stocknoteName);
			deleteStocknoteStatement.setString(2, createUser);
			
			deleteStocknoteStatement.executeUpdate();
			deleteStocknoteStatement.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Deleting stocknote %s failed: %s", stocknoteName, e.toString()));
		}
	}

	public static List<List<String>> getHotKeysAndStocknoteNamesForContentSetID(Connection connection, int contentSetID)
	{
		List<List<String>> hotKeysAndNamePairs = new ArrayList<>();
		hotKeysAndNamePairs.add(Arrays.asList("Hotkey", "Stocknote Name"));
		logger.information(String.format("Getting stocknote hotkeys for content set: %s", contentSetID));

		try
		{
			PreparedStatement getHotKeysAndStocknoteNamesForContentSetIDStatement = connection.prepareStatement(GET_HOTKEYS_AND_STOCKNOTE_NAMES_FOR_CONTENT_SET_ID_STATEMENT_STRING);
			getHotKeysAndStocknoteNamesForContentSetIDStatement.setInt(1, contentSetID);
			ResultSet results = getHotKeysAndStocknoteNamesForContentSetIDStatement.executeQuery();
			while (results.next())
			{
				String hotKey = results.getString("HOTKEY");
				String name = results.getString("NAME");
				if(!hotKeysAndNamePairs.contains(Arrays.asList(hotKey, name)))
				{
					hotKeysAndNamePairs.add(Arrays.asList(hotKey, name));
				}
			}
			results.close();
			getHotKeysAndStocknoteNamesForContentSetIDStatement.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Getting stocknote hotkeys for content set: %s\n%s", contentSetID, e.toString()));
		}
		return hotKeysAndNamePairs;
	}
}
