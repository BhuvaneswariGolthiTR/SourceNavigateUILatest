package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.queries;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.HierarchyNode;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.NodeType;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestService.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public class TargetNodeMockingQueries
{

    private static final String DEFAULT_START_DATE = "2005-12-31";
    private static List<String> deleteNodeStatements = new ArrayList<>();

    private static List<String> deleteNodeFromDispDerivStatements = new ArrayList<>();

    private static final String VOLUME_NUM_STRING = TargetDataMockingNew.getVolumeNumStr();

    static
    {
        deleteNodeFromDispDerivStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DISP_DERIV_QUERY);
        deleteNodeFromDispDerivStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_DISP_DERIV_QUERY);
    }

    static
    {
        deleteNodeStatements.add(TargetDataMockingConstants.DELETE_FROM_TOC_NODE_LOCK);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_SCRIPT_ASSIGNMENT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DOC_FAMILY_LINK_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_DOC_FAMILY_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_LAST_UPLOAD_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_UPLOAD_HISTORY_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_PARENT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LINK_CHILD_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_RESEQUENCED_AUDIT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_AUDIT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_ALT_CITE_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_AUDIT_LATEST_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_CHECKED_OUT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_EXTENDED_METADATA_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_GLOSSARY_LINK_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_KEY_VALUE_METADATA_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_LAST_UPLOAD_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_SRC_DOC_LINK_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_UPLOAD_HISTORY_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_WIP_METADATA_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_CONTENT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_EXTENDED_METADATA_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_SCRIPT_ASSIGNMENT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_LINK_CHILD_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_LINK_PARENT_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_PUB_TOC_NODE_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_UPLOAD_CANDIDATE_QUERY);
        deleteNodeStatements.add(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_NODE_QUERY);
    }

    public void deleteNode(Connection connection, HierarchyNode node)
    {
        boolean hasContent = node.getContentUUID() != null;
        String nodeUUID = node.getNodeUUID();

        deletePubId(connection, nodeUUID);

        for (String statement : deleteNodeFromDispDerivStatements)
        {
            try
            {
                PreparedStatement deleteNodeFromDispDerivStatement = connection.prepareStatement(statement);
                deleteNodeFromDispDerivStatement.setString(1, nodeUUID);
                deleteNodeFromDispDerivStatement.setString(2, nodeUUID);
                deleteNodeFromDispDerivStatement.executeUpdate();
                deleteNodeFromDispDerivStatement.close();
            }
            catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting HierarchyNode with node UUID '%s' failed: %s", nodeUUID, e.toString()));
            }
        }

        for (String statement : deleteNodeStatements)
        {
            if (!hasContent && statement.equals(HierarchyDatabaseConstants.DELETE_NODE_FROM_TOC_CONTENT_QUERY))
            {
                continue;
            }
            try
            {
                PreparedStatement deleteNodeStatement = connection.prepareStatement(statement);
                deleteNodeStatement.setString(1, nodeUUID);
                deleteNodeStatement.executeUpdate();
                deleteNodeStatement.close();
            }
            catch (SQLException e)
            {
                logger.severe("A SQLException is being thrown. This is the statement that is throwing the exception: \"" + statement + "\"");
                logger.severe("? is node uuid, which is: '" + nodeUUID + "'");
                Assertions.fail(String.format("Deleting HierarchyNode with node UUID '%s' failed: %s", nodeUUID, e.toString()));
            }
        }
    }

    public String insertNodeIntoPubNav(Connection connection, HierarchyNode node)
    {
        String nodeUUID = node.getNodeUUID();
        String contentUUID = node.getContentUUID();
        NodeType nodeType = node.getType();
        Date startDate = HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeUUID, connection);
        Date endDate = HierarchyDatabaseUtils.getLegisEffectiveEndDate(nodeUUID, connection);
        int keywordId = TargetDataMockingNew.convertNodeTypeToKeywordId(nodeType);
        int dbNodeTypeId = HierarchyDatabaseUtils.getNodeType(nodeUUID, connection);
        int HID = HierarchyDatabaseUtils.getHIDWithNodeUUID(nodeUUID, connection);
        int contentSetId = TargetDataMockingNew.getCurrentContentSetId();
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, String.valueOf(contentSetId), connection);
        String headText = getHeadTextFromNodeUUID(connection, nodeUUID);

        try
        {
            logger.information("Inserting node into Pub Nav...");
            PreparedStatement insertNodeIntoPubNavStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_NODE_INTO_PUB_NAV);
            insertNodeIntoPubNavStatement.setString(1, nodeUUID);
            insertNodeIntoPubNavStatement.setInt(2, keywordId);
            insertNodeIntoPubNavStatement.setInt(3, dbNodeTypeId);
            insertNodeIntoPubNavStatement.setDate(4, startDate);
            insertNodeIntoPubNavStatement.setDate(5, endDate);
            insertNodeIntoPubNavStatement.setInt(6, HID);
            insertNodeIntoPubNavStatement.setInt(7, contentSetId);
            insertNodeIntoPubNavStatement.setString(8, contentUUID);
            insertNodeIntoPubNavStatement.setString(9, nodeValue);
            insertNodeIntoPubNavStatement.setString(10, headText);
            insertNodeIntoPubNavStatement.executeUpdate();
            insertNodeIntoPubNavStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail("Failed to insert node with uuid '" + nodeUUID + "' into pub nav: " + e.toString());
        }
        return contentUUID;
    }

    public String getHeadTextFromNodeUUID(Connection connection, String nodeUUID)
    {
        String headText = "";
        try
        {
            PreparedStatement getHeadTextStatement = connection.prepareStatement(TargetDataMockingConstants.GET_HEAD_TEXT_FROM_NODE_UUID);
            getHeadTextStatement.setString(1, nodeUUID);
            ResultSet result = getHeadTextStatement.executeQuery();
            if(result.next())
            {
                headText = result.getString("HEAD_TEXT");
            }
            result.close();
            getHeadTextStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to get head text of node with uuid '" + nodeUUID + "': " + e.toString());
        }
        return headText;
    }

    public String getValFromNodeUUID(Connection connection, String nodeUUID)
    {
        String val = "";
        try
        {
            PreparedStatement getValStatement = connection.prepareStatement(TargetDataMockingConstants.GET_VAL_FROM_NODE_UUID);
            getValStatement.setString(1, nodeUUID);
            ResultSet result = getValStatement.executeQuery();
            while (result.next())
            {
                val = result.getString("VAL");
            }
            result.close();
            getValStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to get VAL from node with uuid " + nodeUUID);
        }
        return val;
    }

    public List<String> getNodeUUIDsOfChildrenFromNodeUUID(Connection connection, String parentNodeUUID)
    {
        ArrayList<String> listOfUUIDs = new ArrayList<>();
        try
        {
            PreparedStatement getChildrenStatement = connection.prepareStatement(TargetDataMockingConstants.GET_CHILDREN_OF_NODE);
            getChildrenStatement.setString(1, parentNodeUUID);
            ResultSet result = getChildrenStatement.executeQuery();
            while (result.next())
            {
                listOfUUIDs.add(result.getString("CHILD_NODE_UUID"));
            }
            result.close();
            getChildrenStatement.close();
        }
        catch (SQLException e)
        {
            logger.severe("Getting UUIDs of children of node '" + parentNodeUUID + "' failed: " + e.toString());
        }
        return listOfUUIDs;
    }

    public String insertTocNodePART(Connection connection, int keywordId, int contentSetId, int HID, String headNum, String title)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            // node uuid, keyword id, val, content uuid, hid
            PreparedStatement insertPartStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_PART_INTO_TOC_NODE);
            insertPartStatement.setString(1, nodeUUID);
            insertPartStatement.setInt(2, keywordId);
            insertPartStatement.setInt(3, contentSetId);
            insertPartStatement.setString(4, headNum);
            insertPartStatement.setString(5, contentUUID);
            insertPartStatement.setInt(6, HID);
            insertPartStatement.setString(7, title);
            insertPartStatement.executeUpdate();
            insertPartStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeGRADE(Connection connection, int keywordId, int contentSetId, int HID, String title)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertGradeStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_GRADE_INTO_TOC_NODE);
            insertGradeStatement.setString(1, nodeUUID);
            insertGradeStatement.setInt(2, keywordId);
            insertGradeStatement.setInt(3, contentSetId);
            insertGradeStatement.setString(4, title);
            insertGradeStatement.setString(5, contentUUID);
            insertGradeStatement.setInt(6, HID);
            insertGradeStatement.executeUpdate();
            insertGradeStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeGLOSSARY(Connection connection, int keywordId, int contentSetId, int HID, String title)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertGradeStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_GLOSSARY_INTO_TOC_NODE);
            insertGradeStatement.setString(1, nodeUUID);
            insertGradeStatement.setInt(2, keywordId);
            insertGradeStatement.setInt(3, contentSetId);
            insertGradeStatement.setString(4, title);
            insertGradeStatement.setString(5, contentUUID);
            insertGradeStatement.setInt(6, HID);
            insertGradeStatement.executeUpdate();
            insertGradeStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeTITLE(Connection connection, int keywordId, int contentSetId, int HID, String headNum)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertTitleStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_TITLE_INTO_TOC_NODE);
            insertTitleStatement.setString(1, nodeUUID);
            insertTitleStatement.setInt(2, keywordId);
            insertTitleStatement.setInt(3, contentSetId);
            insertTitleStatement.setString(4, headNum);
            insertTitleStatement.setString(5, contentUUID);
            insertTitleStatement.setInt(6, HID);
            insertTitleStatement.executeUpdate();
            insertTitleStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return contentUUID;
    }

    public String insertTocNodeSUBTITLE(Connection connection, int keywordId, int contentSetId, int HID, String headNum)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertSubtitleStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_SUBTITLE_INTO_TOC_NODE);
            insertSubtitleStatement.setString(1, nodeUUID);
            insertSubtitleStatement.setInt(2, keywordId);
            insertSubtitleStatement.setInt(3, contentSetId);
            insertSubtitleStatement.setString(4, contentUUID);
            insertSubtitleStatement.setInt(5, HID);
            insertSubtitleStatement.setString(6, headNum);
            insertSubtitleStatement.executeUpdate();
            insertSubtitleStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeCHAPTER(Connection connection, int keywordId, int contentSetId, int HID, String headNum, String title)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();
        try
        {
            PreparedStatement insertChapterStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_CHAPTER_INTO_TOC_NODE);
            insertChapterStatement.setString(1, nodeUUID);
            insertChapterStatement.setInt(2, keywordId);
            insertChapterStatement.setInt(3, contentSetId);
            insertChapterStatement.setString(4, headNum);
            insertChapterStatement.setString(5, contentUUID);
            insertChapterStatement.setInt(6, HID);
            insertChapterStatement.setString(7, title);
            insertChapterStatement.executeUpdate();
            insertChapterStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));

        }
        return contentUUID;
    }

    public String insertTocNodeCHAPTERWithEndDate(Connection connection, int keywordId, int contentSetId, String headNum, String title,
            int orderNum, int HID, Date startDate, Date endDate)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertSectionStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_CHAPTER_INTO_TOC_NODE_WITH_END_DATE);
            insertSectionStatement.setString(1, nodeUUID);
            insertSectionStatement.setInt(2, keywordId);
            insertSectionStatement.setDate(3, startDate);
            insertSectionStatement.setDate(4, endDate);
            insertSectionStatement.setInt(5, contentSetId);
            insertSectionStatement.setString(6, headNum);
            insertSectionStatement.setString(7, contentUUID);
            insertSectionStatement.setString(8, title);
            insertSectionStatement.setInt(9, orderNum);
            insertSectionStatement.setInt(10, HID);
            insertSectionStatement.executeUpdate();
            insertSectionStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeRULE(Connection connection, int keywordId, int contentSetId, String headNum, String title, int orderNum, int HID)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertRuleStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_RULE_INTO_TOC_NODE);
            insertRuleStatement.setString(1, nodeUUID);
            insertRuleStatement.setInt(2, keywordId);
            insertRuleStatement.setInt(3, contentSetId);
            insertRuleStatement.setString(4, headNum);
            insertRuleStatement.setString(5, contentUUID);
            insertRuleStatement.setString(6, title);
            insertRuleStatement.setInt(7, orderNum);
            insertRuleStatement.setInt(8, HID);
            insertRuleStatement.executeUpdate();
            insertRuleStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return contentUUID;
    }

    public String insertTocNodeSECTION(Connection connection, int keywordId, int contentSetId, String headNum, String title,
            int orderNum, int HID, Date startDate, int nodeType) // 2 = section, 11 = glossary
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertSectionStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_SECTION_INTO_TOC_NODE);
            insertSectionStatement.setString(1, nodeUUID);
            insertSectionStatement.setInt(2, keywordId);
            insertSectionStatement.setInt(3, nodeType);
            insertSectionStatement.setDate(4, startDate);
            insertSectionStatement.setInt(5, contentSetId);
            insertSectionStatement.setString(6, headNum);
            insertSectionStatement.setString(7, contentUUID);
            insertSectionStatement.setString(8, title);
            insertSectionStatement.setInt(9, orderNum);
            insertSectionStatement.setInt(10, HID);
            insertSectionStatement.executeUpdate();
            insertSectionStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeSECTIONWithEndDate(Connection connection, int keywordId, int contentSetId, String headNum, String title,
            int orderNum, int HID, Date startDate, Date endDate, int nodeType) // 2 = section, 11 = glossary
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertSectionStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_SECTION_INTO_TOC_NODE_WITH_END_DATE);
            insertSectionStatement.setString(1, nodeUUID);
            insertSectionStatement.setInt(2, keywordId);
            insertSectionStatement.setInt(3, nodeType);
            insertSectionStatement.setDate(4, startDate);
            insertSectionStatement.setDate(5, endDate);
            insertSectionStatement.setInt(6, contentSetId);
            insertSectionStatement.setString(7, headNum);
            insertSectionStatement.setString(8, contentUUID);
            insertSectionStatement.setString(9, title);
            insertSectionStatement.setInt(10, orderNum);
            insertSectionStatement.setInt(11, HID);
            insertSectionStatement.executeUpdate();
            insertSectionStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeNODCONTAINER(Connection connection, int keywordId, int contentSetId, String title, int HID)
    {
        String nodeUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertNODContainerStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_NOD_CONTAINER_INTO_TOC_NODE);
            insertNODContainerStatement.setString(1, nodeUUID);
            insertNODContainerStatement.setInt(2, keywordId);
            insertNODContainerStatement.setInt(3, contentSetId);
            insertNODContainerStatement.setString(4, title);
            insertNODContainerStatement.setNull(5, Types.CHAR);
            insertNODContainerStatement.setInt(6, HID);
            insertNODContainerStatement.executeUpdate();
            insertNODContainerStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return nodeUUID;
    }

    public String insertTocNodeXND(Connection connection, int keywordId, int contentSetId, String title, int HID)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertXNDStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_XND_INTO_TOC_NODE);
            insertXNDStatement.setString(1, nodeUUID);
            insertXNDStatement.setInt(2, keywordId);
            insertXNDStatement.setInt(3, contentSetId);
            insertXNDStatement.setString(4, title);
            insertXNDStatement.setString(5, contentUUID);
            insertXNDStatement.setInt(6, HID);
            insertXNDStatement.executeUpdate();
            insertXNDStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeBLAnalysis(Connection connection, int keywordId, int contentSetId, String title, int HID)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertBLAnalysisStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_BL_ANALYSIS_INTO_TOC_NODE);
            insertBLAnalysisStatement.setString(1, nodeUUID);
            insertBLAnalysisStatement.setInt(2, keywordId);
            insertBLAnalysisStatement.setInt(3, contentSetId);
            insertBLAnalysisStatement.setString(4, title);
            insertBLAnalysisStatement.setString(5, contentUUID);
            insertBLAnalysisStatement.setInt(6, HID);
            insertBLAnalysisStatement.executeUpdate();
            insertBLAnalysisStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public String insertTocNodeBlueline(Connection connection, int keywordId, int contentSetId, String title, int HID, String headNum, int orderNum)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertBluelineStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_BLUELINE_INTO_TOC_NODE);
            insertBluelineStatement.setString(1, nodeUUID);
            insertBluelineStatement.setInt(2, keywordId);
            insertBluelineStatement.setInt(3, contentSetId);
            insertBluelineStatement.setString(4, headNum);
            insertBluelineStatement.setString(5, contentUUID);
            insertBluelineStatement.setInt(6, HID);
            insertBluelineStatement.setString(7, title);
            insertBluelineStatement.setInt(8, orderNum);
            insertBluelineStatement.executeUpdate();
            insertBluelineStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into TOC_NODE to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return contentUUID;
    }

    public int getMaxChildNodeOrder(Connection connection, String parentNodeUUID, int defaultNodeOrder)
    {
        int nodeOrder = defaultNodeOrder;
        try
        {
            PreparedStatement getNodeOrderStatement = connection.prepareStatement(TargetDataMockingConstants.GET_MAX_CHILD_NODE_ORDER);
            getNodeOrderStatement.setString(1, parentNodeUUID);
            ResultSet result = getNodeOrderStatement.executeQuery();
            if(result.next())
            {
                nodeOrder = result.getInt("MAX(NODE_ORDER)");
            }
            result.close();
            getNodeOrderStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to get node order of children of node with UUID " + parentNodeUUID + ": " + e.toString());
        }
        return nodeOrder;
    }

    public String insertTocNodeLink(Connection connection, String parentNodeUuid, String childNodeUuid, int orderNum)
    {
        String parentChildUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting into TOC_NODE_LINK...");
            PreparedStatement insertTocNodeLinkStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_TOC_NODE_LINK);
            insertTocNodeLinkStatement.setString(1, parentNodeUuid);
            insertTocNodeLinkStatement.setString(2, parentChildUuid);
            insertTocNodeLinkStatement.setString(3, childNodeUuid);
            insertTocNodeLinkStatement.setInt(4, orderNum);
            insertTocNodeLinkStatement.executeUpdate();
            insertTocNodeLinkStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_NODE_LINK to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertPubTocNodeLink(Connection connection, String parentNodeUuid, String childNodeUuid, int orderNum)
    {
        String parentChildUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting into PUB_TOC_NODE_LINK...");
            PreparedStatement insertTocNodeLinkStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_PUB_TOC_NODE_LINK);
            insertTocNodeLinkStatement.setString(1, parentNodeUuid);
            insertTocNodeLinkStatement.setString(2, parentChildUuid);
            insertTocNodeLinkStatement.setString(3, childNodeUuid);
            insertTocNodeLinkStatement.setInt(4, orderNum);
            insertTocNodeLinkStatement.executeUpdate();
            insertTocNodeLinkStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into PUB_TOC_NODE_LINK to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertTocContentPART(Connection connection, String contentUUID, String contentXml, String title)
    {
        try
        {
            PreparedStatement insertPartContentStatement = connection.prepareStatement(TargetDataMockingConstants.PART_INSERT_INTO_TOC_CONTENT);
            insertPartContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertPartContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertPartContentStatement.setClob(3, contentClob);
            insertPartContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertPartContentStatement.executeUpdate();
            insertPartContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentGRADE(Connection connection, String contentUUID, String contentXml)
    {

        try
        {
            PreparedStatement insertTitleContentStatement = connection.prepareStatement(TargetDataMockingConstants.GRADE_INSERT_INTO_TOC_CONTENT);
            insertTitleContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertTitleContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertTitleContentStatement.setClob(3, contentClob);
            insertTitleContentStatement.executeUpdate();
            insertTitleContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentTITLE(Connection connection, String contentUUID, String contentXml)
    {

        try
        {
            PreparedStatement insertTitleContentStatement = connection.prepareStatement(TargetDataMockingConstants.TITLE_INSERT_INTO_TOC_CONTENT);
            insertTitleContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertTitleContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertTitleContentStatement.setClob(3, contentClob);
            insertTitleContentStatement.executeUpdate();
            insertTitleContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentSUBTITLE(Connection connection, String contentUUID, String contentXml)
    {
        try
        {
            PreparedStatement insertSubtitleContentStatement = connection.prepareStatement(TargetDataMockingConstants.SUBTITLE_INSERT_INTO_TOC_CONTENT);
            insertSubtitleContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertSubtitleContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSubtitleContentStatement.setClob(3, contentClob);
            insertSubtitleContentStatement.executeUpdate();
            insertSubtitleContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentCHAPTER(Connection connection, String contentUUID, String contentXml)
    {
        try
        {
            PreparedStatement insertChapterContentStatement = connection.prepareStatement(TargetDataMockingConstants.CHAPTER_INSERT_INTO_TOC_CONTENT);
            insertChapterContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertChapterContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertChapterContentStatement.setClob(3, contentClob);
            insertChapterContentStatement.executeUpdate();
            insertChapterContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentByWipVersionNum(Connection connection, String contentUuid, int wipVersionNum, int latestFlag)
    {
        try
        {
            logger.information("Inserting into TOC_CONTENT");
            PreparedStatement insertByWipVersionNumTocContentStatement = connection.prepareStatement(TargetDataMockingConstants.SMALL_INSERT_BY_WIP_VERSION_NUM_INTO_TOC_CONTENT);
            insertByWipVersionNumTocContentStatement.setString(1, contentUuid);
            insertByWipVersionNumTocContentStatement.setInt(2, wipVersionNum);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertByWipVersionNumTocContentStatement.setString(3, displayName);
            insertByWipVersionNumTocContentStatement.setInt(4, latestFlag);
            Clob contentClob = CommonDataMocking.clobify(connection, TargetDataMockingConstants.SMALL_TEXT);
            insertByWipVersionNumTocContentStatement.setClob(5, contentClob);
            insertByWipVersionNumTocContentStatement.executeUpdate();
            insertByWipVersionNumTocContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert WIP version: %s data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", wipVersionNum, e.toString()));
        }

        return null;
    }

    public String insertTocContentSECTION(Connection connection, String contentUUID, String contentXml, String title, int wipVersionNum)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.SECTION_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertSectionContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.setInt(5, wipVersionNum);
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentGLOSSARY(Connection connection, String contentUUID, String contentXml, String title, int wipVersionNum)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.GLOSSARY_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            String displayName = TestService.getDisplayName();
            if(displayName.length() > 250)
            {
                displayName = displayName.substring(0, 249);
            }
            insertSectionContentStatement.setString(2, displayName);
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.setInt(5, wipVersionNum);
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch(SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to poulate the hierachy navigate grid failed: %S", e.toString()));
        }
        return  null;
    }

    public void insertPubId(Connection connection, String nodeUUID, int pubId)
    {
        try
        {
            PreparedStatement insertPubIdStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_PUB_ID);
            insertPubIdStatement.setString(1, nodeUUID);
            insertPubIdStatement.setInt(2, pubId);
            insertPubIdStatement.executeUpdate();
            insertPubIdStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to insert pub id '" + pubId + "' for node with uuid '" + nodeUUID + "'");
        }
    }

    public void deletePubId(Connection connection, String nodeUUID)
    {
        try
        {
            PreparedStatement deletePubIdStatement = connection.prepareStatement(TargetDataMockingConstants.DELETE_PUB_ID);
            deletePubIdStatement.setString(1, nodeUUID);
            deletePubIdStatement.executeUpdate();
            deletePubIdStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to delete pub id for node with uuid '" + nodeUUID + "'");
        }
    }

    public String insertTocContentRULE(Connection connection, String contentUUID, String contentXml, String title)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.RULE_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            insertSectionContentStatement.setString(2, TestService.getDisplayName());
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentXND(Connection connection, String contentUUID, String contentXml, String title)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.XND_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            insertSectionContentStatement.setString(2, TestService.getDisplayName());
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentBLAnalysis(Connection connection, String contentUUID, String contentXml, String title)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.BL_ANALYSIS_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            insertSectionContentStatement.setString(2, TestService.getDisplayName());
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentBlueline(Connection connection, String contentUUID, String contentXml, String title)
    {
        try
        {
            PreparedStatement insertSectionContentStatement = connection.prepareStatement(TargetDataMockingConstants.BLUELINE_INSERT_INTO_TOC_CONTENT);
            insertSectionContentStatement.setString(1, contentUUID);
            insertSectionContentStatement.setString(2, TestService.getDisplayName());
            Clob contentClob = CommonDataMocking.clobify(connection, contentXml);
            insertSectionContentStatement.setClob(3, contentClob);
            insertSectionContentStatement.setString(4, "<head.info><headtext>".concat(title).concat("</headtext></head.info>"));
            insertSectionContentStatement.executeUpdate();
            insertSectionContentStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public String insertTocContentPublishingStatus(Connection connection, String contentUuid, int publishStatusId, int contentSetId)
    {
        String publishStatusUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting publishing status into TOC_CONTENT");
            PreparedStatement insertTocContentPublishingStatusStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_TOC_CONTENT_PUBLISHING_STATUS);
            insertTocContentPublishingStatusStatement.setString(1, contentUuid);
            insertTocContentPublishingStatusStatement.setString(2, publishStatusUuid);
            insertTocContentPublishingStatusStatement.setInt(3, publishStatusId);
            insertTocContentPublishingStatusStatement.setInt(4, contentSetId);
            insertTocContentPublishingStatusStatement.executeUpdate();
            insertTocContentPublishingStatusStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT_PUBLISHING_STATUS to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertTocContentPublishingStatusWithWipVersionNum(Connection connection, String contentUuid, int publishStatusId, int contentSetId, int wipVersionNum)
    {
        String publishStatusUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting publishing status into TOC_CONTENT");
            PreparedStatement insertTocContentPublishingStatusStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_TOC_CONTENT_PUBLISHING_STATUS_WITH_WIP_VERSION_NUM);
            insertTocContentPublishingStatusStatement.setString(1, contentUuid);
            insertTocContentPublishingStatusStatement.setInt(2, wipVersionNum);
            insertTocContentPublishingStatusStatement.setString(3, publishStatusUuid);
            insertTocContentPublishingStatusStatement.setInt(4, publishStatusId);
            insertTocContentPublishingStatusStatement.setInt(5, contentSetId);
            insertTocContentPublishingStatusStatement.executeUpdate();
            insertTocContentPublishingStatusStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_CONTENT_PUBLISHING_STATUS to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertTocContentPublishingStatusByWipVersion(Connection connection, String contentUuid, int wipVersionNum, int publishStatusId)
    {
        String publishStatusUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting into TOC_CONTENT_PUBLISHING_STATUS");
            PreparedStatement insertTocContentPublishingStatusStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_BY_WIP_VERSION_NUM_INTO_TOC_CONTENT_PUBLISHING_STATUS);
            insertTocContentPublishingStatusStatement.setString(1, contentUuid);
            insertTocContentPublishingStatusStatement.setInt(2, wipVersionNum);
            insertTocContentPublishingStatusStatement.setString(3, publishStatusUuid);
            insertTocContentPublishingStatusStatement.setInt(4, publishStatusId);
            insertTocContentPublishingStatusStatement.executeUpdate();
            insertTocContentPublishingStatusStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert WIP version: %s data into TOC_CONTENT_PUBLISHING_STATUS to populate the hierarchy navigate grid failed: %s", wipVersionNum, e.toString()));
        }

        return null;
    }

    public String insertTocNodeScriptAssignment(Connection connection, String nodeUuid, int scriptId, int contentSetId)
    {
        try
        {
            logger.information("Inserting into TOC_NODE_SCRIPT_ASSIGNMENT");
            PreparedStatement insertTocNodeScriptAssignmentStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_TOC_NODE_SCRIPT_ASSIGNMENT);
            insertTocNodeScriptAssignmentStatement.setString(1, nodeUuid);
            insertTocNodeScriptAssignmentStatement.setInt(2, scriptId);
            insertTocNodeScriptAssignmentStatement.setInt(3, contentSetId);
            insertTocNodeScriptAssignmentStatement.executeUpdate();
            insertTocNodeScriptAssignmentStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into TOC_NODE_SCRIPT_ASSIGNMENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertPubTocNodeScriptAssignment(Connection connection, String nodeUuid, int scriptId, int contentSetId)
    {
        try
        {
            logger.information("Inserting into PUB_TOC_NODE_SCRIPT_ASSIGNMENT");
            PreparedStatement insertTocNodeScriptAssignmentStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_INTO_PUB_TOC_NODE_SCRIPT_ASSIGNMENT);
            insertTocNodeScriptAssignmentStatement.setString(1, nodeUuid);
            insertTocNodeScriptAssignmentStatement.setInt(2, scriptId);
            insertTocNodeScriptAssignmentStatement.setInt(3, contentSetId);
            insertTocNodeScriptAssignmentStatement.executeUpdate();
            insertTocNodeScriptAssignmentStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into PUB_TOC_NODE_SCRIPT_ASSIGNMENT to populate the hierarchy navigate grid failed: %s", e.toString()));
        }

        return null;
    }

    public String insertGlossaryTerm(Connection connection, String uUID, int contentSetId, String documentId, String term)
    {
        try
        {
            logger.information("Inserting into glossary_term");
            PreparedStatement insertGlossaryTerm = connection.prepareStatement(TargetDataMockingConstants.INSERT_GLOSSARY_TERM);
            insertGlossaryTerm.setString(1, uUID);
            insertGlossaryTerm.setInt(2, contentSetId);
            insertGlossaryTerm.setString(3, documentId);
            insertGlossaryTerm.setString(4, term);
            insertGlossaryTerm.executeUpdate();
            insertGlossaryTerm.close();
        }

        catch(SQLException e)
        {
            Assertions.fail(String.format("Insert data into glossary_term to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public static String insertTocNodeDispDeriv(Connection connection, String derivNodeUUID, String dispNodeUUID, int contentSet)
    {
        String derivDispUuid = CommonDataMocking.generateUUID();
        String dispDerivUuid = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertTocDispDeriv = connection.prepareStatement(TargetDataMockingConstants.INSERT_TOC_NODE_DISP_DERIV);
            insertTocDispDeriv.setString(1, derivNodeUUID);
            insertTocDispDeriv.setString(2, dispNodeUUID);
            insertTocDispDeriv.setString(3, derivDispUuid);
            insertTocDispDeriv.setString(4, dispDerivUuid);
            insertTocDispDeriv.setInt(5, contentSet);
            insertTocDispDeriv.executeUpdate();
            insertTocDispDeriv.close();

            commit(connection);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Assertions.fail(String.format("Inserting into toc_node_disp_deriv failed"));
        }
        return null;
    }

    private String getNodeUUIDWithKeywordVolumeAndContentSet(Connection connection, int keywordId, String volumeNumStr, int contentSetId)
    {
        String nodeUUID = null;
        try
        {
            PreparedStatement selectNodeStatement = connection.prepareStatement(TargetDataMockingConstants.SELECT_NODE_WITH_KEYWORD_ID_VOLUME_AND_CONTENT_SET);
            selectNodeStatement.setInt(1, keywordId);
            selectNodeStatement.setString(2, volumeNumStr);
            selectNodeStatement.setInt(3, contentSetId);
            ResultSet result = selectNodeStatement.executeQuery();
            if(result.next())
            {
                nodeUUID = result.getString("NODE_UUID");
            }
            result.close();
            selectNodeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to select a node: " + e.toString());
        }
        return nodeUUID;
    }

    private boolean nodeExistsWithKeywordVolumeAndContentSet(Connection connection, int keywordId, String volumeNumStr, int contentSetId)
    {
        boolean foundNode = false;
        try
        {
            PreparedStatement selectNodeStatement = connection.prepareStatement(TargetDataMockingConstants.SELECT_NODE_WITH_KEYWORD_ID_VOLUME_AND_CONTENT_SET);
            selectNodeStatement.setInt(1, keywordId);
            selectNodeStatement.setString(2, volumeNumStr);
            selectNodeStatement.setInt(3, contentSetId);
            ResultSet result = selectNodeStatement.executeQuery();
            if(result.next())
            {
                foundNode = true;
            }
            result.close();
            selectNodeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to select a node: " + e.toString());
        }
        return foundNode;
    }

    public String getBOVUUID(Connection connection, int contentSetId)
    {
        return getNodeUUIDWithKeywordVolumeAndContentSet(connection, 101, VOLUME_NUM_STRING, contentSetId);
    }

    public String getEOVUUID(Connection connection, int contentSetId)
    {
        return getNodeUUIDWithKeywordVolumeAndContentSet(connection, 106, VOLUME_NUM_STRING, contentSetId);
    }

    public boolean mockedBOVExists(Connection connection, int contentSetId)
    {
        return nodeExistsWithKeywordVolumeAndContentSet(connection, 101, VOLUME_NUM_STRING, contentSetId);
    }

    public boolean mockedEOVExists(Connection connection, int contentSetId)
    {
        return nodeExistsWithKeywordVolumeAndContentSet(connection, 106, VOLUME_NUM_STRING, contentSetId);
    }

    public String insertTocNodeBOV(Connection connection, int hid, int contentSetId)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting BOV into TOC_NODE...");
            PreparedStatement insertBovStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_BOV_INTO_TOC_NODE);
            insertBovStatement.setString(1, nodeUUID);
            insertBovStatement.setInt(2, hid);
            insertBovStatement.setInt(3, contentSetId);
            insertBovStatement.setString(4, contentUUID);
            insertBovStatement.executeUpdate();
            insertBovStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to insert BOV into TOC_NODE: " + e.toString());
        }
        return contentUUID;
    }

    public String insertTocNodeEOV(Connection connection, int hid, int contentSetId)
    {
        String nodeUUID = CommonDataMocking.generateUUID();
        String contentUUID = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting EOV into TOC_NODE...");
            PreparedStatement insertBovStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_EOV_INTO_TOC_NODE);
            insertBovStatement.setString(1, nodeUUID);
            insertBovStatement.setInt(2, hid);
            insertBovStatement.setInt(3, contentSetId);
            insertBovStatement.setString(4, contentUUID);
            insertBovStatement.executeUpdate();
            insertBovStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to insert EOV into TOC_NODE: " + e.toString());
        }
        return contentUUID;
    }

    public String insertTocContentEOV(Connection connection, String contentUUID, String contentXML, int contentSetId)
    {
        try
        {
            logger.information("Inserting EOV into TOC_CONTENT...");
            PreparedStatement insertEovStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_EOV_INTO_TOC_CONTENT);
            insertEovStatement.setString(1, contentUUID);
            Clob clob = CommonDataMocking.clobify(connection, contentXML);
            insertEovStatement.setClob(2, clob);
            insertEovStatement.setInt(3, contentSetId);
            insertEovStatement.executeUpdate();
            insertEovStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to insert EOV into TOC_CONTENT");
        }
        return null;
    }

    public String insertTocContentBOV(Connection connection, String contentUUID, String contentXML, int contentSetId)
    {
        try
        {
            logger.information("Inserting BOV into TOC_CONTENT...");
            PreparedStatement insertBovStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_BOV_INTO_TOC_CONTENT);
            insertBovStatement.setString(1, contentUUID);
            Clob clob = CommonDataMocking.clobify(connection, contentXML);
            insertBovStatement.setClob(2, clob);
            insertBovStatement.setString(3, "<head.info>9999 999.990 to 1000.end QED TESTING 2022</head.info>");
            insertBovStatement.setInt(4, contentSetId);
            insertBovStatement.executeUpdate();
            insertBovStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to insert BOV into TOC_CONTENT: " + e.toString());
        }
        return null;
    }

    public boolean mockedVolumeExists(Connection connection, int contentSetId)
    {
        boolean foundVolume = false;
        try
        {
            logger.information("Checking for volume...");
            PreparedStatement getVolumeStatement = connection.prepareStatement(TargetDataMockingConstants.GET_VOLUME);
            getVolumeStatement.setInt(1, contentSetId);
            ResultSet result = getVolumeStatement.executeQuery();
            if (result.next())
            {
                foundVolume = true;
            }
            result.close();
            getVolumeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to search for volume.");
        }
        return foundVolume;
    }

    public boolean volumeExistsWithId(Connection connection, int volumeId)
    {
        boolean foundVolume = false;

        try
        {
            logger.information("Searching for volume");
            PreparedStatement findVolumeStatement = connection.prepareStatement(TargetDataMockingConstants.VOLUME_WITH_ID_EXISTS);
            findVolumeStatement.setInt(1, volumeId);
            ResultSet result = findVolumeStatement.executeQuery();
            if(result.next())
            {
                foundVolume = true;
            }
            result.close();
            findVolumeStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to select from VOLUME: " + e.toString());
        }
        return foundVolume;
    }

    public String insertVolume(Connection connection, int volumeId, int publicationId, String bovUUID, String eovUUID, int contentSetId)
    {
        try
        {
            logger.information("Inserting volume");
            PreparedStatement insertVolumeStatement = connection.prepareStatement(TargetDataMockingConstants.INSERT_VOLUME);
            insertVolumeStatement.setInt(1, volumeId);
            insertVolumeStatement.setInt(2, publicationId);
            insertVolumeStatement.setString(3, bovUUID);
            insertVolumeStatement.setString(4, eovUUID);
            insertVolumeStatement.setInt(5, contentSetId);
            insertVolumeStatement.executeUpdate();
            insertVolumeStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Insert data into VOLUME to populate the hierarchy navigate grid failed: %s", e.toString()));
        }
        return null;
    }

    public void deleteTocNodeLink(Connection connection, String childNodeUuid)
    {
        try
        {
            logger.information("Deleting from TOC_NODE_LINK...");
            PreparedStatement deleteTocNodeLinkStatement = connection.prepareStatement(TargetDataMockingConstants.DELETE_FROM_TOC_NODE_LINK);
            deleteTocNodeLinkStatement.setString(1, childNodeUuid);
            deleteTocNodeLinkStatement.executeUpdate();
            deleteTocNodeLinkStatement.close();
            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from TOC_NODE_LINK failed: %s", e.toString()));
        }
    }
}
