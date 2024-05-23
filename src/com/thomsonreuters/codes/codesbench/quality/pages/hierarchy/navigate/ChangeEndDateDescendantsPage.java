package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ChangeEndDateDescendantsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChangeEndDateDescendantsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ChangeEndDateDescendantsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() { PageFactory.initElements(driver, ChangeEndDateDescendantsPageElements.class); }

    public void setEndEffectiveDate(String date)
    {
        sendKeysToElement(ChangeEndDateDescendantsPageElements.endEffectiveDateField, date);
    }

    public void clickQuickLoadSubmit()
    {
        click(ChangeEndDateDescendantsPageElements.quickLoadButton);
        waitForGridRefresh();
        waitForPageLoaded();
        click(ChangeEndDateDescendantsPageElements.submitButton);
        waitForWindowGoneByTitle(ChangeEndDateDescendantsPageElements.CHANGE_END_DATE_DESCENDANTS_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        //switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
    }
}