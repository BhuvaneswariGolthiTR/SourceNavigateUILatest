package com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.openqa.selenium.Keys.ENTER;

@Component
public class YourWorkflowHasBeenCreatedPage extends BasePage
{
    WebDriver driver;
    @Autowired
    public YourWorkflowHasBeenCreatedPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, YourWorkflowHasBeenCreatedPageElements.class);
    }

    public String getWorkflowId()
    {
        String workflowURL = YourWorkflowHasBeenCreatedPageElements.linkInWindow.getText();
        return workflowURL.substring(workflowURL.lastIndexOf("=") + 1);
    }

    
    public void closeWorkflowConfirmationPage()
    {
        closeCurrentWindowIgnoreDialogue();
    }

    public void clickLink()
    {
        sendEnterToElement(YourWorkflowHasBeenCreatedPageElements.linkInWindow);
        switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
    }

    public boolean clickClose()
    {
        enterTheInnerFrame();
        sendEnterToElement(CommonPageElements.CLOSE_BUTTON);
        waitForWindowGoneByTitle(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        return !doesWindowExistByTitle(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
    }

    public void switchToWorkflowConfirmationPage()
    {
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public boolean workflowHasStarted()
    {
       waitForPageLoaded();
       return isElementDisplayed(YourWorkflowHasBeenCreatedPageElements.LINK_IN_WINDOW);
    }

    public void switchToYourWorkflowHasBeenCreatedPage()
    {
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public boolean verifyYourWorkflowHasBeenCreatedPageOpened()
    {
        return doesWindowExistByTitle(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
    }
}
