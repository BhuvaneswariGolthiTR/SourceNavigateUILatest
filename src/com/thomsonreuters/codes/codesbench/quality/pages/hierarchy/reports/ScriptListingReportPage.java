package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyScriptListingReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScriptListingReportPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public ScriptListingReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyScriptListingReportsPageElements.class);
    }

    public void selectWipViewRadioButton()
    {
        enterTheInnerFrame();
        click(HierarchyScriptListingReportsPageElements.wipViewRadioButton);
    }

    public void selectScriptWithGivenValue(String scriptValue)
    {
        click(String.format(HierarchyScriptListingReportsPageElements.SCRIPT_WITH_GIVEN_VALUE_XPATH,scriptValue));
    }

    public void clickOk()
    {
        click(HierarchyScriptListingReportsPageElements.okButton);
        waitForWindowGoneByTitle(HierarchyScriptListingReportsPageElements.HIERARCHY_MENU_SCRIPT_LISTING_REPORT_PAGE_TITLE);
        switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void selectPubViewRadioButton()
    {
        enterTheInnerFrame();
        click(HierarchyScriptListingReportsPageElements.pubViewRadioButton);
    }
}