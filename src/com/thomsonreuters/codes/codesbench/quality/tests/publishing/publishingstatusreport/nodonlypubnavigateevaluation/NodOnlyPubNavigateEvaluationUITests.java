package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlypubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
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

public class NodOnlyPubNavigateEvaluationUITests extends TestService
{
    Connection connection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-6637, HALCYONST-7043, HALCYONST-8255, HALCYONST-7394, HALCYONST-8252, HALCYONST-7725 <br>
     * SUMMARY: Verifies basic functionality and elements on the Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void nodOnlyPubNavigateEvaluationUITest()
    {
        //mock up BL node with 'Hold' publishing status
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String blNodeUUID = datapodObject.getBluelines().get(0).getNodeUUID();
        String blNodeContentUUID = datapodObject.getBluelines().get(0).getContentUUID();
        PublishingDatabaseUtils.setPubNavigatePublishingHold(blNodeContentUUID, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyMenu().goToPubNavigate();

        //verify node is set to hold status
        hierarchyPubNavigateSearchPage().searchContentUuid(blNodeContentUUID);
        boolean nodeIsSetToHold = siblingMetadataPage().isSelectedNodeHoldNodeStatus();
        Assertions.assertTrue(nodeIsSetToHold);
        String value = siblingMetadataPage().getSelectedNodeValue();

        //Go to Nod-Pub navigate evaluation page and filter for mocked BL hold node
        publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        gridPage().waitForGridLoaded();
        gridPage().click("//button/span[text()='Columns']");
        gridHeaderPage().filterForColumnAndSelect("Start Date");
        gridHeaderPage().filterForColumnAndSelect("End Date");
        gridPage().click("//button/span[text()='Columns']");
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(blNodeUUID);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        gridHeaderFiltersPage().click(ToolbarElements.REFRESH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify Publishing Status Reports > Nod-Only PubNavigate Evaluation UI appears as expected
        boolean nodPubNavigateEvaluationHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_NOD_PUB_NAVIGATE_EVALUATION_PAGE_HEADER);
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean requeryAndReloadButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.RELOAD);
        boolean clearFiltersAndSortsButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.CLEAR_FILTERS);
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

        //Verify Nod-Only Pub Navigate Evaluation context menu UI appears as expected
        gridPage().rightClickByNodeTargetValueOnlyDiv(value);
        boolean selectForUpdatedStatusContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_SELECTION_XPATH);
        boolean findDocumentInPubContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findDocumentInWipContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean copyContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        //Verify 'Find document in WIP' context menu option functions correctly
        gridContextMenu().findDocumentInWip();
        hierarchyNavigatePage().waitForPageLoaded();
        boolean findDocumentInWipBroughtToNodeInHierarchy = siblingMetadataPage().getSelectedNodeUuid().equals(blNodeUUID);
        Assertions.assertTrue(findDocumentInWipBroughtToNodeInHierarchy, "Should bring to mocked BL node in hierarchy navigate after Find Document in WIP");
        hierarchyNavigatePage().breakOutOfFrame();
        mainHeaderPage().switchToPublishingStatusReportsNodOnlyPubNavigateEvaluation();

        //Verify 'Find document in PUB' context menu option functions correctly
        gridPage().rightClickByNodeTargetValueOnlyDiv(value);
        gridContextMenu().findDocumentInPub();
        hierarchyPubNavigatePage().waitForPageLoaded();
        boolean findDocumentInPubBroughtToNodeInPubNavigate = siblingMetadataPage().getSelectedNodeUuid().equals(blNodeUUID);
        Assertions.assertTrue(findDocumentInPubBroughtToNodeInPubNavigate, "Should bring to mocked BL node in pub navigate after Find Document in Pub");
        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsNodOnlyPubNavigateEvaluation();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        gridPage().rightClickByNodeTargetValueOnlyDiv(value);
        gridContextMenu().selectForUpdatedStatus();
        toolbarPage().clickNext();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        gridPage().selectByNodeTargetValueOnlyDiv(value);
        boolean nodeIsHighlightedGreen = gridPage().isBackgroundGreenForSelectedRow();
        boolean backButtonAppears = toolbarPage().doesElementExist(ToolbarElements.BACK);
        boolean submitButtonAppears = toolbarPage().doesElementExist(ToolbarElements.SUBMIT);
        boolean publishNowRadioButtonAppears = toolbarPage().doesElementExist(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        boolean publishTonightRadioButtonAppears = toolbarPage().doesElementExist(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);
        toolbarPage().clickPublishNowRadioButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        toolbarPage().clickSubmit();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);

        toolbarPage().closeCurrentWindowIgnoreDialogue();
        hierarchyPubNavigatePage().switchToPubNavigatePage();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        hierarchyPubNavigateSearchPage().searchContentUuid(blNodeContentUUID);
        boolean nodeIsNotSetToHoldAfter = siblingMetadataPage().isSelectedNodeHoldNodeStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodPubNavigateEvaluationHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_NOD_PUB_NAVIGATE_EVALUATION_PAGE_HEADER + " header is not displayed and should be"),
            () -> Assertions.assertTrue(nextButtonIsDisplayed, "Green next button is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Iowa (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(requeryAndReloadButtonIsDisplayed, "Requery and Reload from Database button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(clearFiltersAndSortsButtonIsDisplayed, "Clear Filters and Sorts button is not displayed on this UI and should be"),
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

            () -> Assertions.assertTrue(selectForUpdatedStatusContextMenuOptionIsDisplayed, "Select for updated status menu option is not displayed and should be"),
            () -> Assertions.assertTrue(removeSelectionContextMenuOptionIsDisplayed, "Remove selection menu option is not displayed and should be"),
            () -> Assertions.assertTrue(findDocumentInPubContextMenuOptionIsDisplayed, "Find document in pub menu option is not displayed and should be"),
            () -> Assertions.assertTrue(findDocumentInWipContextMenuOptionIsDisplayed, "Find document in wip menu option is not displayed and should be"),
            () -> Assertions.assertTrue(copyContextMenuOptionIsDisplayed, "Copy menu option is not displayed and should be"),
            () -> Assertions.assertTrue(copyWithHeadersContextMenuOptionIsDisplayed, "Copy with headers menu option is not displayed and should be"),
            () -> Assertions.assertTrue(exportContextMenuOptionIsDisplayed, "Export menu option is not displayed and should be"),

            () -> Assertions.assertTrue(nodeIsHighlightedGreen, "Node selected should be highlighted green"),
            () -> Assertions.assertTrue(backButtonAppears, "Back button should appear in the toolbar"),
            () -> Assertions.assertTrue(submitButtonAppears, "Submit button should appear in the toolbar"),
            () -> Assertions.assertTrue(publishNowRadioButtonAppears, "Publish Now radio button should appear in the toolbar"),
            () -> Assertions.assertTrue(publishTonightRadioButtonAppears, "Publish Tonight radio button should appear in the toolbar"),
            () -> Assertions.assertFalse(nodeIsNotSetToHoldAfter, "Node is no longer set to hold status after updating")
        );
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
