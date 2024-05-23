package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.RandomBillReportContentSelectPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RandomBillReportContentSelectPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RandomBillReportContentSelectPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RandomBillReportContentSelectPageElements.class);
    }

    public void selectContentSetFromNonSelectedList(String contentSet)
    {
        click(String.format(RandomBillReportContentSelectPageElements.NONSELECTED_CONTENT_SET_OPTION,contentSet));
    }

    public void clickSelectOneOptionArrow()
    {
        click(RandomBillReportContentSelectPageElements.SELECT_ONE_OPTION_ARROW);
        DateAndTimeUtils.takeNap(100);
    }

    public void clickSelectAllOptionArrow()
    {
        click(RandomBillReportContentSelectPageElements.SELECT_ALL_OPTIONS_ARROW);
        DateAndTimeUtils.takeNap(100);
    }

    public void clickSearchButton()
    {
        click(RandomBillReportContentSelectPageElements.SEARCH_BUTTON);
        waitForGridRefresh();
    }

    public int getNonSelectedCount()
    {
        return getElements(RandomBillReportContentSelectPageElements.NONSELECTED_CONTENT_SET_LIST + "/option").size();
    }

    public void selectFirstNonSelectedSet()
    {
        click(RandomBillReportContentSelectPageElements.NONSELECTED_CONTENT_SET_LIST + "/option[1]");
    }

    public int getSelectedCount()
    {
        return getElements(RandomBillReportContentSelectPageElements.SELECTED_CONTENT_SET_LIST + "/option").size();
    }

    public void selectFirstSelectedSet()
    {
        click(RandomBillReportContentSelectPageElements.SELECTED_CONTENT_SET_LIST + "/option[1]");
    }

    public void clickDeselectOneArrow()
    {
        click(RandomBillReportContentSelectPageElements.DESELECT_ONE_OPTION_ARROW);
        DateAndTimeUtils.takeNap(100);
    }

    public void clickDeselectAllArrow()
    {
        click(RandomBillReportContentSelectPageElements.DESELECT_ALL_OPTIONS_ARROW);
        DateAndTimeUtils.takeNap(100);
    }

    public void setYear(String year)
    {
        selectDropdownOption(RandomBillReportContentSelectPageElements.YEAR_DROPDOWN_XPATH, year);
    }
}
