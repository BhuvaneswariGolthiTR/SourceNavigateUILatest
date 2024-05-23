package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ModifyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.DeleteRenditionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SpiltRenditionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractModifyClamshellPage  extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractModifyClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ModifyMenuElements.class);  
	}

	public boolean clickSplitRenditionBySection(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.splitRenditionBySection);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(SpiltRenditionsPageElements.SPLIT_RENDITIONS_TITLE);			
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickMarkAsNonDuplicate(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.markAsNonDuplicate);
    	
		if(expectedEnabled == false)
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
	
	public boolean clickOmitRendition(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.omitRendition);
    	
		if(expectedEnabled == false)
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
	
	public boolean clickVetoRendition(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.vetoRendition);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickDeleteRendition(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.deleteRendition);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(DeleteRenditionPageElements.DELETE_CONFIRMATION_PAGE_TITLE);	
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickUndeleteRendition(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.undeleteRendition);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "This action will undelete the selected Rendition(s) and initate a public to Westlaw. Continue?");
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrate(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.integrate);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(DeleteRenditionPageElements.DELETE_CONFIRMATION_PAGE_TITLE);
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAssignUser(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ModifyMenuElements.sideBarHeaderImage);
		click(ModifyMenuElements.assignUser);
    	
		if(expectedEnabled == false)
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
}
