package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

/**
 * Tests the availability of the Sync context menu option for certain flagged documents as well as
 * certain rendition status documents
 */
public class SyncAvailabilityFlaggedRenditionsAndRenditionStatusesTests extends TestService
{
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that a PREP document doesn't have the Sync option enabled for a legal user <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepDocumentSyncDisabledLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncDisabled, "PREP document has Sync enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that a document with no flags has the Sync option enabled <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void noFlagSyncEnabledTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("IN");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is enabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncEnabled, "Orange Invalid Link flagged document does have Sync enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that an orange Invalid Link flagged document has the Sync option enabled
     * for a non-APVRS document as a legal user <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void orangeInvalidLinkFlagSyncEnabledTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Invalid Link");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("IN");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is enabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncEnabled, "Orange Invalid Link flagged document does have Sync enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that a blue Updated Link flagged document has the Sync option disabled for a non-APVRS document <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void blueUpdatedLinkFlagSyncDisabledTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Updated Link");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncDisabled, "Blue Updated Link flagged document has the Sync option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that a red Error flagged document has the Sync option disabled for a non-APVRS document <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void redErrorFlagSyncDisabledTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Error");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncDisabled, "Red Error flagged document has the sync option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that a yellow Warning flagged document has the Sync option enabled for a non-APVRS document <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void yellowWarningFlagSyncEnabledTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Warning");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("UNCHAPTERED");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is enabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncEnabled, "Yellow Warning flagged document has the Sync option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that an APV document has the Create Prep Document option enabled <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPrepDocumentEnabledOnAPVTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if create prep document option is enabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean createPrepDocEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.createPreparationDocument);
        Assertions.assertTrue(createPrepDocEnabled, "APV document has the Create Prep Document option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that an APV document with a red Error Flag has the Create Prep Document option disabled <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPrepDocumentDisabledOnAPVWithRedErrorFlagTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Error");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("UNCHAPTERED");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if create prep document option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean createPrepDocDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createPreparationDocument);
        Assertions.assertTrue(createPrepDocDisabled, "APV document with a Red Error Flag has the create prep document option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the Create Prep Document option is disabled on a non-APV document <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPrepDocumentDisabledOnNonAPVTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("GN");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if create prep document option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean createPrepDocDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createPreparationDocument);
        Assertions.assertTrue(createPrepDocDisabled, "Create Prep Document option is enabled on a non-APV document");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Create prep document for non-synced westlaw document and verify workflow finishes <br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPrepForNonSyncedWestlawTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("California (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2012");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("0005");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().createPrepDocument();
        sourcePage().switchToSourceNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbCreatePrepDocuments", "", "Create Prep Document");
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished, String.format("Workflow %s did finish", workflowID));
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the View -> Deltas Affecting Same Target option is enabled on PREP documents <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewDeltasAffectingSameTargetEnabledOnPREPTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if deltas affecting same target option is enabled
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openContextMenuSubMenu(RenditionContextMenuElements.view);
        boolean deltasAffectingSameTargetEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.viewDeltasAffectingSameTarget);
        Assertions.assertTrue(deltasAffectingSameTargetEnabled, "View -> Deltas Affecting Same Target option is enabled on PREP documents");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the View -> Deltas Affecting Same Target option is disabled on non-PREP documents <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewDeltasAffectingSameTargetDisabledOnNonPREPTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("IN");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if deltas affecting same target option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openContextMenuSubMenu(RenditionContextMenuElements.view);
        boolean deltasAffectingSameTargetDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewDeltasAffectingSameTarget);
        Assertions.assertTrue(deltasAffectingSameTargetDisabled, "View -> Deltas Affecting Same Target option is enabled on non-PREP documents");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that an APVRS document with a red error flag has the Sync option disabled <br>
     * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void apvrsDocumentRedErrorFlagSyncDisabledRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Error");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncDisabled, "APVRS document with red Error Flag has sync option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the Create Prep Document option is disabled on an APV document <br>
     * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void createPrepDocumentDisabledOnAPVRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");

        //Open context menu on first rendition and check if create prep document option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean createPrepDocDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createPreparationDocument);
        Assertions.assertTrue(createPrepDocDisabled, "Create Prep Document option is enabled on an APV document");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that an APVRS document doesn't have the Sync option enabled <br>
     * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void nonApvrsDocumentSyncDisabledRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("DRA");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if sync option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean syncDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
        Assertions.assertTrue(syncDisabled, "APVRS document has the sync option enabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the View -> Deltas Affecting Same Target option is disabled for Risk users <br>
     * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void viewDeltasAffectingSameTargetDisabledOnPREPRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter rendition
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open context menu on first rendition and check if deltas affecting same target option is disabled
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openContextMenuSubMenu(RenditionContextMenuElements.view);
        boolean deltasAffectingSameTargetDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewDeltasAffectingSameTarget);
        Assertions.assertTrue(deltasAffectingSameTargetDisabled, "View -> Deltas Affecting Same Target option is enabled for Risk users on non-PREP documents");
    }
}
