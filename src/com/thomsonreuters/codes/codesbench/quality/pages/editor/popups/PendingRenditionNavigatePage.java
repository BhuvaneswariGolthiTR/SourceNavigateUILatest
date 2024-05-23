package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PendingRenditionNavigatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public PendingRenditionNavigatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PendingRenditionNavigatePageElements.class);
    }

    public void selectFirstRenditionForLink()
    {
        rightClick(PendingRenditionNavigatePageElements.RENDITIONS + "[1]");
        sendEnterToElement(PendingRenditionNavigatePageElements.SELECT_SOURCE_FOR_LINK);
        waitForWindowGoneByTitle(PendingRenditionNavigatePageElements.PAGE_TITLE);
    }

    public void clickRefreshLink()
    {
        sendEnterToElement(PendingRenditionNavigatePageElements.REFRESH_LINK);
        waitForPageLoaded();
    }

    public void switchToPendingRenditionNavigatePage()
    {
        switchToWindow(PendingRenditionNavigatePageElements.PAGE_TITLE);
    }
}
