package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.LineageContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigateLineageTabContextMenuClamshellRiskSharedContentSetTests extends TestService
{
	private static final String IOWA_CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct context menu options are enabled for a risk user under a shared content set when right clicking on a non-APVRS rendition under the lineage tab<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabContextMenuNonAPVRSDocumentRiskSharedTest()
    {
    	homePage().goToHomePage();
        loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
    	filterForSourceDocument();
		
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	sourceNavigateGridPage().rightClickFirstRendition();
    	
		boolean editContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.edit);
		boolean viewContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.view);

		lineageContextMenu().openViewSubMenu();

		boolean viewRenditionContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewRendition);
		boolean viewRenditionNotesContextMenuOptionIsDisabled =  lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewRenditionNotes);
		boolean viewSourceFrontContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewSourceFront);
		boolean viewSourceEndContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewSourceEnd);
		boolean viewRenditionBaselinesContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewRenditionBaselines);
		boolean viewDeltasAffectingSameTargetContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewDeltasAffectingSameTarget);
		boolean viewRenditionXMLContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewRenditionXml);
		boolean viewXMLExtractContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewXmlExtract);
		boolean viewRenditionPrintPreviewContextMenuOptionIsDisabled =  lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewRenditionPrintPreview);
		boolean viewTabularMainframePrintPreviewContextMenuOptionIsDisabled =  lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewTabularMainframePrintPreview);
		boolean viewTabularPrintPreviewContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewTabularPrintPreview);
		boolean viewTabularWLPreviewContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewTabularWlPreview);
		boolean modifyContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.modify);
		boolean classifyInCHCContextMenuOptionIsDisabled =  lineageContextMenu().isElementDisabled(LineageContextMenuElements.classifyInChc);
		boolean sendToVendorForCaptureContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.sendToVendorForCapture);
		boolean createPreparationDocumentContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.createPreparationDocument);
		boolean addContentContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.addContent);
		boolean validateContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.validate);
		boolean trackContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.track);
		boolean reportContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.report);
		boolean transferContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.transfer);
		boolean syncContextMenuOptionIsDisabled  = lineageContextMenu().isElementDisabled(LineageContextMenuElements.sync);
		boolean createBookmarkContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.createBookmark);
		boolean moveTaskContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.moveTask);

		//Change multiple/duplicate filter from "None" to "Multiple"
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    	
    	sourceNavigateGridPage().rightClickFirstRendition();
    	lineageContextMenu().openViewSubMenu();
    	
		boolean viewMultipleAndDuplicateRenditionsContextMenuOptionIsDisabled = lineageContextMenu().isElementDisabled(LineageContextMenuElements.viewMultipleAndDuplicateRenditions);
		
		Assertions.assertAll
		(
				() -> Assertions.assertTrue(editContextMenuOptionIsDisabled, "The 'Edit' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewContextMenuOptionIsDisabled, "The 'View' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewRenditionContextMenuOptionIsDisabled, "The 'View Rendition' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewRenditionNotesContextMenuOptionIsDisabled, "The 'View -> Rendition Notes' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSourceFrontContextMenuOptionIsDisabled, "The 'View -> Source Front' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSourceEndContextMenuOptionIsDisabled, "The 'View -> Source End' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewRenditionBaselinesContextMenuOptionIsDisabled, "The 'View -> Rendition Baselines' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(viewDeltasAffectingSameTargetContextMenuOptionIsDisabled, "The 'View -> Deltas Affecting Same Target' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewRenditionXMLContextMenuOptionIsDisabled, "The 'View -> Rendition XML' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewXMLExtractContextMenuOptionIsDisabled, "The 'View -> XML Extract' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewRenditionPrintPreviewContextMenuOptionIsDisabled, "The 'View -> Rendition Print Preview' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewTabularMainframePrintPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular Mainframe Print Preview' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewTabularPrintPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular Print Preview' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(viewTabularWLPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular WL Preview' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(modifyContextMenuOptionIsDisabled, "The 'Modify' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(classifyInCHCContextMenuOptionIsDisabled, "The 'Classify in CHC' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertTrue(sendToVendorForCaptureContextMenuOptionIsDisabled, "The 'Send to Vendor for Capture' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(createPreparationDocumentContextMenuOptionIsDisabled, "The 'Create Preparation Document' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(addContentContextMenuOptionIsDisabled, "The 'Add Content' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(validateContextMenuOptionIsDisabled, "The 'Validate' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(trackContextMenuOptionIsDisabled, "The 'Track' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(reportContextMenuOptionIsDisabled, "The 'Report' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(transferContextMenuOptionIsDisabled, "The 'Transfer' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertTrue(syncContextMenuOptionIsDisabled, "The 'Sync' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(createBookmarkContextMenuOptionIsDisabled, "The 'Create Bookmark' context menu option was disabled when it should be enabled"),
				() -> Assertions.assertTrue(moveTaskContextMenuOptionIsDisabled, "The 'Move Task' context menu option was enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewMultipleAndDuplicateRenditionsContextMenuOptionIsDisabled, "The 'View Multiple and Duplicate Renditions' context menu option was disabled when it should be enabled")
		);
   }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Edit clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabEditClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	clamshellPage().openSideBarEdit();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean renditionClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickRendition(false,true);
		Assertions.assertTrue(renditionClamshellOptionIsDisabled,"The 'Rendition(s)' clamshell option is disabled");

		boolean integrationPropertiesClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickIntegrationProperties(false,true);
		Assertions.assertTrue(integrationPropertiesClamshellOptionIsDisabled,"The 'Integration Properties' clamshell option is disabled");

		boolean difficultyLevelClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickDifficultyLevel(false,true);
		Assertions.assertTrue(difficultyLevelClamshellOptionIsDisabled,"The 'Difficulty Level' clamshell option is disabled");

		boolean renditionXmlClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickRenditionXml(false,true);
		Assertions.assertTrue(renditionXmlClamshellOptionIsDisabled,"The 'Rendition XML' clamshell option is disabled");

		boolean propertiesClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickProperties(false,true);
		Assertions.assertTrue(propertiesClamshellOptionIsDisabled,"The 'Properties' clamshell option is disabled");

		boolean editPdfMetadataClamshellOptionIsDisabled = lineageTabEditClamshellPage().clickEditPdfMetadata(false,true);
		Assertions.assertTrue(editPdfMetadataClamshellOptionIsDisabled,"The 'Edit PDF/Metadata' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct View clamshell options are disabled or enabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabViewClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
        homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();

    	clamshellPage().openSideBarView();
		sourceNavigateGridPage().clickFirstRendition();

		boolean baselinesClamshellOptionIsDisabled = lineageTabViewClamshellPage().clickBaselines(false,true);
		Assertions.assertTrue(baselinesClamshellOptionIsDisabled,"The 'Baselines' clamshell option is disabled");

		boolean deltasAffectingSameTargetClamshellOptionIsDisabled = lineageTabViewClamshellPage().clickDeltasAffectingSameTarget(false);
		Assertions.assertTrue(deltasAffectingSameTargetClamshellOptionIsDisabled,"The 'Deltas Affecting Same Target' clamshell option is disabled");

		boolean multiplesAndDuplicatesClamshellOptionIsDisabled = lineageTabViewClamshellPage().clickMultipleAndDuplicate(false,true);
		Assertions.assertTrue(multiplesAndDuplicatesClamshellOptionIsDisabled,"The 'Multiples and Duplicates' clamshell option is disabled");

		boolean renditionXmlClamshellOptionIsEnabled = lineageTabViewClamshellPage().clickRenditionXml(true,true);
		Assertions.assertTrue(renditionXmlClamshellOptionIsEnabled,"The 'Rendition XML' clamshell option is enabled");

		sourcePage().switchToLineageNavigatePage();

		boolean xmlExtractClamshellOptionIsEnabled = lineageTabViewClamshellPage().clickXmlExtract(true,true);
		Assertions.assertTrue(xmlExtractClamshellOptionIsEnabled,"The 'XML Extract' clamshell option is enabled");

		sourcePage().switchToLineageNavigatePage();

		boolean renditionPrintPreviewClamshellOptionIsEnabled = lineageTabViewClamshellPage().clickRenditionPrintPreview(true,true);
		Assertions.assertTrue(renditionPrintPreviewClamshellOptionIsEnabled,"The 'Rendition Print Preview' clamshell option is enabled");

		sourcePage().switchToLineageNavigatePage();

		boolean tabularPrintPreviewClamshellOptionIsEnabled = lineageTabViewClamshellPage().clickTabularPrintPreview(true,true);
		Assertions.assertTrue(tabularPrintPreviewClamshellOptionIsEnabled,"The 'Tabular print Preview' clamshell option is enabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the View -> Multiple Documents clamshell option are correctly enabled when it should be<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabViewMultipleDuplicateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    	
    	clamshellPage().openSideBarView();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean viewMultiplesAndDuplicatesIsEnabled = renditionTabViewClamshellPage().clickMultiplesAndDuplicates(true, false);

		sourceNavigateGridPage().clearFilters();

		boolean multiplesAndDuplicatesMessageIsDisplayedAfterClearingFilters = renditionTabViewClamshellPage().isElementDisplayed(SourceNavigatePageElements.MULTIPLES_MESSAGE_XPATH);
    	
    	Assertions.assertAll
		(
        		() -> Assertions.assertTrue(viewMultiplesAndDuplicatesIsEnabled, "The 'View -> Multiples and Duplicates' clamshell menu option was disabled when it should be enabled"),
        		() -> Assertions.assertFalse(multiplesAndDuplicatesMessageIsDisplayedAfterClearingFilters, "The 'View -> Multiples and Duplicates' clamshell menu option was enabled when it should be disabled")
        );
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Modify clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabModifyClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
        homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	clamshellPage().openSideBarModify();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean splitRenditionBySectionClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickSplitRenditionBySection(false, true);
		Assertions.assertTrue(splitRenditionBySectionClamshellOptionIsDisabled,"The 'Split Rendition by Section' clamshell menu option is disabled");

		boolean markAsNonDuplicateClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickMarkAsNonDuplicate(false, true);
		Assertions.assertTrue(markAsNonDuplicateClamshellOptionIsDisabled,"The 'Mark as Non-Duplicate' clamshell menu option is disabled");

		boolean omitRenditionClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickOmitRendition(false, true);
		Assertions.assertTrue(omitRenditionClamshellOptionIsDisabled,"The 'Omit Rendition' clamshell menu option is disabled");

		boolean vetoRenditionClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickVetoRendition(false, true);
		Assertions.assertTrue(vetoRenditionClamshellOptionIsDisabled,"The 'Veto Rendition' clamshell menu option is disabled");

		boolean deleteRenditionClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickDeleteRendition(false, true);
		Assertions.assertTrue(deleteRenditionClamshellOptionIsDisabled,"The 'Delete Rendition' clamshell menu option is disabled");

		boolean undeleteRenditionClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickUndeleteRendition(false, true);
		Assertions.assertTrue(undeleteRenditionClamshellOptionIsDisabled,"The 'Undelete Rendition' clamshell menu option is disabled");

		boolean integrateClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickIntegrate(false, true);
		Assertions.assertTrue(integrateClamshellOptionIsDisabled,"The 'Integrate' clamshell menu option is disabled");

		boolean assignUserClamshellOptionIsDisabled = lineageTabModifyClamshellPage().clickAssignUser(false, true);
		Assertions.assertTrue(assignUserClamshellOptionIsDisabled,"The 'Assign User' clamshell menu option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Create clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabCreateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
        homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
    	filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	clamshellPage().openSideBarCreate();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean createPrepDocumentClamshellOptionIsDisabled = lineageTabCreateClamshellPage().clickCreatePrepDocument(false,true);
		Assertions.assertTrue(createPrepDocumentClamshellOptionIsDisabled,"The 'Create Prep Document' clamshell menu option is disabled");

		boolean addContentClamshellOptionIsDisabled = lineageTabCreateClamshellPage().clickAddContent(false,true);
		Assertions.assertTrue(addContentClamshellOptionIsDisabled,"The 'Add Content' clamshell menu option is disabled");

		boolean addPdfMetadataClamshellOptionIsEnabled = lineageTabCreateClamshellPage().clickAddPDFMetadata(true,true);
		Assertions.assertTrue(addPdfMetadataClamshellOptionIsEnabled,"The 'Add PDF/Metadata' clamshell menu option is enabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Validate clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabValidateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
        homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();

    	clamshellPage().openSideBarValidate();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean runValidationsClamshellOptionIsDisabled = lineageTabValidateClamshellPage().clickRunValidations(false,true);
		Assertions.assertTrue(runValidationsClamshellOptionIsDisabled,"The 'Run Validations' clamshell menu option is disabled");

		String validateAndUpdateLinksClamshellOptionIsDisabled = lineageTabValidateClamshellPage().clickValidateAndUpdateLinks(false,true);
		Assertions.assertTrue(validateAndUpdateLinksClamshellOptionIsDisabled.equals(""),"The 'Validate and Update Links' clamshell menu option is disabled");

		boolean viewValidationsClamshellOptionIsDisabled = lineageTabValidateClamshellPage().clickViewValidations(false,true);
		Assertions.assertTrue(viewValidationsClamshellOptionIsDisabled,"The 'View Validations' clamshell menu option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Track clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabTrackClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	clamshellPage().openSideBarTrack();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean syncToWestlawCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickSyncToWestlawCompleted(false,true);
		Assertions.assertTrue(syncToWestlawCompletedClamshellOptionIsDisabled,"The 'Sync to Westlaw Completed' clamshell menu option is disabled");

		boolean chapterApprovalReceivedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickChapterApprovalReceived(false,true);
		Assertions.assertTrue(chapterApprovalReceivedClamshellOptionIsDisabled,"The 'Chapter/Approval Received' clamshell menu option is disabled");

		boolean apvStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickApvStarted(false,true);
		Assertions.assertTrue(apvStartedClamshellOptionIsDisabled,"The 'APV Started' clamshell menu option is disabled");

		boolean apvCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickApvCompleted(false,true);
		Assertions.assertTrue(apvCompletedClamshellOptionIsDisabled,"The 'APV Completed' clamshell menu option is disabled");

		boolean topicalHeadingNeededClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTopicalHeadingNeeded(false,true);
		Assertions.assertTrue(topicalHeadingNeededClamshellOptionIsDisabled,"The 'Topical Heading Needed' clamshell menu option is disabled");

		boolean topicalHeadingCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTopicalHeadingCompleted(false,true);
		Assertions.assertTrue(topicalHeadingCompletedClamshellOptionIsDisabled,"The 'Topical Heading Completed' clamshell menu option is disabled");

		boolean imagesSentOutClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickImagesSentOut(false,true);
		Assertions.assertTrue(imagesSentOutClamshellOptionIsDisabled,"The 'Images Sent Out' clamshell menu option is disabled");

		boolean imagesCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickImagesCompleted(false,true);
		Assertions.assertTrue(imagesCompletedClamshellOptionIsDisabled,"The 'Images Completed' clamshell menu option is disabled");

		boolean tabularRequestedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTabularRequested(false,true);
		Assertions.assertTrue(tabularRequestedClamshellOptionIsDisabled,"The 'Tabular Requested' clamshell menu option is disabled");

		boolean tabularReceivedHardCopyClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTabularReceivedHardcopy(false,true);
		Assertions.assertTrue(tabularReceivedHardCopyClamshellOptionIsDisabled,"The 'Tabular Received Hardcopy' clamshell menu option is disabled");

		boolean tabularStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTabularStarted(false,true);
		Assertions.assertTrue(tabularStartedClamshellOptionIsDisabled,"The 'Tabular Started' clamshell menu option is disabled");

		boolean tabularCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickTabularCompleted(false,true);
		Assertions.assertTrue(tabularCompletedClamshellOptionIsDisabled,"The 'Tabular Completed' clamshell menu option is disabled");

		boolean apvReviewStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickApvReviewStarted(false,true);
		Assertions.assertTrue(apvReviewStartedClamshellOptionIsDisabled,"The 'APV Review Started' clamshell menu option is disabled");

		boolean apvReviewCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickApvReviewCompleted(false,true);
		Assertions.assertTrue(apvReviewCompletedClamshellOptionIsDisabled,"The 'APV Review Completed' clamshell menu option is disabled");

		boolean readyForAdvanceSheetClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickReadyForAdvanceSheet(false,true);
		Assertions.assertTrue(readyForAdvanceSheetClamshellOptionIsDisabled,"The 'Ready for Advance Sheet' clamshell menu option is disabled");

		boolean publishedInAdvancedSheetClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickPublishedInAdvanceSheet(false,true);
		Assertions.assertTrue(publishedInAdvancedSheetClamshellOptionIsDisabled,"The 'Published in Advance Sheet' clamshell menu option is disabled");

		//The click first rendition method is not needed here because test will fail due to the proceeding elements not being displayed on the page
		boolean attorneyWorkStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAttorneyWorkStarted(false,true);
		Assertions.assertTrue(attorneyWorkStartedClamshellOptionIsDisabled,"The 'Attorney Work Started' clamshell menu option is disabled");

		boolean attorneyWorkCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAttorneyWorkCompleted(false,true);
		Assertions.assertTrue(attorneyWorkCompletedClamshellOptionIsDisabled,"The 'Attorney Work Completed' clamshell menu option is disabled");

		boolean versioningWorkStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickVersioningWorkStarted(false,true);
		Assertions.assertTrue(versioningWorkStartedClamshellOptionIsDisabled,"The 'Versioning Work Started' clamshell menu option is disabled");

		boolean versioningWorkCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickVersioningWorkCompleted(false,true);
		Assertions.assertTrue(versioningWorkCompletedClamshellOptionIsDisabled,"The 'Versioning Work Completed' clamshell menu option is disabled");

		boolean prepStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickPrepStarted(false,true);
		Assertions.assertTrue(prepStartedClamshellOptionIsDisabled,"The 'PREP Started' clamshell menu option is disabled");

		boolean prepCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickPrepCompleted(false,true);
		Assertions.assertTrue(prepCompletedClamshellOptionIsDisabled,"The 'PREP Completed' clamshell menu option is disabled");

		boolean readyForIntegrationClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickReadyForIntegration(false,true);
		Assertions.assertTrue(readyForIntegrationClamshellOptionIsDisabled,"The 'Ready for Integration' clamshell menu option is disabled");

		boolean integrationStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegrationStarted(false,true);
		Assertions.assertTrue(integrationStartedClamshellOptionIsDisabled,"The 'Integration Started' clamshell menu option is disabled");

		boolean integrationCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegrationCompleted(false,true);
		Assertions.assertTrue(integrationCompletedClamshellOptionIsDisabled,"The 'Integration Completed' clamshell menu option is disabled");

		boolean integration2StartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegration2Started(false,true);
		Assertions.assertTrue(integration2StartedClamshellOptionIsDisabled,"The 'Integration 2 Started' clamshell menu option is disabled");

		boolean integration2CompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegration2Completed(false,true);
		Assertions.assertTrue(integration2CompletedClamshellOptionIsDisabled,"The 'Integration 2 Completed' clamshell menu option is disabled");

		boolean integrationQueryStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegrationQueryStarted(false,true);
		Assertions.assertTrue(integrationQueryStartedClamshellOptionIsDisabled,"The 'Integration Query Started' clamshell menu option is disabled");

		boolean integrationQueryCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickIntegrationQueryCompleted(false,true);
		Assertions.assertTrue(integrationQueryCompletedClamshellOptionIsDisabled,"The 'Integration Query Completed' clamshell menu option is disabled");

		boolean auditRequestedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditRequested(false,true);
		Assertions.assertTrue(auditRequestedClamshellOptionIsDisabled,"The 'Audit Requested' clamshell menu option is disabled");

		boolean auditReviewStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditReviewStarted(false,true);
		Assertions.assertTrue(auditReviewStartedClamshellOptionIsDisabled,"The 'Audit Review Started' clamshell menu option is disabled");

		boolean auditReviewCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditReviewCompleted(false,true);
		Assertions.assertTrue(auditReviewCompletedClamshellOptionIsDisabled,"The 'Audit Review Completed' clamshell menu option is disabled");

		boolean auditCorrectionsToBeDoneByClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(false,true);
		Assertions.assertTrue(auditCorrectionsToBeDoneByClamshellOptionIsDisabled,"The 'Audit Corrections to be Done by' clamshell menu option is disabled");

		boolean auditCorrectionsStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditCorrectionStarted(false,true);
		Assertions.assertTrue(auditCorrectionsStartedClamshellOptionIsDisabled,"The 'Audit Corrections Started' clamshell menu option is disabled");

		boolean auditCorrectionsCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickAuditCorrectionsCompleted(false,true);
		Assertions.assertTrue(auditCorrectionsCompletedClamshellOptionIsDisabled,"The 'Audit Corrections Completed' clamshell menu option is disabled");

		boolean correctionsAuditRequestedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickCorrectionsAuditRequested(false,true);
		Assertions.assertTrue(correctionsAuditRequestedClamshellOptionIsDisabled,"The 'Corrections Audit Requested' clamshell menu option is disabled");

		boolean correctionsAuditStartedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickCorrectionsAuditStarted(false,true);
		Assertions.assertTrue(correctionsAuditStartedClamshellOptionIsDisabled,"The 'Corrections Audit Started' clamshell menu option is disabled");

		boolean correctionsAuditCompletedClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickCorrectionsAuditCompleted(false,true);
		Assertions.assertTrue(correctionsAuditCompletedClamshellOptionIsDisabled,"The 'Corrections Audit Completed' clamshell menu option is disabled");

		boolean cleanClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickClean(false,true);
		Assertions.assertTrue(cleanClamshellOptionIsDisabled,"The 'Clean' clamshell menu option is disabled");

		boolean releasedToWestlawClamshellOptionIsDisabled = lineageTabTrackClamshellPage().clickReleasedToWestlaw(false,true);
		Assertions.assertTrue(releasedToWestlawClamshellOptionIsDisabled,"The 'Released to Westlaw' clamshell menu option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Reports clamshell options are disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabReportsClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	clamshellPage().openSideBarReport();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean prepReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickPrepReport(false, true);
    	Assertions.assertTrue(prepReportClamshellOptionIsDisabled,"The 'PREP Report' clamshell option is disabled");

		boolean unusedReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickUnusedReport(false, true);
		Assertions.assertTrue(unusedReportClamshellOptionIsDisabled,"The 'Unused Report' clamshell option is disabled");

		boolean integrationSummaryClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickIntegrationSummary(false, true);
		Assertions.assertTrue(integrationSummaryClamshellOptionIsDisabled,"The 'Integration Summary' clamshell option is disabled");

		boolean integrationResultsClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickIntegrationResults(false, true);
		Assertions.assertTrue(integrationResultsClamshellOptionIsDisabled,"The 'Integration Results' clamshell option is disabled");

		boolean auditsClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickAudits(false, true);
		Assertions.assertTrue(auditsClamshellOptionIsDisabled,"The 'Audits' clamshell option is disabled");

		boolean randomBillReportsClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickRandomBillReports(false, true);
		Assertions.assertTrue(randomBillReportsClamshellOptionIsDisabled,"The 'Random Bill Report' clamshell option is disabled");

		boolean stageCheckReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickStageCheckReport(false, true);
		Assertions.assertTrue(stageCheckReportClamshellOptionIsDisabled,"The 'Stage Check Report' clamshell option is disabled");

		boolean mismatchedReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickMismatchedReport(false, true);
		Assertions.assertTrue(mismatchedReportClamshellOptionIsDisabled,"The 'Mismatched Report' clamshell option is disabled");

		boolean billGapCountReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickBillGapCountReport(false, true);
		Assertions.assertTrue(billGapCountReportClamshellOptionIsDisabled,"The 'Bill/Gap Count Report' clamshell option is disabled");

		boolean lockReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickLockReport(false, true);
		Assertions.assertTrue(lockReportClamshellOptionIsDisabled,"The 'Lock Report' clamshell option is disabled");

		boolean indexReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickIndexReport(false, true);
		Assertions.assertTrue(indexReportClamshellOptionIsDisabled,"The 'Index Report' clamshell option is disabled");

		String addIndexReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickAddIndexReport(false);
		Assertions.assertTrue(addIndexReportClamshellOptionIsDisabled.equals(""),"The 'Add Index Report' clamshell option is disabled");

		boolean repealIndexReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickRepealIndexReport(false, true);
		Assertions.assertTrue(repealIndexReportClamshellOptionIsDisabled,"The 'Repeal Index Report' clamshell option is disabled");

		boolean combinedIndexReportClamshellOptionIsDisabled = lineageTabReportClamshellPage().clickCombinedIndexReport(false, true);
		Assertions.assertTrue(combinedIndexReportClamshellOptionIsDisabled,"The 'Combined Index Report' clamshell option is disabled");
     }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct Sync clamshell option is disabled when selecting the lineage tab from a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void lineageTabSyncClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL", Doc Number="SB1"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	sourcePage().goToLineageTab();
    	
    	clamshellPage().openSideBarSync();
    	sourceNavigateGridPage().clickFirstRendition();

		boolean syncClamshellOptionIsDisabled = lineageTabSyncClamshellPage().clickSync(false,true);
		Assertions.assertTrue(syncClamshellOptionIsDisabled,"The 'Sync' clamshel option is disabled");
    }
    
    private void filterForSourceDocument()
    {
    	sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
    	sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
    	sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
    	sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
    	sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
    	sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
    	sourceNavigateFiltersAndSortsPage().setFilterDocNumber("SB1");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    }
}