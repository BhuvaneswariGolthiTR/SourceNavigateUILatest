package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class BluelineNode extends HierarchyNode
{
    public BluelineNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.BLUELINE;
    }
}
