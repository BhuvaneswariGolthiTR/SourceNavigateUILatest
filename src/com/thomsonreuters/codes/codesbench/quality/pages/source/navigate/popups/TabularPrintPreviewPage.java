package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.TabularPrintPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TabularPrintPreviewPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public TabularPrintPreviewPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, TabularPrintPreviewPageElements.class);
    }

    public void clickOKButton()
    {
        click(TabularPrintPreviewPageElements.okButton);
        switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
    }

    public void clickWorkflowIDLink()
    {
        click(TabularPrintPreviewPageElements.workflowIDLink);
        switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }
}
