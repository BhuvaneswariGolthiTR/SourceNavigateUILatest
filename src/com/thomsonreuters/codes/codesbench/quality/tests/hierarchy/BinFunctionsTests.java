package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ParentageGraphPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.HierarchyNode;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class BinFunctionsTests extends TestService
{
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    List<HierarchyDatapodObject> datapodObjects = new ArrayList<>();
    Connection uatConnection;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This is a test to make sure that multiple nodes can be added as a child together
     * and after closing and reopening the browser, remove them as a child together successfully.
     * Additionally, this test adds a group of nodes whose publishing statuses are set to something
     * other than Not Published to the bin. Once added, we call Bin Functions > Add Bin as Child
     * on a Chapter node that is not the parent of our group of nodes added to the bin. Finally,
     * we verify the group of added nodes publishing statuses are updated to Not Published Status.
     * Finally, we add the group of nodes back to the bin and on the same Chapter node, call
     * Bin Functions > Remove Bin as Child. This should return the group of nodes to their original
     * location in Hierarchy. The nodes should also retain a Not Published publishing status. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addBinAsChildTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());

        String sectionNodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String sectionValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid,contentSetIowa,uatConnection);
        String sectionNodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String sectionValue2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid2,contentSetIowa,uatConnection);
        String sectionNodeUuid3 = datapodObjects.get(0).getSections().get(3).getNodeUUID();
        String sectionValue3 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid3,contentSetIowa,uatConnection);
        String chapterNodeUuid = datapodObjects.get(0).getChapters().get(1).getNodeUUID();
        String chapterValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(chapterNodeUuid, contentSetIowa, uatConnection);

        String[] values = {sectionValue, sectionValue2};
        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add first 2 values to bin
        hierarchySearchPage().quickSearch(keyword, sectionValue);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        siblingMetadataPage().selectNodes(sectionValue2);
        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionNodeUuid, uatConnection), contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionNodeUuid2, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean areNodesWestLawLoaded = siblingMetadataPage().areNodesByValuesWestlawLoaded(values);
        Assertions.assertTrue(areNodesWestLawLoaded, "The nodes publishing statuses should be westlaw loaded after running queries but are not");

        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Add bin as child to value3
        hierarchySearchPage().quickSearch("CHAPTER", chapterValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddBinAsChild();

        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that nodes was added as a child
        hierarchySearchPage().quickSearch(keyword, sectionValue3);
        boolean childAdded1 = siblingMetadataPage().isNodeDisplayedWithValue(sectionValue);
        boolean childAdded2 = siblingMetadataPage().isNodeDisplayedWithValue(sectionValue2);
        boolean childrenNotPublished = siblingMetadataPage().areNodesByValuesNotPublished(values);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(childAdded1, "The value was not added to the bin or was the wrong value.  Expected: " + sectionValue),
            () -> Assertions.assertTrue(childAdded2, "The value was not added as a sibling or was the wrong value .  Expected: " + sectionValue2),
            () -> Assertions.assertTrue(childrenNotPublished, "The nodes have a publishing status of something other than Not Published")
        );

        TestSetupEdge.closeBrowser();
        TestSetupEdge.openBrowser();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add 2 child nodes to bin
        hierarchySearchPage().quickSearch(keyword, sectionValue);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Remove bin as child from node(value3)
        hierarchySearchPage().quickSearch("CHAPTER", chapterValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsRemoveBinAsChild();

        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that child nodes were successfully removed from node
        hierarchySearchPage().quickSearch(keyword, sectionValue3);
        boolean childRemoved1 = siblingMetadataPage().isNodeDisplayedWithValue(sectionValue);
        boolean childRemoved2 = siblingMetadataPage().isNodeDisplayedWithValue(sectionValue2);

        hierarchySearchPage().quickSearch(keyword, sectionValue);
        boolean childrenNotPublishedAfterRemoveChildAsBin = siblingMetadataPage().areNodesByValuesNotPublished(values);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(childRemoved1, "The value was not removed after selecting Remove Bin as Child"),
            () -> Assertions.assertFalse(childRemoved2, "The value was not removed after selecting Remove Bin as Child"),
            () -> Assertions.assertTrue(childrenNotPublishedAfterRemoveChildAsBin, "The nodes have a publishing status of Not Published after Remove Bin as Child")
        );

    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This is a test to make sure that nodes can be added as a sibling. and after closing and reopening
     * the browser, remove them as a child successfully. Additionally, the test adds a section node with not Not Published
     * publishing status to the bin. Then, on a different section node under a different parent than the node added
     * to the bin, we perform a Bin Functions > Add Bin As sibling. We then check that the node we added to the bin
     * is moved and has a publishing status of Not Published.  <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addBinAsSiblingTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid,contentSetIowa,uatConnection);
        String nodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2,contentSetIowa,uatConnection);

        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().quickSearch(keyword, value);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Add node in bin as sibling
        hierarchySearchPage().quickSearch(keyword, value2);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddBinAsSibling();

        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that node successfully added as sibling
        hierarchySearchPage().quickSearch(keyword, value2);
        String checkNodeAdded = siblingMetadataPage().getNodeValueBelowSelectedNode();
        boolean checkNodeAddedNotPublished = siblingMetadataPage().isNodeBelowSelectedNodeNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(checkNodeAddedNotPublished, "Node has a publishing status of Not Published after Add Bin As Sibling"),
            () -> Assertions.assertTrue(checkNodeAdded.equals(value),"Node was not added as a sibling. Expected: " + value)
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test adds 2 nodes at a time to the bin and checks that they appear when the test goes to view the bin.
     * Then when viewing the contents of the bin, the test empties the bin and then checks the contents of the bin again to check
     * that it is now empty. This test has no publishing related verifications. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void emptyBinUITest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid,contentSetIowa,uatConnection);
        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().quickSearch(keyword, value);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Check that node was added to bin and empty bin
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean itemInBin = viewSelectedNodesBinPage().getNodeValueByRow(1).equals(value);
        viewSelectedNodesBinPage().emptyBin();

        //Check that bin is empty
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean binIsEmpty = viewSelectedNodesBinPage().isBinEmpty();

        Assertions.assertAll
          (
                  () -> Assertions.assertTrue(itemInBin,"The node was not added to the bin correctly"),
                  () -> Assertions.assertTrue(binIsEmpty,"The bin did not empty after selecting to empty bin")
          );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test checks to see that after adding a node to the bin, that you can successfully empty the bin by
     * selecting to empty bin in the popup and context menus. This test has no publishing related verifications. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void emptyBinContextMenuTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid,contentSetIowa,uatConnection);

        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().quickSearch(keyword,value);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        //Add node to bin
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().quickSearch(keyword, value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Check that nod was added to bin
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean itemInBin2 = viewSelectedNodesBinPage().getNodeValueByRow(1).equals(value);
        viewSelectedNodesBinPage().closeCurrentWindowIgnoreDialogue();

        //bin functions-> empty bin
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsEmptyBin();

        //Check that bin is empty
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean binIsEmpty2 = viewSelectedNodesBinPage().isBinEmpty();

        Assertions.assertAll
          (
                  () -> Assertions.assertTrue(itemInBin2,"The node was not added to the bin correctly"),
                  () -> Assertions.assertTrue(binIsEmpty2,"The bin is not empty after selecting empty bin from the context menu")
          );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test checks that after adding a node to the bin, that it appears in
     * the sibling metadata section,navigation tree section,disposition/derivation section, and the parentage graph section
     * after clicking on find in hierarchy in the view bin page. It then checks that the bin is empty after clicking the empty button.
     * This test has no publishing related verifications. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addSingleTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID = datapodObjects.get(0).getSections().get(0).getNodeUUID();

        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, contentSetIowa, uatConnection);
        String treeNodeValue = "= " + value;

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Checks that node was successfully added to bin
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        String nodeAdded = viewSelectedNodesBinPage().getNodeValueByRow(1);

        //Find added node in hierarchy
        viewSelectedNodesBinPage().rightClickFindInHierarchy();

        //Verifies that node is found in each section of the page
        boolean sibValueCheck = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean treeValueCheck = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(treeNodeValue);
        boolean dispValueCheck = dispositionDerivationPage().isNodeValueDisplayed(value);
        boolean graphIsDisplayed = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);

        //Empty contents of the bin
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        viewSelectedNodesBinPage().emptyBin();

        //Checks that bin is now empty
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean binIsEmpty = viewSelectedNodesBinPage().isBinEmpty();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeAdded.equals(value), "The value was not added to the bin or was the wrong value.  Expected: " + value),
            () -> Assertions.assertTrue(sibValueCheck, "The sibling value was not the expected value after adding node to bin and then selecting find node in hierarchy.  Expected: " + value),
            () -> Assertions.assertTrue(treeValueCheck, "The tree value was not the expected value after adding node to bin and then selecting find node in hierarchy.  Expected: " + value),
            () -> Assertions.assertTrue(dispValueCheck, "The disp value was not the expected value after adding node to bin and then selecting find node in hierarchy.  Expected: " + value),
            () -> Assertions.assertTrue(graphIsDisplayed, "The parentage graph did not exist after adding node to bin and then selecting find node in hierarchy."),
            () -> Assertions.assertTrue(binIsEmpty, "The bin did not empty after selecting empty bin")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test assures that when you copy a node as a child, that it then shows up in the hierarchy navigation
     * tree on the left side of the window. Additionally, the test checks that the copied node receives a publishing status
     * of Not Published. Finally, we check that the original node added to the bin and copied as a child retains its publishing
     * status set at the beginning of the test. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyBinAsChildTest()
    {
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String sectionContentUuid = datapodObjects.get(0).getSections().get(0).getContentUUID();
        String chapterContentUuid = datapodObjects.get(0).getChapters().get(1).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().searchContentUuid(sectionContentUuid);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(sectionContentUuid, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Copy bin as child
        hierarchySearchPage().searchContentUuid(chapterContentUuid);
        String chapterNodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsCopyBinAsChild();
        copyPage().clickQuickLoadOk();

        //Check in nav tree that node above selected node is value2
        String nodeCopied = hierarchyTreePage().getNodeValueAboveSelectedTreeNode();
        boolean nodeWasCopied = nodeCopied.contains(chapterNodeValue);
        boolean copiedNodeIsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeWasCopied, "The node was not copied to the location"),
            () -> Assertions.assertTrue(copiedNodeIsNotPublishedStatus, "Copied node has publishing status of Not Published")
        );

        hierarchySearchPage().searchContentUuid(sectionContentUuid);
        boolean originalNodeRemainedLoadedToWestlawStatusBeforeDeletingCopy = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(originalNodeRemainedLoadedToWestlawStatusBeforeDeletingCopy, "Original node remained Loaded to Westlaw Status after creating the copy but before deleting");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test checks that multiple nodes can be added to bin and copied as a sibling successfully.
     * Then correctly deletes each copied node one by one. Additionally, this test verifies that the multiple nodes
     * added to the bin and copied receive a publishing status of Not Published. Finally, the test verifies that
     * the original nodes that were added to the bin retain their original publishing status. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyBinAsSiblingMultipleTest()
    {
        HierarchyDatapodConfiguration.getConfig().setChapterCount(2);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());

        String nodeUuid1= datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid1,contentSetIowa,uatConnection);

        String nodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2,contentSetIowa,uatConnection);

        String nodeUuid3 = datapodObjects.get(0).getSections().get(2).getNodeUUID();
        String value3 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid3,contentSetIowa,uatConnection);

        String[] values = {value,value2};// 2 sections in first chapter
        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add nodes to bin
        hierarchySearchPage().quickSearch(keyword, value);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        siblingMetadataPage().selectNodes(value2);
        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid1, uatConnection), contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid2, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Copy bin as sibling to value3
        hierarchySearchPage().quickSearch(keyword, value3);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsCopyBinAsSibling();
        copyPage().clickQuickLoadOk();

        //check bin was copied as sibling
        boolean copiedNodesAreNotPublished = siblingMetadataPage().areNodesByValuesNotPublished(values);
        boolean parentNodeCheck = siblingMetadataPage().isNodeDisplayedWithValue(value3);
        boolean copiedNodeCheck = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean copiedNodeCheck2 = siblingMetadataPage().isNodeDisplayedWithValue(value2);

        hierarchySearchPage().searchContentUuid(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid1, uatConnection));
        boolean originalNodesRemainedLoadedToWestlawAfterCopy = siblingMetadataPage().areNodesByValuesWestlawLoaded(values);

        Assertions.assertAll
          (
                  () -> Assertions.assertTrue(originalNodesRemainedLoadedToWestlawAfterCopy, "Original nodes retained their publishing status of Loaded to Westlaw after calling Copy but before deleting the copied nodes"),
                  () -> Assertions.assertTrue(copiedNodesAreNotPublished, "The nodes added to the bin and copied are not published status"),
                  () -> Assertions.assertTrue(parentNodeCheck, "The parent node was not displayed"),
                  () -> Assertions.assertTrue(copiedNodeCheck, "The node was not successfully copied"),
                  () -> Assertions.assertTrue(copiedNodeCheck2, "The second node was not successfully copied")
          );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test checks that the user can successfully create a deep copy of a node as a child and
     * have the copied node correctly displayed in the nav tree. Then correctly delete the copied node. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deepCopyBinAsChildSingleTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(3);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodContainerNodeUUID = datapodObjects.get(1).getContainerNodes().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodContainerNodeUUID, contentSetIowa, uatConnection);

        String sectionNodeUUID = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value2  = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add node to bin
        hierarchySearchPage().quickSearch("NOD CONTAINER", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Deep copy bin as child
        hierarchySearchPage().quickSearch("=", value2);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsDeepCopyBinAsChild();
        deepCopyPage().clickQuickLoadOk();
        String deepCopyParentNode = hierarchyTreePage().getNodeValueAboveSelectedTreeNode();
        boolean nodeWasDeepCopied = deepCopyParentNode.contains(value2);
        Assertions.assertTrue(nodeWasDeepCopied, "The node was not deep copied correctly");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test adds 2 nodes at a time to the bin and checks that they appear when the test goes to view the bin.
     * Then when viewing the contents of the bin, the test empties the bin and then checks the contents of the bin again to check
     * that it is now empty. This test has no publishing related verifications. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addMultipleTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());

        String nodeUUID1 = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID1,contentSetIowa,uatConnection);

        String nodeUUID2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String value2 =  HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID2,contentSetIowa,uatConnection);

        String[] values = {value,value2};

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add nodes to bin
        hierarchySearchPage().quickSearch("=",value);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //check that bin contains added nodes
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        String isNodeDisplayed1 = viewSelectedNodesBinPage().getNodeValueByRow(1);
        String isNodeDisplayed2 = viewSelectedNodesBinPage().getNodeValueByRow(2);

        //Empty bin
        viewSelectedNodesBinPage().emptyBin();

        //check that bin is now empty
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsViewBin();
        boolean binIsEmpty = viewSelectedNodesBinPage().isBinEmpty();

        Assertions.assertAll
          (
                  () -> Assertions.assertEquals(isNodeDisplayed1, value, "The node was not added to the bin after selecting add to bin"),
                  () -> Assertions.assertEquals(isNodeDisplayed2, value2, "The node was not added to the bin after selecting add to bin"),
                  () -> Assertions.assertTrue(binIsEmpty,"The bin is not empty after clicking empty bin")
          );
    }

    /**
     * STORY/BUG - HALCYONST-12811 <br> //TODO ask jesse about this
     * SUMMARY - This tests checks that the user can link and delink a bin to a node and have the correct display of the
     * nodes in the disposition/derivation section. Additionally, the test verifies that a node with not Not Published
     * status that is added to the bin should have its publishing status updated to Not Published upon calling Bin Functions >
     * Link Bin Node Before. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void linkBinNodeBeforeTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String nodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid, contentSetIowa, connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, contentSetIowa, connection);
        BaseDatabaseUtils.disconnect(connection);
        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check last test run was cleaned properly
        hierarchySearchPage().quickSearch(keyword, value2);

        if (dispositionDerivationPage().isNodeValueDisplayed(value))
        {
            dispositionDerivationPage().clickExpandButton();
            dispositionDerivationPage().selectNodeByValue(value2);
            dispositionDerivationPage().rightClickNodeByValue(value2);
            dispositionDerivationContextMenu().delinkPreviousNode();
            delinkPreviousNodePage().selectFirstNode();
            delinkPreviousNodePage().clickQuickLoadOk();
            dispositionDerivationPage().clickExpandButton();

            //check node is delinked
            boolean isNodeLinked = dispositionDerivationPage().isNodeValueDisplayed(value);
            Assertions.assertFalse(isNodeLinked, "The node was not delinked properly");
        }

        //Add node to bin
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().quickSearch(keyword, value);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Link bin with node before
        hierarchySearchPage().quickSearch(keyword, value2);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsLinkBinNodeBefore();

        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //check if node value exists in disposition/derivation section and if node added to bin and linked updates to Not Published status
        boolean nodeAddedToBinIsNotPublishedAfterLinkBinNodeBefore = siblingMetadataPage().isSelectedNodeNotPublished();
        boolean nodeLinked = dispositionDerivationPage().isNodeValueDisplayed(value);
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeAddedToBinIsNotPublishedAfterLinkBinNodeBefore, "After Linking the node added to the bin before, the node receives a publishing status of Not Published"),
            () -> Assertions.assertTrue(nodeLinked,"The node was not linked properly")
        );

        //delink the node to verify delink does work
        dispositionDerivationPage().clickExpandButton();
        dispositionDerivationPage().selectNodeByValue(value2);
        dispositionDerivationPage().rightClickNodeByValue(value2);
        dispositionDerivationContextMenu().delinkPreviousNode();
        delinkPreviousNodePage().selectFirstNode();
        delinkPreviousNodePage().clickQuickLoadOk();
        dispositionDerivationPage().clickExpandButton();

        //check node is delinked
        boolean nodeIsDisplayed = dispositionDerivationPage().isNodeValueDisplayed(value);
        Assertions.assertFalse(nodeIsDisplayed, "The node was not delinked properly");
    }

    /**
     * STORY/BUG - HALCYONST-12811 <br> //TODO ask jesse about this
     * SUMMARY - This tests checks that the user can link and delink a bin to a node and have the correct display of the
     * nodes in the disposition/derivation section. Additionally, the test verifies that a node with not Not Published
     * status that is added to the bin should have its publishing status updated to Not Published upon calling Bin Functions >
     * Link Bin Node After. Finally, the test verifies that after Delinking a node, the node that is delinked should get a
     * publishing status of Not Published. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void linkBinNodeAfterTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String nodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid, contentSetIowa, connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, contentSetIowa, connection);
        BaseDatabaseUtils.disconnect(connection);
        String keyword = "=";
        String contentUuid = datapodObjects.get(0).getSections().get(0).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check last test run was cleaned properly
        hierarchySearchPage().quickSearch(keyword, value2);
        if (dispositionDerivationPage().isNodeValueDisplayed(value))
        {
            dispositionDerivationPage().clickExpandButton();
            dispositionDerivationPage().selectNodeByValue(value);
            dispositionDerivationPage().rightClickNodeByValue(value);
            dispositionDerivationContextMenu().delinkPreviousNode();
            delinkPreviousNodePage().selectFirstNode();
            delinkPreviousNodePage().clickQuickLoadOk();
            dispositionDerivationPage().clickExpandButton();

            //check node is delinked
            boolean isNodeLinked = dispositionDerivationPage().isNodeValueDisplayed(value2);
            Assertions.assertFalse(isNodeLinked, "The node was not delinked properly");
        }

        //Add node to bin
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().quickSearch(keyword, value);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Link bin with node after
        hierarchySearchPage().quickSearch(keyword, value2);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsLinkBinNodeAfter();

        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //check if node value exists in disposition/derivation section and if node added to bin and linked updates to Not Published status
        boolean nodeAddedToBinIsNotPublishedAfterLinkBinNodeAfter = siblingMetadataPage().isSelectedNodeNotPublished();
        boolean nodeLinked = dispositionDerivationPage().isNodeValueDisplayed(value);
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeAddedToBinIsNotPublishedAfterLinkBinNodeAfter, "After Linking the node added to the bin after, the node receives a publishing status of Not Published"),
            () -> Assertions.assertTrue(nodeLinked,"The node was not linked properly")
        );

        //delink the node
        dispositionDerivationPage().clickExpandButton();
        dispositionDerivationPage().selectNodeByValue(value2);
        dispositionDerivationPage().rightClickNodeByValue(value2);
        dispositionDerivationContextMenu().publishingStatusSetPublishReady();
        dispositionDerivationPage().selectNodeByValue(value);
        dispositionDerivationPage().rightClickNodeByValue(value);
        dispositionDerivationContextMenu().delinkPreviousNode();
        delinkPreviousNodePage().selectFirstNode();
        delinkPreviousNodePage().clickQuickLoadOk();
        dispositionDerivationPage().clickExpandButton();

        //check node is delinked and updated to not published publishing status
        hierarchySearchPage().quickSearch(keyword, value2);
        boolean nodeIsStillLinked = dispositionDerivationPage().isNodeValueDisplayed(value);
        boolean nodeUpdatedToNotPublishedAfterDelink = siblingMetadataPage().isSelectedNodeNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(nodeIsStillLinked,"The node is still linked"),
            () -> Assertions.assertTrue(nodeUpdatedToNotPublishedAfterDelink,"The node that was delinked updated to Not Published publishing status ")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that when moving bins as a child, that the correct children are
     * displayed in the hierarchy tree and sibling metadata sections <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void moveBinAsChildTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());

        HierarchyDatapodObject firstDatapod = datapodObjects.get(1);
        HierarchyDatapodObject secondDatapod = datapodObjects.get(0);

        String firstUUID = firstDatapod.getChapters().get(0).getNodeUUID();
        String secondUUID = secondDatapod.getChapters().get(0).getNodeUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String firstValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(firstUUID, contentSetIowa, uatConnection);
        String secondValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(secondUUID, contentSetIowa, uatConnection);

        List<HierarchyNode> firstValueChildren = firstDatapod.getChapters().get(0).getChildren(uatConnection);
        String secondValueChildUUID = secondDatapod.getSections().get(0).getNodeUUID();
        String secondValueChild = HierarchyDatabaseUtils.getNodeValueByNodeUUID(secondValueChildUUID, contentSetIowa, uatConnection);
        String sectionKeyword = "=";
        String chapterKeyword = "CHAPTER";
        String[] childrenArray = new String[3];
        String[] childrenArrayContentUuids = new String[3];
        for(int i = 0; i < firstValueChildren.size(); i++)
        {
            HierarchyNode node = firstValueChildren.get(i);
            childrenArrayContentUuids[i] = node.getContentUUID();
            childrenArray[i] = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node.getNodeUUID(), contentSetIowa, uatConnection);
        }

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        for (int i = 0; i < childrenArrayContentUuids.length; i++)
        {
            hierarchySearchPage().searchContentUuid(childrenArrayContentUuids[i]);
            if (!siblingMetadataPage().isSelectedNodeNotPublished())
            {
                siblingMetadataPage().rightClickSelectedSiblingMetadata();
                siblingMetadataContextMenu().setNotPublishedPublishingStatus();
            }

            PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(childrenArrayContentUuids[i], contentSetIowa, uatConnection);

            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().refreshSelection();
        }

        //Add nodes to bin
        hierarchySearchPage().quickSearch(sectionKeyword, childrenArray[0]);
        siblingMetadataPage().selectNodes(childrenArray);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        //Move nodes in bin as child
        hierarchySearchPage().quickSearch(chapterKeyword, secondValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsMoveBinAsChild();
        movePage().clickQuickLoadOk();

        String nodesCopied = hierarchyTreePage().getNodeValueAboveSelectedTreeNode();
        boolean nodesWereCopied = nodesCopied.contains(secondValue);
        Assertions.assertTrue(nodesWereCopied, "Nodes were copied correctly. Parent node now appears to be " + secondValue);

        boolean movedNodesAreNotPublishedStatus = siblingMetadataPage().areSelectedNodesNotPublished();

        //Check that node doesn't have children
        hierarchySearchPage().quickSearch(chapterKeyword, firstValue);
        boolean noNodeChildren = hierarchyTreePage().doesSelectedNodeHaveChildren();
        Assertions.assertFalse(noNodeChildren, "The node has children when it should not");

        //Check that searched node has children
        hierarchySearchPage().quickSearch(sectionKeyword, childrenArray[0]);
        List<String> currentMetadataNodeValues = siblingMetadataPage().getAllNodeValuesInSiblingMetadataGrid();
        ListIterator<String> iterator = currentMetadataNodeValues.listIterator();
        Arrays.asList(childrenArray).forEach(element -> Assertions.assertEquals(element, iterator.next(), "Children did not get moved correctly."));
        boolean childNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(secondValueChild);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(childNodeAppears, secondValueChild + " is not a child of " + secondValue + " but should be."),
            () -> Assertions.assertTrue(movedNodesAreNotPublishedStatus, "Nodes that were moved updated to Not Published status")
        );
    }

    /**
     * STORY: HALCYONST-11095 <br>
     * SUMMARY: This test adds a section node with Not Published to the bin. After adding the node to the bin, the test performs a
     * Bin Functions > Move Bin As Sibling on a different section node and verifies the node that was added to the bin will remain
     * not published, and the node that we performed Move Bin as Sibling on remains Westlaw Loaded status <br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void moveBinAsSiblingTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String nodeUuid1 = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String contentUuid1 = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid1, uatConnection);
        String nodeUuid2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String contentUuid2 =  HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid2, uatConnection);
        String parentNodeUuid = datapodObjects.get(0).getChapters().get(0).getNodeUUID();
        String parentContentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(parentNodeUuid, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(parentNodeUuid);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(parentContentUuid, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean isParentNodeLoadedToWestlaw = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(isParentNodeLoadedToWestlaw, "Parent node is Loaded to Westlaw status");

        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        String node1Value = siblingMetadataPage().getSelectedNodeValue();

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid1, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //copy node for mock up
        boolean isNode1LoadedToWestlawStatus = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(isNode1LoadedToWestlawStatus, "The publish status of node1 should be Not Published before starting the test");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        hierarchySearchPage().searchNodeUuid(nodeUuid2);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid2, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean isNode2LoadedToWestlawStatus = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(isNode2LoadedToWestlawStatus, "The publish status of node2 should be WL before starting the test");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsMoveBinAsSibling();
        movePage().clickQuickLoadOk();

        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        boolean isNode1NotPublishedStatusAfterMove = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchySearchPage().searchNodeUuid(nodeUuid2);
        boolean isNode2LoadedToWestlawStatusAfterMove = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        siblingMetadataPage().selectSelectedNodesNextNode();
        boolean nodeMovedCorrectly = siblingMetadataPage().getSelectedNodeValue().equals(node1Value);
        Assertions.assertTrue(nodeMovedCorrectly, "Node 1 Value appears after Node 2 Value after Move Bin as Sibling call");

        hierarchySearchPage().searchNodeUuid(parentNodeUuid);
        boolean isParentNodeLoadedToWestlawAfterMove = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isNode1NotPublishedStatusAfterMove, "Node1 should have a Not Published status status after move"),
            () -> Assertions.assertTrue(isNode2LoadedToWestlawStatusAfterMove, "Node2 should have Loaded to Westlaw status after move"),
            () -> Assertions.assertTrue(isParentNodeLoadedToWestlawAfterMove, "Parent node should have Loaded to Westlaw status after move")
        );
    }

    /**
     * STORY: <br>
     * SUMMARY: This test checks that the Bin Functions > Relocate Bin As Sibling will set the node to not published, the status is transferred, the parent is Westlaw loaded,
     *  the sibling is Westlaw loaded, and the relocated node appears.  <br>
     * USER:  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void relocateBinAsSiblingPublishingTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String parentNodeUuid = datapodObjects.get(0).getChapters().get(0).getNodeUUID();
        String parentContentUUID = datapodObjects.get(0).getChapters().get(0).getContentUUID();
        String parentNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(parentNodeUuid,contentSetIowa,uatConnection);

        String node1NodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String node1ContentUUID = datapodObjects.get(0).getSections().get(0).getContentUUID();
        String node1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node1NodeUuid,contentSetIowa,uatConnection);

        String node2NodeUuid = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        String node2ContentUUID = datapodObjects.get(0).getSections().get(1).getContentUUID();
        String node2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node2NodeUuid,contentSetIowa,uatConnection);

        String relocatedNewValue = "Test relocate sibling";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchContentUuid(parentContentUUID);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(parentContentUUID, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        hierarchySearchPage().searchNodeUuid(node1NodeUuid);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(node1ContentUUID, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        hierarchySearchPage().searchNodeUuid(node2NodeUuid);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(node2ContentUUID, contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsRelocateBinAsSibling();

        relocateBinAsChildSiblingPage().setNewValue(relocatedNewValue);
        relocateBinAsChildSiblingPage().setEffectiveStartDateToToday();
        relocateBinAsChildSiblingPage().setQuickLoad();
        relocateBinAsChildSiblingPage().clickOkButton();

        String relocateWorkflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().setWorkflowID(relocateWorkflowId);
        workflowSearchPage().clickFilterButton();

        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, "The workflow: " + relocateWorkflowId + " should have finished but did not");

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodesByValue(node1Value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean isNode1NotPublishedAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean isNode1TransferStatusAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedRowStatusTransfer();

        siblingMetadataPage().selectNodesByValue(relocatedNewValue);

        boolean isRelocatedNodeNotPublishedAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean isRelocatedNodeLiveStatusAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedRowStatusLive();

        siblingMetadataPage().selectNodes(node2Value);
        boolean isNode2LoadedToWestlawStatusAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        hierarchySearchPage().searchContentUuid(parentContentUUID);
        boolean isParentNodeLoadedToWestlawStatusAfterRelocateBinAsSibling = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isNode1NotPublishedAfterRelocateBinAsSibling, "The original cloned node should have a status of Not Published"),
            () -> Assertions.assertTrue(isNode1TransferStatusAfterRelocateBinAsSibling, "The original cloned node should have a status of transfer"),
            () -> Assertions.assertTrue(isRelocatedNodeNotPublishedAfterRelocateBinAsSibling, "The relocated node should have a publishing status of Not Published"),
            () -> Assertions.assertTrue(isRelocatedNodeLiveStatusAfterRelocateBinAsSibling, "The relocated node should have a status of live"),
            () -> Assertions.assertTrue(isNode2LoadedToWestlawStatusAfterRelocateBinAsSibling, "The node we used as the target for relocation should not have changed its status to Not Published"),
            () -> Assertions.assertTrue(isParentNodeLoadedToWestlawStatusAfterRelocateBinAsSibling, "The parent node not have changed its status to Not Published")
        );
    }

    //TODO update javadoc

    /**
     * STORY: <br>
     * SUMMARY: This test whether the Bin Functions > Relocate Bin As Child will set the node to not publish<br>
     * USER:  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void relocateBinAsChildPublishingTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String parentNodeUuid = datapodObjects.get(0).getChapters().get(0).getNodeUUID();
        String node1NodeUuid = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String relocatedNewValue = "Test relocate child";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(parentNodeUuid);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(parentNodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        hierarchySearchPage().searchNodeUuid(node1NodeUuid);
        String node1Value = siblingMetadataPage().getSelectedNodeValue();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node1NodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsAddToBin();

        hierarchySearchPage().searchNodeUuid(parentNodeUuid);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(parentNodeUuid, uatConnection), contentSetIowa, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().binFunctionsRelocateBinAsChild();

        relocateBinAsChildSiblingPage().setNewValue(relocatedNewValue);
        relocateBinAsChildSiblingPage().setEffectiveStartDateToToday();
        relocateBinAsChildSiblingPage().setQuickLoad();
        relocateBinAsChildSiblingPage().clickOkButton();

        String relocateWorkflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();

        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().setWorkflowID(relocateWorkflowId);
        workflowSearchPage().clickFilterButton();

        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, "Workflow: " + relocateWorkflowId + " finished");

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        hierarchyNavigatePage().switchToHierarchyEditPage();

        hierarchySearchPage().quickSearch("=", node1Value);

        boolean isNode1NotPublishedStatusAfterRelocateBinAsChild = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean isNode1TransferStatusAfterRelocateBinAsChild = siblingMetadataPage().isSelectedRowStatusTransfer();

        siblingMetadataPage().selectNodesByValue(relocatedNewValue);
        boolean isRelocatedNodeNotPublishedAfterRelocateBinAsChild = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean isRelocatedNodeLiveStatusAfterRelocateBinAsChild = siblingMetadataPage().isSelectedRowStatusLive();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isNode1TransferStatusAfterRelocateBinAsChild, "The first node should have a status of Transfer after relocating bin as child"),
            () -> Assertions.assertTrue(isNode1NotPublishedStatusAfterRelocateBinAsChild, "The first node should have a publishing status of 'Not Published' after relocating bin as child"),
            () -> Assertions.assertTrue(isRelocatedNodeNotPublishedAfterRelocateBinAsChild, "The relocated node should have a publishing status of 'Not Published' after relocating bin as child"),
            () -> Assertions.assertTrue(isRelocatedNodeLiveStatusAfterRelocateBinAsChild, "The relocated node should have a status of Live after relocating bin as child")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        for(HierarchyDatapodObject datapodObject : datapodObjects)
        {
            datapodObject.delete();
        }
        datapodObjects.clear();
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
