package com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups;

public class FilterPopupElementsAngular
{

    public static final String FILTER_POP_UP = "//div[@class = 'ag-menu ag-ltr']";
    public static final String FILTER_POP_UP_ALTERNATE = "//div[@aria-label = 'Column Menu']";
    public static final String FIRST_TAB = "//span[@class='ag-icon ag-icon-menu']/parent::span[contains(@class, 'ag-tab')]";
    public static final String SECOND_TAB = "//span[@class='ag-icon ag-icon-filter']/parent::span[contains(@class, 'ag-tab')]";
    public static final String THIRD_TAB = "//span[@class='ag-icon ag-icon-columns']/parent::span[contains(@class, 'ag-tab')]";
    public static final String TAB_SELECTED = "ag-tab-selected";

    //SECOND TAB
    public static final String FIRST_FILTER_CONDITION = "//select[@class='ag-filter-select' and @ref='eOptions1']";
    public static final String SECOND_FILTER_CONDITION = "//select[@class='ag-filter-select' and @ref='eOptions2']";
    public static final String JOIN_OPERATORS_PANEL = "//div[contains(@class, 'ag-filter-condition') and @ref='eJoinOperatorPanel']";
    public static final String AND = JOIN_OPERATORS_PANEL + "/label/input[@ref='eJoinOperatorAnd']";
    public static final String OR = JOIN_OPERATORS_PANEL + "/label/input[@ref='eJoinOperatorOr']";
    public static final String FIRST_INPUT_FIELD = "//div[@class='ag-filter-body' and @ref='eCondition1Body']";
    public static final String FIRST_INPUT_FIELD_FROM = FIRST_INPUT_FIELD + "//div[1]/input";
    public static final String FIRST_INPUT_FIELD_TO = FIRST_INPUT_FIELD + "//div[2]/input";
    public static final String SECOND_INPUT_FIELD = "//div[@class='ag-filter-body' and @ref='eCondition2Body']";
    public static final String SECOND_INPUT_FIELD_FROM = SECOND_INPUT_FIELD + "//div[1]/input";
    public static final String SECOND_INPUT_FIELD_TO = SECOND_INPUT_FIELD + "//div[2]/input";
    public static final String DATE_PICKER_FROM = "//div[@ref='ePanelFrom1']//input";
    public static final String DATE_PICKER_TO = "//div[@ref='ePanelTo1']//input";
    public static final String OPEN_CALENDAR_BUTTON = "//button[@title='Open calendar']";
    public static final String FIRST_OPEN_CALENDAR_BUTTON_FROM = "//div[@ref='%s']" + OPEN_CALENDAR_BUTTON;
    public static final String SECOND_OPEN_CALENDAR_BUTTON_FROM = "//div[@ref='%s']" + OPEN_CALENDAR_BUTTON;

    //THIRD TAB
    public static final String COLUMNS_LIST = "//div[@class='ag-primary-cols-list-panel']";
    public static final String COLUMN_NAME = COLUMNS_LIST + "//span[@class='ag-column-tool-panel-column-label' and text()='%s']";
    public static final String CHECKBOX = COLUMN_NAME + "/parent::div/div[contains(@class, 'ag-column-select-checkbox')]";
    public static final String SELECT_ALL ="//div[@ref='eSelect']";

    //CALENDAR
    public static final String DATE_PICKER = "//ngb-datepicker[contains(@class, 'bento-datepicker')]";
    public static final String PICKER_WIDGET = "//div[@ref='%s']//div[@class='picker-container']" + DATE_PICKER;

    //
    public static final String APPLY_FILTER_BUTTON = "//button[text()='Apply Filter']";
}
