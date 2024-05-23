package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractViewClamshellPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractViewClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewMenuElements.class);
	}
	
	public boolean clickDeltasAffectingSameTarget(Boolean expectedEnabled)
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.deltasAffectingSameTarget);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			waitForPageLoaded();
			expectedWindowAppeared = switchToWindow(SourceNavigatePageElements.DELTA_NAVIGATE_AFFECTING_SAME_TARGET_PAGE_TITLE);
			waitForGridRefresh();
		}
		return expectedWindowAppeared;
	}

	public boolean clickMultipleAndDuplicates(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.multiplesAndDuplicates);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  waitForGridRefresh();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRenditionXml(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.renditionXml);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = sourceRawXmlEditorPage().switchToRawXmlEditorPage();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickXmlExtract(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.xmlExtract);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = xmlExtractSourcePage().switchToXmlExtractWindow();
		}	
		
		sourcePage().closeDocumentWithIFrame(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
