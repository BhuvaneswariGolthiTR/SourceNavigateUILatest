package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.DataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScriptMaintenanceDatabaseUtils extends BaseDatabaseUtils
{
//	private static List<String> deepDeleteStatementStrings = new ArrayList<>();

	// Don't heck up the order or you will crash the database
//	static
//	{
//		deepDeleteStatementStrings.add("DELETE FROM PUB_TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM QUERY_NOTE WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_ATTRIBUTES WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//
//		deepDeleteStatementStrings.add("delete from script_command_end where script_command_uuid in (select script_command_uuid from script_command where script_id in (select script_id from script where script_num = ? and long_name = ? and created_by = ? and script_location_id = 1))");
//
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_COMMAND WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_DEPENDENCY WHERE DEPENDANT_ON_SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_DEPENDENCY WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_VOLUME_STATUS WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT_VALID_CONTENT_SET WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
//		deepDeleteStatementStrings.add("DELETE FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1");
//	}

	public static void deepDeleteScriptMaintenanceTestingTags(Connection connection, String longName, String createUser, String pubTag)
	{
		logger.information(String.format("Deep Deleting Script Maintenance Tag[longName: %s, createdBy: %s]", longName, createUser));
		// Don't heck up the order try blocks or you will crash the database
		Set<Integer> scriptNums = getScriptNums(connection, longName, createUser);
		for (String statement : ScriptMaintenanceDatabaseConstants.deepDeleteStatementStrings)
		{
			for (Integer scriptNum : scriptNums)
			{
				try
				{
					PreparedStatement deepDeleteScriptMaintenanceQuery = connection.prepareStatement(statement);
					deepDeleteScriptMaintenanceQuery.setInt(1, scriptNum);
					deepDeleteScriptMaintenanceQuery.setString(2, longName);
					deepDeleteScriptMaintenanceQuery.setString(3, createUser);
					deepDeleteScriptMaintenanceQuery.executeUpdate();
					deepDeleteScriptMaintenanceQuery.close();
					commit(connection);
				}
				catch (SQLException e)
				{
					Assertions.fail(String.format("Deep Deleting Script Maintenance Tag[scriptID: %s, createdBy: %s] failed: %s", longName, createUser, e.toString()));
				}
			}
		}

		for (Integer scriptNum : scriptNums)
		{
			try
			{
				PreparedStatement deepDeleteScriptMaintenanceQuery = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.DELETE_FROM_SCRIPT_PUBTAG_STATEMENT_STRING);
				deepDeleteScriptMaintenanceQuery.setInt(1, scriptNum);
				deepDeleteScriptMaintenanceQuery.setString(2, pubTag);
				deepDeleteScriptMaintenanceQuery.executeUpdate();
				deepDeleteScriptMaintenanceQuery.close();
				commit(connection);
			}
			catch (SQLException e)
			{
				Assertions.fail(String.format("Deep Deleting Script Maintenance Tag[scriptID: %s, pubTag: %s] failed: %s", longName, pubTag, e.toString()));
			}
		}
	}

	private static Set<Integer> getScriptNums(Connection connection, String longName, String createUser)
	{
		logger.information(String.format("Selecting script_num for [longName: %s, createdBy: %s]", longName, createUser));
		Set<Integer> scriptNums = new HashSet<>();
		try
		{
			PreparedStatement selectScriptNumPreparedStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.SELECT_SCRIPT_NUM_STATEMENT_STRING);
			selectScriptNumPreparedStatement.setString(1, longName);
			selectScriptNumPreparedStatement.setString(2, createUser);
			ResultSet result = selectScriptNumPreparedStatement.executeQuery();
			while(result.next())
			{
				scriptNums.add(result.getInt("SCRIPT_NUM"));
			}
			result.close();
			selectScriptNumPreparedStatement.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Error selecting script_num for [longName: %s, createdBy: %s]", longName, createUser));
		}
		return scriptNums;
	}

	public static int getScript999Count(Connection connection, String longName, String createUser)
	{
		logger.information(String.format("Selecting Script 999 versions for [longName: %s, createdBy: %s]", longName, createUser));
		int results = 0;
		try
		{
			PreparedStatement selectScriptNumPreparedStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.SELECT_999_COUNT);
			selectScriptNumPreparedStatement.setString(1, longName);
			selectScriptNumPreparedStatement.setString(2, createUser);
			ResultSet result = selectScriptNumPreparedStatement.executeQuery();
			while(result.next())
			{
				results = result.getInt(1);
			}
			result.close();
			selectScriptNumPreparedStatement.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Error selecting script 999 versions for [longName: %s, createdBy: %s]", longName, createUser) + e.toString());
		}
		return results;
	}

	public static List<String> getRowsFromScriptContentSetTable(Connection connection, int scriptId)
	{
		logger.information(String.format("Getting Rows From Script Valid Content Set Table [scriptId: %s]", scriptId));
		List<String> scriptContentSetRows = new ArrayList<>();
		try
		{
			PreparedStatement getNodeValueQuery = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.SELECT_CONTENT_SETS_GIVEN_SCRIPT_ID);
			getNodeValueQuery.setInt(1, scriptId);
			ResultSet result = getNodeValueQuery.executeQuery();
			while(result.next())
			{
				scriptContentSetRows.add(Integer.toString(result.getInt("JS_CONTENT_SET_ID")));
			}
			result.close();
			getNodeValueQuery.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Error Getting Rows From Script Valid Content Set Table [scriptId: %s]", scriptId) + e.toString());
		}
		return scriptContentSetRows;
	}
	public static void insertScript(Connection connection, String longName, String createUser, Integer scriptNum, String pubtag, String longNameVersionDescription, int scriptId, String contentID)
	{
		logger.information(String.format("Inserting Script Maintenance Tag[longName: %s, createdBy: %s]", longName, createUser));
		// Don't heck up the order try blocks or you will crash the database
		String changeId = Integer.toString(scriptId);
		changeId = changeId.replaceFirst(".{3}$", "999");
		int scriptId999 = Integer.parseInt(changeId);
		try
		{
			String uuid = DataMocking.generateUUID();
			String uuidFor999 = DataMocking.generateUUID();
			PreparedStatement createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_NUM_INTO_SCRIPT_PUBTAG);
			createStatement.setInt(1, scriptNum);
			createStatement.setString(2, pubtag);
			createStatement.execute();
			createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_FOR_001);
			createStatement.setInt(1, scriptNum);
			createStatement.setString(2, longName);
			createStatement.setString(3, createUser);
			createStatement.setInt(4, scriptId);
			createStatement.setString(5, uuid);
			createStatement.setString(6, longNameVersionDescription);
			createStatement.execute();
			createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_FOR_999);
			createStatement.setInt(1, scriptNum);
			createStatement.setString(2, longName);
			createStatement.setString(3, createUser);
			createStatement.setInt(4, scriptId999);
			createStatement.setString(5, uuidFor999);
			createStatement.execute();
			createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPTID_WITH_CONTENT_SET_INTO_JS_VALID_CONTENT_SETS);
			createStatement.setInt(1, scriptId);
			createStatement.setString(2, contentID);
			createStatement.execute();
			createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPTID_WITH_CONTENT_SET_INTO_JS_VALID_CONTENT_SETS);
			createStatement.setInt(1, scriptId999);
			createStatement.setString(2, contentID);
			createStatement.execute();
			createStatement.close();
			commit(connection);
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("Inserting Script Maintenance Tag[scriptID: %s, createdBy: %s] failed: %s", longName, createUser, e.toString()));
		}
	}

	public static void insertScriptIntoOtherContentSets(Connection connection, int script_Id, String... contentSets)
	{
		for (String contentSet : contentSets)
		{
			try
			{
				PreparedStatement createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPTID_WITH_CONTENT_SET_INTO_JS_VALID_CONTENT_SETS);
				createStatement.setInt(1, script_Id);
				createStatement.setString(2, contentSet);
				createStatement.execute();
				createStatement.close();
				commit(connection);
			}
			catch (SQLException e)
			{
				Assertions.fail(String.format("Failed inserting Script into other Content Set %s, failed: %s", contentSet, e.toString()));
			}
		}
	}

	public static void insertSelectRangeScriptRuleIntoGivenScript(Connection connection, int script_Id, String userName, String startMnemonic, String endMnemonic, int commandSequence, int scriptActionID)
	{
		try
  	 	{
			String commandUUID = DataMocking.generateUUID();
			String commandEndUUID = DataMocking.generateUUID();
			PreparedStatement createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_COMMAND_SELECT_RANGE);
			createStatement.setInt(1, script_Id);
			createStatement.setInt(2, commandSequence);
			createStatement.setInt(3, scriptActionID);
			createStatement.setString(4, startMnemonic);
			createStatement.setInt(5, 1);
			createStatement.setString(6, userName);
			createStatement.setString(7, commandUUID);
			createStatement.execute();
			createStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_COMMAND_END_SELECT_RANGE);
			createStatement.setString(1, endMnemonic);
			createStatement.setString(2, commandEndUUID);
			createStatement.setString(3, commandUUID);
			createStatement.execute();
			createStatement.close();
			commit(connection);
		}
		catch (SQLException e)
		{
			Assertions.fail(e.toString());
		}
	}

	public static List<String> getListOfScriptsFromAContentSet(Connection connection, int contentSet)
	{
		List<String> listOfScriptsFromTheDatabase = new ArrayList<>();
		try
		{
			PreparedStatement getNodeValueQuery = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.SELECT_SCRIPTS_FROM_UI_LIST);
			getNodeValueQuery.setInt(1, contentSet);
			ResultSet result = getNodeValueQuery.executeQuery();
			while(result.next())
			{
				String scriptIDUnformatted = Integer.toString(result.getInt("SCRIPT_ID"));
				String scriptIDFormatted = scriptIDUnformatted.charAt(0) + "-" + scriptIDUnformatted.charAt(1) + scriptIDUnformatted.charAt(2) + scriptIDUnformatted.charAt(3) + scriptIDUnformatted.charAt(4) + "-" + scriptIDUnformatted.charAt(5) + scriptIDUnformatted.charAt(6) + scriptIDUnformatted.charAt(7);
				String longVersionDescription = "";
				if(result.getString("LONG_VER_DESC") != null)
				{
					longVersionDescription = " - " + result.getString("LONG_VER_DESC");
				}
				String dbScript = result.getString("PUBTAG") + " " + result.getString("LONG_NAME") + longVersionDescription + " " + scriptIDFormatted + " " + result.getString("STATUS");
				listOfScriptsFromTheDatabase.add(dbScript);
			}
				result.close();
				getNodeValueQuery.close();
		}
		catch (SQLException e)
		{
			Assertions.fail(String.format("There was an error with selecting a node using the data: contentSet = %s, The error sql provides is %s", contentSet, e.toString()));
		}
		return listOfScriptsFromTheDatabase;
	}

	public static void insertEntryIntoTocNodeScriptAssignment(Connection connection, String nodeUUID, int scriptID, int contentSet)
	{
		try
		{
			PreparedStatement insertEntryIntoTocNodeScriptAssignmentStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.INSERT_SCRIPT_INTO_TOC_NODE_ASSIGNMENT);
			insertEntryIntoTocNodeScriptAssignmentStatement.setString(1, nodeUUID);
			insertEntryIntoTocNodeScriptAssignmentStatement.setInt(2, scriptID);
			insertEntryIntoTocNodeScriptAssignmentStatement.setString(3, null);
			insertEntryIntoTocNodeScriptAssignmentStatement.setInt(4, contentSet);
			insertEntryIntoTocNodeScriptAssignmentStatement.execute();
			insertEntryIntoTocNodeScriptAssignmentStatement.close();
			connection.commit();
		}
		catch (SQLException e)
		{
			Assertions.fail(e.toString());
		}
	}

	public static boolean doesScriptExist(Connection connection, int scriptID)
	{
		boolean doesScriptExists = false;
		try
		{
			PreparedStatement doesScriptExistStatement = connection.prepareStatement(ScriptMaintenanceDatabaseConstants.DOES_SCRIPT_EXIST);
			doesScriptExistStatement.setInt(1, scriptID);
			ResultSet results = doesScriptExistStatement.executeQuery();
			doesScriptExists = results.next();
		}
		catch (SQLException e)
		{
			Assertions.fail("There was a problem running the query to see if a script exists:" + e.toString());
		}
		return doesScriptExists;
	}
}
