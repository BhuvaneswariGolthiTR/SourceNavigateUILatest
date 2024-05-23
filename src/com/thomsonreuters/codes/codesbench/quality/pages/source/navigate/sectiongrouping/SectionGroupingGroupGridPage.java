package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingGroupGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingSectionGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;

@Component
public class SectionGroupingGroupGridPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingGroupGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingGroupGridPageElements.class);
    }

    public boolean doesGroupExist(String groupName)
    {
        return isElementDisplayed(String.format(SectionGroupingGroupGridPageElements.SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    public void clickGroup(String groupName)
    {
        click(String.format(SectionGroupingGroupGridPageElements.SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
        waitForElement(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUPNAME_IS_SELECTED, groupName));
    }

    public boolean checkGroupPosition(String groupname, String position)
    {
        return getElementsText(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUP_POSITION, position)).equals(groupname);
    }

    public void sendEnterToGroup()
    {
        sendEnterToElement(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_NEW_GROUP);
    }

    public void addNewGroup(String groupname)
    {
        sendKeysToElement(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_NEW_GROUPNAME_INPUT, groupname);
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SAVE_BUTTON);
    }

    public void deleteGroupThroughDatabase(String groupName, String level, String uuid)
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        if(level.equals("Section"))
        {
            SourceDatabaseUtils.deleteSectionGroup(connection, uuid, groupName);
        }
        else if (level.equals("Delta"))
        {
            SourceDatabaseUtils.deleteDeltaGroup(connection, uuid, groupName);
        }

        BaseDatabaseUtils.disconnect(connection);
    }

    public boolean checkGroupSelection(String groupName)
    {
        return doesElementExist(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUPNAME_IS_SELECTED, groupName));
    }

    public boolean acceptedDuplicateGroupNameError()
    {
        return AutoITUtils.verifyAlertTextAndAccept(true,"Group with specified name already exists!");
    }

    public void moveGroupUp(String groupname)
    {
        click(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUP_NAME, groupname));
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_MOVE_UP);
    }

    public void moveGroupDown(String groupname)
    {
        click(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUP_NAME, groupname));
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_MOVE_DOWN);
    }

    public void rightClickGroupName(String groupname)
    {
        rightClick(String.format(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_GROUP_NAME, groupname));
    }

    public void rightClickGroupingOnPosition(String position)
    {
        rightClick(String.format(SectionGroupingPageElements.SECTION_DELTA_GROUPING_GROUP_ON_POSITION,position));
    }

    public boolean doesHighlightedGroupOnPositionExist(String position)
    {
        return doesElementExist(String.format(SectionGroupingPageElements.SECTION_DELTA_GROUPING_HIGHLIGHTED_GROUP_ON_POSITION,position));
    }

    public void clickGroupOnPosition(String position)
    {
        click(String.format(SectionGroupingPageElements.SECTION_DELTA_GROUPING_GROUP_ON_POSITION,position));
    }

    public boolean checkDeltaSectionGroupOnPosition(String expectedGroupname, int rowNumber)
    {
        return getElement(String.format(SectionGroupingPageElements.SECTION_DELTA_GROUPING_GROUP_ON_POSITION, rowNumber))
                .getText().equals(expectedGroupname);
    }

    public boolean checkSelectedGroupText(String groupName)
    {
        return getElement(SectionGroupingPageElements.SECTION_DELTA_GROUPING_GROUP_IS_SELECTED).getText().equals(groupName);
    }

    public void groupRename(String newGroupName)
    {
        sendTextToTextbox(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_NEW_GROUPNAME_INPUT, newGroupName);
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_SAVE_BUTTON);
    }

    public String getSectionCountForGroup(String groupName)
    {
        return tableTestingPage().getCellValue(TableTestingPage.GroupNavigateColumns.SECTION_COUNT,tableTestingPage().getRowByText(TableTestingPage.GroupNavigateColumns.GROUP_NAME,groupName)-1);
    }

    public Boolean getSectionNumberContainsGroupForRow(String group, int row)
    {
        return tableTestingPage().getCellValue(TableTestingPage.GroupingColumns.SECTION_NUMBER,row).contains(group);
    }
}
