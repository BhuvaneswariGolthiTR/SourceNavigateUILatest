package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SectionGroupingSectionGridPageElements
{
    public static final String SECTION_DELTA_GROUPING_GROUP_POSITION = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers, 'groupDisplayName')]/div";
    public static final String SECTION_DELTA_GROUPING_GROUP_NAME = "//div[@id='groups']//tbody[@class='yui-dt-data']//td[contains(@headers, 'groupDisplayName')]//div[contains(text(), '%s')]";
    public static final String SECTION_DELTA_GROUPING_REMOVE_GROUP_BUTTON = "//div[@id='toolbarContainer']//a[contains(text(), 'Remove Group')]";
    public static final String SECTION_DELTA_GROUPING_MOVE_UP = "//div[@id='toolbarContainer']//a[contains(text(), 'Move Up')]";
    public static final String YES_BUTTON= "//button[contains(text(),'Yes')]";
    public static final String SECTION_DELTA_GROUPING_APPLY_BUTTON= "//button[text()='Apply']";
    public static final String SECTION_DELTA_GROUPING_APPLY_AND_CLOSE_BUTTON= "//button[text()='Apply and close']";
    public static final String SECTION_DELTA_GROUPING_NEW_GROUP = "//div[@id='toolbarContainer']//a[contains(text(), 'New Group')]";
    public static final String SECTION_DELTA_GROUPING_NEW_GROUPNAME_INPUT = "//div[@id='newGroupDialogContainer']//input[@name='title']";
    public static final String SECTION_DELTA_GROUPING_SAVE_BUTTON = "//button[contains(text(), 'Save')]";
    public static final String SECTION_DELTA_GROUPING_GROUP_IS_SELECTED = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-selected')]/td[contains(@headers, 'groupDisplayName')]/div";
    public static final String SECTION_DELTA_GROUPING_GROUPNAME_IS_SELECTED = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-selected')]/td[contains(@headers, 'groupDisplayName')]/div[contains(text(), '%s')]";
    public static final String SECTION_DELTA_GROUPING_MOVE_DOWN = "//div[@id='toolbarContainer']//a[contains(text(), 'Move Down')]";
    public static final String SECTION_DELTA_GROUPING_SECTION_NUMBER = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'sectionNumber')]";
    public static final String SECTION_DELTA_GROUPING_HIGHLIGHTED_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s][contains(@class,'selected')]/td[contains(@headers, 'groupName')]";
    public static final String SECTION_GROUPING_MOVE_TO_GROUP = "//a[text()='Move to Group']";
    public static final String SECTION_GROUPING_MOVE_TO_GROUP_X = "//div[@id='groupSubmenu']//a[text()='%s']";
    public static final String SECTION_DELTA_GROUPING_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers, 'groupName')]";
    public static final String SECTIONS_DELTAS_FROM_SELECTED_GROUP_TAB = "//em[contains(text(), 'Deltas from selected group') or contains(text(), 'Sections from selected group')]";
    public static final String SECTION_DELTA_GROUPING_SECTION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr";
    public static final String SECTION_DELTA_GROUPING_SECTION_GROUPNAME_IN_COLUMN = "//td[contains(@headers, 'groupName')]/div";
    public static final String SECTION_DELTA_ALL_DELTAS_TAB = "//em[contains(text(), 'All deltas') or contains(text(), 'All sections')]";

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']")
    public static List<WebElement> sectionNumbers;

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber ']")
    public static List<WebElement> subSectionNumbers;

    public static final String FIRST_SECTION_NUMBER_XPATH = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']";

    @FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr")
    public static List<WebElement> sectionGridRows;

    @FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[contains(@class,'selected')]/td[contains(@headers, 'groupName')]")
    public static WebElement selectedSection;

    public static final String FIRST_SUBSECTION_NUMBER_XPATH = "//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber ']";

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-deltaCountFilter yui-dt9-th-deltaCount ']")
    public static List<WebElement> deltaCounts;

    public static final String FIRST_DELTA_COUNT_XPATH = "//td[@headers='yui-dt9-th-deltaCountFilter yui-dt9-th-deltaCount ']";

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-groupNameFilter yui-dt9-th-groupName ']")
    public static List<WebElement> groupNames;

    @FindBy(how = How.XPATH, using = "//a[text()='Move to Group']")
    public static WebElement moveToGroup;

    public static final String MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH = "//div[@id='groupSubmenu']//a[text()='%s']";

    //NOTE: This change (code added below) has been made in both this branch and in benManualToAutomated, both of these have this code that does not exist in Master. There should be no conflicts related to the below code when the two branches are merged into master
    public static final String SELECTED_GROUP_DELTA_COUNT = "//div[@id= 'groups']//div[@class= 'yui-dt-bd']/table//td[contains(@class, 'groupDisplayName')]/div[contains(text(), '%s')]/../../td[contains(@class, 'deltaCount')]/div";
    public static final String SELECTED_GROUP_SECTION_COUNT = "//div[@id= 'groups']//div[@class= 'yui-dt-bd']/table//td[contains(@class, 'groupDisplayName')]/div[contains(text(), '%s')]/../../td[contains(@class, 'sectionCount')]/div";
}
