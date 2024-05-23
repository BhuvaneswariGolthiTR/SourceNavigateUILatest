package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class PartNode extends HierarchyNode
{
    public PartNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.PART;
    }
}
