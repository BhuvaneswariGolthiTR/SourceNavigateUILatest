package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaTabViewClamshellPage extends AbstractViewClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaTabViewClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewMenuElements.class);
	}

	public boolean clickDeltas(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.Deltas);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     
		}
		else 
		{
			expectedWindowAppeared =  editorPage().switchToEditorWindow();
			waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickNotes(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.notes);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else 
		{
			expectedWindowAppeared = instructionsNotesPage().switchToInstructionNotesWindow();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickViewTarget(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.viewTarget);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else 
		{
			expectedWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickViewTargetInHierarchy(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.viewTargetInHierarchy);
    	
		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");     
		}
		else 
		{
			waitForPageLoaded();
			expectedWindowAppeared = switchToWindow(HierarchyPageElements.PAGE_TITLE);	
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
