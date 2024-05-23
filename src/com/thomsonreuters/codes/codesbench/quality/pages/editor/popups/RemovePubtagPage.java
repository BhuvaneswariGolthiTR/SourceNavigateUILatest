package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.RemovePubtagElements;

@Component
public class RemovePubtagPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public RemovePubtagPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, RemovePubtagElements.class);
    }
	

	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
	
	public void clickSingleRightArrow()
	{
		click(RemovePubtagElements.singleRightArrowButton);
	}
	
	public void selectAvailablePubtags(String... pubtags) 
	{
		waitForPageLoaded();
		waitForElementToBeClickable(RemovePubtagElements.AVAILABLE_PUBTAG_LIST_XPATH);
		for (String pubtag : pubtags) 
		{
			selectDropdownOptionUsingJavascript("availablelist", pubtag);
			clickSingleRightArrow();
		}
		clickOk();

		boolean addPubTagWindowDisappeared = checkWindowIsClosed(RemovePubtagElements.PAGE_TITLE);
		Assertions.assertTrue(addPubTagWindowDisappeared, "Add Pubtag window should disappear");
	}

	public void switchToRemovePubTagWindow()
	{
		switchToWindow(RemovePubtagElements.PAGE_TITLE);
	}
}
