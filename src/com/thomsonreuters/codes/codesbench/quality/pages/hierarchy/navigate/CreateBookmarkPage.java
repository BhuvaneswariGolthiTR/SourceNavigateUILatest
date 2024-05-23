package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CreateBookmarkPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateBookmarkPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateBookmarkPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateBookmarkPageElements.class);
    }

    public boolean switchToCreateBookmarkWindow()
    {
        boolean windowAppears = switchToWindow(CreateBookmarkPageElements.CREATE_BOOKMARK_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void setBookmarkName(String name)
    {
        clearAndSendKeysToElement(CreateBookmarkPageElements.createBookmarkNameTextbox,name);
    }

    public void clickSave()
    {
        sendEnterToElement(CreateBookmarkPageElements.saveButton);
        waitForPageLoaded();
    }

    public boolean checkBookmarkWasCreated()
    {
        return getElementsText(CreateBookmarkPageElements.finishedBookmarkGrid).contains("Your bookmark has been created. Click 'close' to exit");
    }

    public void clickClose()
    {
        click(CreateBookmarkPageElements.closeButton);
        waitForWindowGoneByTitle(CreateBookmarkPageElements.CREATE_BOOKMARK_PAGE_TITLE);
        waitForPageLoaded();
    }

    public void clickCancel()
    {
        click(CreateBookmarkPageElements.cancelButton);
        waitForWindowGoneByTitle(CreateBookmarkPageElements.CREATE_BOOKMARK_PAGE_TITLE);
        waitForPageLoaded();
    }

    public boolean renditionIsSelected()
    {
        return isElementSelected(CreateBookmarkPageElements.VALIDATE_REPORT_DOC_NUM_IN_GRID + "/ancestor::*[1]");
    }
}

