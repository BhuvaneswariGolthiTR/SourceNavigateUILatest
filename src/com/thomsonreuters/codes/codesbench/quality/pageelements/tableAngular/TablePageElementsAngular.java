package com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TablePageElementsAngular {

    //this is in fact not a table, but the center portion of it - until no columns are pinned. We'll see if we'll have to change it
    public static final String TABLE = "(//div[@class='ag-center-cols-container'])";
    public static final String OC_PUB_FILES_TABLE = TABLE + "[2]";
    public static final String CHILD = "./child::div";
    public static final String ANCESTOR_ROW = "/ancestor::div[@role = 'row']";
    public static final String ROW_INDEX = "row-index";
    public static final String COLUMN_ID = "col-id";
    public static final String SORTING_ORDER = "./parent::div[@class='ag-header-cell-label']/span[@ref = '%s']";
    public static final String ASCENDING_ORDER = "eSortAsc";
    public static final String DESCENDING_ORDER = "eSortDesc";
    public static final String GRIDCELL_BY_COLUMN_AND_VALUE = "//div[@role = 'gridcell' and @col-id = '%s']/a[contains(text(),'%s')]";
    public static final String CASESERIAL_GRIDCELL_BY_COLUMN_AND_VALUE = "//div[@role = 'gridcell' and @col-id = '%s']/a[contains(text(),'%s')]";
    public static final String NOTES_GRIDCELL_BY_COLUMN_AND_TITLE = "//div[@role = 'gridcell' and @col-id = '%s']/em[@title='%s']";
    public static final String NOTES_CREATE_OR_EDIT_BUTTON = ".//em[@title='%s']";
    public static final String NOTES_BUTTON = "./em";
    public static final String FIRST_GRIDCELL = TABLE + "/div[1]/div[1][@role='gridcell']";
    //Column headers
    public static final String COLUMN_HEADER_CELL = "div[contains(@class, 'ag-cell-label-container')]";
    public static final String COLUMN_CONTROL_BUTTON = "//span[@class = 'ag-header-icon ag-header-cell-menu-button']";
    public static final String COLUMN_HEADER_WITH_NAME = "//span[@class = 'ag-header-cell-text' and text()='%s']";
    public static final String COLUMN_HEADER = "//span[@class = 'ag-header-cell-text']";
    public static final String CONTROL_BUTTON_ANCESTOR = "./ancestor::" + COLUMN_HEADER_CELL + COLUMN_CONTROL_BUTTON;
    public static final String FILTER_ICON = "./following-sibling::span[@ref='eFilter']";
    public static final String COLUMN_POSITION = "aria-colindex";
    //loading
    public static final String LOADING_MESSAGE = "//div[@class='ag-stub-cell']/span[@class='ag-loading-text']";
    public static final String TABLE_ROW = TABLE +  "//div[@role='row' and @row-index='%s']";

    public static final String HIDDEN_OVERLAY_PANEL_XPATH = "//div[@class='ag-overlay ag-hidden' and @aria-hidden = 'true']";
    public static final String OVERLAY_PANEL_XPATH = "//span[@class='ag-loading-text']";
    //toolbar elements
    public static final String CLEAR_FILTERS = "//div[contains(@class, 'header-background')]//button[text() = 'Clear Filters']";
    @FindBy(how = How.XPATH, using = CLEAR_FILTERS)
    public static WebElement clearFiltersButton;
}

