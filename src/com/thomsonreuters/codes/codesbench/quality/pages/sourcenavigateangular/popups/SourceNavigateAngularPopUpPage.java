package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static java.lang.String.format;

@Component
public class SourceNavigateAngularPopUpPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularPopUpPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularPopUpPageElements.class);
    }

    public void closeUi(String uiTitle)
    {
        click(format(CLOSE_UI_BUTTON, uiTitle));
        waitForElementGone(format(UI_TITLE_PATTERN, uiTitle));
    }

    public String getWorkflowId()
    {
        return getElementsText(WORKFLOW_HYPERLINK);
    }

    public void openWorkflowDetailsPage()
    {
        click(WORKFLOW_HYPERLINK);
        switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void confirmAction()
    {
        click(CONFIRM_BUTTON);
    }

    public void cancelAction()
    {
        click(CANCEL_BUTTON);
    }

    public boolean verifyDifficultyLevelDropDownAllOptions(List<String> options)
    {
        return verifyAllDropDownOptionsExist(DIFFICULTY_LEVEL_DROPDOWN, options);
    }
}
