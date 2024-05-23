package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RenditionContextMenuElements
{
	public static final String RENDITION_CONTEXT_MENU = "//div[@id='contextMenu']";
	public static final String RENDITION_CONTEXT_MENU_VIEW = RENDITION_CONTEXT_MENU + "//div[@id='view']";
	public static final String RENDITION_CONTEXT_MENU_REPORT = RENDITION_CONTEXT_MENU + "//div[@id='report']";
	public static final String RENDITION_CONTEXT_MENU_MODIFY = RENDITION_CONTEXT_MENU + "//div[@id='modify']";
	public static final String RENDITION_CONTEXT_MENU_EDIT = RENDITION_CONTEXT_MENU + "//div[@id='edit']";
	public static final String RENDITION_CONTEXT_MENU_TRACK = RENDITION_CONTEXT_MENU + "//div[@id='track']";
	public static final String RENDITION_CONTEXT_MENU_CREATE = RENDITION_CONTEXT_MENU + "//div[@id='Create']";
	public static final String VIEW_XML_EXTRACT = "//div/span[contains(@id,'view') and text()='XML Extract']";
	public static final String XML_EXTRACT_TITLE = "Xml Extract";
	public static final String HEADER_IMAGE = "//img[@class='headerImage']";
	public static final String CLASSIFY_IN_CHC = "//div[@id='contextMenu']//a[text()='Classify in CHC']";
	public static final String VIEW = RENDITION_CONTEXT_MENU + "//a[text()='View']";
	public static final String RENDITION = RENDITION_CONTEXT_MENU + "//a[text()='Rendition']";
	public static final String TAX_TYPE_ADD = "//div[@id='contextMenu']//a[text()='Tax Type Add']";
	public static final String UPDATE_YEAR_SESSION = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Year or Session']";

	// Report
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Report']")
	public static WebElement report;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Add Content']")
	public static WebElement addContent;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Spellcheck Report']")
	public static WebElement reportSpellcheckReport;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Audits']")
	public static WebElement reportAudits;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Shared Delta Report']")
	public static WebElement reportSharedDeltaReport;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Sync']")
	public static WebElement sync;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Modify']")
	public static WebElement modify;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Undelete Rendition']")
	public static WebElement modifyUndeleteRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Undelete Renditions']")
	public static WebElement modifyUndeleteRenditions;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Cumulate Credits']")
	public static WebElement modifyCumulateCredits;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Integrate']")
	public static WebElement modifyIntegrate;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Insert Into Hierarchy Wizard']")
	public static WebElement modifyInsertIntoHierarchyWizard;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Reset Integration Status']")
	public static WebElement modifyResetIntegrateStatus;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Delete Renditions']")
	public static WebElement modifyDeleteRenditions;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Find Fiscal Notes and Publish']")
	public static WebElement modifyFindFiscalNotesAndPublish;

	public static final String EDIT = "//div[@id='contextMenu']//a[text()='Edit']";

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Edit']")
	public static WebElement edit;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Delta Group']")
	public static WebElement deltaGroup;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Effective Date']")
	public static WebElement editEffectiveDate;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Class Number Wizard']")
	public static WebElement editClassNumberWizard;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Approval Date Wizard']")
	public static WebElement editApprovalDateWizard;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='West ID Wizard']")
	public static WebElement editWestIdWizard;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Rendition']")
	public static WebElement editRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Rendition Notes']")
	public static WebElement editRenditionNotes;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Source Front']")
	public static WebElement editSourceFront;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Source End']")
	public static WebElement editSourceEnd;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Integration Properties']")
	public static WebElement editIntegrationProperties;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Difficulty Level']")
	public static WebElement editDifficultyLevel;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Rendition XML']")
	public static WebElement editRenditionXml;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Topical Heading/Index Entry Features']")
	public static WebElement editTopicalHeadingIndexEntryFeatures;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_EDIT + "//a[text()='Year or Session']")
	public static WebElement YearOrSession;

	@FindBy(how = How.XPATH, using = "//div[@id='contextMenu']//div[@id='validate']//a[text()='Validate and Update Links']")
	public static WebElement validateValidateAndUpdateLinks;

	@FindBy(how = How.XPATH, using = "//div[@id='contextMenu']//div[@id='view']//a[text()='Rendition Baselines']")
	public static WebElement viewRenditionsBaselines;

	@FindBy(how = How.XPATH, using = "//div[@id='contextMenu']//a[text()='Classify in CHC']")
	public static WebElement classifyInCHC;

	@FindBy(how = How.XPATH, using = "//div[@id='contextMenu']//div[@id='edit']//a[text()='Section' or text()='Sections']")
	public static WebElement editSections;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Rendition']")
	public static WebElement rendition;

	@FindBy(how = How.XPATH, using = "//a[text()='Rendition Debug']")
	public static WebElement renditionDebug;

	@FindBy(how = How.XPATH, using = "//a[text()='Source Front']")
	public static WebElement sourceFront;

	@FindBy(how = How.XPATH, using = "//a[text()='Source Front Debug']")
	public static WebElement sourceFrontDebug;

	@FindBy(how = How.XPATH, using = "//a[text()='Source End']")
	public static WebElement sourceEnd;

	@FindBy(how = How.XPATH, using = "//a[text()='Source End Debug']")
	public static WebElement sourceEndDebug;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='View']")
	public static WebElement view;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Track']")
	public static WebElement track;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Validate']")
	public static WebElement validate;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Rendition Properties']")
	public static WebElement renditionProperties;
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view5Text')]")
	public static WebElement sidebarMultipleAndDuplicate;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Rendition' or text()='Renditions']")
	public static WebElement viewRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Rendition Notes']")
	public static WebElement viewRenditionNotes;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Source Front']")
	public static WebElement viewSourceFront;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Rendition XML']")
	public static WebElement viewRenditionXml;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Source End']")
	public static WebElement viewSourceEnd;
		
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Deltas Affecting Same Target']")
	public static WebElement viewDeltasAffectingSameTarget;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='XML Extract']")
	public static WebElement viewXmlExtract;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Multiple and Duplicate Renditions']")
	public static WebElement viewMultipleAndDuplicateRenditions;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Rendition Print Preview']")
	public static WebElement viewRenditionPrintPreview;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Tabular Mainframe Print Preview']")
	public static WebElement viewTabularMainframePrintPreview;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Tabular Print Preview']")
	public static WebElement viewTabularPrintPreview;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Tabular WL Preview']")
	public static WebElement viewTabularWlPreview;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Classify in CHC']")
	public static WebElement viewClassifyInChc;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Send to Vendor for Capture']")
	public static WebElement viewSendToVendorForCapture;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Add Content']")
	public static WebElement viewAddContent;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Create Preparation Document']")
	public static WebElement viewCreatePreperationDocument;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Create']")
	public static WebElement create;

	@FindBy(how = How.XPATH, using = "//a[text()='Create Preparation Document']")
	public static WebElement createPreparationDocument;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_CREATE + "//a[text()='Add Content']")
	public static WebElement createAddContent;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Transfer']")
	public static WebElement transfer;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Create Bookmark']")
	public static WebElement createBookmark;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Create Target Document']")
	public static WebElement createTargetDocumenet;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Move Task']")
	public static WebElement MoveTask;
	
	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_VIEW + "//a[text()='Rendition Baselines']")
	public static WebElement viewRenditionBaselines;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Section Grouping']")
	public static WebElement modifySectionGrouping;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Delta Grouping']")
	public static WebElement modifyDeltaGrouping;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Delete Rendition']")
	public static WebElement modifyDeleteRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Veto Rendition']")
	public static WebElement modifyVetoRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Omit Rendition']")
	public static WebElement modifyOmitRendition;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_MODIFY + "//a[text()='Cite Locate']")
	public static WebElement modifyCiteLocate;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Add Index Report']")
	public static WebElement reportAddIndexReport;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Run Compare and Markup Report']")
	public static WebElement reportCompareAndMarkupReport;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Run Compare Report']")
	public static WebElement reportCompareReport;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Repeal Index Report']")
	public static WebElement reportRepealIndexReport;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_REPORT + "//a[text()='Combined Index Report']")
	public static WebElement reportCombinedIndexReport;

	public static final String MODIFY_SECTION_OR_DELTA_GROUPING = "//div[@id='contextMenu']//div[@id='modify']//a[text()='%s Grouping']";
	public static final String EDIT_SECTION_OR_DELTA_GROUPING = "//div[@id='contextMenu']//div[@id='edit']//a[text()='%s Group']";
	public static final String GIVEN_LEVEL_GROUP_PROPERTIES = "//div[@id='contextMenu']//a[text()='%s Group Properties']";
	public static final String MODIFY_ASSIGN_USER = "//div[@id='contextMenu']//div[@id='modify']//a[text()='Assign User']";

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='APV Started']")
	public static WebElement trackAPVStarted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Topical Heading Needed']")
	public static WebElement trackTopicalHeadingNeeded;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Transfer to BTS']")
	public static WebElement transferTransferToBTS;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Transfer to LMS']")
	public static WebElement transferTransferToLMS;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Run Validations']")
	public static WebElement validateRunValidations;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='View Validations']")
	public static WebElement validateViewValidations;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Tabular Requested']")
	public static WebElement trackTabularRequested;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Tabular Started']")
	public static WebElement trackTabularStarted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='APV Review Started']")
	public static WebElement trackAPVReviewStarted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Ready for Advance Sheet']")
	public static WebElement trackReadyForAdvanceSheet;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='APV Completed']")
	public static WebElement trackAPVCompleted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Chapter/Approval Received']")
	public static WebElement trackChapterApprovalReceived;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Topical Heading Completed']")
	public static WebElement topicalHeadingCompleted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Images Completed']")
	public static WebElement imagesCompleted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Images Sent Out']")
	public static WebElement imagesSentOut;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Tabular Received Hardcopy']")
	public static WebElement tabularReceivedHardcopy;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Tabular Completed']")
	public static WebElement tabularCompleted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='APV Review Completed']")
	public static WebElement apvReviewCompleted;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU + "//a[text()='Published in Advance Sheet']")
	public static WebElement publishedInAdvancedSheet;

	@FindBy(how = How.XPATH, using = RENDITION_CONTEXT_MENU_TRACK + "//a[text()='Sync to Westlaw Completed']")
	public static WebElement syncToWestlawCompleted;
}
