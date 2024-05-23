package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements.singleRightArrowButton;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class AddPubtagPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public AddPubtagPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, AddPubtagPageElements.class);
    }

	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
	
	public void clickSingleRightArrow()
	{
		click(singleRightArrowButton);
	}
	
	public void selectAvailablePubtags(String... pubtags)  
	{
		waitForPageLoaded();
		waitForElementToBeClickable(AVAILABLE_LIST);
		
		for (String pubtag : pubtags) 
		{
			selectDropdownOptionUsingJavascript("availablelist", pubtag);
			clickSingleRightArrow();
		}
		clickOk();

		boolean addPubTagWindowDisappeared = checkWindowIsClosed(AddPubtagPageElements.PAGE_TITLE);
		Assertions.assertTrue(addPubTagWindowDisappeared, "Add Pubtag window should disappear");

	}

	public void switchToAddPubTagWindow()
	{
		switchToWindow(PAGE_TITLE);
	}
}
