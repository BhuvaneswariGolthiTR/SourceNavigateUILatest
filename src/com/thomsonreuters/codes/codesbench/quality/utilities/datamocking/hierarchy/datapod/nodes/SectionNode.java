package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public class SectionNode extends HierarchyNode
{
    public SectionNode(String nodeUUID, String contentUUID, boolean... isHistoricalVersion)
    {
        super(nodeUUID, contentUUID);
        if(isHistoricalVersion.length > 0)
        {
            setHistoricalVersion(isHistoricalVersion[0]);
        }
    }

    @Override
    public NodeType getType()
    {
        return NodeType.SECTION;
    }
}
