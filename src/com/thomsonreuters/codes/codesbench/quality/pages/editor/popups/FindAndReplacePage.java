package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.FindAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.base.BaseFindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.base.BaseFindPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class FindAndReplacePage extends BaseFindPage
{
	private WebDriver driver;
	
	@Autowired
	public FindAndReplacePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, FindAndReplacePageElements.class);
    }

	public void setReplaceTerm(String term)
	{
		FindAndReplacePageElements.replaceInputField.click();
		//FindAndReplacePageElements.replaceInputField.clear();
		BaseFindPageElements.replaceInputField.sendKeys(Keys.CONTROL, Keys.chord("a"));
		BaseFindPageElements.replaceInputField.sendKeys(Keys.BACK_SPACE);
		FindAndReplacePageElements.replaceInputField.sendKeys(term);
	}

	public void clickReplaceButton()
	{
		sendEnterToElement(FindAndReplacePageElements.replaceButton);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
	}
	
	public void clickReplaceAllButton()
	{
		sendEnterToElement(FindAndReplacePageElements.replaceAllButton);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
	}

	public void close()
	{
		switchToWindow(FindAndReplacePageElements.PAGE_TITLE);
		sendEnterToElement(FindAndReplacePageElements.closeButton);
	}

	public void switchToFindAndReplaceDialog()
	{
		switchToWindow(FindAndReplacePageElements.PAGE_TITLE);
	}
}
