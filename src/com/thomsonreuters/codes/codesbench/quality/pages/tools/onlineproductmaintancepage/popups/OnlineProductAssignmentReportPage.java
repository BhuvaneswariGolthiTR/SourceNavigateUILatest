package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.OnlineProductAssignmentReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnlineProductAssignmentReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public OnlineProductAssignmentReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductAssignmentReportPageElements.class);
    }

    public String getSelectedProduct()
    {
        return getElement(OnlineProductAssignmentReportPageElements.SELECTED_PRODUCT).getText();
    }

    public void selectWipView()
    {
        OnlineProductAssignmentReportPageElements.wipView.click();
    }

    public void selectPubView()
    {
        OnlineProductAssignmentReportPageElements.pubView.click();
    }

    public void setDate(String date)
    {
        OnlineProductAssignmentReportPageElements.effectiveDate.clear();
        OnlineProductAssignmentReportPageElements.effectiveDate.sendKeys(date);
        waitForPageLoaded();
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(OnlineProductAssignmentReportPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(OnlineProductAssignmentReportPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public boolean clickOk()
    {
        sendEnterToElement(CommonPageElements.OK_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean switchWindow = switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return switchWindow;
    }

}
