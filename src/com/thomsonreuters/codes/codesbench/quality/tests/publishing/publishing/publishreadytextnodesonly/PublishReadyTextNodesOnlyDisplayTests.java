package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishreadytextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.QueryNoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublishReadyTextNodesOnlyDisplayTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY/BUG - HALCYONST-7370 <br>
     * SUMMARY - The Refresh function should remove nodes that have never been published and that are deleted during an open UI from the UI display. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void deletedNotPublishedNodesAndRefreshTest()
    {
        String greenCheckmarkStatus = "check";
        String nodeUuid = "IA24BA520157E11DA8AC5CD53670E6B4E";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String alertMessage = String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s", nodeUuid, DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros());
        String keyword = "= ";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Create new node version by editing and checking in changes with current date to a current node
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorTextPage().breakOutOfFrame();
        editorToolbarPage().clickToolbarClose();
        editorClosurePage().setAssignEffectiveDate(currentDate);
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChangesAndHandleAlert(alertMessage);

        //Check publishing status and validation of new node
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        String validationOfSelectedNode = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean nodeHasGreenCheckmarkFlag = validationOfSelectedNode.equals(greenCheckmarkStatus);
        boolean nodeIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeHasGreenCheckmarkFlag,"The new node does not have the correct validation"),
            () -> Assertions.assertTrue(nodeIsSetToNotPublished,"The new node does not have the correct publishing status")
        );

        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Ready-Text nodes only Toolbox window was not opened");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);
        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);
        gridPage().waitForGridLoaded();

        //Check new node is displayed in toolbox page
        boolean isNodeInReadySelectionPublishingToolbox = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue);
        Assertions.assertTrue(isNodeInReadySelectionPublishingToolbox,"The node with the given value is not displayed in the toolbox when it should be");

        //Delete new node in hierarchy
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyTreePage().setNavigationTreeToCurrentDate();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean nodeWasNotDeleted = siblingMetadataPage().isNodeDisplayedWithValue(nodeValue);
        Assertions.assertFalse(nodeWasNotDeleted,"The node should be deleted");

        boolean toolboxWindowIsOpened = mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();
        Assertions.assertTrue(toolboxWindowIsOpened, "Didn't switch to Ready selection");

        //Refresh toolbox and check new node is not displayed
        toolbarPage().clickRefreshCurrentGrid();
        boolean isNodeInReadySelectionPublishingToolboxAfterDelete = gridPage().isNodeHierarchyColumnValueInGrid(keyword + nodeValue);

        //Reset original node to no end date
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyTreePage().setNavigationTreeToYesterdaysDate();
        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickQuickLoadOk();

        Assertions.assertFalse(isNodeInReadySelectionPublishingToolboxAfterDelete,"The node with the given value should not be displayed");
    }

    /**
     * STORY: HALCYONST-7816 <br>
     * SUMMARY: Verifies that the 2 Step Approval - Ready Selection publishing UI has a Next button and
     * does not have radio buttons available on the submission page <br>
     * USER: LEGAL <br>
     */
    /*
    @Test
    @LEGAL
    @LOG
    public void publishingSubmissionPageRadioButtonsTest()
    {
        String nodeUuid = "IBF170649F823415BB6A3D4BF0DA85EA0";
        String contentUuid = "I4D86AC70BE6B11D9BDF79F56AB79CECB";

        String contentSet = "Texas (Development)";
        String CONTENT_SET_TEXAS = contentSetMap.get(DynamicScrollingHomePage.ContentSets.TEXAS);

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, CONTENT_SET_TEXAS, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSet);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        boolean nodeIsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(nodeIsNotPublishedStatus, "Node was not updated correctly. The publishing status isn't not published");

        boolean toolboxWindowOpened = publishingMenu().goToPublish2StepApprovalReadySelection();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox window did not open and should have");

        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForKeywordColumn();
        gridHeaderFiltersPage().setFilterValue("Sec.");
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);

        gridPage().rightClickByNodeHierarchyColumnValue("Sec. " + nodeValue);
        gridContextMenu().readyStatus();

        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertTrue(nextButtonIsDisplayed, "The button next button was not displayed when it should be.");

        toolbarPage().clickNext();

        boolean publishNowRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        boolean publishTonightRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.PUBLISH_TONIGHT_RADIO_BUTTON_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(publishNowRadioButtonAppears, "Publish Now does not appear on the NOD Publishing Only submission page and should"),
            () -> Assertions.assertFalse(publishTonightRadioButtonAppears, "Publish Tonight does not appear on the NOD Publishing Only submission page and should")
        );
    }
    */

    /**
    * STORY: HALCYONST-7385 <br>
     * SUMMARY: After editing a node and setting an effective date of today, this test verifies that the original node as well as the
     * newly created both node appear in the Publish Ready-Text nodes only UI <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void displayAllVersionWithParentageColumnTest()
    {
        String nodeUuid = "I7AB49D4014EE11DA8AC5CD53670E6B4E";
        String sectionKeyword = "= ";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String alertMessage = String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s", nodeUuid, DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros());
        String legalUser = user().getPublishingToolboxUsername();
        boolean toolboxWindowAppeared;

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);

        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseIntoFirstTextParagraphWithSpace("test");
        editorTextPage().breakOutOfFrame();
        editorToolbarPage().clickToolbarClose();
        editorClosurePage().setAssignEffectiveDate(currentDate);
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChangesAndHandleAlert(alertMessage);

        String priorVersionNodeStartDate = siblingMetadataPage().getSelectedNodeStartDate();
        String priorVersionNodeEndDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);
        String newVersionNodeStartDate = DateAndTimeUtils.getCurentDateMDYYYY();
        String newVersionNodeEndDate = siblingMetadataPage().getSelectedNodeEndDate();

        toolboxWindowAppeared = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Ready-Text nodes only window opened correctly");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);
        gridHeaderPage().openMenuForModifiedByColumn();
        gridHeaderFiltersPage().setFilterValue(legalUser);
        gridHeaderPage().openMenuForModifiedDateColumn();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);
        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);

        boolean newVersionNodeAppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + nodeValue);
        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + nodeValue);
        String newVersionNodeStartDateInPublishingUI = gridPage().getSelectedNodesStartDate();
        String newVersionNodeEndDateInPublishingUI = gridPage().getSelectedNodesEndDate();

        boolean newVersionNodeStartDateMatches = newVersionNodeStartDate.contains(newVersionNodeStartDateInPublishingUI);

        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().clickClearFilter();
        gridHeaderFiltersPage().setFilterDateValue(priorVersionNodeStartDate);

        boolean priorVersionNodeAppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + nodeValue);
        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + nodeValue);
        String priorVersionNodeStartDateInPublishingUI = gridPage().getSelectedNodesStartDate();
        String priorVersionNodeEndDateInPublishingUI = gridPage().getSelectedNodesEndDate();
        boolean priorVersionNodeEndDateMatches = priorVersionNodeEndDate.contains(priorVersionNodeEndDateInPublishingUI);

        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        hierarchyNavigatePage().searchTocNodeUuidHandleAlert(nodeUuid);
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, currentDate);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();

        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, priorVersionNodeStartDate);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();

        updateMetadataPage().enterEffectiveEndDate("");
        updateMetadataPage().clickQuickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(priorVersionNodeAppearsInGrid, "Prior version node does not appear in the grid"),
            () -> Assertions.assertEquals(priorVersionNodeStartDate, priorVersionNodeStartDateInPublishingUI, "Prior version nodes start date does not match"),
            () -> Assertions.assertTrue(priorVersionNodeEndDateMatches, "Prior version Node end date in Hierarchy does not match the end date that appears in the publishing UI"),
            () -> Assertions.assertTrue(priorVersionNodeAppearsInGrid, "New version node does not appear in the grid"),
            () -> Assertions.assertTrue(newVersionNodeAppearsInGrid, "New version node does not appear in the grid"),
            () -> Assertions.assertTrue(newVersionNodeStartDateMatches, "New version Node start date in Hierarchy does not match the start date that appears in the publishing UI"),
            () -> Assertions.assertEquals("no date", newVersionNodeEndDate, "New version nodes end date does not match"),
            () -> Assertions.assertEquals("", newVersionNodeEndDateInPublishingUI, "New version nodes end date does not match")
        );
    }

    /**
     * STORY: Halcyonst-6396, 6953 <br>
     * SUMMARY: Check visual indication that a node has been deleted in the Publish Ready-Text nodes only UI <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void visualDisplayOfNodeDeletion()
    {
        String nodeUuid = "I9495FE3014F211DA8AC5CD53670E6B4E";
        String contentUuid = "I3ED93EC01AF711DAB310FB76B2E4F553";
        String keyword = "= ";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetId,  uatConnection);

        HierarchyDatabaseUtils.updateIsDeleteFlagToDeletedHierarchyNavigate(nodeUuid, contentSetId, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetId, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //go to publishing -> publishing -> 2 Step Approval Ready
        boolean twoStepApprovalReadySelectionPageLoaded = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(twoStepApprovalReadySelectionPageLoaded, "Publish Ready-Text nodes only page loaded");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);

        gridPage().selectByNodeTargetValue(nodeValue);
        boolean isNodeDeletedInToolbox = gridPage().isSelectedNodeDeleted();

        //Find document in hierarchy and Verify node is deleted
        gridPage().rightClickByNodeTargetValue(nodeValue);
        gridContextMenu().findDocumentInHierarchy();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean nodeIsDeleted = hierarchyTreePage().isTreeNodeWithGivenValueDeleted(nodeValue);

        HierarchyDatabaseUtils.updateIsDeleteFlagToUndeletedHierarchyNavigate(nodeUuid, contentSetId, uatConnection);

        Assertions.assertAll
                (
                        () ->  Assertions.assertTrue(isNodeDeletedInToolbox, "The node is Deleted in the toolbox"),
                        () ->  Assertions.assertTrue(nodeIsDeleted, "The node is Deleted in Hierarchy Edit")

                );
    }

    /**
     * STORY: HALCYONST-6019, HALCYONST-6356, HALCYONST-6364, HALCYONST-6523, HALCYONST-6565, HALCYONST-8075 <br>
     * SUMMARY: Verifies that after finding nodes with the various flags in a publishing UI, the nodes should display with the correct flag
     * in hierarchy. Also verifies that after right clicking a node with a flag, the node should have the Approved Status context menu option disabled. <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void flagDisplayTest()
    {
        String errorFlagContentUuid = "I08BF7E701AF811DAB310FB76B2E4F553";
        String warningFlagContentUuid = "I08CC01901AF811DAB310FB76B2E4F553";
        String infoFlagContentUuid = "I37B8CFB00E3011DC8EE5B07DC6DF8E8D";
        String greenCheckFlagContentUuid = "I08EFDD401AF811DAB310FB76B2E4F553";
        String questionFlagContentUuid = "I08FC39501AF811DAB310FB76B2E4F553";
        List<String> flagColumnExpectedFilterValues = Arrays.asList("RED_FLAG", "YELLOW_FLAG", "BLUE_FLAG", "QUERY");
        String sectionKeyword = "= ";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        homePage().goToHomePage();
        loginPage().logIn();

        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, uatConnection);

        // error flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(errorFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(errorFlagContentUuid, contentSetIowa, uatConnection);
        String errorNodeValue = HierarchyDatabaseUtils.getNodeValue(errorFlagContentUuid, contentSetIowa, uatConnection);

        // warning flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(warningFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToWarningValidationFlag(warningFlagContentUuid, contentSetIowa, uatConnection);
        String warningNodeValue = HierarchyDatabaseUtils.getNodeValue(warningFlagContentUuid, contentSetIowa, uatConnection);

        // info flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(infoFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToInfoValidationFlag(infoFlagContentUuid, contentSetIowa, uatConnection);
        String infoNodeValue = HierarchyDatabaseUtils.getNodeValue(infoFlagContentUuid, contentSetIowa, uatConnection);

        // green check flag mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        String greenCheckNodeValue = HierarchyDatabaseUtils.getNodeValue(greenCheckFlagContentUuid, contentSetIowa, uatConnection);

        String questionNodeValue = HierarchyDatabaseUtils.getNodeValue(questionFlagContentUuid, contentSetIowa, uatConnection);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(questionFlagContentUuid);

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().rightClickFirstParagraph();
        editorTextContextMenu().insertQuery();

        queryNotePage().setType("DATE");
        queryNotePage().setQueryNoteActionDateToCurrentDate();
        queryNotePage().setQueryNoteText("questionflagnode");
        queryNotePage().save();

        editorToolbarPage().clickToolbarClose();
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChanges();

        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox did not open and we expected it to");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForFlagColumn();
        gridHeaderFiltersPage().clickFilterIcon();
        List<String> flagColumnActualFilterValues = gridHeaderFiltersPage().getFilterValues();
        boolean flagColumnFilterValuesContainExpected = flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(0)) &&
                flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(1)) &&
                flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(2)) &&
                flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(3)) ;
        Assertions.assertTrue(flagColumnFilterValuesContainExpected, "The flags we expect to be able to filter for do not appear in the list of filter values. Try to manually find the nodes in the grid. Right click one of the nodes and select 'Find document in hierarchy. If the node has a flag other than a green check, this is a bug.");

        gridHeaderFiltersPage().setMultipleFilterValues(flagColumnExpectedFilterValues.get(0), flagColumnExpectedFilterValues.get(1), flagColumnExpectedFilterValues.get(2), flagColumnExpectedFilterValues.get(3));

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + errorNodeValue, sectionKeyword + warningNodeValue, sectionKeyword + infoNodeValue, sectionKeyword + questionNodeValue);

        boolean questionFlagNodeAppearsInGridAfterGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + questionNodeValue);
        gridPage().rightClickByNodeTargetValue(questionNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToQuestionNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(questionNodeValue);
        Assertions.assertTrue(broughtToQuestionNodeAfterFindDocumentInHierarchy, "Brought to Question node after Find Document in Hierarchy");

        QueryNoteDatabaseUtils.deleteQueryNote(uatConnection, "questionflagnode", legalUser().getUsername());

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(errorNodeValue);
        boolean approvedStatusIsDisabledForNodeWithErrorValidationFlag = gridContextMenu().isElementDisabled(GridContextMenuElements.READY_STATUS_XPATH);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToErrorNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(errorNodeValue);
        Assertions.assertTrue(broughtToErrorNodeAfterFindDocumentInHierarchy, "Brought to Error node after Find Document in Hierarchy");

        boolean selectedNodeHasErrorValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(warningNodeValue);
        boolean approvedStatusIsDisabledForNodeWithWarningValidationFlag = gridContextMenu().isElementDisabled(GridContextMenuElements.READY_STATUS_XPATH);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToWarningNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(warningNodeValue);
        Assertions.assertTrue(broughtToWarningNodeAfterFindDocumentInHierarchy, "Brought to Warning node after Find Document in Hierarchy");

        boolean selectedNodeHasWarningValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveWarningValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(infoNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToInfoNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(infoNodeValue);
        Assertions.assertTrue(broughtToInfoNodeAfterFindDocumentInHierarchy, "Brought to Info node after Find Document in Hierarchy");

        boolean selectedNodeHasInfoValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveInfoValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setFilterValue(sectionKeyword + greenCheckNodeValue);

        gridPage().rightClickByNodeTargetValue(greenCheckNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToGreenCheckNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(greenCheckNodeValue);
        Assertions.assertTrue(broughtToGreenCheckNodeAfterFindDocumentInHierarchy, "Brought to Green check node after Find Document in Hierarchy");

        boolean selectedNodeHasGreenCheckValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveGreenCheckValidationFlag();

        // set nodes back to green check validation flag
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(errorFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(warningFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(infoFlagContentUuid, contentSetIowa, uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(questionFlagNodeAppearsInGridAfterGrid, "Node with question validation flag does not appear in the grid and should"),
            () -> Assertions.assertTrue(selectedNodeHasErrorValidationFlagInHierarchy, "The node with an Error validation flag in the publishing UI does not appear to have an Error flag in Hierarchy and should"),
            () -> Assertions.assertTrue(approvedStatusIsDisabledForNodeWithErrorValidationFlag, "Ready Status is not disabled for the node with an error flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasWarningValidationFlagInHierarchy, "The node with a Warning validation flag in the publishing UI does not appear to have a Warning flag in Hierarchy and should"),
            () -> Assertions.assertTrue(approvedStatusIsDisabledForNodeWithWarningValidationFlag, "Ready Status is not disabled for the node with a warning flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasInfoValidationFlagInHierarchy, "The node with an Info validation flag in the publishing UI does not appear to have an Info flag in Hierarchy and should"),
            () -> Assertions.assertTrue(selectedNodeHasGreenCheckValidationFlagInHierarchy, "The node with no flag in the publishing UI does not appear to have a Green check flag in Hierarchy and should")
        );
    }


    /**
     * STORY/BUG - HALCYONST-8942, HALCYONST-8479 <br>
     * SUMMARY - Test verifies that all law tracking statuses appear in the 2-Step Approval - Ready Selection UI under the Law Tracking column <br>
     * USER - LEGAL <br>
     */

    //This test is in the wrong file needs to be moved to TwoStepPublishingPublishApproveDisplayTests

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void lawTrackingStatusesTest()
    {
        String[] nodeUuids = {"I4C2B7960157711DA8AC5CD53670E6B4E", "I4C2FE630157711DA8AC5CD53670E6B4E", "I4C3FC4B0157711DA8AC5CD53670E6B4E", "I4C445890157711DA8AC5CD53670E6B4E"};
        String[] contentUuids = {"I7DBF30901B0111DAB311FB76B2E4F553", "I7DC7BC101B0111DAB311FB76B2E4F553", "I7DD773801B0111DAB311FB76B2E4F553", "I7DE667A01B0111DAB311FB76B2E4F553"};
        String sectionKeyword = "= ";
        String userOnPublishingToolbox = user().getPublishingToolboxUsername();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String codeName = HierarchyDatabaseUtils.getNodeCodeName(contentUuids[0], uatConnection);
        ArrayList<String> nodeValues = new ArrayList<>();
        for(int i = 0; i < contentUuids.length; i++)
        {
            nodeValues.add(HierarchyDatabaseUtils.getNodeValue(contentUuids[i], contentSetIowa, uatConnection));
        }
        for(int i = 0; i < contentUuids.length; i++)
        {
            PublishingDatabaseUtils.setPublishingNodeToReady(contentUuids[i], contentSetIowa, uatConnection);
        }

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //Full Vols
        hierarchySearchPage().searchNodeUuid(nodeUuids[0]);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean node1UpdatedToNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(node1UpdatedToNotPublished, "Node with UUID: " + nodeUuids[0] + " did not update to not published and should have");

        int node1WipVersionNum = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(uatConnection,contentUuids[0]);
        HierarchyDatabaseUtils.setNodesWipVersionLawTrackingStatusToFullVols(contentUuids[0], node1WipVersionNum, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickWipVersionByIndex("1");
        String node1WipVersionLawTrackingStatus = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        boolean node1WipVersionLawTrackingStatusIsFullVols = node1WipVersionLawTrackingStatus.equals("Full Vols");
        Assertions.assertTrue(node1WipVersionLawTrackingStatusIsFullVols, "Node with UUID: " + nodeUuids[0] + " does not have a law tracking status of Full Vols");

        previousWipVersionsPage().clickClose();

        //Quick Load
        hierarchySearchPage().searchNodeUuid(nodeUuids[1]);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean node2UpdatedToNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(node2UpdatedToNotPublished, "Node with UUID: " + nodeUuids[1] + " did not update to not published and should have");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickWipVersionByIndex("1");
        String node2WipVersionLawTrackingStatus = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        boolean node2WipVersionLawTrackingStatusIsQuickLoad = node2WipVersionLawTrackingStatus.equals("Quick Load");
        Assertions.assertTrue(node2WipVersionLawTrackingStatusIsQuickLoad, "Node with UUID: " + nodeUuids[1] + " does not have a law tracking status of Quick Load");

        previousWipVersionsPage().clickClose();

        //Full Vols-Compare
        hierarchySearchPage().searchNodeUuid(nodeUuids[2]);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean node3UpdatedToNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(node3UpdatedToNotPublished, "Node with UUID: " + nodeUuids[2] + " did not update to not published and should have");

        int node3WipVersionNum = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(uatConnection,contentUuids[2]);
        HierarchyDatabaseUtils.setNodesWipVersionLawTrackingStatusToFullVolsCompare(contentUuids[2], node3WipVersionNum, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickWipVersionByIndex("1");
        String node3WipVersionLawTrackingStatus = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        boolean node3WipVersionLawTrackingStatusIsFullVols = node3WipVersionLawTrackingStatus.equals("Full Vols-Compare");
        Assertions.assertTrue(node3WipVersionLawTrackingStatusIsFullVols, "Node with UUID: " + nodeUuids[2] + " does not have a law tracking status of Full Vols-Compare");

        previousWipVersionsPage().clickClose();

        //Full Vols-Recomp
        hierarchySearchPage().searchNodeUuid(nodeUuids[3]);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean node4UpdatedToNotPublished = siblingMetadataPage().isSelectedNodeNotPublished();
        Assertions.assertTrue(node4UpdatedToNotPublished, "Node with UUID: " + nodeUuids[3] + " did not update to not published and should have");

        int node4WipVersionNum = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(uatConnection,contentUuids[3]);
        HierarchyDatabaseUtils.setNodesWipVersionLawTrackingStatusToFullVolsRecomp(contentUuids[3], node4WipVersionNum, uatConnection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickWipVersionByIndex("1");
        String node4WipVersionLawTrackingStatus = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        boolean node4WipVersionLawTrackingStatusIsFullVols = node4WipVersionLawTrackingStatus.equals("Full Vols-Recomp");
        Assertions.assertTrue(node4WipVersionLawTrackingStatusIsFullVols, "Node with UUID: " + nodeUuids[3] + " does not have a law tracking status of Full Vols-Recomp");

        previousWipVersionsPage().clickClose();

        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuids[0], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuids[1], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuids[2], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuids[3], contentSetIowa, uatConnection);

        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox window did not open and we expected it to");

        boolean hideFullVolsCheckboxAppears = toolbarPage().isElementDisplayed(ToolbarElements.HIDE_FULL_VOLS_CHECKBOX);
        boolean hideFullVolsSarCheckboxAppears = toolbarPage().isElementDisplayed(ToolbarElements.HIDE_FULL_VOLS_SAR_CHECKBOX);
        boolean hideComLawTrackingCheckboxAppears = toolbarPage().isElementDisplayed(ToolbarElements.HIDE_COM_LAW_TRACKING_CHECKBOX);
        boolean hideFullVolsRecompCheckboxAppears = toolbarPage().isElementDisplayed(ToolbarElements.HIDE_FULL_VOLS_RECOMP_CHECKBOX);
        boolean hideFullVolsCompareCheckboxAppears = toolbarPage().isElementDisplayed(ToolbarElements.HIDE_FULL_VOLS_COMPARE_CHECKBOX);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(hideFullVolsCheckboxAppears,"The Hide Full Vols checkbox is not displayed when it should be"),
            () -> Assertions.assertTrue(hideFullVolsSarCheckboxAppears,"The Hide Full Vols Sar checkbox is not displayed when it should be"),
            () -> Assertions.assertTrue(hideComLawTrackingCheckboxAppears,"The Hide Com Law Tracking checkbox is not displayed when it should be"),
            () -> Assertions.assertTrue(hideFullVolsRecompCheckboxAppears,"The Hide Full Vols Recomp checkbox is not displayed when it should be"),
            () -> Assertions.assertTrue(hideFullVolsCompareCheckboxAppears,"The Hide Full Vols Compare checkbox is not displayed when it should be")
        );

        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);
        gridHeaderPage().openMenuForModifiedByColumn();
        gridHeaderFiltersPage().setFilterValue(userOnPublishingToolbox);
        gridHeaderPage().openMenuForModifiedDateColumn();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + nodeValues.get(0), sectionKeyword + nodeValues.get(1), sectionKeyword + nodeValues.get(2), sectionKeyword + nodeValues.get(3));

        int numberOfSelectableRowsAfterFiltering = gridPaginationPage().getTotalNumberOfSelectableRows();
        boolean numberOfSelectableRowsIsAtleastFour = numberOfSelectableRowsAfterFiltering >= 4;
        Assertions.assertTrue(numberOfSelectableRowsIsAtleastFour, "Number of selectable rows is at least 4");

        gridPage().selectByNodeTargetValue(nodeValues.get(0));
        String node1LawTrackingStatusOnPublishingUI = gridPage().getSelectedNodesLawTrackingStatus();
        boolean node1LawTrackingStatusIsFullVolsOnPublishingUI = node1LawTrackingStatusOnPublishingUI.contains(node1WipVersionLawTrackingStatus);

        gridPage().selectByNodeTargetValue(nodeValues.get(1));
        String node2LawTrackingStatusOnPublishingUI = gridPage().getSelectedNodesLawTrackingStatus();
        boolean node2LawTrackingStatusIsQuickLoad = node2LawTrackingStatusOnPublishingUI.contains(node2WipVersionLawTrackingStatus);

        gridPage().selectByNodeTargetValue(nodeValues.get(2));
        String node3LawTrackingStatusOnPublishingUI = gridPage().getSelectedNodesLawTrackingStatus();
        boolean node3LawTrackingStatusIsFullVolsCompare = node3LawTrackingStatusOnPublishingUI.contains(node3WipVersionLawTrackingStatus);

        gridPage().selectByNodeTargetValue(nodeValues.get(3));
        String node4LawTrackingStatusOnPublishingUI = gridPage().getSelectedNodesLawTrackingStatus();
        boolean node4LawTrackingStatusIsFullVolsRecomp = node4LawTrackingStatusOnPublishingUI.contains(node4WipVersionLawTrackingStatus);

        // Hide Full Vols is expected to hide Full Vols, Full Vols-Recomp, and Full Vols-Compare from the grid
        toolbarPage().checkHideFullVolsCheckbox();
        boolean gridHidNodesAfterCheckingHideFullVols = gridPaginationPage().getTotalNumberOfSelectableRows() == numberOfSelectableRowsAfterFiltering - 3;
        toolbarPage().uncheckHideFullVolsCheckbox();

        // Hide Full Vols-Recomp is expected to hide Full Vols-Recomp from the grid
        toolbarPage().checkHideFullVolsRecompCheckbox();
        boolean gridHidNodeAfterCheckingFullVolsRecomp = gridPaginationPage().getTotalNumberOfSelectableRows() == numberOfSelectableRowsAfterFiltering - 1;
        toolbarPage().uncheckHideFullVolsRecompCheckbox();

        // Hide Full Vols is expected to hide Full Vols, Full Vols-Recomp, and Full Vols-Compare from the grid
        toolbarPage().checkHideFullVolsCompareCheckbox();
        boolean gridHidNodeAfterCheckingFullVolsCompare = gridPaginationPage().getTotalNumberOfSelectableRows() == numberOfSelectableRowsAfterFiltering - 1;
        toolbarPage().uncheckHideFullVolsCompareCheckbox();

