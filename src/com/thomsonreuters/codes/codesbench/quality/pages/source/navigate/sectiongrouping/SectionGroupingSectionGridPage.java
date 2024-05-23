package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingSectionGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SectionGroupingSectionGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingSectionGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingSectionGridPageElements.class);
    }

    public int getNumberOfSectionGridRows()
    {
        return SectionGroupingSectionGridPageElements.sectionGridRows.size();
    }

    public List<WebElement> getSectionGridRows()
    {
        return SectionGroupingSectionGridPageElements.sectionGridRows;
    }

    public void selectSectionsInRange(int firstSection, int lastSection)
    {
        //decrement the numberOfDocumentsToSelect because we are clicking the first
        //document before entering the for loop to select the other documents
        firstSection--;
        List<WebElement> sections = getSectionGridRows();
        if((lastSection - firstSection) > 0 && (lastSection - firstSection) < sections.size())
        {
            try
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                RobotUtils.getRobot().delay(500);

                for (int i = firstSection; i < lastSection; i++) {
                    click(sections.get(i));
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
            finally
            {
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }

    public void moveSelectedSectionsToGroup(String groupName)
    {
        rightClick(SectionGroupingSectionGridPageElements.selectedSection);
        openContextMenu(SectionGroupingSectionGridPageElements.moveToGroup, getElement(String.format(SectionGroupingSectionGridPageElements.MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH, groupName)));
    }

    public enum SortOrder
    {
        ASC,
        DESC
    }

    public boolean checkSectionNumberFilter(String filter)
    {
        waitForElement(SectionGroupingSectionGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SectionGroupingSectionGridPageElements.sectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkGroupNameFilter(String filter)
    {
        waitForElement(SectionGroupingSectionGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return SectionGroupingSectionGridPageElements.groupNames.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSubSectionNumberFilter(String filter)
    {
        waitForElement(SectionGroupingSectionGridPageElements.FIRST_SUBSECTION_NUMBER_XPATH);
        return SectionGroupingSectionGridPageElements.subSectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaCountFilter(String filter)
    {
        waitForElement(SectionGroupingSectionGridPageElements.FIRST_DELTA_COUNT_XPATH);
        return SectionGroupingSectionGridPageElements.deltaCounts.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionNumberSort(SortOrder sortOrder)
    {
        return checkSort(SectionGroupingSectionGridPageElements.sectionNumbers, (String s1, String s2) -> SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkSubSectionNumberSort(SortOrder sortOrder)
    {
        return checkSort(SectionGroupingSectionGridPageElements.subSectionNumbers, (String s1, String s2) -> SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkDeltaCountSort(SortOrder sortOrder)
    {
        return checkSort(SectionGroupingSectionGridPageElements.deltaCounts, (String s1, String s2) -> SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    protected boolean checkSort(List<WebElement> column, Comparator<String> comparator)
    {
        List<String> values = new ArrayList<>();
        column.forEach(val -> values.add(val.getText()));
        for (int i = 0; i < values.size() - 1; i++)
        {
            if (comparator.compare(values.get(i), values.get(i+1)) > 0)
            {
                return false;
            }
        }
        return true;
    }


    public void clickXSectionsInARange(int firstElement, int lastElement)
    {
        firstElement--;
        List<WebElement> documents = getElements(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION_NUMBER);
        if((lastElement - firstElement) > 0 && (lastElement - firstElement) < documents.size())
        {
            try
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                RobotUtils.getRobot().delay(500);
                for (int i = firstElement; i <= lastElement - 1; i++) {
                    documents.get(i).click();
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
            finally
            {
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }
    public void rightClickHighlightedSection(int firstSection)
    {
        rightClick(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_HIGHLIGHTED_GROUP_ON_POSITION, firstSection));
    }
    public void openMoveToGroupContextMenu(String groupName)
    {
        openContextMenu(SectionGroupingSectionGridPageElements.SECTION_GROUPING_MOVE_TO_GROUP,String.format(SectionGroupingSectionGridPageElements.SECTION_GROUPING_MOVE_TO_GROUP_X, groupName));
    }

    public boolean checkSectionGroupOnPosition(String groupName, int position)
    {
        return getElementsText(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUP_ON_POSITION, position)).equals(groupName);
    }

    public void clickSectionFromSelectedGroup()
    {
        click(SectionGroupingSectionGridPageElements.SECTIONS_DELTAS_FROM_SELECTED_GROUP_TAB);
    }

    public boolean checkSelectedGroupSectionDeltaNameList(String groupname)
    {
        ArrayList<Object> checksList = new ArrayList<>();
        int sectionsQuantity = getElements(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION).size();
        for(int i=1; i<sectionsQuantity; i++)
        {
            boolean groupNameFromColumn = getElement(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION + "[" + i + "]" +
                    SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION_GROUPNAME_IN_COLUMN).getText().equals(groupname);
            checksList.add(groupNameFromColumn);
        }
        return !checksList.contains(false);
    }

    public void selectAllSections()
    {
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_ALL_DELTAS_TAB);
    }

    public boolean checkAllGroupsSectionsDeltasNameList(String groupname)
    {
        ArrayList<Object> checksList = new ArrayList<>();
        int sectionsQuantity = getElements(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION).size();
        for(int i=1; i<sectionsQuantity; i++)
        {
            boolean groupNameFromColumn = getElement(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION + "[" + i + "]" +
                    SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SECTION_GROUPNAME_IN_COLUMN).getText().equals(groupname);
            checksList.add(groupNameFromColumn);
        }
        return checksList.contains(true);
    }

    public int getListOfItemsInGrid()
    {
        return getElements("//tbody[@class='yui-dt-data']/tr").size();
    }

    public void goToNextPage()
    {
        click(SectionGroupingPageElements.SECTION_DELTA_GROUPING_NEXT_PAGE_LINK);
        waitForElement(SectionGroupingPageElements.SECTION_DELTA_GROUPING_PREVIOUS_PAGE_LINK);
    }

    public boolean sectionGroupSecondPageIsEmpty()
    {
        return getElements(SectionGroupingPageElements.SECTION_DELTA_GROUPING_SECOND_PAGE_SPAN).isEmpty();
    }

    public boolean sectionGroupingDeltaCountInNewGroupNotEmpty(String groupname, int deltaCount)
    {
        return getElements(String.format(SectionGroupingPageElements.SECTION_DELTA_GROUPING_DELTAS_COUNT_IN_NEW_GROUP,groupname,deltaCount)).isEmpty();
    }

       public int getGroupDeltaCountByGroupName(String groupName)
    {
        return Integer.parseInt(getElementsText(String.format(SectionGroupingSectionGridPageElements.SELECTED_GROUP_DELTA_COUNT, groupName)));
    }

    public int getGroupSectionCountByGroupName(String groupName)
    {
        return Integer.parseInt(getElementsText(String.format(SectionGroupingSectionGridPageElements.SELECTED_GROUP_SECTION_COUNT, groupName)));
    }
}
