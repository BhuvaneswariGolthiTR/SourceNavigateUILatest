package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ReportPageElements.class);
    }

    public boolean switchToReportPage()
    {
        boolean windowAppears = switchToWindow(ReportPageElements.REPORT_PAGE_TITLE);
        waitForPageLoaded();
        return windowAppears;
    }

    public void clickPrint()
    {
        click(ReportPageElements.printButton);
    }

    public void clickClose()
    {
        click(ReportPageElements.closeButton);
        waitForWindowGoneByTitle(ReportPageElements.REPORT_PAGE_TITLE);
    }

    public boolean titleIsWorksheetReport()
    {
        return verifyReportTitle(ReportPageElements.WORKSHEET_REPORT_TITLE);
    }

    public boolean titleIsUnusedReport()
    {
        return verifyReportTitle(ReportPageElements.UNUSED_REPORT_TITLE);
    }

    public boolean titleIsIntegrationReport()
    {
        return verifyReportTitle(ReportPageElements.INTEGRATION_REPORT_TITLE);
    }

    private boolean verifyReportTitle(String title)
    {
        return isElementDisplayed(String.format(ReportPageElements.REPORT_TITLE, title));
    }
}
