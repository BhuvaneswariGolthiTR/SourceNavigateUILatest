package com.thomsonreuters.codes.codesbench.quality.utilities.database.popname;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopNameDatabaseUtils extends BaseDatabaseUtils
{

	public static String getUuidOfPopNameFromDatabase(String name, String query) 
	{
		BaseDatabaseUtils uatPopnamesDB = new BaseDatabaseUtils();
		Connection uatConnection = uatPopnamesDB.connectToDatabasePopnamesUAT();
		String sql = StringUtils.replace(query, ":popName", "'" + name + "'");
		String uuidStr = null;
		ResultSet resultSet = uatPopnamesDB.executeQuery(uatConnection, sql);
		
		try 
		{
			while(resultSet.next())
			{
				uuidStr = resultSet.getString(1);
			}
			resultSet.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		uatPopnamesDB.disconnect(uatConnection);
		return uuidStr;
	}

	public static String getRelationshipUuidFromPopName(Connection connection, String popNameDesc, String popNameSectionNumber)
	{
		String results = "";
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.GET_RELATIONSHIP_UUID_FROM_GIVEN_POPNAME);
			query.setString(1,popNameDesc);
			query.setString(2,popNameDesc);
			query.setString(3,popNameSectionNumber);
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
				results = resultSet.getString("RELATIONSHIP_UUID");
			}
			resultSet.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	public static int getPopNameRecordsCount(Connection connection, String popNameLetter, String date, String nodeUUID)
	{
		int results = 0;
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.TOTAL_COUNT_OF_POPNAME_WITH_LETTER_AND_DATE_QUERY);
			//query.setString(1,popNameLetter + "%"); //the percent sign is needed for the query since it is a like statement in SQL
			//query.setString(1,date);
			query.setString(1,popNameLetter);
			query.setString(2,nodeUUID);
			ResultSet act_resultset = query.executeQuery();
			while(act_resultset.next())
			{
				results = act_resultset.getInt(1);
			}
			act_resultset.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}
	
	public static int getNumberOfPopNamesReturned(Connection connection, String publicLawSection)
	{
		int results = 0;
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.GET_TOTAL_POPNAME_COUNT);
			query.setString(1,publicLawSection);
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
				results = resultSet.getInt(1);
			}
			resultSet.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}
	
	public static int isNodeDeletedRelationship(Connection connection,String popNameDesc)
	{
		int results = 0;
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.CHECK_IF_NODE_IS_DELETED_RELATIONSHIP_QUERY);
			query.setString(1,popNameDesc);
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
				results = resultSet.getInt(1);
			}
			resultSet.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	public static int getNumberOfDeletedPopNames(Connection connection, String popNameLetter)
	{
		int results = 0;
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.NUMBER_OF_DELETED_POPNAME_NODES_WITH_LETTER_QUERY);
			query.setString(1,popNameLetter);
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
				results = resultSet.getInt(1);
			}
			resultSet.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	public static int isNodeDeleted(Connection connection,String popNameDesc)
	{
		int results = 0;
		try
		{
			PreparedStatement query = connection.prepareStatement(PopNameDatabaseConstants.CHECK_IF_NODE_IS_DELETED_QUERY);
			query.setString(1,popNameDesc);
			ResultSet resultSet = query.executeQuery();
			while(resultSet.next())
			{
				results = resultSet.getInt(1);
			}
			resultSet.close();
			query.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	// TODO: Jay: Ask Sarah to merge these methods from hierarchy then delete these
	public static void updateIsDeleteFlagToUndeleted(String nodeUuid, String contentSet, Connection connection)
	{
		updateDeletedFlagToBeDeleted(nodeUuid, contentSet, connection, 0);
	}

	public static void updateIsDeleteFlagToDeleted(String nodeUuid, String contentSet, Connection connection)
	{
		updateDeletedFlagToBeDeleted(nodeUuid, contentSet, connection, 1);
	}

	/*This updatd the Deleted flag to be 1 or 0 depending on what is needed */
	private static void updateDeletedFlagToBeDeleted(String NodeUuid, String contentSet, Connection connection, int deleteFlagStatus)
	{
		logger.information(String.format("Selecting a node from the database with these parameters: deletedStatus =  %d, nodeUuid = %s, ContentSetID=%s", deleteFlagStatus, NodeUuid, contentSet));
		try
		{
			PreparedStatement updateDeleteFlagStatement = connection.prepareStatement(PopNameDatabaseConstants.UPDATE_DELETE_FLAG_TO_BE_DELETED);
			updateDeleteFlagStatement.setInt(1, deleteFlagStatus);
			updateDeleteFlagStatement.setString(2, NodeUuid);
			updateDeleteFlagStatement.setInt(3, Integer.parseInt(contentSet));

			updateDeleteFlagStatement.executeUpdate();
			updateDeleteFlagStatement.close();
			commit(connection);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Assertions.fail("An issue occurred in the database with updating this node to be deleted ");
		}
	}

}
