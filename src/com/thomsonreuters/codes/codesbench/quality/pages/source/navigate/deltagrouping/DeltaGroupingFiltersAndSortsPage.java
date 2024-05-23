package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping.DeltaGroupingFiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupingFiltersAndSortsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingFiltersAndSortsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingFiltersAndSortsPageElements.class);
    }

    public void setSectionNumberFilter(String setValue)
    {
        clearAndSendKeysToElement(DeltaGroupingFiltersAndSortsPageElements.sectionNumberFilter, setValue);
        sendEnterToElement(DeltaGroupingFiltersAndSortsPageElements.sectionNumberFilter);
        deltaGroupingPage().waitForDeltaGridRefresh();
    }

    public void setSubSectionNumberFilter(String setValue)
    {
        clearAndSendKeysToElement(DeltaGroupingFiltersAndSortsPageElements.subSectionNumberFilter, setValue);
        sendEnterToElement(DeltaGroupingFiltersAndSortsPageElements.subSectionNumberFilter);
        deltaGroupingPage().waitForDeltaGridRefresh();
    }

    public void setDeltaLevelFilter(String setValue)
    {
        clearAndSendKeysToElement(DeltaGroupingFiltersAndSortsPageElements.deltaLevelFilter, setValue);
        sendEnterToElement(DeltaGroupingFiltersAndSortsPageElements.deltaLevelFilter);
        deltaGroupingPage().waitForDeltaGridRefresh();
    }

    public void setGroupNameFilter(String setValue)
    {
        scrollToView(DeltaGroupingFiltersAndSortsPageElements.groupNameFilter);
        clearAndSendKeysToElement(DeltaGroupingFiltersAndSortsPageElements.groupNameFilter, setValue);
        sendEnterToElement(DeltaGroupingFiltersAndSortsPageElements.groupNameFilter);
        deltaGroupingPage().waitForDeltaGridRefresh();
    }

    public void setActionFilter(String setValue)
    {
        clearAndSendKeysToElement(DeltaGroupingFiltersAndSortsPageElements.actionFilter, setValue);
        sendEnterToElement(DeltaGroupingFiltersAndSortsPageElements.actionFilter);
        deltaGroupingPage().waitForDeltaGridRefresh();
    }

    public void clickSectionNumberSort()
    {
        click(DeltaGroupingFiltersAndSortsPageElements.sectionNumberSort);
    }

    public void sortSectionNumberAscending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.sectionNumberSort.getAttribute("title").contains("ascending"))
        {
            clickSectionNumberSort();
        }
        else
        {
            clickSectionNumberSort();
            clickSectionNumberSort();
        }
    }

    public void sortSectionNumberDescending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.sectionNumberSort.getAttribute("title").contains("descending"))
        {
            clickSectionNumberSort();
        }
        else
        {
            clickSectionNumberSort();
            clickSectionNumberSort();
        }
    }

    public void sortDeltaLevelAscending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.deltaLevelSort.getAttribute("title").contains("ascending"))
        {
            clickDeltaLevelSort();
        }
        else
        {
            clickDeltaLevelSort();
            clickDeltaLevelSort();
        }
    }

    public void sortDeltaLevelDescending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.deltaLevelSort.getAttribute("title").contains("descending"))
        {
            clickDeltaLevelSort();
        }
        else
        {
            clickDeltaLevelSort();
            clickDeltaLevelSort();
        }
    }

    public void sortActionAscending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.actionSort.getAttribute("title").contains("ascending"))
        {
            clickActionSort();
        }
        else
        {
            clickActionSort();
            clickActionSort();
        }
    }

    public void sortActionDescending()
    {
        if(DeltaGroupingFiltersAndSortsPageElements.actionSort.getAttribute("title").contains("descending"))
        {
            clickActionSort();
        }
        else
        {
            clickActionSort();
            clickActionSort();
        }
    }

    public void clickSubSectionNumberSort()
    {
        click(DeltaGroupingFiltersAndSortsPageElements.subSectionNumberSort);
    }

    public void clickDeltaLevelSort()
    {
        click(DeltaGroupingFiltersAndSortsPageElements.deltaLevelSort);
    }

    public void clickActionSort()
    {
        click(DeltaGroupingFiltersAndSortsPageElements.actionSort);
    }

}
