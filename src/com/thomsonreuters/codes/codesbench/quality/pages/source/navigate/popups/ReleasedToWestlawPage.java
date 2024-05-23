package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ReleasedToWestlawPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReleasedToWestlawPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ReleasedToWestlawPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ReleasedToWestlawPageElements.class);
    }

    public boolean switchToReleasedToWestlawWindow()
    {
        boolean windowAppears = switchToWindow(ReleasedToWestlawPageElements.RELEASED_TO_WESTLAW_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void setReleasedToWestlawDate(String date)
    {
        sendTextToTextbox(ReleasedToWestlawPageElements.releasedToWestlawDate, date);
    }

    public void clickSave()
    {
        click(ReleasedToWestlawPageElements.saveButton);
        waitForWindowGoneByTitle(ReleasedToWestlawPageElements.RELEASED_TO_WESTLAW_PAGE_TITLE);
        waitForGridRefresh();
    }

    public void clickCancel()
    {
        sendEnterToElement(ReleasedToWestlawPageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
        waitForWindowGoneByTitle(ReleasedToWestlawPageElements.RELEASED_TO_WESTLAW_PAGE_TITLE);
    }
}
