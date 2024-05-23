package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyContextMenuPageElements
{
	// Check Out - Carswell specific
	public static final String CHECK_OUT = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Check Out']";
	// Edit Content
	public static final String EDIT_CONTENT_OLD = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Edit Content-Old']";
	// Edit Content as Chunked
	public static final String EDIT_CONTENT_AS_CHUNKED_OLD = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Edit Content as Chunked-Old']";
	//Edit Rendition with DS
	public static final String EDIT_CONTENT = "//div[@id='siblingMetadataContextMenu']//li/a[text()='Edit Content']";
	// View Content
	public static final String VIEW_CONTENT_OLD = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Content-Old']";
	// Refresh Selection
	public static final String REFRESH_SELECTION = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Refresh Selection']";
	// View Metadata
	public static final String VIEW_METADATA = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Metadata']";
	// Update Metadata
	public static final String UPDATE_METADATA = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Update Metadata']";
	// View/Modify Previous WIP Version
	public static final String VIEW_MODIFY_PREVIOUS_WIP_VERSION = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View/Modify Previous WIP Version']";
	// View Volume Info
	public static final String VIEW_VOLUME_INFO = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Volume Info']";
	// Classify in CHC
	public static final String CLASSIFY_IN_CHC = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Classify in CHC']";
	// Classify Descendants
	public static final String CLASSIFY_DESCENDANTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Classify Descendants']";
	// Hierarchy Functions
	public static final String HIERARCHY_FUNCTIONS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Hierarchy Functions']";
	// Hierarchy Functions -> Insert in Hierarchy
	public static final String HIERARCHY_FUNCTIONS_INSERT_IN_HIERARCHY = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Insert in Hierarchy']";
	/// Hierarchy Functions -> Input as child
	public static final String HIERARCHY_FUNCTIONS_INPUT_AS_CHILD = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Input as Child']";
	/// Hierarchy Functions -> Input as sibling
	public static final String HIERARCHY_FUNCTIONS_INPUT_AS_SIBLING = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Input as Sibling']";
	// Hierarchy Functions -> Add To Range
	public static final String HIERARCHY_FUNCTIONS_ADD_TO_RANGE = "//div[contains(@id,'ContextMenuhierarchyFunctions') or contains(@id,'ContextMenuMultipleSelectionhierarchyFunctions')]//a[text()='Add To Range']";
	// Hierarchy Functions -> Remove From Range
	public static final String HIERARCHY_FUNCTIONS_REMOVE_FROM_RANGE = "//div[contains(@id,'ContextMenuhierarchyFunctions') or contains(@id,'ContextMenuMultipleSelectionhierarchyFunctions')]//a[text()='Remove From Range']";
	// Hierarchy Functions -> Remove From Range (Clone Range)
	public static final String HIERARCHY_FUNCTIONS_REMOVE_FROM_RANGE_CLONE_RANGE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Remove From Range (Clone Range)']";
	// Hierarchy Functions -> Clone After
	public static final String HIERARCHY_FUNCTIONS_CLONE_AFTER = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Clone After']";
	// Hierarchy Functions -> Clone Before
	public static final String HIERARCHY_FUNCTIONS_CLONE_BEFORE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Clone Before']";
	// Hierarchy Functions -> Deep Clone
	public static final String HIERARCHY_FUNCTIONS_DEEP_CLONE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Deep Clone']";
	// Hierarchy Functions -> Repeal
	public static final String HIERARCHY_FUNCTIONS_REPEAL = "//div[contains(@id,'ContextMenuhierarchyFunctions') or contains(@id,'ContextMenuMultipleSelectionhierarchyFunctions')]//a[text()='Repeal']";
	// Hierarchy Functions -> Reserve
	public static final String HIERARCHY_FUNCTIONS_RESERVE = "//div[contains(@id,'ContextMenuhierarchyFunctions') or contains(@id,'ContextMenuMultipleSelectionhierarchyFunctions')]//a[text()='Reserve']";
	// Hierarchy Functions -> Reuse
	public static final String HIERARCHY_FUNCTIONS_REUSE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Reuse']";
	// Hierarchy Functions -> Reorder Children
	public static final String HIERARCHY_FUNCTIONS_REORDER_CHILDREN = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Reorder Children']";
	// Hierarchy Functions -> Target Tag
	public static final String HIERARCHY_FUNCTIONS_TARGET_TAG = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Target Tag']";
	// Hierarchy Functions -> Update Alternate Cite
	public static final String HIERARCHY_FUNCTIONS_UPDATE_ALTERNATE_CITE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Update Alternate Cite']";
	// Hierarchy Functions -> Include in Short Cite
	public static final String HIERARCHY_FUNCTIONS_INCLUDE_IN_SHORT_CITE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Include in Short Cite']";
	// Hierarchy Functions -> Exclude from Short Cite
	public static final String HIERARCHY_FUNCTIONS_EXCLUDE_FROM_SHORT_CITE = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Exclude from Short Cite']";
	// Hierarchy Functions -> Change Volume Number(descendants)
	public static final String HIERARCHY_FUNCTIONS_CHANGE_VOLUME_NUMBER_DESCENDANTS = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Change Volume Number(descendants)']";
	// Hierarchy Functions -> Change End Date(descendants)
	public static final String HIERARCHY_FUNCTIONS_CHANGE_END_DATE_DESCENDANTS = "//div[contains(@id,'ContextMenuhierarchyFunctions')]//a[text()='Change End Date(descendants)']";
	// Bin Functions
	public static final String BIN_FUNCTIONS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Bin Functions']";
	// Bin Functions -> Add to Bin
	public static final String BIN_FUNCTIONS_ADD_TO_BIN = "//a[text()='Add to Bin']";
	// Bin Functions -> View Bin
	public static final String BIN_FUNCTIONS_VIEW_BIN = "//a[text()='View Bin']";
	// Bin Functions -> Empty Bin
	public static final String BIN_FUNCTIONS_EMPTY_BIN = "//a[text()='Empty Bin']";
	// Bin Functions -> Add Bin as Child
	public static final String BIN_FUNCTIONS_ADD_BIN_AS_CHILD = "//a[text()='Add Bin as Child']";
	// Bin Functions -> Add Bin as Sibling
	public static final String BIN_FUNCTIONS_ADD_BIN_AS_SIBLING = "//a[text()='Add Bin as Sibling']";
	// Bin Functions -> Copy Bin as Child
	public static final String BIN_FUNCTIONS_COPY_BIN_AS_CHILD = "//a[text()='Copy Bin as Child']";
	// Bin Functions -> Copy Bin as Sibling
	public static final String BIN_FUNCTIONS_COPY_BIN_AS_SIBLING = "//a[text()='Copy Bin as Sibling']";
	// Bin Functions -> Deep Copy Bin as Child
	public static final String BIN_FUNCTIONS_DEEP_COPY_BIN_AS_CHILD = "//a[text()='Deep Copy Bin as Child']";
	// Bin Functions -> Move Bin as Child
	public static final String BIN_FUNCTIONS_MOVE_BIN_AS_CHILD = "//a[text()='Move Bin as Child']";
	// Bin Functions -> Move Bin as Sibling
	public static final String BIN_FUNCTIONS_MOVE_BIN_AS_SIBLING = "//a[text()='Move Bin as Sibling']";
	// Bin Functions -> Relocate Bin as Child
	public static final String BIN_FUNCTIONS_RELOCATE_BIN_AS_CHILD = "//a[text()='Relocate Bin as Child']";
	// Bin Functions -> Relocate Bin as Sibling
	public static final String BIN_FUNCTIONS_RELOCATE_BIN_AS_SIBLING = "//a[text()='Relocate Bin as Sibling']";
	// Bin Functions -> Link Bin Node Before
	public static final String BIN_FUNCTIONS_LINK_BIN_NODE_BEFORE = "//a[text()='Link Bin Node Before']";
	// Bin Functions -> Link Bin Node After
	public static final String BIN_FUNCTIONS_LINK_BIN_NODE_AFTER = "//a[text()='Link Bin Node After']";
	// Bin Functions -> Remove Bin as Child
	public static final String BIN_FUNCTIONS_REMOVE_BIN_AS_CHILD = "//a[text()='Remove Bin as Child']";
	// Validation Flags
	public static final String VALIDATION_FLAGS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Validation Flags']";
	// Validation Flags -> Reverify Flags
	public static final String VALIDATION_FLAGS_REVERIFY_FLAGS = "//a[text()='Reverify Flags']";
	// Validation Flags -> Check Node Validation Flags
	public static final String VALIDATION_FLAGS_CHECK_NODE_VALIDATION_FLAGS = "//a[text()='Check Node Validation Flags']";
	// Validation Flags -> Check Hierarchy Validation Flags
	public static final String VALIDATION_FLAGS_CHECK_HIERARCHY_VALIDATION_FLAGS = "//a[text()='Check Hierarchy Validation Flags']";
	// Validation Flags -> Count Descendants
	public static final String VALIDATION_FLAGS_COUNT_DESCENDANTS = "//a[text()='Count Descendants']";
	// Validation Flags -> Initiate Validation Workflow
	public static final String VALIDATION_FLAGS_INITIATE_VALIDATION_WORKFLOW = "//a[text()='Initiate Validation Workflow']";
	// Validation Flags -> Open Validation Flags Report
	public static final String VALIDATION_FLAGS_OPEN_VALIDATION_FLAGS_REPORT = "//a[text()='Open Validation Flags Report']";
	// Delete Functions
	public static final String DELETE_FUNCTIONS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Delete Functions']";
	// Delete Functions -> Delete
	public static final String DELETE_FUNCTIONS_DELETE = "//a[text()='Delete']";
	// Delete Functions -> Delete With Promote Children
	public static final String DELETE_FUNCTIONS_DELETE_WITH_PROMOTE_CHILDREN = "//a[text()='Delete With Promote Children']";
	// Delete Functions -> Undelete
	public static final String DELETE_FUNCTIONS_UNDELETE = "//a[text()='Undelete']";

	// Queries
	public static final String QUERIES = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Queries']";
	// Queries -> Insert Index Query
	public static final String QUERIES_INSERT_INDEX_QUERY = "//a[text()='Insert Index Query']";

	// Scripts
	public static final String SCRIPTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Scripts']";
	// Scripts -> View/Remove Assigned Scripts
	public static final String SCRIPTS_VIEW_REMOVE_ASSIGNED_SCRIPTS = "//div[contains(@id,'metadataGridContextMenuscripts')]/div/ul/li/a[text()='View/Remove Assigned Scripts']";
	// Scripts -> Add Scripts
	public static final String SCRIPTS_VIEW_ADD_SCRIPTS = "//div[contains(@id,'metadataGridContextMenuscripts')]/div/ul/li/a[text()='Add Scripts']";
	// Scripts -> Add Scripts High Level
	public static final String SCRIPTS_VIEW_ADD_SCRIPTS_HIGH_LEVEL = "//div[contains(@id,'metadataGridContextMenuscripts')]/div/ul/li/a[text()='Add Scripts High Level']";
	// Online Product Assignments
	public static final String ONLINE_PRODUCT_ASSIGNMENTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Online Product Assignments']";
	public static final String TAX_TYPE_ASSIGNMENTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Tax Type Assignments']";
	public static final String PUBLISHING_WORKFLOWS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Publishing Workflows']";
	// Publishing Workfows -> Bulk Publish by Hierarchy
	public static final String PUBLISHING_WORKFLOWS_BULK_PUBLISH_BY_HIERARCHY = "//div[contains(@id,'metadataGridContextMenupublishingWorkflows')]/div/ul/li/a[text()='Bulk Publish by Hierarchy']";

	// Publishing Workfows -> Bulk Publish by Hierarchy
	public static final String PUBLISHING_WORKFLOWS_BULK_PUBLISH_RULEBOOK_BY_HIERARCHY = "//a[text()='Bulk Publish Rulebook by Hierarchy']";
	// Publishing Workfows -> Bulk Publish by Hierarchy
	public static final String PUBLISHING_WORKFLOWS_PUBLISH_BY_HIERARCHY = "//div[contains(@id,'metadataGridContextMenupublishingWorkflows')]/div/ul/li/a[text()='Publish by Hierarchy']";
	// Publishing Workflows -> Bulk Publish by Hierarchy (for Multiple Selections)
	public static final String PUBLISHING_WORKFLOWS_BULK_PUBLISH_RULEBOOK_BY_HIERARCHY_MULTIPLE_SELECTIONS = "//div[contains(@id,'metadataGridContextMenuMultipleSelectionpublishingWorkflows')]/div/ul/li/a[text()='Bulk Publish Rulebook by Hierarchy']";
	// Publishing Workfows -> Bulk Publish by Volume
	public static final String PUBLISHING_WORKFLOWS_BULK_PUBLISH_BY_VOLUME = "//div[contains(@id,'metadataGridContextMenupublishingWorkflows')]/div/ul/li/a[text()='Bulk Publish by Volume']";
	// Run Search & Replace
	public static final String RUN_SEARCH_AND_REPLACE = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Run Search & Replace']";
	// View Raw Xml
	public static final String VIEW_RAW_XML = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Raw Xml']";
	// Edit Raw Xml
	public static final String EDIT_RAW_XML = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Edit Raw Xml']";
	// Show Group
	public static final String SHOW_GROUP = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Show Group']";
	// Manage Glossary Terms
	public static final String MANAGE_GLOSSARY_TERMS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Manage Glossary Terms']";
	// Create Bookmark
	public static final String CREATE_BOOKMARK = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Create Bookmark']";
	// Send Node and Children to Bermuda
	public static final String SEND_NODE_AND_CHILDREN_TO_BERMUDA = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Send Node and Children to Bermuda']";
	// Clone After
	public static final String CLONE_AFTER = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Clone After']";
	// Clone Before
	public static final String CLONE_BEFORE = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Clone Before']";
	// Check Node Validation Flags
	public static final String CHECK_NODE_VALIDATION_FLAGS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Check Node Validation Flags']";
	// Reverify Flags
	public static final String REVERIFY_FLAGS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Reverify Flags']";
	// Delink Previous Node
	public static final String DELINK_PREVIOUS_NODE = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Delink Previous Node']";
	// Novus Extracts
	public static final String NOVUS_EXTRACTS = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Novus Extracts']";
	// Keycite History Publishing
	public static final String KEYCITE_HISTORY_PUBLISHING = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Keycite History Publishing']";
	// Publishing Status
	public static final String PUBLISHING_STATUS = "//div[contains(@id, 'ContextMenu') or contains(@id, 'contextmenu')]//a[text()='Publishing Status']";
	// Publishing Status > Set Publish Ready
	public static final String SET_PUBLISH_READY = "//div[contains(@id, 'ContextMenu') or contains(@id, 'contextmenu')]//a[text()='Set Publish Ready']";

	public static final String CREATE_WIP_VERSION = "//div[contains(@id, 'ContextMenu') or contains(@id, 'contextmenu')]//a[text()='Create WIP Version']";

	//Create WIP version
	@FindBy(how = How.XPATH, using = "//div[contains(@id, 'ContextMenu') or contains(@id, 'contextmenu')]//a[text()='Create WIP Version']")
	public static WebElement createWipVersion;
}
