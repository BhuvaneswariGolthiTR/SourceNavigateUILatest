package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ChangeSourceTagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ChangeSourceTagPage  extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ChangeSourceTagPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, ChangeSourceTagPageElements.class);
    }
	
	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
		boolean changeSourceTagWindowDisappeared = checkWindowIsClosed(ChangeSourceTagPageElements.CHANGE_SOURCETAG_PAGE_TITLE);
		Assertions.assertTrue(changeSourceTagWindowDisappeared, "Change SourceTag window should have closed.");
	}
	
	public void clickCancel()
	{
		click(ChangeSourceTagPageElements.cancelButton);
		boolean changeSourceTagWindowDisappeared = checkWindowIsClosed(ChangeSourceTagPageElements.CHANGE_SOURCETAG_PAGE_TITLE);
		Assertions.assertTrue(changeSourceTagWindowDisappeared, "Change SourceTag window should have closed.");
	}
	
	public void selectSourceTagDropdownOption(String optionToSelect)
	{
		selectDropdownOption(ChangeSourceTagPageElements.sourceTagDropdown, optionToSelect);

	}

	public void switchToChangeSourceTagWindow()
	{
		switchToWindow(ChangeSourceTagPageElements.CHANGE_SOURCETAG_PAGE_TITLE);
	}

}