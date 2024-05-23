package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.UpdateYearOrSessionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UpdateYearOrSessionPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UpdateYearOrSessionPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateYearOrSessionPageElements.class);
    }

    public void clickSave()
    {
        click(UpdateYearOrSessionPageElements.saveButton);
    }

    public void setYearDate(String year)
    {
        sendTextToTextbox(UpdateYearOrSessionPageElements.yearTextBox, year);
    }

    public boolean clickWorkflowLink()
    {
        click(UpdateYearOrSessionPageElements.WORKFLOW_LINK);
        return switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
    }

    public void setSessionDropdown(String session)
    {
        selectDropdownOption(UpdateYearOrSessionPageElements.sessionDropdown, session);
    }

    public boolean clickCancel()
    {
        click(UpdateYearOrSessionPageElements.cancelButton);
        return !isElementDisplayed(UpdateYearOrSessionPageElements.UPDATE_YEAR_OR_SESSION_PAGE_TITLE);
    }
}
