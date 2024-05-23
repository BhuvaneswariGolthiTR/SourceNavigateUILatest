package com.thomsonreuters.codes.codesbench.quality.utilities.database.nodangular;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NodAngularDatabaseUtils extends BaseDatabaseUtils
{
    /**
     * Getter Methods
     */
    public static String getOpinionUuid(String opinionNumber, String contentSet, Connection connection)
    {
        logger.information(String.format("Getting opinionUuid from NOD_ADMIN_OPINION table with opinion_number = %s", opinionNumber));
        String opinionUuid = "";
        try
        {
            PreparedStatement insertAdminOpinionStatement = connection.prepareStatement(NodAngularDatabaseConstants.GET_OPINION_UUID_FROM_NODE_ADMIN_OPINION_TABLE);
            insertAdminOpinionStatement.setString(1, opinionNumber);
            insertAdminOpinionStatement.setInt(2, Integer.parseInt(contentSet));
            ResultSet opinionUuidResult = insertAdminOpinionStatement.executeQuery();
            opinionUuidResult.next();
            opinionUuid = opinionUuidResult.getString("OPINION_UUID");
            opinionUuidResult.close();
            insertAdminOpinionStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to get the opinionUuid from NOD_ADMIN_OPINION table"));
        }
        return opinionUuid;
    }

    public static String getCaseUuid(String caseSerial, Connection connection)
    {
        logger.information(String.format("Getting caseUuid from NOD_CASE table with caseSerial = %s", caseSerial));
        String caseUuid = "";
        try
        {
            PreparedStatement getCaseUuidStatement = connection.prepareStatement(NodAngularDatabaseConstants.GET_CASE_UUID_FROM_NOD_CASE_TABLE);
            getCaseUuidStatement.setString(1, caseSerial);
            ResultSet caseUuidResult = getCaseUuidStatement.executeQuery();
            caseUuidResult.next();
            caseUuid = caseUuidResult.getString("case_uuid");
            caseUuidResult.close();
            getCaseUuidStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to get the caseUuid from NOD_CASE table with caseSerial = %s", caseSerial));
        }
        return caseUuid;
    }

    public static String getSubscribedCaseUuid(String caseUuid, String contentSet, Connection connection)
    {
        logger.information(String.format("Getting subscribedCaseUuid from NOD_SUBSCRIBED_CASE table with caseUuid = %s and contentSet = %s", caseUuid, contentSet));
        String subscribedCaseUuid = "";
        try
        {
            PreparedStatement getSubscribedCaseUuidStatement = connection.prepareStatement(NodAngularDatabaseConstants.GET_SUBSCRIBED_CASE_UUID_FROM_NOD_SUBSCRIBED_CASE_TABLE);
            getSubscribedCaseUuidStatement.setString(1, caseUuid);
            getSubscribedCaseUuidStatement.setInt(2, Integer.parseInt(contentSet));
            ResultSet subscribedCaseUuidResult = getSubscribedCaseUuidStatement.executeQuery();
            subscribedCaseUuidResult.next();
            subscribedCaseUuid = subscribedCaseUuidResult.getString("subscribed_case_uuid");
            subscribedCaseUuidResult.close();
            getSubscribedCaseUuidStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to get the subscribedCaseUuid from NOD__SUBSCRIBED_CASE table with caseUuid = %s and contentSet = %s", caseUuid, contentSet));
        }
        return subscribedCaseUuid;
    }

    public static String getHeadnoteUuid(String caseUuid, int headnoteNumber, Connection connection)
    {
        logger.information(String.format("Getting headnoteUuid from NOD_HEADNOTE table with caseUuid = %s and headnoteNumber = %d", caseUuid, headnoteNumber));
        String headnoteUuid = "";
        try
        {
            PreparedStatement getHeadnoteUuidStatement = connection.prepareStatement(NodAngularDatabaseConstants.GET_HEADNOTE_UUID_FROM_NOD_HEADNOTE_TABLE);
            getHeadnoteUuidStatement.setString(1, caseUuid);
            getHeadnoteUuidStatement.setInt(2, headnoteNumber);
            ResultSet headnoteUuidResult = getHeadnoteUuidStatement.executeQuery();
            headnoteUuidResult.next();
            headnoteUuid = headnoteUuidResult.getString("headnote_uuid");
            headnoteUuidResult.close();
            getHeadnoteUuidStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to get the headnoteUuid from NOD_HEADNOTE table with caseUuid = %s and headnoteNumber = %d", caseUuid, headnoteNumber));
        }
        return headnoteUuid;
    }

    public static int getMaxHeadnoteNumber(String caseUuid, Connection connection)
    {
        logger.information(String.format("Getting max headnote number from NOD_HEADNOTE table with caseUuid = %s", caseUuid));
        int headnoteNumber = -1;
        try
        {
            PreparedStatement getHeadnoteNumberStatement = connection.prepareStatement(NodAngularDatabaseConstants.GET_MAX_HEADNOTE_NUMBER);
            getHeadnoteNumberStatement.setString(1, caseUuid);
            ResultSet headnoteNumberResult = getHeadnoteNumberStatement.executeQuery();
            while(headnoteNumberResult.next())
            {
                headnoteNumber = headnoteNumberResult.getInt("headnote_number");
            }
            headnoteNumberResult.close();
            getHeadnoteNumberStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to get the headnoteNumber from NOD_HEADNOTE table with caseUuid = %s, caseUuid"));
        }
        return headnoteNumber;
    }

    /**
     *  Setter Methods
     */
    public static void setCcdbNumber(String caseUuid, String ccdb, Connection connection)
    {
        logger.information(String.format("Setting Ccdb Number for caseUuid = %s", caseUuid));
        try
        {
            PreparedStatement setCcdbStatement =  connection.prepareStatement(NodAngularDatabaseConstants.SET_CCDB_NUMBER_IN_NOD_CASE_TABLE);
            setCcdbStatement.setInt(1, Integer.parseInt(ccdb));
            setCcdbStatement.setString(2, caseUuid);
            setCcdbStatement.executeUpdate();
            commit(connection);
            setCcdbStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("There was an error setting CCDB for caseUuid = %s", caseUuid));
        }
    }

    /**
     * Insert Methods
     */
    public static void insertAdministrativeOpinion(String opinionUuid, String category, String text, String opinionNumber, String contentSet, Connection connection)
    {
        logger.information(String.format("Inserting opinion into NOD_ADMIN_OPINION table with opinionUuid = %s", opinionUuid));
        try
        {
            PreparedStatement insertAdminOpinionStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_ADMIN_OPINION_INTO_NOD_ADMIN_OPINION_TABLE);
            insertAdminOpinionStatement.setString(1, opinionUuid);
            insertAdminOpinionStatement.setString(2, category);
            insertAdminOpinionStatement.setString(3, text);
            insertAdminOpinionStatement.setString(4, opinionNumber);
            insertAdminOpinionStatement.setInt(5, Integer.parseInt(contentSet));
            insertAdminOpinionStatement.executeQuery();
            commit(connection);
            insertAdminOpinionStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new administrative opinion in NOD_ADMIN_OPINION table"));
        }
    }

    public static void insertCase(String title, String serialNumber, String courtUuid, Connection connection)
    {
        String caseUuid = CommonDataMocking.generateUUID();
        logger.information(String.format("Inserting case into NOD_CASE table with caseUuid = %s", caseUuid));
        try
        {
            PreparedStatement insertCaseStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_CASE_INTO_NOD_CASE_TABLE);
            insertCaseStatement.setString(1, caseUuid);
            insertCaseStatement.setString(2, title);
            insertCaseStatement.setString(3, serialNumber);
            insertCaseStatement.setString(4, courtUuid);
            insertCaseStatement.executeQuery();
            insertCaseStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new case in NOD_CASE table with caseUuid = %s", caseUuid));
        }
    }

    public static void subscribeToCase(String caseUuid, String contentSet, Connection connection)
    {
        String subscribedCaseUuid = CommonDataMocking.generateUUID();
        logger.information(String.format("Inserting subscribe case to NOD_SUBSCRIBED_CASE table with caseUuid = %s", caseUuid));
        try
        {
            //Subscribe to given case
            PreparedStatement insertSubscribedCaseStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_SUBSCRIBED_CASE_INTO_NOD_SUBSCRIBED_CASE_TABLE);
            insertSubscribedCaseStatement.setString(1, subscribedCaseUuid);
            insertSubscribedCaseStatement.setString(2, caseUuid);
            insertSubscribedCaseStatement.setInt(3, Integer.parseInt(contentSet));
            insertSubscribedCaseStatement.setInt(4, Integer.parseInt(contentSet));
            insertSubscribedCaseStatement.executeQuery();
            commit(connection);
            insertSubscribedCaseStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new subscribed case in NOD_SUBSCRIBED_CASE table with caseUuid = %s", caseUuid));
        }
    }

    public static void insertHeadnote(String caseUuid, String headnoteText, Connection connection)
    {
        String headnoteUuid = CommonDataMocking.generateUUID();
        int headnoteNumber = getMaxHeadnoteNumber(caseUuid, connection) + 1;
        logger.information(String.format("Inserting headnote into NOD_HEADNOTE table and adding a topicKey to NOD_TOPIC_KEY table with headnoteUuid = %s", headnoteUuid));
        try
        {
            //Add headnote
            PreparedStatement insertHeadnoteStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_HEADNOTE_INTO_NOD_HEADNOTE_TABLE);
            insertHeadnoteStatement.setString(1, headnoteUuid);
            insertHeadnoteStatement.setString(2, caseUuid);
            insertHeadnoteStatement.setInt(3, headnoteNumber);
            insertHeadnoteStatement.setString(4, headnoteText);
            insertHeadnoteStatement.executeQuery();
            insertHeadnoteStatement.close();
            commit(connection);
            //Add topic key assignment to headnote
            insertHeadnoteTopicKey(headnoteUuid, 9999, "Test topic", 9999, connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new headnote in NOD_HEADNOTE and NOD_TOPIC_KEY tables with headnoteUuid = %s", headnoteUuid));
        }
    }

    public static void subscribeToHeadnote(String subscribedCaseUuid, String headnoteUuid, Connection connection)
    {
        String subscribedHeadnoteUuid = CommonDataMocking.generateUUID();
        logger.information(String.format("Inserting subscribe headnote to NOD_SUBSCRIBED_HEADNOTE table with headnoteUuid = %s", headnoteUuid));
        try
        {
            //Subscribe to given headnote
            PreparedStatement insertSubscribedHeadnoteStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_SUBSCRIBED_HEADNOTE_INTO_NOD_SUBSCRIBED_HEADNOTE_TABLE);
            insertSubscribedHeadnoteStatement.setString(1, subscribedHeadnoteUuid);
            insertSubscribedHeadnoteStatement.setString(2, subscribedCaseUuid);
            insertSubscribedHeadnoteStatement.setString(3, headnoteUuid);
            insertSubscribedHeadnoteStatement.executeQuery();
            insertSubscribedHeadnoteStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert headnote into NOD_SUBSCRIBED_HEADNOTE tables with headnoteUuid = %s", headnoteUuid));
        }
    }

    public static void insertMultipleHeadnotesAndSubscribe(int numberOfHeadnotes, String caseUuid, String subscribedCaseUuid, String headnoteText, Connection connection)
    {
        int headnoteNum = getMaxHeadnoteNumber(caseUuid, connection) + 1;
        for (int i = headnoteNum; i < (numberOfHeadnotes + headnoteNum); i++)
        {
            insertHeadnote(caseUuid, headnoteText, connection);
            String headnoteUuid = getHeadnoteUuid(caseUuid, i, connection);
            subscribeToHeadnote(subscribedCaseUuid, headnoteUuid, connection);
        }
    }

    public static void insertHeadnoteTopicKey(String headnoteUuid, int topicNumber, String topicName, int keyNumber, Connection connection)
    {
        String topicKeyUuid = CommonDataMocking.generateUUID();
        logger.information(String.format("Inserting topicKey into NOD_TOPIC_KEY headnoteUuid = %s", headnoteUuid));
        try
        {
            PreparedStatement insertHeadnoteTopicKeyStatement = connection.prepareStatement(NodAngularDatabaseConstants.INSERT_HEADNOTE_TOPIC_KEY_INTO_NOD_TOPIC_KEY_TABLE);
            insertHeadnoteTopicKeyStatement.setString(1, topicKeyUuid);
            insertHeadnoteTopicKeyStatement.setString(2, headnoteUuid);
            insertHeadnoteTopicKeyStatement.setInt(3, topicNumber);
            insertHeadnoteTopicKeyStatement.setString(4, topicName);
            insertHeadnoteTopicKeyStatement.setInt(5, keyNumber);
            insertHeadnoteTopicKeyStatement.executeQuery();
            insertHeadnoteTopicKeyStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to insert a new topicKey in NOD_TOPIC_KEY with headnoteUuid = %s", headnoteUuid));
        }
    }

    /**
     * Deletion methods
     */
    public static void deleteAdministrativeOpinion(String opinionUuid, String contentSet, Connection connection)
    {
        logger.information(String.format("Deleting opinion from NOD_ADMIN_OPINION table with opinionUuid = %s", opinionUuid));
        try
        {
            PreparedStatement deleteAdminOpinionStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_ADMIN_OPINION_FROM_NOD_ADMIN_OPINION_TABLE);
            deleteAdminOpinionStatement.setString(1, opinionUuid);
            deleteAdminOpinionStatement.setInt(2, Integer.parseInt(contentSet));
            deleteAdminOpinionStatement.executeQuery();
            commit(connection);
            deleteAdminOpinionStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to delete an administrative opinion from NOD_ADMIN_OPINION table"));
        }
    }

    public static void deleteCase(String caseUuid, String contentSet, Connection connection)
    {
        logger.information(String.format("Deleting case from NOD_CASE and subscribe case from NOD_SUBSCRIBED_CASE table with caseUuid = %s", caseUuid));
        try
        {
            String subscribedCaseUuid = getSubscribedCaseUuid(caseUuid, contentSet, connection);
            if (subscribedCaseUuid != null)
            {
                //NOTE: They need to be in this order, delete subscribed first, then delete case
                //Delete headnotes
                deleteAllHeadnotes(caseUuid, subscribedCaseUuid, connection );
                //Delete subscribed case
                PreparedStatement deleteSubscribedCaseStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_SUBSCRIBED_CASE_FROM_NOD_CASE_TABLE);
                deleteSubscribedCaseStatement.setString(1, caseUuid);
                deleteSubscribedCaseStatement.executeQuery();
                deleteSubscribedCaseStatement.close();
            }
            //Delete case
            PreparedStatement deleteCaseStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_CASE_FROM_NOD_CASE_TABLE);
            deleteCaseStatement.setString(1, caseUuid);
            deleteCaseStatement.executeQuery();
            deleteCaseStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to delete a case from NOD_CASE and NOD_SUBSCRIBED_CASE tables with caseUuid = %s", caseUuid));
        }
    }

    public static void deleteHeadnote(String headnoteUuid, Connection connection)
    {
        logger.information(String.format("Deleting headnote from NOD_HEADNOTE and subscribe headnote from NOD_SUBSCRIBED_HEADNOTE table with headnoteUuid = %s", headnoteUuid));
        try
        {
            //NOTE: They need to be in this order, delete subscribed and topicKey first, then delete headnote
            //Delete subscribed headnote
            PreparedStatement deleteTopicKeyStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_HEADNOTE_TOPIC_KEY_FROM_NOD_TOPIC_KEY_TABLE);
            deleteTopicKeyStatement.setString(1, headnoteUuid);
            deleteTopicKeyStatement.executeQuery();
            deleteTopicKeyStatement.close();
            //Delete topic key
            PreparedStatement deleteSubscribedHeadnoteStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_HEADNOTE_FROM_NOD_SUBSCRIBED_HEADNOTE_TABLE);
            deleteSubscribedHeadnoteStatement.setString(1, headnoteUuid);
            deleteSubscribedHeadnoteStatement.executeQuery();
            deleteSubscribedHeadnoteStatement.close();
            //Delete headnote
            PreparedStatement deleteHeadnoteStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_HEADNOTE_FROM_NOD_HEADNOTE_TABLE);
            deleteHeadnoteStatement.setString(1, headnoteUuid);
            deleteHeadnoteStatement.executeQuery();
            deleteHeadnoteStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to delete a headnote from NOD_HEADNOTE and NOD_SUBSCRIBED_HEADNOTE tables with headnoteUuid = %s", headnoteUuid));
        }
    }

    public static void deleteAllHeadnotes(String caseUuid, String subscribedCaseUuid, Connection connection)
    {
        logger.information(String.format("Deleting all headnotes from NOD_HEADNOTE and subscribe headnotes from NOD_SUBSCRIBED_HEADNOTE and topicKeys from NOD_TOPIC_KEY tables with caseUuid = %s", caseUuid));
        try
        {
            //NOTE: They need to be in this order, delete topicKey first, then delete subscribed, then delete headnote
            //Delete headnote topicKeys
            PreparedStatement deleteHeadnoteTopicKeyStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_ALL_HEADNOTE_TOPIC_KEY_FROM_NOD_TOPIC_KEY_TABLE);
            deleteHeadnoteTopicKeyStatement.setString(1, caseUuid);
            deleteHeadnoteTopicKeyStatement.executeQuery();
            deleteHeadnoteTopicKeyStatement.close();
            //Delete subscribed headnotes
            PreparedStatement deleteSubscribedHeadnoteStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_ALL_HEADNOTE_FROM_NOD_SUBSCRIBED_HEADNOTE_TABLE);
            deleteSubscribedHeadnoteStatement.setString(1, subscribedCaseUuid);
            deleteSubscribedHeadnoteStatement.executeQuery();
            deleteSubscribedHeadnoteStatement.close();
            commit(connection);
            //Delete headnote
            PreparedStatement deleteCaseStatement = connection.prepareStatement(NodAngularDatabaseConstants.DELETE_ALL_HEADNOTE_FROM_NOD_HEADNOTE_TABLE);
            deleteCaseStatement.setString(1, caseUuid);
            deleteCaseStatement.executeQuery();
            deleteCaseStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("An issue occurred in the database while trying to delete all headnote from NOD_HEADNOTE and NOD_SUBSCRIBED_HEADNOTE tables with caseUuid = %s", caseUuid));
        }
    }
}