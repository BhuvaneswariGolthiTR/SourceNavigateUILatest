package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.AddAssignedScriptsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddAssignedScriptsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AddAssignedScriptsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddAssignedScriptsPageElements.class);
    }

    public void rightClickScriptWithGivenPubTagValue(String pubTagValue)
    {
        click(String.format(AddAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE,pubTagValue));
        rightClick(String.format(AddAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE,pubTagValue));
    }

    public boolean switchToAddAssignedScriptsPage()
    {
        boolean windowAppeared = switchToWindow(AddAssignedScriptsPageElements.ADD_ASSIGNED_SCRIPTS_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean isAddAssignedScriptsPageClosed()
    {
      return checkWindowIsClosed(AddAssignedScriptsPageElements.ADD_ASSIGNED_SCRIPTS_PAGE_TITLE);
    }
}
