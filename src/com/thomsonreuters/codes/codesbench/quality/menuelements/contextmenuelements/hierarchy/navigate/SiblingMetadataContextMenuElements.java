package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyContextMenuElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//This class extends HierarchyContextMenuElements because it uses the same elements as other context menus in the Hierarchy Edit page
public class SiblingMetadataContextMenuElements extends HierarchyContextMenuElements
{
	private static final String HIERARCHY_FUNCTIONS_SUBMENU_XPATH = "//div[contains(@id,'ContextMenuhierarchyFunctions') or contains(@id,'ContextMenuMultipleSelectionhierarchyFunctions') or contains(@id,'metadataGridContextMenuhierarchyFunctions')]";
	private static final String CONTEXT_MENU_BASE_XPATH = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]";
	private static final String METADATA_CONTEXT_MENU_SCRIPTS_BASE_XPATH = "//div[contains(@id,'metadataGridContextMenuscripts') or contains(@id,'metadataGridContextMenuMultipleSelectionscripts')]";
	public static final String PUBLISHING_STATUS = "//div[@id='siblingMetadataContextMenu']//a[text()='Publishing Status']";
	public static final String PUBLISHING_STATUS_NOT_PUBLISHED = "//a[text()='Not Published']";
	public static final String PUBLISHING_STATUS_PUBLISH_READY = "//a[text()='Set Publish Ready']";
	public static final String PUBLISHING_STATUS_PUBLISH_APPROVED = "//a[text()='Set Publish Approved']";
	public static final String PUBLISHING_STATUS_SET_LOADED_TO_WESTLAW = "//a[text()='Set Loaded to Westlaw']";
	public static final String PUBLISHING_STATUS_SET_PUBLISHED = "//a[text()='Set Published']";
	public static final String PUBLISHING_STATUS_SET_CODESBENCH_FAILURE = "//a[text()='Set CodesBench Failure']";
	public static final String PUBLISHING_STATUS_SET_LTC_FAILURE= "//a[text()='Set LTC Failure']";
	public static final String PUBLISHING_STATUS_SET_PUBLISHED_TO_PUB = "//a[text()='Set Published to Pub']";

	public static final String CLASSIFY_DESCENDANTS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Classify Descendants']";
	public static final String CLASSIFY_IN_CHC = CONTEXT_MENU_BASE_XPATH + "//a[text()='Classify in CHC']";
	public static final String CREATE_BOOKMARK = CONTEXT_MENU_BASE_XPATH + "//a[text()='Create Bookmark']";

	public static final String PUBLISHING_WORKFLOWS_XPATH = CONTEXT_MENU_BASE_XPATH + "//a[text()='Publishing Workflows']";
	public static final String OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH = "//a[text()='Output Republish Rulebook by Hierarchy']";
	public static final String OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH = "//a[text()='Output Republish by Hierarchy']";
	public static final String OUTPUT_REPUBLISH_BY_VOLUME_XPATH = "//a[text()='Output Republish by Volume']";
	public static final String WESTMATE_TOC_XPATH = "//a[text()='WestMate TOC']";
	public static final String WESTMATE_HIGH_TOC_XPATH = "//a[text()='WestMate High TOC']";

	public static final String BULK_PUBLISH_BY_HIERARCHY_XPATH = "//a[text()='Bulk Publish by Hierarchy']";
	public static final String BULK_PUBLISH_RULEBOOK_BY_HIERARCHY_XPATH = "//a[text()='Bulk Publish Rulebook by Hierarchy']";
	public static final String BULK_PUBLISH_BY_VOLUME_XPATH = "//a[text()='Bulk Publish by Volume']";

	public static final String DELETE_FUNCTIONS_XPATH = "//div[@id='siblingMetadataContextMenu']//a[text()='Delete Functions']";
	public static final String DELETE_XPATH = "//a[text()='Delete']";
	public static final String UNDELETE_XPATH = "//a[text()='Undelete']";

	public static final String EDIT_CONTENT_AS_CHUNKED_OLD = CONTEXT_MENU_BASE_XPATH + "//a[text()='Edit Content as Chunked-Old']";
	public static final String QUERIES = CONTEXT_MENU_BASE_XPATH + "//a[text()='Queries']";
	public static final String ONLINE_PRODUCT_ASSIGNMENTS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Online Product Assignments']";
	public static final String SHOW_GROUP = CONTEXT_MENU_BASE_XPATH + "//a[text()='Show Group']";
	public static final String VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH = "//div[contains(@id,'metadataGridContextMenuqueries')]//a[text()='View Online Product Node Queries']";
    public static final String ADD_TO_SERIES = CONTEXT_MENU_BASE_XPATH + "//a[text()='Add To Series']";
	public static final String CHECK_OUT = CONTEXT_MENU_BASE_XPATH + "//a[text()='Check Out']";
	public static final String TAX_TYPE_ASSIGNMENTS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Tax Type Assignments']";
	public static final String XML_EXTRACT_STATE_FEED = CONTEXT_MENU_BASE_XPATH + "//a[text()='XML Extract - State Feed']";
	public static final String CREATE_NEW_VOLUME_XPATH = CONTEXT_MENU_BASE_XPATH + "//a[text()='Create New Volume']";


	@FindBy(how = How.XPATH, using = BIN_FUNCTIONS)
	public static WebElement binFunctions;

	@FindBy(how = How.XPATH, using = ADD_TO_SERIES)
	public static WebElement addToSeries;

	@FindBy(how = How.XPATH, using = "//a[text()='Add to Bin']")
	public static WebElement binFunctionsAddToBin;

	@FindBy(how = How.XPATH, using = "//a[text()='Add Bin as Child']")
	public static WebElement binFunctionsAddBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Remove Bin as Child']")
	public static WebElement binFunctionsRemoveBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Add Bin as Sibling']")
	public static WebElement binFunctionsAddBinAsSibling;

	@FindBy(how = How.XPATH, using = "//a[text()='Relocate Bin as Sibling']")
	public static WebElement binFunctionsRelocateBinAsSibling;

	@FindBy(how = How.XPATH, using = "//a[text()='Relocate Bin as Child']")
	public static WebElement binFunctionsRelocateBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Move Bin as Sibling']")
	public static WebElement binFunctionsMoveBinAsSibling;

	@FindBy(how = How.XPATH, using = "//a[text()='View Bin']")
	public static WebElement binFunctionsViewBin;

	@FindBy(how = How.XPATH, using = "//a[text()='Copy Bin as Child']")
	public static WebElement binFunctionsCopyBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Copy Bin as Sibling']")
	public static WebElement binFunctionsCopyBinAsSibling;

	@FindBy(how = How.XPATH, using = "//a[text()='Deep Copy Bin as Child']")
	public static WebElement binFunctionsDeepCopyBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Empty Bin']")
	public static WebElement binFunctionsEmptyBin;

	@FindBy(how = How.XPATH, using = "//a[text()='Link Bin Node Before']")
	public static WebElement binFunctionsLinkBinNodeBefore;

	@FindBy(how = How.XPATH, using = "//a[text()='Link Bin Node After']")
	public static WebElement binFunctionsLinkBinNodeAfter;

	@FindBy(how = How.XPATH, using = "//a[text()='Move Bin as Child']")
	public static WebElement binFunctionsMoveBinAsChild;

	@FindBy(how = How.XPATH, using = "//a[text()='Edit Content']")
	public static WebElement editContent;

	@FindBy(how = How.XPATH, using = "//a[text()='Edit Content Debug']")
	public static WebElement editContentDebug;

	public static final String REFRESH_SELECTION = CONTEXT_MENU_BASE_XPATH + "//a[text()='Refresh Selection']";

	@FindBy(how = How.XPATH, using = REFRESH_SELECTION)
	public static WebElement refreshSelection;

	public static final String HIERARCHY_FUNCTIONS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Hierarchy Functions']";

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS)
	public static WebElement hierarchyFunctions;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Publishing Workflows']")
	public static WebElement publishingWorkflows;

	@FindBy(how = How.XPATH, using = EDIT_RAW_XML)
	public static WebElement editRawXml;

	@FindBy(how = How.XPATH, using = "//a[text()='Output Republish by Volume']")
	public static WebElement outputRepublishByVolume;

	@FindBy(how = How.XPATH, using = "//a[text()='Publish by Hierarchy']")
	public static WebElement publishByHierarchy;

	@FindBy(how = How.XPATH, using = "//a[text()='Bulk Publish Rulebook by Hierarchy']")
	public static WebElement bulkPublishRulebookByHierarchy;

	@FindBy(how = How.XPATH, using = "//a[text()='Bulk Publish by Volume']")
	public static WebElement bulkPublishByVolume;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Input as Sibling']")
	public static WebElement hierarchyFunctionsInputAsSibling;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Insert in Hierarchy']")
	public static WebElement hierarchyFunctionsInsertInHierarchy;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()= 'Insert in Hierarchy - NOD']")
	public static WebElement hierarchyFunctionsInsertInHierarchyNOD;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Input Document Content']")
	public static WebElement hierarchyFunctionsInputDocumentContent;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Update Metadata']")
	public static WebElement updateMetadata;

	public static final String VIEW_MODIFY_PREVIOUS_WIP_VERSION = CONTEXT_MENU_BASE_XPATH + "//a[text()='View/Modify Previous WIP Version']";

	@FindBy(how = How.XPATH, using = VIEW_MODIFY_PREVIOUS_WIP_VERSION)
	public static WebElement viewModifyPreviousWIPVersion;

	public static final String MANAGE_GLOSSARY_TERMS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Manage Glossary Terms']";

	@FindBy(how = How.XPATH, using = MANAGE_GLOSSARY_TERMS)
	public static WebElement manageGlossaryTerms;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Create WIP Version']")
	public static WebElement createWipVersion;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Not Published']")
	public static WebElement notPublished;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Set Publish Ready']")
	public static WebElement setPublishReady;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Set Publish Approved']")
	public static WebElement setPublishApproved;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH+ "//a[text()='Set Hold Node']")
	public static WebElement setHoldNode;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Release Hold']")
	public static WebElement releaseHold;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Publishing Status']")
	public static WebElement publishingStatus;

	// Delete Functions
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Delete Functions']")
	public static WebElement deleteFunctions;

	// Delete Functions -> Delete
	@FindBy(how = How.XPATH, using = DELETE_XPATH)
	public static WebElement deleteFunctionsDelete;

	// Delete Functions -> Delete With Promote Children
	@FindBy(how = How.XPATH, using = "//a[text()='Delete With Promote Children']")
	public static WebElement deleteFunctionsDeleteWithPromoteChildren;

	// Delete Functions -> Undelete
	@FindBy(how = How.XPATH, using = UNDELETE_XPATH)
	public static WebElement deleteFunctionsUndelete;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Add To Range']")
	public static WebElement hierarchyFunctionsAddToRange;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Remove From Range']")
	public static WebElement hierarchyFunctionsRemoveFromRange;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Repeal']")
	public static WebElement hierarchyFunctionsRepeal;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Reserve']")
	public static WebElement hierarchyFunctionsReserve;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Reuse']")
	public static WebElement hierarchyFunctionsReuse;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Change Volume Number(descendants)']")
	public static WebElement hierarchyFunctionsChangeVolumeNumberDescendants;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Change End Date(descendants)']")
	public static WebElement hierarchyFunctionsChangeEndDateDescendants;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Clone Before']")
	public static WebElement hierarchyFunctionsCloneBefore;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Clone After']")
	public static WebElement hierarchyFunctionsCloneAfter;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Deep Clone']")
	public static WebElement hierarchyFunctionsDeepClone;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Update Alternate Cite']")
	public static WebElement hierarchyFunctionsUpdateAlternateCite;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Update CodeName/ID']")
	public static WebElement hierarchyFunctionsUpdateCodeNameID;

	public static final String SCRIPTS = CONTEXT_MENU_BASE_XPATH+ "//a[text()='Scripts']";

	@FindBy(how = How.XPATH, using = SCRIPTS)
	public static WebElement scripts;

	@FindBy(how = How.XPATH, using = METADATA_CONTEXT_MENU_SCRIPTS_BASE_XPATH + "/div/ul/li/a[text()='Add Scripts']")
	public static WebElement scriptsAddScripts;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'metadataGridContextMenuscripts')]/div/ul/li/a[text()='Add Scripts High Level']")
	public static WebElement scriptsAddScriptsHighLevel;

	@FindBy(how = How.XPATH, using = METADATA_CONTEXT_MENU_SCRIPTS_BASE_XPATH + "/div/ul/li/a[text()='View/Remove Assigned Scripts']")
	public static WebElement scriptsViewRemoveAssignedScripts;

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'metadataGridContextMenuvalidationFlags')]/div/ul/li/a[text()='Check Node Validation Flags']")
	public static WebElement checkNodeValidationFlags;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_BASE_XPATH + "//a[text()='Preview']")
	public static WebElement preview;

	public static final String VALIDATION_FLAGS = CONTEXT_MENU_BASE_XPATH + "//a[text()='Validation Flags']";

	@FindBy(how = How.XPATH, using = VALIDATION_FLAGS)
	public static WebElement validationFlags;

	@FindBy(how = How.XPATH, using = "//a[text()='Check Node Validation Flags']")
	public static WebElement validationFlagsCheckNodeValidationFlags;

	@FindBy(how = How.XPATH, using = "//a[text()='Reverify Flags']")
	public static WebElement validationFlagsReverifyFlags;

	@FindBy(how = How.XPATH, using = "//a[text()='Open Validation Flags Report']")
	public static WebElement validationFlagsOpenValidationFlagsReport;

	@FindBy(how = How.XPATH, using = "//a[text()='Check Hierarchy Validation Flags']")
	public static WebElement validationFlagsCheckHierarchyValidationFlags;

	public static final String VIEW_VOLUME_INFO = CONTEXT_MENU_BASE_XPATH + "//a[text()='View Volume Info']";

	@FindBy(how = How.XPATH, using = VIEW_VOLUME_INFO)
	public static WebElement viewVolumeInfo;

	@FindBy(how = How.XPATH, using = VIEW_CONTENT)
	public static WebElement viewContentOld;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Reorder Children']")
	public static WebElement hierarchyFunctionsReorderChildren;

	@FindBy(how = How.XPATH, using = HIERARCHY_FUNCTIONS_SUBMENU_XPATH + "//a[text()='Input as Child']")
	public static WebElement hierarchyFunctionsInputAsChild;

	public static final String RUN_SEARCH_AND_REPLACE = CONTEXT_MENU_BASE_XPATH + "//a[text()='Run Search & Replace']";

	@FindBy(how = How.XPATH, using = RUN_SEARCH_AND_REPLACE)
	public static WebElement runSearchAndReplace;

	@FindBy(how = How.XPATH, using = "//a[text()='View Content']")
	public static WebElement viewContent;

	@FindBy(how = How.XPATH, using = "//a[text()='View Content Debug']")
	public static WebElement viewContentDebug;

	@FindBy(how = How.XPATH, using = "//a[text()='XML Extract']")
	public static WebElement xmlExtract;

	@FindBy(how = How.XPATH, using = XML_EXTRACT_STATE_FEED)
	public static WebElement xmlExtractStateFeed;

	@FindBy(how = How.LINK_TEXT, using = "Create New Volume")
	public static WebElement createNewVolume;
}
