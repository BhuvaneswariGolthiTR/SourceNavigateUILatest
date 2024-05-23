package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.CourtsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.ListOfCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.BrowseReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.DetailReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.NoTeamReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.SummaryReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.tools.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerPageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NodMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public NodMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
	    PageFactory.initElements(driver, NodMenuElements.class);
	}

    //NOD -> Subscribed Cases
    public boolean goToSubscribedCases()
    {
    	openMenu(NodMenuElements.nod);
		sendEnterToElement(NodMenuElements.subscribedCases);
		boolean subscribedCasesWindowAppeared = switchToWindow(SubscribedCasesPageElementsAngular.PAGE_TITLE);
		maximizeCurrentWindow();
        return subscribedCasesWindowAppeared;
    }

	//NOD -> Cases
	public boolean goToCases()
	{
		openMenu(NodMenuElements.nod);
		sendEnterToElement(NodMenuElements.cases);
		boolean listOfCasesWindowAppeared = switchToWindow(SubscribedCasesPageElementsAngular.PAGE_TITLE);
		waitForGridRefresh();
		return listOfCasesWindowAppeared;
	}

	//NOD - Open Canadian NOD
	public boolean goToCanadianNOD()
	{
		click(NodMenuElements.nod);
		boolean listOfCasesWindowAppeared = switchToWindow(SubscribedCasesPageElementsAngular.PAGE_TITLE);
		maximizeCurrentWindow();
		waitForGridRefresh();
		return listOfCasesWindowAppeared;
	}


    //NOD -> Administrative Opinions
    public boolean goToAdministrativeOpinions()
    {
    	openMenu(NodMenuElements.nod);
		sendEnterToElement(NodMenuElements.administrativeOpinions);
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE);
    }

    //NOD -> Courts
    public boolean goToCourts()
    {
    	openMenu(NodMenuElements.nod);
		sendEnterToElement(NodMenuElements.courts);
		return switchToWindow(CourtsPageElements.COURTS_PAGE_TITLE);
    }

    //NOD -> Reports -> Browse Reports
    public boolean goToBrowseReports()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.reports);
    	sendEnterToElement(NodMenuElements.browseReports);
		return switchToWindow(BrowseReportsPageElements.PAGE_TITLE);
    }

    //NOD -> Reports -> No Team
    public boolean goToReportsNoTeam()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.reports);
    	sendEnterToElement(NodMenuElements.noTeam);
    	return switchToWindow(NoTeamReportPageElements.NO_TEAM_REPORT_PAGE_TITLE);
    }

    //NOD -> Reports -> Summary
    public boolean goToReportsSummary()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.reports);
    	sendEnterToElement(NodMenuElements.summary);
    	return switchToWindow(SummaryReportPageElements.SUMMARY_REPORT_PAGE_TITLE);
    }

    //NOD -> Reports -> Detail
    public boolean goToReportsDetail()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.reports);
    	sendEnterToElement(NodMenuElements.detail);
    	return switchToWindow(DetailReportPageElements.DETAIL_REPORT_PAGE_TITLE);
    }

    //NOD -> Tools -> Initiate NOD Batch Merge
    public boolean goToToolsInitiateNodBatchMerge()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateNODBatchMerge);
    	return switchToWindow(InitiateNODBatchMergePageElements.INITIATE_NOD_BATCH_MERGE_PAGE_TITLE);
    }

    //NOD -> Tools -> Initiate NOD Unmerge Report
    public boolean goToToolsInitiateNodUnmergedReport()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateNODUnmergedReport);
    	switchToWindow(InitiateNODUnmergedReportPageElements.INITIATE_NOD_UNMERGED_REPORT_PAGE_TITLE);
		return waitForElementExists(InitiateNODUnmergedReportPageElements.INITIATE_NOD_UNMERGED_REPORT_HEADER);
    }

    //NOD -> Tools -> Initiate NOD Update
    public boolean goToUpdate()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateNODUpdate);
    	return switchToWindow(InitiateNODUpdatePageElements.INITIATE_NOD_UPDATE_PAGE_TITLE);
    }

    //NOD -> Tools -> Import Court/Team Routing
    public boolean goToToolsImportCourtTeamRouting()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.importCourtTeamRouting);
    	switchToWindow(ImportCourtTeamRoutingPageElements.IMPORT_COURT_TEAM_ROUTING_PAGE_TITLE);
		return waitForElementExists(ImportCourtTeamRoutingPageElements.IMPORT_COURT_TEAM_ROUTING_HEADER);
    }

    //NOD -> Tools -> Initiate NOD Data Validation
    public boolean goToToolsInitiateNodDataValidation()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateNODDataValidation);
    	switchToWindow(InitiateNODDataValidationPageElements.INITIATE_NOD_DATA_VALIDATION_PAGE_TITLE);
		return waitForElementExists(InitiateNODDataValidationPageElements.INITIATE_NOD_DATA_VALIDATION_HEADER);
    }

    //NOD -> Tools -> Initiate XUSSC Update
    public boolean goToToolsInitiateXusscUpdate()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateXUSSCUpdate);
    	switchToWindow(InitiateXUSCCUpdatePageElements.INITIATE_NOD_XUSSC_UPDATE_PAGE_TITLE);
		return waitForElementExists(InitiateXUSCCUpdatePageElements.INITIATE_NOD_XUSSC_UPDATE_HEADER);
    }

    //NOD -> Tools -> Initiate NOD Batch Reorder
    public boolean goToToolsInitiateNodBatchReorder()
    {
    	openMenu(NodMenuElements.nod);
    	openSubMenu(NodMenuElements.tools);
    	sendEnterToElement(NodMenuElements.initiateNODBatchReorder);
    	switchToWindow(InitiateNODBatchReorderPageElements.INITIATE_NOD_BATCH_REORDER_PAGE_TITLE);
		return waitForElementExists(InitiateNODBatchReorderPageElements.INITIATE_NOD_BATCH_REORDER_HEADER);
    }

    // NOD -> Tools -> Inititate needed report
	public boolean goToToolsInitiateGivenReport(String reportType, String reportTypeWindow)
	{
		openMenu(NodMenuElements.nod);
		openSubMenu(NodMenuElements.tools);
		sendEnterToElement(String.format(NodMenuElements.GENERIC_INITIATE_REPORT_XPATH, reportType));
		boolean windowAppeared = switchToWindow(reportTypeWindow);
		enterTheInnerFrame();
		return windowAppeared;
	}

    //NOD -> Blueline Spellcheck Dictionary
    public boolean goToBluelineSpellcheckDictionary()
    {
    	openMenu(NodMenuElements.nod);
    	sendEnterToElement(NodMenuElements.bluelineSpellcheckDictionary);
    	return switchToWindow(SpellcheckManagerPageElements.SPELLCHECK_MANAGER_PAGE_TITLE);
    }

	public void openMenu()
	{
		sendKeyToElement(NodMenuElements.nod, Keys.ARROW_DOWN);
	}
}
