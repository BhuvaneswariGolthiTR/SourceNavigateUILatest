package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyContextMenuElements;

//This class extends HierarchyContextMenuElements because it uses the same elements as other context menus in the Hierarchy Edit page
public class DispositionDerivationContextMenuElements extends HierarchyContextMenuElements
{
    public static final String CONTEXT_MENU = "//div[@id='dispDerivGridContextMenu']";
    public static final String DELINK_PREVIOUS_NODE = CONTEXT_MENU + "//a[text()='Delink Previous Node']";
    public static final String DELETE_FUNCTIONS = CONTEXT_MENU + "//a[text()='Delete Functions']";
    public static final String DELETE_FUNCTIONS_DELETE = CONTEXT_MENU + "//a[text()='Delete']";
    public static final String DELETE_FUNCTIONS_UNDELETE = CONTEXT_MENU + "//a[text()='Undelete']";
    public static final String DELETE_FUNCTIONS_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Delete Functions']";
    public static final String DELETE_FUNCTIONS_DELETE_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Delete']";
    public static final String DELETE_FUNCTIONS_UNDELETE_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Undelete']";
    public static final String PUBLISHING_STATUS = CONTEXT_MENU + "//a[text()='Publishing Status']";
    public static final String SET_READY_STATUS = CONTEXT_MENU + "//a[text()='Set Publish Ready']";
    public static final String REFRESH_SELECTION = CONTEXT_MENU + "//a[text()='Refresh Selection']";
    public static final String REFRESH_SELECTION_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Refresh Selection']";
    public static final String UPDATE_METADATA = CONTEXT_MENU + "//a[text()='Update Metadata']";
    public static final String UPDATE_METADATA_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Update Metadata']";
    public static final String CLONE_AFTER = CONTEXT_MENU + "//a[text()='Clone After']";
    public static final String CLONE_AFTER_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Clone After']";
    public static final String CLONE_BEFORE = CONTEXT_MENU + "//a[text()='Clone Before']";
    public static final String CLONE_BEFORE_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Clone Before']";
    public static final String EDIT_CONTENT = CONTEXT_MENU + "//a[text()='Edit Content']";
    public static final String EDIT_CONTENT_GRAPH = "//div[@id='DispDerivGraphContextMenu']//a[text()='Edit Content']";
    public static final String VIEW_CONTENT = CONTEXT_MENU + "//a[text()='View Content']";
    public static final String VIEW_CONTENT_GRAPH ="//div[@id='DispDerivGraphContextMenu']//a[text()='View Content']";
    public static final String HIERARCHY_FUNCTIONS = CONTEXT_MENU + "//a[text()='Hierarchy Functions']";
    public static final String CHECK_NODE_VALIDATION_FLAGS = CONTEXT_MENU + "//a[text()='Check Node Validation Flags']";
    public static final String REVERIFY_FLAGS = CONTEXT_MENU + "//a[text()='Reverify Flags']";
    public static final String CREATE_NEW_VOLUME_XPATH = CONTEXT_MENU + "//a[text()='Create New Volume']";

    //This element won't exist in the context menu, but needs to be checked against
    public static final String XML_EXTRACT_STATE_FEED = CONTEXT_MENU + "//a[text()='XML Extract - State Feed']";
}
