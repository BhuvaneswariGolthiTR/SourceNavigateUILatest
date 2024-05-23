package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RenditionTabViewClamshellPage extends AbstractViewClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public RenditionTabViewClamshellPage(WebDriver driver) 
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
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     	}
		else 
		{
			expectedWindowAppeared = editorPage().switchToEditorWindow();
			waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
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
			expectedWindowAppeared = viewBaselinesNavigatePage().switchToViewBaselinesPage();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRenditionPrintPreview(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.renditionPrintPreview);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     	}
		else 
		{
			expectedWindowAppeared = printPreviewPage().switchToPrintPreviewWindow();
		}

		sourcePage().closeDocumentWithIFrame(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickTabularPrintPreview(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.tabularPrintPreview);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     	}
		else
		{
			expectedWindowAppeared = printPreviewPage().switchToTabularPrintPreviewWindow();
		}

		sourcePage().closeDocumentWithIFrame(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickMultiplesAndDuplicates(Boolean expectedEnabled, Boolean closeWindow)
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
			waitForGridRefresh();
			waitForPageLoaded();
			expectedWindowAppeared = isElementDisplayed(SourceNavigateGridPageElements.MULTIPLES_DUPLICATES_MESSAGE);
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
