package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;


import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DeletePubtagPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DeletePubtagPageElements.singleRightArrowButton;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DeletePubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class DeletePubtagPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public DeletePubtagPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, DeletePubtagPageElements.class);
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
			selectDropdownOptionUsingJavascript("availableList", pubtag);
			clickSingleRightArrow();
		}
		clickOk();

		boolean addPubTagWindowDisappeared = checkWindowIsClosed(DeletePubtagPageElements.PAGE_TITLE);
		Assertions.assertTrue(addPubTagWindowDisappeared, "Add Pubtag window should disappear");
	}

	public void switchToDeletePubTagWindow()
	{
		switchToWindow(PAGE_TITLE);
	}
}
