package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestService.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public abstract class HierarchyNode
{
    protected String nodeUUID;
    protected String contentUUID;

    protected boolean isHistoricalVersion;

    /**
     * HierarchyNode constructor
     *
     * @param nodeUUID    The node UUID of the node in Hierarchy in CodesBench that this object represents
     * @param contentUUID The content UUID of the node in Hierarchy in CodesBench that this object represents
     */
    protected HierarchyNode(String nodeUUID, String contentUUID)
    {
        this.nodeUUID = nodeUUID;
        this.contentUUID = contentUUID;
        this.isHistoricalVersion = false;
    }

    /**
     * Get the node UUID of the node in Hierarchy in CodesBench that this object represents
     *
     * @return The node UUID of the node in Hierarchy in CodesBench that this object represents
     */
    public String getNodeUUID()
    {
        return nodeUUID;
    }

    /**
     * Get whether this node is a Historical Version
     * @return Whether this node is a Historical Version
     */
    public boolean isHistoricalVersion()
    {
        return isHistoricalVersion;
    }

    /**
     * Sets this node as historical. Only for use within the node!
     * @param isHistoricalVersion
     */
    protected void setHistoricalVersion(boolean isHistoricalVersion)
    {
        this.isHistoricalVersion = isHistoricalVersion;
    }

    /**
     * Get the content UUID of the node in Hierarchy in CodesBench that this object represents
     *
     * @return The content UUID of the node in Hierarchy in CodesBench that this object represents
     */
    public String getContentUUID()
    {
        return contentUUID;
    }

    /**
     * Get the immediate children of the node in Hierarchy in CodesBench that this object represents
     *
     * @return The immediate children of the node in Hierarchy in CodesBench that this object represents
     */
    public List<HierarchyNode> getChildren(Connection connection)
    {
        TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
        List<String> childUUIDs = nodeMocker.getNodeUUIDsOfChildrenFromNodeUUID(connection, nodeUUID);
        List<HierarchyNode> children = new ArrayList<>();
        for (String childNodeUUID : childUUIDs)
        {
            String childContentUUID = HierarchyDatabaseUtils.getContentUuidWithNodeUuidAllowEmptyResultSet(childNodeUUID, connection);
            int childKeywordId = HierarchyDatabaseUtils.getNodeKeyword(childNodeUUID, connection);
            NodeType childType = nodeMocker.convertKeywordIdToNodeType(childKeywordId);
            switch (childType.ordinal())
            {
                case 0:
                    children.add(new TitleNode(childNodeUUID, childContentUUID));
                    break;
                case 1:
                    children.add(new SubtitleNode(childNodeUUID, childContentUUID));
                    break;
                case 2:
                    children.add(new ChapterNode(childNodeUUID, childContentUUID));
                    break;
                case 3:
                    children.add(new SectionNode(childNodeUUID, childContentUUID, HierarchyDatabaseUtils.nodeHasLegisEndEffectiveDate(childNodeUUID, connection)));
                    break;
                case 4:
                    children.add(new NODContainerNode(childNodeUUID, childContentUUID));
                    break;
                case 5:
                    children.add(new XNDNode(childNodeUUID, childContentUUID));
                    break;
                case 6:
                    children.add(new BLAnalysisNode(childNodeUUID, childContentUUID));
                    break;
                case 7:
                    children.add(new BluelineNode(childNodeUUID, childContentUUID));
                    break;
                case 8:
                    children.add(new GradeNode(childNodeUUID, childContentUUID));
                    break;
                case 9:
                    children.add(new RuleNode(childNodeUUID, childContentUUID));
                    break;
                case 10:
                    children.add(new PartNode(childNodeUUID, childContentUUID));
                    break;
                case 11:
                    children.add(new NDRSNode(childNodeUUID, childContentUUID));
                    break;
                case 12:
                    children.add(new ARLNode(childNodeUUID, childContentUUID));
                    break;
                case 13:
                    children.add(new SubchapterNode(childNodeUUID, childContentUUID));
                    break;
                case 14:
                    children.add(new ArticleNode(childNodeUUID, childContentUUID));
                    break;
                case 15:
                    children.add(new DivisionNode(childNodeUUID, childContentUUID));
                    break;
                case 16:
                    children.add(new GlossaryNode(childNodeUUID, childContentUUID));
                    break;
                default:
                    break;
            }
        }
        return children;
    }

    /**
     * Delete the node in Hierarchy in CodesBench that this object represents
     *
     * @param connection The database connection
     */
    public void delete(Connection connection)
    {
        logger.information("Deleting node with Node UUID '" + nodeUUID + "'");
        TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
        nodeMocker.deleteNode(connection, this);
        commit(connection);
    }

    /**
     * Get the type of this particular Hierarchy Node
     *
     * @return The node's type
     */
    public abstract NodeType getType();
}
