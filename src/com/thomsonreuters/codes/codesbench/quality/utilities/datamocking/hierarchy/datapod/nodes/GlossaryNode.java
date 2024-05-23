package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

/**
 * @deprecated Deprecated because there is no such thing as a "Glossary Node" is the CodesBench Database. Glossary Nodes are "=" nodes which are Sections.
 */
public class GlossaryNode extends HierarchyNode
{

    public GlossaryNode(String nodeUUID, String contentUUID)
    {
        super(nodeUUID, contentUUID);
    }

    @Override
    public NodeType getType()
    {
        return NodeType.GLOSSARY;
    }
}
