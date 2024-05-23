package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class NODContainerNode extends HierarchyNode
{
    public NODContainerNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.NOD_CONTAINER;
    }
}
