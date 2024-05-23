package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.SectionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SourceNavigateSectionTabContextAndClamshellMenuRiskSharedContentSetTests extends TestService
{
	private static final String IOWA_CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct context menu options are disabled or enabled for a section from a non-APVRS rendition for a risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionTabContextMenuNonAPVRSDocumentRiskSharedTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2011", Content Type="BILL", Doc Number="SB504"
		filterForSourceDocument();
		
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToSectionTab();
    	sourceNavigateGridPage().rightClickFirstRendition();

		boolean editContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.edit);
		boolean viewContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.view);
		boolean viewSectionContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.viewSection);
		boolean viewSectionNotesContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.viewSectionNotes);
		boolean modifyContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.modify);
		boolean validateContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.validate);
		boolean trackContextMenuOptionIsDisabled = sectionContextMenu().isElementDisabled(SectionContextMenuElements.track);
		
		Assertions.assertAll
		(
				() -> Assertions.assertTrue(editContextMenuOptionIsDisabled, "The 'Edit' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewContextMenuOptionIsDisabled, "The 'View' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSectionContextMenuOptionIsDisabled, "The 'View -> Section' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSectionNotesContextMenuOptionIsDisabled, "The 'View -> Section Notes' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertTrue(modifyContextMenuOptionIsDisabled, "The 'Modify' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(validateContextMenuOptionIsDisabled, "The 'Validate' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(trackContextMenuOptionIsDisabled, "The 'Track' context menu option is enabled when it should be disabled")
		);
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Edit clamshell options are disabled when selecting the Section tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionTabEditClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2011", Content Type="BILL", Doc Number="SB504"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToSectionTab();

    	clamshellPage().openSideBarEdit();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean sectionsClamshellOptionIsDisabled = sectionTabEditClamshellPage().clickSections(false,true);
		Assertions.assertTrue(sectionsClamshellOptionIsDisabled,"The 'Section(s)' clamshell option is disabled");

		boolean effectiveDateClamshellOptionIsDisabled = sectionTabEditClamshellPage().clickEffectiveDate(false,true);
		Assertions.assertTrue(effectiveDateClamshellOptionIsDisabled,"The 'Effective Date' clamshell option is disabled");

		boolean integrationPropertiesClamshellOptionIsDisabled = sectionTabEditClamshellPage().clickIntegrationProperties(false,true);
		Assertions.assertTrue(integrationPropertiesClamshellOptionIsDisabled,"The 'Integration Properties' clamshell option is disabled");

		boolean difficultyLevelClamshellOptionIsDisabled = sectionTabEditClamshellPage().clickDifficultyLevel(false,true);
		Assertions.assertTrue(difficultyLevelClamshellOptionIsDisabled,"The 'Difficulty Level' clamshell option is disabled");

		boolean propertiesClamshellOptionIsDisabled = sectionTabEditClamshellPage().clickProperties(false,true);
		Assertions.assertTrue(propertiesClamshellOptionIsDisabled,"The 'Properties' clamshell option is disabled");
    }
    
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Modify clamshell option is disabled when selecting the Section tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionTabModifyClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2011", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToSectionTab();

    	clamshellPage().openSideBarModify();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean assignUserClamshellOptionIsDisabled = sectionTabModifyClamshellPage().clickAssignUser(false,true);
		Assertions.assertTrue(assignUserClamshellOptionIsDisabled,"The 'Assign User' clamshell option is disabled");
    }
    
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Track clamshell options are correctly disabled when selecting the Section tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionTabTrackClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2011", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToSectionTab();
    	
    	clamshellPage().openSideBarTrack();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean imagesSentOutClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickImagesSentOut(false,true);
		Assertions.assertTrue(imagesSentOutClamshellOptionIsDisabled,"The 'Images Sent Out' clamshell option is disabled");

		boolean imagesCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickImagesCompleted(false,true);
		Assertions.assertTrue(imagesCompletedClamshellOptionIsDisabled,"The 'Images Completed' clamshell option is disabled");

		boolean tabularRequestedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickTabularRequested(false,true);
		Assertions.assertTrue(tabularRequestedClamshellOptionIsDisabled,"The 'Tabular Requested' clamshell option is disabled");

		boolean tabularStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickTabularStarted(false,true);
		Assertions.assertTrue(tabularStartedClamshellOptionIsDisabled,"The 'Tabular Started' clamshell option is disabled");

		boolean tabularCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickTabularCompleted(false,true);
		Assertions.assertTrue(tabularCompletedClamshellOptionIsDisabled,"The 'Tabular Completed' clamshell option is disabled");

		boolean attorneyWorkStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAttorneyWorkStarted(false,true);
		Assertions.assertTrue(attorneyWorkStartedClamshellOptionIsDisabled,"The 'Attorney Work Started' clamshell option is disabled");

		boolean attorneyWorkCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAttorneyWorkCompleted(false,true);
		Assertions.assertTrue(attorneyWorkCompletedClamshellOptionIsDisabled,"The 'Attorney Work Completed' clamshell option is disabled");

		boolean versioningWorkStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickVersioningWorkStarted(false,true);
		Assertions.assertTrue(versioningWorkStartedClamshellOptionIsDisabled,"The 'Versioning Work Started' clamshell option is disabled");

		boolean versioningWorkCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickVersioningWorkCompleted(false,true);
		Assertions.assertTrue(versioningWorkCompletedClamshellOptionIsDisabled,"The 'Versioning Work Completed' clamshell option is disabled");

		boolean prepStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickPrepStarted(false,true);
		Assertions.assertTrue(prepStartedClamshellOptionIsDisabled,"The 'PREP Started' clamshell option is disabled");

		boolean prepCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickPrepCompleted(false,true);
		Assertions.assertTrue(prepCompletedClamshellOptionIsDisabled,"The 'PREP Completed' clamshell option is disabled");

		boolean readyForIntegrationClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickReadyForIntegration(false,true);
		Assertions.assertTrue(readyForIntegrationClamshellOptionIsDisabled,"The 'Ready for Integration' clamshell option is disabled");

		boolean integrationStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegrationStarted(false,true);
		Assertions.assertTrue(integrationStartedClamshellOptionIsDisabled,"The 'Integration Started' clamshell option is disabled");

		boolean integrationCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegrationCompleted(false,true);
		Assertions.assertTrue(integrationCompletedClamshellOptionIsDisabled,"The 'Integration Completed' clamshell option is disabled");

		boolean integration2StartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegration2Started(false,true);
		Assertions.assertTrue(integration2StartedClamshellOptionIsDisabled,"The 'Integration 2 Started' clamshell option is disabled");

		boolean integration2CompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegration2Completed(false,true);
		Assertions.assertTrue(integration2CompletedClamshellOptionIsDisabled,"The 'Integration 2 Completed' clamshell option is disabled");

		boolean integrationQueryStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegrationQueryStarted(false,true);
		Assertions.assertTrue(integrationQueryStartedClamshellOptionIsDisabled,"The 'Integration Query Started' clamshell option is disabled");

		boolean integrationQueryCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickIntegrationQueryCompleted(false,true);
		Assertions.assertTrue(integrationQueryCompletedClamshellOptionIsDisabled,"The 'Integration Query Completed' clamshell option is disabled");

		boolean auditRequestedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditRequested(false,true);
		Assertions.assertTrue(auditRequestedClamshellOptionIsDisabled,"The 'Audit Requested' clamshell option is disabled");

		//The click first rendition method is not needed here because test will fail due to the proceeding elements not being displayed on the page
		boolean auditReviewStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditReviewStarted(false,true);
		Assertions.assertTrue(auditReviewStartedClamshellOptionIsDisabled,"The 'Audit Review Started' clamshell option is disabled");

		boolean auditReviewCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditReviewCompleted(false,true);
		Assertions.assertTrue(auditReviewCompletedClamshellOptionIsDisabled,"The 'Audit Review Completed' clamshell option is disabled");

		boolean auditCorrectionsToBeDoneByClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(false,true);
		Assertions.assertTrue(auditCorrectionsToBeDoneByClamshellOptionIsDisabled,"The 'Audit Corrections to be Done by' clamshell option is disabled");

		boolean auditCorrectionsStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditCorrectionStarted(false,true);
		Assertions.assertTrue(auditCorrectionsStartedClamshellOptionIsDisabled,"The 'Audit Corrections Started' clamshell option is disabled");

		boolean auditCorrectionsCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickAuditCorrectionsCompleted(false,true);
		Assertions.assertTrue(auditCorrectionsCompletedClamshellOptionIsDisabled,"The 'Audit Corrections Completed' clamshell option is disabled");

		boolean correctionsAuditRequestedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickCorrectionsAuditRequested(false,true);
		Assertions.assertTrue(correctionsAuditRequestedClamshellOptionIsDisabled,"The 'Corrections Audit Requested' clamshell option is disabled");

		boolean correctionsAuditStartedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickCorrectionsAuditStarted(false,true);
		Assertions.assertTrue(correctionsAuditStartedClamshellOptionIsDisabled,"The 'Corrections Audit Started' clamshell option is disabled");

		boolean correctionsAuditCompletedClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickCorrectionsAuditCompleted(false,true);
		Assertions.assertTrue(correctionsAuditCompletedClamshellOptionIsDisabled,"The 'Corrections Audit Completed' clamshell option is disabled");

		boolean cleanClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickClean(false,true);
		Assertions.assertTrue(cleanClamshellOptionIsDisabled,"The 'Clean' clamshell option is disabled");

		boolean releasedToWestlawClamshellOptionIsDisabled = sectionTabTrackClamshellPage().clickReleasedToWestlaw(false,true);
		Assertions.assertTrue(releasedToWestlawClamshellOptionIsDisabled,"The 'Released to Westlaw' clamshell option is disabled");
    }
    
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Reports clamshell option is enabled when selecting the Section tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sectionTabReportsClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2011", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToSectionTab();
    	
    	clamshellPage().openSideBarReport();
		sourceNavigateGridPage().clickFirstRendition();

		boolean lockReportClamshellOptionIsEnabled = sectionTabReportClamshellPage().clickLockReport(true,true);
		Assertions.assertTrue(lockReportClamshellOptionIsEnabled,"The 'Lock Report' clamshell option is enabled");
    }
      
    private void filterForSourceDocument()
    {
    	sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
    	sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
    	sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
    	sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
    	sourceNavigateFiltersAndSortsPage().setFilterYear("2011");
    	sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
    	sourceNavigateFiltersAndSortsPage().setFilterDocNumber("SB504");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    }
}