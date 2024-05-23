package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page;

public class ScriptMaintenanceClamshellPageElements
{
    private static final String GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE = "//toolbox-script-sidenav-functions-list//mat-tree-node[@role='group' and @aria-expanded='false']/button[@aria-label='toggle %s']";
    private static final String GENERIC_EXPANDED_CLAMSHELL_MENU_PANE = "//toolbox-script-sidenav-functions-list//mat-tree-node[@role='group' and @aria-expanded='true']/button[@aria-label='toggle %s']";
    private static final String GENERIC_CLAMSHELL_MENU_BUTTON = "//toolbox-script-sidenav-functions-list//mat-tree-node[@role='treeitem']/button[contains(text(),'%s')]";
    private static final String GENERIC_CLAMSHELL_MENU_SPAN = "//toolbox-script-sidenav-functions-list//mat-tree-node[@role='treeitem']/span[contains(text(),'%s')]";

    public static final String EDIT_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "Edit");
    public static final String EDIT_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "Edit");
    public static final String EDIT_SCRIPT_RULES_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Edit Script Rules");
    public static final String EDIT_SCRIPT_RULES_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Edit Script Rules");

    public static final String VIEW_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "View");
    public static final String VIEW_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "View");
    public static final String VIEW_DESCRIPTION_CONTENT_SETS_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "View Description/Content Sets");
    public static final String VIEW_DESCRIPTION_CONTENT_SETS_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "View Description/Content Sets");
    public static final String VIEW_PRINT_SCRIPT_RULES_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "View Print Script Rules");
    public static final String VIEW_PRINT_SCRIPT_RULES_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "View Print Script Rules");
    public static final String VIEW_PRINT_LIST_OF_SCRIPT_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "View Print List of Script");
    public static final String VIEW_PRINT_LIST_OF_SCRIPT_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "View Print List of Script"); //This is never used since the element in question is always enabled therefore it will always be a button and not a span

    public static final String CREATE_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "Create");
    public static final String CREATE_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "Create");
    public static final String CREATE_SCRIPT_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Create Script");
    public static final String CREATE_SCRIPT_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Create Script");
    public static final String CREATE_SCRIPT_VERSION_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Create Script Version");
    public static final String CREATE_SCRIPT_VERSION_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Create Script Version");
    public static final String CREATE_SCRIPT_VERSION_998_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Create Script Version 998");
    public static final String CREATE_SCRIPT_VERSION_998_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Create Script Version 998");

    public static final String COPY_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "Copy");
    public static final String COPY_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "Copy");
    public static final String COPY_SCRIPT_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Copy Script");
    public static final String COPY_SCRIPT_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Copy Script");
    public static final String COPY_SCRIPT_FOR_VERSION_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Copy Script for Version");
    public static final String COPY_SCRIPT_FOR_VERSION_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Copy Script for Version");

    public static final String DELETE_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "Delete");
    public static final String DELETE_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "Delete");
    public static final String DELETE_SCRIPT_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Delete Script");
    public static final String DELETE_SCRIPT_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Delete Script");

    public static final String MISC_CLAMSHELL_MENU_PANE_UNEXPANDED = String.format(GENERIC_UNEXPANDED_CLAMSHELL_MENU_PANE, "Misc");
    public static final String MISC_CLAMSHELL_MENU_PANE_EXPANDED = String.format(GENERIC_EXPANDED_CLAMSHELL_MENU_PANE, "Misc");
    public static final String CHANGE_DESCRIPTION_CONTENT_SETS_BUTTON = String.format(GENERIC_CLAMSHELL_MENU_BUTTON, "Change Description/Content Sets");
    public static final String CHANGE_DESCRIPTION_CONTENT_SETS_SPAN = String.format(GENERIC_CLAMSHELL_MENU_SPAN, "Change Description/Content Sets");
}
