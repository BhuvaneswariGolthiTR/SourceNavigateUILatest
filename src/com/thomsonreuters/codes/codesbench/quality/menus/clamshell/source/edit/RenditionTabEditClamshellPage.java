package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.EditMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.RenditionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RenditionTabEditClamshellPage extends AbstractEditClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public RenditionTabEditClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditMenuElements.class);
	}

	public boolean clickProperties(Boolean expectedEnabled, Boolean toCloseWindow) 
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage);
		click(EditMenuElements.renditionAndLineageProperties);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");	
     	}
		else 
		{
			waitForGridRefresh();
			expectedWindowAppeared = switchToWindow(RenditionPropertiesPageElements.RENDITION_PROPERTIES_TITLE);
			enterTheInnerFrame();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, toCloseWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRendition(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage); 
		click(EditMenuElements.renditions);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = editorPage().switchToEditorWindow();
			waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
			editorPage().switchDirectlyToTextFrame();
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickEffectiveDate(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage);
		click(EditMenuElements.effectiveDate);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(RenditionPropertiesPageElements.RENDITION_PROPERTIES_TITLE);
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickDifficultyLevel(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage);
		click(EditMenuElements.difficultyLevel);

		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else
		{
			expectedWindowAppeared = difficultyLevelPage().switchToDifficultyLevelWindow();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
