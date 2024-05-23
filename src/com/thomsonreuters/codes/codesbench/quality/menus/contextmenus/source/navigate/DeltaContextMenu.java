package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.DeltaContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.Menu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.InsertIntoHierarchyWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.TaxTypeAddPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaContextMenu extends Menu
{
    private WebDriver driver;

    @Autowired
    public DeltaContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DeltaContextMenuElements.class);
	}

	public void getSideBarViewDelta()
	{
		click(DeltaContextMenuElements.delta);
		editorPage().switchToEditor();
	}
	
	public boolean displayAllResultsandClearFilters()
	{
		return isElementDisplayed("//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']");
	}

	public boolean goToViewTargetInHierarchy()
	{
		openContextMenu(DeltaContextMenuElements.view, DeltaContextMenuElements.viewTargetInHierarchy);
		return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public void goToViewTargetContent()
	{
		openContextMenu(DeltaContextMenuElements.view, DeltaContextMenuElements.viewTargetContent);
	}

	public void goToEditTargetContent()
	{
		openContextMenu(DeltaContextMenuElements.edit, DeltaContextMenuElements.editTargetContent);
	}

	public void goToModifyResetIntegrationStatus()
	{
		openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.modifyResetIntegrationStatus);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToModifyAssignUser()
	{
		openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.modifyAssignUser);
		assignUserPage().switchToAssignedUserPage();
	}

	public void goToModifyIntegrate()
	{
		openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.modifyIntegrate);
		waitForGridRefresh();
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToEditIntegrationProperties()
	{
		openContextMenu(DeltaContextMenuElements.edit, DeltaContextMenuElements.editIntegrationProperties);
		integrationPropertiesPage().switchToIntegrationPropertiesPage();
	}

	public void goToDeltaProperties()
	{
		openContextMenu(DeltaContextMenuElements.deltaProperties);
		deltaPropertiesPage().switchToDeltaPropertiesWindow();
	}

	public void goToEditDeltaNotes()
	{
		openContextMenu(DeltaContextMenuElements.edit, DeltaContextMenuElements.editDeltaNotes);
		instructionsNotesPage().switchToInstructionNotesWindow();
	}

	public void goToEditEffectiveDate()
	{
		openContextMenu(DeltaContextMenuElements.edit, DeltaContextMenuElements.editEffectiveDate);
		effectiveDatePage().switchToEffectiveDatePage();
	}

	public void goToEditDifficultyLevel()
	{
		openContextMenu(DeltaContextMenuElements.edit, DeltaContextMenuElements.editDifficultyLevel);
		difficultyLevelPage().switchToDifficultyLevelWindow();
	}

	public void goToModifyCiteLocate()
	{
		openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.modifyCiteLocate);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToModifyInsertIntoHierarchyWizard()
	{
		openContextMenu(DeltaContextMenuElements.modify, DeltaContextMenuElements.insertIntoHierarchyWizard);
		waitForPageLoaded();
		switchToWindow(InsertIntoHierarchyWizardPageElements.INSERT_INTO_HIERARCHY_WIZARD_TITLE);
	}

	public void editDelta()
	{
		openContextMenu(RenditionContextMenuElements.EDIT, DeltaContextMenuElements.DELTA);
	}

	public void viewDelta()
	{
		openContextMenu(RenditionContextMenuElements.VIEW, DeltaContextMenuElements.VIEW_DELTA);
	}

	public boolean editTaxTypeAdd()
	{
		openContextMenu(RenditionContextMenuElements.EDIT,RenditionContextMenuElements.TAX_TYPE_ADD);
		boolean result = switchToWindow(TaxTypeAddPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return result;
	}

	public boolean goToReportIntegrationResults()
	{
		openContextMenu(DeltaContextMenuElements.report, DeltaContextMenuElements.reportIntegrationResults);

		return integrationResultsPage().switchToIntegrationResultsWindow();
	}

	public void goToTrackImagesSentOut()
	{
		openContextMenu(DeltaContextMenuElements.track, DeltaContextMenuElements.trackImagesSentOut);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToTrackImagesCompleted()
	{
		openContextMenu(DeltaContextMenuElements.track, DeltaContextMenuElements.trackImagesCompleted);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToTrackTabularRequested()
	{
		openContextMenu(DeltaContextMenuElements.track, DeltaContextMenuElements.trackTabularRequested);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToTrackTabularStarted()
	{
		openContextMenu(DeltaContextMenuElements.track, DeltaContextMenuElements.trackTabularStarted);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToTrackTabularCompleted()
	{
		openContextMenu(DeltaContextMenuElements.track, DeltaContextMenuElements.trackTabularCompleted);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void goToViewDeltaOld()
	{
		openContextMenu(DeltaContextMenuElements.view, DeltaContextMenuElements.viewDelta);
		editorPage().switchToEditor();
		waitForPageLoaded();
	}

	public boolean goToViewDeltaNotes()
	{
		openContextMenu(DeltaContextMenuElements.view, DeltaContextMenuElements.viewDeltaNotes);
		waitForPageLoaded();
		return instructionsNotesPage().switchToInstructionNotesWindow();
	}
	public void openViewSubMenu()
	{
		openSubMenu(DeltaContextMenuElements.view);
	}
}
