package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.ViewMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationPropertiesPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionTabViewClamshellPage extends AbstractViewClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public SectionTabViewClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewMenuElements.class);
	}
	
	public boolean clickViewSections(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(ViewMenuElements.sideBarHeaderImage);
		click(ViewMenuElements.viewSections);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = switchToWindow(IntegrationPropertiesPageElements.PAGE_TITLE);
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
			waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}