package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class XNDNode extends HierarchyNode
{

    public XNDNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.XND;
    }
}
