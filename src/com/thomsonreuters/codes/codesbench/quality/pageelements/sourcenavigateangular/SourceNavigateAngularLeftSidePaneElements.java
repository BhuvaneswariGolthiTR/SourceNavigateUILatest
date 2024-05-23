package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;

public class SourceNavigateAngularLeftSidePaneElements
{
    public static final String SOURCE_NAV_FIND_TOOL_PANEL = "//source-nav-find-tool-panel";
    public static final String SIDE_BUTTON = SOURCE_NAV_RENDITIONS + "//button/span";
    public static final String SECTION_SIDE_BUTTON = SOURCE_NAV_SECTION + "//button/span";
    public static final String SECTION_GROUP_SIDE_BUTTON = SOURCE_NAV_SECTIONGROUPS + "//button/span";
    public static final String DELTA_SIDE_BUTTON = SOURCE_NAV_DELTAS + "//button/span";
    public static final String DELTA_GROUP_SIDE_BUTTON = SOURCE_NAV_DELTAGROUPS + "//button/span";
    private static final String LINEAGE_SIDE_BUTTON = SOURCE_NAV_LINEAGE_RENDITIONS + "//button/span";
    public static final String FIND_TEXT_FIELD_PATTERN = SOURCE_NAV_FIND_TOOL_PANEL + "/div/label[text()='%s ']/input";
    public static final String ASSIGN_USER_OPTION_DROPDOWN = "//div[contains(@class,'bento-list-row')]/div/div[contains(text(),'%s')]";
    public static final String SELECT_ALL_COLUMNS_CHECKBOX_STATUS = SOURCE_NAV_LINEAGE_RENDITIONS +
            "//input[@aria-label='Toggle Select All Columns']/parent::div";
    public static final String COLUMN_SEARCH_FIELD = "(//div/div/input[@class='ag-input-field-input ag-text-field-input'])[4]";
    public static final String COLUMN_SEARCH_COMMON = "(//div[@class='ag-wrapper ag-input-wrapper ag-text-field-input-wrapper']//input[@aria-label='Filter Columns Input'])[%d]";

    @FindBy(how = How.XPATH, using = SIDE_BUTTON + "[text()='Filters']")
    public static WebElement filtersLeftPaneButton;

    @FindBy(how = How.XPATH, using = SIDE_BUTTON + "[text()='Columns']")
    public static WebElement columnsLeftPaneButton;

    @FindBy(how = How.XPATH, using = SIDE_BUTTON + "[text()='Find']")
    public static WebElement findLeftPaneButton;

    @FindBy(how = How.XPATH, using = SOURCE_NAV_FIND_TOOL_PANEL + "//button[text()='Find']")
    public static WebElement findButton;

    @FindBy(how = How.XPATH, using = "//label[text()='Assigned To User']/following-sibling::source-nav-single-select-combobox/bento-combobox")
    public static WebElement assignedToUserDropdown;

    @FindBy(how = How.XPATH, using = SOURCE_NAV_LINEAGE_RENDITIONS + "//input[@aria-label='Toggle Select All Columns']")
    public static WebElement toggleSelectAllColumns;

    /* ------------------Column fields for all tabs--------------- */
    public static final String SECTION_COLUMNS = SECTION_SIDE_BUTTON + "[text()='Columns']";
    public static final String SECTION_GROUP_COLUMNS = SECTION_GROUP_SIDE_BUTTON + "[text()='Columns']";
    public static final String DELTA_COLUMNS = DELTA_SIDE_BUTTON + "[text()='Columns']";
    public static final String DELTA_GROUP_COLUMNS = DELTA_GROUP_SIDE_BUTTON + "[text()='Columns']";
    public static final String LINEAGE_COLUMNS = LINEAGE_SIDE_BUTTON + "[text()='Columns']";

}
