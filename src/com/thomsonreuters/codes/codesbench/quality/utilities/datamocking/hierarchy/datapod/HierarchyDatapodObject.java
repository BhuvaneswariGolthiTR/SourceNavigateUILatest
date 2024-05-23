package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.maps.HashMapUtilities;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HierarchyDatapodObject
{
    private int currentDeletePriority = 0;

    boolean deleted = false;

    private HierarchyNode topNode;

    private ArrayList<PartNode> partList;
    private ArrayList<GradeNode> gradeList;
    private ArrayList<RuleNode> ruleList;
    private ArrayList<NDRSNode> ndrsList;
    private ArrayList<ARLNode> arlList;
    private ArrayList<BluelineNode> bluelineList;
    private ArrayList<BLAnalysisNode> analysisList;
    private ArrayList<XNDNode> xndList;
    private ArrayList<NODContainerNode> containerList;
    private ArrayList<SectionNode> sectionList;
    private ArrayList<ChapterNode> chapterList;
    private ArrayList<SubtitleNode> subtitleList;
    private ArrayList<TitleNode> titleList;
    private ArrayList<SubchapterNode> subchapterList;
    private ArrayList<ArticleNode> articleList;
    private ArrayList<DivisionNode> divisionList;
    private ArrayList<GlossaryNode> glossaryList;

    // Contains the priority for something to be deleted. Bigger numbers are deleted first (e.g. a Blueline will have the highest number and a Title or Grade will typically have the lowest)
    private HashMap<HierarchyNode, Integer> deletePriorityMap;

    /**
     * Constructs a datapod using the given node as the top node of the datapod.
     * If you are not working on Scale and Performance, you probably do not need this constructor (though you may need other methods defined in this class).
     * See TargetDataMockingNew for the usage you likely require.
     *
     * @param topNode The top node of the datapod (usually a Title, but may be a Grade)
     */
    public HierarchyDatapodObject(HierarchyNode topNode)
    {
        this.topNode = topNode;
        deletePriorityMap = new HashMap<>();
        gradeList = new ArrayList<>();
        ruleList = new ArrayList<>();
        ndrsList = new ArrayList<>();
        arlList = new ArrayList<>();
        bluelineList = new ArrayList<>();
        analysisList = new ArrayList<>();
        xndList = new ArrayList<>();
        containerList = new ArrayList<>();
        sectionList = new ArrayList<>();
        chapterList = new ArrayList<>();
        partList = new ArrayList<>();
        subtitleList = new ArrayList<>();
        titleList = new ArrayList<>();
        subchapterList = new ArrayList<>();
        articleList = new ArrayList<>();
        divisionList = new ArrayList<>();
        glossaryList = new ArrayList<>();
        Connection connection = CommonDataMocking.connectToDatabase(TestService.environmentTag);
        updateNodes(connection);
        BaseDatabaseUtils.disconnect(connection);
    }

    /**
     * Clears information and reads the whole datapod. Used to check for new nodes that were not part of the original datapod.
     * If you are not working on Scale and Performance, you probably do not need this method.
     * I know what you're thinking: "How do I know if a certain node is in the datapod or not?"
     * See updateThenCheckContains further down in this class. updateNodes() is private to prevent overuse, which would cause major slowdowns in tests.
     *
     * @param connection A connection to the database that contains this datapod
     */
    private void updateNodes(Connection connection)
    {
        currentDeletePriority = 0;
        deletePriorityMap.clear();
        deletePriorityMap.put(topNode, currentDeletePriority);
        addAllDescendantsAndAssignDeletePriority(topNode, connection);
    }

    /**
     * Recursive method that gets all descendants of the given node and assigns them a delete priority.
     * This delete priority defines when a node is to be deleted after the datapod.delete() call.
     * Highest numbers get deleted first.
     * If you are not working on Scale and Performance, you probably do not need this method. The delete() method already calls this.
     *
     * @param node       The HierarchyNode to check for descendants and assign their delete priority
     * @param connection A connection to the database that contains this datapod
     */
    private void addAllDescendantsAndAssignDeletePriority(HierarchyNode node, Connection connection)
    {
        List<HierarchyNode> children = node.getChildren(connection);
        currentDeletePriority++;
        if (children.size() > 0)
        {
            for (int i = 0; i < children.size(); i++)
            {
                if (!contains(children.get(i).getNodeUUID()))
                {
                    addNode(children.get(i));
                }
                if (node.getNodeUUID().equals(children.get(i).getNodeUUID()))
                {
                    continue;
                }
                deletePriorityMap.put(children.get(i), currentDeletePriority);
                addAllDescendantsAndAssignDeletePriority(children.get(i), connection);
            }
        }
    }

    /**
     * Get an ArrayList containing all the RuleNodes in this datapod
     *
     * @return An ArrayList containing all the RuleNodes in this datapod
     */
    public ArrayList<RuleNode> getRules()
    {
        return ruleList;
    }

    /**
     * Get an ArrayList containing all the GradeNodes in this datapod
     *
     * @return An ArrayList containing all the GradeNodes in this datapod
     */
    public ArrayList<GradeNode> getGrades()
    {
        return gradeList;
    }

    /**
     * Get an ArrayList containing all the NDRSNodes in this datapod
     *
     * @return An ArrayList containing all the NDRSNodes in this datapod
     */
    public ArrayList<NDRSNode> getNDRSNodes()
    {
        return ndrsList;
    }

    /**
     * Get an ArrayList containing all the ARLNodes in this datapod
     *
     * @return An ArrayList containing all the ARLNodes in this datapod
     */
    public ArrayList<ARLNode> getARLNodes()
    {
        return arlList;
    }

    /**
     * Get an ArrayList containing all the BluelineNodes in this datapod
     *
     * @return An ArrayList containing all the BluelineNodes in this datapod
     */
    public ArrayList<BluelineNode> getBluelines()
    {
        return bluelineList;
    }

    /**
     * Get an ArrayList containing all the SectionNodes in this datapod
     *
     * @return An ArrayList containing all the SectionNodes in this datapod
     */
    public ArrayList<SectionNode> getSections()
    {
        return sectionList;
    }

    /**
     * Get an ArrayList containing all the ChapterNodes in this datapod
     *
     * @return An ArrayList containing all the ChapterNodes in this datapod
     */
    public ArrayList<ChapterNode> getChapters()
    {
        return chapterList;
    }

    /**
     * Get an ArrayList containing all the SubtitleNodes in this datapod
     *
     * @return An ArrayList containing all the SubtitleNodes in this datapod
     */
    public ArrayList<SubtitleNode> getSubtitles()
    {
        return subtitleList;
    }

    /**
     * Get an ArrayList containing all the XNDNodes in this datapod
     *
     * @return An ArrayList containing all the XNDNodes in this datapod
     */
    public ArrayList<XNDNode> getXNDNodes()
    {
        return xndList;
    }

    public  ArrayList<NODContainerNode> getContainerNodes()
    {
        return containerList;
    }

    /**
     * Get an ArrayList containing all the TitleNodes in this datapod
     *
     * @return An ArrayList containing all the TitleNodes in this datapod
     */
    public ArrayList<TitleNode> getTitles()
    {
        return titleList;
    }

    /**
     * Get an ArrayList containing all the BLAnalysisNodes in this datapod
     *
     * @return An ArrayList containing all the BLAnalysisNodes in this datapod
     */
    public ArrayList<BLAnalysisNode> getBLAnalyses()
    {
        return analysisList;
    }

    /**
     * Get an ArrayList containing all the GlossaryNodes in the datapod
     *
     * @return An ArrayList containing all the GlossaryNodes in this datapod
     */
    public ArrayList<GlossaryNode> getGlossaryList()
    {
        return glossaryList;
    }

    /**
     * Get an ArrayList containing all the PartNodes in this datapod
     *
     * @return An ArrayList containing all the PartNodes in this datapod
     */
    public ArrayList<PartNode> getParts()
    {
        return partList;
    }

    /**
     * Get an ArrayList containing all the NODContainerNodes in this datapod
     * @return An ArrayList containing all the NODContainerNodes in this datapod
     */
    public ArrayList<NODContainerNode> getNODContainers()
    {
        return containerList;
    }

    /**
     * Get an ArrayList containing all the SubchapterNodes in this datapod
     *
     * @return An ArrayList containing all the SubchapterNodes in this datapod
     */
    public ArrayList<SubchapterNode> getSubchapters()
    {
        return subchapterList;
    }

    /**
     * Get an ArrayList containing all the LawNodes in this datapod
     *
     * @return An ArrayList containing all the LawNodes in this datapod
     */
    public ArrayList<ArticleNode> getArticles()
    {
        return articleList;
    }

    /**
     * Get an ArrayList containing all the DivisionNodes in this datapod
     *
     * @return An ArrayList containing all the DivisionNodes in this datapod
     */
    public ArrayList<DivisionNode> getDivisions()
    {
        return divisionList;
    }

    /**
     * Adds a PartNode to this datapod
     *
     * @param partNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addPart(PartNode partNode)
    {
        return partList.add(partNode);
    }

    /**
     * Adds a RuleNode to this datapod
     *
     * @param ruleNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addRule(RuleNode ruleNode)
    {
        return ruleList.add(ruleNode);
    }

    /**
     * Adds a GradeNode to this datapod
     *
     * @param gradeNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addGrade(GradeNode gradeNode)
    {
        return gradeList.add(gradeNode);
    }

    /**
     * Adds a NDRSNode to this datapod
     *
     * @param ndrsNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addNDRSNode(NDRSNode ndrsNode)
    {
        return ndrsList.add(ndrsNode);
    }

    /**
     * Adds a ARLNode to this datapod
     *
     * @param arlNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addARLNode(ARLNode arlNode)
    {
        return arlList.add(arlNode);
    }

    /**
     * Adds a BluelineNode to this datapod
     *
     * @param bluelineNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addBlueline(BluelineNode bluelineNode)
    {
        return bluelineList.add(bluelineNode);
    }

    /**
     * Adds a BLAnalysisNode to this datapod
     *
     * @param analysisNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addBLAnalysis(BLAnalysisNode analysisNode)
    {
        return analysisList.add(analysisNode);
    }

    /**
     * Adds a XNDNode to this datapod
     *
     * @param xndNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addXND(XNDNode xndNode)
    {
        return xndList.add(xndNode);
    }

    /**
     * Adds a NODContainerNode to this datapod
     *
     * @param containerNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addNODContainer(NODContainerNode containerNode)
    {
        return containerList.add(containerNode);
    }

    /**
     * Adds a SectionNode to this datapod
     *
     * @param sectionNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addSection(SectionNode sectionNode)
    {
        return sectionList.add(sectionNode);
    }

    /**
     * Adds a ChapterNode to this datapod
     *
     * @param chapterNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addChapter(ChapterNode chapterNode)
    {
        return chapterList.add(chapterNode);
    }

    /**
     * Adds a SubtitleNode to this datapod
     *
     * @param subtitleNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addSubtitle(SubtitleNode subtitleNode)
    {
        return subtitleList.add(subtitleNode);
    }
    /**
     * Adds a GlossaryNode to this datapod
     *
     * @param glossaryNode The node being added
     * @return The boolean values as defined by ArrayList.add
     */
    public boolean addGlossaryNode(GlossaryNode glossaryNode)
    {
        return glossaryList.add(glossaryNode);
    }

    /**
     * Adds a TitleNode to this datapod
     *
     * @param titleNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addTitle(TitleNode titleNode)
    {
        return titleList.add(titleNode);
    }

    /**
     * Adds a SubchapterNode to this datapod
     *
     * @param subchapterNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addSubchapterNode(SubchapterNode subchapterNode)
    {
        return subchapterList.add(subchapterNode);
    }

    /**
     * Adds a LawNode to this datapod
     *
     * @param articleNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addArticleNode(ArticleNode articleNode)
    {
        return articleList.add(articleNode);
    }

    /**
     * Adds a DivisionNode to this datapod
     *
     * @param divisionNode The node being added
     * @return The boolean value as defined by ArrayList.add
     */
    public boolean addDivision(DivisionNode divisionNode)
    {
        return divisionList.add(divisionNode);
    }

    /**
     * Adds a HierarchyNode to this datapod. Figures out what kind of node it is and handles it.
     * A little more resource intensive than the other add methods, so only do this if it makes sense to do so (e.g. you're not sure what kind of node you are adding)
     *
     * @param node The node being added
     * @return true if able to find a method to add the node, false if not.
     */
    public boolean addNode(HierarchyNode node)
    {
        NodeType type = node.getType();

        switch (type.ordinal())
        {
            case 0:
                addTitle((TitleNode) node);
                break;
            case 1:
                addSubtitle((SubtitleNode) node);
                break;
            case 2:
                addChapter((ChapterNode) node);
                break;
            case 3:
                addSection((SectionNode) node);
                break;
            case 4:
                addNODContainer((NODContainerNode) node);
                break;
            case 5:
                addXND((XNDNode) node);
                break;
            case 6:
                addBLAnalysis((BLAnalysisNode) node);
                break;
            case 7:
                addBlueline((BluelineNode) node);
                break;
            case 8:
                addGrade((GradeNode) node);
                break;
            case 9:
                addRule((RuleNode) node);
                break;
            case 10:
                addPart((PartNode) node);
                break;
            case 11:
                addNDRSNode((NDRSNode) node);
                break;
            case 12:
                addARLNode((ARLNode) node);
                break;
            case 13:
                addSubchapterNode((SubchapterNode) node);
                break;
            case 14:
                addArticleNode((ArticleNode) node);
                break;
            case 15:
                addDivision((DivisionNode) node);
                break;
            case 16:
                addGlossaryNode((GlossaryNode) node);
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Checks for new nodes, then checks if this datapod contains a node with given UUID
     * Due to the updateNodes call, takes some time (takes longer for larger datapods).
     *
     * @param connection Connection to the database that contains this datapod
     * @param nodeUUID   The UUID of the node to check for
     * @return Whether this datapod contains that node
     */
    public boolean updateThenCheckContains(Connection connection, String nodeUUID)
    {
        updateNodes(connection);
        return contains(nodeUUID);
    }

    /**
     * Checks if the datapod contains a node with the given UUID *WITHOUT CHECKING FOR NEW NODES*
     *
     * @param nodeUUID The UUID of the node to check for
     * @return Whether this datapod contains that node
     */
    public boolean contains(String nodeUUID)
    {
        for (HierarchyNode node : getAllNodes())
        {
            if (node.getNodeUUID().equals(nodeUUID))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the amount of nodes in this datapod *WITHOUT CHECKING FOR NEW NODES*
     *
     * @return The amount of nodes in this datapod
     */
    public int getSize()
    {
        return getAllNodes().size();
    }

    /**
     * Checks for new nodes, then returns the amount of nodes in the datapod
     *
     * @param connection Connection to the database that contains this datapod
     * @return The amount of nodes in the datapod
     */
    public int checkForNewNodesAndGetSize(Connection connection)
    {
        updateNodes(connection);
        return getAllNodes().size();
    }

    /**
     * Returns an ArrayList of all nodes in this datapod
     *
     * @return An ArrayList of all nodes in this datapod
     */
    public List<HierarchyNode> getAllNodes()
    {
        List<HierarchyNode> nodes = new ArrayList<>();
        nodes.add(topNode);
        nodes.addAll(titleList);
        nodes.addAll(subtitleList);
        nodes.addAll(partList);
        nodes.addAll(chapterList);
        nodes.addAll(sectionList);
        nodes.addAll(containerList);
        nodes.addAll(xndList);
        nodes.addAll(analysisList);
        nodes.addAll(bluelineList);
        nodes.addAll(gradeList);
        nodes.addAll(ruleList);
        nodes.addAll(arlList);
        nodes.addAll(ndrsList);
        nodes.addAll(subchapterList);
        nodes.addAll(articleList);
        nodes.addAll(divisionList);
        return nodes;
    }

    /**
     * Checks for new nodes, then deletes the datapod by deleting each node from the highest priority to the lowest.
     * Typically, this means Bluelines get deleted first and Titles get deleted last.
     */
    public void delete()
    {
        if(deleted)
        {
            TestService.logger.information("Datapod.delete() was called but the datapod was already deleted.");
            return;
        }
        deleted = true;
        Connection connection = CommonDataMocking.connectToDatabase(TestService.environmentTag);
        updateNodes(connection);
        deletePriorityMap = HashMapUtilities.sortByValueHighToLow(deletePriorityMap);
        int nodeCount = deletePriorityMap.size();
        int deletedCount = 0;
        TestService.logger.information("Found " + nodeCount + " nodes in the datapod. Deleting them now...");
        for (HierarchyNode node : deletePriorityMap.keySet())
        {
            node.delete(connection);
            TestService.logger.information("Deleted " + (++deletedCount) + "/" + nodeCount + " nodes.");
        }
        BaseDatabaseUtils.disconnect(connection);
    }
}
