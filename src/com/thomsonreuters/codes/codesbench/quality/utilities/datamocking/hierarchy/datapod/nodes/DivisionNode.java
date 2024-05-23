package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class DivisionNode extends HierarchyNode
{

    public DivisionNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.DIVISION;
    }
}
