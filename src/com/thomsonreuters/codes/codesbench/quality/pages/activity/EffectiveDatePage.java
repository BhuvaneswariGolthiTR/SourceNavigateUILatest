package com.thomsonreuters.codes.codesbench.quality.pages.activity;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.EffectiveDatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EffectiveDatePage extends BasePage
{
	private WebDriver driver;

    @Autowired
    public EffectiveDatePage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EffectiveDatePageElements.class);
    }
    
    public void setEffectiveDate(String date)
    {
    	clearAndSendKeysToElement(EffectiveDatePageElements.effectiveDate, date);
    }

    public void setCurrentDateInEffectiveDateCalender()
    {
        enterTheInnerFrame();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendTextToTextbox(EffectiveDatePageElements.effectiveDate, currentDate);
    }

    public void clickSave()
    {
        sendEnterToElement(EffectiveDatePageElements.saveButton);
    	waitForWindowGoneByTitle(EffectiveDatePageElements.EFFECTIVE_DATE_PAGE_TITLE);
    }

    public void clickCancel()
    {
        click(EffectiveDatePageElements.cancelButton);
        waitForWindowGoneByTitle(EffectiveDatePageElements.EFFECTIVE_DATE_PAGE_TITLE);
    }

    public String getEffectiveDate()
    {
        return getElementsText(EffectiveDatePageElements.INPUT_EFFECTIVE_DATE);
    }

    public void checkRunCiteLocate()
    {
        checkCheckbox(EffectiveDatePageElements.runCiteLocateCheckbox);
    }

    public boolean switchToEffectiveDatePage()
    {
        boolean windowAppears = switchToWindow(EffectiveDatePageElements.EFFECTIVE_DATE_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }
}
