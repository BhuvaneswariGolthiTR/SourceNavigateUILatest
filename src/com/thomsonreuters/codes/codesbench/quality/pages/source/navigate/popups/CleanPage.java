package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.CleanPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CleanPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CleanPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CleanPageElements.class);
    }

    public boolean switchToCleanWindow()
    {
        boolean windowAppears = switchToWindow(CleanPageElements.CLEAN_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void setMarkAsCleanDate(String date)
    {
        sendTextToTextbox(CleanPageElements.markAsCleanDate, date);
    }

    public void clickSave()
    {
        click(CleanPageElements.saveButton);
        waitForWindowGoneByTitle(CleanPageElements.CLEAN_PAGE_TITLE);
        waitForGridRefresh();
    }

    public void clickCancel()
    {
        sendEnterToElement(CleanPageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
        waitForWindowGoneByTitle(CleanPageElements.CLEAN_PAGE_TITLE);
    }
}
