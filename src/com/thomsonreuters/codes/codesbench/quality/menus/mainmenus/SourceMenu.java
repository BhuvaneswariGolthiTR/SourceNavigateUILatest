package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.SourceMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.BTSPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SharedDeltaReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourcePrintPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.novusextracts.LexisExtractOfLawsAndOrdersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.novusextracts.ProcessHistoricalExtract;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public SourceMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SourceMenuElements.class);
	}

	public boolean goToSourceSharedDeltaReport()
	{
		openMenu(SourceMenuElements.source);
		sendEnterToElement(SourceMenuElements.sharedDeltaReport);
		boolean sharedDeltaReportWindowOpened = switchToWindow(SharedDeltaReportPageElements.SHARED_DELTA_REPORT_PAGE_TITLE);
		enterTheInnerFrame();
		return sharedDeltaReportWindowOpened;
	}

	public boolean goToSourceC2012Navigate() 
	{
		openMenu(SourceMenuElements.source);
		sendEnterToElement(SourceMenuElements.navigate);
		boolean sourceNavigateAppeared = switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
		waitForGridRefresh();
		return sourceNavigateAppeared;
	}

	// Source -> C2012 Print Navigate
	public boolean goToSourceC2012PrintNavigate() 
	{
		openMenu(SourceMenuElements.source);
		sendEnterToElement(SourceMenuElements.printNavigate);
		return switchToWindow(SourcePrintPageElements.SOURCE_PRINT_PAGE_TITLE);
	}

	// Source -> C2012 BTS
	public boolean goToSourceC2012BTS() 
	{
		openMenu(SourceMenuElements.source);
		sendEnterToElement(SourceMenuElements.bts);
		boolean btsWindowAppeared = switchToWindow(BTSPageElements.BTS_PAGE_TITLE);
		waitForGridRefresh();
		return btsWindowAppeared;
	}

	// Source -> C2012 Reports -> Lock Report
	public boolean goToSourceC2012ReportsLockReport() 
	{
		openMenu(SourceMenuElements.source);
		openSubMenu(SourceMenuElements.reports);
		sendEnterToElement(SourceMenuElements.lockReport);
		return lockReportPage().switchToLockReportPage();
	}

	// Source -> C2012 All Deltas with Shared Target
	public boolean goToSourceC2012AllDeltasWithSharedTarget() 
	{
		openMenu(SourceMenuElements.source);
		sendEnterToElement(SourceMenuElements.sharedDeltaReport);
		return switchToWindow(SharedDeltaReportPageElements.SHARED_DELTA_REPORT_PAGE_TITLE);
	}

	// Source -> Novus Extracts -> Lexis Extract
	public boolean goToSourceNovusExtractsLexisExtract()
	{
		openMenu(SourceMenuElements.source);
		openSubMenu(SourceMenuElements.novusExtracts);
		sendEnterToElement(SourceMenuElements.novusLexisExtract);
		return switchToWindow(LexisExtractOfLawsAndOrdersPageElements.LEXIS_EXTRACT_OF_LAWS_AND_ORDERS_PAGE_TITLE);
	}

	// Source -> Novus Extracts -> Historical Extract
	public boolean goToSourceNovusExtractsHistoricalExtract()
	{
		openMenu(SourceMenuElements.source);
		openSubMenu(SourceMenuElements.novusExtracts);
		sendEnterToElement(SourceMenuElements.novusHistoricalExtract);
		return switchToWindow(ProcessHistoricalExtract.PROCESS_HISTORICAL_EXTRACT_PAGE_TITLE);
	}

	public void openMenu()
	{
		openMenu(SourceMenuElements.source);
	}
}
