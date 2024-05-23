package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.pubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.ContentPreferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class PubNavigateEvaluationOverarchingTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-6637, HALCYONST-7043, HALCYONST-8255, HALCYONST-7394, HALCYONST-8252, HALCYONST-7725 <br>
     * SUMMARY: Verifies basic functionality and elements on the Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void issuesAfterPubCompleteUITest()
    {
        String pennsylvaniaAdminCodeContentSet = ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT.getCode();
        String pennsylvaniaAdminCodeContentSetText = ContentSets.PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT.getName();
        String holdNodeContentUuid = "I5814CF908E6A11DEA1A9CF3F7A575F80";
        String ltcFailureContentUuid = "I8D5660B090CC11DE9819E4AEF12068F2";
        String cwbFailureContentUuid = "I698B32B06D5511EBBE65AEECEADC94F2";
        String cwbFailureContentUuid2 = "IC15FA2506D5511EB99F68F649C6B09B0";
        String publishingContentUuid = "IBCDC9620FC4711E7826FD99F8B23008B";
        List<String> publishingStatuses = Arrays.asList("(Select All)", "CODESBENCH_FAILURE", "HOLD_NODE", "LOADED_TO_WESTLAW", "LTC_FAILURE", "PUBLISHED_TO_PUB", "PUBLISHING");
        String ruleKeyword = "R. ";
        String sectionKeyword = "Sec. ";
        String titleKeyword = "T. ";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), pennsylvaniaAdminCodeContentSet, uatConnection);

        // Hold Node
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(holdNodeContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String holdNodeValue = HierarchyDatabaseUtils.getNodeValue(holdNodeContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);

        // LTC Failure Node
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(ltcFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String ltcFailureValue = HierarchyDatabaseUtils.getNodeValue(ltcFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);

        // CodesBench Failure Node
        PublishingDatabaseUtils.setPublishingNodeToCodesbenchFailure(cwbFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToCodesbenchFailure(cwbFailureContentUuid2, pennsylvaniaAdminCodeContentSet, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(cwbFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String cwbFailureValue = HierarchyDatabaseUtils.getNodeValue(cwbFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String cwbFailureCodeName = HierarchyDatabaseUtils.getNodeCodeName(cwbFailureContentUuid, uatConnection);

        // Publishing Node
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(publishingContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String publishingValue = HierarchyDatabaseUtils.getNodeValue(publishingContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);
        String publishingCodeName = HierarchyDatabaseUtils.getNodeCodeName(publishingContentUuid, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(pennsylvaniaAdminCodeContentSetText);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(holdNodeContentUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setPublishApproved();

        //Check that workflow finishes
        yourWorkflowHasBeenCreatedPage().clickLink();
        String workflowId = workflowDetailsPage().getWorkflowID();
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished,"The workflow did not finish: " + workflowId);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        yourWorkflowHasBeenCreatedPage().enterTheInnerFrame();
        yourWorkflowHasBeenCreatedPage().clickClose();
        yourWorkflowHasBeenCreatedPage().breakOutOfFrame();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        //The following will allow us to test that a workflow ID for a node is correctly displayed on the Publishing UI
        boolean oneStepApprovalToolboxWindowOpened = publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        Assertions.assertTrue(oneStepApprovalToolboxWindowOpened, "One Step Approval Publishing UI opened");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(cwbFailureCodeName);
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(cwbFailureValue);

        gridPage().rightClickByNodeTargetValue(cwbFailureValue);
        gridContextMenu().approvedStatus();

        toolbarPage().clickNext();
        toolbarPage().clickPublishNowRadioButton();
        toolbarPage().clickSubmit();

        gridPage().waitForToastMessageToAppear();
        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyMenu().goToPubNavigate();

        // Mock up node with Hold Node status
        hierarchySearchPage().searchContentUuid(holdNodeContentUuid);

        if (!siblingMetadataPage().isSelectedNodeHoldNodeStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setHoldNode();
        }

        // Mock up node with Codesbench Failure status
        hierarchySearchPage().searchContentUuid(cwbFailureContentUuid);
        boolean selectedNodeIsPublishedToPub = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();
        hierarchySearchPage().searchContentUuid(cwbFailureContentUuid2);
        String cwbFailureValue2 = siblingMetadataPage().getSelectedNodeValue();
        boolean selectedNodeIsCodesbenchFailureStatus = siblingMetadataPage().isSelectedNodeCodesbenchFailureStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectedNodeIsPublishedToPub, "Node is published to pub after sending the node through via the One Step Approval UI"),
            () -> Assertions.assertTrue(selectedNodeIsCodesbenchFailureStatus, "Node is CodesBench Failure status after sending the above node through via the One Step Approval UI")
        );

        // Mock up node with LTC Failure status
        hierarchySearchPage().searchContentUuid(ltcFailureContentUuid);
        if (!siblingMetadataPage().isSelectedNodeHoldNodeStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setHoldNode();
        }

        PublishingDatabaseUtils.setPublishingNodeToLTCFailure(ltcFailureContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);

        // Mock up node with Publishing status
        hierarchySearchPage().searchContentUuid(publishingContentUuid);
        if (!siblingMetadataPage().isSelectedNodeHoldNodeStatus())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setHoldNode();
        }

        PublishingDatabaseUtils.setPublishingNodeToPublishing(publishingContentUuid, pennsylvaniaAdminCodeContentSet, uatConnection);

        boolean pubNavigateEvaluationIssuesAfterPubCompleteWindowOpened = publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        Assertions.assertTrue(pubNavigateEvaluationIssuesAfterPubCompleteWindowOpened, "Pub Navigate Evaluation Issues After Pub Complete window did open");
        gridPage().waitForGridLoaded();

        // HALCYONST-6637, HALCYONST-7394, HALCYONST-7892
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(pennsylvaniaAdminCodeContentSetText);
        boolean issuesAfterPubCompleteHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_PUB_NAVIGATE_EVALUATION_PAGE_HEADER);
        boolean refreshCurrentGridButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.REFRESH);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean startDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.START_DATE_COLUMN_XPATH);
        boolean endDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.END_DATE_COLUMN_XPATH);
        boolean publishingStatusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.PUBLISHING_STATUS_COLUMN_XPATH_2);
        boolean statusSetDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_SET_DATE_COLUMN_XPATH);
        boolean workflowIdColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.WORKFLOW_ID_COLUMN_XPATH);
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        gridHeaderPage().openMenuForPublishingStatusColumn2();
        gridHeaderFiltersPage().clickFilterIcon();

        // HALCYONST-6637, HALCYONST-7394
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        List<String> publishingStatusColumnActualFilterValues = gridHeaderFiltersPage().getFilterValues();
        boolean publishingStatusColumnFilterValuesMatchExpected = publishingStatuses.equals(publishingStatusColumnActualFilterValues);

        hierarchyPubNavigatePage().switchToPubNavigatePage();
        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(holdNodeValue);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
