package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.StageCheckReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StageCheckReportsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public StageCheckReportsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, StageCheckReportsPageElements.class);
    }

    public boolean switchToStageCheckReportWindow()
    {
        boolean windowAppears = switchToWindow(StageCheckReportsPageElements.STAGE_CHECK_REPORTS_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        maximizeCurrentWindow();
        return windowAppears;
    }

    public void selectYearDropdown(String year)
    {
        click(StageCheckReportsPageElements.yearDropdown);
        selectDropdownOption(StageCheckReportsPageElements.yearDropdown, year);
    }

    public void selectLegislationDropdown(String legislation)
    {
        click(StageCheckReportsPageElements.legislationDropdown);
        selectDropdownOption(StageCheckReportsPageElements.legislationDropdown, legislation);
    }


    public boolean addContentSet(String contentSet)
    {
        click(String.format(StageCheckReportsPageElements.STAGE_CHECK_REPORT_CONTENT_SET_NON_SELECTED_OPTION, contentSet));
        click(StageCheckReportsPageElements.rightArrowButton);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
        return !(getElementsAttribute(StageCheckReportsPageElements.selectedContentSetList, "size").equals("0"));
    }

    public void clickSearch()
    {
        click(StageCheckReportsPageElements.searchButton);
    }

    public boolean searchResultsPopulate()
    {
        return doesElementExist(StageCheckReportsPageElements.STAGE_CHECK_REPORT_ROW_IN_SEARCH_RESULT_GRID);
    }

    public void enterGridFrame()
    {
        driver().switchTo().frame(getElement(SourceNavigatePageElements.STAGE_CHECK_REPORT_SEARCH_RESULTS_RESULTS_FRAME));
        waitForGridRefresh();
    }

    public int getRowCount()
    {
        return tableTestingPage().getRowCount(TableTestingPage.StageCheckReportsColumns.CONTENT_SET);
    }

    public void setContentSetFilter(String value)
    {
        tableTestingPage().setFilter(TableTestingPage.StageCheckReportsColumns.CONTENT_SET, value);
    }
    public void setSessionFilter(String value)
    {
        tableTestingPage().setFilter(TableTestingPage.StageCheckReportsColumns.SESSION, value);
    }
    public void setDocTypeFilter(String value)
    {
        tableTestingPage().setFilter(TableTestingPage.StageCheckReportsColumns.DOC_TYPE, value);
    }
    public void setDocNumberFilter(String value)
    {
        tableTestingPage().setFilter(TableTestingPage.StageCheckReportsColumns.DOC_NUMBER, value);
    }
    public void setContentTypeFilter(String value)
    {
        tableTestingPage().setFilter(TableTestingPage.StageCheckReportsColumns.CONTENT_TYPE, value);
    }

    public void clickRefresh()
    {
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(TableTestingPage.StageCheckReportsColumns.CONTENT_TYPE)).click();
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(TableTestingPage.StageCheckReportsColumns.CONTENT_TYPE)).sendKeys(Keys.ENTER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        getElement(TableTestingPage.FILTER_INPUTS_MAP.get(TableTestingPage.StageCheckReportsColumns.CONTENT_TYPE)).sendKeys(Keys.ENTER);
        waitForGridRefresh();
    }
}
