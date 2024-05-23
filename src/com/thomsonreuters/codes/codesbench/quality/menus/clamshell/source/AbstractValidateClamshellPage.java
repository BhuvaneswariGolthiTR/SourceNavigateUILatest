package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ValidateMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractValidateClamshellPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractValidateClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ValidateMenuElements.class);
	}

	public boolean clickRunValidations(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ValidateMenuElements.sideBarHeaderImage);
		click(ValidateMenuElements.runValidations);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public String clickValidateAndUpdateLinks(Boolean expectedEnabled, Boolean closeWindow)
	{
		String workflowID = "";
		click(ValidateMenuElements.sideBarHeaderImage);
		click(ValidateMenuElements.validateAndUpdateLinks);
    	
		if(expectedEnabled == false)
		{
			AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			AutoITUtils.verifyAlertTextAndAccept(true, "Validate and Update Links on the selected renditions?");
			workflowID = AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
			waitForGridRefresh();
		}
		return workflowID;
	}
	
	public boolean clickViewValidations(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ValidateMenuElements.sideBarHeaderImage);
		click(ValidateMenuElements.viewValidations);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  validationReportPage().switchToValidationReportWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}