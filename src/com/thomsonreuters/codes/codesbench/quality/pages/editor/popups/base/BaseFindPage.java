package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.base;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.base.BaseFindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BaseFindPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public BaseFindPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, BaseFindPageElements.class);
    }

	public void setFindTerm(String term)
	{
		BaseFindPageElements.findInputField.click();
		clearAndSendTextToTextbox(BaseFindPageElements.findInputField,term );
	}
	public void setFindTerm(String term, boolean caseSensitive)
	{
		setFindTerm(term);
		if (caseSensitive) {
			checkCaseSensitiveCheckbox();
		} else {
			uncheckCaseSensitiveCheckbox();
		}
	}

	public void checkCaseSensitiveCheckbox()
	{
		if(!BaseFindPageElements.caseSensitiveCheckBox.isSelected())
		{
			BaseFindPageElements.caseSensitiveCheckBox.sendKeys(Keys.SPACE);
		}
	}
	
	public void uncheckCaseSensitiveCheckbox()
	{
		if(BaseFindPageElements.caseSensitiveCheckBox.isSelected())
		{
			BaseFindPageElements.caseSensitiveCheckBox.sendKeys(Keys.SPACE);
		}
	}
	
	public void clickFindButton()
	{
		sendEnterToElement(BaseFindPageElements.findButton);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
	}

	public void clickAddMarkupButton()
	{
		sendEnterToElement(BaseFindPageElements.addMarkupButton);
		switchToWindow(InsertWestMarkupPageElements.WINDOW_TITLE);
		enterTheInnerFrame();
	}
}
