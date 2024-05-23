package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.VolumeSelectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextAndNodNodesByVolumeOverarchingTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUG - Halcyonst-14535 <br>
     * SUMMARY - This test verifies the basic functionality of the Publish Approve-Text and NOD nodes by volume UI <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void oneStepApprovalApproveSelectionByVolumeUITest()
    {
        String contentUuid = "IF7DCED601B2211DAB311FB76B2E4F553";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String vols = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid, contentSetIowa, uatConnection);
        String value = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean publishingPageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(publishingPageLoaded, "The Publish Approve-Text and NOD nodes by volume Page loaded");

        //check the volume text
        boolean loadingCandidatesAppeared = publishingMenu().isElementDisplayed(GridElements.LOADING_CANDIDATES_XPATH);
        boolean volumeSelectedRequired = gridPage().isElementDisplayed(GridElements.VOLUME_SELECTION_IS_REQUIRED_XPATH);

        int selectableRows = gridPaginationPage().getTotalNumberOfSelectableRows();
        Assertions.assertEquals(0, selectableRows, "Selectable rows count should be 0 until a volume is selected");

        toolbarPage().clickVolumeSelection();
        boolean availableVolumesAppears = volumeSelectionPage().isElementDisplayed(VolumeSelectionPageElements.AVAILABLE_VOLUMES_TITLE);
        boolean selectedVolumesAppears = volumeSelectionPage().isElementDisplayed(VolumeSelectionPageElements.SELECTED_VOLUMES_TITLE);
        Assertions.assertTrue(availableVolumesAppears, "Available Volumes column Should Appear");
        Assertions.assertTrue(selectedVolumesAppears, "Selected Volumes column Should Appear");

        //check that the selected volumes pane is empty
        int numberOfVolumesAvailable = volumeSelectionPage().getNumberOfAvialableVolumes();
        int numberOfVolumesSelected = volumeSelectionPage().getNumberOfSelectedVolumes();
        Assertions.assertNotEquals(0, numberOfVolumesAvailable, "Available volumes column should NOT be empty");
        Assertions.assertEquals(0, numberOfVolumesSelected, "Selected volumes column should be empty");

        volumeSelectionPage().clickCheckBoxForVolume(vols);
        volumeSelectionPage().clickAdd();

        boolean volumeNoLongerUnderAvailableVolumes = volumeSelectionPage().isElementDisplayed(String.format(VolumeSelectionPageElements.CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_AVAILABLE_VOLUME, vols));
        boolean volumeAppearsUnderSelectedVolumesColumn = volumeSelectionPage().isElementDisplayed(String.format(VolumeSelectionPageElements.CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_SELECTED_VOLUME, vols));
        Assertions.assertTrue(volumeAppearsUnderSelectedVolumesColumn, "The volume should now appear under selected volumes");
        Assertions.assertFalse(volumeNoLongerUnderAvailableVolumes, "The volume should no longer appear under available volumes");
        volumeSelectionPage().clickConfirm();

        int updatedSelectedRows = gridPaginationPage().getTotalNumberOfSelectableRows();
        Assertions.assertNotEquals(0, updatedSelectedRows, "The grid should load with the selected volume data");

        //Verify volume select text changed
        boolean selectedVolumeTextUpdated = gridPage().isElementDisplayed(String.format(GridElements.SELECTED_VOLUMES_TEXT, vols));

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(value);
        boolean selectableNodesAfterFilter = gridPaginationPage().getTotalNumberOfSelectableRows() <= 2;

        gridPage().rightClickByNodeTargetValue(value);
        gridContextMenu().approvedStatus();
        boolean isBackgroundGreen = gridPage().isBackgroundGreenForSelectedRow();

        toolbarPage().clickNext();
        boolean isBackgroundGreenAfterClickingNext = gridPage().isBackgroundGreenForDeselectedLastRow();

        toolbarPage().clickPublishNowRadioButton();
        toolbarPage().clickSubmit();
        gridPage().waitForToastMessageToAppear();
        boolean publishingStatusUpdatedMessageAppears = gridPage().checkUpdatedStatusToastMessage();

        toolbarPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowForWestlawNovusFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("WestlawNovusUploadAndPublishNow", "SOS.IAT", "",  user().getUsername());
        String workflowId2 = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowForWestlawNovusFinished, "workflow For WestlawNovusPublishNow finished: " + workflowId2);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(contentUuid);
        boolean isPublishingStatusApproved = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(volumeSelectedRequired, "Volume selection required text should appear"),
            ()-> Assertions.assertTrue(loadingCandidatesAppeared, "loading candidates text should appear"),
            ()-> Assertions.assertTrue(selectedVolumeTextUpdated, "Selected volumes text should have updated"),
            ()-> Assertions.assertTrue(selectableNodesAfterFilter, "number of selectable rows should be "),
            ()-> Assertions.assertTrue(isBackgroundGreen, "The background for selected row should be green"),
            ()-> Assertions.assertTrue(isBackgroundGreenAfterClickingNext, "The background for selected row after clicking next should be light green"),
            ()-> Assertions.assertTrue(publishingStatusUpdatedMessageAppears, "After submit we should see a toast message appear"),
            ()-> Assertions.assertTrue(isPublishingStatusApproved, "Publishing Status Should now be approved")
        );
    }

    /**
     * STORY/BUG - Halcyonst-14535 <br>
     * SUMMARY - This test verifies all context menu items appear on right click for the Publish Approve-Text and NOD nodes by volume page<br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void oneStepApprovalApproveSelectionByVolumeContextMenuTest()
    {
        String contentUuid = "IF7DCED601B2211DAB311FB76B2E4F553";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String vols = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid, contentSetIowa, uatConnection);
        String value = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean publishingPageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(publishingPageLoaded, "The Publish Approve-Text and NOD nodes by volume Page loaded");

        toolbarPage().clickVolumeSelection();

        volumeSelectionPage().clickCheckBoxForVolume(vols);
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridPage().rightClickByNodeTargetValue(value);
        boolean approvedStatusContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.APPROVED_STATUS_XPATH);
        boolean removeApprovedStatusContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_APPROVED_STATUS_XPATH);
        boolean findDocumentInHierarchyContextMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_HIERARCHY_XPATH);
        boolean copyMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportMenuOptionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(approvedStatusContextMenuOptionIsDisplayed, "Selected nodes For approved status should be a menu option"),
            ()-> Assertions.assertTrue(removeApprovedStatusContextMenuOptionIsDisplayed, "Remove approved status should be a menu option"),
            ()-> Assertions.assertTrue(findDocumentInHierarchyContextMenuOptionIsDisplayed, "Find documents in hierarchy should be a menu option"),
            ()-> Assertions.assertTrue(copyMenuOptionIsDisplayed, "copy should be a menu option"),
            ()-> Assertions.assertTrue(copyWithHeadersMenuOptionIsDisplayed, "copy with Headers should be a menu option"),
            ()-> Assertions.assertTrue(exportMenuOptionIsDisplayed, "export should be a menu option")
        );
    }
}