//        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + holdNodeValue);
        gridPage().selectNodebyHierarchyColumnValue(holdNodeValue);
        String holdNodeWorkflowId = gridPage().getSelectedNodesWorkflowId();
        String holdNodeStatusBeforeSubmissionPage = gridPage().getSelectedNodesPublishingStatus();

        // HALCYONST-6637, HALCYONST-7394
//        gridPage().rightClickByNodeTargetValue(holdNodeValue);
        gridPage().rightClickByNodeTargetValueOnlyDiv(holdNodeValue);
        boolean selectForUpdatedStatusContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_SELECTION_XPATH);
        boolean findDocumentInWipContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean findDocumentInPubContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findPublishingWorkflowContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_PUBLISHING_WORKFLOW_XPATH);

        gridContextMenu().findDocumentInPub();
        boolean selectedNodeIsHoldNodeStatus = siblingMetadataPage().isSelectedNodeHoldNodeStatus();

        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(cwbFailureCodeName);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
//        gridPage().selectNodebyHierarchyColumnValue(titleKeyword + cwbFailureValue2);
        gridPage().selectNodebyHierarchyColumnValue(cwbFailureValue2);
        String codesbenchFailureNodeStatusBeforeSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String codesbenchFailureNodeStatusSetDateBeforeSubmissionPage = gridPage().getSelectedNodesStatusSetDate();
        String codesbenchFailureNodeWorkflowIdBeforeSubmissionPage = gridPage().getSelectedNodesWorkflowId();

        // HALCYONST-6637
//        gridPage().rightClickByNodeTargetValue(cwbFailureValue2);
        gridPage().rightClickByNodeTargetValueOnlyDiv(cwbFailureValue2);
        gridContextMenu().findDocumentInPub();
        boolean selectedNodeIsCodesbenchFailureStatusAfterFindDocInPub = siblingMetadataPage().isSelectedNodeCodesbenchFailureStatus();

        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(ltcFailureValue);