//        //Clean up
//        for(int i = 0; i < contentUuids.length; i++)
//        {
//            HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuids[i], uatConnection);
//            HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuids[i], uatConnection);
//        }

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(node1LawTrackingStatusIsFullVolsOnPublishingUI, "Node 1 law tracking status on publishing UI is not Full Vols and should be"),
            () -> Assertions.assertTrue(node2LawTrackingStatusIsQuickLoad, "Node 2 law tracking status on publishing UI is not Quick Load and should be"),
            () -> Assertions.assertTrue(node3LawTrackingStatusIsFullVolsCompare, "Node 3 law tracking status on publishing UI is not Full Vols-Compare and should be"),
            () -> Assertions.assertTrue(node4LawTrackingStatusIsFullVolsRecomp, "Node 4 law tracking status on publishing UI is not Full Vols-Recomp and should be"),
            () -> Assertions.assertTrue(gridHidNodesAfterCheckingHideFullVols, "Nodes with Full Vols, Full Vols-Recomp, Full Vols-Compare no longer appear in the grid after checking Hide Full Vols"),
            () -> Assertions.assertTrue(gridHidNodeAfterCheckingFullVolsRecomp, "Node with Full Vols-Recomp no longer appears in the grid after checking Hide Full Vols-Recomp"),
            () -> Assertions.assertTrue(gridHidNodeAfterCheckingFullVolsCompare, "Node with Full Vols-Compare no longer appears in the grid after checking Hide Full Vols-Compare")
        );
    }


    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
