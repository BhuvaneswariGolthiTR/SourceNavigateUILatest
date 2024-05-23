package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class UpdateMetadataTests extends TestService
{
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    Connection connection;
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-12811 <br>
     * SUMMARY - This test verifies that the metadata of a node can be successfully changed within the 'Update Metadata' page.
     * Also verifies that after updating the metadata of the node, the node publishing status updates to Not Published status. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateMetadataSingleLegalTest()
    {
        String title = "title i";
        String codeNameToSet = "amendments to the constitution";
        String nodeTypeToSet = "SECTION";
        String keywordToSet = "RULE";
        String versionedToSet = "False";
        String codeLevelToSet = "IS_CODE_LEVEL";

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();
        String contentUUID = datapodObject.getSubtitles().get(0).getContentUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        int codeIdBefore = HierarchyDatabaseUtils.getNodeCodeId(nodeUUID, connection);
        int nodeTypeBefore = HierarchyDatabaseUtils.getNodeType(nodeUUID, connection);
        int keywordBefore = HierarchyDatabaseUtils.getNodeKeyword(nodeUUID, connection);
        int codeLevelBefore = HierarchyDatabaseUtils.getCodeLevel(nodeUUID, connection);
        int versionedBefore = HierarchyDatabaseUtils.getVersionedLevel(nodeUUID, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().setFirstDropdownUnderQuickSearchTab(title);
        hierarchySearchPage().searchNodeUuid(nodeUUID);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUUID, contentSetIowa, connection);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //Change metadata of the node
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().setCodeName(codeNameToSet);
        updateMetadataPage().setNodeType(nodeTypeToSet);
        updateMetadataPage().setKeywordDropdown(keywordToSet);
        updateMetadataPage().setCodeLevel(codeLevelToSet);
        updateMetadataPage().setVersioned(versionedToSet);
        updateMetadataPage().clickQuickLoadOk();
        boolean singleNodeUpdatedToNotPublishedAfterUpdatingMetadata = siblingMetadataPage().isSelectedNodeNotPublished();

        int codeIdAfter = HierarchyDatabaseUtils.getNodeCodeId(nodeUUID, connection);
        int nodeTypeAfter = HierarchyDatabaseUtils.getNodeType(nodeUUID, connection);
        int keywordAfter = HierarchyDatabaseUtils.getNodeKeyword(nodeUUID, connection);
        int codeLevelAfter = HierarchyDatabaseUtils.getCodeLevel(nodeUUID, connection);
        int versionedAfter = HierarchyDatabaseUtils.getVersionedLevel(nodeUUID, connection);

        //Check node metadata changed as expected
        boolean codeNameIsUnchanged = codeIdBefore == codeIdAfter;
        boolean nodeTypeIsUnchanged = nodeTypeBefore == nodeTypeAfter;
        boolean keywordIsUnchanged = keywordBefore == keywordAfter;
        boolean codeLevelIsUnchanged = codeLevelBefore == codeLevelAfter;
        boolean versionedIsUnchanged = versionedBefore == versionedAfter;

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(singleNodeUpdatedToNotPublishedAfterUpdatingMetadata, "Single node updated to Not Published status after updating metadata in Hierarchy"),
                        () -> Assertions.assertFalse(codeNameIsUnchanged, "The code name of the node did not changed when it should have"),
                        () -> Assertions.assertFalse(nodeTypeIsUnchanged, "The node type of the node did not changed when it should have"),
                        () -> Assertions.assertFalse(keywordIsUnchanged, "The keyword of the node did not changed when it should have"),
                        () -> Assertions.assertFalse(codeLevelIsUnchanged, "The code level of the node did not changed when it should have"),
                        () -> Assertions.assertFalse(versionedIsUnchanged, "The versioned of the node did not changed when it should have")
                );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that two nodes can have their metadata successfully updated at the same time <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateMetadataOnRangeLegalTest()
    {
        String value1;
        String value1NodeUuid;
        String value2;
        String value2NodeUuid;
        String codeNameToSet = "amendments to the constitution";
        String startDateToSet = "10/25/2005";
        String versionedToSet = "False";
        String endDateToSet = DateAndTimeUtils.getCurrentDateMMddyyyy();

        HierarchyDatapodConfiguration config = HierarchyDatapodConfiguration.getConfig();
        config.setChapterCount(2);
        config.setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        value1NodeUuid = datapodObject.getChapters().get(0).getNodeUUID();
        value2NodeUuid = datapodObject.getChapters().get(1).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        value1 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(value1NodeUuid, contentSetIowa, connection);
        value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(value2NodeUuid, contentSetIowa, connection);

        String[] values = {value1, value2};

        int value1CodeId = HierarchyDatabaseUtils.getNodeCodeId(value1NodeUuid, connection);
        int value1Versioned = HierarchyDatabaseUtils.getVersionedLevel(value1NodeUuid, connection);
        Date value1StartDate = HierarchyDatabaseUtils.getLegisEffectiveStartDate(value1NodeUuid, connection);
        Date value1EndDate = HierarchyDatabaseUtils.getLegisEffectiveEndDate(value1NodeUuid, connection);

        int value2CodeId = HierarchyDatabaseUtils.getNodeCodeId(value2NodeUuid, connection);
        int value2Versioned = HierarchyDatabaseUtils.getVersionedLevel(value2NodeUuid, connection);
        Date value2StartDate = HierarchyDatabaseUtils.getLegisEffectiveStartDate(value2NodeUuid, connection);
        Date value2EndDate = HierarchyDatabaseUtils.getLegisEffectiveEndDate(value2NodeUuid, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(value1NodeUuid);

        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().setCodeName(codeNameToSet);
        updateMetadataPage().enterEffectiveStartDate(startDateToSet);
        updateMetadataPage().enterEffectiveEndDate(endDateToSet);
        updateMetadataPage().setVersioned(versionedToSet);
        updateMetadataPage().clickQuickLoadOk();

        //Check first node info changed
        int value1CodeIdAfter = HierarchyDatabaseUtils.getNodeCodeId(value1NodeUuid, connection);
        int value1VersionedAfter = HierarchyDatabaseUtils.getVersionedLevel(value1NodeUuid, connection);
        Date value1StartDateAfter = HierarchyDatabaseUtils.getLegisEffectiveStartDate(value1NodeUuid, connection);
        Date value1EndDateAfter = HierarchyDatabaseUtils.getLegisEffectiveEndDate(value1NodeUuid, connection);

        //Check second node info changed
        int value2CodeIdAfter = HierarchyDatabaseUtils.getNodeCodeId(value2NodeUuid, connection);
        int value2VersionedAfter = HierarchyDatabaseUtils.getVersionedLevel(value2NodeUuid, connection);
        Date value2StartDateAfter = HierarchyDatabaseUtils.getLegisEffectiveStartDate(value2NodeUuid, connection);
        Date value2EndDateAfter = HierarchyDatabaseUtils.getLegisEffectiveEndDate(value2NodeUuid, connection);

        //Check to see that all info was update between both nodes
        boolean codeNameIsUnchanged = value1CodeId == value1CodeIdAfter;
        boolean codeNameIsUnchanged2 = value2CodeId == value2CodeIdAfter;
        boolean startDateIsUnchanged = value1StartDate.equals(value1StartDateAfter);
        boolean startDateIsUnchanged2 = value2StartDate.equals(value2StartDateAfter);
        boolean endDateHasChanged = value1EndDate == null && value1EndDateAfter != null;
        boolean endDateHasChanged2 = value2EndDate == null && value2EndDateAfter != null;
        boolean versionedIsUnchanged = value1Versioned == value1VersionedAfter;
        boolean versionedIsUnchanged2 = value2Versioned == value2VersionedAfter;

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(codeNameIsUnchanged, "The code name of the node did not update when it should have"),
                        () -> Assertions.assertFalse(codeNameIsUnchanged2, "The code name of the node did not update when it should have"),
                        () -> Assertions.assertFalse(startDateIsUnchanged, "The start date of the node did not update when it should have"),
                        () -> Assertions.assertFalse(startDateIsUnchanged2, "The start date of the node did not update when it should have"),
                        () -> Assertions.assertTrue(endDateHasChanged, "The end date of the node did not update when it should have"),
                        () -> Assertions.assertTrue(endDateHasChanged2, "The end date of the node did not update when it should have"),
                        () -> Assertions.assertFalse(versionedIsUnchanged, "The versioned of the node did not update when it should have"),
                        () -> Assertions.assertFalse(versionedIsUnchanged2, "The versioned of the node did not update when it should have")
                );
    }

    /**
     * STORY/BUG - HALCYONST-12811 <br>
     * SUMMARY - This test verifies that updating the metadata of two nodes at one time can be successfully done and verified in the sibling metadata pane.
     * Also verifies that the updates made to the multiple nodes update each nodes status to Not Published <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateMetadataEffectiveDatesOnMultipleNodesLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeUuid1 = datapodObject.getSections().get(0).getNodeUUID();
        String contentUUID1 = datapodObject.getSections().get(0).getContentUUID();
        String nodeUuid2 = datapodObject.getSections().get(1).getNodeUUID();
        String contentUUID2 = datapodObject.getSections().get(1).getContentUUID();
        String value1 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid1, contentSetIowa, connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, contentSetIowa, connection);
        String[] values = {value1, value2};
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Get start and end date of first node
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        String startDate1Before = siblingMetadataPage().getSelectedNodeStartDate();
        String endDate1Before = siblingMetadataPage().getEndDateOfSelectedNode();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())//TODO Refactor this after Sarah implements method to handle updating nodes with publishing status of ERROR
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUUID1, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //Get start and end date of second node
        hierarchySearchPage().searchNodeUuid(nodeUuid2);
        String startDate2Before = siblingMetadataPage().getSelectedNodeStartDate();
        String endDate2Before = siblingMetadataPage().getEndDateOfSelectedNode();

        if (!siblingMetadataPage().isSelectedNodeNotPublished())//TODO Refactor this after Sarah implements method to handle updating nodes with publishing status of ERROR
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUUID2, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        //Set both nodes' start and end date to today's date
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().enterEffectiveStartDate(date);
        updateMetadataPage().enterEffectiveEndDate(date);
        updateMetadataPage().clickQuickLoadOk();

        //Check that first node was updated and publishing status updated to Not Published
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        String startDate1Middle = siblingMetadataPage().getSelectedNodeStartDate();
        String endDate1Middle = siblingMetadataPage().getEndDateOfSelectedNode();
        boolean node1UpdatedToNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        //Check that second node was updated and publishing status updated to Not Published
        hierarchySearchPage().searchNodeUuid(nodeUuid2);
        String startDate2Middle = siblingMetadataPage().getSelectedNodeStartDate();
        String endDate2Middle = siblingMetadataPage().getEndDateOfSelectedNode();
        boolean node2UpdatedToNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        boolean startDateIsUnchanged1 = startDate1Before.equals(startDate1Middle);
        boolean startDateIsUnchanged2 = startDate2Before.equals(startDate2Middle);
        boolean endDateIsUnchanged1 = endDate1Before.equals(endDate1Middle);
        boolean endDateIsUnchanged2 = endDate2Before.equals(endDate2Middle);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(node1UpdatedToNotPublishedStatus, "Node 1 updated to Not Published status after making changes via Update Metadata"),
                        () -> Assertions.assertTrue(node2UpdatedToNotPublishedStatus, "Node 2 updated to Not Published status after making changes via Update Metadata"),
                        () -> Assertions.assertFalse(startDateIsUnchanged1, "The first node's start date did not change when it should have"),
                        () -> Assertions.assertFalse(startDateIsUnchanged2, "The second node's start date did not change when it should have"),
                        () -> Assertions.assertFalse(endDateIsUnchanged1, "The first node's end date did not change when it should have"),
                        () -> Assertions.assertFalse(endDateIsUnchanged2, "The second node's end date did not change when it should have")
                );
    }

    /**
     * STORY/BUG HALCYONST - 2708 <br>
     * SUMMARY - This test attempts to update the metadata of 3 nodes and clicks Quick-Load and Update without updating other fields to the sibling metadata,
     * verifying the resulting alert: "Changes or selection not made. Make change and check box next to metadata being updated." <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleSelectedNoChangeMadeAlertLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String node1UUID = datapodObject.getSections().get(0).getNodeUUID();
        String node2UUID = datapodObject.getSections().get(1).getNodeUUID();
        String node3UUID = datapodObject.getSections().get(2).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String node1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node1UUID, contentSetIowa, connection);
        String node2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node2UUID, contentSetIowa, connection);
        String node3Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node3UUID, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Searching for and selecting the three nodes we need.
        hierarchySearchPage().searchNodeUuid(node1UUID);
        siblingMetadataPage().selectNodes(node1Value, node2Value, node3Value);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();

        //Going to Update Metadata Multiple Page.
        boolean updateMetadataMultiplePageAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataMultiplePageAppeared, "The Update Metadata Page for multiple selected nodes did NOT appear");

        //Clicking QuickLoad and update without making any changes.
        updateMetadataMultiplePage().clickQuickLoad();
        updateMetadataMultiplePage().clickUpdate(true);
        AutoITUtils.verifyAlertTextAndAccept(true, "Changes or selection not made. Make change and check box next to metadata being updated.");
        updateMetadataMultiplePage().clickCancel();
    }

    /**
     * STORY/BUG HALCYONST - 2708 <br>
     * SUMMARY - This test selects 3 nodes and attempts to set the effective start date and click Quick-Load and Update without checking the Effective State Date Check Box <br>
     * Then it verifies the resulting alert: "Changes or selection not made. Make change and check box next to metadata being updated." <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleSelectedSetEffectiveStartDateNoCheckBoxAlertLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String keyword = "=";
        String nodeUuid1 = datapodObject.getSections().get(0).getNodeUUID();
        String nodeUuid2 = datapodObject.getSections().get(1).getNodeUUID();
        String nodeUuid3 = datapodObject.getSections().get(2).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String node1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid1, contentSetIowa, connection);
        String node2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, contentSetIowa, connection);
        String node3Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid3, contentSetIowa, connection);
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Searching for and selecting the three nodes we need.
        hierarchySearchPage().quickSearch(keyword, node1Value);
        siblingMetadataPage().selectNodes(node1Value, node2Value, node3Value);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();

        //Going to Update Metadata Multiple Page.
        boolean updateMetadataMultiplePageAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataMultiplePageAppeared, "The Update Metadata Page for multiple selected nodes did NOT appear");

        //Checking the Effective Start Date Checkbox without setting an effective start date and clicking QuickLoad update to verify the resulting alert.
        updateMetadataMultiplePage().setEffectiveStartDate(currentDate, false);
        updateMetadataMultiplePage().clickQuickLoad();
        updateMetadataMultiplePage().clickUpdate(true);
        AutoITUtils.verifyAlertTextAndAccept(true, "Changes or selection not made. Make change and check box next to metadata being updated.");
    }

    /**
     * STORY/BUG HALCYONST - 2708 <br>
     * SUMMARY - This test sets the effective start date for 3 nodes and then clicks cancel and verifies that no changes are made <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleSelectedSetEffectiveStartDateAndCancelLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String node1UUID = datapodObject.getSections().get(0).getNodeUUID();
        String node2UUID = datapodObject.getSections().get(1).getNodeUUID();
        String node3UUID = datapodObject.getSections().get(2).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String node1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node1UUID, contentSetIowa, connection);
        String node2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node2UUID, contentSetIowa, connection);
        String node3Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(node3UUID, contentSetIowa, connection);

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Searching for and selecting the three nodes we need for this test and saving their initial Start Date values.
        hierarchySearchPage().searchNodeUuid(node1UUID);
        String node1EffectiveStartDate = siblingMetadataPage().getSelectedNodeStartDate();

        hierarchySearchPage().searchNodeUuid(node2UUID);
        String node2EffectiveStartDate = siblingMetadataPage().getSelectedNodeStartDate();

        hierarchySearchPage().searchNodeUuid(node3UUID);
        String node3EffectiveStartDate = siblingMetadataPage().getSelectedNodeStartDate();

        //Going to Update Metadata Multiple Page.
        siblingMetadataPage().selectNodes(node1Value, node2Value, node3Value);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        boolean updateMetadataMultiplePageAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataMultiplePageAppeared, "The Update Metadata Page for multiple selected nodes did NOT appear");

        //Checking the Effective Start Date Checkbox and setting an effective start date and clicking QuickLoad cancel.
        updateMetadataMultiplePage().setEffectiveStartDate(currentDate, true);
        updateMetadataMultiplePage().clickCancel();

        //checking that the start dates of each node were not changed
        hierarchySearchPage().searchNodeUuid(node1UUID);
        boolean node1DateDidNotChange = node1EffectiveStartDate.equals(siblingMetadataPage().getSelectedNodeStartDate());
        hierarchySearchPage().searchNodeUuid(node2UUID);
        boolean node2DateDidNotChange = node2EffectiveStartDate.equals(siblingMetadataPage().getSelectedNodeStartDate());
        hierarchySearchPage().searchNodeUuid(node3UUID);
        boolean node3DateDidNotChange = node3EffectiveStartDate.equals(siblingMetadataPage().getSelectedNodeStartDate());

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(node1DateDidNotChange, "Node 1's Effective Start Date changed when we did not expect it to change."),
                        () -> Assertions.assertTrue(node2DateDidNotChange, "Node 2's Effective Start Date changed when we did not expect it to change."),
                        () -> Assertions.assertTrue(node3DateDidNotChange, "Node 3's Effective Start Date changed when we did not expect it to change.")
                );
    }


    /**
     * STORY/BUG HALCYONST - 2708 <br>
     * SUMMARY - This test takes 3 nodes and updates some of their metadata values and verifies that only the changed metadata values are updated correctly in the Grid<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleSelectedUpdateValuesAndVerifyDataUpdateLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String contentSet = contentSetIowa;

        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String node1UUID = datapodObject.getSections().get(0).getNodeUUID();
        String node2UUID = datapodObject.getSections().get(1).getNodeUUID();
        String node3UUID = datapodObject.getSections().get(2).getNodeUUID();

        String[] nodeUuids = {node1UUID, node2UUID, node3UUID};
        String node1Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuids[0], contentSet, connection);
        String node2Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuids[1], contentSet, connection);
        String node3Value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuids[2], contentSet, connection);
        String keyword = "=";

        HashMap<String, String> node1Metadata = null, node2Metadata = null, node3Metadata = null;
        String[] metadataUpdates = {"territorial government", "False", "IS_CODE_LEVEL"};

        int i = 0;
        HashMap[] nodes = {node1Metadata, node2Metadata, node3Metadata};
        //Setting up hashmap array to store metadata about each node
        while (i < 3)
        {
            nodes[i] = new HashMap<>();
            nodes[i].put("Code Name", HierarchyDatabaseUtils.getCodeNameAndID(nodeUuids[i], connection).get(1));
            nodes[i].put("Code ID", HierarchyDatabaseUtils.getCodeNameAndID(nodeUuids[i], connection).get(0));
            nodes[i].put("Effective Start Date", HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeUuids[i], connection));
            nodes[i].put("Effective End Date", HierarchyDatabaseUtils.getLegisEffectiveEndDate(nodeUuids[i], connection));
            nodes[i].put("Versioned", HierarchyDatabaseUtils.getVersionedLevel(nodeUuids[i], connection));
            nodes[i].put("Code Level", HierarchyDatabaseUtils.getCodeLevel(nodeUuids[i], connection));
            nodes[i].put("Keyword", HierarchyDatabaseUtils.getNodeKeyword(nodeUuids[i], connection));
            nodes[i].put("Status", HierarchyDatabaseUtils.getNodeLegacyStatusCode(nodeUuids[i], connection));
            i++;
        }
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Searching for and selecting the three nodes we need for this test and saving their initial Start Date values.
        hierarchySearchPage().quickSearch(keyword, node1Value);

        //Verifying that the nodes are clean for the current run of the test. If not manually clean them
        for (String uuid : nodeUuids)
        {
            Assertions.assertTrue(HierarchyDatabaseUtils.getNodeValidationFlag(uuid, connection).equals("0"), String.format("Node uuid %s did not have a green checkmark", uuid));
        }

        //Selecting all 3 nodes and updating specific metadata
        siblingMetadataPage().selectNodes(node1Value, node2Value, node3Value);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();

        //Going to Update Metadata Multiple Page
        boolean updateMetadataMultiplePageAppeared = siblingMetadataContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataMultiplePageAppeared, "The Update Metadata Page for multiple selected nodes did NOT appear");

        updateMetadataMultiplePage().updateCodeName(metadataUpdates[0], true);
        updateMetadataMultiplePage().updateVersioned(metadataUpdates[1], true);
        updateMetadataMultiplePage().updateCodeLevel(metadataUpdates[2], true);
        updateMetadataMultiplePage().clickQuickLoad();
        updateMetadataMultiplePage().clickUpdate(false);

        //Verifying that changed content updated correct, and unchanged content remains the same
        while (i < 3)
        {
            Assertions.assertNotEquals(nodes[i].get("Code Name"), HierarchyDatabaseUtils.getCodeNameAndID(nodeUuids[i], connection).get(1));
            Assertions.assertNotEquals(nodes[i].get("Code ID"), HierarchyDatabaseUtils.getCodeNameAndID(nodeUuids[i], connection).get(0));
            Assertions.assertEquals(nodes[i].get("Effective Start Date"), HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeUuids[i], connection));
            Assertions.assertEquals(nodes[i].get("Effective End Date"), HierarchyDatabaseUtils.getLegisEffectiveEndDate(nodeUuids[i], connection));
            Assertions.assertNotEquals(nodes[i].get("Versioned"), HierarchyDatabaseUtils.getVersionedLevel(nodeUuids[i], connection));
            Assertions.assertNotEquals(nodes[i].get("Code Level"), HierarchyDatabaseUtils.getCodeLevel(nodeUuids[i], connection));
            Assertions.assertEquals(nodes[i].get("Keyword"), HierarchyDatabaseUtils.getNodeKeyword(nodeUuids[i], connection));
            Assertions.assertEquals(nodes[i].get("Status"), HierarchyDatabaseUtils.getNodeLegacyStatusCode(nodeUuids[i], connection));
            i++;
        }
        i = 0;
        //
        for (String uuid : nodeUuids)
        {
            Assertions.assertTrue(HierarchyDatabaseUtils.getNodeValidationFlag(uuid, connection).equals("8796093022208"), String.format("Node uuid %s did not have a red X", uuid));
        }
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            disconnect(connection);
        }
    }
}