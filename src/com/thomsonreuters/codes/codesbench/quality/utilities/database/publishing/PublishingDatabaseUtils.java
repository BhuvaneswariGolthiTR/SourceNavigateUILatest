package com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

import static java.lang.String.format;

public class PublishingDatabaseUtils extends BaseDatabaseUtils {

    /**
     * This method is for debugging purposes. The method will return a result from selecting the uuid and content set in the database.
     *
     * @param contentUuid
     * @param contentSet
     * @return
     */
    public static String selectPublishingNode(String contentUuid, int contentSet) //todo? can we get rid of this now?
    {
        logger.information(String.format("Selecting a node from the database with these parameters: nodeUUID = %s, contentSetID = %d", contentUuid, contentSet));

        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        try
        {
            PreparedStatement selectPublishingNodeStatement = connection.prepareStatement(PublishingDatabaseConstants.SELECT_PUBLISHING_NODE_QUERY);
            selectPublishingNodeStatement.setString(1, contentUuid);
            selectPublishingNodeStatement.setInt(2, contentSet);

            ResultSet result = selectPublishingNodeStatement.executeQuery();
            connection.close();
            return result.toString();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with selecting a node using the data: contentUUID = %s, contentSet = %d", contentUuid, contentSet));
        }
        return "";
    }

    public static void setPublishingNodeStatus(String contentUuid, String contentSet, String query, Connection connection)
    {
        logger.information(String.format("Updating the node publishing status in the database with these parameters: contentUUID = %s contentSet = %s", contentUuid, contentSet));
        try
        {
            PreparedStatement setPublishingNodeReadyStatement =  connection.prepareStatement(query);
            setPublishingNodeReadyStatement.setString(1, contentUuid);
            setPublishingNodeReadyStatement.setInt(2, Integer.parseInt(contentSet));

            int recordsChanged = setPublishingNodeReadyStatement.executeUpdate();
            commit(connection);
            setPublishingNodeReadyStatement.close();
            //Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with updating the node status with the parameters: ContentUUID = %s, contentSet = %s", contentUuid, contentSet));
        }
    }

