package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping.DeltaGroupingDeltaGridPageElements;
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
public class DeltaGroupingDeltaGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingDeltaGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingDeltaGridPageElements.class);
    }

    public int getNumberOfDeltaGridRows()
    {
        return DeltaGroupingDeltaGridPageElements.deltaGridRows.size();
    }

    public List<WebElement> getDeltaGridRows()
    {
        return DeltaGroupingDeltaGridPageElements.deltaGridRows;
    }

    public String getSectionNumberTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(DeltaGroupingDeltaGridPageElements.SECTION_NUMBER_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getSubSectionNumberTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(DeltaGroupingDeltaGridPageElements.SUBSECTION_NUMBER_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getDeltaLevelTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(DeltaGroupingDeltaGridPageElements.DELTA_LEVEL_UNDER_NUMBER_XPATH, rowNum));
    }

    public String getActionTextUnderGivenRow(String rowNum)
    {
        return getElementsText(String.format(DeltaGroupingDeltaGridPageElements.ACTION_UNDER_NUMBER_XPATH, rowNum));
    }

    public void selectDeltasInRange(int firstDelta, int lastDelta)
    {
        //decrement the numberOfDocumentsToSelect because we are clicking the first
        //document before entering the for loop to select the other documents
        firstDelta--;
        List<WebElement> deltas = sourceNavigateGridPage().getDeltaGridRows();
        if((lastDelta - firstDelta) > 0 && (lastDelta - firstDelta) < deltas.size())
        {
            try
            {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
                RobotUtils.getRobot().delay(500);
                for (int i = firstDelta; i < lastDelta; i++)
                {
                    click(deltas.get(i));
                }
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
            finally
            {
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }

    public void moveSelectedDeltasToGroup(String groupName)
    {
        click(DeltaGroupingDeltaGridPageElements.selectedDelta);
        rightClick(DeltaGroupingDeltaGridPageElements.selectedDelta);
        openContextMenu(DeltaGroupingDeltaGridPageElements.moveToGroup, getElement(String.format(DeltaGroupingDeltaGridPageElements.MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH, groupName)));
    }

    public enum SortOrder
    {
        ASC,
        DESC
    }

    public boolean checkSectionNumberFilter(String filter)
    {
        waitForElement(DeltaGroupingDeltaGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return DeltaGroupingDeltaGridPageElements.sectionNumbers.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkDeltaLevelFilter(String filter)
    {
        waitForElement(DeltaGroupingDeltaGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return DeltaGroupingDeltaGridPageElements.deltaLevels.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkActionFilter(String filter)
    {
        waitForElement(DeltaGroupingDeltaGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return DeltaGroupingDeltaGridPageElements.actions.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkGroupNameFilter(String filter)
    {
        waitForElement(DeltaGroupingDeltaGridPageElements.FIRST_SECTION_NUMBER_XPATH);
        return DeltaGroupingDeltaGridPageElements.groupNames.stream().allMatch(val -> val.getText().contains(filter));
    }

    public boolean checkSectionNumberSort(DeltaGroupingDeltaGridPage.SortOrder sortOrder)
    {
        return checkSort(DeltaGroupingDeltaGridPageElements.sectionNumbers, (String s1, String s2) -> DeltaGroupingDeltaGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkDeltaLevelSort(DeltaGroupingDeltaGridPage.SortOrder sortOrder)
    {
        return checkSort(DeltaGroupingDeltaGridPageElements.deltaLevels, (String s1, String s2) -> DeltaGroupingDeltaGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
    }

    public boolean checkActionSort(DeltaGroupingDeltaGridPage.SortOrder sortOrder)
    {
        return checkSort(DeltaGroupingDeltaGridPageElements.actions, (String s1, String s2) -> DeltaGroupingDeltaGridPage.SortOrder.ASC.equals(sortOrder) ? s1.compareTo(s2) : s2.compareTo(s1));
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
}
