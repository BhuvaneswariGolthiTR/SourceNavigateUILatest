package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IndexReportSortOrderPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IndexReportSortOrderPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexReportSortOrderPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexReportSortOrderPageElements.class);
    }

    public void clickDefaultSortOrder()
    {
        setSortOrder("default");
    }

    public void clickClassNumberSortOrder()
    {
        setSortOrder("sortByClassNumber");
    }

    public void clickWestIdSortOrder()
    {
        setSortOrder("sortByWestId");
    }

    public void clickTrackingNumberSortOrder()
    {
        setSortOrder("sortByTrackingNumber");
    }

    private void setSortOrder(String type)
    {
        //default, sortByClassNumber, sortByWestId, sortByTrackingNumber
        checkCheckbox(String.format(IndexReportSortOrderPageElements.INDEX_REPORT_SORT_ORDER_CHECKBOX, type));
    }

    public String clickOk()
    {
        click(IndexReportSortOrderPageElements.okButton);
        return AutoITUtils.getWorkflowIdFromSourceNavigateAlert(true, "The following workflow has been started:");
    }

    public boolean switchToIndexReportSortOrderPage()
    {
        boolean windowAppears = switchToWindow(IndexReportSortOrderPageElements.INDEX_REPORT_SORT_ORDER_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }
}
