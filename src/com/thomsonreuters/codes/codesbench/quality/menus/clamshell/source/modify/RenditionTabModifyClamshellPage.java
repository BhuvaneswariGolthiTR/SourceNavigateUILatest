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
public class RenditionTabModifyClamshellPage extends AbstractModifyClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public RenditionTabModifyClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ModifyMenuElements.class);
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
			checkAlertTextMatchesGivenText("Mark selected rendition(s) as non-Duplicate?");
			expectedWindowAppeared = checkAlertTextMatchesGivenText("The selected rendition(s) were successfully marked as non-duplicate.");
			waitForPageLoaded();
			waitForGridRefresh();
			waitForPageLoaded();
			waitForGridRefresh();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
