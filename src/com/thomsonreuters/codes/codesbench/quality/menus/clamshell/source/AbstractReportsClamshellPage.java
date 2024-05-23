package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ReportsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.MismatchedReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.billGapCountSelectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.randomBillReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.reports.LockReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractReportsClamshellPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractReportsClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ReportsMenuElements.class);
	}
	
	public boolean clickIntegrationResults(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.integrationResults);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = integrationResultsPage().switchToIntegrationResultsWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrationSummary(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.integrationSummary);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = reportPage().switchToReportPage();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickLockReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.lockReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  switchToWindow(LockReportPageElements.LOCK_REPORT_PAGE_TITLE);
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickPrepReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.prepReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  reportPage().switchToReportPage();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickUnusedReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.unusedReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = reportPage().switchToReportPage();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAudits(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.audits);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  auditBySourcePage().switchToAuditBySourceWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRandomBillReports(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.randomBillReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  switchToWindow(randomBillReportsPageElements.RANDOM_BILLS_REPORTS_PAGE_TITLE);
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickStageCheckReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.stageCheckReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  stageCheckReportsPage().switchToStageCheckReportWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickMismatchedReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.mismatchedReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  switchToWindow(MismatchedReportPageElements.MISMATCHED_REPORTS_PAGE_TITLE);
			waitForPageLoaded();
			waitForGridRefresh();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickBillGapCountReport(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.billGapCountReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  switchToWindow(billGapCountSelectionPageElements.BILL_GAP_COUNT_REPORT_PAGE_TITLE);
			waitForGridRefresh();
			waitForPageLoaded();
			waitForGridRefresh();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIndexReport(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.indexReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = indexReportPage().switchToIndexReportWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public String clickAddIndexReport(Boolean expectedEnabled)
	{
		String workflowID = "";
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.addIndexReport);
    	
		if(expectedEnabled == false)
		{
			AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			workflowID = AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
		}
		return workflowID;
	}
	
	public boolean clickRepealIndexReport(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.repealIndexReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else
		{
			expectedWindowAppeared = indexReportSortOrderPage().switchToIndexReportSortOrderPage();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public String clickRepealIndexReportSingleSelect(Boolean expectedEnabled, Boolean closeWindow)
	{
		String workflowID = "";
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.repealIndexReport);

		if(expectedEnabled == false)
		{
			Assertions.assertTrue(AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function."), "Not supported alert did not appear");
		}
		else
		{
			workflowID = AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true);
		}

		return workflowID;
	}
	
	public boolean clickCombinedIndexReport(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.combinedIndexReport);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else
		{
			expectedWindowAppeared = indexReportSortOrderPage().switchToIndexReportSortOrderPage();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
