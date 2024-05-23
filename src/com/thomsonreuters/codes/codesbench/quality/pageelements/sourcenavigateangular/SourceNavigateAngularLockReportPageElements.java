package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceNavigateAngularLockReportPageElements
{

    public static final String AG_GRID_ANGULAR = "/ag-grid-angular";
    public static final String LOCK_REPORT_COLUMN_ID_PATTERN = "//div[@col-id='%s']";
    public static final String FILTER_ICON_ON_COLUMN_PATTERN = LOCK_REPORT_COLUMN_ID_PATTERN + "//span[@class='ag-icon ag-icon-filter']";
    public static final String COLUMN_FILTER_BUTTON_PATTERN = LOCK_REPORT_COLUMN_ID_PATTERN + "//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String LOADING_PLATE = "//span[text()='Loading...']";
    public static final String SPAN_CONTAINS_TEXT = "//span[contains(text(),'%s')]";
    public static final String SPAN_TEXT = "//span[text()='%s']";
    public static final String SPAN_COLUMN_OPTIONS = "//span[@class='ag-icon ag-icon-menu']";
    public static final String CLEAR_FILTERS = "//button[text()='Clear Filters']";
    public static final String COLUMN_MENU_FILTERS = "//div[@role='tablist']/span[@aria-label='filter']";
    public static final String SOURCE_NAV_LOCKED_RENDITIONS = "//source-nav-source-lock-report";
    public static final String SOURCE_NAV_LOCKED_RENDITIONS_AG_GRID = SOURCE_NAV_LOCKED_RENDITIONS + AG_GRID_ANGULAR;
    public static final String FIRST_LOCKED_RENDITION_ROW = "(" + SOURCE_NAV_LOCKED_RENDITIONS_AG_GRID + "//div[@row-id='0'])[2]";
    public static final String LOCK_REPORT_RENDITION_ROW_PATTERN = "(" + SOURCE_NAV_LOCKED_RENDITIONS_AG_GRID + "//div[@row-id='%s'])[2]";
    public static final String TOTAL_LOCKS_NUMBER = "//span[contains(text(),' Total locks: %s')]";
    public static final String CLEAR_FILTERS_LOCKS = SOURCE_NAV_LOCKED_RENDITIONS + CLEAR_FILTERS;
    public static final String REFRESH_TABLE_DATA = "(//button[@ngbtooltip='Refresh table data']/em[@class='bento-icon-refresh'])";
    public static final String FILTER_INPUT_FIELD = "(//div/input[@class='ag-input-field-input ag-text-field-input'])[1]";
    public static final String LOCKED_ROWS = "//div[@class='ag-center-cols-container']/div";
    public static final String CONTEXT_MENU_OPTION = "//span[@ref='eName' and text()='%s']";
    public static final String CONTEXT_MENU_OPTION_PARENT = CONTEXT_MENU_OPTION + "//parent::div";
    public static final String COLUMN_FILTER_BUTTON_LOCK_PATTERN = "//source-nav-source-lock-report//div[@col-id='%s']//span[@class='ag-header-icon ag-header-cell-menu-button']/span";
    public static final String CONTENT_SET = "contentSetLongName";
    public static final String LOCK_REPORT_RENDITION_STATUS = "renditionStatus";
    public static final String FIRST_CONTENT_SET = "(//div[text()='%s'])[1]";
    public static final String FIRST_RENDITION_STATUS="(//div[text()='%s'])[1]";
}
