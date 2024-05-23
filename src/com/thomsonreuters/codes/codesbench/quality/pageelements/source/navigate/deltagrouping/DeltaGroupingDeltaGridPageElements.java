package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DeltaGroupingDeltaGridPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr")
    public static List<WebElement> deltaGridRows;

    @FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[contains(@class,'selected')]/td[contains(@headers, 'groupName')]")
    public static WebElement selectedDelta;

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']")
    public static List<WebElement> sectionNumbers;

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-groupNameFilter yui-dt9-th-groupName ']")
    public static List<WebElement> groupNames;

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-deltaLevelFilter yui-dt9-th-deltaLevel ']")
    public static List<WebElement> deltaLevels;

    @FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-actionFilter yui-dt9-th-action ']")
    public static List<WebElement> actions;

    @FindBy(how = How.XPATH, using = "//a[text()='Move to Group']")
    public static WebElement moveToGroup;

    public static final String FIRST_SECTION_NUMBER_XPATH = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']";

    public static final String MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH = "//div[@id='groupSubmenu']//a[text()='%s']";

    public static final String SECTION_NUMBER_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber '])[%s]";

    public static final String SUBSECTION_NUMBER_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber '])[%s]";

    public static final String ACTION_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-actionFilter yui-dt9-th-action '])[%s]";

    public static final String DELTA_LEVEL_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-deltaLevelFilter yui-dt9-th-deltaLevel '])[%s]";
}
