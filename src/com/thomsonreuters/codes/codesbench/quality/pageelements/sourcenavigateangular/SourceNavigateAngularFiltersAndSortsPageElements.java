package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceNavigateAngularFiltersAndSortsPageElements
{
    public static final String COLUMN_FILTER_BUTTON_PATTERN = "//source-nav-renditions//div[@col-id='%s']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_BUTTON_PATTERN_SECTION_TAB = "//source-nav-sections//div[@col-id='renditionStatus']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_SECTIONNUMBER_PATTERN_SECTION_TAB = "//source-nav-sections//div[@col-id='sectionNumber']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_BUTTON_PATTERN_LINEAGE_TAB = "//source-nav-lineage-renditions//div[@col-id='contentType']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_BUTTON_PATTERN_SECTION_GROUP_TAB = "//source-nav-section-groups//div[@col-id='renditionStatus']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_BUTTON_PATTERN_DELTA_TAB = "//source-nav-deltas//div[@col-id='renditionStatus']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_BUTTON_PATTERN_DELTA_GROUP_TAB = "//source-nav-delta-groups//div[@col-id='renditionStatus']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_FILTER_SECTIONNUMBER_PATTERN_DELTA_TAB = "//source-nav-deltas//div[@col-id='sectionNumber']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String COLUMN_MENU_FILTERS = "//div[@role='tablist']/span[@aria-label='filter']";
    public static final String COLUMN_FILTER_ICON = "//div[@col-id='%s']//span/span[@class='ag-icon ag-icon-filter']";

    public static final String ENABLED_FILTER_CHECKBOX_PATTERN = "//span[text()='%s']/ancestor::div[@ref='eCheckbox']/div[contains(@class,'ag-checked')]";
    public static final String FILTER_CHECKBOX_PATTERN = "//span[text()='%s']/ancestor::div[@ref='eCheckbox']/div";
    public static final String FILTER_RENDITION_STATUS  ="(//input[contains(@class,'ag-input-field-input ag-text-field-input')])[5]";

    public static final String SELECT_ALL = "(Select All)";
    public static final String BLANKS = "(Blanks)";
    public static final String SECTION_NUMBER_FILTER_VALUE="//source-nav-sections//input[@placeholder='Filter...'][1]";
    public static final String SECTION_NUMBER_FILTER_VALUE_DELTA_TAB="//source-nav-deltas//input[@placeholder='Filter...'][1]";
    public static final String APV_RENDITION_STATUS = "APV";

    public static final String MULTIPLE_FLAG = "Multiple";
    public static final String DUPLICATE_FLAG = "Duplicate";

    public static final String COLUMN_FILTER_BUTTON_INTERGRATION = "//source-nav-integration-results//ag-grid-angular//div[@col-id='sectionNumber']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String FILTER_SECTION_NUMBER_INTERGRATION_RESULT = "(//div[contains(@class,'ag-filter-body')]//input[contains(@class,'ag-input-field-input ag-text-field-input')])[1]";
    public static final String INTERGRATION_SECTION_NUMBER="//span[contains(@class,'ag-tab ag-tab-selected')]/span";

    @FindBy(how = How.XPATH, using = "//input[@id='docNumberFilter']")
    public static WebElement docNumberFilter;

    @FindBy(how = How.XPATH, using = "//source-nav-renditions//div[@col-id='flag']//span[@class='ag-header-icon ag-header-cell-menu-button']/span")
    public static WebElement errorFlag;
}
