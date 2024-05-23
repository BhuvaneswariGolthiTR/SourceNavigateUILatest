package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ReservePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReservePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ReservePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ReservePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(ReservePageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(ReservePageElements.okButton);
        waitForWindowGoneByTitle(ReservePageElements.RESERVE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void setCurrentDateAsEffectiveStartDate()
    {
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendKeysToElement(ReservePageElements.effectiveStartDateTextBox,date);
    }
}