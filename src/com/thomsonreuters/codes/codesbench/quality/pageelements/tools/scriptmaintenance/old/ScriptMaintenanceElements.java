package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.old;

public class ScriptMaintenanceElements
{
    public static final String SCRIPT_MAINTENANCE_PAGE_TITLE = "Toolbox";

    public static final String PROGRESS_BAR = "//mat-progress-bar";

    public static final String HIDE_DELETED = "//mat-checkbox[@formcontrolname='hideDeleted']";
    public static final String HIDE_DELETED_CHECKBOX = "//mat-checkbox[@formcontrolname='hideDeleted']//input[@type='checkbox']";

    //Functions
    public static final String EDIT_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[./button[@aria-label='toggle Edit']]";
    public static final String EDIT_SCRIPT_RULES_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Edit Script Rules ']";
    public static final String EDIT_SCRIPT_RULES_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Edit Script Rules ']";

    public static final String VIEW_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[./button[@aria-label='toggle View']]";
    public static final String VIEW_DESCRIPTION_CONTENTSET_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' View Description/Content Sets ']";
    public static final String VIEW_DESCRIPTION_CONTENTSET_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' View Description/Content Sets ']";
    public static final String VIEW_PRINT_SCRIPT_RULES_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' View Print Script Rules ']";
    public static final String VIEW_PRINT_SCRIPT_RULES_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' View Print Script Rules ']";

    public static final String CREATE_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[./button[@aria-label='toggle Create']]";
    public static final String CREATE_SCRIPT_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//*[text() = ' Create Script ']";
    public static final String CREATE_SCRIPT_VERSION_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Create Script Version ']";
    public static final String CREATE_SCRIPT_VERSION_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Create Script Version ']";
    public static final String CREATE_SCRIPT_VERSION_998_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Create Script Version 998 ']";
    public static final String CREATE_SCRIPT_VERSION_998_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Create Script Version 998 ']";

    public static final String COPY_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[./button[@aria-label='toggle Copy']]";
    public static final String COPY_SCRIPT_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Copy Script ']";
    public static final String COPY_SCRIPT_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Copy Script ']";
    public static final String COPY_SCRIPT_FOR_VERSION_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Copy Script for Version ']";
    public static final String COPY_SCRIPT_FOR_VERSION_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Copy Script for Version ']";

    public static final String DELETE_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[./button[@aria-label='toggle Delete']]";
    public static final String DELETE_SCRIPT_FUNCTION_SPAN = "//toolbox-script-sidenav-functions-list//span[text() = ' Delete Script ']";
    public static final String DELETE_SCRIPT_FUNCTION_BUTTON = "//toolbox-script-sidenav-functions-list//button[text() = ' Delete Script ']";

    //Body
    //Scripts Table
    public static final String SCRIPTS_TABLE = "//div[@role='grid']";

    public static final String SCRIPTS_TABLE_HEADER = SCRIPTS_TABLE + "/div[@ref='headerRoot']";
    public static final String SCRIPTS_TABLE_COLUMN_TITLE = SCRIPTS_TABLE_HEADER + "//span[@role='columnheader' and text()='%s']/ancestor::div[@class='ag-header-cell']";

    public static final String SCRIPTS_TABLE_BODY = SCRIPTS_TABLE + "//div[@ref='eBodyContainer']";
    public static final String SCRIPTS_TABLE_ROWS = SCRIPTS_TABLE_BODY + "/div[@role='row']";
    public static final String SCRIPTS_TABLE_ROW_BY_NUM = SCRIPTS_TABLE_ROWS + "[@row-index='%d']";
    public static final String SCRIPTS_TABLE_ROW_BY_VALUE = SCRIPTS_TABLE_ROWS + "[./div[text()='%s']]";

    public static final String SCRIPTS_TABLE_ROW_PUBTAG = SCRIPTS_TABLE_ROW_BY_NUM + "/div[@col-id='pubtag']";
    public static final String SCRIPTS_TABLE_ROW_DESCRIPTION = SCRIPTS_TABLE_ROW_BY_NUM + "/div[@col-id='0']";
    public static final String SCRIPTS_TABLE_ROW_SCRIPT = SCRIPTS_TABLE_ROW_BY_NUM + "/div[@col-id='1']";
    public static final String SCRIPTS_TABLE_ROW_STATUS = SCRIPTS_TABLE_ROW_BY_NUM + "/div[@col-id='status.status']";

    //Script Details Form
    public static final String SCRIPTS_DETAILS_FORM = "//toolbox-script-details-form";
    public static final String SCRIPTS_DETAILS_SCRIPT_NAME = SCRIPTS_DETAILS_FORM + "//input[@placeholder='Script Name']";
    public static final String SCRIPTS_DETAILS_VERSION_DESCRIPTION = SCRIPTS_DETAILS_FORM + "//input[@placeholder='Version Description']";
    public static final String SCRIPTS_DETAILS_PUB_TAG = SCRIPTS_DETAILS_FORM + "//input[@placeholder='Pub Tag']";

    public static final String CONTENT_SETS_AREA = SCRIPTS_DETAILS_FORM + "//toolbox-collector[@ng-reflect-collector-title='Content Sets']";
    public static final String SELECTED_CONTENT_SETS_LIST = "(" + CONTENT_SETS_AREA + "//mat-selection-list)[2]";
    public static final String CONTENT_SET_LABEL = "//div[@class='mat-list-text']";
}
