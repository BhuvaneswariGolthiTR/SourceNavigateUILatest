package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageOnlineProductAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageTaxTypeAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.OnlineProductAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ManageOnlineProductAssignmentsPage extends ManageAssignmentsPage
{
    WebDriver driver;

    @Autowired
    public ManageOnlineProductAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ManageOnlineProductAssignmentsPageElements.class);
    }


    public void clickSubmit()
    {
        sendEnterToElement(CommonPageElements.SUBMIT_BUTTON);
    }

    public boolean doesManageOnlineProductAssignmentsPageClose()
    {
        waitForWindowGoneByTitle(ManageOnlineProductAssignmentsPageElements.PAGE_TITLE);
        return !doesWindowExistByTitle(ManageOnlineProductAssignmentsPageElements.PAGE_TITLE);
    }

    public boolean clickSubmitAndVerifyWorkflowStarted()
    {
        waitForPageLoaded();
        sendEnterToElement(CommonPageElements.SUBMIT_BUTTON);
        boolean workflowStarted = switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return workflowStarted;
    }

}
