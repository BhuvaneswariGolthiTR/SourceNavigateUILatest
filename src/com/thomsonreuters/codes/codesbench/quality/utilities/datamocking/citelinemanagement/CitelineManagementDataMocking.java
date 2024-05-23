package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public class CitelineManagementDataMocking
{
    /**
     * This method creates a Cite Line Reference with the 3 basic columns originalFirstLine, First Line and Second Line
     */
    public static void insertCiteline(Connection connection, int jsJurisdictionId, String volsCt1Num, String originalFirstLineCite, String firstLineCite, String secondLineCitePre, String contentSetName)
    {
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "R", originalFirstLineCite, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "0", firstLineCite, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "1", secondLineCitePre, contentSetName);
    }

    /**
     * This method creates a Cite Line Reference with the 10 current columns
     */
    public static void insertCiteLine(Connection connection, int jsJurisdictionId, String volsCt1Num, String originalFirstLineCite, String firstLineCite, String secondLineCitePre,
                                      String secondLineCiteApp, String expandedCitePre, String expandedCiteApp, String formerCite, String modifiedBy, String modifiedDate, String comments, String contentSetName)
    {
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "R", originalFirstLineCite, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "0", firstLineCite, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "1", secondLineCitePre, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "2", secondLineCiteApp, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "3", expandedCitePre, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "4", expandedCiteApp,contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "5", formerCite, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "6", modifiedDate, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "7", modifiedBy, contentSetName);
        insertIntoPubStableTableHelper(connection, jsJurisdictionId, volsCt1Num, "8", comments, contentSetName);
    }

    private static void insertIntoPubStableTableHelper(Connection connection, int jsJursidictionId, String volsCt1Num, String recordType, String citationText, String contentSetName)
    {
        String uuid = CommonDataMocking.generateUUID();

        PreparedStatement insertTocNodeStatement = null;
        try
        {
            logger.information("Inserting into PUB_STABLE_TABLE");
            insertTocNodeStatement = connection.prepareStatement(CitelineManagementDataMockingConstants.INSERT_INTO_PUB_STABLE_TABLE);
            insertTocNodeStatement.setInt(1, jsJursidictionId);
            insertTocNodeStatement.setString(2, volsCt1Num);
            insertTocNodeStatement.setString(3, recordType);
            insertTocNodeStatement.setString(4, citationText);
            insertTocNodeStatement.setString(5, "N");
            insertTocNodeStatement.setString(6, uuid);
            insertTocNodeStatement.setInt(7, 0);
            insertTocNodeStatement.setString(8, contentSetName);

            insertTocNodeStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("creation in PUB_STABLE_TABLE failed: %s", e));
        }
        finally
        {
            if(insertTocNodeStatement != null)
            {
                try
                {
                    insertTocNodeStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deleteFromPubStableTable(Connection connection, String ct1)
    {
        PreparedStatement deleteTocNodeStatement = null;

        try
        {
            logger.information("Deleting from PUB_STABLE_TABLE");
            deleteTocNodeStatement = connection.prepareStatement(CitelineManagementDataMockingConstants.DELETE_FROM_PUB_STABLE_TABLE);
            deleteTocNodeStatement.setString(1, ct1);
            deleteTocNodeStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from PUB_STABLE_TABLE failed: %s", e));
        }
        finally
        {
            if(deleteTocNodeStatement != null)
            {
                try
                {
                    deleteTocNodeStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method creates a normalized cite with the current columns in the Normalized Cite ui
     */
    public static void insertNormalizedCite(Connection connection, int position, String citationPrefix, String condensedPrefix, int jsJursidictionId, String contentSetName)
    {
        String nodeUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information(String.format("Inserting nodeUuid: %s, citation prefix: %s, content set name: %s into PUB_LEGACY_NORM_TABLE", nodeUuid, citationPrefix, contentSetName));
            PreparedStatement insertTocNodeStatement = connection.prepareStatement(CitelineManagementDataMockingConstants.INSERT_INTO_PUB_LEGACY_NORM_TBL);
            insertTocNodeStatement.setString(1, nodeUuid);
            insertTocNodeStatement.setInt(2, position);
            insertTocNodeStatement.setString(3, citationPrefix);
            insertTocNodeStatement.setString(4, condensedPrefix);
            insertTocNodeStatement.setInt(5, jsJursidictionId);
            insertTocNodeStatement.setString(6, contentSetName);

            insertTocNodeStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting nodeUuid: %s, citation prefix: %s, content set name: %s into PUB_LEGACY_NORM_TABLE failed: %s", nodeUuid, citationPrefix, contentSetName, e.toString()));
        }
    }

    /**
     * This method deletes a cite in the Normalized Cite ui
     */
    public static void deleteNormalizedCite(Connection connection, String citationPrefix, String contentSetName)
    {
        try
        {
            logger.information(String.format("Deleting citation prefix: %s, content set name: %s from PUB_LEGACY_NORM_TABLE", citationPrefix, contentSetName));
            PreparedStatement deleteTocNodeStatement = connection.prepareStatement(CitelineManagementDataMockingConstants.DELETE_FROM_PUB_LEGACY_NORM_TBL);
            deleteTocNodeStatement.setString(1, citationPrefix);
            deleteTocNodeStatement.setString(2, contentSetName);
            deleteTocNodeStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting citation prefix: %s, content set name: %s from PUB_LEGACY_NORM_TABLE failed: %s", citationPrefix, contentSetName, e.toString()));
        }
    }
}
