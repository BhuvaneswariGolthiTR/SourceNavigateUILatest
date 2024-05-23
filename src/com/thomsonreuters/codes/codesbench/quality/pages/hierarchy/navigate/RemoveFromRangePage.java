package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RemoveFromRangePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RemoveFromRangePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RemoveFromRangePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RemoveFromRangePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(RemoveFromRangePageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(RemoveFromRangePageElements.okButton);
        waitForWindowGoneByTitle(RemoveFromRangePageElements.REMOVE_FROM_RANGE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }
}
