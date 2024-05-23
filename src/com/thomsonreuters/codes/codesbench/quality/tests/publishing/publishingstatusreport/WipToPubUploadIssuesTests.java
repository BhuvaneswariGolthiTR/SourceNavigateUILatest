package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.QueryNoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class WipToPubUploadIssuesTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText= ContentSets.IOWA_DEVELOPMENT.getName();

    /**
     * STORY: HALCYONST-7925, HALCONST-6629 <br>
     * SUMMARY: After the split up of the 2 Hierarchy Navigate Troubleshooting UIs, this test verifies basic functionality
     * and the display of elements on the Publish Troubleshooting Approval Upload Issues UI <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void approvalUploadIssuesUITest()
    {
        String nodeUuid = "I261A57C014F311DA8AC5CD53670E6B4E";
        String contentUuid = "I4F061AF01AF911DAB310FB76B2E4F553";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToApprove(contentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        String value = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        boolean selectedNodeIsApprovedStatus = siblingMetadataPage().isSelectedNodeApprovedStatus();
        Assertions.assertTrue(selectedNodeIsApprovedStatus, "Selected node is not approved status and should be");

        boolean toolboxWindowAppeared = publishingMenu().goToPublishingStatusReportsWipToPubUploadIssues();
        Assertions.assertTrue(toolboxWindowAppeared, "Toolbox window appeared");
        gridPage().waitForGridLoaded();

        //HALCONST-6629
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean refreshCurrentGridButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.REFRESH);
        boolean approvalUploadIssuesHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_WIP_TO_PUB_UPLOAD_ISSUES_PAGE_HEADER);
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
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(nodeUuid);

        //HALCONST-7925
        gridPage().rightClickByNodeTargetValue(value);
        boolean selectForUpdatedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_SELECTION_XPATH);
        boolean massSelectionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_XPATH);
        boolean findDocumentInPubIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findDocumentInWipIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean copyIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        //HALCONST-6629, HALCYONST-7925
        gridContextMenu().findDocumentInWip();
        boolean selectedNodeIsApprovedStatusAfterFindDocumentInWip = siblingMetadataPage().isSelectedNodeApprovedStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(nextButtonIsDisplayed, "Next button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(refreshCurrentGridButtonIsDisplayed, "Refresh Current Grid button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(approvalUploadIssuesHeaderIsDisplayed, "Hierarchy Navigate Troubleshooting - Approval/Upload Issues header is not displayed and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range Indicator column is not displayed and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
            () -> Assertions.assertTrue(startDateColumnIsDisplayed, "Start Date column is not displayed and should be"),
            () -> Assertions.assertTrue(endDateColumnIsDisplayed, "End Date column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),
            () -> Assertions.assertTrue(selectForUpdatedStatusIsDisplayed, "Select for Updated Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(removeSelectionIsDisplayed, "Remove Selection does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(massSelectionIsDisplayed, "Mass Selection does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInPubIsDisplayed, "Find Document In PUB does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInWipIsDisplayed, "Find Document In WIP does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyIsDisplayed, "Copy does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyWithHeadersIsDisplayed, "Copy With Headers does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(exportIsDisplayed, "Export does not appear as a context menu option and should "),
            () -> Assertions.assertTrue(selectedNodeIsApprovedStatusAfterFindDocumentInWip, "Selected node is not set to Approved status and should be")
        );
    }

    /**
     * STORY: HALCYONST-6019, HALCYONST-6356, HALCYONST-6364, HALCYONST-6523, HALCYONST-6565, HALCYONST-8075, Halcyonst-13805 <br>
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
        PublishingDatabaseUtils.setPublishingNodeToApprove(errorFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(errorFlagContentUuid, contentSetIowa, uatConnection);
        String errorNodeValue = HierarchyDatabaseUtils.getNodeValue(errorFlagContentUuid, contentSetIowa, uatConnection);

        //warning flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToApprove(warningFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToWarningValidationFlag(warningFlagContentUuid, contentSetIowa, uatConnection);
        String warningNodeValue = HierarchyDatabaseUtils.getNodeValue(warningFlagContentUuid, contentSetIowa, uatConnection);

        //info flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToApprove(infoFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToInfoValidationFlag(infoFlagContentUuid, contentSetIowa, uatConnection);
        String infoNodeValue = HierarchyDatabaseUtils.getNodeValue(infoFlagContentUuid, contentSetIowa, uatConnection);

        //green check flag mockup
        PublishingDatabaseUtils.setPublishingNodeToApprove(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        String greenCheckNodeValue = HierarchyDatabaseUtils.getNodeValue(greenCheckFlagContentUuid, contentSetIowa, uatConnection);

        String questionNodeValue = HierarchyDatabaseUtils.getNodeValue(questionFlagContentUuid, contentSetIowa, uatConnection);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
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

        PublishingDatabaseUtils.setPublishingNodeToApprove(questionFlagContentUuid, contentSetIowa, uatConnection);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        siblingMetadataContextMenu().refreshSelection();
        boolean approvedStatusAfterDBCall = siblingMetadataPage().isSelectedNodeApprovedStatus();
        Assertions.assertTrue(approvedStatusAfterDBCall, "The publishing status should be Approved after we make database call");

        boolean toolboxWindowOpened = publishingMenu().goToPublishingStatusReportsWipToPubUploadIssues();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox did not open and we expected it to");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForFlagColumn();
        gridHeaderFiltersPage().clickFilterIcon();
        List<String> flagColumnActualFilterValues = gridHeaderFiltersPage().getFilterValues();
        boolean flagColumnFilterValuesContainExpected = flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(0)) &&
        flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(1)) &&
        flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(2)) &&
        flagColumnActualFilterValues.contains(flagColumnExpectedFilterValues.get(3));
        Assertions.assertTrue(flagColumnFilterValuesContainExpected, "The flags we expect to be able to filter for do not appear in the list of filter values. Try to manually find the nodes in the grid. Right click one of the nodes and select 'Find Document in WIP. If the node has a flag other than a green check, this is a bug.");
        gridHeaderFiltersPage().setMultipleFilterValues("RED_FLAG", "YELLOW_FLAG", "BLUE_FLAG", "QUERY");

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(sectionKeyword + errorNodeValue, sectionKeyword + warningNodeValue, sectionKeyword + infoNodeValue, sectionKeyword + questionNodeValue);

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsWipToPubUploadIssues();

        boolean questionFlagNodeAppearsInGridAfterGrid = gridPage().isNodeHierarchyColumnValueInGrid(sectionKeyword + questionNodeValue);
        gridPage().rightClickByNodeTargetValue(questionNodeValue);
        gridContextMenu().findDocumentInWip();

        boolean broughtToQuestionNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(questionNodeValue);
        Assertions.assertTrue(broughtToQuestionNodeAfterFindDocumentInHierarchy, "Brought to Question node after Find Document in Hierarchy");

        QueryNoteDatabaseUtils.deleteQueryNote(uatConnection, "questionflagnode", legalUser().getUsername());

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsWipToPubUploadIssues();

        gridPage().rightClickByNodeTargetValue(errorNodeValue);
//        boolean notPublishedStatusIsDisabledForNodeWithErrorValidationFlag = !gridContextMenu().isElementDisabled(GridContextMenuElements.NOT_PUBLISHED_STATUS_XPATH);
        gridContextMenu().findDocumentInWip();

        boolean broughtToErrorNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(errorNodeValue);
        Assertions.assertTrue(broughtToErrorNodeAfterFindDocumentInHierarchy, "Brought to Error node after Find Document in Hierarchy");

        boolean selectedNodeHasErrorValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsWipToPubUploadIssues();

        gridPage().rightClickByNodeTargetValue(warningNodeValue);
//        boolean notPublishedStatusIsDisabledForNodeWithWarningValidationFlag = !gridContextMenu().isElementDisabled(GridContextMenuElements.NOT_PUBLISHED_STATUS_XPATH);
        gridContextMenu().findDocumentInWip();

        boolean broughtToWarningNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(warningNodeValue);
        Assertions.assertTrue(broughtToWarningNodeAfterFindDocumentInHierarchy, "Brought to Warning node after Find Document in Hierarchy");

        boolean selectedNodeHasWarningValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveWarningValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsWipToPubUploadIssues();

        gridPage().rightClickByNodeTargetValue(infoNodeValue);
        gridContextMenu().findDocumentInWip();

        boolean broughtToInfoNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(infoNodeValue);
        Assertions.assertTrue(broughtToInfoNodeAfterFindDocumentInHierarchy, "Brought to Info node after Find Document in Hierarchy");

        boolean selectedNodeHasInfoValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveInfoValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsWipToPubUploadIssues();

        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setFilterValue(sectionKeyword + greenCheckNodeValue);

        gridPage().rightClickByNodeTargetValue(greenCheckNodeValue);
        gridContextMenu().findDocumentInWip();

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
//            () -> Assertions.assertTrue(notPublishedStatusIsDisabledForNodeWithErrorValidationFlag, "Not Published Status is not disabled for the node with an error flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasWarningValidationFlagInHierarchy, "The node with a Warning validation flag in the publishing UI does not appear to have a Warning flag in Hierarchy and should"),
//            () -> Assertions.assertTrue(notPublishedStatusIsDisabledForNodeWithWarningValidationFlag, "Not Published Status is not disabled for the node with a warning flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasInfoValidationFlagInHierarchy, "The node with an Info validation flag in the publishing UI does not appear to have an Info flag in Hierarchy and should"),
            () -> Assertions.assertTrue(selectedNodeHasGreenCheckValidationFlagInHierarchy, "The node with no flag in the publishing UI does not appear to have a Green check flag in Hierarchy and should")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
