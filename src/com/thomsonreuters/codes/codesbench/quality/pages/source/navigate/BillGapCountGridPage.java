package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.BillGapCountGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillGapCountGridPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public BillGapCountGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, BillGapCountGridPageElements.class);
    }

    public boolean recordListIsEmpty()
    {
        enterTheInnerFrame();
        boolean isListEmpty = doesElementExist(BillGapCountGridPageElements.NO_RECORDS_FOUND_MESSAGE);
        breakOutOfFrame();
        return isListEmpty;
    }

    public void refreshTheGrid()
    {
        click(BillGapCountGridPageElements.SEARCH_BUTTON);
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void setFilterDocType(String setValue)
    {
        enterTheInnerFrame();
        clearAndSendTextToTextbox(BillGapCountGridPageElements.DOC_TYPE_FILTER,setValue);
        breakOutOfFrame();
    }

    public List<String> getFilterList(String filterType)
    {
        enterTheInnerFrame();
        List<String> recordList = getElementsTextList(String.format(BillGapCountGridPageElements.GRID_FILTER_X_XPATH, filterType));
        breakOutOfFrame();
        return recordList;
    }

    public List<Boolean> getVerifiedListBasedOnFilteredType(List<String>list, String value)
    {
        ArrayList<Boolean> verifiedList = new ArrayList<Boolean>();
        list.forEach((filterType)->{
            verifiedList.add(filterType.equals(value));
        });
        return verifiedList;
    }

    public void sortByDocType()
    {
        enterTheInnerFrame();
        click(BillGapCountGridPageElements.DOC_TYPE_SORT);
        waitForGridRefresh();
        breakOutOfFrame();
        waitForPageLoaded();
    }

    public void clearAllFilters()
    {
        enterTheInnerFrame();;
        click(BillGapCountGridPageElements.CLEAR_ALL_FILTERS);
        waitForGridRefresh();
        breakOutOfFrame();
        waitForPageLoaded();
    }

    public boolean listIsNotFilteredByX(List<String>list, String value)
    {
        return !list.stream().filter(columnValue->!columnValue.equals(value)).collect(Collectors.toList()).isEmpty();
    }

}
