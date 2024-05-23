package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IndexReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IndexReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IndexReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IndexReportPageElements.class);
    }

    public boolean switchToIndexReportWindow()
    {
        boolean windowAppears = switchToWindow(IndexReportPageElements.INDEX_REPORT_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void clickNext()
    {
        click(IndexReportPageElements.nextButton);
    }

    public void clickCancel()
    {
        click(IndexReportPageElements.cancelButton);
    }

    public void setReportType(String type)
    {
        //ADD, REPEAL, COMBINED
        checkCheckbox(String.format(IndexReportPageElements.INDEX_REPORT_REPORT_TYPE_CHECKBOX, type));
    }

    public void setSortOrderType(String type)
    {
        //default, sortByClassNumber, sortByWestId, sortByTrackingNumber
        checkCheckbox(String.format(IndexReportPageElements.INDEX_REPORT_SORT_ORDER_TYPE_CHECKBOX, type));
    }

    public void setYear(String year)
    {
        selectDropdownOption(IndexReportPageElements.INDEX_REPORT_YEAR_DROPDOWN, year);
    }

    public void setContentSet(String contentSet)
    {
        selectDropdownOption(IndexReportPageElements.INDEX_REPORT_CONTENT_SET_DROPDOWN, contentSet);
    }

    public void setSession(String session)
    {
        selectDropdownOption(IndexReportPageElements.INDEX_REPORT_SESSION_DROPDOWN, session);
    }

    public int getNumberOfRenditionsToMove()
    {
        return Integer.parseInt(getElementsAttribute(IndexReportPageElements.renditionsToMoveOptions, "size"));
    }

    public void selectRenditionByIndex(int index)
    {
        click(getElements(IndexReportPageElements.INDEX_REPORT_SELECTED_RENDITIONS_TO_MOVE_OPTIONS).get(index));
    }

    public void moveRenditionToReport()
    {
        click(IndexReportPageElements.INDEX_REPORT_SELECTED_RENDITIONS_MOVE_BUTTON);
    }

    public void clickRunReport()
    {
        click(IndexReportPageElements.INDEX_REPORT_SELECTED_RENDITIONS_RUN_REPORT_BUTTON);
        waitForWindowGoneByTitle(IndexReportPageElements.INDEX_REPORT_PAGE_TITLE);
        sourcePage().switchToSourceNavigatePage();
    }
}
