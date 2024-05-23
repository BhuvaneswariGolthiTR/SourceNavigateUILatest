package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ReportsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationResultsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaTabReportsClamshellPage extends AbstractReportsClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaTabReportsClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ReportsMenuElements.class);
	}

	public boolean clickIntegrationWorkflow(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.integrationWorkflow);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickIntegrationResults(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ReportsMenuElements.sideBarHeaderImage);
		click(ReportsMenuElements.integrationResults);

		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else
		{
			expectedWindowAppeared =  integrationResultsPage().switchToIntegrationResultsWindow();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
