package com.thomsonreuters.codes.codesbench.quality.pages.source;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.StageCheckReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class StageCheckReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public StageCheckReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, StageCheckReportPageElements.class);
    }

    public void setYearDropDown(String value)
    {
        selectDropdownOption(StageCheckReportPageElements.yearDropdown, value);
    }
    public void setLegislationDropDown(String value)
    {
        selectDropdownOption(StageCheckReportPageElements.legislationDropdown, value);
    }
    public void setContentSetList(String value)
    {
        selectDropdownOption(StageCheckReportPageElements.nonSelectedContentSets, value);
    }
    public void clickMoveOneFromNonSelectedToSelected()
    {
        click(StageCheckReportPageElements.moveOneToSelectedButton);
    }
    public void clickSearch()
    {
        click(StageCheckReportPageElements.searchButton);
        click(StageCheckReportPageElements.searchButton);
        waitForGridRefresh();
    }
    public void setContentSetFilter(String value)
    {
        sendTextToTextbox(StageCheckReportPageElements.contentSetFilter, value);
    }
    public void setSessionFilter(String value)
    {
        sendTextToTextbox(StageCheckReportPageElements.sessionFilter, value);
    }
    public void setDocTypeFilter(String value)
    {
        sendTextToTextbox(StageCheckReportPageElements.docTypeFilter, value);
    }
    public void setDocNumberFilter(String value)
    {
        sendTextToTextbox(StageCheckReportPageElements.docNumberFilter, value);
    }
    public void setContentTypeFilter(String value)
    {
        sendTextToTextbox(StageCheckReportPageElements.contentTypeFilter, value);
    }
    public void refreshTheGrid()
    {
        click(StageCheckReportPageElements.refreshButton);
    }
    public int getRowCount()
    {
        return tableTestingPage().getRowCount(TableTestingPage.StageCheckReportsColumns.CONTENT_SET);
    }
    public void enterGridFrame()
    {
        switchToInnerIFrameByIndex(1);
    }
}
