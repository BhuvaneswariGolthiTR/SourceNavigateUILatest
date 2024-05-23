package com.thomsonreuters.codes.codesbench.quality.pages.source;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.MismatchedReportPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.DateSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;


@Component
public class DateSearchFilterPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DateSearchFilterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DateSearchFilterPageElements.class);
    }

    public void setFilterDate(String setValue)
    {
        sendTextToTextbox(DateSearchFilterPageElements.dateFilter, setValue);
    }

    public void clickSubmitButton()
    {
        clickSubmitButtonOnForm();
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
    }

    public void setDateBeforeFilter(String setValue)
    {
        sendTextToTextbox(DateSearchFilterPageElements.dateBeforeInput, setValue);
    }

    public void clickDateBeforeRadioButton()
    {
        click(DateSearchFilterPageElements.dateBeforeRadioButton);
    }

    public void clickClearFilterRadioButton()
    {
        click(DateSearchFilterPageElements.noneRadioButton);
    }

    public boolean validateLoadDateFilterTitle(String expectedValue)
    {
        return sourceNavigateFiltersAndSortsPage().validateFilterAttributeValue(
                SourceNavigatePageElements.RENDITION_LOAD_DATE_FILTER_XPATH, "title", expectedValue);
    }

    public boolean validateVersionDateFilterTitle(String expectedValue)
    {
        return sourceNavigateFiltersAndSortsPage().validateFilterAttributeValue(
                SourceNavigatePageElements.RENDITION_VERSION_DATE_FILTER_XPATH, "title", expectedValue);
    }

    public boolean checkFilterDateBeforeInputIsExpected(String date)
    {
        return checkFieldValueIsExpectedOne(DateSearchFilterPageElements.dateBeforeInput, date);
    }

    public void clickSearchFilterCalendar()
    {
        click(DateSearchFilterPageElements.searchFilterCalendar);
    }

    public String getCalendarText()
    {
        return getElementsText(DateSearchFilterPageElements.MISMATCHED_REPORTS_CALENDAR);
    }

    public void selectDayNumber(String dayNumber)
    {
        click(String.format("//a[text()='%s']",dayNumber));
    }

    public void clickSubmitButtonMismatchReportPage()
    {
        clickSubmitButtonOnForm();
        switchToWindow(MismatchedReportPageElements.MISMATCHED_REPORTS_PAGE_TITLE);
    }

    public String getDateText()
    {
        return DateSearchFilterPageElements.dateTextBox.getAttribute("value");
    }

    public void clickCancelButtonMismatchReportPage()
    {
        click(CommonPageElements.CANCEL_BUTTON);
        switchToWindow(MismatchedReportPageElements.MISMATCHED_REPORTS_PAGE_TITLE);
    }
}