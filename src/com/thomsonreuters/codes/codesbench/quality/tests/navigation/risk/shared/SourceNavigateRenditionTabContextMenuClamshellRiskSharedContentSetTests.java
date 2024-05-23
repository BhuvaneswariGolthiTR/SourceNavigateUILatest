package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigateRenditionTabContextMenuClamshellRiskSharedContentSetTests extends TestService
{
	private static final String IOWA_CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the correct context menu options are disabled or enabled for the non-APVRS rendition for a risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabContextMenuNonAPVRSDocumentRiskSharedTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
		
    	sourceNavigateGridPage().rightClickFirstRendition();

		boolean editContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.edit);
		boolean viewContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.view);

		renditionContextMenu().openViewSubMenu();

		boolean viewRenditionContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewRendition);
		boolean viewRenditionNotesContextMenuOptionIsDisabled =  renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewRenditionNotes);
		boolean viewSourceFrontContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSourceFront);
		boolean viewSourceEndContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSourceEnd);
		boolean viewRenditionBaselinesContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewRenditionBaselines);
		boolean viewDeltasAffectingSameTargetContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewDeltasAffectingSameTarget);
		boolean viewRenditionXMLContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewRenditionXml);
		boolean viewXMLExtractContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewXmlExtract);
		boolean viewRenditionPrintPreviewContextMenuOptionIsDisabled =  renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewRenditionPrintPreview);
		boolean viewTabularMainframePrintPreviewContextMenuOptionIsDisabled =  renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewTabularMainframePrintPreview);
		boolean viewTabularPrintPreviewContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewTabularPrintPreview);
		boolean viewTabularWLPreviewContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewTabularWlPreview);
		boolean modifyContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.modify);
		boolean renditionPropertiesContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.renditionProperties);
		boolean classifyInCHCContextMenuOptionIsDisabled =  renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewClassifyInChc);
		boolean sendToVendorForCaptureContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSendToVendorForCapture);
		boolean createPreparationDocumentContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createPreparationDocument);
		boolean addContentContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.addContent);
		boolean validateContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.validate);
		boolean trackContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.track);
		boolean reportContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.report);
		boolean transferContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.transfer);
		boolean syncContextMenuOptionIsDisabled  = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
		boolean createBookmarkContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createBookmark);
		boolean createTargetDocumentContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.createTargetDocumenet);
		boolean moveTaskContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.MoveTask);

		//Filter multiple/duplicates to multiple
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    	
    	sourceNavigateGridPage().rightClickFirstRendition();
		renditionContextMenu().openViewSubMenu();
    	
		boolean viewMultipleAndDuplicateRenditionsContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewMultipleAndDuplicateRenditions);
				
		Assertions.assertAll
		(
				() -> Assertions.assertTrue(editContextMenuOptionIsDisabled, "The 'Edit' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewContextMenuOptionIsDisabled, "The 'View' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewRenditionContextMenuOptionIsDisabled, "The 'View -> Rendition' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewRenditionNotesContextMenuOptionIsDisabled, "The 'View -> Rendition Notes' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSourceFrontContextMenuOptionIsDisabled, "The 'View -> Source Front' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewSourceEndContextMenuOptionIsDisabled, "The 'View -> Source End' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewRenditionBaselinesContextMenuOptionIsDisabled, "The 'View -> Rendition Baselines' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(viewDeltasAffectingSameTargetContextMenuOptionIsDisabled, "The 'View -> Deltas Affecting Same Target' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewMultipleAndDuplicateRenditionsContextMenuOptionIsDisabled, "The 'View -> Multiple and Duplicate Renditions' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewRenditionXMLContextMenuOptionIsDisabled, "The 'View -> Rendition XML' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertFalse(viewXMLExtractContextMenuOptionIsDisabled, "The 'View -> XML Extract' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewRenditionPrintPreviewContextMenuOptionIsDisabled, "The 'View -> Rendition Print Preview' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(viewTabularMainframePrintPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular Mainframe Print Preview' context menu option is disabled when it should be enabled"),
				() -> Assertions.assertTrue(viewTabularPrintPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular Print Preview' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(viewTabularWLPreviewContextMenuOptionIsDisabled, "The 'View -> Tabular WL Preview' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(modifyContextMenuOptionIsDisabled, "The 'Modify' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(renditionPropertiesContextMenuOptionIsDisabled, "The 'Rendition Properties' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(classifyInCHCContextMenuOptionIsDisabled, "The 'Classify in CHC' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(sendToVendorForCaptureContextMenuOptionIsDisabled, "The 'Send to Vendor for Capture' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(createPreparationDocumentContextMenuOptionIsDisabled, "The 'Create Preparation Document' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(addContentContextMenuOptionIsDisabled, "The 'Add Content' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(validateContextMenuOptionIsDisabled, "The 'Validate' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(trackContextMenuOptionIsDisabled, "The 'Track' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(reportContextMenuOptionIsDisabled, "The 'Report' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(transferContextMenuOptionIsDisabled, "The 'Transfer' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(syncContextMenuOptionIsDisabled, "The 'Sync' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertFalse(createBookmarkContextMenuOptionIsDisabled, "The 'Create Bookmark' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(createTargetDocumentContextMenuOptionIsDisabled, "The 'Create Target Document' context menu option is enabled when it should be disabled"),
				() -> Assertions.assertTrue(moveTaskContextMenuOptionIsDisabled, "The 'Move Task' context menu option is enabled when it should be disabled")
			);
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Edit clamshell options are disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabEditClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();

    	sourceNavigateGridPage().clickFirstRendition();
		clamshellPage().openSideBarEdit();

    	boolean renditionClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickRendition(false,true);
    	Assertions.assertTrue(renditionClamsellOptionIsDisabled,"The 'Rendition(s)' clamshell option is disabled");

		boolean integrationPropertiesClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickIntegrationProperties(false,true);
		Assertions.assertTrue(integrationPropertiesClamsellOptionIsDisabled,"The 'Integration Properties' clamshell option is disabled");

		boolean difficultyLevelClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickDifficultyLevel(false,true);
		Assertions.assertTrue(difficultyLevelClamsellOptionIsDisabled,"The 'Difficulty Level' clamshell option is disabled");

		boolean renditionXmlClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickRenditionXml(false,true);
		Assertions.assertTrue(renditionXmlClamsellOptionIsDisabled,"The 'Rendition XML' clamshell option is disabled");

		boolean propertiesClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickProperties(false,true);
		Assertions.assertTrue(propertiesClamsellOptionIsDisabled,"The 'Properties' clamshell option is disabled");

		boolean editPdfMetadataClamsellOptionIsDisabled = renditionTabEditClamshellPage().clickEditPdfMetadata(false,true);
		Assertions.assertTrue(editPdfMetadataClamsellOptionIsDisabled,"The 'Edit PDF/Metadata' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following View clamshell options are correctly enabled or disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabViewClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();

    	sourceNavigateGridPage().clickFirstRendition();
		clamshellPage().openSideBarView();

		boolean baselinesClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickBaselines(false,true);
		Assertions.assertTrue(baselinesClamsellOptionIsEnabled,"The 'Baselines' clamshell option is disabled");

		boolean deltasAffectingSameTargetClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickDeltasAffectingSameTarget(false);
		Assertions.assertTrue(deltasAffectingSameTargetClamsellOptionIsEnabled,"The 'Deltas Affecting Same Target' clamshell option is disabled");

		boolean renditionXmlClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickRenditionXml(true,true);
		Assertions.assertTrue(renditionXmlClamsellOptionIsEnabled,"The 'Rendition XML' clamshell option is enabled");

		sourcePage().switchToSourceNavigatePage();

		boolean xmlExtractClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickXmlExtract(true,true);
		Assertions.assertTrue(xmlExtractClamsellOptionIsEnabled,"The 'Xml Extract' clamshell option is enabled");

		sourcePage().switchToSourceNavigatePage();

		boolean renditionPrintPreviewClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickRenditionPrintPreview(true,true);
		Assertions.assertTrue(renditionPrintPreviewClamsellOptionIsEnabled,"The 'Rendition Print Preview' clamshell option is enabled");

		sourcePage().switchToSourceNavigatePage();

		boolean tabularPrintPreviewClamsellOptionIsEnabled = renditionTabViewClamshellPage().clickTabularPrintPreview(true,true);
		Assertions.assertTrue(tabularPrintPreviewClamsellOptionIsEnabled,"The 'Tabular Print Preview' clamshell option is enabled");
    }

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following View clamshell options are correctly enabled or disabled when selecting a multiple/duplicate non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabViewMultipleDuplicateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
		sourceNavigateFooterToolsPage().refreshTheGrid();

    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarView();
    	
    	boolean viewMultiplesAndDuplicatesIsEnabled = renditionTabViewClamshellPage().clickMultiplesAndDuplicates(true, false);

    	sourceNavigateGridPage().clearFilters();
		sourceNavigateGridPage().clickFirstRendition();
		clamshellPage().openSideBarView();

    	boolean viewMultiplesAndDuplicatesIsEnabledAfterClearingFilters = renditionTabViewClamshellPage().clickMultiplesAndDuplicates(true, false);
    	
    	Assertions.assertAll
		(
        		() -> Assertions.assertTrue(viewMultiplesAndDuplicatesIsEnabled, "The 'View -> Multiples and Duplicates' context menu option is disabled when it should be enabled"),
        		() -> Assertions.assertFalse(viewMultiplesAndDuplicatesIsEnabledAfterClearingFilters, "The 'View -> Multiples and Duplicates' context menu option is enabled when it should be disabled")
        );
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Modify clamshell options are correctly disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabModifyClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarModify();

		boolean splitRenditionBySectionClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickSplitRenditionBySection(false,true);
		Assertions.assertTrue(splitRenditionBySectionClamshellOptionIsDisabled,"The 'Split Rendition by Section' clamshell option is disabled");

		boolean markAsNonDuplicateClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickMarkAsNonDuplicate(false,true);
		Assertions.assertTrue(markAsNonDuplicateClamshellOptionIsDisabled,"The 'Mark as Non-Duplicate' clamshell option is disabled");

		boolean omitRenditionClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickOmitRendition(false,true);
		Assertions.assertTrue(omitRenditionClamshellOptionIsDisabled,"The 'Omit Rendition' clamshell option is disabled");

		boolean vetoRenditionClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickVetoRendition(false,true);
		Assertions.assertTrue(vetoRenditionClamshellOptionIsDisabled,"The 'Veto Rendition' clamshell option is disabled");

		boolean deleteRenditionClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickDeleteRendition(false,true);
		Assertions.assertTrue(deleteRenditionClamshellOptionIsDisabled,"The 'Delete Rendition' clamshell option is disabled");

		boolean undeleteRenditionClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickUndeleteRendition(false,true);
		Assertions.assertTrue(undeleteRenditionClamshellOptionIsDisabled,"The 'Undelete Rendition' clamshell option is disabled");

		boolean integrateClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickIntegrate(false,true);
		Assertions.assertTrue(integrateClamshellOptionIsDisabled,"The 'Integrate' clamshell option is disabled");

		boolean assignUserClamshellOptionIsDisabled = renditionTabModifyClamshellPage().clickAssignUser(false,true);
		Assertions.assertTrue(assignUserClamshellOptionIsDisabled,"The 'Assign User' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Create clamshell options are correctly disabled or enabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabCreateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarCreate();

		boolean createPrepDocumentClamshellOptionIsDisabled = renditionTabCreateClamshellPage().clickCreatePrepDocument(false,true);
		Assertions.assertTrue(createPrepDocumentClamshellOptionIsDisabled,"The 'Create Prep Document' clamshell option is disabled");

		boolean addContentClamshellOptionIsDisabled = renditionTabCreateClamshellPage().clickAddContent(false,true);
		Assertions.assertTrue(addContentClamshellOptionIsDisabled,"The 'Add Content' clamshell option is disabled");

		boolean addPdfMetadataClamshellOptionIsEnabled = renditionTabCreateClamshellPage().clickAddPDFMetadata(true,true);
		Assertions.assertTrue(addPdfMetadataClamshellOptionIsEnabled,"The 'Add PDF/Metadata' clamshell option is enabled");
    }
    
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Validate clamshell options are correctly disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabValidateClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarValidate();

		boolean runValidationsClamshellOptionIsDisabled = renditionTabValidateClamshellPage().clickRunValidations(false,true);
		Assertions.assertTrue(runValidationsClamshellOptionIsDisabled,"The 'Run Validations' clamshell option is disabled");

		String validateAndUpdateLinksClamshellOptionIsDisabled = renditionTabValidateClamshellPage().clickValidateAndUpdateLinks(false,true);
		Assertions.assertTrue(validateAndUpdateLinksClamshellOptionIsDisabled.equals(""),"The 'Validate and Update Links' clamshell option is disabled");

		boolean viewValidationsClamshellOptionIsDisabled = renditionTabValidateClamshellPage().clickViewValidations(false,true);
		Assertions.assertTrue(viewValidationsClamshellOptionIsDisabled,"The 'View Validations' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Track clamshell options are correctly disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabTrackClamshellMenuNonAPVRSDocumentRiskSharedTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarTrack();

		boolean syncToWestlawCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickSyncToWestlawCompleted(false,true);
		Assertions.assertTrue(syncToWestlawCompletedClamshellOptionIsDisabled,"The 'Sync to Westlaw Completed' clamshell option is disabled");

		boolean chapterApprovalReceivedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickChapterApprovalReceived(false,true);
		Assertions.assertTrue(chapterApprovalReceivedClamshellOptionIsDisabled,"The 'Chapter/Approval Received' clamshell option is disabled");

		boolean apvStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickApvStarted(false,true);
		Assertions.assertTrue(apvStartedClamshellOptionIsDisabled,"The 'APV Started' clamshell option is disabled");

		boolean apvCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickApvCompleted(false,true);
		Assertions.assertTrue(apvCompletedClamshellOptionIsDisabled,"The 'APV Completed' clamshell option is disabled");

		boolean topicalHeadingNeededClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTopicalHeadingNeeded(false,true);
		Assertions.assertTrue(topicalHeadingNeededClamshellOptionIsDisabled,"The 'Topical Heading Needed' clamshell option is disabled");

		boolean topicalHeadingCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTopicalHeadingCompleted(false,true);
		Assertions.assertTrue(topicalHeadingCompletedClamshellOptionIsDisabled,"The 'Topical Heading Completed' clamshell option is disabled");

		boolean imagesSentOutClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickImagesSentOut(false,true);
		Assertions.assertTrue(imagesSentOutClamshellOptionIsDisabled,"The 'Images Sent Out' clamshell option is disabled");

		boolean imagesCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickImagesCompleted(false,true);
		Assertions.assertTrue(imagesCompletedClamshellOptionIsDisabled,"The 'Images Completed' clamshell option is disabled");

		boolean tabularRequestedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTabularRequested(false,true);
		Assertions.assertTrue(tabularRequestedClamshellOptionIsDisabled,"The 'Tabular Requested' clamshell option is disabled");

		boolean tabularReceivedHardCopyClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTabularReceivedHardcopy(false,true);
		Assertions.assertTrue(tabularReceivedHardCopyClamshellOptionIsDisabled,"The 'Tabular Received Hard Copy' clamshell option is disabled");

		boolean tabularStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTabularStarted(false,true);
		Assertions.assertTrue(tabularStartedClamshellOptionIsDisabled,"The 'Tabular Started' clamshell option is disabled");

		boolean tabularCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickTabularCompleted(false,true);
		Assertions.assertTrue(tabularCompletedClamshellOptionIsDisabled,"The 'Tabular Completed' clamshell option is disabled");

		boolean apvReviewStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickApvReviewStarted(false,true);
		Assertions.assertTrue(apvReviewStartedClamshellOptionIsDisabled,"The 'APV Review Started' clamshell option is disabled");

		boolean apvReviewCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickApvReviewCompleted(false,true);
		Assertions.assertTrue(apvReviewCompletedClamshellOptionIsDisabled,"The 'APV Review Completed' clamshell option is disabled");

		boolean readyForAdvanceSheetClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickReadyForAdvanceSheet(false,true);
		Assertions.assertTrue(readyForAdvanceSheetClamshellOptionIsDisabled,"The 'Ready for Advance Sheet' clamshell option is disabled");

		boolean publishedInAdvancedSheetClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickPublishedInAdvanceSheet(false,true);
		Assertions.assertTrue(publishedInAdvancedSheetClamshellOptionIsDisabled,"The 'Published in Advance Sheet' clamshell option is disabled");

		//The click first rendition method is not needed here because test will fail due to the proceeding elements not being displayed on the page
		boolean attorneyWorkStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAttorneyWorkStarted(false,true);
		Assertions.assertTrue(attorneyWorkStartedClamshellOptionIsDisabled,"The 'Attorney Work Started' clamshell option is disabled");

		boolean attorneyWorkCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAttorneyWorkCompleted(false,true);
		Assertions.assertTrue(attorneyWorkCompletedClamshellOptionIsDisabled,"The 'Attorney Work Completed' clamshell option is disabled");

		boolean versioningWorkStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickVersioningWorkStarted(false,true);
		Assertions.assertTrue(versioningWorkStartedClamshellOptionIsDisabled,"The 'Versioning Work Started' clamshell option is disabled");

		boolean versioningWorkCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickVersioningWorkCompleted(false,true);
		Assertions.assertTrue(versioningWorkCompletedClamshellOptionIsDisabled,"The 'Versioning Work Completed' clamshell option is disabled");

		boolean prepStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickPrepStarted(false,true);
		Assertions.assertTrue(prepStartedClamshellOptionIsDisabled,"The 'PREP Started' clamshell option is disabled");

		boolean prepCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickPrepCompleted(false,true);
		Assertions.assertTrue(prepCompletedClamshellOptionIsDisabled,"The 'PREP Completed' clamshell option is disabled");

		boolean readyForIntegrationClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickReadyForIntegration(false,true);
		Assertions.assertTrue(readyForIntegrationClamshellOptionIsDisabled,"The 'Ready for Integration' clamshell option is disabled");

		boolean integrationStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegrationStarted(false,true);
		Assertions.assertTrue(integrationStartedClamshellOptionIsDisabled,"The 'Integration Started' clamshell option is disabled");

		boolean integrationCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegrationCompleted(false,true);
		Assertions.assertTrue(integrationCompletedClamshellOptionIsDisabled,"The 'Integration Completed' clamshell option is disabled");

		boolean integration2StartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegration2Started(false,true);
		Assertions.assertTrue(integration2StartedClamshellOptionIsDisabled,"The 'Integration 2 Started' clamshell option is disabled");

		boolean integration2CompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegration2Completed(false,true);
		Assertions.assertTrue(integration2CompletedClamshellOptionIsDisabled,"The 'Integration 2 Completed' clamshell option is disabled");

		boolean integrationQueryStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegrationQueryStarted(false,true);
		Assertions.assertTrue(integrationQueryStartedClamshellOptionIsDisabled,"The 'Integration Query Started' clamshell option is disabled");

		boolean integrationQueryCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickIntegrationQueryCompleted(false,true);
		Assertions.assertTrue(integrationQueryCompletedClamshellOptionIsDisabled,"The 'Integration Query Completed' clamshell option is disabled");

		boolean auditRequestedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditRequested(false,true);
		Assertions.assertTrue(auditRequestedClamshellOptionIsDisabled,"The 'Audit Requested' clamshell option is disabled");

		boolean auditReviewStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditReviewStarted(false,true);
		Assertions.assertTrue(auditReviewStartedClamshellOptionIsDisabled,"The 'Audit Review Started' clamshell option is disabled");

		boolean auditReviewCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditReviewCompleted(false,true);
		Assertions.assertTrue(auditReviewCompletedClamshellOptionIsDisabled,"The 'Audit Review Completed' clamshell option is disabled");

		boolean auditCorrectionsToBeDoneByClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditCorrectionsToBeDoneBy(false,true);
		Assertions.assertTrue(auditCorrectionsToBeDoneByClamshellOptionIsDisabled,"The 'Audit Corrections to be Done by' clamshell option is disabled");

		boolean auditCorrectionsStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditCorrectionStarted(false,true);
		Assertions.assertTrue(auditCorrectionsStartedClamshellOptionIsDisabled,"The 'Audit Corrections Started' clamshell option is disabled");

		boolean auditCorrectionsCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickAuditCorrectionsCompleted(false,true);
		Assertions.assertTrue(auditCorrectionsCompletedClamshellOptionIsDisabled,"The 'Audit Corrections Completed' clamshell option is disabled");

		boolean correctionsAuditRequestedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickCorrectionsAuditRequested(false,true);
		Assertions.assertTrue(correctionsAuditRequestedClamshellOptionIsDisabled,"The 'Corrections Audit Requested' clamshell option is disabled");

		boolean correctionsAuditStartedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickCorrectionsAuditStarted(false,true);
		Assertions.assertTrue(correctionsAuditStartedClamshellOptionIsDisabled,"The 'Corrections Audit Started' clamshell option is disabled");

		boolean correctionsAuditCompletedClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickCorrectionsAuditCompleted(false,true);
		Assertions.assertTrue(correctionsAuditCompletedClamshellOptionIsDisabled,"The 'Corrections Audit Completed' clamshell option is disabled");

		boolean cleanClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickClean(false,true);
		Assertions.assertTrue(cleanClamshellOptionIsDisabled,"The 'Clean' clamshell option is disabled");

		boolean releasedToWestlawClamshellOptionIsDisabled = renditionTabTrackClamshellPage().clickReleasedToWestlaw(false,true);
		Assertions.assertTrue(releasedToWestlawClamshellOptionIsDisabled,"The 'Release to Westlaw' clamshell option is disabled");
   }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Reports clamshell options are correctly disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabReportsClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarReport();

		boolean prepReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickPrepReport(false, true);
		Assertions.assertTrue(prepReportClamshellOptionIsDisabled,"The 'Prep Report' clamshell option is disabled");

		boolean unusedReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickUnusedReport(false, true);
		Assertions.assertTrue(unusedReportClamshellOptionIsDisabled,"The 'Unused Report' clamshell option is disabled");

		boolean integrationSummaryClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickIntegrationSummary(false, true);
		Assertions.assertTrue(integrationSummaryClamshellOptionIsDisabled,"The 'Integration Summary' clamshell option is disabled");

		boolean integrationResultsClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickIntegrationResults(false, true);
		Assertions.assertTrue(integrationResultsClamshellOptionIsDisabled,"The 'Integration Results' clamshell option is disabled");

		boolean auditsClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickAudits(false, true);
		Assertions.assertTrue(auditsClamshellOptionIsDisabled,"The 'Audits' clamshell option is disabled");

		boolean randomBilReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickRandomBillReports(false, true);
		Assertions.assertTrue(randomBilReportClamshellOptionIsDisabled,"The 'Random Bill Report' clamshell option is disabled");

		boolean stageCheckReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickStageCheckReport(false, true);
		Assertions.assertTrue(stageCheckReportClamshellOptionIsDisabled,"The 'Stage Check Report' clamshell option is disabled");

		boolean mismatchedReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickMismatchedReport(false, true);
		Assertions.assertTrue(mismatchedReportClamshellOptionIsDisabled,"The 'Mismatched Report' clamshell option is disabled");

		boolean billGapCountReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickBillGapCountReport(false, true);
		Assertions.assertTrue(billGapCountReportClamshellOptionIsDisabled,"The 'Bill Gap Count Report' clamshell option is disabled");

		boolean lockReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickLockReport(false, true);
		Assertions.assertTrue(lockReportClamshellOptionIsDisabled,"The 'Lock Report' clamshell option is disabled");

		boolean indexReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickIndexReport(false, true);
		Assertions.assertTrue(indexReportClamshellOptionIsDisabled,"The 'Index Report' clamshell option is disabled");

		String addIndexReportClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickAddIndexReport(false);
		Assertions.assertTrue(addIndexReportClamshellOptionIsDisabled.equals(""),"The 'Add Index Report' clamshell option is disabled");

		boolean repealIndexReportsClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickRepealIndexReport(false, true);
		Assertions.assertTrue(repealIndexReportsClamshellOptionIsDisabled,"The 'Repeal Index Reports' clamshell option is disabled");

		boolean combinedIndexReportsClamshellOptionIsDisabled = renditionTabReportClamshellPage().clickCombinedIndexReport(false, true);
		Assertions.assertTrue(combinedIndexReportsClamshellOptionIsDisabled,"The 'Combined Index Reports' clamshell option is disabled");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the following Sync clamshell option is correctly disabled when selecting a non-APVRS rendition for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void renditionTabSyncClamshellMenuNonAPVRSDocumentRiskSharedTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(IOWA_CONTENT_SET);
		sourceMenu().goToSourceC2012Navigate();

		//Filters: Validation="None", Multiple/Duplicate="None", Deleted="Not Deleted", Content Set="Iowa (Development)", Year="2015", Content Type="BILL"
		filterForSourceDocument();
    	
    	sourceNavigateGridPage().clickFirstRendition();
    	clamshellPage().openSideBarSync();

		boolean syncClamshellOptionIsDisabled = renditionTabSyncClamshellPage().clickSync(false,true);
		Assertions.assertTrue(syncClamshellOptionIsDisabled,"The 'Sync' clamshell option is disabled");
    }

    private void filterForSourceDocument()
    {
    	sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
    	sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
    	sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
    	sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
    	sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
    	sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
    	sourceNavigateFooterToolsPage().refreshTheGrid();
    }
}