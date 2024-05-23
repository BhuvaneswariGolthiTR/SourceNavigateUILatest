package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishreadytextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
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

public class PublishReadyTextNodesOnlyValidationsTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

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

        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        publishingMenu().goToPublishReadyTextNodesOnly();

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

        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Ready-Text nodes only Toolbox window was not opened");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForStatusColumn();
        gridHeaderFiltersPage().setFilterValue("Repeal");
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValues[0]);

        //Right click node in range and click next
        gridPage().rightClickByNodeTargetValue(nodeValues[0]);
        gridContextMenu().readyStatus();
        toolbarPage().clickNext();

        //Check that warning message appears and the rest of range nodes appears in grid
        boolean warningRMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.R_WARNING_XPATH);

        boolean[] validationOfNodeIsSetToRArray = new boolean[nodeValues.length];
        for(int i =0; i<nodeValues.length; i++)
        {
            gridPage().selectByNodeTargetValue(nodeValues[i]);
            String validationText = gridPage().getValidationOfSelectedNode();
            validationOfNodeIsSetToRArray[i] = validationText.equals("R");
        }

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningRMessageAppears,"The warning message with title: 'Warning R' is not displayed when it should"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[0],"The validation of the first node should  be set to 'R'."),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[1],"The validation of the second node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[2],"The validation of the third node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[3],"The validation of the fourth node should be set to 'R.'"),
            () -> Assertions.assertTrue(validationOfNodeIsSetToRArray[4],"The validation of the fifth node should be set to 'R.'")
        );
    }

    /**
     * STORY/BUG - HALCYONST-6858, HALCYONST-7874 <br>
     * SUMMARY - An earlier version of a node should appear in the submission page if the user attempts to set the current version of a node to ready status.<br>
     * There should also be a note at the top of the page titled "Warning V" and the original node should have a validation status of 'V.'<br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void versioningValidationTest()
    {
        String nodeUuid = "I2AB4D96014F111DA8AC5CD53670E6B4E";
        String contentUuid = "I59F5C6801AF511DAB310FB76B2E4F553";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Create new node by editing and checking in changes with current date to a current node
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

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
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        String validationOfSelectedNode = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean nodeHasGreenCheckmarkFlag = validationOfSelectedNode.equals("check");
        boolean nodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        String newNodeUuid = updateMetadataPage().getNodeUuid();
        updateMetadataPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeHasGreenCheckmarkFlag,"The new node does not have the correct validation"),
            () -> Assertions.assertTrue(nodeIsSetToNotPublished,"The new node does not have the correct publishing status")
        );

        //Check new node is displayed in toolbox page and set it to ready status
        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        gridPage().waitForGridLoaded();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Ready-Text nodes only window was not opened");

        toolbarPage().clickRequeryAndReloadFromDatabase();
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);
        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);
        gridPage().waitForGridLoaded();

        boolean isNodeInReadySelectionPublishingToolbox = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);
        Assertions.assertTrue(isNodeInReadySelectionPublishingToolbox,"The node with the given value is not displayed in the toolbox when it should be");

        gridPage().rightClickByNodeTargetValue(nodeValue);
        gridContextMenu().readyStatus();
        toolbarPage().clickNext();

        //Check original node appears has "V." symbol and warning message appears
        boolean warningVMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.V_WARNING_XPATH);
        boolean originalNodeWasAdded = gridPaginationPage().getTotalNumberOfSelectableRows()>=2;
        gridPage().selectFirstSectionNode();
        String validationText = gridPage().getValidationOfSelectedNode();
        boolean validationIsSetToV = validationText.equals("V");
        toolbarPage().closeCurrentWindowIgnoreDialogue();

        //clean up steps
        HierarchyDatabaseUtils.updateIsDeleteFlagToDeletedHierarchyNavigate(newNodeUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.updateIsDeleteFlagToUndeletedHierarchyNavigate(nodeUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid, "", uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningVMessageAppears,"A warning message with the title 'Warning V' does not appear when it should"),
            () -> Assertions.assertTrue(originalNodeWasAdded,"The original node was not added to the submission page when it should have been"),
            () -> Assertions.assertTrue(validationIsSetToV,"The validation of the original node is not set to 'V' when it should have")
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
