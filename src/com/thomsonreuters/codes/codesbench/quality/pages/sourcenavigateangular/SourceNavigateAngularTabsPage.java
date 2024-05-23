package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceNavigateAngularTabsPage extends BasePage
{
    private final WebDriver driver;

    public SourceNavigateAngularTabsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularTabsPageElements.class);
    }

    public boolean isTabDisabled(String tabName)
    {
        return isElementDisabled(String.format(SourceNavigateAngularTabsPageElements.TAB_XPATH, tabName));
    }

    public void clickSourceTab()
    {
        click(SourceNavigateAngularTabsPageElements.SOURCE_TAB_NAME);
    }

    public void clickLockReportTab()
    {
        click(SourceNavigateAngularTabsPageElements.LOCK_REPORT_TAB_NAME);
    }

    public void clickAnyTab(String tabName)
    {
        click(String.format(SourceNavigateAngularTabsPageElements.ANY_TAB_NAME, tabName));
    }
}
