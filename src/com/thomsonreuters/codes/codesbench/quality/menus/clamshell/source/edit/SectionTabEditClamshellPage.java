package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.EditMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.EffectiveDatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SectionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionTabEditClamshellPage extends AbstractEditClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public SectionTabEditClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditMenuElements.class);
	}
	
	public boolean clickSections(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage);
		click(EditMenuElements.sections);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
			waitForGridRefresh();
		}
		
		sourcePage().closeEditorPage(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickProperties(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(EditMenuElements.sideBarHeaderImage);
		click(EditMenuElements.sectionProperties);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else 
		{
			expectedWindowAppeared = switchToWindow(SectionPropertiesPageElements.SECTION_PROPERTIES_PAGE_TITLE);	
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
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
			expectedWindowAppeared = switchToWindow(EffectiveDatePageElements.SECTION_EFFECTIVE_DATE_PAGE_TITLE);
			enterTheInnerFrame();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}