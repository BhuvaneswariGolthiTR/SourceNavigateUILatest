package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.PrintPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PrintPreviewPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public PrintPreviewPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, PrintPreviewPageElements.class);
    }


    public boolean switchToTabularPrintPreviewWindow()
    {
        boolean windowAppears = switchToWindow(PrintPreviewPageElements.TABULAR_PRINT_PREVIEW_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public boolean switchToPrintPreviewWindow()
    {
        boolean windowAppears = switchToWindow(PrintPreviewPageElements.PRINT_PREVIEW_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public String getWorkflowId()
    {
        return getElementsText(PrintPreviewPageElements.workflowIDLink);
    }

    public void clickOK()
    {
        click(PrintPreviewPageElements.okButton);
        waitForWindowGoneByTitle(PrintPreviewPageElements.TABULAR_PRINT_PREVIEW_PAGE_TITLE);
    }

    public void clickWorkflowIDLink()
    {
        click(PrintPreviewPageElements.workflowIDLink);
        switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }
}
