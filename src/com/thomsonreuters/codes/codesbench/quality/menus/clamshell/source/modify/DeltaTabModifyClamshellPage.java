package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ModifyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractModifyClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaTabModifyClamshellPage extends AbstractModifyClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaTabModifyClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ModifyMenuElements.class);
	}

	public boolean clickRunCiteLocate(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.modifyCiteLocate);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickAssignUser(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.assignUser);

		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else
		{
			expectedWindowAppeared = assignUserPage().switchToAssignedUserPage();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickInsertIntoHierarchyWizard(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.insertIntoHierarchyWizard);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickResetIntegrationStatus(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.resetIntegrationStatus);
    	
		if(expectedEnabled==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickResolveCiteLocate(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.resolveCiteLocate);
    	
		if(expectedEnabled==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
