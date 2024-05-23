package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.AddToRangePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddToRangePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AddToRangePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddToRangePageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(AddToRangePageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(AddToRangePageElements.okButton);
        waitForWindowGoneByTitle(AddToRangePageElements.ADD_TO_RANGE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
