package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DelinkPreviousNodePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DelinkPreviousNodePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DelinkPreviousNodePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DelinkPreviousNodePageElements.class);
    }

    public void selectFirstNode()
    {
        click(DelinkPreviousNodePageElements.firstNode);
    }

    public void clickQuickLoadOk()
    {
        click(DelinkPreviousNodePageElements.quickLoadButton);
        waitForPageLoaded();
        click(DelinkPreviousNodePageElements.okButton);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForGridRefresh();
    }
}
