package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextNodesOnlyValidationTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-9108 <br>
     * SUMMARY - If a prior version node of a selected node has a status of "Not Published" it should be identified as an expected node
     * and automatically included on the Submission page as part of the Prior Version Validation. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void validationForPriorVersionsTest()
    {
        String nodeUuid = "I93AFD7F0157611DA8AC5CD53670E6B4E";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Check that the legal user has the necessary permissions
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        String originalNodesStartDate = siblingMetadataPage().getSelectedNodeStartDate();

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().closeAndCheckInChangesWithGivenDate(currentDate);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                "THIS IS NOT AN ERROR, just a notice that the selected node ["+nodeUuid+"] ended before your navigation filter date. Your navigation date has been reset to "+siblingMetadataPage().getYesterdaysDateWithoutLeadingZeros()+".");
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");
        editorPage().waitForEditorToClose();

        //Check publishing status and validation of new node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean selectedNodeIsOldNode = siblingMetadataPage().getSelectedNodeStartDate().equals(originalNodesStartDate);
        boolean oldNodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(selectedNodeIsOldNode,"The old node is not selected when it should have been");
        Assertions.assertTrue(oldNodeIsSetToNotPublished,"The old node does not have the correct publishing status");

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        boolean newNodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(newNodeIsSetToNotPublished,"The new node does not have the correct publishing status");

        //Check new node is displayed in toolbox page and set it to ready status
        boolean toolboxWindowOpened = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "the Publish Approve-Text nodes only Toolbox window was not opened");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);
        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);

        gridPage().rightClickByNodeTargetValue(nodeValue);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        //Check original node appears has "V." symbol and warning message appears
        boolean warningVMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.V_WARNING_XPATH);
        boolean originalNodeWasAdded = gridPaginationPage().getTotalNumberOfSelectableRows()>=2;
        boolean newNodeIsHighlightedGreen = gridPage().isBackgroundGreenForDeselectedLastRow();

        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(originalNodesStartDate);
        boolean oldNodeIsHighlightedGreen = gridPage().isBackgroundGreenForDeselectedLastRow();

        //Delete new node in hierarchy
        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyTreePage().setNavigationTreeToCurrentDate();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean nodeWasDeleted = siblingMetadataPage().isNodeDisplayedWithValue(nodeValue);

        //Reset original node to no end date
        hierarchyTreePage().setNavigationTreeToYesterdaysDate();
        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickQuickLoadOk();
        boolean nodeUpdated = siblingMetadataPage().checkSelectedNodesEndDateEqualsNoDate();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningVMessageAppears,"A warning message with the title 'Warning V' does not appear when it should"),
            () -> Assertions.assertTrue(originalNodeWasAdded,"The original node was not added to the submission page when it should have been"),
            () -> Assertions.assertTrue(newNodeIsHighlightedGreen,"The new node is not highlighted green when it should have"),
            () -> Assertions.assertTrue(oldNodeIsHighlightedGreen,"The old node is not highlighted green when it should have"),
            () -> Assertions.assertTrue(nodeUpdated,"The old node should have been updated to have no end date but still has one"),
            () -> Assertions.assertFalse(nodeWasDeleted,"The node was not deleted when it should have been")
        );
    }

    /**
     * STORY/BUG - HALCYONST-7156, HALCYONST-7874 <br>
     * SUMMARY - When any one node within a range is selected and a user chooses Ready status or Approved status,
     * then all nodes within the range should be added to the Ready status or Approved status selection whether the user has them selected or not.<br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void rangeRepealValidationTest()
    {
        String[] nodeValues = {"347.1", "347.2", "347.3", "347.4", "347.5", "347.6"};
        String nodeUuid = "I181C93C014F511DA8AC5CD53670E6B4E";
        String keyword = "= ";

        //add approver from approved list via database
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that selected node is in a range,has a publishing status of 'Not Published', and set to 'Repeal'
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        boolean nodeIsStartOfRange = siblingMetadataPage().isSelectedNodeTheStartOfARange();
        boolean nodeStatusIsSetToRepeal = siblingMetadataPage().isSelectedRowStatusRepeal();
        boolean publishStatusSetToNotPublish = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeIsStartOfRange,"The node is not the starting node of the range when it should be"),
            () -> Assertions.assertTrue(nodeStatusIsSetToRepeal,"The node's status is not set to Repeal when it should be"),
            () -> Assertions.assertTrue(publishStatusSetToNotPublish,"The node's publishing status is not set to Not Published when it should be")
        );

        boolean toolboxWindowOpened = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Approve-Text nodes only Toolbox window was not opened");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForStatusColumn();
        gridHeaderFiltersPage().setFilterValue("Repeal");
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValues[0]);

        //Right click node in range and click next
        gridPage().rightClickByNodeTargetValue(nodeValues[0]);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        //Check that warning message appears and the rest of range nodes appears in grid
        boolean warningRMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.R_WARNING_XPATH);

        boolean[] validationOfNodeIsSetToRArray = new boolean[nodeValues.length];
        for(int i =1; i<nodeValues.length; i++)
        {
            gridPage().selectByNodeTargetValue(nodeValues[i]);
            String validationText = gridPage().getValidationOfSelectedNode();
            validationOfNodeIsSetToRArray[i] = validationText.equals("R");
        }

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningRMessageAppears,"The warning message with title: 'Warning R' is not displayed when it should"),
//            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[0],"The validation of the first node should  be set to 'R'."),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[1],"The validation of the second node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[2],"The validation of the third node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[3],"The validation of the fourth node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[4],"The validation of the fifth node should be set to 'R.'")
        );
    }

    /**
     * STORY/BUG - HALCYONST-6100, HALCYONST-7874 <br>
     * SUMMARY - This test verifies that if a parent node has not been published at any point, after setting the child node to ready status in the Publishing UI,
     * a warning message appears saying a node was added because they are required parent nodes.
     * This test then verifies that the validation of the added parent node is set to 'P'. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void parentageValidationTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String chapterNodeUuid = datapodObject.getAllNodes().get(2).getNodeUUID();

        String sectionNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid, contentSetIowa, uatConnection);
        String chapterNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(chapterNodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        publishingMenu().goToPublishReadyTextNodesOnly();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(sectionNodeValue);

        gridPage().rightClickByNodeTargetValue(sectionNodeValue);
        gridContextMenu().readyStatus();
        toolbarPage().clickNext();

        gridPage().selectByNodeTargetValue(chapterNodeValue);
        String validationText = gridPage().getValidationOfSelectedNode();
        boolean pValidationAppears = validationText.equals("P");
        boolean pWarningMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.P_WARNING_MESSAGE_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(pValidationAppears,"The selected chapter node should have a 'P' symbol in the validation column but does not"),
            () -> Assertions.assertTrue(pWarningMessageAppears,"The 'Warning: P' message does not appear at the top of the Publishing UI when it should")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(uatConnection != null)
        {
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}
