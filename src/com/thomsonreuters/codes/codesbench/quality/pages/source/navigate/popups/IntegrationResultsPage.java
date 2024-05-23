package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationResultsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IntegrationResultsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IntegrationResultsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IntegrationResultsPageElements.class);
    }

    public boolean switchToIntegrationResultsWindow()
    {
        boolean windowAppears = switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE, true);
        waitForPageLoaded();
        enterTheInnerFrame();
        waitForGridRefresh();
        waitForPageLoaded();
        return windowAppears;
    }

    public boolean checkIntegrationResultsWindowIsClosed()
    {
        return checkWindowIsClosed(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
    }

    public void clickClose()
    {
        click(IntegrationResultsPageElements.closeButton);
        waitForWindowGoneByTitle(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
    }
}
