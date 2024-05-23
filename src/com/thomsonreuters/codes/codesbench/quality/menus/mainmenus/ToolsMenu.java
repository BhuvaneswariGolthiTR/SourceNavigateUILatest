package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ToolsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.RedliningComparePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.codesauthority.CodesAuthoritySearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.effectivedatemetricsreport.EffectiveDateMetricsReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.keyciteextract.KeyCiteResearchReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.old.ScriptMaintenanceElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.StateFeedBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.undotypeselection.UndoTypeSelectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ToolsMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public ToolsMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ToolsMenuElements.class);
	}

    //Tools -> Search and Replace
    public boolean goToSearchAndReplace()  
    {
    	openMenu(ToolsMenuElements.toolsMenu);
    	sendEnterToElement(ToolsMenuElements.toolsMenuSearchAndReplace);
    	return switchToWindow(SearchAndReplacePageElements.SAR_PAGE_TITLE_UPPERCASE);
    }

    //Tools -> Stock Note Manager
    public boolean goToStockNoteManager()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
    	sendEnterToElement(ToolsMenuElements.stockNoteManager);
    	boolean windowFound = switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
    	maximizeCurrentWindow();
    	waitForPageLoaded();
    	return windowFound;
    }

    //Tools -> Workflow Reporting System
	public boolean goToWorkflowReportingSystem()
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.workflowReportingSystem);
    	boolean workflowReportingWindowAppeared = switchToWindow(WorkflowSearchPageElements.WORKFLOW_REPORTING_SYSTEM_PAGE_TITLE);
    	maximizeCurrentWindow();
    	waitForPageLoaded();
    	return workflowReportingWindowAppeared;
    }

    //Tools -> Codes Authority -> Codes Authority Search Grid
    public boolean goToCodesAuthoritySearchGrid()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		openSubMenu(ToolsMenuElements.codesAuthority);
		sendEnterToElement(ToolsMenuElements.codesAuthoritySearchGrid);
    	return switchToWindow(CodesAuthoritySearchPageElements.CODES_AUTHORITY_SEARCH_PAGE_TITLE);
    }

    //Tools -> Script Maintenance
    public boolean goToScriptMaintenance()
    {
        openMenu(ToolsMenuElements.toolsMenu);
        sendEnterToElement(ToolsMenuElements.scriptMaintenance);
        boolean windowOpened = switchToWindow(ScriptMaintenanceElements.SCRIPT_MAINTENANCE_PAGE_TITLE);
        maximizeCurrentWindow();
        waitForPageLoaded();
        waitForElementGone(ScriptMaintenanceElements.PROGRESS_BAR);
        return windowOpened;
    }

    //Tools -> Online Product Maintenance
    public boolean goToOnlineProductMaintenance()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.onlineProductMaintenance);
    	return switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
    }

    //Tools -> Undo
    public boolean goToUndo()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.undo);
    	return switchToWindow(UndoTypeSelectionPageElements.UNDO_TYPE_SELECTION_PAGE_TITLE);
    }

    //Tools -> Redlining Compare
    public boolean goToRedliningCompare()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.redliningCompare);
		boolean didRedliningCompareWindowAppear = switchToWindow(RedliningComparePageElements.REDLINING_COMPARE_PAGE_TITLE);
		enterTheInnerFrameById("dialogContentId");
		return didRedliningCompareWindowAppear;
    }

	//Tools -> OC Extract
	public boolean goToOCExtract()
	{
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.ocExtract);
		boolean windowOpened = switchToWindow(OCExtractBasePageElements.PAGE_TITLE);
		maximizeCurrentWindow();
		waitForPageLoaded();
		publicationsTablePage().waitForHiddenOverlay();
		return windowOpened;
	}

    //Tools -> KeyCite Extract
    public boolean goToKeyCiteExtract()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.keyCiteExtract);
		if(waitForWindowByUrl(KeyCiteResearchReferencesPageElements.KEYCITE_RESEARCH_REFERENCES_PAGE_URL))
		{
			maximizeCurrentWindow();
			keyCiteResearchReferencesPage().waitForPageLoaded();
			return true;
		}
		else
		{
			return false;
		}
    }

    //Tools -> Spellcheck Manager
    public boolean goToSpellcheckManager()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.spellcheckManager);
    	return switchToWindow(SpellcheckManagerPageElements.SPELLCHECK_MANAGER_PAGE_TITLE);
    }

    //Tools -> Effective Dates Metric Report
    public boolean goToEffectiveDateMetricsReport()  
    {
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.toolsMenuEffectiveDateMetricsReport);
    	switchToModalWindow();
    	return doesElementExist(EffectiveDateMetricsReportPageElements.EFFECTIVE_DATE_METRICS_REPORT_PAGE_TITLE);
    }

    public boolean goToQueryNoteReport()
	{
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.queryNoteReport);
		boolean windowOpened = switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		maximizeCurrentWindow();
		waitForGridRefresh();
		return windowOpened;
	}

	public boolean goToQueryNoteReportAngular()
	{
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.queryNoteReportAngular);
		boolean windowOpened = switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForElementGone(QueryNoteReportPageElements.GRID_LOADING);
		return windowOpened;
	}

	public boolean goToStateFeed()
	{
		openMenu(ToolsMenuElements.toolsMenu);
		sendEnterToElement(ToolsMenuElements.stateFeeds);
		boolean windowOpened = switchToWindow(StateFeedBasePageElements.PAGE_TITLE);
		maximizeCurrentWindow();
		waitForPageLoaded();
		extractedDataPage().waitForHiddenOverlay();
		fileManagementPage().waitForHiddenOverlay();
		return windowOpened;
	}

	public void openMenu()
	{
		sendKeyToElement(ToolsMenuElements.toolsMenu, Keys.ARROW_DOWN);
	}

	public boolean isOCExtractDisplayedAfterStateFeeds()
	{
		openMenu();
		List<String> expectedOCExtractAndStateFeedsOptionsOrder = Arrays.asList("State Feeds", "OC Extract");
		List<String> actualOCExtractAndStateFeedsOptionsOrder = new ArrayList<>();
		getElements(ToolsMenuElements.TOOLS_MENU_OPTIONS_XPATH).stream().forEach(option -> actualOCExtractAndStateFeedsOptionsOrder.add(option.getText()));
		Integer listLength = actualOCExtractAndStateFeedsOptionsOrder.size();
		return isElementDisplayed(ToolsMenuElements.TOOLS_MENU_OC_EXTRACT) &&
				actualOCExtractAndStateFeedsOptionsOrder.subList(listLength-2, listLength).equals(expectedOCExtractAndStateFeedsOptionsOrder);
	}
}
