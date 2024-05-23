package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ViewBaselinesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LineageTabViewClamshellPage  extends AbstractViewClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public LineageTabViewClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewMenuElements.class);
	}
	
	public boolean clickRenditions(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.renditions);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     	
		}
		else 
		{
			expectedWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRenditionPrintPreview(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.renditionPrintPreview);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = printPreviewPage().switchToPrintPreviewWindow();
		}

		sourcePage().closeDocumentWithIFrame(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickBaselines(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.baselines);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");	
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(ViewBaselinesPageElements.PAGE_TITLE);
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	
	public boolean clickTabularPrintPreview(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.tabularPrintPreview);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     	
		}
		else 
		{
			expectedWindowAppeared = printPreviewPage().switchToTabularPrintPreviewWindow();
		}

		sourcePage().closeDocumentWithIFrame(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickMultipleAndDuplicate(Boolean expectedEnabled, Boolean closeWindow) 
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
			waitForPageLoaded();
			expectedWindowAppeared = isElementDisplayed("//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']");		
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
