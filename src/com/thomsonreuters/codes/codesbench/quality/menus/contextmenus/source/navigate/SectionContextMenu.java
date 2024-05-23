package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.SectionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.Menu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.TaxTypeAddPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionContextMenu extends Menu
{

	/*
	 * Source -> Navigate Source -> Print Navigate Source -> BTS Source -> Reports
	 * -> Lock Report Source -> Shared Delta Report Source -> Novus Extracts ->
	 * Lexis Extract Source -> Novus Extracts -> Historical Extract
	 */

	private WebDriver driver;

	@Autowired
	public SectionContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SectionContextMenuElements.class);
	}
	
	public void goToSectionNavigation()
	{
		switchToWindow(SourceNavigatePageElements.SOURCE_NAVIGATE_SECTION_PAGE_TITLE);
	}
	
	public void getViewSections()
	{
		click(SectionContextMenuElements.viewSections);
		waitForPageLoaded();
	}

	public boolean viewSectionOld()
	{
		openContextMenu(SectionContextMenuElements.VIEW, SectionContextMenuElements.VIEW_SECTION);
		boolean result = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		return result;
	}

	public boolean viewSection()
	{
		openContextMenu(SectionContextMenuElements.VIEW, SectionContextMenuElements.VIEW_SECTION);
		boolean result = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		waitForElement(EditorToolbarPageElements.CLOSE_DOC);
		return result;
	}

	public boolean viewSectionNotes()
	{
		openContextMenu(SectionContextMenuElements.VIEW, SectionContextMenuElements.VIEW_SECTION_NOTES);
		boolean result = switchToWindow(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
		enterTheInnerFrame();
		return result;
	}
	
	public boolean editSection()
	{
		openContextMenu(RenditionContextMenuElements.EDIT, SectionContextMenuElements.EDIT_SECTION);
		boolean pageOpened = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForElement(EditorToolbarPageElements.CLOSE_DOC);
		return pageOpened;
	}

	public boolean editSections()
	{
		openContextMenu(RenditionContextMenuElements.EDIT, SectionContextMenuElements.EDIT_SECTIONS);
		boolean pageOpened = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForElement(EditorToolbarPageElements.CLOSE_DOC);
		return pageOpened;
	}
	
	public boolean openCheckValidations()
	{
		openContextMenu(SectionContextMenuElements.VALIDATE, SectionContextMenuElements.VALIDATE_CHECK_VALIDATIONS);
		boolean results = switchToWindow(ValidationReportPageElements.VALIDATION_REPORT_PAGE_TITLE);
		enterTheInnerFrame();
		return results;
	}

	public boolean editTaxTypeAdd()
	{
		openContextMenu(RenditionContextMenuElements.EDIT,RenditionContextMenuElements.TAX_TYPE_ADD);
		boolean result = switchToWindow(TaxTypeAddPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return result;
	}

	public boolean openSectionProperties()
	{
		openContextMenu(SectionContextMenuElements.sectionProperties);
		boolean pageOpened = switchToWindow(SectionPropertiesPageElements.SECTION_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean openSectionEditor()
	{
		openContextMenu(SectionContextMenuElements.EDIT,SectionContextMenuElements.EDIT_SECTION);
		boolean pageOpened = switchToWindow(EditorPageElements.PAGE_TITLE);
		waitForGridRefresh();
		return pageOpened;
	}

	public boolean openSectionNotesEditor()
	{
		openContextMenu(SectionContextMenuElements.EDIT,SectionContextMenuElements.EDIT_SECTION_NOTES);
		boolean pageOpened = switchToWindow(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean openSectionEffectiveDateEditor()
	{
		openContextMenu(SectionContextMenuElements.EDIT,SectionContextMenuElements.EDIT_EFFECTIVE_DATE);
		boolean pageOpened = switchToWindow(SectionEffectiveDatePageElements.SECTION_EFFECTIVE_DATE_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean openSectionIntegrationPropertiesEditor()
	{
		openContextMenu(SectionContextMenuElements.EDIT,SectionContextMenuElements.EDIT_INTEGRATION_PROPERTIES);
		boolean pageOpened = switchToWindow(IntegrationPropertiesPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean openDifficultyLevelEditor()
	{
		openContextMenu(SectionContextMenuElements.EDIT,SectionContextMenuElements.EDIT_DIFFICULTY_LEVEL);
		boolean pageOpened = switchToWindow(DifficultyLevelPageElements.DIFFICULTY_LEVEL_PAGE_TITTLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public boolean openAssignUser(){
		openContextMenu(SectionContextMenuElements.MODIFY,SectionContextMenuElements.MODIFY_ASSIGN_USER);
		boolean pageOpened = switchToWindow(AssignUserPageElements.ASSIGN_USER_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return pageOpened;
	}

	public void clickTrackImagesSentOut()
	{
		openContextMenu(SectionContextMenuElements.TRACK,SectionContextMenuElements.TRACK_IMAGES_SENT_OUT);
		waitForGridRefresh();
	}

	public void clickTrackImagesCompleted()
	{
		openContextMenu(SectionContextMenuElements.TRACK,SectionContextMenuElements.TRACK_IMAGES_COMPLETED);
		waitForGridRefresh();
	}

	public void clickTrackTabularRequested()
	{
		openContextMenu(SectionContextMenuElements.TRACK,SectionContextMenuElements.TRACK_TABULAR_REQUESTED);
		waitForGridRefresh();
	}

	public void clickTrackTabularStarted()
	{
		openContextMenu(SectionContextMenuElements.TRACK,SectionContextMenuElements.TRACK_TABULAR_STARTED);
		waitForGridRefresh();
	}

	public void clickTrackTabularCompleted()
	{
		openContextMenu(SectionContextMenuElements.TRACK,SectionContextMenuElements.TRACK_TABULAR_COMPLETED);
		waitForGridRefresh();
	}
}	
