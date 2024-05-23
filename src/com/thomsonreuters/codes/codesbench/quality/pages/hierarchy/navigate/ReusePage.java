package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ReusePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReusePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ReusePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ReusePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(ReusePageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(ReusePageElements.okButton);
        waitForWindowGoneByTitle(ReusePageElements.REUSE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void setCurrentDateAsEffectiveStartDate()
    {
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendKeysToElement(ReusePageElements.effectiveStartDateTextBox,date);
    }
}