//        gridPage().selectNodebyHierarchyColumnValue(ruleKeyword + ltcFailureValue);
        gridPage().selectNodebyHierarchyColumnValue(ltcFailureValue);
        String ltcFailureNodeStatusBeforeSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String ltcFailureNodeStatusSetDateBeforeSubmissionPage = gridPage().getSelectedNodesStatusSetDate();

        // HALCYONST-6637
//        gridPage().rightClickByNodeTargetValue(ltcFailureValue);
        gridPage().rightClickByNodeTargetValueOnlyDiv(ltcFailureValue);
        gridContextMenu().findDocumentInPub();
        boolean selectedNodeIsLtcFailureStatus = siblingMetadataPage().isSelectedNodeLtcFailureStatus();

        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(publishingCodeName);
//        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + publishingValue);
        gridPage().selectNodebyHierarchyColumnValue(publishingValue);
        String publishingNodeStatusBeforeSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String publishingNodeStatusSetDateBeforeSubmissionPage = gridPage().getSelectedNodesStatusSetDate();

        // HALCYONST-6637
//        gridPage().rightClickByNodeTargetValue(publishingValue);
        gridPage().rightClickByNodeTargetValueOnlyDiv(publishingValue);
        gridContextMenu().findDocumentInPub();
        boolean selectedNodeIsPublishingStatus = siblingMetadataPage().isSelectedNodePublishingStatus();

        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickClearFiltersAndSorts();
        toolbarPage().clickClearFiltersAndSorts(); // Stabilization

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(holdNodeValue, cwbFailureValue2);
//        gridPage().selectMultipleNodesByNodeHierarchyColumnValues(sectionKeyword + holdNodeValue, titleKeyword + cwbFailureValue2);
//        gridPage().selectAllNodesBetweenTwoNodeValueColumnValues(holdNodeValue, cwbFailureValue2);
        gridPage().selectAllnodesBetweenTwoNodeTargetValueOnlyDiv(holdNodeValue, cwbFailureValue2);
        gridPage().rightClickMultipleSelectedNodes();
        gridContextMenu().selectForUpdatedStatus();

        toolbarPage().clickNext();

        // HALCYONST-8701
        int backButtonWidth = toolbarPage().getElementsWidth(ToolbarElements.BACK);
        int backButtonHeight = toolbarPage().getElementsHeight(ToolbarElements.BACK);
        int submitButtonWidth = toolbarPage().getElementsWidth(ToolbarElements.SUBMIT);
        int submitButtonHeight = toolbarPage().getElementsHeight(ToolbarElements.SUBMIT);

//        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + holdNodeValue);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWENTY_SECONDS);
        gridPage().selectNodebyHierarchyColumnValue(holdNodeValue);
        String holdNodeStatusAfterSubmissionPage = gridPage().getSelectedNodesPublishingStatus();

//        gridPage().selectNodebyHierarchyColumnValue(titleKeyword + cwbFailureValue2);
        gridPage().selectNodebyHierarchyColumnValue(cwbFailureValue2);
        String codesbenchFailureNodeStatusAfterSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String codesbenchFailureNodeStatusSetDateAfterSubmissionPage = gridPage().getSelectedNodesStatusSetDate();
        String codesbenchFailureNodeWorkflowIdAfterSubmissionPage = gridPage().getSelectedNodesWorkflowId();

        toolbarPage().clickPublishNowRadioButton();
        toolbarPage().clickSubmit();

        // HALCYONST-7394, HALCYONST-8252
        gridPage().waitForToastMessageToAppear();
        boolean publishingStatusUpdatedMessageAppearsPublishNow = gridPage().checkUpdatedStatusToastMessage();
        Assertions.assertTrue(publishingStatusUpdatedMessageAppearsPublishNow, "Toast message did not appear");

        toolbarPage().clickBack();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(cwbFailureValue2);
//        gridPage().rightClickByNodeTargetValue(cwbFailureValue2);
        gridPage().rightClickByNodeTargetValueOnlyDiv(cwbFailureValue2);
        gridContextMenu().findPublishingWorkflow();
        String workflowId2 = workflowDetailsPage().getWorkflowID();
        boolean westlawNovusPublishNowWorkflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(westlawNovusPublishNowWorkflowFinished, "WestlawNovusPublishNow workflow finished: " + workflowId2);
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();

        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickClearFiltersAndSorts();
        gridHeaderPage().openMenuForStatusSetDateColumn();
        gridHeaderFiltersPage().setFilterDateValue(currentDate);
