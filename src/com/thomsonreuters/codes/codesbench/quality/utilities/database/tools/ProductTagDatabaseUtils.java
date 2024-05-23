package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTagDatabaseUtils extends BaseDatabaseUtils
{

    private static List<String> statementStrings = new ArrayList<>();

    // Don't heck up the order or you will crash the database
    static
    {
        statementStrings.add(ProductTagDatabaseConstants.DELETE_PUB_TOC_NODE_SCRIPT_ASSIGNMENT);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_TOC_NODE_SCRIPT_ASSIGNMENT);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_QUERY_NOTE);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_ATTRIBUTES);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_COMMAND);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_DEPENDENCY_DEPENDANT);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_DEPENDENCY);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_VOLUME_STATUS);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT_VALID_CONTENT_SET);
        statementStrings.add(ProductTagDatabaseConstants.DELETE_SCRIPT);
    }

    public static void deleteProductTag(Connection connection, Integer productID, String longName, String shortName, String createUser, String productTypeName, String pubTag)
    {

        logger.information(String.format("Deleting Product Tag %s made by %s", productID, createUser));
        // Don't heck up the order try blocks or you will crash the database
        for (String statement : statementStrings)
        {
            try
            {
                PreparedStatement deleteProductTagStatementFromPubTocNode = connection.prepareStatement(statement);
                deleteProductTagStatementFromPubTocNode.setInt(1, productID);
                deleteProductTagStatementFromPubTocNode.setString(2, longName);
                deleteProductTagStatementFromPubTocNode.setString(3, shortName);
                deleteProductTagStatementFromPubTocNode.setString(4, createUser);
                deleteProductTagStatementFromPubTocNode.setString(5, productTypeName);

                deleteProductTagStatementFromPubTocNode.executeUpdate();
                deleteProductTagStatementFromPubTocNode.close();

            } catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting Product Tag %s made by %s, failed: %s", productID, createUser, e.toString()));
            }
        }

        try
        {
            PreparedStatement deleteProductTagStatementFromPubTocNode = connection.prepareStatement(ProductTagDatabaseConstants.DELETE_FROM_SCRIPT_PUBTAG_STATEMENT_STRING);
            deleteProductTagStatementFromPubTocNode.setInt(1, productID);
            deleteProductTagStatementFromPubTocNode.setString(2, pubTag);

            deleteProductTagStatementFromPubTocNode.executeUpdate();
            deleteProductTagStatementFromPubTocNode.close();

        } catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting Product Tag %s made by %s, failed: %s", productID, createUser, e.toString()));
        }
        commit(connection);
    }

    public static void addProductTagToNode(Connection connection, String productTagID, String nodeUUID, String contentSetID)
    {
        logger.information(String.format("Inserting product tag id %s to node %s with a content set ID of %s", productTagID, nodeUUID, contentSetID));
        try
        {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(ProductTagDatabaseConstants.ADD_PRODUCT_TAG_TO_NODE);
            insertPreparedStatement.setString(1, nodeUUID);
            insertPreparedStatement.setString(2, productTagID);
            insertPreparedStatement.setString(3, contentSetID);

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            commit(connection);
        }
        catch(SQLException e)
        {
            Assertions.fail(String.format("Inserting product tag %s to node %s failed: %s", productTagID, nodeUUID, e));
        }
    }

    public static void removeProductTagFromNode(Connection connection, String productTagID, String nodeUUID, String contentSetID)
    {
        logger.information(String.format("Removing product tag id %s from node %s with a content set ID of %s", productTagID, nodeUUID, contentSetID));
        try
        {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(ProductTagDatabaseConstants.DELETE_PRODUCT_TAG_FROM_NODE);
            insertPreparedStatement.setString(1, nodeUUID);
            insertPreparedStatement.setString(2, productTagID);
            insertPreparedStatement.setString(3, contentSetID);

            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            commit(connection);
        }
        catch(SQLException e)
        {
            Assertions.fail(String.format("Removing product tag %s from node %s failed: %s", productTagID, nodeUUID, e));
        }
    }
}
