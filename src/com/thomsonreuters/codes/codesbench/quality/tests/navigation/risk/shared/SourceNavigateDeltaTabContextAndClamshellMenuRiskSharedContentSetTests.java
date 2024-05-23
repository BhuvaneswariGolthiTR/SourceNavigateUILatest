package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.DeltaContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class SourceNavigateDeltaTabContextAndClamshellMenuRiskSharedContentSetTests extends TestService
{
	private static final String IOWA_CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options are correct when right clicking a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabContextMenuNonAPVRSDocumentRiskSharedTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
    	sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
    	filterForSourceDocument();
		
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToDeltaTab();
    	sourceNavigateGridPage().rightClickFirstRendition();

		DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
    	
		boolean editContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.edit);
		boolean viewContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.view);
		
		deltaContextMenu().openViewSubMenu();
		
		boolean viewDeltaContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.viewDelta);
		boolean viewDeltaNotesContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.viewDeltaNotes);
		boolean viewDeltasAffectingSameTargetContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.viewDeltasAffectingSameTarget);
		boolean viewTargetContentContextMenuOptionIsDisabled =  deltaContextMenu().isElementDisabled(DeltaContextMenuElements.viewTargetContent);
		boolean viewTargetInHierarchyContextMenuOptionIsDisabled =  deltaContextMenu().isElementDisabled(DeltaContextMenuElements.viewTargetInHierarchy);
		boolean modifyContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.modify);
		boolean deltaPropertiesContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.deltaProperties);
		boolean validateContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.validate);
		boolean reportContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.report);
		boolean trackContextMenuOptionIsDisabled = deltaContextMenu().isElementDisabled(DeltaContextMenuElements.track);
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(editContextMenuOptionIsDisabled, "The 'Edit' menu option is enabled when it should not be"),
			() -> Assertions.assertFalse(viewContextMenuOptionIsDisabled, "The 'View' menu option is not enabled when it should be"),
			() -> Assertions.assertFalse(viewDeltaContextMenuOptionIsDisabled, "The 'View -> Delta' menu option is not enabled when it should be"),
			() -> Assertions.assertFalse(viewDeltaNotesContextMenuOptionIsDisabled, "The 'View -> Delta Notes' menu option is not enabled when it should be"),
			() -> Assertions.assertTrue(viewDeltasAffectingSameTargetContextMenuOptionIsDisabled, "The 'View -> Deltas Affecting Same Target' menu option is enabled when it should not be"),
			() -> Assertions.assertFalse(viewTargetContentContextMenuOptionIsDisabled, "The 'View -> Target Content' menu option is not enabled when it should be"),
			() -> Assertions.assertFalse(viewTargetInHierarchyContextMenuOptionIsDisabled, "The 'View -> Target In Hierarchy' menu option is not enabled when it should be"),
			() -> Assertions.assertTrue(modifyContextMenuOptionIsDisabled, "The 'Modify' menu option is enabled when it should not be"),
			() -> Assertions.assertTrue(deltaPropertiesContextMenuOptionIsDisabled, "The 'Delta Properties' menu option is enabled when it should not be"),
			() -> Assertions.assertTrue(validateContextMenuOptionIsDisabled, "The 'Validate' menu option is enabled when it should not be"),
			() -> Assertions.assertTrue(trackContextMenuOptionIsDisabled, "The 'Track' menu option is enabled when it should not be"),
			() -> Assertions.assertTrue(reportContextMenuOptionIsDisabled, "The 'Report' menu option is enabled when it should not be")
		);
    }

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Edit clamshell options are disabled when selecting a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabEditClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
    	sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToDeltaTab();

    	clamshellPage().openSideBarEdit();
		sourceNavigateGridPage().clickFirstRendition();

		boolean notesClamshellOptionIsDisabled = deltaTabEditClamshellPage().clickNotes(false,true);
		Assertions.assertTrue(notesClamshellOptionIsDisabled,"The 'Notes' clamshell option is disabled");

		boolean effectiveDateClamshellOptionIsDisabled = deltaTabEditClamshellPage().clickEffectiveDate(false,true);
		Assertions.assertTrue(effectiveDateClamshellOptionIsDisabled,"The 'Effective Date' clamshell option is disabled");

		boolean integrationPropertiesClamshellOptionIsDisabled = deltaTabEditClamshellPage().clickIntegrationProperties(false,true);
		Assertions.assertTrue(integrationPropertiesClamshellOptionIsDisabled,"The 'Integration Properties' clamshell option is disabled");

		boolean difficultyLevelClamshellOptionIsDisabled = deltaTabEditClamshellPage().clickDifficultyLevel(false,true);
		Assertions.assertTrue(difficultyLevelClamshellOptionIsDisabled,"The 'Difficulty Level' clamshell option is disabled");

		boolean propertiesClamshellOptionIsDisabled = deltaTabEditClamshellPage().clickProperties(false,true);
		Assertions.assertTrue(propertiesClamshellOptionIsDisabled,"The 'Properties' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following View clamshell options are correctly enabled or disabled when selecting a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabViewClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
    	sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToDeltaTab();

    	clamshellPage().openSideBarView();

		sourceNavigateGridPage().clickFirstRendition();

		boolean notesClamshellOptionIsDisabled = deltaTabViewClamshellPage().clickNotes(false,true);
		Assertions.assertTrue(notesClamshellOptionIsDisabled,"The 'Notes' clamshell option is disabled");

		boolean deltasAffectingSameTargetClamshellOptionIsDisabled = deltaTabViewClamshellPage().clickDeltasAffectingSameTarget(false);
		Assertions.assertTrue(deltasAffectingSameTargetClamshellOptionIsDisabled,"The 'Deltas Affecting Same Target' clamshell option is disabled");

		boolean viewTargetInHierarchyClamshellOptionIsEnabled = deltaTabViewClamshellPage().clickViewTargetInHierarchy(true,true);
		Assertions.assertTrue(viewTargetInHierarchyClamshellOptionIsEnabled,"The 'View Target In Hierarchy' clamshell option is enabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Modify clamshell options are disabled when selecting a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabModifyClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
		filterForSourceDocument();

		sourceNavigateGridPage().clickFirstRendition();
		sourcePage().goToDeltaTab();

		clamshellPage().openSideBarModify();
		sourceNavigateGridPage().clickFirstRendition();

		boolean integrateClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickIntegrate(false,true);
		Assertions.assertTrue(integrateClamshellOptionIsDisabled,"The 'Integrate' clamshell option is disabled");

		boolean runCiteLocateClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickRunCiteLocate(false,true);
		Assertions.assertTrue(runCiteLocateClamshellOptionIsDisabled,"The 'Run Cite Locate' clamshell option is disabled");

		boolean resolveCiteLocateClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickResolveCiteLocate(false,true);
		Assertions.assertTrue(resolveCiteLocateClamshellOptionIsDisabled,"The 'Resolve Cite Locate' clamshell option is disabled");

		boolean insertIntoHierarchyWizardClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickInsertIntoHierarchyWizard(false,true);
		Assertions.assertTrue(insertIntoHierarchyWizardClamshellOptionIsDisabled,"The 'Insert Into Hierarchy Wizard' clamshell option is disabled");

		boolean resetIntegrationStatusClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickResetIntegrationStatus(false,true);
		Assertions.assertTrue(resetIntegrationStatusClamshellOptionIsDisabled,"The 'Reset Integration Status' clamshell option is disabled");

		boolean assignUserClamshellOptionIsDisabled = deltaTabModifyClamshellPage().clickAssignUser(false,true);
		Assertions.assertTrue(assignUserClamshellOptionIsDisabled,"The 'Assign User' clamshell option is disabled");
    }
    
	 /**
	  * STORY/BUG - n/a <br>
	  * SUMMARY - This test verifies that the following Track clamshell options are disabled when selecting a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	  * USER - Risk <br>
	  */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabTrackClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
		filterForSourceDocument();

		sourceNavigateGridPage().clickFirstRendition();
		sourcePage().goToDeltaTab();

		clamshellPage().openSideBarTrack();
		sourceNavigateGridPage().clickFirstRendition();

		boolean imagesSentOutClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickImagesSentOut(false,true);
		Assertions.assertTrue(imagesSentOutClamshellOptionIsDisabled,"The 'Images Sent Out' clamshell option is disabled" + imagesSentOutClamshellOptionIsDisabled);

		boolean imagesCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickImagesCompleted(false,true);
		Assertions.assertTrue(imagesCompletedClamshellOptionIsDisabled,"The 'Images Completed' clamshell option is disabled");

		boolean tabularRequestedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickTabularRequested(false,true);
		Assertions.assertTrue(tabularRequestedClamshellOptionIsDisabled,"The 'Tabular Requested' clamshell option is disabled");

		boolean tabularStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickTabularStarted(false,true);
		Assertions.assertTrue(tabularStartedClamshellOptionIsDisabled,"The 'Tabular Started' clamshell option is disabled");

		boolean tabularCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickTabularCompleted(false,true);
		Assertions.assertTrue(tabularCompletedClamshellOptionIsDisabled,"The 'Tabular Completed' clamshell option is disabled");

		boolean attorneyWorkStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAttorneyWorkStarted(false,true);
		Assertions.assertTrue(attorneyWorkStartedClamshellOptionIsDisabled,"The 'Attorney Work Started' clamshell option is disabled");

		boolean attorneyWorkCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAttorneyWorkCompleted(false,true);
		Assertions.assertTrue(attorneyWorkCompletedClamshellOptionIsDisabled,"The 'Attorney Work Completed' clamshell option is disabled");

		boolean versioningWorkStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickVersioningWorkStarted(false,true);
		Assertions.assertTrue(versioningWorkStartedClamshellOptionIsDisabled,"The 'Versioning Work Started' clamshell option is disabled");

		boolean versioningWorkCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickVersioningWorkCompleted(false,true);
		Assertions.assertTrue(versioningWorkCompletedClamshellOptionIsDisabled,"The 'Versioning Work Completed' clamshell option is disabled");

		boolean prepStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickPrepStarted(false,true);
		Assertions.assertTrue(prepStartedClamshellOptionIsDisabled,"The 'PREP Started' clamshell option is disabled");

		boolean prepCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickPrepCompleted(false,true);
		Assertions.assertTrue(prepCompletedClamshellOptionIsDisabled,"The 'PREP Completed' clamshell option is disabled");

		boolean readyForIntegrationClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickReadyForIntegration(false,true);
		Assertions.assertTrue(readyForIntegrationClamshellOptionIsDisabled,"The 'Ready for Integration' clamshell option is disabled");

		boolean integrationStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegrationStarted(false,true);
		Assertions.assertTrue(integrationStartedClamshellOptionIsDisabled,"The 'Integration Started' clamshell option is disabled");

		boolean integrationCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegrationCompleted(false,true);
		Assertions.assertTrue(integrationCompletedClamshellOptionIsDisabled,"The 'Integration Completed' clamshell option is disabled");

		boolean integration2StartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegration2Started(false,true);
		Assertions.assertTrue(integration2StartedClamshellOptionIsDisabled,"The 'Integration 2 Started' clamshell option is disabled");

		boolean integration2CompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegration2Completed(false,true);
		Assertions.assertTrue(integration2CompletedClamshellOptionIsDisabled,"The 'Integration 2 Completed' clamshell option is disabled");

		//The click first rendition method is not needed here because test will fail due to the proceeding elements not being displayed on the page
		boolean integrationQueryClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegrationQueryStarted(false,true);
		Assertions.assertTrue(integrationQueryClamshellOptionIsDisabled,"The 'Integration Query' clamshell option is disabled");

		boolean integrationQueryCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickIntegrationQueryCompleted(false,true);
		Assertions.assertTrue(integrationQueryCompletedClamshellOptionIsDisabled,"The 'Integration Query Completed' clamshell option is disabled");

		boolean cutAndPasteCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickCutAndPasteCompleted(false,true);
		Assertions.assertTrue(cutAndPasteCompletedClamshellOptionIsDisabled,"The 'Cut and Paste Completed' clamshell option is disabled");

		//The click first rendition method is not needed here because test will fail due to the proceeding elements not being displayed on the page
		boolean auditRequestedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditRequested(false,true);
		Assertions.assertTrue(auditRequestedClamshellOptionIsDisabled,"The 'Audit Requested' clamshell option is disabled");

		boolean auditReviewStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditReviewStarted(false,true);
		Assertions.assertTrue(auditReviewStartedClamshellOptionIsDisabled,"The 'Audit Review Started' clamshell option is disabled");

		boolean auditReviewCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditReviewCompleted(false,true);
		Assertions.assertTrue(auditReviewCompletedClamshellOptionIsDisabled,"The 'Audit Review Completed' clamshell option is disabled");

		boolean auditCorrectionsToBeDoneByClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(false,true);
		Assertions.assertTrue(auditCorrectionsToBeDoneByClamshellOptionIsDisabled,"The 'Audit Corrections to be Done by' clamshell option is disabled");

		boolean auditCorrectionsStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditCorrectionStarted(false,true);
		Assertions.assertTrue(auditCorrectionsStartedClamshellOptionIsDisabled,"The 'Audit Corrections Started' clamshell option is disabled");

		boolean auditCorrectionsCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickAuditCorrectionsCompleted(false,true);
		Assertions.assertTrue(auditCorrectionsCompletedClamshellOptionIsDisabled,"The 'Audit Corrections Completed' clamshell option is disabled");

		boolean correctionsAuditRequestedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickCorrectionsAuditRequested(false,true);
		Assertions.assertTrue(correctionsAuditRequestedClamshellOptionIsDisabled,"The 'Corrections Audit Requested' clamshell option is disabled");

		boolean correctionsAuditStartedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickCorrectionsAuditStarted(false,true);
		Assertions.assertTrue(correctionsAuditStartedClamshellOptionIsDisabled,"The 'Corrections Audit Started' clamshell option is disabled");

		boolean correctionsAuditCompletedClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickCorrectionsAuditCompleted(false,true);
		Assertions.assertTrue(correctionsAuditCompletedClamshellOptionIsDisabled,"The 'Corrections Audit Completed' clamshell option is disabled");

		boolean cleanClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickClean(false,true);
		Assertions.assertTrue(cleanClamshellOptionIsDisabled,"The 'Clean' clamshell option is disabled");

		boolean releaseToWestlawClamshellOptionIsDisabled = deltaTabTrackClamshellPage().clickReleasedToWestlaw(false,true);
		Assertions.assertTrue(releaseToWestlawClamshellOptionIsDisabled,"The 'Released to Westlaw' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Report clamshell options are disabled when selecting a delta from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void deltaTabReportClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Alabama (Development)", Year="2015", Rendition Status="APV", Content Type="UNCHAPTERED", Doc Type="S", Doc Number="2"
		filterForSourceDocument();

		sourceNavigateGridPage().clickFirstRendition();
		sourcePage().goToDeltaTab();

		clamshellPage().openSideBarReport();
		sourceNavigateGridPage().clickFirstRendition();

		boolean integrationResultsClamshellOptionIsDisabled = deltaTabReportClamshellPage().clickIntegrationResults(false,true);
    	Assertions.assertTrue(integrationResultsClamshellOptionIsDisabled,"The 'Integration Results' clamshell option is disabled");

		boolean integrationWorkflowClamshellOptionIsDisabled = deltaTabReportClamshellPage().clickIntegrationWorkflow(false,true);
		Assertions.assertTrue(integrationWorkflowClamshellOptionIsDisabled,"The 'Integration Workflow' clamshell option is disabled");
    }
    
	 public void filterForSourceDocument()
	 {
			sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
			sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
			sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
			sourceNavigateFiltersAndSortsPage().setFilterContentSet("Alabama (Development)");
			sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
			sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
			sourceNavigateFiltersAndSortsPage().setFilterContentType("UNCHAPTERED");
			sourceNavigateFiltersAndSortsPage().setFilterDocType("S");
			sourceNavigateFiltersAndSortsPage().setFilterDocNumber("2");
			sourceNavigateFooterToolsPage().refreshTheGrid();
	 }
}