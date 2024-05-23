package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class SiblingMetadataContextMenu extends ContextMenu
{
	private WebDriver driver;

	@Autowired
	public SiblingMetadataContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SiblingMetadataContextMenuElements.class);
	}

	public void expandPublishingStatus()
	{
		click(SiblingMetadataContextMenuElements.PUBLISHING_STATUS);
		waitForElement(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_NOT_PUBLISHED);
	}

	public boolean manageGlossaryTerms()
	{
		openContextMenu(SiblingMetadataContextMenuElements.manageGlossaryTerms);
		boolean windowAppeared = switchToWindow(GlossaryTermPageElements.GLOSSARY_TERM_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}


	public boolean hierarchyFunctionsInputAsSibling()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions, SiblingMetadataContextMenuElements.hierarchyFunctionsInputAsSibling);
		boolean windowAppeared = switchToWindow(HierarchyInputAsSiblingPageElements.HIERARCHY_INPUT_AS_SIBLING_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean hierarchyFunctionsInsertInHierarchy()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions, SiblingMetadataContextMenuElements.hierarchyFunctionsInsertInHierarchy);
		boolean windowAppeared = switchToWindow(InsertNewNodesPageElements.INSERT_NEW_NODES_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean viewModifyPreviousWIPVersion()
	{
		openContextMenu(SiblingMetadataContextMenuElements.viewModifyPreviousWIPVersion);
		boolean previousWipVersionPageAppeared = switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE, true, DateAndTimeUtils.FIFTEEN_SECONDS);
		waitForGridRefresh();
		return previousWipVersionPageAppeared;
	}

	public boolean publishingWorkflowsRepublishByHierarchy()
	{
		openContextMenu(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH, HierarchyPageElements.REPUBLISH_BY_HIERARCHY_XPATH);
		boolean bulkPublishPageAppeared = switchToWindow(BulkPublishPageElements.BULK_PUBLISH_PAGE_TITLE);
		enterTheInnerFrame();
		return bulkPublishPageAppeared;
	}


	public boolean deleteFunctionsDelete()
	{
		openContextMenu(SiblingMetadataContextMenuElements.DELETE_FUNCTIONS_XPATH, SiblingMetadataContextMenuElements.DELETE_XPATH);
		boolean deleteWindowAppeared = switchToWindow(DeleteNodePageElements.DELETE_PAGE_TITLE);
		enterTheInnerFrame();
		return deleteWindowAppeared;
	}

	public boolean deleteFunctionsUndelete()
	{
		openContextMenu(SiblingMetadataContextMenuElements.DELETE_FUNCTIONS_XPATH, SiblingMetadataContextMenuElements.UNDELETE_XPATH);
		boolean undeleteWindowAppeared = switchToWindow(UndeleteNodePageElements.UNDELETE_PAGE_TITLE);
		enterTheInnerFrame();
		return undeleteWindowAppeared;
	}

	public boolean deleteFunctionsDeleteWithPromoteChildren()
	{
		openContextMenu(SiblingMetadataContextMenuElements.deleteFunctions, SiblingMetadataContextMenuElements.deleteFunctionsDeleteWithPromoteChildren);
		boolean windowAppeared = switchToWindow(DeleteWithPromoteChildrenPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean onlineProductAssignments()
	{
		openContextMenu(SiblingMetadataContextMenuElements.ONLINE_PRODUCT_ASSIGNMENTS);
		waitForPageLoaded();
		boolean onlineProductAssignmentsAppeared = switchToWindow(OnlineProductAssignmentsPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return onlineProductAssignmentsAppeared;
	}

	public boolean taxTypeAssignments()
	{
		openContextMenu(SiblingMetadataContextMenuElements.TAX_TYPE_ASSIGNMENTS);
		waitForPageLoaded();
		boolean onlineProductAssignmentsAppeared = switchToWindow(TaxTypeAssignmentsPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return onlineProductAssignmentsAppeared;
	}

	@Deprecated
	public boolean isPublishingWorkflowsDisplayed()
	{
		waitForPageLoaded();
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
	}

	public void expandPublishingWorkflows()
	{
		click(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
		waitForElementExists(SiblingMetadataContextMenuElements.bulkPublishRulebookByHierarchy);
	}

	public void expandPublishingWorkflowsPublishingDisabled()
	{
		click(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
		waitForElementExists(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
	}

	@Deprecated
	public boolean isOutputRepublishRulebookByHierarchyDisplayed()
	{
		waitForPageLoaded();
		return isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
	}

	@Deprecated
	public boolean isOutputRepublishByHierarchyDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
	}

	@Deprecated
	public boolean isOutputRepublishByVolumeDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
	}

	@Deprecated
	public boolean isWestMateTocDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
	}

	@Deprecated
	public boolean isWestMateHighTocDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);
	}

	@Deprecated
	public boolean isBulkPublishByHierarchyDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_HIERARCHY_XPATH);
	}

	@Deprecated
	public boolean isBulkPublishRulebookByHierarchyDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
	}

	@Deprecated
	public boolean isBulkPublishByVolumeDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_VOLUME_XPATH);
	}

	@Deprecated
	public boolean isSetPublishApprovedDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_APPROVED);
	}

	@Deprecated
	public boolean isSetPublishingDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_PUBLISHED);
	}

	@Deprecated
	public boolean isSetCodesbenchFailureDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_CODESBENCH_FAILURE);
	}

	@Deprecated
	public boolean isSetLTCFailureDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_LTC_FAILURE);
	}

	@Deprecated
	public boolean isSetPublishedToPubDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_PUBLISHED_TO_PUB);
	}

	@Deprecated
	public boolean isSetLoadedToWestlawDisplayed()
	{
		return isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_SET_LOADED_TO_WESTLAW);
	}

	@Deprecated
	public boolean isNotPublishedDisabled()
	{
		return isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_NOT_PUBLISHED);
	}

	@Deprecated
	public boolean isSetPublishReadyDisabled()
	{
		return isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_READY);
	}

	@Deprecated
	public boolean isSetPublishApprovedDisabled()
	{
		return isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_APPROVED);
	}

	@Deprecated
	public boolean isClassifyDescendantsDisplayed()
	{
		waitForPageLoaded();
		return isElementDisplayed(SiblingMetadataContextMenuElements.CLASSIFY_DESCENDANTS);
	}

	public void binFunctionsAddToBin()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsAddToBin);
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
		waitForGridRefresh();
	}

	public void binFunctionsAddBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsAddBinAsChild);
		boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,"Please investigate potential parentage delinking issue");
		Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or wasn't accepted");
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsRemoveBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsRemoveBinAsChild);
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsAddBinAsSibling()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsAddBinAsSibling);
		boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,"Please investigate potential parentage delinking issue");
		Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or wasn't accepted");
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsViewBin()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsViewBin);
		switchToWindow(ViewSelectedNodesBinPageElements.VIEW_SELECTED_NODES_BIN_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsCopyBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsCopyBinAsChild);
		switchToWindow(CopyPageElements.COPY_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsCopyBinAsSibling()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsCopyBinAsSibling);
		switchToWindow(CopyPageElements.COPY_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsDeepCopyBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsDeepCopyBinAsChild);
		switchToWindow(DeepCopyPageElements.DEEP_COPY_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsEmptyBin()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsEmptyBin);
	}

	public void binFunctionsLinkBinNodeBefore()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsLinkBinNodeBefore);
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsLinkBinNodeAfter()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsLinkBinNodeAfter);
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void binFunctionsMoveBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsMoveBinAsChild);
		switchToWindow(MovePageElements.MOVE_PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
	}

	public void binFunctionsMoveBinAsSibling()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsMoveBinAsSibling);
		switchToWindow(MovePageElements.MOVE_PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
	}

	public void binFunctionsRelocateBinAsSibling()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsRelocateBinAsSibling);
		switchToWindow(RelocateBinAsChildSiblingPageElements.RELOCATE_BIN_AS_CHILD_SIBLING_TITLE_PAGE);
		waitForPageLoaded();
		enterTheInnerFrame();
	}

	public void binFunctionsRelocateBinAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.binFunctions, SiblingMetadataContextMenuElements.binFunctionsRelocateBinAsChild);
		switchToWindow(RelocateBinAsChildSiblingPageElements.RELOCATE_BIN_AS_CHILD_SIBLING_TITLE_PAGE);
		waitForPageLoaded();
		enterTheInnerFrame();
	}

	public boolean updateMetadata()
	{
		openContextMenu(SiblingMetadataContextMenuElements.updateMetadata);
		boolean windowAppeared = switchToWindow(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return windowAppeared;
	}

	public void classifyDescendants()
	{
		openContextMenu(SiblingMetadataContextMenuElements.CLASSIFY_DESCENDANTS);
		switchToWindow(ClassifyDescendantsWorkflowPageElements.CLASSIFY_DESCENDANTS_WORKFLOW_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
	}

	public void hierarchyFunctionsInputDocumentContent()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions, SiblingMetadataContextMenuElements.hierarchyFunctionsInputDocumentContent);
		switchToWindow(InputDocumentContentPageElements.INPUT_DOCUMENT_CONTENT_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void viewRawXML()
	{
		openContextMenu(SiblingMetadataContextMenuElements.VIEW_RAW_XML);
		switchToWindow(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
	}

	public void createBookmark()
	{
		openContextMenu(SiblingMetadataContextMenuElements.CREATE_BOOKMARK);
		switchToWindow(CreateBookmarkPageElements.CREATE_BOOKMARK_PAGE_TITLE);
		switchToInnerIFrameByIndex(0);
	}

	public void hierarchyFunctionsAddToRange()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsAddToRange);
		switchToWindow(AddToRangePageElements.ADD_TO_RANGE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsRemoveFromRange()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsRemoveFromRange);
		switchToWindow(RemoveFromRangePageElements.REMOVE_FROM_RANGE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsRepeal()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsRepeal);
		switchToWindow(RepealPageElements.REPEAL_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsReserve()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsReserve);
		switchToWindow(ReservePageElements.RESERVE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsReuse()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsReuse);
		switchToWindow(ReusePageElements.REUSE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsChangeVolumeNumberDescendants()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsChangeVolumeNumberDescendants);
		switchToWindow(ChangeVolumeNumberDescendantsPageElements.CHANGE_VOLUME_NUMBER_DESCENDANTS_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsCloneBefore()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsCloneBefore);
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsCloneAfter()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsCloneAfter);
		switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsDeepClone()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsDeepClone);
		switchToWindow(DeepClonePageElements.DEEP_CLONE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void hierarchyFunctionsUpdateAlternateCite()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsUpdateAlternateCite);
		switchToWindow(UpdateAlternativeCitePageElements.UPDATE_ALTERNATIVE_CITE_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void scriptsAddToScript()
	{
		openContextMenu(SiblingMetadataContextMenuElements.scripts,SiblingMetadataContextMenuElements.scriptsAddScripts);
		switchToWindow(AddAssignedScriptsPageElements.ADD_ASSIGNED_SCRIPTS_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void scriptsAddScriptsHighLevel()
	{
		openContextMenu(SiblingMetadataContextMenuElements.scripts,SiblingMetadataContextMenuElements.scriptsAddScriptsHighLevel);
		switchToWindow(AddHighAssignedScriptsPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void scriptsViewRemoveAssignedScripts()
	{
		openContextMenu(SiblingMetadataContextMenuElements.scripts,SiblingMetadataContextMenuElements.scriptsViewRemoveAssignedScripts);
		switchToWindow(RemoveAssignedScriptsPageElements.REMOVE_ASSIGNED_SCRIPTS_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void createWipVersion(boolean errorExpected)
	{
		openContextMenu(SiblingMetadataContextMenuElements.createWipVersion);
		if(errorExpected)
		{
			return;
		}
		switchToWindow(CreateWipVersionPageElements.CREATE_WIP_VERSION_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void setNotPublishedPublishingStatus()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingStatus, SiblingMetadataContextMenuElements.notPublished);
		AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void setReadyPublishingStatus()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingStatus, SiblingMetadataContextMenuElements.setPublishReady);
		AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
		waitForGridRefresh();
	}

	public void setPublishApproved()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingStatus, SiblingMetadataContextMenuElements.setPublishApproved);
		waitForWindowByTitle(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void setHoldNode()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingStatus, SiblingMetadataContextMenuElements.setHoldNode);
		AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
		waitForGridRefresh();
	}

	public void releaseHold()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingStatus, SiblingMetadataContextMenuElements.releaseHold);
		if(TestSetupEdge.getBrowserTag().equals(CustomAnnotations.BrowserAnnotations.EDGE))
		{
			checkAlertTextMatchesGivenText("Publishing status was updated.");
		}
		else
		{
			AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
		}
		waitForGridRefresh();
	}

	public void refreshSelectedNode()
	{
		openContextMenu(SiblingMetadataContextMenuElements.refreshSelection);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public void editRawXml()
	{
		openContextMenu(SiblingMetadataContextMenuElements.editRawXml);
		switchToWindow(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
		waitForPageLoaded();
	}

	public boolean xmlExtractUnableToPerform()
	{
		openContextMenu(SiblingMetadataContextMenuElements.xmlExtract);
		return AutoITUtils.verifyAlertTextAndAccept(true, "Unable to perform XML extract from this document");
	}

	public boolean xmlExtract()
	{
		openContextMenu(SiblingMetadataContextMenuElements.xmlExtract);
		boolean windowAppears = switchToWindow(XmlExtractPageElements.XML_EXTRACT_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return windowAppears;
	}

	public void refreshSelection()
	{
		openContextMenu(SiblingMetadataContextMenuElements.refreshSelection);
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public void preview()
	{
		openContextMenu(SiblingMetadataContextMenuElements.preview);
		switchToWindow(HierarchyPreviewDocumentPageElements.HIERARCHY_PREVIEW_DOCUMENT_PAGE_TITLE);
	}

	public boolean validationFlagsCheckNodeValidationFlags()
	{
		openContextMenu(SiblingMetadataContextMenuElements.validationFlags,SiblingMetadataContextMenuElements.validationFlagsCheckNodeValidationFlags);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		boolean pageOpened = switchToWindowWithNoTitle(2);
		return pageOpened;
	}

	public void validationFlagsOpenValidationFlagsReport()
	{
		openContextMenu(SiblingMetadataContextMenuElements.validationFlags,SiblingMetadataContextMenuElements.validationFlagsOpenValidationFlagsReport);
		switchToWindow("");
		waitForPageLoaded();
		waitForGridRefresh();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles)
		{
			try
			{
				driver.switchTo().window(windowHandle);
			}
			catch (UnhandledAlertException e)
			{
				acceptAlertNoFail();
				driver.switchTo().window(windowHandle);
			}
			String currentWindowTitle = driver.getTitle();
			if(!currentWindowTitle.equals(HierarchyPageElements.PAGE_TITLE))
			{
				break;
			}
		}
	}

	public boolean validationFlagsCheckHierarchyValidationFlags()
	{
		openContextMenu(SiblingMetadataContextMenuElements.validationFlags,SiblingMetadataContextMenuElements.validationFlagsCheckHierarchyValidationFlags);
		// 2 because at the time of implementation, the only time this method is used is in a test where two windows were open
		boolean pageOpened = switchToWindowWithNoTitle(2);
		waitForPageLoaded();
		waitForGridRefresh();
		Assertions.assertTrue(isElementDisplayed(ValidationFlagsReportPopupPageElements.VALIDATION_FLAGS_REPORT_POPUP_PAGE_HEADER), "Validation Flags Report opened");
		return pageOpened;
	}

	public void validationFlagsReverifyFlags()
	{
		openContextMenu(SiblingMetadataContextMenuElements.validationFlags,SiblingMetadataContextMenuElements.validationFlagsReverifyFlags);
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public void validationFlagsReverifyFlagsMultipleSelected()
	{
		openContextMenu(SiblingMetadataContextMenuElements.validationFlagsReverifyFlags);
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public boolean viewVolumeInfo()
	{
		openContextMenu(SiblingMetadataContextMenuElements.viewVolumeInfo);
		boolean windowIsOpened = switchToWindow(VolumeInformationPageElements.VOLUME_INFORMATION_PAGE_TITLE);
		enterTheInnerFrame();
		return windowIsOpened;
	}

	public void hierarchyFunctionsReorderChildren()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsReorderChildren);
		switchToWindow(ReorderChildrenPageElements.REORDER_CHILDREN_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public boolean hierarchyFunctionsUpdateCodeNameID()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions, SiblingMetadataContextMenuElements.hierarchyFunctionsUpdateCodeNameID);
		boolean windowAppeared = switchToWindow(UpdateCodeNameIDPageElements.UPDATE_CODE_NAME_ID_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;

	}

	public void hierarchyFunctionsInputAsChild()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions, SiblingMetadataContextMenuElements.hierarchyFunctionsInputAsChild);
		switchToWindow(InputAsChildPageElements.INPUT_AS_CHILD_PAGE_TITLE);
		enterTheInnerFrame();
	}

	public boolean runSearchAndReplace()
	{
		openContextMenu(SiblingMetadataContextMenuElements.runSearchAndReplace);
		boolean windowOpened = switchToWindow(SearchAndReplacePageElements.SAR_PAGE_TITLE_LOWERCASE);
		enterTheInnerFrame();
		return windowOpened;
	}

	public boolean publishingWorkflowsBulkPublishRulebookByHierarchy()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingWorkflows, SiblingMetadataContextMenuElements.bulkPublishRulebookByHierarchy);
		boolean windowAppeared = switchToWindow(BulkPublishPageElements.BULK_PUBLISH_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public boolean publishingWorkflowsPublishByHierarchy()
	{
		openContextMenu(SiblingMetadataContextMenuElements.publishingWorkflows, SiblingMetadataContextMenuElements.publishByHierarchy);
		boolean windowAppeared = switchToWindow(BulkPublishPageElements.BULK_PUBLISH_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public void openPublishingWorkflowsSubmenu()
	{
		openContextSubMenu(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);
	}

	public boolean hierarchyFunctionsChangeEndDateDescendants()
	{
		openContextMenu(SiblingMetadataContextMenuElements.hierarchyFunctions,SiblingMetadataContextMenuElements.hierarchyFunctionsChangeEndDateDescendants);
		boolean windowAppeared = switchToWindow(ChangeEndDateDescendantsPageElements.CHANGE_END_DATE_DESCENDANTS_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public void openQueriesSubmenu()
	{
		openContextMenuSubMenu(getElement(SiblingMetadataContextMenuElements.QUERIES));
	}

	//Note: Currently we don't do anything with the GRC News and Alerts page, so we are just passing in the window title as a string so we don't have to make a full class pair for it
	//There is a separate risk team that tests this specific risk functionality
	public boolean addToSeries()
	{
		openContextMenu(SiblingMetadataContextMenuElements.addToSeries);
		return switchToWindow("GRC News And Alerts");
	}

	public boolean createNewVolume()
	{
		openContextMenu(SiblingMetadataContextMenuElements.createNewVolume);
		boolean windowIsOpened = switchToWindow(CreateNewVolumePageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return windowIsOpened;
	}

	public boolean xmlExtractStateFeed()
	{
		openContextMenu(SiblingMetadataContextMenuElements.xmlExtractStateFeed);
		boolean windowAppears = switchToWindow(XmlExtractStateFeedPageElements.XML_EXTRACT_STATE_FEED_PAGE_TITLE);
		enterTheInnerFrame();
		waitForPageLoaded();
		return windowAppears;
	}
}
