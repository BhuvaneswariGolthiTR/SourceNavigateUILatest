package com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.DataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HierarchyDatabaseUtils extends BaseDatabaseUtils
{
    private static final String GET_TEXT_OF_NODE_WITH_GIVEN_CONTENT_UUID = "SELECT TEXT FROM TOC_CONTENT WHERE CONTENT_UUID = ?" +
            " and LATEST_FLAG = 1";

    private static final String DELETE_LATEST_WIP_VERSION = "DELETE FROM TOC_CONTENT WHERE CONTENT_UUID = ? AND LATEST_FLAG = 1";

    private static final String UPDATE_WIP_VERSION_TO_LATEST = "UPDATE TOC_CONTENT SET LATEST_FLAG = 1 WHERE CONTENT_UUID = ? AND WIP_VERSION_NUM = (SELECT MAX(WIP_VERSION_NUM) FROM TOC_CONTENT WHERE CONTENT_UUID = ?)";

    private static final String GET_NODE_STATUS_CODE_DESCRIPTION = "SELECT DESCRIPTION FROM TOC_NODE_STATUS WHERE STATUS_CODE_ID = (SELECT LEGACY_STATUS_CODE FROM TOC_NODE WHERE NODE_UUID = ?)";

    private static List<String> setNodeDeletedStatementStringsList = new ArrayList<>();

    private static List<String> deepDeleteNodeStatementStringsList = new ArrayList<>();

    private static List<String> deepDeleteDispDerivNodeStatementStringsList = new ArrayList<>();

    static
    {
        setNodeDeletedStatementStringsList.add(HierarchyDatabaseConstants.SET_NODE_DELETED_QUERY);
        setNodeDeletedStatementStringsList.add(HierarchyDatabaseConstants.SET_NODE_DELETED_DISP_DERIV_TABLE_QUERY);
    }

    static
    {
        deepDeleteDispDerivNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DISP_DERIV_QUERY);
        deepDeleteDispDerivNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_DISP_DERIV_QUERY);
    }

    //Don't mess up the order or the database will crash
    static
    {
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_SCRIPT_ASSIGNMENT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DOC_FAMILY_LINK_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DOC_FAMILY_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_LAST_UPLOAD_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_UPLOAD_HISTORY_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_PARENT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_CHILD_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_RESEQUENCED_AUDIT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_AUDIT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_ALT_CITE_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_AUDIT_LATEST_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_CHECKED_OUT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_EXTENDED_METADATA_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_GLOSSARY_LINK_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_KEY_VALUE_METADATA_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LAST_UPLOAD_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_SRC_DOC_LINK_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_UPLOAD_HISTORY_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_WIP_METADATA_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_CONTENT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_EXTENDED_METADATA_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_SCRIPT_ASSIGNMENT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_LINK_CHILD_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_LINK_PARENT_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_TOC_NODE_QUERY);
        deepDeleteNodeStatementStringsList.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_NODE_QUERY);
    }

    public static int getHIDWithNodeUUID(String nodeUUID, Connection connection)
    {
        logger.information(String.format("Getting HID of the node with uuid: %s", nodeUUID));
        int hid = -1;
        try
        {
            PreparedStatement getHIDStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_HID_WITH_NODE_UUID);
            getHIDStatement.setString(1, nodeUUID);
            ResultSet result = getHIDStatement.executeQuery();
            if(result.next())
            {
                hid = result.getInt("HID");
            }
            result.close();
            getHIDStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to get HID from database: " + e);
        }
        return hid;
    }

    public static String getContentUuidWithNodeUuid(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting contentUuid of the node with uuid: %s", nodeUuid));
        String contentUuid = "";
        try
        {
            PreparedStatement getContentUuidStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_CONTENT_UUID_QUERY);
            getContentUuidStatement.setString(1, nodeUuid);

            ResultSet contentUuidResult = getContentUuidStatement.executeQuery();
            if(contentUuidResult.next())
            {
                contentUuid = contentUuidResult.getString("CONTENT_UUID");
                logger.information("Found contentUuid: " + contentUuid);
            }
            else
            {
                Assertions.fail("Unable to get content UUID (empty result set)!");
            }
            contentUuidResult.close();
            getContentUuidStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Unable to get contentUuid of node with uuid %s: %s", nodeUuid, e.toString()));
        }
        return contentUuid;
    }

    public static String getContentUuidWithNodeUuidAllowEmptyResultSet(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting contentUuid of the node with uuid: %s", nodeUuid));
        String contentUuid = null;
        try
        {
            PreparedStatement getContentUuidStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_CONTENT_UUID_QUERY);
            getContentUuidStatement.setString(1, nodeUuid);

            ResultSet contentUuidResult = getContentUuidStatement.executeQuery();
            if(contentUuidResult.next())
            {
                contentUuid = contentUuidResult.getString("CONTENT_UUID");
                logger.information("Found contentUuid: " + contentUuid);
            }
            contentUuidResult.close();
            getContentUuidStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Unable to get contentUuid of node with uuid %s: %s", nodeUuid, e.toString()));
        }
        return contentUuid;
    }

    public static String getNodeUuidWithContentUuid(Connection connection, String contentUuid)
    {
        logger.information(String.format("Get the node uuid of the node with content uuid = %s", contentUuid));
        String nodeUuid = "";
        try
        {
            PreparedStatement getNodeUuidWithContentUuuidStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_UUID_WITH_CONTENT_UUID_QUERY);
            getNodeUuidWithContentUuuidStatement.setString(1, contentUuid);

            ResultSet nodeUuidResult = getNodeUuidWithContentUuuidStatement.executeQuery();
            nodeUuidResult.next();
            nodeUuid = nodeUuidResult.getString("NODE_UUID");
            nodeUuidResult.close();
            getNodeUuidWithContentUuuidStatement.close();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database trying to get the node uuid of node with content uuid = %s", contentUuid));
        }
        return nodeUuid;
    }

    public static Date getLegisEffectiveStartDate(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Effective Start Date for nodeUUID: %s", nodeUuid));
        Date startDate = null;
        try
        {
            PreparedStatement getStartDateStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_START_DATE_QUERY);
            getStartDateStatement.setString(1, nodeUuid);

            ResultSet startDateResult = getStartDateStatement.executeQuery();
            startDateResult.next();
            startDate = startDateResult.getDate("LEGIS_START_EFFECTIVE_DATE");
            startDateResult.close();
            getStartDateStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Unable to get start date of node %s: %s", nodeUuid, e.toString()));
        }
        return startDate;
    }

    public static Date getLegisEffectiveEndDate(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Effective End Date for nodeUUID: %s", nodeUuid));
        Date endDate = null;
        try
        {
            PreparedStatement getEndDateStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_END_DATE_QUERY);
            getEndDateStatement.setString(1, nodeUuid);

            ResultSet endDateResult = getEndDateStatement.executeQuery();
            endDateResult.next();
            endDate = endDateResult.getDate("LEGIS_END_EFFECTIVE_DATE");
            endDateResult.close();
            getEndDateStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Unable to get the end date of node %s: %s", nodeUuid, e.toString()));
        }
        return endDate;
    }

    public static boolean nodeHasLegisEndEffectiveDate(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Checking for Effective End Date for nodeUUID: %s", nodeUuid));
        boolean hasEndDate = false;
        try
        {
            PreparedStatement getEndDateStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_END_DATE_QUERY);
            getEndDateStatement.setString(1, nodeUuid);

            ResultSet endDateResult = getEndDateStatement.executeQuery();

            if(endDateResult.next())
            {
                hasEndDate = endDateResult.getDate("LEGIS_END_EFFECTIVE_DATE") != null;
            }
            endDateResult.close();
            getEndDateStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Unable to get the end date of node %s: %s", nodeUuid, e.toString()));
        }
        return hasEndDate;
    }

    public static String getXmlTextOfNodeWithContentUuid(String contentUuid, Connection connect)
    {
        String xmlText = "";
        try
        {
            PreparedStatement selectedNodeXmlText = connect.prepareStatement(GET_TEXT_OF_NODE_WITH_GIVEN_CONTENT_UUID);
            selectedNodeXmlText.setString(1,contentUuid);
            ResultSet result = selectedNodeXmlText.executeQuery();
            while (result.next())
            {
                xmlText = result.getString("TEXT");
            }
            result.close();
            selectedNodeXmlText.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }
        return xmlText;
    }

    public static void deleteLatestWipVersion(String contentUuid, Connection connection)
    {
        logger.information(String.format("Deleting latest wip version of node with content uuid: %s", contentUuid));
        try
        {
            PreparedStatement deleteCurrentWipVersion = connection.prepareStatement(DELETE_LATEST_WIP_VERSION);
            deleteCurrentWipVersion.setString(1, contentUuid);

            boolean rowWasDeleted =  deleteCurrentWipVersion.executeUpdate() >= 1;
            commit(connection);
            deleteCurrentWipVersion.close();
            Assertions.assertTrue(rowWasDeleted, "The rows were not updated and the query is incorrect");
        }
        catch (SQLException e)
        {
            Assertions.fail("The current WIP version could not be deleted correctly");
        }
    }

    public static void updateWipVersionToLatest(String contentUuid, Connection connection)
    {
        logger.information(String.format("Updating wip version to latest of node with content uuid: %s", contentUuid));
        try
        {
            PreparedStatement updateWipVersion = connection.prepareStatement(UPDATE_WIP_VERSION_TO_LATEST);
            updateWipVersion.setString(1, contentUuid);
            updateWipVersion.setString(2, contentUuid);
            boolean rowWasUpdated = updateWipVersion.executeUpdate() >= 1;
            commit(connection);
            updateWipVersion.close();
            Assertions.assertTrue(rowWasUpdated, "The rows were not updated and the query is incorrect");
        } catch (SQLException e)
        {
            Assertions.fail("The current WIP version could not be updated correctly");
        }
    }

    private static void setNodeKeyword(String contentUuid, String contentSet, String query, Connection connection)
    {
        logger.information(String.format("Updating the node keyword in the database with these parameters: contentUUID = %s contentSet = %s", contentUuid, contentSet));
        try
        {
            PreparedStatement setNodeKeywordStatement =  connection.prepareStatement(query);
            setNodeKeywordStatement.setString(1, contentUuid);
            setNodeKeywordStatement.setInt(2, Integer.parseInt(contentSet));

            setNodeKeywordStatement.executeUpdate();
            commit(connection);
            setNodeKeywordStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with updating the node keyword with the parameters: ContentUUID = %s, contentSet = %s", contentUuid, contentSet));
        }
    }

    public static void setNodeKeywordToBlueLine(String contentUuid, String contentSet, Connection connection)
    {
        setNodeKeyword(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_KEYWORD_TO_BLUELINE, connection);
    }

    public static void setNodeKeywordToBLAnalysis(String contentUuid, String contentSet, Connection connection)
    {
        setNodeKeyword(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_KEYWORD_TO_BL_ANALYSIS, connection);
    }

    public static void setNodeKeywordToNDRS(String contentUuid, String contentSet, Connection connection)
    {
        setNodeKeyword(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_KEYWORD_TO_NDRS, connection);
    }

    public static void setNodeKeywordToARL(String contentUuid, String contentSet, Connection connection)
    {
        setNodeKeyword(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_KEYWORD_TO_ARL, connection);
    }

    public static void setNodeKeywordToSubTitle(String contentUuid, String contentSet, Connection connection)
    {
        setNodeKeyword(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_KEYWORD_TO_SUB_TITLE, connection);
    }

    private static void setNodeStatus(String contentUuid, String contentSet, String query, Connection connection)
    {
        logger.information(String.format("Updating the node status in the database with these parameters: contentUUID = %s contentSet = %s", contentUuid, contentSet));
        try
        {
            PreparedStatement setNodeStatusStatement =  connection.prepareStatement(query);
            setNodeStatusStatement.setString(1, contentUuid);
            setNodeStatusStatement.setInt(2, Integer.parseInt(contentSet));

            int recordsChanged = setNodeStatusStatement.executeUpdate();
            commit(connection);
            setNodeStatusStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with updating the node status with the parameters: ContentUUID = %s, contentSet = %s", contentUuid, contentSet));
        }
    }

    public static void setNodeToLiveStatus(String contentUuid, String contentSet, Connection connection)
    {
        setNodeStatus(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_LIVE_STATUS_QUERY, connection);
    }

    public static void setNodeToRepealStatus(String contentUuid, String contentSet, Connection connection)
    {
        setNodeStatus(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_REPEAL_STATUS_QUERY, connection);
    }

    public static void setNodeToReserveStatus(String contentUuid, String contentSet, Connection connection)
    {
        setNodeStatus(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_RESERVE_STATUS_QUERY, connection);
    }

    public static void setNodeToTransferStatus(String contentUuid, String contentSet, Connection connection)
    {
        setNodeStatus(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_TRANSFER_STATUS_QUERY, connection);
    }

    private static void setNodeValidationFlag(String contentUuid, String contentSet, String query, Connection connection)
    {
        logger.information(String.format("Updating the node validation flag in the database with these parameters: contentUUID = %s contentSet = %s", contentUuid, contentSet));
        try
        {
            PreparedStatement setNodeStatusStatement =  connection.prepareStatement(query);
            setNodeStatusStatement.setString(1, contentUuid);
            setNodeStatusStatement.setInt(2, Integer.parseInt(contentSet));

            int recordsChanged = setNodeStatusStatement.executeUpdate();
            commit(connection);
            setNodeStatusStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query. Assure that content uuid you are passing in appears in the content set you are passing in");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with updating the node validation flag with the parameters: ContentUUID = %s, contentSet = %s", contentUuid, contentSet));
        }
    }

    public static void setNodeToDeletedValidationFlag(String contentUUID, String iowaContentNumber, Connection connection)
    {
        logger.information("Setting node to Deleted Validation Flag");
        try
        {
            PreparedStatement setNodeToDeletedValidationFlag = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_TO_DELETED_VALIDATION_FLAG);
            setNodeToDeletedValidationFlag.setString(1,contentUUID);
            setNodeToDeletedValidationFlag.setString(2, iowaContentNumber);
            setNodeToDeletedValidationFlag.execute();
            setNodeToDeletedValidationFlag.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to set the node to deleted Validation Flag");
        }
    }

    public static void setNodeToDataErrorValidationFlag(String contentUUID, String iowaContentNumber, Connection connection)
    {
        logger.information("Setting node to Data Error Validation Flag");
        try
        {
            PreparedStatement setNodeToDataErrorValidationFlag = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_TO_DATA_ERROR_VALIDATION_FLAG_STATEMENT);
            setNodeToDataErrorValidationFlag.setString(1, contentUUID);
            setNodeToDataErrorValidationFlag.execute();
            setNodeToDataErrorValidationFlag.close();
            commit(connection);
        }
        catch(SQLException e)
        {
            Assertions.fail("Failed to set the node to data error validation flag");
        }
    }

    public static void setNodeToDTDErrorValidationFlag(String contentUUID, String iowaContentNumber, Connection connection)
    {
        logger.information("Setting node to DTD Error Validation Flag");
        try
        {
            PreparedStatement setNodeToDTDErrorValidationFlagStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_TO_DTD_ERROR_VALIDATION_FLAG_STATEMENT);
            setNodeToDTDErrorValidationFlagStatement.setString(1, contentUUID);
            setNodeToDTDErrorValidationFlagStatement.execute();
            setNodeToDTDErrorValidationFlagStatement.close();
            commit(connection);
        }
        catch(SQLException e)
        {
            Assertions.fail("Failed to set the node to DTD error validation flag");
        }
    }

    public static String   getNodeValidationFlag(String nodeUuid, Connection connection)
    {
        String flagStatus = "";
        logger.information(String.format("Getting the node validation flag for node with nodeUuid: %s", nodeUuid));
        try
        {
            PreparedStatement getNodeValidationFlagStatus =  connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VALIDATION_FLAG);
            getNodeValidationFlagStatus.setString(1, nodeUuid);
            ResultSet result = getNodeValidationFlagStatus.executeQuery();
            while (result.next())
            {
                flagStatus = result.getString("REQUIRES_VERIFICATION_FLAG");
            }
            result.close();
            getNodeValidationFlagStatus.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with getting the node validation flag status for node with nodeUuid: %s", nodeUuid));
        }
        return flagStatus;
    }

    public static void setNodeToGreenCheckValidationFlag(String contentUuid, String contentSet, Connection connection)
    {
        setNodeValidationFlag(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_GREEN_CHECK_VALIDATION_FLAG_QUERY, connection);
    }

    public static void setNodeToGreenCheckValidationFlagWithGivenNodeUuid(String nodeUuid, String contentSet, Connection connection)
    {
        setNodeValidationFlag(nodeUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_GREEN_CHECK_VALIDATION_FLAG_QUERY_WITH_GIVEN_NODE_UUID, connection);
    }

    public static void setNodeToErrorValidationFlag(String contentUuid, String contentSet, Connection connection)
    {
        setNodeValidationFlag(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_ERROR_VALIDATION_FLAG_QUERY, connection);
    }

    public static void setNodeToWarningValidationFlag(String contentUuid, String contentSet, Connection connection)
    {
        setNodeValidationFlag(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_WARNING_VALIDATION_FLAG_QUERY, connection);
    }

    public static void setNodeToInfoValidationFlag(String contentUuid, String contentSet, Connection connection)
    {
        setNodeValidationFlag(contentUuid, contentSet, HierarchyDatabaseConstants.SET_NODE_TO_INFO_VALIDATION_FLAG_QUERY, connection);
    }

    public static void updateLegisStartEffectiveDate(String contentUuid, String startDate, Connection connection)
    {
        logger.information("Updating Legis Start Effective Date for Node with Content uuid: " + contentUuid);
        updateDateInTocNode(contentUuid, startDate, HierarchyDatabaseConstants.UPDATE_LEGIS_START_EFFECTIVE_DATE_QUERY, connection);
    }
    public static void updateLegisEndEffectiveDate(String contentUuid, String endDate, Connection connection)
    {
        updateDateInTocNode(contentUuid, endDate, HierarchyDatabaseConstants.UPDATE_LEGIS_END_EFFECTIVE_DATE_QUERY, connection);
    }

    /**
     * This method takes a CONTENT UUID and updates the modifiedByDate metadata for the node
     * @param contentUuid content uuid of target node
     * @param modifiedByDate formatted date (06-JAN-2022) OR (05-MAR-2022 9:32:00) you can include the time or leave it blank, and it will be auto generated in the database
     *                      Use DateAndTimeUtils.getCurrentDateddMMMYYYY() for best results
     * @param connection standard connection used for all database methods
     */
    public static void updateModifiedDate(String contentUuid, String modifiedByDate, Connection connection)
    {
        updateDateInTocNode(contentUuid, modifiedByDate, HierarchyDatabaseConstants.UPDATE_MODIFIED_DATE_QUERY, connection);
    }
    /*change the start date of the node. Have date in this format 31-jul-07 (day-mon-year) */
    public static void updateDateInTocNode(String contentUuid, String startDate, String query, Connection connection)
    {
        logger.information(String.format("update the start date to %s with a content uuid = %s", startDate, contentUuid));
        try
        {
            PreparedStatement updateStartStatement = connection.prepareStatement(query);
            updateStartStatement.setString(1, startDate);
            updateStartStatement.setString(2, contentUuid);
            int recordsChanged = updateStartStatement.executeUpdate();
            commit(connection);
            updateStartStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occured in the database while trying to update the date "));
        }
    }

    public static void updateNodeCodeId(int codeId, String nodeUuid, Connection connection)
    {
        logger.information(String.format("Updating the node %s's code id to %s.", nodeUuid, codeId));
        try
        {
            PreparedStatement updateCodeNameStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_NODE_CODE_ID_QUERY);
            updateCodeNameStatement.setInt(1, codeId);
            updateCodeNameStatement.setString(2, nodeUuid);
            updateCodeNameStatement.executeUpdate();
            updateCodeNameStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Updating the node %s's code name to %s failed: %s", codeId, nodeUuid, e.toString()));
        }
    }

    public static void updateNodeType(int nodeType, String nodeUuid, Connection connection)
    {
        logger.information(String.format("Updating the node %s's node type to %s", nodeUuid, nodeType));
        try
        {
            PreparedStatement updateNodeTypeStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_NODE_TYPE_QUERY);
            updateNodeTypeStatement.setInt(1, nodeType);
            updateNodeTypeStatement.setString(2, nodeUuid);
            updateNodeTypeStatement.executeUpdate();
            updateNodeTypeStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Updating the node %s's node type to %s failed: %s", nodeUuid, nodeType, e.toString()));
        }
    }

    public static void updateNodeCodeLevel(int codeLevel, String nodeUuid, Connection connection)
    {
        logger.information(String.format("Updating the node %s's code level to %s", nodeUuid, codeLevel));
        try
        {
            PreparedStatement updateCodeLevelStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_NODE_CODE_LEVEL_QUERY);
            updateCodeLevelStatement.setInt(1, codeLevel);
            updateCodeLevelStatement.setString(2, nodeUuid);
            updateCodeLevelStatement.executeUpdate();
            updateCodeLevelStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Updating the node %s's code level to %s failed: %s", nodeUuid, codeLevel, e.toString()));
        }
    }

    public static void updateNodeVersioned(int versioned, String nodeUuid, Connection connection)
    {
        logger.information(String.format("Updating the node %s's versioned to %s", nodeUuid, versioned));
        try
        {
            PreparedStatement updateVersionedStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_NODE_VERSIONED_QUERY);
            updateVersionedStatement.setInt(1, versioned);
            updateVersionedStatement.setString(2, nodeUuid);
            updateVersionedStatement.executeUpdate();
            updateVersionedStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Updating the node %s's versioned level to %s failed: %s", nodeUuid, versioned, e.toString()));
        }
    }

    public static void setEffectiveEndDateToNull(String nodeUuid, String contentSet, Connection connection)
    {
        logger.information(String.format("Updating this node's legis end effective date to null with these parameters: nodeUuid = %s contentSet = %s", nodeUuid, contentSet));
        try
        {
            PreparedStatement setEndDateNull = connection.prepareStatement(HierarchyDatabaseConstants.SET_LEGIS_END_EFFECTIVE_DATE_NULL_QUERY);
            setEndDateNull.setString(1, nodeUuid);
            setEndDateNull.setString(2, contentSet);

            int recordsChanged = setEndDateNull.executeUpdate();
            commit(connection);
            setEndDateNull.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Setting the node's end effective date with parameters: nodeUuid %s and contentSet %s failed, %s", nodeUuid, contentSet, e.toString()));
        }
    }

    /**
     * Sets the volume number of a specific node given with nodeUuid
     * @param volumeNumber
     * @param nodeUuid
     * @param contentSet
     * @param connection
     */
    public static void setNodeVolumeNumberTo(String volumeNumber, String nodeUuid, String contentSet, Connection connection)
    {
        logger.information(String.format("Updating this node's volume number in the database to %s with these parameters: nodeUuid = %s contentSet = %s", volumeNumber, nodeUuid, contentSet));

        try
        {
            PreparedStatement setVolumeNumberStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_VOLUME_TO_QUERY);
            setVolumeNumberStatement.setString(1, volumeNumber);
            setVolumeNumberStatement.setString(2, nodeUuid);
            setVolumeNumberStatement.setString(3, contentSet);

            int recordsChanged = setVolumeNumberStatement.executeUpdate();
            commit(connection);
            setVolumeNumberStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query.");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Setting the volume number to %s failed: %s", volumeNumber, e));
        }
    }

    /**
     * Sets the volume number for all children of the given node uuid -- not including the parent node
     * @param volumeNumber
     * @param parentNodeUuid
     * @param connection
     */
    public static void setNodeVolumeNumberForDescendants(String volumeNumber, String parentNodeUuid, Connection connection)
    {
        logger.information(String.format("Updating all the children of node %s to volume number %s", parentNodeUuid, volumeNumber));

        try
        {
            PreparedStatement setVolumeNumberStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_VOLUME_TO_DESCENDANTS);
            setVolumeNumberStatement.setString(1, volumeNumber);
            setVolumeNumberStatement.setString(2, parentNodeUuid);

            int recordsChanged = setVolumeNumberStatement.executeUpdate();
            commit(connection);
            setVolumeNumberStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from the query");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Setting the volume number to %s for all children failed: %s", volumeNumber, e.toString()));
        }

    }

    /**
     * This method takes in a parent node and collects a list of all its direct non deleted children, used in getDescendentsListFromParentNode to build an array list that holds a parent node and all of its non-deleted children
     * @param parentNodeUUID parent node that will be checked for children
     * @param connection Connection to the database
     * @return Returns an array list containing all children of the given node
     */
    public static ArrayList<String> getChildListForGivenNode(String parentNodeUUID, Connection connection)
    {
        logger.information(String.format("Collecting a list of all children of parent with node_uuid: %s", parentNodeUUID));
        ArrayList<String> descendantsNodeUuid = new ArrayList<>();

        try
        {
            PreparedStatement getChildren = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_CHILDREN_FOR_GIVEN_NODE);
            getChildren.setString(1, parentNodeUUID);
            ResultSet result = getChildren.executeQuery();
            while (result.next())
            {
                descendantsNodeUuid.add(result.getString("CHILD_NODE_UUID"));
            }
            result.close();
            getChildren.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Finding list of children for parent node %s failed with error: %s", parentNodeUUID, e));
        }
        return descendantsNodeUuid;
    }


    private static void setNodeWipVersionLawTrackingStatus(String contentUuid, int wipVersion, String query, Connection connection)
    {
        logger.information(String.format("Updating this node's wip version law tracking status in the database with these parameters: contentUUID = %s wipVersion = %s", contentUuid, wipVersion));
        try
        {
            PreparedStatement setNodeStatusStatement =  connection.prepareStatement(query);
            setNodeStatusStatement.setString(1, contentUuid);
            setNodeStatusStatement.setInt(2, wipVersion);

            int recordsChanged = setNodeStatusStatement.executeUpdate();
            commit(connection);
            setNodeStatusStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with updating this node's wip version law tracking status with the parameters: ContentUUID = %s, contentSet = %s", contentUuid, wipVersion));
        }
    }

    public static void setNodesWipVersionLawTrackingStatusToFullVols(String contentUuid, int wipVersion, Connection connection)
    {
        setNodeWipVersionLawTrackingStatus(contentUuid, wipVersion, HierarchyDatabaseConstants.UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_QUERY, connection);
    }

    public static void setNodesWipVersionLawTrackingStatusToQuickLoad(String contentUuid, int wipVersion, Connection connection)
    {
        setNodeWipVersionLawTrackingStatus(contentUuid, wipVersion, HierarchyDatabaseConstants.UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_QUICK_LOAD_QUERY, connection);
    }

    public static void setNodesWipVersionLawTrackingStatusToFullVolsCompare(String contentUuid, int wipVersion, Connection connection)
    {
        setNodeWipVersionLawTrackingStatus(contentUuid, wipVersion, HierarchyDatabaseConstants.UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_COMPARE_QUERY, connection);
    }

    public static void setNodesWipVersionLawTrackingStatusToComLawTracking(String contentUuid, int wipVersion, Connection connection)
    {
        setNodeWipVersionLawTrackingStatus(contentUuid, wipVersion, HierarchyDatabaseConstants.UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_COM_LAW_TRACKING, connection);
    }

    public static void setNodesWipVersionLawTrackingStatusToFullVolsRecomp(String contentUuid, int wipVersion, Connection connection)
    {
        setNodeWipVersionLawTrackingStatus(contentUuid, wipVersion, HierarchyDatabaseConstants.UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_RECOMP_QUERY, connection);
    }

    //Gets the value of a node in Hierarchy via database
    public static String getNodeValue(String contentUuid, String contentSet, Connection connect)
    {
        String nodeValue = "";
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VALUE_QUERY);
            getNodeValueQuery.setString(1, contentUuid);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeValue = result.getString("VAL");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }
        return nodeValue;
    }

    public static String getNodeCodeName(String contentUuid, Connection connect)
    {
        String codeName = "";
        try
        {
            PreparedStatement getCodeNameQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_CODE_NAME_QUERY);
            getCodeNameQuery.setString(1, contentUuid);
            ResultSet result = getCodeNameQuery.executeQuery();
            while (result.next())
            {
                codeName = result.getString("CODE_NAME");
            }
            result.close();
            getCodeNameQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }
        return codeName;
    }

    public static int getNodeCodeId(String nodeUuid, Connection connect)
    {
        int codeId = -1;
        try
        {
            PreparedStatement getCodeIdQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_CODE_ID_QUERY);
            getCodeIdQuery.setString(1, nodeUuid);
            ResultSet result = getCodeIdQuery.executeQuery();
            while (result.next())
            {
                codeId = result.getInt("CODE_ID");
            }
            result.close();
            getCodeIdQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", nodeUuid));
        }
        return codeId;
    }

    public static String getNodeLegacyText(String contentUuid, String contentSet, Connection connect)
    {
        String nodeLegacyText = "";
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_LEGACY_TEXT_QUERY);
            getNodeValueQuery.setString(1, contentUuid);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeLegacyText = result.getString("LEGACY_TEXT");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }
        return nodeLegacyText;
    }


    public static String getNodeVolumeWithContentUuid(String contentUuid, String contentSet, Connection connect)
    {
        String nodeVolume = "";
        logger.information("Getting child node volumes using contentUuid");
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VOLUME_QUERY_USING_CONTENT_UUID);
            getNodeValueQuery.setString(1, contentUuid);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeVolume = result.getString("VOLUME_NUM_STR");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }
        return nodeVolume;
    }

    public static String getNodeVolumeWithNodeUuid(String nodeUuid, String contentSet, Connection connect)
    {
        String nodeVolume = "";
        logger.information("Getting child node volumes using nodeUuid: " + nodeUuid);
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VOLUME_QUERY_USING_NODE_UUID);
            getNodeValueQuery.setString(1, nodeUuid);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeVolume = result.getString("VOLUME_NUM_STR");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: nodeUuid = %s", nodeUuid));
        }
        return nodeVolume;
    }

    public static String getVolumeTitleWithVolumeNumber(String volumeNumString, String contentSet, Connection connect)
    {
        String nodeVolumeTitle = "";
        logger.information("Getting volume title using volume number: " + volumeNumString);
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VOLUME_TITLE_QUERY_USING_VOLUME_NUMBER);
            getNodeValueQuery.setString(1, volumeNumString);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeVolumeTitle = result.getString("TITLE");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a volume title using the data: volumeNumString = %s\n %s", volumeNumString, e));
        }
        return nodeVolumeTitle;
    }

    public static int getNodeType(String nodeUuid, Connection connection)
    {
        int nodeType = -1;
        try
        {
            PreparedStatement getNodeTypeQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_TYPE_QUERY);
            getNodeTypeQuery.setString(1, nodeUuid);
            ResultSet result = getNodeTypeQuery.executeQuery();
            result.next();
            nodeType = result.getInt("NODE_TYPE_ID");
            result.close();
            getNodeTypeQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node type of node %s: %s", nodeUuid, e.toString()));
        }
        return nodeType;
    }

    public static int getNodeKeyword(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Keyword for nodeUUID: %s", nodeUuid));
        int nodeKeyword = -1;
        try
        {
            PreparedStatement getNodeKeywordQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_KEYWORD_QUERY);
            getNodeKeywordQuery.setString(1, nodeUuid);
            ResultSet result = getNodeKeywordQuery.executeQuery();
            result.next();
            nodeKeyword = result.getInt("KEYWORD_ID");
            result.close();
            getNodeKeywordQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node keyword of node %s: %s", nodeUuid, e.toString()));
        }
        return nodeKeyword;
    }

    public static int getNodeLegacyStatusCode(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Legacy Status for nodeUUID: %s", nodeUuid));
        int legacyStatusCode = -1;
        try
        {
            PreparedStatement getNodeKeywordQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_LEGACY_STATUS_CODE);
            getNodeKeywordQuery.setString(1, nodeUuid);
            ResultSet result = getNodeKeywordQuery.executeQuery();
            result.next();
            legacyStatusCode = result.getInt("LEGACY_STATUS_CODE");
            result.close();
            getNodeKeywordQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node keyword of node %s: %s", nodeUuid, e.toString()));
        }
        return legacyStatusCode;
    }

    public static int getCodeLevel(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Code Level for nodeUUID: %s", nodeUuid));
        int codeLevel = -1;
        try
        {
            PreparedStatement getNodeCodeLevelQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_CODE_LEVEL_QUERY);
            getNodeCodeLevelQuery.setString(1, nodeUuid);
            ResultSet result = getNodeCodeLevelQuery.executeQuery();
            result.next();
            codeLevel = result.getInt("IS_CODE_LEVEL");
            result.close();
            getNodeCodeLevelQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node keyword of node %s: %s", nodeUuid, e.toString()));
        }
        return codeLevel;
    }

    public static int getVersionedLevel(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting current Versioned Level for nodeUUID: %s", nodeUuid));
        int versionedLevel = -1;
        try
        {
            PreparedStatement getNodeVersionedQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VERSIONED_QUERY);
            getNodeVersionedQuery.setString(1, nodeUuid);
            ResultSet result = getNodeVersionedQuery.executeQuery();
            result.next();
            versionedLevel = result.getInt("IS_VERSIONED");
            result.close();
            getNodeVersionedQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node keyword of node %s: %s", nodeUuid, e.toString()));
        }
        return versionedLevel;
    }

    /**
     * This method takes a node uuid and returns the node's modified by date metadata, there is a disparity between what the database shows in Oracle SQL and what we get
     * when we run this query in our tests, so I (Ben Dunkerton) beefed this method up to make sure it returns a properly formatted modified
     * date value that can be used directly with the updateModifiedDate database method for cleanup
     * @param nodeUuid node uuid identifier of target node
     * @param connection standard connection used with all our database methods
     * @return
     */
    public static String getModifiedDate(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Getting Modified Date for nodeUUID: %s", nodeUuid));
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String modifiedDate = "";
        try
        {
            PreparedStatement getModifiedDateQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_MODIFIED_DATE);
            getModifiedDateQuery.setString(1, nodeUuid);
            ResultSet result = getModifiedDateQuery.executeQuery();
            result.next();
            modifiedDate = result.getString("MODIFIED_DATE");

            //Formatting the query result to match how the data is displayed when we query for it in Oracle SQL
            StringTokenizer tokenizer = new StringTokenizer(modifiedDate);
            String year = tokenizer.nextToken("-");
            String month = tokenizer.nextToken("-");
            String day = tokenizer.nextToken(" ");
            String time = tokenizer.nextToken();
            tokenizer = new StringTokenizer(time);
            time = (Integer.parseInt(tokenizer.nextToken(":")) % 12) + ":" + tokenizer.nextToken(":") + ":" +  tokenizer.nextToken(":");

            modifiedDate = day.substring(1) + "-" + months[Integer.parseInt(month) - 1] + "-" + year + " " + time;

            result.close();
            getModifiedDateQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the modified date of node %s: %s", nodeUuid, e));
        }
        return modifiedDate;
    }

    public static String getModifiedBy(String nodeUuid, Connection connection)
    {
    logger.information(String.format("Getting Modified by info for nodeUUID: %s", nodeUuid));
    String modifiedBy = "";
    try
    {
        PreparedStatement getModifiedByQuery = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_MODIFIED_BY);
        getModifiedByQuery.setString(1, nodeUuid);
        ResultSet result = getModifiedByQuery.executeQuery();
        result.next();
        modifiedBy = result.getString("MODIFIED_BY");
        result.close();
        getModifiedByQuery.close();
    }
    catch (SQLException e)
    {
        Assertions.fail(String.format("There was an error getting the modified by info of node %s: %s", nodeUuid, e));
    }
    return modifiedBy;
}


    /*
    The following two query methods can be used to mock up a node with a lock in Hierarchy.
    See PublishingUserInterfaceTests.java class and search for one of the two methods to see how they're used in an actual test.
     */
    public static void insertNodeIntoTocNodeLockTable(String lockUuid, String lockedItemDescription, int lockType,
                                                      String lockUserId, String lockDate, int lockReason, String volume,
                                                      String nodeUuid, Connection connection)
    {
        try
        {
            PreparedStatement insertNodeIntoTocNodeLockTableStatement =  connection.prepareStatement(HierarchyDatabaseConstants.INSERT_NODE_INTO_TOC_NODE_LOCK_TABLE_QUERY);
            insertNodeIntoTocNodeLockTableStatement.setString(1, lockUuid);
            insertNodeIntoTocNodeLockTableStatement.setString(2, lockedItemDescription);
            insertNodeIntoTocNodeLockTableStatement.setInt(3, lockType);
            insertNodeIntoTocNodeLockTableStatement.setString(4, lockUserId);
            insertNodeIntoTocNodeLockTableStatement.setString(5, lockDate);
            insertNodeIntoTocNodeLockTableStatement.setInt(6, lockReason);
            insertNodeIntoTocNodeLockTableStatement.setString(7, volume);
            insertNodeIntoTocNodeLockTableStatement.setString(8, nodeUuid);

            int recordsChanged = insertNodeIntoTocNodeLockTableStatement.executeUpdate();
            commit(connection);
            insertNodeIntoTocNodeLockTableStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query. Assure that all of the data passed in is accurate.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with inserting a node into the TOC_NODE_LOCK table with node UUID = %s", nodeUuid));
        }
    }

    public static void insertNodeIntoLockedTocNodeTable(String nodeUuid, String loadUuid, Connection connection)
    {
        try
        {
            PreparedStatement insertNodeIntoTocNodeLockTableStatement =  connection.prepareStatement(HierarchyDatabaseConstants.INSERT_NODE_INTO_LOCKED_TOC_NODES_TABLE_QUERY);
            insertNodeIntoTocNodeLockTableStatement.setString(1, nodeUuid);
            insertNodeIntoTocNodeLockTableStatement.setString(2, loadUuid);

            int recordsChanged = insertNodeIntoTocNodeLockTableStatement.executeUpdate();
            commit(connection);
            insertNodeIntoTocNodeLockTableStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query. Assure that all of the data passed in is accurate.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with inserting a node into the LOCKED_TOC_NODES table with node UUID = %s", nodeUuid));
        }
    }

    /*
    The following query method should be used as clean up to remove a lock from a node in Hierarchy.
    See PublishingUserInterfaceTests.java class and search for this method to see how it's used in an actual test.
     */
    public static void deleteNodeFromTocNodeLockTable(String nodeUuid, Connection connection)
    {
        try
        {
            PreparedStatement deleteNodeFromTocNodeLockTableStatement =  connection.prepareStatement(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_NODE_LOCK_TABLE_QUERY);
            deleteNodeFromTocNodeLockTableStatement.setString(1, nodeUuid);

            int recordsChanged = deleteNodeFromTocNodeLockTableStatement.executeUpdate();
            commit(connection);
            deleteNodeFromTocNodeLockTableStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were deleted from that query. Assure that this node appears within the TOC_NODE_LOCK table.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with deleting a node from the TOC_NODE_LOCK table with node UUID = %s", nodeUuid));
        }
    }

    /*
    The following query method should be used as clean up to remove a lock from a node in Hierarchy.
    It will not fail if the node specified is already not locked.
    See QueryNoteTests.java to see how it is used in actual tests.
     */
    public static void deleteNodeFromTocNodeLockTableNoFailIfNotFound(String nodeUuid, Connection connection)
    {
        try
        {
            PreparedStatement deleteNodeFromTocNodeLockTableStatement =  connection.prepareStatement(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_NODE_LOCK_TABLE_QUERY);
            deleteNodeFromTocNodeLockTableStatement.setString(1, nodeUuid);

            int recordsChanged = deleteNodeFromTocNodeLockTableStatement.executeUpdate();
            commit(connection);
            deleteNodeFromTocNodeLockTableStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with deleting a node from the TOC_NODE_LOCK table with node UUID = %s", nodeUuid));
        }
    }

    /**
     * This method changes the is_deleted flag in the database to 1 for a node
     * @param nodeUuid
     * @param username
     * @param connection
     */
    public static void setNodeDeleted(String nodeUuid, String username, Connection connection) //todo look into these delte methods
    {
        logger.information(String.format("Setting the node to deleted with nodeUuid: %s", nodeUuid));

        try
        {
            PreparedStatement deleteNodeStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_DELETED_QUERY);
            deleteNodeStatement.setString(1, username);
            deleteNodeStatement.setString(2, nodeUuid);
            deleteNodeStatement.executeUpdate();
            deleteNodeStatement.close();

            PreparedStatement setDispDerivNodeDeletedStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_DELETED_DISP_DERIV_TABLE_QUERY);
            setDispDerivNodeDeletedStatement.setString(1, username);
            setDispDerivNodeDeletedStatement.setString(2, nodeUuid);
            setDispDerivNodeDeletedStatement.setString(3, nodeUuid);
            setDispDerivNodeDeletedStatement.executeUpdate();
            commit(connection);
            setDispDerivNodeDeletedStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error setting the node %s to deleted: %s", nodeUuid, e.toString()));
        }
    }

    /**
     * This method sets the is_deleted flag to 0 in the database for a node
     * @param nodeUuid
     * @param username
     * @param connection
     */
    public static void setNodeUndeleted(String nodeUuid, String username, Connection connection)
    {
        logger.information(String.format("Setting the node to undeleted with nodeUuid: %s", nodeUuid));

        try
        {
            PreparedStatement setNodeUndeletedStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_UNDELETED_QUERY);
            setNodeUndeletedStatement.setString(1, username);
            setNodeUndeletedStatement.setString(2, nodeUuid);
            setNodeUndeletedStatement.executeUpdate();
            setNodeUndeletedStatement.close();

            PreparedStatement setDispDerivNodeUndeletedStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_UNDELETED_DISP_DERIV_TABLE_QUERY);
            setDispDerivNodeUndeletedStatement.setString(1, username);
            setDispDerivNodeUndeletedStatement.setString(2, nodeUuid);
            setDispDerivNodeUndeletedStatement.setString(3, nodeUuid);
            setDispDerivNodeUndeletedStatement.executeUpdate();
            commit(connection);
            setDispDerivNodeUndeletedStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error setting the node %s to undeleted: %s", nodeUuid, e.toString()));
        }
    }

    /**
     * This method deletes records from the TOC_NODE_ALT_CITE table where the nodeUuid matches
     * @param nodeUuid
     * @param connection
     */
    public static void resetAlternativeCite(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Resetting alternative cite on node with uuid: %s", nodeUuid));
        try
        {
            PreparedStatement resetAlternativeCiteStatement = connection.prepareStatement(HierarchyDatabaseConstants.DELETE_ALTERNATIVE_CITE_QUERY);
            resetAlternativeCiteStatement.setString(1, nodeUuid);

            int recordsChanged = resetAlternativeCiteStatement.executeUpdate();
            commit(connection);
            resetAlternativeCiteStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were deleted from that query.");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Resetting the alternative cite on the node %s failed: %s", nodeUuid, e.toString()));
        }
    }

    /**
     * This method goes through all tables that a node may be found in and deletes the records containing the node
     * @param nodeUuid
     * @param connection
     */
    public static void deepDeleteNode(String nodeUuid, Connection connection)
    {
        logger.information(String.format("Deep deleting node with node uuid: %s", nodeUuid));

        for (String statement : deepDeleteDispDerivNodeStatementStringsList)
        {
            try
            {
                PreparedStatement deepDeleteDispDerivStatement = connection.prepareStatement(statement);
                deepDeleteDispDerivStatement.setString(1, nodeUuid);
                deepDeleteDispDerivStatement.setString(2, nodeUuid);
                deepDeleteDispDerivStatement.executeUpdate();
                commit(connection);
                deepDeleteDispDerivStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deep deleting node failed: %s", e.toString()));
            }
        }

        for (String statement : deepDeleteNodeStatementStringsList)
        {
            try
            {
                PreparedStatement deepDeleteNodeStatement = connection.prepareStatement(statement);
                deepDeleteNodeStatement.setString(1, nodeUuid);
                deepDeleteNodeStatement.executeUpdate();
                commit(connection);
                deepDeleteNodeStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deep deleting node failed: %s", e.toString()));
            }
        }
    }

    public static String getNodeUuidFromTocNodeTableWithNullValue(Connection connection, int keywordId, String contentSetId, int volumneNumber)
    {
        return getNodeUuidFromTocNodeTableWhereValueISNull(connection, keywordId, contentSetId, volumneNumber,"NODE_UUID");
    }

    public static String getContentUuidFromTocNodeTableWithNullValue(Connection connection, int keywordId, String contentSetId, int volumneNumber)
    {
        return getNodeUuidFromTocNodeTableWhereValueISNull(connection, keywordId, contentSetId, volumneNumber, "CONTENT_UUID");
    }

    /*
        The query method gets the node uuid or content uuid via the js_contentSet, Keyword Id, and the volumne number ->this is for when the value is Null
     */
    public static String getNodeUuidFromTocNodeTableWhereValueISNull(Connection connection, int keywordId, String contentSetId, int volumneNumber, String contentOrNodeUuid) {
        logger.information(String.format("Selecting a node from the database with these parameters: keyword = %d, contentSetID = %s, volumeNumber = %d", keywordId, contentSetId, volumneNumber));
        String nodeUuid = "";
        try
        {
            PreparedStatement getNodeUuidFromTocNodeTableStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_UUID_WITH_NULL_VALUE);
            getNodeUuidFromTocNodeTableStatement.setInt(1, keywordId);
            getNodeUuidFromTocNodeTableStatement.setInt(2, Integer.parseInt(contentSetId));
            getNodeUuidFromTocNodeTableStatement.setInt(3, volumneNumber);

            ResultSet result = getNodeUuidFromTocNodeTableStatement.executeQuery();

            while (result.next())
            {
                nodeUuid = result.getString("NODE_UUID");
            }
            commit(connection);
            result.close();
            getNodeUuidFromTocNodeTableStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error getting the node uuid "));
        }
        return nodeUuid;
    }

    public static void updateIsDeleteFlagToUndeletedHierarchyNavigate(String nodeUuid, String contentSet, Connection connection)
    {
        updateDeletedFlagToBeDeleted(nodeUuid, contentSet, connection, 0);
    }

    public static void updateIsDeleteFlagToDeletedHierarchyNavigate(String nodeUuid, String contentSet, Connection connection)
    {
        updateDeletedFlagToBeDeleted(nodeUuid, contentSet, connection, 1);
    }

    /*This updatd the Deleted flag to be 1 or 0 depending on what is needed */
    private static void updateDeletedFlagToBeDeleted(String NodeUuid, String contentSet, Connection connection, int deleteFlagStatus)
    {
        NodeUuid = NodeUuid.toUpperCase();
        logger.information(String.format("Updating a node from the database with these parameters: deletedStatus =  %d, nodeUuid = %s, ContentSetID=%s", deleteFlagStatus, NodeUuid, contentSet));
        try
        {
            PreparedStatement updateDeleteFlagStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_DELETE_FLAG_TO_BE_DELETED_HIERARCHY_NAVIAGTE);
            updateDeleteFlagStatement.setInt(1, deleteFlagStatus);
            updateDeleteFlagStatement.setString(2, NodeUuid);
            updateDeleteFlagStatement.setInt(3, Integer.parseInt(contentSet));
            int recordsChanged = updateDeleteFlagStatement.executeUpdate();
            commit(connection);
            updateDeleteFlagStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "The node with a uuid of " + NodeUuid + " was updated to be deleted");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database with updating this node to be deleted "));
        }
    }

    public static void updateIsDeleteFlagToUndeletedPubNavigate(String nodeUuid, String contentSet, Connection connection)
    {
        updateDeletedFlagTobeDeletedInPubNavigate(nodeUuid, contentSet, connection, 0);
    }

    public static void updateIsDeleteFlagToDeletedPubNavigate(String nodeUuid, String contentSet, Connection connection)
    {
        updateDeletedFlagTobeDeletedInPubNavigate(nodeUuid, contentSet, connection, 1);
    }

    private static void updateDeletedFlagTobeDeletedInPubNavigate(String NodeUuid, String contentSet, Connection connection, int deleteFlagStatus)
    {
        NodeUuid = NodeUuid.toUpperCase();
        logger.information(String.format("Updating a node from the database with these parameters: deletedStatus =  %d, nodeUuid = %s, ContentSetID=%s", deleteFlagStatus, NodeUuid, contentSet));
        try
        {
            PreparedStatement updateDeleteFlagStatement = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_DELETE_FLAG_TO_BE_DELETED_PUB_NAVIGATE);
            updateDeleteFlagStatement.setInt(1, deleteFlagStatus);
            updateDeleteFlagStatement.setString(2, NodeUuid);
            updateDeleteFlagStatement.setInt(3, Integer.parseInt(contentSet));


            int recordsChanged = updateDeleteFlagStatement.executeUpdate();
            commit(connection);
            updateDeleteFlagStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "The node with a uuid of " + NodeUuid + " was updated to be deleted");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("An issue occurred in the database with updating this node to be deleted ");
        }
    }

    public static void updateModifiedBy(String nodeUuid, String user, Connection connection)
    {
        logger.information(String.format("update the modified by metadata for node: %s", nodeUuid));
        try
        {
            PreparedStatement updateModifiedBy = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_NODE_MODIFIED_BY_QUERY);
            updateModifiedBy.setString(1, user);
            updateModifiedBy.setString(2, nodeUuid);
            int recordsChanged = updateModifiedBy.executeUpdate();
            commit(connection);
            updateModifiedBy.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("An issue occurred in the database while trying to update the modified by metadata ");
        }
    }

    /* get the latest Wip Version of selected content uuid */
    public static int getLatestWIPVersionOfSelectedContentUuid(Connection connection, String contentUuid)
    {
        int WipVersion =0;
        logger.information(String.format("Get the latest Wip Version with these parameters: conntentUuid= %s", contentUuid));
        try
        {
            PreparedStatement updateDeleteFlagStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_LATEST_WIP_VERSION);
            updateDeleteFlagStatement.setString(1, contentUuid);

            ResultSet result = updateDeleteFlagStatement.executeQuery();
            commit(connection);
            while (result.next())
            {
                WipVersion = ((Number) result.getObject(1)).intValue();
            }
            result.close();
            updateDeleteFlagStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occured in the database while trying to get the latest WIP Version"));
        }
        return WipVersion;
    }

    /* get the latest Wip Version of selected content uuid */
    public static String getModifiedByInTocContent(Connection connection, String contentUuid, int wipVersion)
    {
        String modifiedBy ="";
        logger.information(String.format("Get the latest Wip Version with these parameters: conntentUuid= %s", contentUuid));
        try
        {
            PreparedStatement getModifiedByStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_MODIFIED_BY);
            getModifiedByStatement.setString(1, contentUuid);
            getModifiedByStatement.setInt(1, wipVersion);

            ResultSet result = getModifiedByStatement.executeQuery();
            connection.close();
            while (result.next())
            {
                modifiedBy = result.toString();
            }
            modifiedBy = result.toString();
            result.close();
            getModifiedByStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occured in the database while trying to get the latest WIP Version"));
        }
        return modifiedBy;
    }

    public static String getQueryNoteReportHierarchyBreadcrumbForSelectedNode(String currentNodeUUID, Connection connection)
    {
        String parentNodeUUID = getNodeParentUUID(currentNodeUUID, connection);
        String breadcrumbString = "";
        while (!parentNodeUUID.equals(""))
        {
            breadcrumbString = getNodeDefaultDisplayNameValueHeadText(parentNodeUUID, connection) + " -- " + breadcrumbString;
            parentNodeUUID = getNodeParentUUID(parentNodeUUID, connection);
        }
        breadcrumbString = breadcrumbString.substring(0, breadcrumbString.lastIndexOf(" -- "));
        return breadcrumbString;
    }

    /**
     *
     * @param nodeUuid
     * @param connection
     * @return This Method returns a concatenated string of a Nodes Default Display Name, Value, and Head text as they would appear in the Hierarchy Tree Page
     */
    public static String getNodeDefaultDisplayNameValueHeadText(String nodeUuid, Connection connection)
    {
        String treeTitle = "";
        logger.information(String.format("Get the node tree title for node with node_uuid of %s", nodeUuid));
        try
        {
            PreparedStatement getNodeHierarchyTreePageTitle = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_HIERARCHY_TREE_PAGE_TITLE);
            getNodeHierarchyTreePageTitle.setString(1, nodeUuid);
            getNodeHierarchyTreePageTitle.setString(2, nodeUuid);
            ResultSet result = getNodeHierarchyTreePageTitle.executeQuery();
            result.next();
            String defaultDisplayName = result.getString("DEFAULT_DISPLAY_NAME");
            String val = result.getString("VAL");
            String headText = result.getString("HEAD_TEXT");
            treeTitle += defaultDisplayName + " ";
            if (defaultDisplayName.equals("SET"))
            {
                treeTitle += val;
            }
            else
            {
                treeTitle += val + " ";
            }
            if (headText != null)
            {
                treeTitle += headText.replace("<conditional product.type=\"print\" action=\"suppress\">", "").replace("</conditional>", "");
            }
            result.close();
            getNodeHierarchyTreePageTitle.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the node tree title of node %s: %s", nodeUuid, e));
        }
        return treeTitle;
    }

    public static String getNodeParentUUID(String nodeUuid, Connection connection)
    {
        String parentNodeUUID = "";
        logger.information(String.format("Getting the parent node UUID for child node UUID of %s", nodeUuid));
        try
        {
            PreparedStatement getNodeParentUUID = connection.prepareStatement(HierarchyDatabaseConstants.GET_NODE_PARENT_UUID);
            getNodeParentUUID.setString(1, nodeUuid);
            ResultSet result = getNodeParentUUID.executeQuery();
            while(result.next())
            {
                parentNodeUUID = result.getString("PARENT_NODE_UUID");
            }
            result.close();
            getNodeParentUUID.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error getting the parent node UUID for child with node UUID of %s: %s", nodeUuid, e));
        }
        return parentNodeUUID;
    }

    public static void insertDateQueryNoteIntoNode(String queryNoteText, String createdBy, String nodeUuid, String paraUuid, int jsContentSetID, Connection connection)
    {
        String queryNoteUuid = DataMocking.generateUUID();
        String automaticallyCreatedQueryNoteUuid = DataMocking.generateUUID();
        logger.information(String.format("Inserting a query note into paragraph %s of node %s", nodeUuid, paraUuid));
        try
        {
            PreparedStatement insertDateQueryNote = connection.prepareStatement(HierarchyDatabaseConstants.INSERT_DATE_QUERY_NOTE);
            insertDateQueryNote.setString(1, queryNoteUuid);
            insertDateQueryNote.setString(2, queryNoteText);
            insertDateQueryNote.setString(3, createdBy);
            insertDateQueryNote.setString(4, nodeUuid);
            insertDateQueryNote.setString(5, paraUuid);
            insertDateQueryNote.setInt(6, jsContentSetID);
            insertDateQueryNote.setString(7, queryNoteText);
            insertDateQueryNote.executeUpdate();
            insertDateQueryNote.close();

            PreparedStatement insertAutomaticallyCreatedQuery = connection.prepareStatement(HierarchyDatabaseConstants.INSERT_AUTOMATICALLY_CREATED_QUERY);
            insertAutomaticallyCreatedQuery.setString(1, automaticallyCreatedQueryNoteUuid);
            insertAutomaticallyCreatedQuery.setString(2, createdBy);
            insertAutomaticallyCreatedQuery.setString(3, nodeUuid);
            insertAutomaticallyCreatedQuery.setInt(4, jsContentSetID);
            insertAutomaticallyCreatedQuery.executeUpdate();
            insertAutomaticallyCreatedQuery.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            logger.severe(e.toString());
            Assertions.fail(String.format("There was an error inserting a query note into paragraph %s of node %s", paraUuid, nodeUuid));
        }
    }


    /**
     * This method takes in a parent node uuid, and builds a list of all non-deleted descendants using getChildListForGivenNode
     * @param parentNodeUuid Base node which is the root of the descendant list
     * @param connection Connection to the database
     * @return Returns an array list that starts with the base parent node and holds all non deleted descendants
     */
    public static ArrayList<String> getDescendentsListFromParentNode(String parentNodeUuid, Connection connection)
    {
        int i = 0;
        ArrayList<String> descendantsList = new ArrayList<>();
        descendantsList.add(parentNodeUuid);
        while (i < descendantsList.size())
        {
            ArrayList<String> queryResults = HierarchyDatabaseUtils.getChildListForGivenNode(descendantsList.get(i), connection);
            for (String child : queryResults)
            {
                descendantsList.add(child);
            }
            i++;
        }
        return descendantsList;
    }

    /**
     * This method grabs both the Code ID and Code Name of a given node.
     * @param nodeUuid Uses a node uuid to target a specific node's metadata
     * @param connection Pass in the database connection created in the main test
     * @return It return an array list that holds both values. In a main test, call "arrayListName".get(0) to pull the Code ID value and "arrayListName".get(1) to pull the Code Name value
     *
     */
    public static ArrayList<String> getCodeNameAndID(String nodeUuid, Connection connection)
    {
        ArrayList<String> metadata = new ArrayList<>();
        logger.information(String.format("Getting current code id and code name for node with UUID %s", nodeUuid));
        try {
            PreparedStatement getCurrentCodeNameAndCodeID = connection.prepareStatement(HierarchyDatabaseConstants.GET_CURRENT_CODE_NAME_AND_CODE_ID);
            getCurrentCodeNameAndCodeID.setString(1, nodeUuid);
            ResultSet result = getCurrentCodeNameAndCodeID.executeQuery();
            while (result.next())
            {
                metadata.add(result.getString("code_id"));
                metadata.add(result.getString("code_name"));
            }
            result.close();
            getCurrentCodeNameAndCodeID.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Could not find either the Code Name or the Code ID", e));
        }
        return metadata;
    }

    public static void deleteVolumeFromDatabase(Connection connection, String title, String volumeID, String contentSet)
    {
        logger.information(String.format("deleting the volume with VolumeId: %s ", volumeID));
        try
        {
            PreparedStatement deleteVolumeFromVolumePartTable = connection.prepareStatement(HierarchyDatabaseConstants.DELETE_VOLUME_FROM_VOLUME_PART);
            deleteVolumeFromVolumePartTable.setString(1, title);
            deleteVolumeFromVolumePartTable.setString(2, volumeID);
            deleteVolumeFromVolumePartTable.setInt(3, Integer.parseInt(contentSet));
            int recordsChanged = deleteVolumeFromVolumePartTable.executeUpdate();
            deleteVolumeFromVolumePartTable.close();
            Assertions.assertTrue(recordsChanged == 1, String.format("the volume with ID %s was updated to deleted from volume part table", volumeID));

            PreparedStatement deleteVolumeFromVolumeTable = connection.prepareStatement(HierarchyDatabaseConstants.DELETE_VOLUME_FROM_VOLUME);
            deleteVolumeFromVolumeTable.setString(1, title);
            deleteVolumeFromVolumeTable.setString(2, volumeID);
            deleteVolumeFromVolumeTable.setInt(3, Integer.parseInt(contentSet));
            recordsChanged = deleteVolumeFromVolumeTable.executeUpdate();

            commit(connection);
            deleteVolumeFromVolumeTable.close();
            Assertions.assertTrue(recordsChanged == 1, String.format("the volume with ID %s was updated to deleted from volume table", volumeID));
        }
        catch (SQLException e)
        {
            logger.severe(e.toString());
            Assertions.fail(String.format("Deleting %s from the volume and volume part tables failed", volumeID));
        }
    }


    /**
     * This method deletes a code name based off of the user who created it, the specified name, and the content set it exists in
     * @param createdBy The specified user who created the code name
     * @param createdCodeName The code name *star of the show*
     * @param contentSet The content set the code name lives in
     * @param connection Connection to the Database
     */
    public static void deleteCodeName(String createdBy, String createdCodeName, String contentSet, Connection connection)
    {
        logger.information(String.format("Deleting code name: %s created for this test", createdCodeName));
        try
        {
            PreparedStatement deleteCreatedCodeName = connection.prepareStatement(HierarchyDatabaseConstants.DELETE_CREATED_CODE_NAME_QUERY);
            deleteCreatedCodeName.setString(1, createdBy);
            deleteCreatedCodeName.setString(2, createdCodeName);
            deleteCreatedCodeName.setInt(3, Integer.parseInt(contentSet));
            int recordsChanged = deleteCreatedCodeName.executeUpdate();
            commit(connection);
            deleteCreatedCodeName.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting selected CodeName: %s with code id: %s in contentSet: %s failed, %s", createdCodeName, createdBy, contentSet, e));
        }
    }

    public static String getNodeValueByNodeUUID(String nodeUuid, String contentSet, Connection connect)
    {
        logger.information(String.format("Getting current Node Value for nodeUUID: %s", nodeUuid));
        String nodeValue = "";
        try
        {
            PreparedStatement getNodeValueQuery = connect.prepareStatement(HierarchyDatabaseConstants.GET_NODE_VALUE_BY_NODE_UUID_QUERY);
            getNodeValueQuery.setString(1, nodeUuid);
            getNodeValueQuery.setInt(2, Integer.parseInt(contentSet));
            ResultSet result = getNodeValueQuery.executeQuery();
            while (result.next())
            {
                nodeValue = result.getString("VAL");
            }
            result.close();
            getNodeValueQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: nodeUUID = %s", nodeUuid));
        }
        return nodeValue;
    }

    public static long getMaxRowInTocNodeAudit(Connection connection)
    {
        logger.information(String.format("Select Max entry into toc node audit table for inserting new row later"));
        long result = 1;
        try
        {

            PreparedStatement insertNewTocNodeAuditEntry = connection.prepareStatement(HierarchyDatabaseConstants.GET_MAX_TOC_NODE_AUDIT_ROW);
            ResultSet queryResult = insertNewTocNodeAuditEntry.executeQuery();
            queryResult.next();
            result = queryResult.getLong("audit_id");
            queryResult.close();
            insertNewTocNodeAuditEntry.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("Selecting max row from toc_node_audit failed to execute");
        }
        return result;
    }

    /**
     * Note, this does not update this to the latest version, will have to call update wip version to latest for that
     * @param contentUuid
     * @param wipVersion
     * @param modifiedBy
     * @param textValue
     * @param displayHeadText
     * @param contentSet
     * @param connection
     */
    public static void insertNewWipVersionInTocContent(String contentUuid, int wipVersion, String modifiedBy, String textValue, String displayHeadText, int contentSet, Connection connection)
    {
        logger.information(String.format("Insert entry into toc content table as new wip version = %s", contentUuid));
        try
        {
            PreparedStatement insertNewWipVersionStatement = connection.prepareStatement(HierarchyDatabaseConstants.INSERT_NEW_WIP_VERSION_WITH_VALUE);
            insertNewWipVersionStatement.setString(1, contentUuid);
            insertNewWipVersionStatement.setInt(2, wipVersion);
            insertNewWipVersionStatement.setString(3, modifiedBy);
            insertNewWipVersionStatement.setClob(4, CommonDataMocking.clobify(connection,textValue));
            insertNewWipVersionStatement.setString(5, displayHeadText);
            insertNewWipVersionStatement.setInt(6, contentSet);
            insertNewWipVersionStatement.executeQuery();
            commit(connection);
            insertNewWipVersionStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new wip version in toc content"));
        }
    }

    /**
     * current query fields to enter and order:
     * (node_uuid, content_uuid, material_id, wip_version, job_id, created_by, created_date, hierarchy_function,
     * node_inserted, node_metadata_modified, derived_from_changed, children_changed, script_changed, content_changed, alt_cite_changed, transaction_id, flag_changed, js_content_set_id)
     * @param nodeUuid
     * @param contentUuid
     * @param materialUuid
     * @param wipVersion
     * @param jobId
     * @param createdBy
     * @param hierarchyFunction
     * @param node_inserted
     * @param node_metadata_modified
     * @param derived_from_changed
     * @param children_changed
     * @param script_changed
     * @param content_changed
     * @param alt_cite_changed
     * @param flag_changed
     * @param contentSet
     * @param connection
     */
    public static void insertEntryIntoTocNodeAudit(long auditId, String nodeUuid, String contentUuid, String materialUuid, int wipVersion, String jobId, String createdBy, int hierarchyFunction,
                                                   int node_inserted, int node_metadata_modified, int derived_from_changed, int children_changed, int script_changed, int content_changed,
                                                   int alt_cite_changed, int flag_changed, int contentSet, Connection connection)
    {
        logger.information(String.format("Insert entry into toc node audit table with hierarchy function type, node_uuid = %s", nodeUuid));
        try
        {
            PreparedStatement insertNewTocNodeAuditEntry = connection.prepareStatement(HierarchyDatabaseConstants.INSERT_RECORD_INTO_TOC_NODE_AUDIT);

            insertNewTocNodeAuditEntry.setLong(1,auditId);
            insertNewTocNodeAuditEntry.setString(2,nodeUuid);
            insertNewTocNodeAuditEntry.setString(3,contentUuid);
            insertNewTocNodeAuditEntry.setString(4,materialUuid);
            insertNewTocNodeAuditEntry.setInt(5,wipVersion);
            insertNewTocNodeAuditEntry.setString(6,jobId);
            insertNewTocNodeAuditEntry.setString(7,createdBy);
            insertNewTocNodeAuditEntry.setInt(8,hierarchyFunction);
            insertNewTocNodeAuditEntry.setInt(9,node_inserted);
            insertNewTocNodeAuditEntry.setInt(10,node_metadata_modified);
            insertNewTocNodeAuditEntry.setInt(11,derived_from_changed);
            insertNewTocNodeAuditEntry.setInt(12,children_changed);
            insertNewTocNodeAuditEntry.setInt(13,script_changed);
            insertNewTocNodeAuditEntry.setInt(14,content_changed);
            insertNewTocNodeAuditEntry.setInt(15,alt_cite_changed);
            insertNewTocNodeAuditEntry.setString(16, CommonDataMocking.generateUUID());
            insertNewTocNodeAuditEntry.setInt(17,flag_changed);
            insertNewTocNodeAuditEntry.setInt(18, contentSet);
            insertNewTocNodeAuditEntry.executeQuery();
            commit(connection);
            insertNewTocNodeAuditEntry.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new entry into toc node audit"));
        }
    }
    public static void setLatestWipVersionToNotLatest(String contentUUID, Connection connection)
    {
        logger.information("Unsetting latest wip version for node with content uuid: " + contentUUID);
        try
        {
            PreparedStatement unsetLatestStatement = connection.prepareStatement(HierarchyDatabaseConstants.UNSET_LATEST_WIP_VERSION);
            unsetLatestStatement.setString(1, contentUUID);
            unsetLatestStatement.executeUpdate();
            unsetLatestStatement.close();
            commit(connection);
            unsetLatestStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to unset latest wip version on node with content uuid " + contentUUID + ". " + e.toString());
        }
    }

    public static String getKeywordDefaultDisplayName(Connection connection, String nodeUUID)
    {
        String defaultDisplayName = "";
        try
        {
            logger.information("Getting keyword Default Display Name");

            PreparedStatement getKeywordDefaultDisplayNameStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_KEYWORD_DEFAULT_DISPLAY_NAME);

            getKeywordDefaultDisplayNameStatement.setString(1, nodeUUID);
            ResultSet querySet = getKeywordDefaultDisplayNameStatement.executeQuery();
            querySet.next();
            defaultDisplayName = querySet.getString("DEFAULT_DISPLAY_NAME");
            commit(connection);
            querySet.close();
            getKeywordDefaultDisplayNameStatement.close();
        }
        catch (SQLException ex)
        {
            Assertions.fail("Failed get keyword Default Display name  with node uuid " + nodeUUID + ". " + ex.toString());
        }
        return defaultDisplayName;
    }

    public static String getVolumeNumberString(Connection connection, String uuid)
    {
        String volumeNumberString = "";

        try
        {
            PreparedStatement getVolumeNumberStringStatement = connection.prepareStatement(HierarchyDatabaseConstants.VOLUME_NUM_STR_PREPARES_STATEMENT);
            getVolumeNumberStringStatement.setString(1, uuid);
            ResultSet resultSet = getVolumeNumberStringStatement.executeQuery();
            resultSet.next();
            volumeNumberString = resultSet.getString("volume_num_str");
            resultSet.close();
            getVolumeNumberStringStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            Assertions.fail(String.format("Getting Legacy Text", e.toString()));
        }

        return volumeNumberString;
    }

    public static String getLegisStartEffectiveDate(Connection connection, String uuid)
    {
        String legisStartEffectiveDate = "";

        try
        {
            PreparedStatement getLegisStartEffectiveDateStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_START_DATE_QUERY);
            getLegisStartEffectiveDateStatement.setString(1, uuid);
            ResultSet resultSet = getLegisStartEffectiveDateStatement.executeQuery();
            resultSet.next();
            legisStartEffectiveDate = resultSet.getString("legis_start_effective_date");
            resultSet.close();
            getLegisStartEffectiveDateStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return legisStartEffectiveDate;
    }

    public static String getLegisEndEffectiveDate(Connection connection, String uuid)
    {
        String legisEndEffectiveDate = "";

        try
        {
            PreparedStatement getLegisEndEffectiveDateStatement = connection.prepareStatement(HierarchyDatabaseConstants.GET_END_DATE_QUERY);
            getLegisEndEffectiveDateStatement.setString(1, uuid);
            ResultSet resultSet = getLegisEndEffectiveDateStatement.executeQuery();
            resultSet.next();
            legisEndEffectiveDate = resultSet.getString("legis_end_effective_date");
            resultSet.close();
            getLegisEndEffectiveDateStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return legisEndEffectiveDate;
    }

    public static String getLegacyText(Connection connection, String uuid)
    {
        String legacyText = "";

        try
        {
            PreparedStatement getLegacyTextStatement = connection.prepareStatement(HierarchyDatabaseConstants.LEGACY_TEXT_PREPARED_STATEMENT);
            getLegacyTextStatement.setString(1, uuid);
            ResultSet resultSet = getLegacyTextStatement.executeQuery();
            resultSet.next();
            legacyText = resultSet.getString("legacy_text");
            resultSet.close();
            getLegacyTextStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            Assertions.fail(String.format("Getting Legacy Text", e.toString()));
        }

        return legacyText;
    }

    public static void setNodeValue(Connection connection, String nodeUUID, String newNodeValue)
    {
        try
        {
               PreparedStatement setNodeValuePreparedStatement = connection.prepareStatement(HierarchyDatabaseConstants.SET_NODE_VALUE_PREPARED_STATEMENT);

               setNodeValuePreparedStatement.setString(1, newNodeValue);
               setNodeValuePreparedStatement.setString(2, nodeUUID);
               setNodeValuePreparedStatement.executeUpdate();
               setNodeValuePreparedStatement.close();
               commit(connection);
               setNodeValuePreparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            Assertions.fail("Failed to set New node value");
        }
    }

    public static void updateLegacyInitRangeHID (Connection connection,String nodeUUID, int hid)
    {
        try
        {
            PreparedStatement updateLegacyInitRangeHID = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_LEGACY_INIT_RANGE_HID);

            updateLegacyInitRangeHID.setInt(1, hid);
            updateLegacyInitRangeHID.setString(2, nodeUUID);

            boolean legacyInitRangeHIDWasUpdated = updateLegacyInitRangeHID.executeUpdate() >= 1;
            commit(connection);
            updateLegacyInitRangeHID.close();
            Assertions.assertTrue(legacyInitRangeHIDWasUpdated, "The legacy wasn't updated correctly");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("Updating Legacy Init Range Hid");
        }
    }
    public static void updateInitRangeNodeUUID(Connection connection, String nodeUUID, String NodeHID)
    {
        try
        {
            PreparedStatement updateInitRangeNodeUUID = connection.prepareStatement(HierarchyDatabaseConstants.UPDATE_INIT_RANGE_NODE_UUID);
            updateInitRangeNodeUUID.setString(1,NodeHID);
            updateInitRangeNodeUUID.setString(2, nodeUUID);

            boolean initRangeNodeUUID = updateInitRangeNodeUUID.executeUpdate() >= 1;
            commit(connection);
            updateInitRangeNodeUUID.close();
            Assertions.assertTrue(initRangeNodeUUID, "The NodeHID was updated successfuly");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("Updating Init Range UUID failed");
        }
    }
}