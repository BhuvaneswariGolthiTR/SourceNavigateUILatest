package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RepealPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HierarchyFunctionsTests extends TestService
{
    Connection connection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after changing a nodes status to Repeal, that it can be added with another node
     * to the range and display the correct status and range symbol afterwards. Then after removing the nodes form the range, <br>
     * the test checks that the status and range symbol were changed <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addRemoveRangeLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String nodeUUID2 = datapodObject.getSections().get(1).getNodeUUID();
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID2, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String[] values = {value, value2};

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Set node status to Repeal
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().setStatusTo("Repeal");
        updateMetadataPage().clickQuickLoadOk();

        //Add the two nodes to range
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsAddToRange();
        addToRangePage().clickQuickLoadOk();

        //Check that node is part of range and status is Repeal
        hierarchySearchPage().quickSearch("=", value);
        boolean nodeIsStartOfRange = siblingMetadataPage().isSelectedNodeTheStartOfARange();
        boolean nodeStatusIsSetToRepeal = siblingMetadataPage().isSelectedRowStatusRepeal();

        //Check that node is part of range and status is Repeal
        hierarchySearchPage().quickSearch("=", value2);
        boolean nodeIsInRange = siblingMetadataPage().isSelectedNodeInARange();
        boolean nodeStatusIsSetToRepeal2 = siblingMetadataPage().isSelectedRowStatusRepeal();

        //Remove nodes from range
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsRemoveFromRange();
        removeFromRangePage().clickQuickLoadOk();

        //Check that node is not a part of the range and status is not Repeal
        hierarchySearchPage().quickSearch("=", value);
        boolean nodeIsStartOfRange2 = siblingMetadataPage().isSelectedNodeTheStartOfARange();
        boolean nodeStatusIsSetToRepeal3 = siblingMetadataPage().isSelectedRowStatusRepeal();

        //Check that node is not a part of range and status is not Repeal
        hierarchySearchPage().quickSearch("=", value2);
        boolean nodeIsInRange3 = siblingMetadataPage().isSelectedNodeInARange();
        boolean nodeStatusIsSetToRepeal4 = siblingMetadataPage().isSelectedRowStatusRepeal();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(nodeIsStartOfRange, "The node is not the starting node of the range when it should be"),
              () -> Assertions.assertTrue(nodeStatusIsSetToRepeal, "The node's status is not set to Repeal when it should be"),
              () -> Assertions.assertTrue(nodeIsInRange, "The node is not a part of the range when it should be"),
              () -> Assertions.assertTrue(nodeStatusIsSetToRepeal2, "The node's status is not set to Repeal when it should be"),
              () -> Assertions.assertFalse(nodeIsStartOfRange2, "The node is the starting node of the range when it should not be"),
              () -> Assertions.assertFalse(nodeStatusIsSetToRepeal3, "The node's status is set to Repeal when it should not be"),
              () -> Assertions.assertFalse(nodeIsInRange3, "The node is a part of the range when it should not be"),
              () -> Assertions.assertFalse(nodeStatusIsSetToRepeal4, "The node's status is set to Repeal when it should not be")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that repealing multiple nodes at a time through the
     * hierarchy functions will correctly update the nodes statuses to Repeal <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void repealMultipleNodesLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionOneNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String sectionTwoNodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        String parentNodeUUID = datapodObject.getChapters().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionOneNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionTwoNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String[] values = {value, value2};

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(parentNodeUUID);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(parentNodeUUID, connection), contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        hierarchySearchPage().searchNodeUuid(sectionOneNodeUUID);
        Assertions.assertFalse(siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag(), "The selected sibling metadata already had an Error Validation Flag before it was repealed. This needs to be fixed before rerunning the test");
        HierarchyDatabaseUtils.setNodeToLiveStatus(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionOneNodeUUID, connection), contentSetIowa, connection);

        hierarchySearchPage().searchNodeUuid(sectionTwoNodeUUID);
        Assertions.assertFalse(siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag(), "The selected sibling metadata already had an Error Validation Flag before it was repealed. This needs to be fixed before rerunning the test");
        HierarchyDatabaseUtils.setNodeToLiveStatus(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionTwoNodeUUID, connection), contentSetIowa, connection);

        PublishingDatabaseUtils.setPublishingNodeToReady(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionOneNodeUUID, connection), contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToReady(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionTwoNodeUUID, connection), contentSetIowa, connection);

        //Repeal both nodes
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsRepeal();
        repealPage().setCurrentDateAsEffectiveStartDate();

        String yesterdaysDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();

        repealPage().click(RepealPageElements.quickLoadTrackingButton);
        repealPage().waitForPageLoaded();
        repealPage().click(RepealPageElements.okButton);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.", sectionOneNodeUUID, yesterdaysDate));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");
        repealPage().waitForWindowGoneByTitle(RepealPageElements.REPEAL_PAGE_TITLE);
        repealPage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
        repealPage().waitForPageLoaded();
        repealPage().waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);

        hierarchyTreePage().setNavigationTreeToCurrentDate();

        //Check that both nodes were repealed
        hierarchySearchPage().quickSearch("=", value);
        boolean nodeStatusIsSetToRepeal = siblingMetadataPage().isSelectedRowStatusRepeal();
        boolean nodePublishingStatusIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean nodeHasErrorFlag = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();

        hierarchySearchPage().quickSearch("=", value2);
        boolean nodeStatusIsSetToRepeal2 = siblingMetadataPage().isSelectedRowStatusRepeal();
        boolean nodePublishingStatusIsNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean nodeHasErrorFlag2 = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();


        hierarchySearchPage().searchNodeUuid(parentNodeUUID);
        boolean parentNodeRemainsLoadedToWestlawStatus = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(nodeStatusIsSetToRepeal, "The node status should be set to Repeal but was not"),
              () -> Assertions.assertTrue(nodePublishingStatusIsNotPublished, "The node publishing status of the first value should be set to Not Published but was not"),
              () -> Assertions.assertTrue(nodeStatusIsSetToRepeal2, "The node status should be set to Repeal but was not"),
              () -> Assertions.assertTrue(nodeHasErrorFlag, "The node did not have an error flag when an error flag was expected"),
              () -> Assertions.assertTrue(nodePublishingStatusIsNotPublished2, "The node publishing status of the second value should be set to Not Published but was not"),
              () -> Assertions.assertTrue(nodeHasErrorFlag2, "The node did not have an error flag when an error flag was expected."),
              () -> Assertions.assertTrue(parentNodeRemainsLoadedToWestlawStatus, "Parent node was suppose to stay loaded to westlaw after Clone After but did not")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that reserving multiple nodes at a time through the
     * hierarchy functions will correctly update the nodes statuses to Reserve <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reserveMultipleNodesLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionOneNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String sectionTwoNodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionOneNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionTwoNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String[] values = {value, value2};

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Set first node to status Live
        hierarchySearchPage().quickSearch("=", value);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionOneNodeUUID, connection), contentSetIowa, connection);
        HierarchyDatabaseUtils.setNodeToLiveStatus(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionOneNodeUUID, connection), contentSetIowa, connection);

        //Set second node to status Live
        hierarchySearchPage().quickSearch("=", value2);

        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionTwoNodeUUID, connection), contentSetIowa, connection);
        HierarchyDatabaseUtils.setNodeToLiveStatus(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(sectionTwoNodeUUID, connection), contentSetIowa, connection);

        //Reserve both nodes
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsReserve();
        reservePage().setCurrentDateAsEffectiveStartDate();
        reservePage().clickQuickLoadOk();

        //Check that both nodes were reserved
        hierarchySearchPage().quickSearch("=", value);
        boolean nodeStatusIsSetToReserve = siblingMetadataPage().isSelectedRowStatusReserve();
        boolean nodePublishingStatusIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchySearchPage().quickSearch("=", value2);
        boolean nodeStatusIsSetToReserve2 = siblingMetadataPage().isSelectedRowStatusReserve();
        boolean nodePublishingStatusIsNotPublished2 = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(nodeStatusIsSetToReserve2, "The node status should be set to Reserve but was not"),
              () -> Assertions.assertTrue(nodePublishingStatusIsNotPublished2, "The node publishing status should be set to Not Published but was not"),
              () -> Assertions.assertTrue(nodeStatusIsSetToReserve, "The node status should be set to Reserve but was not"),
              () -> Assertions.assertTrue(nodePublishingStatusIsNotPublished, "The node publishing status should be set to Not Published but was not")
        );
    }

    /**
     * STORY/BUG - HALCYONST-733 <br>
     * SUMMARY - This test verifies that having a node go through the
     * hierarchy functions -> reuse, will correctly update the nodes status to Live <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reuseLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Set first node to status Repeal
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().setStatusTo("Repeal");
        updateMetadataPage().clickQuickLoadOk();

        //Hierarchy Functions->Reuse
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsReuse();
        reusePage().setCurrentDateAsEffectiveStartDate();
        reusePage().clickQuickLoadOk();

        //Check that node status is now Live
        hierarchySearchPage().quickSearch("=", value);

        boolean nodeStatusIsSetToLive = siblingMetadataPage().isSelectedRowStatusLive();
        boolean nodePublishingStatusIsErrorStatus = siblingMetadataPage().isSelectedNodeErrorStatus();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(nodeStatusIsSetToLive, "The node status should be set to Live but was not"),
              () -> Assertions.assertTrue(nodePublishingStatusIsErrorStatus, "The node publishing status should be set to ERROR but was not")
        );
    }

    /**
     * STORY/BUG - HALCYONST-3126 <br>
     * SUMMARY - This test verifies that a node can be successfully changed to a new value and correctly displayed in the sibling metadata pane <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changeVolumeNumberLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String parentNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(parentNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String newVolumeNumber = "0050";

        String expectedErrorMessage = "Invalid volume number. Check current and previous node.";
        boolean isChildUpdated;
        boolean isChildValidationFlagCorrect;
        String childVolumeNumber;
        String childValidationFlagStatus;

        ArrayList<String> descendantsList = HierarchyDatabaseUtils.getDescendentsListFromParentNode(parentNodeUUID, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Set a new Vols number to the node
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsChangeVolumeNumberDescendants();
        changeVolumeNumberDescendantsPage().setVolumesDropdownTo(newVolumeNumber);
        changeVolumeNumberDescendantsPage().clickQuickLoadOk();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the change was successful
        boolean volsNumberIsCorrect = siblingMetadataPage().isSelectedNodeVolsNumberEqualTo(newVolumeNumber);
        boolean isNodeInvalid = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();
        Assertions.assertTrue(volsNumberIsCorrect, "The node's Vols number was not updated correctly");
        Assertions.assertTrue(isNodeInvalid, "The designated node did not have an error flag when it should have");

        //verifying validation errors:
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().validationFlagsCheckNodeValidationFlags();
        String actualErrorMessage = validationFlagsReportPopupPage().getErrorValueForGivenNode(parentNodeUUID);
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "The expected error was not present for the selected node");
        validationFlagsReportPopupPage().clickClose();

        //Getting ALL children from given starting point

        //Verifying that all children were updated to the new volumeNumber and that they have a green checkmark in the validation flag column
        for (String descendant : descendantsList)
        {
            if (!descendant.equals(parentNodeUUID))
            {
                childVolumeNumber = HierarchyDatabaseUtils.getNodeVolumeWithNodeUuid(descendant, contentSetIowa, connection);
                childValidationFlagStatus = HierarchyDatabaseUtils.getNodeValidationFlag(descendant, connection);
                isChildUpdated = childVolumeNumber.equals(newVolumeNumber);
                isChildValidationFlagCorrect = childValidationFlagStatus.equals("0");
                Assertions.assertTrue(isChildUpdated, String.format("Child %s was not updated with the correct volume number. \nExpected volume number: %s \n Actual volume number: %s", descendant, newVolumeNumber, childVolumeNumber));
                Assertions.assertTrue(isChildValidationFlagCorrect, String.format("Child %s did not have the expected validation flag. \nExpected: 0%nActual: %s", descendant, childValidationFlagStatus));
            }
        }
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the cloned node shows before the original node and has the correct value and start date. <br>
     * This test also verifies that the original node's value and start date never changes through the process of cloning the node and deleting the clone node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cloneBeforeLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Clone the node
        hierarchySearchPage().quickSearch("=", value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsCloneBefore();
        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that clone node appears
        List<WebElement> nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(value);
        boolean nodeCloned = nodeList.size() == 2;
        Assertions.assertTrue(nodeCloned, "2 nodes with the same value should be displayed");

        //Check that clone node value and start date are correct
        nodeList.get(0).click();
        String cloneValue = siblingMetadataPage().getSelectedNodeValue();
        String cloneStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean cloneValueIsCorrect = cloneValue.equals(value);
        boolean cloneStartDateIsCorrect = cloneStartDate.equals("no date");

        //Check that original node value and start date are correct
        nodeList.get(1).click();
        String originalValue = siblingMetadataPage().getSelectedNodeValue();
        String originalStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean originalValueIsCorrect = originalValue.equals(value);
        boolean originalStartDateIsSetToNoDate = originalStartDate.equals("no date");

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cloneValueIsCorrect, "The cloned node does not display the correct value"),
              () -> Assertions.assertTrue(cloneStartDateIsCorrect, "The cloned node does not display the correct start date"),
              () -> Assertions.assertTrue(originalValueIsCorrect, "The original node does not display the correct value"),
              () -> Assertions.assertFalse(originalStartDateIsSetToNoDate, "The original node does not display the correct start date")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the cloned node shows after the original node and has the correct value and start date.
     * This test also verifies that the original node's value and start date never changes through the process of cloning the node and deleting the clone node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cloneAfterLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String chapterNodeUUID = datapodObject.getChapters().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String parentValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(chapterNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String contentUuidOfParentValue = datapodObject.getChapters().get(0).getContentUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Clone the node
        hierarchySearchPage().quickSearch("=", value);


        if (!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuidOfParentValue, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsCloneAfter();
        hierarchySetLawTrackingPage().quickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        boolean clonedNodePublishingStatusIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(clonedNodePublishingStatusIsNotPublished, "The cloned nodes publishing status should be not published but is not");

        hierarchySearchPage().quickSearch("CHAPTER", parentValue);

        boolean parentNodeRemainsLoadedToWestlawStatus = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(parentNodeRemainsLoadedToWestlawStatus, "Parent node was suppose to stay loaded to westlaw after Clone After but did not");

        hierarchySearchPage().searchNodeUuid(sectionNodeUUID);

        //Check that clone node appears
        List<WebElement> nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(value);
        boolean nodeCloned = nodeList.size() == 2;
        Assertions.assertTrue(nodeCloned, "2 nodes with the same value should be displayed");

        //Check that clone node value and start date are correct
        nodeList.get(1).click();
        String cloneValue = siblingMetadataPage().getSelectedNodeValue();
        String cloneStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean cloneValueIsCorrect = cloneValue.equals(value);
        boolean cloneStartDateIsCorrect = cloneStartDate.equals("no date");
        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cloneValueIsCorrect, "The cloned node does not display the correct value"),
              () -> Assertions.assertTrue(cloneStartDateIsCorrect, "The cloned node does not display the correct start date")
        );

        //Check that original node value and start date are correct
        nodeList.get(0).click();
        String originalValue = siblingMetadataPage().getSelectedNodeValue();
        String originalStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        boolean originalValueIsCorrect = originalValue.equals(value);
        boolean originalStartDateIsSetToNoDate = originalStartDate.equals("no date");
        Assertions.assertAll
        (
              () -> Assertions.assertTrue(originalValueIsCorrect, "The original node does not display the correct value"),
              () -> Assertions.assertFalse(originalStartDateIsSetToNoDate, "The original node does not display the correct start date")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after a node is deep cloned, that the correct number of nodes are shown in the sibling metadata and Disp/Deriv pane. <br>
     * The test also checks to see that the deep clone node was successfully deleted. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deepCloneLegalTest()
    {

        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String chapterNodeUUID = datapodObject.getChapters().get(0).getNodeUUID();
        String sectionNodeUUID1 = datapodObject.getSections().get(0).getNodeUUID();
        String sectionNodeUUID2 = datapodObject.getSections().get(1).getNodeUUID();
        String yesterdaysDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(sectionNodeUUID2);
        String sectionNodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        boolean nodeIsNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(nodeIsNotPublished, "Node should have not published status after setting it.");

        hierarchySearchPage().searchNodeUuid(sectionNodeUUID1);
        String sectionNodeWithDescendantsValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        nodeIsNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(nodeIsNotPublished, "Node should have not published status after setting it.");

        //Set effective start date to today and Deep Clone node
        hierarchySearchPage().searchNodeUuid(chapterNodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsDeepClone();
        deepClonePage().setCurrentDateAsEffectiveStartDate();
        deepClonePage().clickQuickLoadOk();
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation " +
                        "filter date. Your navigation date has been reset to %s.", chapterNodeUUID, yesterdaysDate));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");

        //Check that deep cloned node is displayed in sibling metadata and Disp/Deriv panes
        hierarchyNavigatePage().switchToHierarchyEditPage();

        String chapterNodeValue = siblingMetadataPage().getSelectedNodeValue();

        List<WebElement> nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(chapterNodeValue);
        boolean nodeDeepCloned = nodeList.size() == 2;
        boolean multipleNodesAreShown = dispositionDerivationPage().areMultipleNodesShown();
        Assertions.assertAll
        (
              () -> Assertions.assertTrue(nodeDeepCloned, "2 nodes with the same value should be displayed"),
              () -> Assertions.assertTrue(multipleNodesAreShown, "2 nodes with the same value should be displayed in the Disp/Deriv pane")
        );

        nodeList.get(1).click();
        boolean clonedGradeNodeIsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        String clonedGradeNodeUuid = siblingMetadataPage().getSelectedNodeUuid();

        hierarchySearchPage().searchNodeUuid(clonedGradeNodeUuid);
        String clonedGradeNodeTreeValue = hierarchyTreePage().getSelectedTreeNodeValue();

        hierarchyTreePage().clickExpandButtonNextToGivenValue(clonedGradeNodeTreeValue);
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(sectionNodeValue);
        boolean clonedSectionNodeIsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchyTreePage().clickOnTreeNodeWithGivenValue(sectionNodeWithDescendantsValue);
        boolean clonedSectionNodeWithDescendantsIsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(clonedGradeNodeIsNotPublishedStatus, "Cloned grade node should be not published status after performing a deep clone but it is not"),
              () -> Assertions.assertTrue(clonedSectionNodeIsNotPublishedStatus, "Cloned section node should be not published status after performing a deep clone but it is not"),
              () -> Assertions.assertTrue(clonedSectionNodeWithDescendantsIsNotPublishedStatus, "Cloned section node with descendants should be not published status after performing a deep clone but it is not")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after updating the node's alternative cite, that the node can be found using the added cite <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateAltCiteLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String altCiteKeyword = "RULE";
        String altCiteValue = "Test_Rule";

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //Set default keyword and value to node cite
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsUpdateAlternateCite();
        updateAlternativeCitePage().setDefaultAlternateCiteKeyword(altCiteKeyword);
        updateAlternativeCitePage().setDefaultAlternateCiteValue(altCiteValue);
        updateAlternativeCitePage().clickUpdate();

        //Check that node value was left unchanged
        hierarchySearchPage().quickSearch(altCiteKeyword, altCiteValue);
        String testValue = siblingMetadataPage().getSelectedNodeValue();
        boolean nodeValueUnchanged = testValue.equals(value);

        Assertions.assertTrue(nodeValueUnchanged, "The node value changed when it should not have");
    }

    /**
     * STORY: Halcyonst- 8222 <br>
     * SUMMARY: When a repeal is executed using the "process as repealed range" option, the nodes created during the processing of the range
     * repeal all receive an ERROR status instead of a Not Published Status <br>
     * USER: legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editRepealRangeFunctionCreatesNodesWithErrorStatus()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid,contentSetId, connection);

        String nodeUuid2 = datapodObject.getSections().get(1).getNodeUUID();
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, contentSetId, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //select it and its next sibling
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().selectNodes(nodeValue, nodeValue2);

        //right click two nodes: hierarchy function -> repeal
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsRepeal();

        //in repeal window:
        repealPage().setCurrentDateAsEffectiveStartDate();
        repealPage().clickShareNodsForward();
        repealPage().clickProcessAsRepealedRange();
        repealPage().clickRunPubTagRefreshNow();
        repealPage().click(RepealPageElements.quickLoadTrackingButton);
        repealPage().waitForPageLoaded();
        repealPage().click(RepealPageElements.okButton);
        String yesterdaysDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.", nodeUuid, yesterdaysDate));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");
        repealPage().waitForWindowGoneByTitle(RepealPageElements.REPEAL_PAGE_TITLE);
        repealPage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
        repealPage().waitForPageLoaded();
        repealPage().waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);

        hierarchyTreePage().setNavigationTreeToCurrentDate();

        //get repeal status of new Node A
        siblingMetadataPage().selectNodes(nodeValue);
        boolean isStatusRepealNewNodeA = siblingMetadataPage().isSelectedRowStatusRepeal();

        //get repeal status of new Node B
        siblingMetadataPage().selectSelectedNodesNextNode();
        boolean isStatusRepealNewNodeB = siblingMetadataPage().isSelectedRowStatusRepeal();

        //verify status of not published for children
        hierarchyTreePage().clickExpandButtonNextToGivenValue(nodeValue);

        //selects second Child
        siblingMetadataPage().selectNodesByValue(nodeValue);
        boolean nodeChild3StatusISNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().selectSelectedNodesNextNode();
        boolean nodeChild4StatusIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
              () -> assertTrue(isStatusRepealNewNodeA, "is status of New Range Node A Repeal"),
              () -> assertTrue(isStatusRepealNewNodeB, "is status of New Range Node B Repeal"),
              () -> assertTrue(nodeChild3StatusISNotPublished, "Child 1 is set to not published"),
              () -> assertTrue(nodeChild4StatusIsNotPublished, "Child 2 is set to not published")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that Change End Date (descendants) changes the end date for the parent and child nodes <br>
     * USER - CITELINE <br>
     */
    @Test
    @IE_EDGE_MODE
    @CITELINE
    @LOG
    public void changeEndDateDescendantsSwatTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String[] nodeArray =
                {
                        //Child Nodes
                        datapodObject.getSections().get(0).getNodeUUID(),
                        datapodObject.getSections().get(1).getNodeUUID(),
                        datapodObject.getSections().get(2).getNodeUUID(),
                        //Parent Node
                        datapodObject.getChapters().get(0).getNodeUUID()
                };

        /*  Fun stuff so we can run this test all year!
        Unfortunately dates are displayed differently between the hierarchy metadata pane and the hierarchy disposition derivation pane.
        In the metadata pane dates are in the form of 12/01/2021 where days with a date under 10 are displayed with a leading zero.
        In the disposition derivation pane the dates are in form of 12/1/2021 where days that have a date under 10 are not displayed with a leading zero.
        */

        //This is the current date value we will use to check against the hierarchy metadata pane
        String currentDateLeadingZero = DateAndTimeUtils.getCurrentDateMMddyyyy();
        logger.information("Current date with leading zeros: " + currentDateLeadingZero);

        //This is the current date value we will use to check against the disposition derivation pane
        String currentDateNoLeadingZero = "";
        String monthValue = Integer.parseInt(DateAndTimeUtils.getCurrentMonthMM()) + "";//Integer.parseInt will strip the leading zero
        String dayValue = Integer.parseInt(DateAndTimeUtils.getCurrentDayDD()) + "";//Integer.parseInt will strip the leading zero
        currentDateNoLeadingZero += monthValue + "/" + dayValue + "/" + DateAndTimeUtils.getCurrentYearyyyy();
        logger.information("Current date without leading zeros: " + currentDateNoLeadingZero);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //search for the parent Node using its UUID and change the end date of itself and it's children
        hierarchySearchPage().searchNodeUuid(nodeArray[3]);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean changeEndDateDescendantsWindowAppears = siblingMetadataContextMenu().hierarchyFunctionsChangeEndDateDescendants();
        Assertions.assertTrue(changeEndDateDescendantsWindowAppears, "The Change End Date Descendants Window did NOT appear");
        changeEndDateDescendantsPage().setEndEffectiveDate(currentDateLeadingZero);
        changeEndDateDescendantsPage().clickQuickLoadSubmit();

        //verify that the Your Workflow Page appears, collect the workflow ID, and verify that the work flow finishes
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        String workflowID = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowID);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished, "The workflow did not finish within 5 minutes");
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        //Search for the three children nodes and verify that the Effective End Date is the current date set by the parent node
        hierarchyNavigatePage().switchToHierarchyEditPage();

        hierarchySearchPage().searchNodeUuid(nodeArray[0]);
        boolean child1SiblingMetadataPageEndDateIsCorrect = siblingMetadataPage().getEndDateOfSelectedNode().equals(currentDateLeadingZero);
        boolean child1DispositionDerivationPageEndDateIsCorrect = (dispositionDerivationPage().getEndDateOfSelectedNode()).equals(currentDateNoLeadingZero);

        hierarchySearchPage().searchNodeUuid(nodeArray[1]);
        boolean child2SiblingMetadataPageEndDateIsCorrect = siblingMetadataPage().getEndDateOfSelectedNode().equals(currentDateLeadingZero);
        boolean child2DispositionDerivationPageEndDateIsCorrect = (dispositionDerivationPage().getEndDateOfSelectedNode()).equals(currentDateNoLeadingZero);

        hierarchySearchPage().searchNodeUuid(nodeArray[2]);
        boolean child3SiblingMetadataPageEndDateIsCorrect = siblingMetadataPage().getEndDateOfSelectedNode().equals(currentDateLeadingZero);
        boolean child3DispositionDerivationPageEndDateIsCorrect = (dispositionDerivationPage().getEndDateOfSelectedNode()).equals(currentDateNoLeadingZero);

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(child1SiblingMetadataPageEndDateIsCorrect, "Child 1 Sibling Metadata page Effective End Date is incorrect"),
              () -> Assertions.assertTrue(child1DispositionDerivationPageEndDateIsCorrect, "Child 1 Disposition Derivation page Effective End Date is incorrect"),
              () -> Assertions.assertTrue(child2SiblingMetadataPageEndDateIsCorrect, "Child 2 Sibling Metadata page Effective End Date is incorrect"),
              () -> Assertions.assertTrue(child2DispositionDerivationPageEndDateIsCorrect, "Child 2 Disposition Derivation page Effective End Date is incorrect"),
              () -> Assertions.assertTrue(child3SiblingMetadataPageEndDateIsCorrect, "Child 3 Sibling Metadata page Effective End Date is incorrect"),
              () -> Assertions.assertTrue(child3DispositionDerivationPageEndDateIsCorrect, "Child 3 Disposition Derivation page Effective End Date is incorrect")
        );
    }

    /**
     * STORY/BUG - HALCYONST 2272 <br>
     * SUMMARY - This test creates a new code name and verifies that there is a character limit enforced for the code name <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateCodeNameIDCharacterLimitLegalTest()
    {
        String extraLongCodeName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        String goldilocksCodeName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv";
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(datapodObject.getSections().get(0).getNodeUUID());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsUpdateCodeNameID();

        //Updating Code Name
        updateCodeNameIDPage().clickCreateNewCodeNameIDRadioButton();
        updateCodeNameIDPage().setCreateNewCodeNameIDTextField(extraLongCodeName);
        Assertions.assertEquals(goldilocksCodeName, updateCodeNameIDPage().copyAndGetTextFromClipBoard(), "The file report name was not shortened to the expected length");
    }

    /**
     * STORY/BUG - HALCYONST 2272 <br>
     * SUMMARY - This test verifies that a new code name can be created and that changes are correctly propagated through a node's descendants <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
            (
                    {
                            "true, ", //Parameters for creating a new code name
                            "false, title iii" //Parameters for changing to an existing code name
                    }
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void updateCodeNameCreateNewAndChangeExisting(boolean createNewCodeName, String updatedCodeName)
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String parentNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String expectedValidationError = "Code_Id for node and for parent are different";
        ArrayList<String> parentMetadata, lineageMetadata, descendantsList;
        String contentSet = ContentSets.IOWA_DEVELOPMENT.getCode();

        //Getting original metadata values
        parentMetadata = HierarchyDatabaseUtils.getCodeNameAndID(parentNodeUUID, connection);
        descendantsList = HierarchyDatabaseUtils.getDescendentsListFromParentNode(parentNodeUUID, connection);
        String originalCodeID = parentMetadata.get(0);
        try
        {
            //Logging into Codesbench
            homePage().goToHomePage();
            loginPage().logIn();

            //Filtering for target parent node
            hierarchyMenu().goToNavigate();
            hierarchySearchPage().searchNodeUuid(parentNodeUUID);
            //Updating CodeName of parent Node and its children
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().hierarchyFunctionsUpdateCodeNameID();
            if (createNewCodeName)
            {
                updatedCodeName = "test" + DateAndTimeUtils.getCurrentDateMMddyyyy();
                updateCodeNameIDPage().clickCreateNewCodeNameIDRadioButton();
                updateCodeNameIDPage().setCreateNewCodeNameIDTextField(updatedCodeName);
            }
            else
            {
                updateCodeNameIDPage().clickChangeExistingCodeNameIDRadioButton();
                updateCodeNameIDPage().changeExistingCodeNameID(updatedCodeName);
            }
            updateCodeNameIDPage().checkAssignCodeNameIDToMetadata();
            updateCodeNameIDPage().clickSubmit();
            hierarchySearchPage().refreshPage();

            //Verifying info validation flag appears on parent node indicating that its CodeName is different from its ancestor's CodeName
            boolean isExpectedValidationFlagPresent = siblingMetadataPage().doesSelectedNodeHaveInfoValidationFlag();
            Assertions.assertTrue(isExpectedValidationFlagPresent, "The expected validation flag did not appear when expected");
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().validationFlagsCheckNodeValidationFlags();
            Assertions.assertEquals(expectedValidationError, validationFlagsReportPopupPage().getErrorValueForGivenNode(parentNodeUUID), "The expected validation flag description was not present");
            validationFlagsReportPopupPage().clickClose();

            //Verifying that the parent node's codename and codeID have updated correctly, and that its descendants also have matching codename/codeIDs
            for (String descendant : descendantsList)
            {
                if (descendant.equals(parentNodeUUID)) //This loop starts with the parent node (it is included in the descendantsList arraylist)
                {
                    parentMetadata = HierarchyDatabaseUtils.getCodeNameAndID(parentNodeUUID, connection);
                    boolean doesParentNodeCodeIDMatchOriginalCodeID = parentMetadata.get(0).equals(originalCodeID); //This should be given to an assert FALSE
                    if (!createNewCodeName)
                    {
                        updatedCodeName = updatedCodeName.toUpperCase();
                    }
                    boolean doesParentNodeCodeNameMatchNewCodeName = parentMetadata.get(1).trim().equals(updatedCodeName); //This should be given to an assert TRUE
                    Assertions.assertFalse(doesParentNodeCodeIDMatchOriginalCodeID, "The Parent node's code ID was not updated when it should have been");
                    Assertions.assertTrue(doesParentNodeCodeNameMatchNewCodeName, "The Parent node's code name was not updated when it should have been");
                }
                else
                {
                    lineageMetadata = HierarchyDatabaseUtils.getCodeNameAndID(descendant, connection);
                    boolean doesDescendantCodeIDMatchParentCodeID = lineageMetadata.get(0).equals(parentMetadata.get(0)); //This should be given to an assert TRUE
                    boolean doesDescendantCodeNameMatchParentCodeName = lineageMetadata.get(1).equals(parentMetadata.get(1)); //This should be given to an assert TRUE
                    Assertions.assertTrue(doesDescendantCodeIDMatchParentCodeID, "Node: " + descendant + " did not have a matching Code ID");
                    Assertions.assertTrue(doesDescendantCodeNameMatchParentCodeName, "Node: " + descendant + " did not have a matching Code Name");
                }
            }
        }
        finally
        {
            if (datapodObject != null)
            {
                datapodObject.delete();
            }
            if (createNewCodeName)
            {
                HierarchyDatabaseUtils.deleteCodeName(user().getUsername(), updatedCodeName, contentSet, connection);
            }
        }
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }
}
