package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.EditMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeleteConfirmationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeltaGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.TaxTypeAddPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RenditionContextMenu extends ContextMenu
{
	private WebDriver driver;

	@Autowired
	public RenditionContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, RenditionContextMenuElements.class);
	}

	public boolean goToReportSpellcheckReport()
	{
		openContextMenuSubMenu(RenditionContextMenuElements.report);
		sendEnterToElement(RenditionContextMenuElements.reportSpellcheckReport);
		boolean windowAppeared = switchToWindow("Spellcheck");
		enterTheInnerFrame();
		return windowAppeared;
	}
			
	public void goToReportAudits()
	{
		openContextMenuSubMenu(RenditionContextMenuElements.report);
		sendEnterToElement(RenditionContextMenuElements.reportAudits);
		switchToWindow(AuditBySourcePageElements.AUDIT_BY_SOURCE_PAGE_TITLE);
		waitForPageLoaded();
	}

	public void openReportSharedDeltaReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportSharedDeltaReport);
		enterTheInnerFrame();
	}

	public void openReport()
	{
		RenditionContextMenuElements.report.sendKeys(Keys.RIGHT);
	}

	public boolean isContextMenuElementDisabled(WebElement element)
	{
		return isElementDisabled(element);
	}

	public boolean isEditTaxTypeAddDisabled()
	{
		openContextMenuSubMenu(getElement(RenditionContextMenuElements.EDIT));
		return isContextMenuElementDisabled(getElement(RenditionContextMenuElements.TAX_TYPE_ADD));
	}

	public void clickEdit()
	{
		openContextMenuSubMenu(getElement(RenditionContextMenuElements.EDIT));
	}

	public void editRenditionOldWithLockedDoc()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editRendition);
	}

	public boolean editRenditionNotes()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editRenditionNotes);
		return instructionsNotesPage().switchToInstructionNotesWindow();
	}

	public boolean editSourceEnd()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editSourceEnd);
		boolean editorWindowAppears = editorPage().switchToEditorWindow();
		waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		waitForElement(EditorToolbarPageElements.CLOSE_DOC);
		return editorWindowAppears;
	}

	public boolean editSourceFront()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editSourceFront);
		boolean editorWindowAppears = editorPage().switchToEditorWindow();
		waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		waitForElement(EditorToolbarPageElements.CLOSE_DOC);
		return editorWindowAppears;
	}

	public boolean editIntegrationProperties()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editIntegrationProperties);
		return integrationPropertiesPage().switchToIntegrationPropertiesPage();
	}

	public boolean editRenditionXml()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editRenditionXml);
		return sourceRawXmlEditorPage().switchToRawXmlEditorPage();
	}

	public boolean editTopicalHeadingIndexEntryFeatures()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editTopicalHeadingIndexEntryFeatures);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		return topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
	}

	public void edit()
	{
		RenditionContextMenuElements.edit.sendKeys(Keys.ARROW_RIGHT);
	}

	public void view()
	{
		RenditionContextMenuElements.view.sendKeys(Keys.ARROW_RIGHT);
	}

	public boolean clickTaxTypeAdd()
	{
		click(RenditionContextMenuElements.TAX_TYPE_ADD);
		boolean result = switchToWindow(TaxTypeAddPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return result;
	}

	public boolean modifyDeleteRendition()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyDeleteRendition);
		boolean deleteConfirmationWindowAppeared = switchToWindow(DeleteConfirmationPageElements.DELETE_CONFIRMATION_PAGE_TITLE);
		enterTheInnerFrame();
		deleteConfirmationPopupPage().clickYes();
		return deleteConfirmationWindowAppeared;
	}

	public boolean modifyVetoRendition()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyVetoRendition);
		boolean gridRefresh = waitForGridRefresh();
		waitForPageLoaded();
		return gridRefresh;
	}

	public boolean modifyOmitRendition()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyOmitRendition);
		boolean gridRefresh = waitForGridRefresh();
		waitForPageLoaded();
		return gridRefresh;
	}

	public boolean modifyCiteLocate()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyCiteLocate);
		waitForPageLoaded();
		return waitForGridRefresh();
	}

	public boolean editSection()
	{
		openContextMenu(RenditionContextMenuElements.edit,RenditionContextMenuElements.editSections);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public void editLockedSection()
	{
		openContextMenu(RenditionContextMenuElements.edit,RenditionContextMenuElements.editSections);
	}

	public boolean editTaxTypeAdd()
	{
		openContextMenu(RenditionContextMenuElements.EDIT,RenditionContextMenuElements.TAX_TYPE_ADD);
		boolean result = switchToWindow(TaxTypeAddPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return result;
	}

	public void openContextMenuSubMenu(WebElement element)
	{
		sendKeyToElement(element, Keys.ARROW_RIGHT);
	}

	public boolean modifySectionGrouping()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifySectionGrouping);
		return switchToWindow(SectionGroupingPageElements.SECTION_GROUPING_PAGE_TITLE);
	}

	public boolean modifyDeltaGrouping()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyDeltaGrouping);
		return switchToWindow(DeltaGroupingPageElements.DELTA_GROUPING_PAGE_TITLE);
	}
	
	public void clickSideBarViewMulitipleandDuplicate()
	{
		click(RenditionContextMenuElements.sidebarMultipleAndDuplicate);
    	homePage().waitForPageLoaded();
	}

	public boolean openRenditionProperties()
	{
		openContextMenu(RenditionContextMenuElements.renditionProperties);
		boolean pageOpened = switchToWindow(PropertiesPageElements.RENDITION_PROPERTIES_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean transferToBTS()
	{
		openContextMenu(RenditionContextMenuElements.transfer, RenditionContextMenuElements.transferTransferToBTS);
		boolean alertAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true, "The following workflow has been started:");
		waitForGridRefresh();
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
		return alertAppeared;
	}

	public boolean transferToLMS()
	{
		openContextMenu(RenditionContextMenuElements.transfer, RenditionContextMenuElements.transferTransferToLMS);
		boolean alertAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true, "The following workflow has been started:");
		waitForGridRefresh();
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
		return alertAppeared;
	}

	public boolean displayAllResultsandClearFilters()
	{
		return isElementDisplayed("//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']");
	}
	public String validateValidateAndUpdateLinks()
	{
		openContextMenu(RenditionContextMenuElements.validate, RenditionContextMenuElements.validateValidateAndUpdateLinks);
		boolean alert1 = AutoITUtils.verifyAlertTextAndAccept(true,"Validate and Update Links on the selected renditions?");
		String workflowId = AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true,"The following workflow has been started:");
		waitForGridRefresh();
		waitForPageLoaded();
		if(alert1)
		{
			return workflowId;
		}
		return "";
	}

	public boolean validateAndUpdateLinksFirstRenditionBillTrack()
	{
		openContextMenu(RenditionContextMenuElements.validate, RenditionContextMenuElements.validateValidateAndUpdateLinks);

		AutoITUtils.verifyAlertTextAndAccept(true, "Validate and Update Links on the selected renditions?");
		boolean alert2 = AutoITUtils.verifyAlertTextAndAccept(true, "You must select one or more renditions that are not of type BILLTRACK");
		waitForGridRefresh();
		waitForPageLoaded();
		return alert2;
	}

	public void viewRenditionBaselines()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewRenditionsBaselines);
		switchToWindow(ViewBaselinesPageElements.PAGE_TITLE);
		waitForPageLoaded();
		waitForGridRefresh();
		enterTheInnerFrame();
	}

	public void validateRunValidations()
	{
		openContextMenu(RenditionContextMenuElements.validate, RenditionContextMenuElements.validateRunValidations);
	}

	public boolean validateViewValidations()
	{
		openContextMenu(RenditionContextMenuElements.validate, RenditionContextMenuElements.validateViewValidations);
		boolean results = switchToWindow(ValidationReportPageElements.VALIDATION_REPORT_PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
		waitForGridRefresh();
		return results;
	}

	public boolean classifyInCHC()
	{
		openContextMenu(RenditionContextMenuElements.classifyInCHC);
		waitForPageLoaded();
		return switchToWindow("Enterprise Single Sign-On");
	}

	public boolean editEffectiveDate()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editEffectiveDate);
		return effectiveDatePage().switchToEffectiveDatePage();
	}

	public boolean editClassNumberWizard()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editClassNumberWizard);
		return classNumberWizardPage().switchToClassNumberWizardWindow();
	}

	public boolean editApprovalDateWizard()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editApprovalDateWizard);
		return approvalDateWizardPage().switchToApprovalDateWizardWindow();
	}

	public boolean editWestIdWizard()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editWestIdWizard);
		return westIdWizardPage().switchToWestIdWizardWindow();
	}

	public boolean editDifficultyLevel()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.editDifficultyLevel);
		return difficultyLevelPage().switchToDifficultyLevelWindow();
	}

	public boolean modifyCumulateCredits()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyCumulateCredits);
		boolean firstAlert = AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to initiate the Credit Cumulator? This action will modify the selected rendition(s) and create a new baseline for each rendition.");
		boolean secondAlert = AutoITUtils.verifyAlertTextContainsAndAccept(true, "The following workflow has been started:");
		return firstAlert && secondAlert;
	}

	public void modifyResetIntegrationStatus()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyResetIntegrateStatus);
		waitForGridRefresh();
	}

	public void modifyIntegrate()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyIntegrate);
		waitForGridRefresh();
		waitForPageLoaded();
	}

	public boolean modifyInsertIntoHierarchyWizard()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyInsertIntoHierarchyWizard);
		boolean windowAppeared = switchToWindow(InsertIntoHierarchyWizardPageElements.INSERT_INTO_HIERARCHY_WIZARD_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean modifyUndeleteRendition()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyUndeleteRendition);
		AutoITUtils.verifyAlertTextAndAccept(true,
				"This action will undelete the selected rendition(s) and initiate a publish to Westlaw. Continue?");
		AutoITUtils.verifyAlertTextContainsAndAccept(true, "The following workflow has been started for sync processing: ");
		return true;
	}

	public void viewMultipleAndDuplicateRenditions()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewMultipleAndDuplicateRenditions);
		waitForGridRefresh();
	}
	public boolean viewTargetInHierarchy()
	{
		openContextMenu(RenditionContextMenuElements.view, ViewMenuElements.viewTargetInHierarchy);
		return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public boolean modifyDeleteRenditions()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyDeleteRenditions);
		boolean deleteConfirmationWindowAppeared = switchToWindow(DeleteConfirmationPageElements.DELETE_CONFIRMATION_PAGE_TITLE);
		enterTheInnerFrame();
		deleteConfirmationPopupPage().clickYes();
		return deleteConfirmationWindowAppeared;
	}

	public boolean modifyUndeleteRenditions()
	{
		openContextMenu(RenditionContextMenuElements.modify, RenditionContextMenuElements.modifyUndeleteRenditions);
		AutoITUtils.verifyAlertTextContainsAndAccept(true, "");
		AutoITUtils.verifyAlertTextContainsAndAccept(true, "One or more of the selected documents has a multiple or a duplicate flag. Please resolve");
		return true;
	}

	public boolean sync()
	{
		openContextMenu(RenditionContextMenuElements.sync);
		boolean syncPage = switchToWindow(SyncPageElements.SYNC_PAGE_TITLE);
		enterTheInnerFrame();
		clickSubmitButtonOnForm();
		waitForPageLoaded();
		return syncPage;
	}

	public boolean renditionProperties()
	{
		openContextMenu(RenditionContextMenuElements.renditionProperties);
		return renditionPropertiesPage().switchToRenditionPropertiesWindow();
	}

	public boolean createBookmark()
	{
		openContextMenu(RenditionContextMenuElements.createBookmark);
		boolean createBookmarkPage = createBookmarkPage().switchToCreateBookmarkWindow();
		enterTheInnerFrame();
		return createBookmarkPage;
	}

	public boolean createPrepDocument()
	{
		openContextMenu(RenditionContextMenuElements.createPreparationDocument);
		boolean createPreparationDocumentAlert = AutoITUtils.verifyAlertTextAndAccept(true, "PREP Rendition(s) have been previously created for this Rendition. Do you want to proceed with the creation of PREP document(s)?");
		waitForPageLoaded();
		waitForGridRefresh();
		return createPreparationDocumentAlert;
	}

	public boolean createPrepDocumentNotSyncedToWestlaw()
	{
		openContextMenu(RenditionContextMenuElements.createPreparationDocument);
		boolean notSyncedToWestlawAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "One or more of the selected documents have not been synced. Do you wish to continue?");
		waitForPageLoaded();
		waitForGridRefresh();
		return notSyncedToWestlawAlertAppeared;
	}

	public boolean createAddContent()
	{
		openContextMenu(RenditionContextMenuElements.createAddContent);
		return manualDataEntryPage().switchToManualDataEntryWindow();
	}

	public boolean openModifyAssignUser()
	{
		openContextMenu(RenditionContextMenuElements.modify,getElement( RenditionContextMenuElements.MODIFY_ASSIGN_USER));
		return assignUserPage().switchToAssignedUserPage();
	}

	public boolean switchToEditor()
	{
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean modifySectionOrDeltaGrouping(String level)
	{
		openContextMenu(RenditionContextMenuElements.modify,getElement(String.format(RenditionContextMenuElements.MODIFY_SECTION_OR_DELTA_GROUPING, level)));
		boolean pageAppeared =switchToWindow("Grouping");
		waitForPageLoaded();
		return pageAppeared;
	}

	public boolean openEditorByGrouping(String level)
	{
		openContextMenu(RenditionContextMenuElements.edit, getElement(String.format(RenditionContextMenuElements.EDIT_SECTION_OR_DELTA_GROUPING, level)));
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean openPropertiesByGrouping(String level) {
		openContextMenu(String.format(RenditionContextMenuElements.GIVEN_LEVEL_GROUP_PROPERTIES, level));
		boolean pageAppeared = switchToWindow("Group Properties");
		enterTheInnerFrame();
		return pageAppeared;
	}

	public boolean openSectionProperties()
	{
		openContextMenu(EditMenuElements.sectionProperties);
		boolean pageAppeared = switchToWindow(SectionPropertiesPageElements.SECTION_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		return pageAppeared;
	}

	public boolean openSendToVendorForCapture()
	{
		openContextMenu(RenditionContextMenuElements.viewSendToVendorForCapture);
		waitForPageLoaded();
		boolean windowAppeared = switchToWindow(SendToVendorPageElements.SEND_TO_VENDOR_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public void getRendition()
	{
		getElement("//div/span[contains(@id,'view') and text()='Rendition(s)']").click();
	}

	public boolean goToViewRenditionsCommonEditorNameEnabled()
	{
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean viewRenditionXML()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewRenditionXml);
		return sourceRawXmlEditorPage().switchToRawXmlEditorPage();
	}

	public boolean viewXMLExtract()
	{
		getElement(RenditionContextMenuElements.HEADER_IMAGE).click();
    	getElement(RenditionContextMenuElements.VIEW_XML_EXTRACT).click();
    	boolean viewXmlExtractEnabled = switchToWindow(RenditionContextMenuElements.XML_EXTRACT_TITLE);
    	enterTheInnerFrame();
    	getElement(CommonPageElements.CLOSE_BUTTON).click();
    	return viewXmlExtractEnabled;
	}

	public void viewDeltasAffectingSameTarget()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewDeltasAffectingSameTarget);
		waitForGridRefresh();
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE_DELTA_NAVIGATE);
	}

	public boolean TublarPrintPreview()
	{
		getElement(RenditionContextMenuElements.HEADER_IMAGE).click();
		getElement("//div/span[contains(@id,'view9Text')]").click();
    	boolean viewTabularPrintPreviewEnabled = switchToWindow("Tabular Print Preview");
    	enterTheInnerFrame();
    	getElement(CommonPageElements.OK_BUTTON).click();
		return viewTabularPrintPreviewEnabled;
	}

	public void editRendition()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.rendition);
	}

	public boolean viewRendition()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewRendition);
		boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
		editorPage().switchToEditor();
		return editorWindowAppeared;
	}

	public boolean viewPrintPreview()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewRenditionPrintPreview);
		boolean viewPrintPreviewEnabled = switchToWindow(PrintPreviewPageElements.PRINT_PREVIEW_PAGE_TITLE);
		enterTheInnerFrame();
		return viewPrintPreviewEnabled;
	}

	public boolean viewRenditionNotes()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewRenditionNotes);
		boolean switchedToRenditionNotes = switchToWindow(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
		enterTheInnerFrame();
		return switchedToRenditionNotes;
	}

	public boolean viewTabularPrintPreview()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewTabularPrintPreview);
		boolean viewPrintPreviewEnabled = switchToWindow(TabularPrintPreviewPageElements.TABULAR_PRINT_PREVIEW_PAGE_TITLE);
		enterTheInnerFrame();
		return viewPrintPreviewEnabled;
	}

	public boolean viewSourceFront()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewSourceFront);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean viewSourceEnd()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewSourceEnd);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public boolean viewTabularWLPreview()
	{
		openContextMenu(RenditionContextMenuElements.view, RenditionContextMenuElements.viewTabularWlPreview);
		boolean tabularWLPreviewAppeared = switchToWindow(TabularWLPreviewPageElements.TABULAR_WL_PREVIEW_TITLE);
		enterTheInnerFrame();
		return tabularWLPreviewAppeared;
	}

	public void openModifySubMenu()
	{
		openContextMenuSubMenu(RenditionContextMenuElements.modify);
	}

	public void openEditSubMenu()
	{
		openContextMenuSubMenu(RenditionContextMenuElements.edit);
	}

	public void openViewSubMenu()
	{
		openContextMenuSubMenu(RenditionContextMenuElements.view);
	}

	public String reportAddIndexReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportAddIndexReport);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		return AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
	}

	public String reportCompareAndMarkupReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportCompareAndMarkupReport);
		AutoITUtils.verifyAlertTextAndAccept(true, "One of the renditions you selected was created by something other than source input. Do you wish to continue?");
		return AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
	}

	public String reportCompareReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportCompareReport);
		AutoITUtils.verifyAlertTextAndAccept(true, "One of the renditions you selected was created by something other than source input. Do you wish to continue?");
		return AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
	}

	public boolean reportRepealIndexReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportRepealIndexReport);
		return indexReportSortOrderPage().switchToIndexReportSortOrderPage();
	}

	public String reportRepealIndexReportSingleSelect()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportRepealIndexReport);
		return AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true);
	}

	public boolean reportCombinedIndexReport()
	{
		openContextMenu(RenditionContextMenuElements.report, RenditionContextMenuElements.reportCombinedIndexReport);
		return indexReportSortOrderPage().switchToIndexReportSortOrderPage();
	}

	public boolean updateYearSession()
	{
		openContextMenu(RenditionContextMenuElements.edit, RenditionContextMenuElements.YearOrSession);
		waitForGridRefresh();
		waitForPageLoaded();
		boolean windowAppears = switchToWindow(UpdateYearOrSessionPageElements.UPDATE_YEAR_OR_SESSION_PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
		return windowAppears;
	}

	//-------------------------------------------------------//
	//-------------Tracking Context Menu Options-------------//
	//-------------------------------------------------------//

	public void trackAPVStarted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackAPVStarted);
		waitForGridRefresh();
	}

	public void trackTopicalHeadingNeeded()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackTopicalHeadingNeeded);
		waitForGridRefresh();
	}

	public void trackImagesSentOut()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.imagesSentOut);
		waitForGridRefresh();
	}

	public void trackTabularRequested()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackTabularRequested);
		waitForGridRefresh();
	}

	public void trackTabularStarted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackTabularStarted);
		waitForGridRefresh();
	}

	public void trackAPVReviewStarted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackAPVReviewStarted);
		waitForGridRefresh();
	}

	public void trackReadyForAdvancedSheet()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackReadyForAdvanceSheet);
		waitForGridRefresh();
	}

	public void trackAPVCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackAPVCompleted);
		waitForGridRefresh();
	}

	public void trackChapterApprovalReceived()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.trackChapterApprovalReceived);
		waitForGridRefresh();
	}

	public void trackTopicalHeadingCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.topicalHeadingCompleted);
		waitForGridRefresh();
	}

	public void trackImagesCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.imagesCompleted);
		waitForGridRefresh();
	}

	public void trackTabularReceivedHardcopy()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.tabularReceivedHardcopy);
		waitForGridRefresh();
	}

	public void trackTabularCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.tabularCompleted);
		waitForGridRefresh();
	}

	public void trackAPVReviewCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.apvReviewCompleted);
		waitForGridRefresh();
	}

	public void trackPublishedInAdvancedSheetDate()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.publishedInAdvancedSheet);
		waitForGridRefresh();
	}

	public void trackSyncToWestlawCompleted()
	{
		openContextMenu(RenditionContextMenuElements.track, RenditionContextMenuElements.syncToWestlawCompleted);
		waitForGridRefresh();
		waitForPageLoaded();
	}
}