//        gridPage().selectMultipleNodesByNodeHierarchyColumnValues(ruleKeyword + ltcFailureValue, sectionKeyword + publishingValue);
        gridPage().selectAllnodesBetweenTwoNodeTargetValueOnlyDiv(ltcFailureValue, publishingValue);

        gridPage().rightClickMultipleSelectedNodes();
        gridContextMenu().selectForUpdatedStatus();

        toolbarPage().clickNext();

//        gridPage().selectNodebyHierarchyColumnValue(ruleKeyword + ltcFailureValue);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWENTY_SECONDS);
        gridPage().selectNodebyHierarchyColumnValue(ltcFailureValue);
        String ltcFailureNodeStatusAfterSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String ltcFailureNodeStatusSetDateAfterSubmissionPage = gridPage().getSelectedNodesStatusSetDate();

//        gridPage().selectNodebyHierarchyColumnValue(sectionKeyword + publishingValue);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWENTY_SECONDS);
        gridPage().selectNodebyHierarchyColumnValue(publishingValue);
        String publishingNodeStatusAfterSubmissionPage = gridPage().getSelectedNodesPublishingStatus();
        String publishingNodeStatusSetDateAfterSubmissionPage = gridPage().getSelectedNodesStatusSetDate();

        toolbarPage().clickPublishTonightRadioButton();
        toolbarPage().clickSubmit();

        // HALCYONST-8255
        gridPage().waitForToastMessageToAppear();
        boolean publishingStatusUpdatedMessageAppearsPublishTonight = gridPage().checkUpdatedStatusToastMessage();
        Assertions.assertTrue(publishingStatusUpdatedMessageAppearsPublishTonight, "Toast message did not appear");

        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyPubNavigatePage().switchToPubNavigatePage();

        // HALCYONST-8252, HALCYONST-8255
        hierarchySearchPage().searchContentUuid(holdNodeContentUuid);
        boolean holdNodeIsPublishingStatusAfterPublishNow = siblingMetadataPage().isSelectedNodePublishingStatus()|| siblingMetadataPage().isSelectedNodeWestlawLoadedStatusInPub();
        hierarchySearchPage().searchContentUuid(cwbFailureContentUuid2);
        boolean codesbenchFailureNodeIsPublishingStatusAfterPublishNow = siblingMetadataPage().isSelectedNodePublishingStatus() || siblingMetadataPage().isSelectedNodeWestlawLoadedStatusInPub();;
        hierarchySearchPage().searchContentUuid(ltcFailureContentUuid);
        boolean ltcFailureNodeIsPublishedToPubAfterPublishTonight = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();
        hierarchySearchPage().searchContentUuid(publishingContentUuid);
        boolean publishingNodeIsPublishedToPubAfterPublishTonight = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
                        () -> Assertions.assertTrue(issuesAfterPubCompleteHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_PUB_NAVIGATE_EVALUATION_PAGE_HEADER + " header is not displayed and should be"),
                        () -> Assertions.assertTrue(refreshCurrentGridButtonIsDisplayed, "Refresh Current Grid button is not displayed on this UI and should be"),
                        () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
                        () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range Indicator column is not displayed and should be"),
                        () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
                        () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
                        () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
                        () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
                        () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
                        () -> Assertions.assertTrue(startDateColumnIsDisplayed, "Start Date column is not displayed and should be"),
                        () -> Assertions.assertTrue(endDateColumnIsDisplayed, "End Date column is not displayed and should be"),
                        () -> Assertions.assertTrue(publishingStatusColumnIsDisplayed, "Publishing Status column is not displayed and should be"),
                        () -> Assertions.assertTrue(statusSetDateColumnIsDisplayed, "Status Set Date column is not displayed and should be"),
                        () -> Assertions.assertTrue(workflowIdColumnIsDisplayed, "Workflow ID column is not displayed and should be"),
                        () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),
                        () -> Assertions.assertTrue(publishingStatusColumnFilterValuesMatchExpected, "The filter values in the publishing status column of the publishing UI do not match the expected array list and should"),
                        () -> Assertions.assertTrue(selectForUpdatedStatusContextMenuOptionIsDisplayed, "Select for Updated Status context menu option is not displayed and should be"),
                        () -> Assertions.assertTrue(removeSelectionContextMenuOptionIsDisplayed, "Remove Selection context menu option is not displayed and should be"),
                        () -> Assertions.assertTrue(findDocumentInWipContextMenuOptionIsDisplayed, "Find Document in WIP context menu option is not displayed and should be"),
                        () -> Assertions.assertTrue(findDocumentInPubContextMenuOptionIsDisplayed, "Find Document in PUB context menu option is not displayed and should be"),
                        () -> Assertions.assertTrue(findPublishingWorkflowContextMenuOptionIsDisplayed, "Find Publishing Workflow context menu option is not displayed and should be"),
                        () -> Assertions.assertTrue(selectedNodeIsHoldNodeStatus, "After Find Document In PUB on node with Hold Node status, status in the sibling metadata page is not Hold Node and should be"),
                        () -> Assertions.assertTrue(selectedNodeIsCodesbenchFailureStatusAfterFindDocInPub, "After Find Document In PUB on node with Codesbench Failure status, status in the sibling metadata page is not Codesbench Failure and should be"),
                        () -> Assertions.assertEquals(34, backButtonWidth, "The width of the Back button is incorrect"),
                        () -> Assertions.assertEquals(20, backButtonHeight, "The height of the Back button is incorrect"),
                        () -> Assertions.assertEquals(50, submitButtonWidth, "The width of the Submit button is incorrect"),
                        () -> Assertions.assertEquals(20, submitButtonHeight, "The height of the Submit button is incorrect"),
                        () -> Assertions.assertTrue(westlawNovusPublishNowWorkflowFinished, "After selecting Publish Now, the WestlawNovusUploadAndPublishNow workflow finished"),
                        () -> Assertions.assertTrue(selectedNodeIsLtcFailureStatus, "After Find Document In PUB on node with LTC Failure status, status in the sibling metadata page is not LTC Failure and should be"),
                        () -> Assertions.assertTrue(selectedNodeIsPublishingStatus, "After Find Document In PUB on node with Publishing status, status in the sibling metadata page is not Publishing and should be"),
                        () -> Assertions.assertEquals(holdNodeWorkflowId, holdNodeWorkflowId, "For the node with Hold Node, we should not see an associated workflow ID, but we do. This is incorrect."),
                        () -> Assertions.assertEquals(holdNodeStatusBeforeSubmissionPage, holdNodeStatusAfterSubmissionPage, "For the node with Hold Node, what we see in the Publishing Status column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(codesbenchFailureNodeStatusBeforeSubmissionPage, codesbenchFailureNodeStatusAfterSubmissionPage, "For the node with Codesbench Failure, what we see in the Publishing Status column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(codesbenchFailureNodeStatusSetDateBeforeSubmissionPage, codesbenchFailureNodeStatusSetDateAfterSubmissionPage, "For the node with Codesbench Failure, what we see in the Status Set Date column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(codesbenchFailureNodeWorkflowIdBeforeSubmissionPage, codesbenchFailureNodeWorkflowIdAfterSubmissionPage, "For the node with Codesbench Failure, what we see in the Workflow ID column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(ltcFailureNodeStatusBeforeSubmissionPage, ltcFailureNodeStatusAfterSubmissionPage, "For the node with LTC Failure, what we see in the Publishing Status column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(ltcFailureNodeStatusSetDateBeforeSubmissionPage, ltcFailureNodeStatusSetDateAfterSubmissionPage, "For the node with LTC Failure, what we see in the Status Set Date column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(publishingNodeStatusBeforeSubmissionPage, publishingNodeStatusAfterSubmissionPage, "For the node with Publishing, what we see in the Publishing Status column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertEquals(publishingNodeStatusSetDateBeforeSubmissionPage, publishingNodeStatusSetDateAfterSubmissionPage, "For the node with Publishing, what we see in the Status Set Date column before and after clicking Next is not the same and should be"),
                        () -> Assertions.assertTrue(holdNodeIsPublishingStatusAfterPublishNow, "The Hold node did not update to Publishing status after running Publish Now and should have"),
                        () -> Assertions.assertTrue(codesbenchFailureNodeIsPublishingStatusAfterPublishNow, "The Codesbech Failure node did not update to Publishing status after running Publish Now and should have"),
                        () -> Assertions.assertTrue(ltcFailureNodeIsPublishedToPubAfterPublishTonight, "The LTC Failure node did not update to Published to Pub after running Publish Tonight and should have"),
                        () -> Assertions.assertTrue(publishingNodeIsPublishedToPubAfterPublishTonight, "The Publishing node did not update to Published to Pub after running Publish Tonight and should have")
                );
    }

    /**
     * STORY: Halcyonst-9277 <br>
     * SUMMARY: For the Pub Navigate Evaluation UI users have the choice to push the documents through using "Publish Now"
     * to initiate a WestlawNovusPublishNow workflow which skips the Upload process completely. This workflow is being initiated,
     * but the status of the nodes selected is being changed in WIP. <br>
     * USER: legal <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void pubNavigateEvaluationUiPublishNowOptionChangeingWIPStatus()
    {
        String nodeUuid = "I7B48C6F014EE11DA8AC5CD53670E6B4E";
        String contentUuid = "I1B0CA6C01AF311DAB310FB76B2E4F553";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        /*
          This is mocking up data for the test
         */
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().setPublishApproved();

        //verify workflow finishes
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        yourWorkflowHasBeenCreatedPage().clickLink();
        String workflowId = workflowDetailsPage().getWorkflowID();
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();

        Assertions.assertTrue(workflowFinished, "workflow did not finish: " + workflowId);
        workflowDetailsPage().closeAndSwitchToYourWorkflowHasBeenCreatedPage();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        yourWorkflowHasBeenCreatedPage().clickClose();

        //should be approved or complete
        boolean StatusNowSetToComplete = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();
        Assertions.assertTrue(StatusNowSetToComplete, "Publishing status is Approved or Complete");

        boolean hierachyPubnavigatePageAppeared = hierarchyMenu().goToPubNavigate();

        Assertions.assertTrue(hierachyPubnavigatePageAppeared, "The hierarchy pub navigate page did not appear");
        boolean publishingStatusCompleteInPub = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();
        Assertions.assertTrue(publishingStatusCompleteInPub, "Publishing status in pub Nav should be complete");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setHoldNode();
        siblingMetadataPage().waitForGridRefresh();

        /*
         THIS IS WHERE THE TEST OFFICALY STARTS
         */
        hierarchyMenu().goToNavigate();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();

        //go to publishing Status Reports-pub Navigate issues after pub Complete
        boolean pubNavigateEvaluationIssuesAfterPubCompleteWindowOpened = publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        Assertions.assertTrue(pubNavigateEvaluationIssuesAfterPubCompleteWindowOpened, "Pub Navigate Evaluation Issues After Pub Complete window did open");
        gridPage().waitForGridLoaded();

        //right click node and select update status
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(nodeUuid);
        gridPage().rightClickByNodeTargetValue(nodeValue);
        gridContextMenu().selectForUpdatedStatus();
        toolbarPage().clickNext();

        //click publish now -> submit -> back -> requery and reload
        toolbarPage().clickPublishNowRadioButton();
        toolbarPage().clickSubmit();
        toolbarPage().clickBack();

        //close and switch to hierarchy edit and go to tools -> workflow reporting
        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();

        //select WestlawNovusPublishNow workflow and verify it finishes
        boolean workflowForWestlawNovusFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("WestlawNovusPublishNow", "SOS.IAT", "",  user().getUsername());
       String workflowId2 = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowForWestlawNovusFinished, "workflow For WestlawNovusPublishNow Finished: " + workflowId2);
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();


        //verify node no longer appears in grid
        publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        toolbarPage().clickRequeryAndReloadFromDatabase();
        boolean nodeNoLongerAppearsInGrid = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue);
        Assertions.assertTrue(nodeNoLongerAppearsInGrid, "After requery and reload the node should not  be in the grid");


        //in hierarchy Nav right click and refresh selection -> verify not published
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean verifyStatusNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        //hierarchy Pub nav -> verify status is loaded to westlaw
        hierarchyMenu().goToPubNavigate();
        hierarchyPubNavigateSearchPage().searchNodeUuid(nodeUuid);
        boolean verifyPubNavStatusIsLoadedToWestlaw = siblingMetadataPage().isSelectedNodeCompleteStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(verifyStatusNotPublished, "Status in hierarchy Nav is not publihsed"),
            () -> Assertions.assertTrue(verifyPubNavStatusIsLoadedToWestlaw, "Status in Pub Nav Loaded To Westlaw")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
