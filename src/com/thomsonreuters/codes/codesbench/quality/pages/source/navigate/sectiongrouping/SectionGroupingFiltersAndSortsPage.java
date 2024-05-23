package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingFiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SectionGroupingFiltersAndSortsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingFiltersAndSortsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingFiltersAndSortsPageElements.class);
    }

    public void setSectionNumberFilter(String setValue)
    {
        clearAndSendKeysToElement(SectionGroupingFiltersAndSortsPageElements.sectionNumberFilter, setValue);
        sendEnterToElement(SectionGroupingFiltersAndSortsPageElements.sectionNumberFilter);
        sectionGroupingPage().waitForSectionGridRefresh();
    }

    public void setSubSectionNumberFilter(String setValue)
    {
        clearAndSendKeysToElement(SectionGroupingFiltersAndSortsPageElements.subSectionNumberFilter, setValue);
        sendEnterToElement(SectionGroupingFiltersAndSortsPageElements.subSectionNumberFilter);
        sectionGroupingPage().waitForSectionGridRefresh();
    }

    public void setDeltaCountFilter(String setValue)
    {
        clearAndSendKeysToElement(SectionGroupingFiltersAndSortsPageElements.deltaCountFilter, setValue);
        sendEnterToElement(SectionGroupingFiltersAndSortsPageElements.deltaCountFilter);
        sectionGroupingPage().waitForSectionGridRefresh();
    }

    public void setGroupNameFilter(String setValue)
    {
        clearAndSendKeysToElement(SectionGroupingFiltersAndSortsPageElements.groupNameFilter, setValue);
        sendEnterToElement(SectionGroupingFiltersAndSortsPageElements.groupNameFilter);
        sectionGroupingPage().waitForSectionGridRefresh();
    }
    public void clickSectionNumberSort()
    {
        click(SectionGroupingFiltersAndSortsPageElements.sectionNumberSort);
    }

    public void sortSectionNumberAscending()
    {
        if(SectionGroupingFiltersAndSortsPageElements.sectionNumberSort.getAttribute("title").contains("ascending"))
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
        if(SectionGroupingFiltersAndSortsPageElements.sectionNumberSort.getAttribute("title").contains("descending"))
        {
            clickSectionNumberSort();
        }
        else
        {
            clickSectionNumberSort();
            clickSectionNumberSort();
        }
    }

    public void clickSubSectionNumberSort()
    {
        click(SectionGroupingFiltersAndSortsPageElements.subSectionNumberSort);
    }

    public void sortSubSectionNumberAscending()
    {
        if(SectionGroupingFiltersAndSortsPageElements.subSectionNumberSort.getAttribute("title").contains("ascending"))
        {
            clickSubSectionNumberSort();
        }
        else
        {
            clickSubSectionNumberSort();
            clickSubSectionNumberSort();
        }
    }

    public void sortSubSectionNumberDescending()
    {
        if(SectionGroupingFiltersAndSortsPageElements.subSectionNumberSort.getAttribute("title").contains("descending"))
        {
            clickSubSectionNumberSort();
        }
        else
        {
            clickSubSectionNumberSort();
            clickSubSectionNumberSort();
        }
    }

    public void clickDeltaCountSort()
    {
        click(SectionGroupingFiltersAndSortsPageElements.deltaCountSort);
        System.out.println(SectionGroupingFiltersAndSortsPageElements.deltaCountSort.getAttribute("title"));
    }

    public void sortDeltaCountAscending()
    {
        if(SectionGroupingFiltersAndSortsPageElements.deltaCountSort.getAttribute("title").contains("ascending"))
        {
            clickDeltaCountSort();
        }
        else
        {
            clickDeltaCountSort();
            clickDeltaCountSort();
        }
    }

    public void sortDeltaCountDescending()
    {
        if(SectionGroupingFiltersAndSortsPageElements.deltaCountSort.getAttribute("title").contains("descending"))
        {
            clickDeltaCountSort();
        }
        else
        {
            clickDeltaCountSort();
            clickDeltaCountSort();
        }
    }

    public List<String> getTopToolBarItems()
    {
        List<String> listOfButtons = new ArrayList<>();
        for(int i=1; i<=4; i++)
        {
            String button = getElementsText(String.format(SectionGroupingFiltersAndSortsPageElements.SECTION_DELTA_GROUPING_TOOLBAR_BUTTONS, i));
            listOfButtons.add(button);
        }
        return listOfButtons;
    }

    public List<String> getBottomToolBarItems() {
        List<String> listOfButtons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String button = getElementsText(String.format(SectionGroupingFiltersAndSortsPageElements.SECTION_DELTA_GROUPING_BOTTOM_BUTTONS, i));
            listOfButtons.add(button);
        }
        return listOfButtons;
    }
}