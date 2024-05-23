package com.thomsonreuters.codes.codesbench.quality.tests.source.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeltaTests extends TestService
{

    /**
     * STORY - HALCYONST-8605 <br>
     * BUG - HALCYONST-11601 <br>
     * SUMMARY - Modify source navigate display to show CP tagged content in specified color <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void ModifySourceNavigateDisplayToShowCPTaggedContentInSpecifiedColorTest()
    {

        String uuid = "IBCE0FAC01F0111E98D18B00554385E22";
        String filterLocationIntegrationStatus = "Target located and ready";
        String viewTag = "CHKPNT";
        String orangeColor = "rgba(255, 179, 71, 1)";

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to Source -> C2012 Navigate
        //STEP 03: Filter for: Deleted: Not Deleted; Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Doc Number: 2303
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //STEP 04: Click the rendition and click the Delta tab.
        //STEP 05: Scroll to the far right of the Delta grid
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();
        //STEP 06: The Location/Integration Status column shows that at least a few deltas show 'Target located, ready'.
        //STEP 07: Right click a delta with a Location/Integration Status of 'Target located, ready'
        sourcePage().selectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyCiteLocate();
        boolean isDeltaWithLocationIntegrationStatusExist = sourceNavigateGridPage().isDeltaWithLocationIntegrationStatusExist(filterLocationIntegrationStatus);
        sourceNavigateGridPage().clickFirstDelta();
        sourceNavigateGridPage().rightClickFirstDelta();
        //STEP 08: Select View -> Target In Hierarchy
        //STEP 09: VERIFY: Hierarchy Edit window appears. The target node is shown seleted in the sibling metadata grid
        Assertions.assertTrue(deltaContextMenu().goToViewTargetInHierarchy(),
                "STEP 09: Hierarchy Edit window DOESN'T appear");
        String siblingMetadataValue = siblingMetadataPage().getSelectedNodeValue();
        //STEP 10: Right click the sibling metadata node and select Online Product Assignments
        //STEP 11: VERIFY: Online Product Maintenance window appears
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().onlineProductAssignments(),
                "STEP 11: Online Product Maintenance window DOESN'T appear");
        //STEP 12: Add the Checkpoint product type tag to the single node, and verify the workflow finishes
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        //STEP 13: Close the Workflow windows and the Online Product Assignments window.
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
        //STEP 14: Go back to the Delta grid and click Refresh
        sourceNavigateGridPage().switchToDeltaGridPage();
        sourcePage().selectAllOnPage();
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyCiteLocate();
        //STEP 15: VERIFY: The delta is highlighted in an orange color
        String deltaColorWithAssignment = sourceNavigateGridPage().getFirstDeltaColorWithLocationIntegrationStatus(filterLocationIntegrationStatus);
        //STEP 16: VERIFY: The Online Product Tag of the first item added on delta tab, filter by Online Product tag
        sourceNavigateFooterToolsPage().selectPublicView("all cols");
        boolean isOnlineProductTagAddedOnDeltaTab = sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        int sizeBeforeFilteringDeltaTab = sourceNavigateGridPage().getRowsNumber();
        sourceNavigateFiltersAndSortsPage().setOnlineProductTagFilter("Y");
        sourceNavigateGridPage().clickFirstItem();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        int sizeAfterFilteringDeltaTab = sourceNavigateGridPage().getRowsNumber();
        sourceNavigateFiltersAndSortsPage().resetOnlineProductTagFilter();
        //STEP 17: VERIFY: The Online Product Tag of the first item added on section tab, filter by Online Product tag, the section is highlighted in an orange color
        sourcePage().goToSectionTab();
        String sectionColorWithAssignment = sourceNavigateGridPage().getFirstRowColor();
        sourceNavigateFooterToolsPage().selectPublicView("all cols");
        boolean isOnlineProductTagAddedOnSectionTab = sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        int sizeBeforeFilteringSectionTab = sourceNavigateGridPage().getRowsNumber();
        sourceNavigateFiltersAndSortsPage().setOnlineProductTagFilter("Y");
        sourceNavigateGridPage().clickFirstItem();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        int sizeAfterFilteringSectionTab = sourceNavigateGridPage().getRowsNumber();
        sourceNavigateFiltersAndSortsPage().resetOnlineProductTagFilter();
        //BUG - HALCYONST-11601
        //STEP 18: VERIFY: The Online Product Tag of the first item added on rendition tab, the rendition is highlighted in an orange color
        sourcePage().goToRenditionTab();
        sourceNavigateFooterToolsPage().selectPublicView("all cols (with CPD Auto Integration)");
        boolean isOnlineProductTagAddedOnRenditionTab = sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        String renditionColorWithCheckpoint = sourceNavigateGridPage().getFirstRowColor();
        sourcePage().goToDeltaTab();
        //STEP 19: Go back to Hierarchy Edit and remove the Checkpoint product tag on the single node, and verify the workflow finishes
        sourceNavigateGridPage().selectFirstDeltaWithLocationIntegrationStatus(filterLocationIntegrationStatus);
        Assertions.assertTrue(deltaContextMenu().goToViewTargetInHierarchy(),
                "STEP 19: Hierarchy Edit window DOESN'T appear");
        siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().onlineProductAssignments(),
                "STEP 19: Online Product Maintenance window DOESN'T appear");
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
        //STEP 20: Go back to the Delta grid and click Refresh
        sourceNavigateGridPage().switchToDeltaGridPage();
        sourcePage().selectAllOnPage();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyCiteLocate();
        //STEP 21: VERIFY: The delta is no longer highlighted in an orange color
        String deltaColorWithoutAssignment = sourceNavigateGridPage().getFirstDeltaColorWithLocationIntegrationStatus(filterLocationIntegrationStatus);
        //STEP 22: VERIFY: The Online Product Tag of the first item removed on delta tab
        boolean isOnlineProductTagRemovedOnDeltaTab = !sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        //STEP 23: VERIFY: The Online Product Tag of the first item removed on section tab, the section is no longer highlighted in an orange color
        sourcePage().goToSectionTab();
        String sectionColorWithoutAssignment = sourceNavigateGridPage().getFirstRowColor();
        boolean isOnlineProductTagRemovedOnSectionTab = !sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        //BUG - HALCYONST-11601
        //STEP 24: VERIFY: The Online Product Tag of the first item removed on rendition tab, the rendition is no longer highlighted in an orange color
        sourcePage().goToRenditionTab();
        boolean isOnlineProductTagRemovedOnRenditionTab = !sourceNavigateGridPage().isOnlineProductTagOfFirstItemAdded();
        String renditionColorWithoutCheckpoint = sourceNavigateGridPage().getFirstRowColor();

        Assertions.assertAll("ModifySourceNavigateDisplayToShowCPTaggedContentInSpecifiedColorTest",
                () -> Assertions.assertTrue(isDeltaWithLocationIntegrationStatusExist,
                        String.format("STEP 06: there IS NO delta with '%s' status in the Location/Integration Status column", filterLocationIntegrationStatus)),
                () -> Assertions.assertNotEquals(siblingMetadataValue, "There isn't selected Sibling Metadata",
                        "STEP 09: the target node IS NOT shown selected in the sibling metadata grid"),
                () -> Assertions.assertEquals(orangeColor, deltaColorWithAssignment,
                        "STEP 15: the delta IS NOT highlighted in an orange color"),
                () -> Assertions.assertTrue(isOnlineProductTagAddedOnDeltaTab,
                        "STEP 16: the online product tag wasn't added on delta tab"),
                () -> Assertions.assertNotEquals(sizeAfterFilteringDeltaTab, sizeBeforeFilteringDeltaTab,
                        "STEP 16: the online product tag filter wasn't applied"),
                () -> Assertions.assertEquals(orangeColor, sectionColorWithAssignment,
                        "STEP 17: the section IS NOT highlighted in an orange color"),
                () -> Assertions.assertTrue(isOnlineProductTagAddedOnSectionTab,
                        "STEP 17: the online product tag  wasn't added on section tab"),
                () -> Assertions.assertNotEquals(sizeAfterFilteringSectionTab, sizeBeforeFilteringSectionTab,
                        "STEP 17: the online product tag filter wasn't applied"),
                () -> Assertions.assertTrue(isOnlineProductTagAddedOnRenditionTab,
                        "STEP 18: the online product tag wasn't added on rendition tab"),
                () -> Assertions.assertEquals(orangeColor, renditionColorWithCheckpoint,
                        "STEP 18: the rendition IS NOT highlighted in an orange color"),
                () -> Assertions.assertNotEquals(orangeColor, deltaColorWithoutAssignment,
                        "STEP 21: the delta IS highlighted in an orange color"),
                () -> Assertions.assertTrue(isOnlineProductTagRemovedOnDeltaTab,
                        "STEP 22: the online product tag wasn't removed on delta tab"),
                () -> Assertions.assertNotEquals(orangeColor, sectionColorWithoutAssignment,
                        "STEP 23: the section IS highlighted in an orange color"),
                () -> Assertions.assertTrue(isOnlineProductTagRemovedOnSectionTab,
                        "STEP 23: the online product tag  wasn't removed on section tab"),
                () -> Assertions.assertTrue(isOnlineProductTagRemovedOnRenditionTab,
                        "STEP 24: the online product tag wasn't removed on rendition tab"),
                () -> Assertions.assertNotEquals(orangeColor, renditionColorWithoutCheckpoint,
                        "STEP 24: the rendition IS highlighted in an orange color"));

    }
}
