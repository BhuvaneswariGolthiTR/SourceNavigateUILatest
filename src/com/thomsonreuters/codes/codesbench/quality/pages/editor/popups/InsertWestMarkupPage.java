package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertWestMarkupPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public InsertWestMarkupPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, InsertWestMarkupPageElements.class);
    }

	public void selectMarkupToBeInserted(String option)
	{
		selectDropdownOptionUsingJavascript(InsertWestMarkupPageElements.MARKUP_TO_BE_INSERTED_SELECT_ID,option);
	}

	// This method only works with these excessive waits. If you are going to change it, please do multiple testing!
	public void clickSave()
	{
		editorPage().waitForElement(CommonPageElements.SAVE_BUTTON);
		WebElement markupSaveButton = returnExistingElement(CommonPageElements.SAVE_BUTTON);
		markupSaveButton.click();
	}
}
