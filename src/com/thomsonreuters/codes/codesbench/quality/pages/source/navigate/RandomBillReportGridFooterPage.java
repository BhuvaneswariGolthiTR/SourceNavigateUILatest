package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.RandomBillReportGridFooterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RandomBillReportGridFooterPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RandomBillReportGridFooterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RandomBillReportGridFooterPageElements.class);
    }

    public int getNumberOfResults()
    {
        String results = RandomBillReportGridFooterPageElements.renditionResult.getAttribute("innerHTML");
        int result = Integer.parseInt(results.substring(0,results.indexOf(' ')));
        return result;
    }

    public void refreshTheGrid()
    {
        click(RandomBillReportGridFooterPageElements.REPORT_REFRESH);
        waitForPageLoaded();
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clearGridSorts()
    {
        click(RandomBillReportGridFooterPageElements.REPORT_CLEAR_SORT);
        waitForPageLoaded();
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clearGridFilters()
    {
        click(RandomBillReportGridFooterPageElements.REPORT_CLEAR_ALL_FILTERS);
        waitForPageLoaded();
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void clearGridSelections()
    {
        click(RandomBillReportGridFooterPageElements.REPORT_CLEAR_SELECTION);
    }
}

