package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextnodesonly;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
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

public class PublishApproveTextNodesOnlyOverarchingTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();

    /**
     * STORY/BUG -HALCYONST-6330, HALCYONST-6360, HALCYONST-6838, HALCYONST-6874,
     * HALCYONST-6982, HALCYONST-6994, HALCYONST-7043, HALCYONST-6368, HALCYONST-7816 <br>
     * SUMMARY - Users should be able to move nodes directly from a Not Published status to a Publish Approve status. <br>
     * For this reason, a second Publish Approve UI should be implemented that displays nodes with a status of Publish Ready or Not Published <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void oneStepApprovalApproveSelectionUITest()
    {
        String contentUuid1 = "I22FEE1101B0011DAB310FB76B2E4F553";
        String contentUuid2 = "I230A2BB01B0011DAB310FB76B2E4F553";
        String[] nodeValues = {"421.3", "421.4"};

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid1, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid2, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        String codeName = HierarchyDatabaseUtils.getNodeCodeName(contentUuid1, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(contentUuid2);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        // Check that all main components of the Publishing UI are displayed
        boolean toolboxPageAppears = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(toolboxPageAppears, "The Toolbox page should appear but did not");
        gridPage().waitForGridLoaded();

        // Hard assertion because we plan on clicking the Next button further down in the test
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertTrue(nextButtonIsDisplayed, "Next button is displayed because user is on the list of publish approvers");

        // Check that all core columns and headers are displayed
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean oneStepApprovalApproveSelectionHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISH_APPROVE_TEXT_NODES_ONLY_PAGE_HEADER);
        boolean isNodeHierarchyColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean isRangeColumnWithNoHeaderTextDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean isFlagColumnWithNoHeaderTextDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean isVolColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean isStatusColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean isCodeColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean isKeywordColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean isValueColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean isStartDateColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.START_DATE_COLUMN_XPATH);
        boolean isEndDateColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.END_DATE_COLUMN_XPATH);
        boolean isModifiedByColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_BY_COLUMN_XPATH);
        boolean isModifiedDateColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_DATE_COLUMN_XPATH);
        boolean isPublishingStatusWithNoHeaderTextColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.PUBLISHING_STATUS_COLUMN_XPATH);

        boolean publishingStatusColumnHasNoHeaderText = gridHeaderPage().getPublishingStatusColumnHeaderText().isEmpty();
        boolean isReadyUserColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.READY_USER_COLUMN_XPATH);
        boolean isReadyDateColumnDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.READY_DATE_COLUMN_XPATH);

        // Filter for the first node
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodeValues);
        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);

        // Check that the selected node's publishing status id is correct and the 2 ready status columns are populated
        gridPage().selectByNodeTargetValue(nodeValues[1]);
        String nodePublishingStatusId2 = gridPage().getSelectedNodesPublishingStatusId();
        boolean nodePublishingStatusIsReady = nodePublishingStatusId2.equals("1");
        boolean readyUserColumnIsEmptyForReadyStatusNode = gridPage().isSelectedNodesReadyUserColumnEmpty();
        boolean readyDateColumnIsEmptyForReadyStatusNode = gridPage().isSelectedNodesReadyDateColumnEmpty();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        // Check that node has a 'Ready' status in the sibling metadata pane
        gridPage().rightClickByNodeTargetValue(nodeValues[1]);
        gridContextMenu().findDocumentInHierarchy();
        boolean broughtToCorrectNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(nodeValues[1]);
        Assertions.assertTrue(broughtToCorrectNodeAfterFindDocumentInHierarchy, "Expected to be brought to " + nodeValues[1] + " but we were not");

        boolean selectedNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();

        // Filter for the second node
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();

        // Check that the selected node's publishing status id is correct
        gridPage().selectByNodeTargetValue(nodeValues[0]);
        String nodePublishingStatusId = gridPage().getSelectedNodesPublishingStatusId();
        boolean nodePublishingStatusIsNotPublished = nodePublishingStatusId.equals("0");

        // Check that node has a 'Not Published' status in the sibling metadata pane
        gridPage().rightClickByNodeTargetValue(nodeValues[0]);
        gridContextMenu().findDocumentInHierarchy();
        boolean broughtToCorrectNodeAfterFindDocumentInHierarchy2 = siblingMetadataPage().getSelectedNodeValue().equals(nodeValues[0]);
        Assertions.assertTrue(broughtToCorrectNodeAfterFindDocumentInHierarchy2, "Expected to be brought to " + nodeValues[0] + " but we were not");

        //Set node to 'Approve Status' and check radio buttons appear
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextNodesOnlyWindow();
        gridPage().rightClickByNodeTargetValue(nodeValues[0]);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        boolean publishNowRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        boolean publishTonightRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(oneStepApprovalApproveSelectionHeaderIsDisplayed, "The page's header text should be " + MainHeaderElements.PUBLISH_APPROVE_TEXT_NODES_ONLY_PAGE_HEADER + " but was not"),
            () -> Assertions.assertTrue(isNodeHierarchyColumnDisplayed,"The Node Hierarchy column is not displayed when it should be"),
            () -> Assertions.assertTrue(isRangeColumnWithNoHeaderTextDisplayed,"The untitled Range column is not displayed when it should be"),
            () -> Assertions.assertTrue(isFlagColumnWithNoHeaderTextDisplayed,"The untitled Flag column is not displayed when it should be"),
            () -> Assertions.assertTrue(isVolColumnDisplayed,"The Vol column is not displayed when it should be"),
            () -> Assertions.assertTrue(isStatusColumnDisplayed,"The Status column is not displayed when it should be"),
            () -> Assertions.assertTrue(isCodeColumnDisplayed,"The Code column is not displayed when it should be"),
            () -> Assertions.assertTrue(isKeywordColumnDisplayed,"The Keyword column is not displayed when it should be"),
            () -> Assertions.assertTrue(isValueColumnDisplayed,"The Value column is not displayed when it should be"),
            () -> Assertions.assertTrue(isStartDateColumnDisplayed,"The Start Date column is not displayed when it should be"),
            () -> Assertions.assertTrue(isEndDateColumnDisplayed,"The End Date column is not displayed when it should be"),
            () -> Assertions.assertTrue(isModifiedByColumnDisplayed,"The Modified By column is not displayed when it should be"),
            () -> Assertions.assertTrue(isModifiedDateColumnDisplayed,"The Modified Date column is not displayed when it should be"),
            () -> Assertions.assertTrue(isPublishingStatusWithNoHeaderTextColumnDisplayed, "The untitled Publishing Status column is not displayed when it should be"),
            () -> Assertions.assertTrue(publishingStatusColumnHasNoHeaderText, "The untitled Publishing Status column has header text when it should not"),
            () -> Assertions.assertTrue(isReadyUserColumnDisplayed,"The Ready User column is not displayed when it should be"),
            () -> Assertions.assertTrue(isReadyDateColumnDisplayed,"The Ready Date column is not displayed when it should be"),
            () -> Assertions.assertTrue(selectedNodeIsSetToReady,"The selected node should have a publishing status of 'Ready' but it does not"),
            () -> Assertions.assertTrue(nodePublishingStatusIsNotPublished, "The selected node should have a publishing status id of '0' signifying 'Not Published' but it does not"),
            () -> Assertions.assertTrue(nodePublishingStatusIsReady, "The selected node should have a publishing status id of '1' signifying 'Ready' but it does not"),
            () -> Assertions.assertFalse(readyUserColumnIsEmptyForReadyStatusNode, "The selected node should have a value in the 'Ready User' column but it does not"),
            () -> Assertions.assertFalse(readyDateColumnIsEmptyForReadyStatusNode, "The selected node should have a value in the 'Ready' date column but it does not"),
            () -> Assertions.assertTrue(publishNowRadioButtonAppears, "The 'Publish Now' radio button should appear but it does not"),
            () -> Assertions.assertTrue(publishTonightRadioButtonAppears, "The 'Publish Tonight' radio button should appear but it does not")
        );
    }
    
    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}

