package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishreadytextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishReadyTextNodesOnlyOverarchingTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();


    /**
     *  STORY: HALCYONST-6068, HALCYONST-6330, HALCYONST-6360, HALCYONST-6982, HALCYONST-6986, HALCYONST-7060,
     *  HALCYONST-7172, HALCYONST-7043, HALCYONST-8270, HALCYONST-7901, HALCYONST-7030, HALCYONST-9480, HALCYONST-7816 <br>
     *  SUMMARY: Verifies overall content, functionality and display of the Publish Ready-Text nodes only publishing UI  <br>
     *  USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void twoStepApprovalReadySelectionUITest()
    {
        String nodeUuid1 = "I7AE6C9C0005F11DB8AD6C31C3DF13255";
        String contentUuid1 = "I7AF855F0005F11DB8AD6C31C3DF13255";
        String nodeUuid2 = "I29108D5014F311DA8AC5CD53670E6B4E";
        String contentUuid2 = "I5533ADC01AF911DAB310FB76B2E4F553";
        String sectionKeyword = "= ";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuid1, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid2, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        String codeName = HierarchyDatabaseUtils.getNodeCodeName(contentUuid1, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        // HALCYONST-7030: Verify that we do not see any publishing status for the root node
        boolean rootNodeHasAPublishingStatus = siblingMetadataPage().doesSelectedNodeHaveAPublishingStatus();

        // Verify that we are able to update a node's publishing status through Codesbench using the Publishing Status context menu option
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        String node1Value = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        boolean node1IsNotPublishedStatus = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(node1IsNotPublishedStatus, "The first node we mocked up does not have a not published status and should");

        // Verify we do not see the following sub-context menu options after expanding the Publishing Status context menu option
        hierarchySearchPage().searchNodeUuid(nodeUuid2);
        String node2Value = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();

        boolean setPublishingIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_PUBLISHED);
        boolean setCodesbenchFailureIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_CODESBENCH_FAILURE);
        boolean setLTCFailureIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_LTC_FAILURE);
        boolean setPublishedToPubIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_PUBLISHED_TO_PUB);
        boolean setLoadedToWestlawIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_LOADED_TO_WESTLAW);

        boolean toolboxWindowOpened = publishingMenu().goToPublishReadyTextNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox window did not open and we expected it to");
        gridPage().waitForGridLoaded();

        //Hard assert because we plan on clicking Next button further down in the test
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertTrue(nextButtonIsDisplayed, "Next button is displayed");

        int numberOfSelectableRowsBeforeSubmission = gridPaginationPage().getTotalNumberOfSelectableRows();

        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean twoStepApprovalReadySelectionIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISH_READY_TEXT_NODES_ONLY_PAGE_HEADER);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean flagColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean startDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.START_DATE_COLUMN_XPATH);
        boolean endDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.END_DATE_COLUMN_XPATH);
        boolean modifiedByColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_BY_COLUMN_XPATH);
        boolean modifiedyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_DATE_COLUMN_XPATH);
        boolean lawTrackingColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.LAW_TRACKING_COLUMN_XPATH);

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(node1Value, node2Value);

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);

        boolean node1AppearsInReadySelectionUI = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + node1Value);
        Assertions.assertTrue(node1AppearsInReadySelectionUI, "Node 1 does not appear in the grid and it should");

        boolean node2AppearsInReadySelectionUI = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + node1Value);
        Assertions.assertTrue(node2AppearsInReadySelectionUI, "Node 2 does not appear in the grid and it should");

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(node2Value);

        boolean hierarchyEditWindowOpenedAndMaximized = gridContextMenu().findDocumentInHierarchy();
        Assertions.assertTrue(hierarchyEditWindowOpenedAndMaximized, "After selecting Find Document In Hierarchy from the context menu in the publishing toolbox, The Hierarchy Edit window did not open and maximize successfully and should have");

        boolean broughtToCorrectNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(node2Value);
        Assertions.assertTrue(broughtToCorrectNodeAfterFindDocumentInHierarchy, "Expected to be brought to " + node2Value + " but we were not");
        boolean selectedNodeIsNotPublishedStatusAfterFindDocumentInHierarchy = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        mainHeaderPage().switchToPublishReadyTextNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(node1Value);
        gridContextMenu().readyStatus();

        boolean selectedRowAfterReadyStatusHasGreenBackground = gridPage().isBackgroundGreenForSelectedRow();
        Assertions.assertTrue(selectedRowAfterReadyStatusHasGreenBackground, "Selected row after ready status should have a green background but does not");

        toolbarPage().clickNext();

        boolean publishNowRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        boolean publishTonightRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);

        toolbarPage().clickSubmit();
        gridPage().waitForToastMessageToAppear();

        boolean didProgressBarDisappear = toolbarPage().didProgressBarDisappear();
        boolean isSubmitDisabled = toolbarPage().isSubmitButtonDisabled();
        boolean updatedStatusToastMessageIsDisplayed = gridPage().checkUpdatedStatusToastMessage();

        toolbarPage().clickBack();

        int numberOfSelectableRowsAfterSubmission = gridPaginationPage().getTotalNumberOfSelectableRows();
        boolean numberOfSelectableRowsDecreasedAfterSubmission = numberOfSelectableRowsBeforeSubmission > numberOfSelectableRowsAfterSubmission;
        boolean isNodeInReadySelectionPublishingToolbox = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + node1Value);

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodesByValue(node1Value);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean nodeIsReadyStatusAfterSubmittingViaTwoStepApprovalReadySelection = siblingMetadataPage().isSelectedNodeReadyStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(rootNodeHasAPublishingStatus, "Root node does have a publishing status and should not"),
            () -> Assertions.assertFalse(setPublishingIsDisplayed, "Set Publishing is displayed after expanding Publishing Status and should not"),
            () -> Assertions.assertFalse(setCodesbenchFailureIsDisplayed, "Set CodesBench Failure is displayed after expanding Publishing Status and should not"),
            () -> Assertions.assertFalse(setLTCFailureIsDisplayed, "Set LTCFailure is displayed after expanding Publishing Status and should not"),
            () -> Assertions.assertFalse(setPublishedToPubIsDisplayed, "Set Published to Pub is displayed after expanding Publishing Status and should not"),
            () -> Assertions.assertFalse(setLoadedToWestlawIsDisplayed, "Set Loaded to Westlaw is displayed after expanding Publishing Status and should not"),
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(twoStepApprovalReadySelectionIsDisplayed, MainHeaderElements.PUBLISH_READY_TEXT_NODES_ONLY_PAGE_HEADER + " is not displayed on the header and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(startDateColumnIsDisplayed, "Start date column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(endDateColumnIsDisplayed, "End date is not displayed on the header and should be"),
            () -> Assertions.assertTrue(modifiedByColumnIsDisplayed, "Modified by column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(modifiedyColumnIsDisplayed, "Modified column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(lawTrackingColumnIsDisplayed, "Law tracking column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(selectedNodeIsNotPublishedStatusAfterFindDocumentInHierarchy, "After selecting Find Document In Hierarchy from the context menu on the Publishing UI, the node is not Not Published status on the sibling metadata page"),
            () -> Assertions.assertFalse(publishNowRadioButtonAppears, "Publish Now does appear on the 2-Step Approval Ready Selection UI submission page"),
            () -> Assertions.assertFalse(publishTonightRadioButtonAppears, "Publish Tonight does appear on the 2-Step Approval Ready Selection UI submission page"),
            () -> Assertions.assertTrue(didProgressBarDisappear, "Progress bar did disappear"),
            () -> Assertions.assertTrue(isSubmitDisabled, "Submit button is not disabled"),
            () -> Assertions.assertTrue(updatedStatusToastMessageIsDisplayed, "Toast message is wrong"),
            () -> Assertions.assertTrue(numberOfSelectableRowsDecreasedAfterSubmission, "Row count didn't decrease"),
            () -> Assertions.assertFalse(isNodeInReadySelectionPublishingToolbox, "Node still exists in the Ready Selection publishing toolbox"),
            () -> Assertions.assertTrue(nodeIsReadyStatusAfterSubmittingViaTwoStepApprovalReadySelection, "Node updated to ready status after submitting via Two Step Approval - Ready Selection")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
