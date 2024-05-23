package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.DispositionDerivationContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.ParentageGraphContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.TreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyNavigateContextMenuRiskSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Risk user has the correct context menu options when right clicking a node in the Sibling Metadata pane on the Hierarchy Edit page<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void siblingMetadataContextMenuOptionsRiskSharedTest() 
    {
    	String iowaContentSet = "Iowa (Development)";
    	String nodeUuid = "I601EAAA014F011DA8AC5CD53670E6B4E";

		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(iowaContentSet);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
        
        boolean editContentContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.EDIT_CONTENT);
        boolean editContentAsChunkedContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.EDIT_CONTENT_AS_CHUNKED_OLD);
        boolean viewContentContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_CONTENT);
        boolean refreshSelectionContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.REFRESH_SELECTION);
        boolean viewMetadataContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_METADATA);
        boolean viewModifyPreviousWipVersionContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_MODIFY_PREVIOUS_WIP_VERSION);
        boolean viewVolumeInfoContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_VOLUME_INFO);
        boolean classifyInCHCContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_IN_CHC);
        boolean classifyDescendantsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_DESCENDANTS);
        boolean hierarchyFunctionsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.HIERARCHY_FUNCTIONS);
        boolean binFunctionsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BIN_FUNCTIONS);
        boolean validationFlagsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VALIDATION_FLAGS);
        boolean deleteFunctionsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.DELETE_FUNCTIONS_XPATH);
        boolean queriesContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.QUERIES);
        
        //Open Queries submenu and verify that the 'View Online Product Node Queries' menu option is displayed
		siblingMetadataContextMenu().openQueriesSubmenu();

		boolean viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH);
        boolean scriptsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.SCRIPTS);
        boolean onlineProductAssignmentsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.ONLINE_PRODUCT_ASSIGNMENTS);
        boolean publishingWorkflowsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
        boolean runSearchAndReplaceContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.RUN_SEARCH_AND_REPLACE);
        boolean editRawXmlContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.EDIT_RAW_XML);
        boolean viewRawXmlContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.VIEW_RAW_XML);
        boolean showGroupContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.SHOW_GROUP);
        boolean manageGlossaryTermsContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.MANAGE_GLOSSARY_TERMS);
        boolean createBookmarkContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CREATE_BOOKMARK);
        
		Assertions.assertAll
		(
				() -> Assertions.assertFalse(editContentContextMenuOptionIsDisplayed, "The 'Edit Content' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(editContentAsChunkedContextMenuOptionIsDisplayed,"The 'Edit Content as Chunked' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(viewContentContextMenuOptionIsDisplayed, "The 'View Content' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(refreshSelectionContextMenuOptionIsDisplayed, "The 'Refresh Selection' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(viewMetadataContextMenuOptionIsDisplayed, "The 'View Metadata' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(viewModifyPreviousWipVersionContextMenuOptionIsDisplayed,"The 'View/Modify Previous WIP Version' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(viewVolumeInfoContextMenuOptionIsDisplayed, "The 'View Volume Info' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(classifyInCHCContextMenuOptionIsDisplayed, "The 'Classify in CHC' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(classifyDescendantsContextMenuOptionIsDisplayed, "The 'Classify Descendants' context menu option did not appear when it should have"),
				() -> Assertions.assertFalse(hierarchyFunctionsContextMenuOptionIsDisplayed, "The 'Hierarchy Functions' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(binFunctionsContextMenuOptionIsDisplayed, "The 'Bin Functions' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(validationFlagsContextMenuOptionIsDisplayed, "The 'Validation Flags' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(deleteFunctionsContextMenuOptionIsDisplayed, "The 'Delete Functions' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(queriesContextMenuOptionIsDisplayed, "The 'Queries' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed, "The 'View Online Product Node Queries' context menu option did not appear when it should have"),
				() -> Assertions.assertFalse(scriptsContextMenuOptionIsDisplayed, "The 'Scripts' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(onlineProductAssignmentsContextMenuOptionIsDisplayed,"The 'Online Product Assignments' context menu option did not appear when it should have"),
				() -> Assertions.assertFalse(publishingWorkflowsContextMenuOptionIsDisplayed, "The 'Publishing Workflows' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(runSearchAndReplaceContextMenuOptionIsDisplayed, "The 'Run Search & Replace' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(editRawXmlContextMenuOptionIsDisplayed, "The 'Edit Raw Xml' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(viewRawXmlContextMenuOptionIsDisplayed, "The 'View Raw Xml' context menu option did not appear when it should have"),
				() -> Assertions.assertFalse(showGroupContextMenuOptionIsDisplayed, "The 'Show Group' context menu option appeared when it should not have"),
				() -> Assertions.assertFalse(manageGlossaryTermsContextMenuOptionIsDisplayed, "The 'Manage Glossary Terms' context menu option appeared when it should not have"),
				() -> Assertions.assertTrue(createBookmarkContextMenuOptionIsDisplayed, "The 'Create Bookmark' context menu option did not appear when it should have")
		);
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Risk user has the correct context menu options when right clicking a node in the hierarchy tree pane on the Hierarchy Edit page<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigationPaneContextMenuOptionsRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";
		String nodeUuid = "I601EAAA014F011DA8AC5CD53670E6B4E";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		
		hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        boolean editContentContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.EDIT_CONTENT);
        boolean editContentAsChunkedContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.EDIT_CONTENT_AS_CHUNKED);
        boolean viewContentContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_CONTENT);
        boolean viewMetadataContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_METADATA);
        boolean viewModifyPreviousWipVersionContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_MODIFY_PREVIOUS_WIP_VERSION);
        boolean viewVolumeInfoContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_VOLUME_INFO);
        boolean classifyInCHCContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.CLASSIFY_IN_CHC);
        boolean classifyDescendantsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.CLASSIFY_DESCENDANTS);
        boolean hierarchyFunctionsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.HIERARCHY_FUNCTIONS);
        boolean binFunctionsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.BIN_FUNCTIONS);
        boolean validationFlagsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VALIDATION_FLAGS);
        boolean deleteFunctionsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.DELETE_FUNCTIONS);
        boolean queriesContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.QUERIES);

		//Open Queries submenu and verify that the 'View Online Product Node Queries' menu option is displayed
		treeContextMenu().openQueriesSubmenu();

		boolean viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed = siblingMetadataContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH);
        boolean scriptsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.SCRIPTS);
        boolean onlineProductAssignmentsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.ONLINE_PRODUCT_ASSIGNMENTS);
        boolean publishingWorkflowsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
        boolean runSearchAndReplaceContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.RUN_SEARCH_AND_REPLACE);
        boolean editRawXmlContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.EDIT_RAW_XML);
        boolean viewRawXmlContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.VIEW_RAW_XML);
        boolean manageGlossaryTermsContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.SHOW_GROUP);
        boolean createBookmarkContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.CREATE_BOOKMARK);
        boolean sendNodeAndChildrenToBermudaContextMenuOptionIsDisplayed = treeContextMenu().isElementDisplayed(TreeContextMenuElements.SEND_NODE_AND_CHILDREN_TO_BERMUDA);
        
        Assertions.assertAll
        (
        		() -> Assertions.assertFalse(editContentContextMenuOptionIsDisplayed , "The 'Edit Content' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(editContentAsChunkedContextMenuOptionIsDisplayed , "The 'Edit Content as Chunked' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewContentContextMenuOptionIsDisplayed , "The 'View Content' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(viewMetadataContextMenuOptionIsDisplayed , "The 'View Metadata' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(viewModifyPreviousWipVersionContextMenuOptionIsDisplayed , "The 'View/Modify Previous WIP Version' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(viewVolumeInfoContextMenuOptionIsDisplayed , "The 'View Volume Info' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(classifyInCHCContextMenuOptionIsDisplayed , "The 'Classify in CHC' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(classifyDescendantsContextMenuOptionIsDisplayed , "The 'Classify Descendants' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(hierarchyFunctionsContextMenuOptionIsDisplayed , "The 'Hierarchy Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(binFunctionsContextMenuOptionIsDisplayed , "The 'Bin Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(validationFlagsContextMenuOptionIsDisplayed , "The 'Validation Flags' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(deleteFunctionsContextMenuOptionIsDisplayed , "The 'Delete Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(queriesContextMenuOptionIsDisplayed, "The 'Queries' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed, "The 'View Online Product Node Queries' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(scriptsContextMenuOptionIsDisplayed , "The 'Scripts' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(onlineProductAssignmentsContextMenuOptionIsDisplayed , "The 'Online Product Assignments' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(publishingWorkflowsContextMenuOptionIsDisplayed, "The 'Publishing Workflows' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(runSearchAndReplaceContextMenuOptionIsDisplayed, "The Run Search & Replace' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(editRawXmlContextMenuOptionIsDisplayed, "The 'Edit Raw Xml' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewRawXmlContextMenuOptionIsDisplayed , "The 'View Raw Xml' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(manageGlossaryTermsContextMenuOptionIsDisplayed  , "The 'Manage Glossary Terms' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(createBookmarkContextMenuOptionIsDisplayed , "The 'Create Bookmark' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(sendNodeAndChildrenToBermudaContextMenuOptionIsDisplayed, "The 'Send Node and Children to Bermuda' context menu option appeared when it should not have")
        );
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Risk user has the correct context menu options when right clicking a node in the Disposition/Derivation pane on the Hierarchy Edit page<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void dispositionDerivationContextMenuOptionsRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";
		String nodeUuid = "I601EAAA014F011DA8AC5CD53670E6B4E";

		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(iowaContentSet);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		dispositionDerivationPage().rightClickSelectedNode();

		boolean editContentContextMenuOptionIsDisplayed =  dispositionDerivationContextMenu().isElementDisplayed(HierarchyContextMenuElements.EDIT_CONTENT);
		boolean viewContentContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(HierarchyContextMenuElements.VIEW_CONTENT);
		boolean refreshSelectionContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.REFRESH_SELECTION);
        boolean viewMetadataContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.VIEW_METADATA);
        boolean hierarchyFunctionsContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.HIERARCHY_FUNCTIONS);
        boolean cloneAfterContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.CLONE_AFTER);
        boolean cloneBeforeContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.CLONE_BEFORE);
        boolean binFunctionsContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.BIN_FUNCTIONS);
        boolean checkNodeValidationFlagsContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.CHECK_NODE_VALIDATION_FLAGS);
        boolean reverifyFlagsContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.REVERIFY_FLAGS);
        boolean deleteFunctionsContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS);
        boolean editRawXmlContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.EDIT_RAW_XML);
        boolean viewRawXmlContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.VIEW_RAW_XML);
        boolean delinkPreviousNodeContextMenuOptionIsDisplayed = dispositionDerivationContextMenu().isElementDisplayed(DispositionDerivationContextMenuElements.DELINK_PREVIOUS_NODE);
        
        Assertions.assertAll
        (
        		() -> Assertions.assertFalse(editContentContextMenuOptionIsDisplayed, "The 'Edit Content' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewContentContextMenuOptionIsDisplayed, "The 'View Content' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(refreshSelectionContextMenuOptionIsDisplayed, "The 'Refresh Selection' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(viewMetadataContextMenuOptionIsDisplayed, "The 'View Metadata' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(hierarchyFunctionsContextMenuOptionIsDisplayed, "The 'Hierarchy Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(cloneAfterContextMenuOptionIsDisplayed, "The 'Clone After' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(cloneBeforeContextMenuOptionIsDisplayed, "The 'Clone Before' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(binFunctionsContextMenuOptionIsDisplayed, "The 'Bin Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(checkNodeValidationFlagsContextMenuOptionIsDisplayed, "The 'Check Node Validation Flags' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(reverifyFlagsContextMenuOptionIsDisplayed, "The 'Reverify Flags' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(deleteFunctionsContextMenuOptionIsDisplayed, "The 'Delete Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(editRawXmlContextMenuOptionIsDisplayed, "The 'Edit Raw Xml' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewRawXmlContextMenuOptionIsDisplayed, "The 'View Raw Xml' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(delinkPreviousNodeContextMenuOptionIsDisplayed, "The 'Delink Previous Node' context menu option appeared when it should not have")
        );
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Risk user has the correct context menu options when right clicking a node in the Parentage Graph pane on the Hierarchy Edit page<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void parentageGraphContextMenuOptionsRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";
		String nodeUuid = "I601EAAA014F011DA8AC5CD53670E6B4E";

		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(iowaContentSet);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		parentageGraphPage().clickExpandButton();//This is needed otherwise the 'View Online Product Node Queries' option is blocked by the popup at the bottom of the page
		parentageGraphPage().rightClickSelectedParentageGraphNodeImage();

		boolean editContentContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.EDIT_CONTENT);
        boolean viewContentContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.VIEW_CONTENT);
        boolean refreshSelectionContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.REFRESH_SELECTION);
        boolean viewMetadataContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.VIEW_METADATA);
        boolean viewModifyPreviousWipVersionContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.VIEW_MODIFY_PREVIOUS_WIP_VERSION);
        boolean binFunctionsContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.BIN_FUNCTIONS);
        boolean queriesContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.QUERIES);

		//Open Queries submenu and verify that the 'View Online Product Node Queries' menu option is displayed
		parentageGraphContextMenu().openQueriesSubmenu();

		boolean viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH);
        boolean editRawXmlContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.EDIT_RAW_XML);
        boolean viewRawXmlContextMenuOptionIsDisplayed = parentageGraphContextMenu().isElementDisplayed(ParentageGraphContextMenuElements.VIEW_RAW_XML);
        
        Assertions.assertAll
        (
        		() -> Assertions.assertFalse(editContentContextMenuOptionIsDisplayed, "The 'Edit Content' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewContentContextMenuOptionIsDisplayed, "The 'View Content' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(refreshSelectionContextMenuOptionIsDisplayed, "The 'Refresh Selection' context menu option did not appear when it should have"),
        		() -> Assertions.assertTrue(viewMetadataContextMenuOptionIsDisplayed, "The 'View Metadata' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(viewModifyPreviousWipVersionContextMenuOptionIsDisplayed, "The 'View/Modify Previous WIP Version' context menu option appeared when it should not have"),
        		() -> Assertions.assertFalse(binFunctionsContextMenuOptionIsDisplayed, "The 'Bin Functions' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(queriesContextMenuOptionIsDisplayed, "The 'Queries' context menu option did not appear when it should have"),
				() -> Assertions.assertTrue(viewOnlineProductNodeQueriesContextMenuOptionIsDisplayed, "The 'View Online Product Node Queries' context menu option did not appear when it should have"),
        		() -> Assertions.assertFalse(editRawXmlContextMenuOptionIsDisplayed, "The 'Edit Raw Xml' context menu option appeared when it should not have"),
        		() -> Assertions.assertTrue(viewRawXmlContextMenuOptionIsDisplayed, "The 'View Raw Xml' context menu option did not appear when it should have")
        );
    }
}
