package com.thomsonreuters.codes.codesbench.quality.utilities.database.source;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.DataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.json.JsonUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceDatabaseUtils extends BaseDatabaseUtils
{
    private static List<String> deleteLockStatementStringsList = new ArrayList<>();
    private static List<String> deleteDeltaGroupStatementStringsList = new ArrayList<>();
    private static List<String> deleteSectionGroupStatementStringsList = new ArrayList<>();
    private static List<String> deleteViewStatementStringsList = new ArrayList<>();

    //Don't goof the order or you will crash the database :)
    static
    {
        deleteLockStatementStringsList.add(SourceDatabaseConstants.DELETE_LOCK_ASSOC_PREPARED_STATEMENT);
        deleteLockStatementStringsList.add(SourceDatabaseConstants.DELETE_LOCK_PREPARED_STATEMENT);
    }

    static
    {
        deleteDeltaGroupStatementStringsList.add(SourceDatabaseConstants.DELETE_DELTA_GROUP_ASSOC_PREPARED_STATEMENT);
        deleteDeltaGroupStatementStringsList.add(SourceDatabaseConstants.DELETE_DELTA_GROUP_PREPARED_STATEMENT);
    }

    static
    {
        deleteSectionGroupStatementStringsList.add(SourceDatabaseConstants.DELETE_SECTION_GROUP_ASSOC_PREPARED_STATEMENT);
        deleteSectionGroupStatementStringsList.add(SourceDatabaseConstants.DELETE_SECTION_GROUP_PREPARED_STATEMENT);
    }

    static
    {
        deleteViewStatementStringsList.add(SourceDatabaseConstants.DELETE_VIEW_ASSOC_PREPARED_STATEMENT);
        deleteViewStatementStringsList.add(SourceDatabaseConstants.DELETE_VIEW_PREPARED_STATEMENT);
    }

    public static String getDocNumberFromRenditionUuid(Connection connection, String renditionUuid)
    {
        String docNumber = "";
        try
        {
            PreparedStatement getDocNumberStatement = connection.prepareStatement(SourceDatabaseConstants.GET_DOC_NUMBER_PREPARED_STATEMENT);
            getDocNumberStatement.setString(1, renditionUuid);

            ResultSet docNumberResult = getDocNumberStatement.executeQuery();
            docNumberResult.next();
            docNumber = docNumberResult.getString("DOC_NUMBER");
            docNumberResult.close();
            getDocNumberStatement.close();
        }
        catch (SQLException e)
        {

        }
        return docNumber;
    }

    public static String getLineageUuidFromRenditionUuid(Connection connection, String renditionUuid)
    {
        String lineageUuid = "";
        PreparedStatement getLineageUuidStatement = null;
        try
        {
            getLineageUuidStatement = connection.prepareStatement(SourceDatabaseConstants.GET_LINEAGE_UUID_PREPARED_STATEMENT);
            getLineageUuidStatement.setString(1, renditionUuid);

            ResultSet lineageUuidResult = getLineageUuidStatement.executeQuery();
            while (lineageUuidResult.next())
            {
                lineageUuid = lineageUuidResult.getString("CWB_SRC_LINEAGE_UUID");
            }
            lineageUuidResult.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Getting the lineageUuid from the renditionUuid failed: %s", e.toString()));
        }
        finally
        {
            try
            {
                if(getLineageUuidStatement != null)
                {
                    getLineageUuidStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed closing prepared statement");
            }
        }
        return lineageUuid;
    }

    public static String getDocumentUuidFromRenditionUuid(Connection connection, String renditionUuid)
    {
        String documentUuid = "";
        PreparedStatement getDocumentUuidStatement = null;
        try
        {
            getDocumentUuidStatement = connection.prepareStatement(SourceDatabaseConstants.GET_DOCUMENT_UUID_PREPARED_STATEMENT);
            getDocumentUuidStatement.setString(1, renditionUuid);

            ResultSet documentUuidResult = getDocumentUuidStatement.executeQuery();
            while (documentUuidResult.next())
            {
                documentUuid = documentUuidResult.getString("DOCUMENT_UUID");
            }
            documentUuidResult.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Getting the documentUuid from the renditionUuid failed: %s", e.toString()));
        }
        finally
        {
            try
            {
                if(getDocumentUuidStatement != null)
                {
                    getDocumentUuidStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed closing prepared statement");
            }
        }
        return documentUuid;
    }

    public static String getCorrelationUuidFromLineageUuid(Connection connection, String lineageUuid)
    {
        String correlationUuid = "";
        PreparedStatement getCorrelationUuidStatement = null;
        try
        {
            getCorrelationUuidStatement = connection.prepareStatement(SourceDatabaseConstants.GET_CORRELATION_UUID_PREPARED_STATEMENT);
            getCorrelationUuidStatement.setString(1, lineageUuid);

            ResultSet correlationUuidResult = getCorrelationUuidStatement.executeQuery();
            while (correlationUuidResult.next())
            {
                correlationUuid = correlationUuidResult.getString("CORRELATION_ID");
            }
            correlationUuidResult.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Getting the correlationUuid from the lineageUuid failed: %s", e.toString()));
        }
        finally
        {
            try
            {
                if(getCorrelationUuidStatement != null)
                {
                    getCorrelationUuidStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed closing prepared statement");
            }
        }
        return correlationUuid;
    }

    public static String getTargetLocationUuidFromDeltaUuid(Connection connection, String deltaUuid)
    {
        String targetLocationUuid = "";
        try
        {
            PreparedStatement getTargetLocationStatement = connection.prepareStatement(SourceDatabaseConstants.GET_TARGET_LOCATION_UUID_PREPARED_STATEMENT);
            getTargetLocationStatement.setString(1, deltaUuid);

            ResultSet targetLocationResult = getTargetLocationStatement.executeQuery();
            while (targetLocationResult.next())
            {
                targetLocationUuid = targetLocationResult.getString("CWB_TARGET_LOCATION_UUID");
            }
            targetLocationResult.close();
            getTargetLocationStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Getting the targetLocationUuid from the deltaUuid failed: %s", e.toString()));
        }
        return targetLocationUuid;
    }

    public static int getQueryKeyForRenditionUUID(Connection connection, String key, String renditionUuid)
    {
        int count = 0;
        try
        {
            JsonUtils jsonUtils = new JsonUtils();
            String stmt = jsonUtils.getDataFromJsonConstants(key).replace("?", renditionUuid);
            PreparedStatement getCount = connection.prepareStatement(stmt);

            ResultSet getCountResult = getCount.executeQuery();
            getCountResult.next();
            switch (key)
            {
                case "getSectionsCountForRenditionUUID" :
                    count = getCountResult.getInt("SECTIONS_COUNT");
                    break;
                case "getDeltasCountForRenditionUUID" :
                    count = getCountResult.getInt("DELTAS_COUNT");
                    break;
                default:
            }
            getCountResult.close();
            getCount.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Getting the Count from the Uuid failed: %s", e.toString()));
        }

        return count;
    }

    public static void assignUser(Connection connection, String userId, String renditionUUID)
    {
        try
        {
            PreparedStatement assignUserStatement = connection.prepareStatement(SourceDatabaseConstants.ASSIGN_USER_PREPARED_STATEMENT);
            assignUserStatement.setString(1, userId);
            assignUserStatement.setString(2, renditionUUID);


            boolean rowsWereUpdated =  assignUserStatement.executeUpdate() >= 1;
            assignUserStatement.close();
            Assertions.assertTrue(rowsWereUpdated, "The rows were not updated and the query is incorrect");

            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("assigning user: %s to rendition %s failed: %s",userId,renditionUUID, e.toString()));
        }
    }

    public static void deleteLocks(Connection connection, String renditionUuid)
    {
        List<String> lockUuids = getLockUuids(connection, renditionUuid);
        for (String statement : deleteLockStatementStringsList)
        {
            for (String lockUuid : lockUuids)
            {
                try
                {
                    PreparedStatement deleteLockPreparedStatement = connection.prepareStatement(statement);
                    deleteLockPreparedStatement.setString(1, lockUuid);

                    deleteLockPreparedStatement.executeUpdate();
                    deleteLockPreparedStatement.close();
                    commit(connection);
                    DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_AND_A_HALF_SECONDS);
                }
                catch (SQLException e)
                {
                    Assertions.fail(String.format("deleteLocks() failed for: %s: %s", lockUuid, e.toString()));
                }
            }
        }
    }

    public static List<String> getLockUuids(Connection connection, String renditionUuid)
    {
        List<String> lockUuidsList = new ArrayList<>();
        try
        {
            PreparedStatement selectLockUuidsPreparedStatement = connection.prepareStatement(SourceDatabaseConstants.SELECT_LOCK_UUIDS_PREPARED_STATEMENT);
            selectLockUuidsPreparedStatement.setString(1, renditionUuid);

            ResultSet resultSet = selectLockUuidsPreparedStatement.executeQuery();

            while(resultSet.next())
            {
                lockUuidsList.add(resultSet.getString("CWB_SRC_LOCK_UUID"));
            }
            resultSet.close();
            selectLockUuidsPreparedStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("getLockUuids() failed for %s: %s", renditionUuid, e.toString()));
        }
        return lockUuidsList;
    }

    public static void createLock(Connection connection, String renditionUuid, String userId)
    {
        String lockUUID = DataMocking.generateUUID();
        String lockAssocUUID = DataMocking.generateUUID();

        try
        {
            PreparedStatement createLockPreparedStatement = connection.prepareStatement(SourceDatabaseConstants.CREATE_LOCK_PREPARED_STATEMENT);
            createLockPreparedStatement.setString(1, lockUUID);
            createLockPreparedStatement.setString(2, userId);
            createLockPreparedStatement.setString(3, "Edit Rendition");
            createLockPreparedStatement.setInt(4, 2);
            createLockPreparedStatement.setString(5, "SourceRenditionLockImpl");

            createLockPreparedStatement.executeUpdate();
            createLockPreparedStatement.close();
            commit(connection);

            PreparedStatement createLockAssocPreparedStatement = connection.prepareStatement(SourceDatabaseConstants.CREATE_LOCK_ASSOC_PREPARED_STATEMENT);
            createLockAssocPreparedStatement.setString(1, lockAssocUUID);
            createLockAssocPreparedStatement.setString(2, lockUUID);
            createLockAssocPreparedStatement.setString(3, "AssignedRenditionImpl");
            createLockAssocPreparedStatement.setString(4, renditionUuid);

            createLockAssocPreparedStatement.executeUpdate();
            createLockAssocPreparedStatement.close();
            commit(connection);

        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("creating a lock on rendition with uuid %s failed. %s", renditionUuid, e.toString()));
        }
    }

    public static void setRenditionDeleted(Connection connection, String rendUuid, String user)
    {
        logger.information(String.format("Deleting rendition with uuid: %s", rendUuid));

        try
        {
            PreparedStatement deleteRenditionStatement = connection.prepareStatement(SourceDatabaseConstants.DELETE_RENDITION_PREPARED_STATEMENT);
            deleteRenditionStatement.setString(1, user);
            deleteRenditionStatement.setString(2, rendUuid);

            deleteRenditionStatement.executeUpdate();
            deleteRenditionStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting the rendition with uuid: %s failed: %s", rendUuid, e.toString()));
        }
    }

    public static void setRenditionUndeleted(Connection connection, String rendUuid, String user)
    {
        logger.information(String.format("Undeleting rendition with uuid: %s", rendUuid));

        try
        {
            PreparedStatement undeleteRenditionStatement = connection.prepareStatement(SourceDatabaseConstants.UNDELETE_RENDITION_PREPARED_STATEMENT);
            undeleteRenditionStatement.setString(1, user);
            undeleteRenditionStatement.setString(2, rendUuid);

            undeleteRenditionStatement.executeUpdate();
            undeleteRenditionStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Undeleting the rendition with uuid: %s was unsuccessful: %s", rendUuid, e.toString()));
        }
    }

    public static void deleteDeltaGroup(Connection connection, String rendUuid, String groupName)
    {
        logger.information(String.format("Deleting delta group %s with rendition uuid %s", groupName, rendUuid));

        for(String statement : deleteDeltaGroupStatementStringsList)
        {
            try
            {
                PreparedStatement deleteDeltaGroupStatement = connection.prepareStatement(statement);
                deleteDeltaGroupStatement.setString(1, rendUuid);
                deleteDeltaGroupStatement.setString(2, groupName);

                deleteDeltaGroupStatement.executeUpdate();
                deleteDeltaGroupStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting the delta group %s failed: %s", groupName, e.toString()));
            }
        }
        commit(connection);
    }

    public static void deleteSectionGroup(Connection connection, String rendUuid, String groupName)
    {
        logger.information(String.format("Deleting section group %s with rendition uuid %s", groupName, rendUuid));

        for (String statement : deleteSectionGroupStatementStringsList)
        {
            try
            {
                PreparedStatement deleteSectionGroupStatement = connection.prepareStatement(statement);
                deleteSectionGroupStatement.setString(1, rendUuid);
                deleteSectionGroupStatement.setString(2, groupName);

                deleteSectionGroupStatement.executeUpdate();
                deleteSectionGroupStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting the section group %s failed: %s", groupName, e.toString()));
            }
        }
        commit(connection);
    }

    public static void deleteView(Connection connection, String viewName)
    {
        logger.information(String.format("Deleting view %s through the database", viewName));

        for (String statement : deleteViewStatementStringsList)
        {
            try
            {
                PreparedStatement deleteViewStatement = connection.prepareStatement(statement);
                deleteViewStatement.setString(1, viewName);

                deleteViewStatement.executeUpdate();
                deleteViewStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting the view %s failed: %s", viewName, e));
            }
        }
        commit(connection);
    }

    public static void restoreBaselineToAPreviousBaseline(Connection connection, String renditionUuid, String username, int baselineDifference)
    {
        logger.information(String.format("Restoring baseline to a previous baseline on rendition %s", renditionUuid));

        try
        {
            String baselineNumber = getCurrentBaselineNumber(connection, renditionUuid);
            //Add one to get the number the new baseline will be
            int baselineInt = Integer.parseInt(baselineNumber) + 1;
            PreparedStatement restoreBaselineToPreviousStatement = connection.prepareStatement(SourceDatabaseConstants.RESTORE_BASELINE_TO_PREVIOUS_BASELINE);
            restoreBaselineToPreviousStatement.setString(1, DataMocking.generateUUID());
            restoreBaselineToPreviousStatement.setString(2, renditionUuid);
            restoreBaselineToPreviousStatement.setString(3, username);
            restoreBaselineToPreviousStatement.setString(4, String.format("Restored from %s", (baselineInt - baselineDifference)));
            restoreBaselineToPreviousStatement.setString(5, baselineInt + "");
            restoreBaselineToPreviousStatement.executeUpdate();
            restoreBaselineToPreviousStatement.close();
            commit(connection);

            PreparedStatement updatePreviousBaselineTextStatement = connection.prepareStatement(SourceDatabaseConstants.UPDATE_PREVIOUS_BASELINE_TEXT);
            updatePreviousBaselineTextStatement.setString(1, baselineInt - baselineDifference + "");
            updatePreviousBaselineTextStatement.setString(2, renditionUuid);
            updatePreviousBaselineTextStatement.setString(3, baselineInt - 1 + "");
            updatePreviousBaselineTextStatement.setString(4, renditionUuid);
            updatePreviousBaselineTextStatement.executeUpdate();
            updatePreviousBaselineTextStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Restoring a baseline to a previous baseline has failed: %s", e.toString()));
        }
    }

    public static void restoreBaselineToOriginal(Connection connection, String renditionUuid, String username)
    {
        logger.information(String.format("Restoring baseline to original on rendition %s", renditionUuid));

        try
        {
            String baselineNumber = getCurrentBaselineNumber(connection, renditionUuid);
            int baselineInt = Integer.parseInt(baselineNumber) + 1;
            PreparedStatement restoreBaselineToOriginalStatement = connection.prepareStatement(SourceDatabaseConstants.RESTORE_BASELINE_TO_ORIGINAL_BASELINE);

            restoreBaselineToOriginalStatement.setString(1, DataMocking.generateUUID());
            restoreBaselineToOriginalStatement.setString(2, renditionUuid);
            restoreBaselineToOriginalStatement.setString(3, username);
            restoreBaselineToOriginalStatement.setString(4, baselineInt + "");
            restoreBaselineToOriginalStatement.executeUpdate();
            restoreBaselineToOriginalStatement.close();

            PreparedStatement updatePreviousBaselineTextStatement = connection.prepareStatement(SourceDatabaseConstants.UPDATE_PREVIOUS_BASELINE_TEXT);
            updatePreviousBaselineTextStatement.setString(1, "0");
            updatePreviousBaselineTextStatement.setString(2, renditionUuid);
            updatePreviousBaselineTextStatement.setString(3, baselineInt - 1 + "");
            updatePreviousBaselineTextStatement.setString(4, renditionUuid);
            updatePreviousBaselineTextStatement.executeUpdate();
            updatePreviousBaselineTextStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Restoring a baseline to original failed: %s", e.toString()));
        }
        commit(connection);
    }

    public static String getMaxSiblingOrder(Connection connection, String tableName, String uuid, String uuidName)
    {
        String siblingOrder = "";

        try
        {
            String query = SourceDatabaseConstants.SELECT_MAX_SIBLING_ORDER_PREPARED_STATEMENT.replace("$tableName", tableName).replace("$uuid", uuidName);
            PreparedStatement getMaxSiblingOrderStatement = connection.prepareStatement(query);
            getMaxSiblingOrderStatement.setString(1, uuid);

            ResultSet siblingOrderResult = getMaxSiblingOrderStatement.executeQuery();
            siblingOrderResult.next();
            siblingOrder = siblingOrderResult.getString("MAX(SIBLING_ORDER)");
            siblingOrderResult.close();
            getMaxSiblingOrderStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Failed to get the max sibling order from %s: %s", tableName, e.toString()));
        }

        return siblingOrder;
    }

    public static String getMaxDeltaCount(Connection connection, String uuid, String tableName, String uuidName)
    {
        String deltaCount = "";
        try
        {
            String query = SourceDatabaseConstants.SELECT_MAX_DELTA_COUNT_PREPARED_STATEMENT.replace("$tableName", tableName).replace("$uuid", uuidName);
            PreparedStatement getDeltaCountStatement = connection.prepareStatement(query);
            getDeltaCountStatement.setString(1, uuid);

            ResultSet deltaCountResult = getDeltaCountStatement.executeQuery();
            deltaCountResult.next();
            deltaCount = deltaCountResult.getString("MAX(DELTA_COUNT)");
            deltaCountResult.close();
            getDeltaCountStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Failed to get the current delta count from section %s: %s", uuid, e.toString()));
        }
        return deltaCount;
    }

    public static String getYear(Connection connection, String renditionUuid)
    {
        String year = "";
        PreparedStatement getYearStatement = null;
        try
        {
            getYearStatement = connection.prepareStatement(SourceDatabaseConstants.GET_YEAR);
            getYearStatement.setString(1, renditionUuid);

            ResultSet yearResult = getYearStatement.executeQuery();
            yearResult.next();
            year = yearResult.getString("year");
            yearResult.close();
        }
        catch(SQLException e)
        {
            Assertions.fail(String.format("Failed to get the Year from cwb_src_rendition rendition uuid: %s", renditionUuid));
        }
        finally
        {
            try
            {
                if (getYearStatement != null)
                {
                    getYearStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed to close prepare statement");
            }
        }
        return year;
    }

    public static String getSession(Connection connection, String renditionUuid)
    {
        String session = "";
        PreparedStatement getSessionStatement = null;
        try
        {
            getSessionStatement = connection.prepareStatement(SourceDatabaseConstants.GET_SESSION);
            getSessionStatement.setString(1, renditionUuid);

            ResultSet sessionResult = getSessionStatement.executeQuery();
            sessionResult.next();
            session = sessionResult.getString("js_session_short_name");
            sessionResult.close();
        }
        catch(SQLException e)
        {
            Assertions.fail(String.format("Failed to get the session from js_session_enum rendition uuid: %s", renditionUuid));
        }
        finally
        {
            try
            {
                if(getSessionStatement != null)
                {
                    getSessionStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed to close prepare statement");
            }
        }
        return session;
    }

    private static String getCurrentBaselineNumber(Connection connection, String renditionUuid)
    {
        String baselineNumber = "";
        try
        {
            PreparedStatement getBaselineNumberStatement = connection.prepareStatement(SourceDatabaseConstants.SELECT_CURRENT_BASELINE_NUMBER_PREPARED_STATEMENT);
            getBaselineNumberStatement.setString(1, renditionUuid);

            ResultSet baselines = getBaselineNumberStatement.executeQuery();
            baselines.next();
            baselineNumber = baselines.getString("MAX(SIBLING_ORDER)");
            baselines.close();
            getBaselineNumberStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Failed to get the current baseline number from rendition %s: %s", renditionUuid, e.toString()));
        }
        return baselineNumber;
    }

    public static String getBillId(Connection connection, String renditionUUID)
    {
        String billId = "";
        try
        {
            PreparedStatement getBillIdStatement = connection.prepareStatement(SourceDatabaseConstants.GET_BILL_ID);
            getBillIdStatement.setString(1, renditionUUID);

            ResultSet result = getBillIdStatement.executeQuery();
            if(result.next())
            {
                billId = result.getString("BILL_ID");
            }

            result.close();
            getBillIdStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Failed to get the bill id from rendition %s: %s", renditionUUID, e.toString()));
        }
        return billId;
    }

    public static String getRenditionChapterType(Connection connection, String renditionUuid)
    {
        return null;
    }

    public static String getRenditionStatus (Connection connection, String rendtionUuid)
    {
        return null;
    }

    public static String getRenditionLegislationType(Connection connection, String rendtionUuid)
    {
        return null;
    }

    public static String getRenditionContentType(Connection connection, String rendtionUuid)
    {
        return null;
    }

}
