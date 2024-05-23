package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.westlawloadcomplete;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class WestlawLoadCompleteTests extends TestService
{
    Connection uatConnection;

    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();

    /**
     * STORY: HALCYONST-6637, HALCYONST-7043, HALCYONST-8255, Halcyonst-14472 <br>
     * SUMMARY: Verifies basic functionality and elements on the Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void loadCompleteWLReviewTest()
    {
        String contentUuidToday = "I6FE727901B0411DAB311FB76B2E4F553";
        String contentUuidYesterday = "I7016EA201B0411DAB311FB76B2E4F553";
        String contentUuid30DaysAgo = "I702590201B0411DAB311FB76B2E4F553";
        String contentUuid31DaysAgo = "I8ADE6F20FB1111DAA93FDD8159A93D60";
        String sectionNodeKeyword = "= ";
        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String yesterdaysDate = DateAndTimeUtils.getYesterdayDateMMddyyyy();
        String todaysDateMinus30 = DateAndTimeUtils.getXdaysFromCurrentDateMMddyyy(30);
        String todaysDateMinus31 = DateAndTimeUtils.getXdaysFromCurrentDateMMddyyy(31);

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuidToday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.updateNodesLoadedToWestlawDate(todaysDate, contentUuidToday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuidYesterday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.updateNodesLoadedToWestlawDate(yesterdaysDate, contentUuidYesterday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid30DaysAgo, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.updateNodesLoadedToWestlawDate(todaysDateMinus30, contentUuid30DaysAgo, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid31DaysAgo, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.updateNodesLoadedToWestlawDate(todaysDateMinus31, contentUuid31DaysAgo, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //This test has a verification that there is no Next button on the UI. In other Publishing UIs,
        //the next button appears only if the user is listed as a publish approvers. In adding the user
        // to the list of publish approvers, we will have better overall coverage for this verification.
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        String todaysDateNodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuidToday, contentSetIowa, uatConnection);
        String yesterdaysDateNodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuidYesterday, contentSetIowa, uatConnection);
        String todaysDateMinus30NodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid30DaysAgo, contentSetIowa, uatConnection);
        String todaysDateMinus31NodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid31DaysAgo, contentSetIowa, uatConnection);

        boolean loadCompleteWlReviewWindowOpened = publishingMenu().goToPublishingStatusReportsLoadCompleteWlReview();
        Assertions.assertTrue(loadCompleteWlReviewWindowOpened, "Load Complete - WL Review window opened");
        gridPage().waitForGridLoaded();

        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean issuesAfterPubCompleteHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_WESTLAW_LOAD_COMPLETE_PAGE_HEADER);
        boolean isNextButtonDisplayed = mainHeaderPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean isRefreshButtonDisplayed = mainHeaderPage().isElementDisplayed(ToolbarElements.LOAD_COMPLETE_FOR_LAST_30_DAYS_BUTTON);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean startDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.START_DATE_COLUMN_XPATH);
        boolean endDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.END_DATE_COLUMN_XPATH);
        boolean lastLawTrackingColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.LAW_TRACKING_COLUMN_XPATH);
        boolean wlLoadCompleteDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.WL_LOAD_COMPLETE_DATE_COLUMN_XPATH);
        boolean workflowIdColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.WORKFLOW_ID_COLUMN_XPATH);
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        boolean nodeWithLoadCompleteDateOfTodaysDateAppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionNodeKeyword + todaysDateNodeValue);
        Assertions.assertTrue(nodeWithLoadCompleteDateOfTodaysDateAppearsInGrid, "Node with Load Complete date of today's date does not appear in the grid and should");

        boolean nodeWithLoadCompleteDateOfYesterdaysDateAppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionNodeKeyword + yesterdaysDateNodeValue);

        toolbarPage().clickLoadCompleteForPast30DaysButton();

        boolean nodeWithLoadCompleteDateOfTodaysDateMinus30AppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionNodeKeyword + todaysDateMinus30NodeValue);
        boolean nodeWithLoadCompleteDateOfTodaysDateMinus31AppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionNodeKeyword + todaysDateMinus31NodeValue);

        gridPage().selectByNodeTargetValueOnlyDiv(todaysDateNodeValue);
        String nodeStartDateInPubUi = gridPage().getSelectedNodesStartDateWithLeadingZeros();//Halcyonst-14472

        gridPage().rightClickByNodeTargetValueOnlyDiv(todaysDateNodeValue);
        gridContextMenu().findDocumentInWip();
        String todaysDateNodeValueAfterFindDocumentInWip = siblingMetadataPage().getSelectedNodeValue();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToLoadCompleteWLReviewWindow();

        gridPage().rightClickByNodeTargetValueOnlyDiv(todaysDateNodeValue);
        gridContextMenu().findDocumentInPub();
        String todaysDateNodeValueAfterFindDocumentInPub = siblingMetadataPage().getSelectedNodeValue();

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuidToday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuidYesterday, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid30DaysAgo, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid31DaysAgo, contentSetIowa, uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(issuesAfterPubCompleteHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_WESTLAW_LOAD_COMPLETE_PAGE_HEADER + "header is not displayed and should be"),
            () -> Assertions.assertFalse(isNextButtonDisplayed, "Next button is displayed and should not be"),
            () -> Assertions.assertTrue(isRefreshButtonDisplayed, "Refresh button is not displayed and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
            () -> Assertions.assertTrue(startDateColumnIsDisplayed, "Start Date column is not displayed and should be"),
            () -> Assertions.assertTrue(endDateColumnIsDisplayed, "End Date column is not displayed and should be"),
            () -> Assertions.assertTrue(lastLawTrackingColumnIsDisplayed, "Last Law Tracking column is not displayed and should be"),
            () -> Assertions.assertTrue(wlLoadCompleteDateColumnIsDisplayed, "WL Load Complete Date column is not displayed and should be"),
            () -> Assertions.assertTrue(workflowIdColumnIsDisplayed, "Workflow ID column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeWithLoadCompleteDateOfYesterdaysDateAppearsInGrid, "Node with Load Complete date of yesterday's date does not appear in the grid and should"),
            () -> Assertions.assertTrue(nodeWithLoadCompleteDateOfTodaysDateMinus30AppearsInGrid, "Node with Load Complete date of today's date minus 30 does not appear in the grid and should"),
            () -> Assertions.assertFalse(nodeWithLoadCompleteDateOfTodaysDateMinus31AppearsInGrid, "Node with Load Complete date of today's date minus 31 does appear in the grid and should not"),
            () -> Assertions.assertEquals(todaysDateNodeValue, todaysDateNodeValueAfterFindDocumentInWip, "After Find Document in Wip, node value we are brought to is not " + todaysDateNodeValue + " and should be"),
            () -> Assertions.assertEquals("12/31/2005", nodeStartDateInPubUi, "date in the ui of the node with start date of today should match with today's date"),
            () -> Assertions.assertEquals(todaysDateNodeValue, todaysDateNodeValueAfterFindDocumentInPub, "After Find Document in Pub, node value we are brought to is not " + todaysDateNodeValue + " and should be")
        );
    }

    /**
     * STORY: Halcyonst-8933 <br>
     * SUMMARY: When node is set to Delete status the node should appear in the ui as deleted in the Load Complete ui<br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void wlReviewUiAndPublishingUiDoNotDisplayDeletesWithStrikethrough()
    {
        String nodeUuid = "I9495FE3014F211DA8AC5CD53670E6B4E";
        String contentUuid = "I3ED93EC01AF711DAB310FB76B2E4F553";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatabaseUtils.updateIsDeleteFlagToDeletedHierarchyNavigate(nodeUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.updateNodesLoadedToWestlawDate(DateAndTimeUtils.getCurrentDateMMddyyyy(), contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Publishing -> Publishing dashboards -> Loaded Complete - Wl Review
        publishingMenu().goToPublishingStatusReportsLoadCompleteWlReview();

        //click load complete for past 30 days
        toolbarPage().clickLoadCompleteForPast30DaysButton();

        //filter by node UUID
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(nodeUuid);

        //verify node has strike Through
        gridPage().selectFirstSectionNode();
        boolean isLoadCompleteNodeDeleted = gridPage().isSelectedNodeDeleted();

        //undelete node for clean up:
        HierarchyDatabaseUtils.updateIsDeleteFlagToUndeletedHierarchyNavigate(nodeUuid, contentSetIowa, uatConnection);

        Assertions.assertTrue(isLoadCompleteNodeDeleted, "Node shows up in load complete UI as Deleted");
    }
}
