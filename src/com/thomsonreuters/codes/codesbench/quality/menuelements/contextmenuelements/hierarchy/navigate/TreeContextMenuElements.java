package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyContextMenuElements;

//This class extends HierarchyContextMenuElements because it uses the same elements as other context menus in the Hierarchy Edit page
public class TreeContextMenuElements extends HierarchyContextMenuElements
{
    public static final String EDIT_CONTENT_AS_CHUNKED = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Edit Content as Chunked']";
    public static final String VIEW_VOLUME_INFO = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Volume Info']";
    public static final String CLASSIFY_IN_CHC = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Classify in CHC']";
    public static final String CLASSIFY_DESCENDANTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Classify Descendants']";
    public static final String HIERARCHY_FUNCTIONS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Hierarchy Functions']";
    public static final String VALIDATION_FLAGS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Validation Flags']";
    public static final String DELETE_FUNCTIONS = "//div[contains(@id,'treecontextmenu')]//a[text()='Delete Functions']";
    public static final String DELETE_FUNCTIONS_DELETE = "//a[text()='Delete']";
    public static final String DELETE_FUNCTIONS_UNDELETE = "//a[text()='Undelete']";
    public static final String QUERIES = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Queries']";
    public static final String VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Online Product Node Queries']";
    public static final String SCRIPTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Scripts']";
    public static final String ONLINE_PRODUCT_ASSIGNMENTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Online Product Assignments']";
    public static final String PUBLISHING_WORKFLOWS_XPATH = "//div[@id='mytreecontextmenu']//a[text()='Publishing Workflows']";
    public static final String RUN_SEARCH_AND_REPLACE = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Run Search & Replace']";
    public static final String SHOW_GROUP = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Show Group']";
    public static final String MANAGE_GLOSSARY_TERMS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Manage Glossary Terms']";
    public static final String CREATE_BOOKMARK = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Create Bookmark']";
    public static final String SEND_NODE_AND_CHILDREN_TO_BERMUDA = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Send Node and Children to Bermuda']";
    public static final String OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Output Republish Rulebook by Hierarchy']";
    public static final String OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Output Republish by Hierarchy']";
    public static final String OUTPUT_REPUBLISH_BY_VOLUME_XPATH = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Output Republish by Volume']";
    public static final String WESTMATE_TOC_XPATH = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='WestMate TOC']";
    public static final String WESTMATE_HIGH_TOC_XPATH = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='WestMate High TOC']";
    public static final String BULK_PUBLISH_BY_HIERARCHY = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Bulk Publish by Hierarchy']";
    public static final String BULK_PUBLISH_RULEBOOK_BY_HIERARCHY = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Bulk Publish Rulebook by Hierarchy']";
    public static final String BULK_PUBLISH_BY_VOLUME = "//div[@id='navTreeContextMenupublishingWorkflows']//a[text()='Bulk Publish by Volume']";
    public static final String CREATE_NEW_VOLUME_XPATH = "//div[contains(@id,'treecontextmenu')]//a[text()='Create New Volume']";
    //This element won't exist in the context menu, but needs to be checked against
    public static final String XML_EXTRACT_STATE_FEED = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='XML Extract - State Feed']";

}