    public static void setPublishingNodeToReady(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_READY_QUERY, connection);
    }

    public static void setPublishingNodeToNotPublish(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_NOT_PUBLISH_QUERY, connection);
    }

    public static void setPublishingNodeToCodesbenchFailure(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_CODESBENCH_FAILURE_QUERY, connection);
    }

    public static void setPublishingNodeToLTCFailure(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_LTC_FAILURE_QUERY, connection);
    }

    public static void setPublishingNodeToPublishing(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_PUBLISHING_QUERY, connection);
    }

    public static void resetPublishingNode(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.RESET_PUBLISHING_NODE_QUERY, connection);
    }

    public static void setPublishingNodeToApprove(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_APPROVE_QUERY, connection);
    }

    public static void setPublishingNodeToPublishComplete(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_PUBLISH_COMPLETE_QUERY, connection);
    }

    public static void setPublishingNodeToWestlawLoaded(String contentUuid, String contentSet, Connection connection)
    {
        setPublishingNodeStatus(contentUuid, contentSet, PublishingDatabaseConstants.SET_NODE_TO_WESTLAW_LOADED_QUERY, connection);
    }

    public static String getPublishingNodeApprovedDate(String contentUuid, String contentSet, Connection connection) {
        return getColumnInformationForTocContentPublishingStatus(contentUuid, contentSet, "PUBLISH_APPROVED_DATE", connection);
    }

    public static String getPublishingNodeReadyDate(String contentUuid, String contentSet, Connection connection) {
        return getColumnInformationForTocContentPublishingStatus(contentUuid, contentSet, "PUBLISH_READY_DATE", connection);
    }

    public static String getPublishingNodeCompleteDate(String contentUuid, String contentSet, Connection connection) {
        return getColumnInformationForTocContentPublishingStatus(contentUuid, contentSet, "PUB_COMPLETE_DATE", connection);
    }
    public static void removeNodeLawTrackingStatusFromPublishingUI(String contentUuid, String contentSet, Connection connection)
    {
        try
        {
            PreparedStatement removeNodeLawTrackingFromPublishingUIStatement = connection.prepareStatement(PublishingDatabaseConstants.REMOVE_NODE_LAW_TRACKING_STATUS_FROM_PUBLISHING_UI);
            removeNodeLawTrackingFromPublishingUIStatement.setString(1, contentUuid);
            removeNodeLawTrackingFromPublishingUIStatement.setInt(2, Integer.parseInt(contentSet));
            removeNodeLawTrackingFromPublishingUIStatement.setString(3, contentUuid);
            removeNodeLawTrackingFromPublishingUIStatement.setInt(4, Integer.parseInt(contentSet));

            int recordsChanged = removeNodeLawTrackingFromPublishingUIStatement.executeUpdate();
            commit(connection);
            removeNodeLawTrackingFromPublishingUIStatement.close();
            Assertions.assertTrue(recordsChanged >= 2, "No records were changed from that query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with removing the node status with the parameters: ContentUUID = %s and contentSet = %d", contentUuid, contentSet));
        }
    }

    public static void updateNodesLoadedToWestlawDate(String date, String contentUuid, String contentSet, Connection connection)
    {
        java.util.Date dateObj = DateAndTimeUtils.convertStringToDateObject(date, DateAndTimeUtils.MMddyyyy_DATE_FORMAT);
        long time = dateObj.getTime();
        Timestamp ts = new Timestamp(time);
        try
        {
            PreparedStatement updateNodesLoadedToWestlawDateStatement = connection.prepareStatement(PublishingDatabaseConstants.UPDATE_LOADED_TO_WESTLAW_DATE_ON_PUBLISHING_UI);
            updateNodesLoadedToWestlawDateStatement.setTimestamp(1, ts);
            updateNodesLoadedToWestlawDateStatement.setString(2, contentUuid);
            updateNodesLoadedToWestlawDateStatement.setInt(3, Integer.parseInt(contentSet));
            int recordsChanged = updateNodesLoadedToWestlawDateStatement.executeUpdate();
            commit(connection);
            updateNodesLoadedToWestlawDateStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from the update nodes loaded to westlaw date query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database with updating to this nodes Loaded to Westlaw Date"));
        }
    }

    //method only works for content sets with the correct property name value in the database
    //method that works for content sets that do not have the property name is coming soon...
    public static boolean checkAndSetContentSetToBeEnabledForPublishing(String contentSet, Connection connection)
    {
        boolean contentSetIsEnabledBoolean = false;
        try
        {
            PreparedStatement isContentSetEnabledForPublishing = connection.prepareStatement(PublishingDatabaseConstants.IS_CONTENT_SET_ENABLED_FOR_PUBLISHING);
            isContentSetEnabledForPublishing.setInt(1, Integer.parseInt(contentSet));
            ResultSet result = isContentSetEnabledForPublishing.executeQuery();
            while (result.next())
            {
                String contentSetIsEnabledString = result.getString("PROPERTY_VALUE");
                contentSetIsEnabledBoolean = Boolean.valueOf(contentSetIsEnabledString);
                if (!contentSetIsEnabledBoolean)
                {
                    logger.information(String.format("Updating contentSet = %s to be enabled for Publishing...", contentSet));
                    PreparedStatement updateContentSetToBeEnabledForPublishingStatement = connection.prepareStatement(PublishingDatabaseConstants.UPDATE_CONTENT_SET_TO_BE_ENABLED_FOR_PUBLISHING);
                    updateContentSetToBeEnabledForPublishingStatement.setInt(1, Integer.parseInt(contentSet));
                    int recordsChanged = updateContentSetToBeEnabledForPublishingStatement.executeUpdate();
                    commit(connection);
                    result.close();
                    isContentSetEnabledForPublishing.close();
                    contentSetIsEnabledBoolean = recordsChanged >= 1;
                    Assertions.assertTrue(contentSetIsEnabledBoolean, "No records were changed from the update content set to be enabled for publishing query. " +
                            "You can manually enable a content set for publishing by going to the following link: http://uat.magellan3.int.westgroup.com:9177/targetBatchUiWeb/appPages/PublishingUI/contentSetConfiguration.jsf");
                }

                else
                {
                    logger.information(String.format("Publishing is already enabled for contentSet = %s", contentSet));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database when checking if the content set is enabled for publishing"));
        }

        return contentSetIsEnabledBoolean;
    }

    //method only works for content sets with the correct property name value in the database
    //method that works for content sets that do not have the property name is coming soon...
    public static boolean checkAndSetContentSetToBeDisabledForPublishing(String contentSet, Connection connection)
    {
        boolean contentSetIsEnabledBoolean = true;
        try
        {
            PreparedStatement isContentSetEnabledForPublishing = connection.prepareStatement(PublishingDatabaseConstants.IS_CONTENT_SET_ENABLED_FOR_PUBLISHING);
            isContentSetEnabledForPublishing.setInt(1, Integer.parseInt(contentSet));
            ResultSet result = isContentSetEnabledForPublishing.executeQuery();
            while (result.next())
            {
                String contentSetIsEnabledString = result.getString("PROPERTY_VALUE");
                contentSetIsEnabledBoolean = Boolean.valueOf(contentSetIsEnabledString);
                if (contentSetIsEnabledBoolean)
                {
                    logger.information(String.format("Updating contentSet = %s to be disabled for Publishing...", contentSet));
                    PreparedStatement updateContentSetToBeDisabledForPublishingStatement = connection.prepareStatement(PublishingDatabaseConstants.UPDATE_CONTENT_SET_TO_BE_DISABLED_FOR_PUBLISHING);
                    updateContentSetToBeDisabledForPublishingStatement.setInt(1, Integer.parseInt(contentSet));
                    int recordsChanged = updateContentSetToBeDisabledForPublishingStatement.executeUpdate();
                    commit(connection);
                    result.close();
                    isContentSetEnabledForPublishing.close();
                    contentSetIsEnabledBoolean = recordsChanged >= 1;
                    Assertions.assertFalse(!contentSetIsEnabledBoolean, "No records were changed from the update content set to be enabled for publishing query. " +
                            "You can manually enable a content set for publishing by going to the following link: http://uat.magellan3.int.westgroup.com:9177/targetBatchUiWeb/appPages/PublishingUI/contentSetConfiguration.jsf");
                }

                else
                {
                    logger.information(String.format("Publishing is already disabled for contentSet = %s", contentSet));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database when checking if the content set is enabled for publishing"));
        }

        return !contentSetIsEnabledBoolean;
    }

    //use this temporary method as a check for content sets who are expected to have publishing disabled. Simply Assert False.
    public static boolean isContentSetEnabledForPublishing(String contentSet, Connection connection)
    {
        boolean contentSetIsEnabledBoolean = false;
        try
        {
            PreparedStatement isContentSetEnabledForPublishing = connection.prepareStatement(PublishingDatabaseConstants.IS_CONTENT_SET_ENABLED_FOR_PUBLISHING);
            isContentSetEnabledForPublishing.setInt(1, Integer.parseInt(contentSet));
            ResultSet result = isContentSetEnabledForPublishing.executeQuery();
            while (result.next())
            {
                String contentSetIsEnabledString = result.getString("PROPERTY_VALUE");
                contentSetIsEnabledBoolean = Boolean.valueOf(contentSetIsEnabledString);
            }
            isContentSetEnabledForPublishing.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database when checking if the content set is enabled for publishing"));
        }
        return contentSetIsEnabledBoolean;
    }

    public static void checkAndAddPublishApproverForContentSet(String userId, String userFirstName, String userLastName, String contentSet, Connection connection)
    {
        String listOfPublishApproversXML = "";
        userId = "u" + userId;
        try
        {
            PreparedStatement getListOfPublishApproversForContentSetQuery = connection.prepareStatement(PublishingDatabaseConstants.GET_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY);
            getListOfPublishApproversForContentSetQuery.setInt(1, Integer.parseInt(contentSet));
            ResultSet result = getListOfPublishApproversForContentSetQuery.executeQuery();
            while (result.next())
            {
                listOfPublishApproversXML = result.getString("PROPERTY_VALUE");
            }
            getListOfPublishApproversForContentSetQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with getting the list of publish approvers using the data: contentSetId = %s", contentSet));
        }

        logger.information(String.format("Checking if userId = %s is in the list of publish approvers for contentSet = %s", userId, contentSet));
        if(!(listOfPublishApproversXML.contains(userId) || listOfPublishApproversXML.contains(userId.toUpperCase())))
        {
            logger.information(String.format("User with userId = %s is not in the list of publish approvers. Adding this user now for contentSet = %s", userId, contentSet));
            String existingPublishApproversWithoutApprovalsClosingTag = listOfPublishApproversXML.substring(0, listOfPublishApproversXML.indexOf(PublishingDatabaseConstants.APPROVALS_CLOSING_TAG));
            String newUser = String.format(PublishingDatabaseConstants.PUBLISH_APPROVER_SKELETON, userFirstName + " " + userLastName, userId);
            String newPublishApproversListWithAddedUser = existingPublishApproversWithoutApprovalsClosingTag + newUser + PublishingDatabaseConstants.APPROVALS_CLOSING_TAG;
            try
            {
                PreparedStatement setPublishingNodeReadyStatement =  connection.prepareStatement(PublishingDatabaseConstants.UPDATE_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY);
                setPublishingNodeReadyStatement.setString(1, newPublishApproversListWithAddedUser);
                setPublishingNodeReadyStatement.setInt(2, Integer.parseInt(contentSet));
                int recordsChanged = setPublishingNodeReadyStatement.executeUpdate();
                commit(connection);
                setPublishingNodeReadyStatement.close();
                Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Assertions.fail(String.format("There was an error with updating the list of publish approvers for contentSet = %s", contentSet));
            }
        }
        else
        {
            logger.information(String.format("User with userId = %s is already in the list of publish approvers for contentSet = %s. Continuing on with test.", userId, contentSet));
        }
    }

    public static void checkAndRemovePublishApproverForContentSet(String userId, String userFirstName, String userLastName, String contentSet, Connection connection)
    {
        String listOfPublishApproversXML = "";
        userId = "u" + userId;
        try
        {
            PreparedStatement getListOfPublishApproversForContentSetQuery = connection.prepareStatement(PublishingDatabaseConstants.GET_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY);
            getListOfPublishApproversForContentSetQuery.setInt(1, Integer.parseInt(contentSet));
            ResultSet result = getListOfPublishApproversForContentSetQuery.executeQuery();
            while (result.next())
            {
                listOfPublishApproversXML = result.getString("PROPERTY_VALUE");
            }
            result.close();
            getListOfPublishApproversForContentSetQuery.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("There was an error with getting the list of publish approvers using the data: contentSetId = %s", contentSet));
        }

        logger.information(String.format("Checking if userId = %s is in the list of publish approvers for contentSet = %s", userId, contentSet));
        if(listOfPublishApproversXML.contains(userId) || listOfPublishApproversXML.contains(userId.toUpperCase()))
        {
            logger.information(String.format("User with userId = %s is in the list of publish approvers. Removing this user now for contentSet = %s", userId, contentSet));
            String newPublishApproversListWithoutUser = listOfPublishApproversXML.replace(String.format(PublishingDatabaseConstants.PUBLISH_APPROVER_SKELETON, userFirstName + " " + userLastName, userId), "");
            try
            {
                PreparedStatement setPublishingNodeReadyStatement =  connection.prepareStatement(PublishingDatabaseConstants.UPDATE_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY);
                setPublishingNodeReadyStatement.setString(1, newPublishApproversListWithoutUser);
                setPublishingNodeReadyStatement.setInt(2, Integer.parseInt(contentSet));
                int recordsChanged = setPublishingNodeReadyStatement.executeUpdate();
                commit(connection);
                setPublishingNodeReadyStatement.close();
                Assertions.assertTrue(recordsChanged >= 1, "No records were changed from that query");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Assertions.fail(String.format("There was an error with updating the list of publish approvers for contentSet = %s", contentSet));
            }
        }

        else
        {
            logger.information(String.format("User with userId = %s is not in the list of publish approvers for contentSet = %s. Continuing on with test.", userId, contentSet));
        }
    }

    private static String getColumnInformationForTocContentPublishingStatus(String contentUuid, String contentSet, String columnLabel, Connection connect)
    {
        logger.information(format("Selecting a node from the database with these parameters: contentUuid = %s,contentSetID = %s", contentUuid, contentSet));
        String columnLabelInformationString = "";
        try
        {
            PreparedStatement selectPublishingNodeStatement = connect.prepareStatement(PublishingDatabaseConstants.SELECT_PUBLISHING_NODE_QUERY);
            selectPublishingNodeStatement.setString(1, contentUuid);
            selectPublishingNodeStatement.setInt(2, Integer.parseInt(contentSet));

            ResultSet result = selectPublishingNodeStatement.executeQuery();

            while (result.next())
            {
                columnLabelInformationString = result.getString(columnLabel);
            }
            selectPublishingNodeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(format("There was an error with selecting a node using the data: contentUUID = %s", contentUuid));
        }

        return columnLabelInformationString;

    }

    public static String getCitationPrefixFromPubLegacyNormTable(int position, String ContentSetname, Connection connect)
    {
       return getColumnInformationWithoutCitationPrefixFromPubLegacyNormTable(position, ContentSetname, "CITATION_PREFIX", connect);
    }

    public static String getCondensedPrefixFromPubLegacyNormTable(int position, String ContentSetname, Connection connect)
    {
        return getColumnInformationWithoutCitationPrefixFromPubLegacyNormTable(position, ContentSetname, "CONDENSED_PREFIX", connect);
    }

    private static String getColumnInformationWithoutCitationPrefixFromPubLegacyNormTable(int position, String ContentSetname, String ColumnName, Connection connect)
    {
        logger.information(format("Getting %s from Pub Legacy Norm Table with these parameters: position = %s,ContentSet name = %s",ColumnName, position, ContentSetname));
        String prefix = "";
        try
        {
            PreparedStatement selectPublishingNodeStatement = connect.prepareStatement(PublishingDatabaseConstants.SELECT_FROM_PUB_LEGACY_NORM_TBL_WITHOUT_CITATION_PREFIX);
            selectPublishingNodeStatement.setString(1, ContentSetname);
            selectPublishingNodeStatement.setInt(2, position);

            ResultSet result = selectPublishingNodeStatement.executeQuery();

            while (result.next())
            {
                prefix = result.getString(ColumnName);
            }
            result.close();
            selectPublishingNodeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(format("There was an error with selecting a node using the data: position = %s,ContentSet name = %s", position, ContentSetname));
        }
        return prefix;
    }

    public static void setPubNavigatePublishingHold(String contentUuid, String contentSet, Connection connection)
    {
        try
        {
            PreparedStatement setPubNavigatePublishingHoldStatement = connection.prepareStatement(PublishingDatabaseConstants.SET_PUB_NODE_TO_HOLD_STATUS);
            setPubNavigatePublishingHoldStatement.setString(1, contentUuid);
            setPubNavigatePublishingHoldStatement.setInt(2, Integer.parseInt(contentSet));
            int recordsChanged = setPubNavigatePublishingHoldStatement.executeUpdate();
            commit(connection);
            setPubNavigatePublishingHoldStatement.close();
            Assertions.assertTrue(recordsChanged >= 1, "No records were changed from the update nodes loaded to westlaw date query");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database with updating to this node to hold status"));
        }
    }

    public static void setNodeToPublishingERRORStatus(String contentUuid, String contentSet, Connection connection)
    {
        PreparedStatement setNodeToPublishingERRORStatusStatement = null;
        try
        {
            setNodeToPublishingERRORStatusStatement = connection.prepareStatement(PublishingDatabaseConstants.SET_NODE_TO_ERROR_PUBLISHING_STATUS);
            setNodeToPublishingERRORStatusStatement.setString(1, contentUuid);
            setNodeToPublishingERRORStatusStatement.setInt(2, Integer.parseInt(contentSet));
            setNodeToPublishingERRORStatusStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error with removing the node status with the parameters: ContentUUID = %s and contentSet = %s", contentUuid, contentSet));
        }
        finally
        {
            try
            {
                if(setNodeToPublishingERRORStatusStatement != null)
                {
                    setNodeToPublishingERRORStatusStatement.close();
                }
            }
            catch(SQLException e)
            {
                Assertions.fail("Failed closing prepared statement");
            }
        }
    }
}