package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.DateSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.MismatchedReportFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MismatchedReportFiltersPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public MismatchedReportFiltersPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, MismatchedReportFiltersPageElements.class);
    }

    public void setFilterYear(String value)
    {
        sendTextToTextBoxAuto(MismatchedReportFiltersPageElements.yearFilter,value);
    }

    public void setFilterContentSet(String value)
    {
        sendTextToTextBoxAuto(MismatchedReportFiltersPageElements.contentSetFilter,value);
    }

    public void setFilterDocType(String value)
    {
        sendTextToTextBoxAuto(MismatchedReportFiltersPageElements.docTypeFilter,value);
    }

    public void setFilterDocNumber(String value)
    {
        sendTextToTextbox(MismatchedReportFiltersPageElements.docNumberFilter,value);
        sendEnterToElement(MismatchedReportFiltersPageElements.docNumberFilter);
    }

    public boolean clickSourceLoadDateCalendar()
    {
        click(MismatchedReportFiltersPageElements.SOURCE_LOAD_DATE_CALENDAR_XPATH);
        boolean pageAppeared = switchToWindow(DateSearchFilterPageElements.DATE_SEARCH_FILTER_PAGE_TITLE);
        enterTheInnerFrame();
        return pageAppeared;
    }
}