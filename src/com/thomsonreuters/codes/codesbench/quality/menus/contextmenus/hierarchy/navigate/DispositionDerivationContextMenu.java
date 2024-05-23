package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.DispositionDerivationContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DispositionDerivationContextMenu extends ContextMenu
{
    WebDriver driver;

    @Autowired
    public DispositionDerivationContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DispositionDerivationContextMenuElements.class);
    }

    public boolean delinkPreviousNode()
    {
        openContextMenu(DispositionDerivationContextMenuElements.DELINK_PREVIOUS_NODE);
        boolean windowAppeared = switchToWindow(DelinkPreviousNodePageElements.DELINK_PREVIOUS_NODE_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean deleteFunctionsDelete()
    {
        waitForElementExists(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS);
        openContextMenu(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS,DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_DELETE);
        boolean windowAppeared = switchToWindow(DeleteNodePageElements.DELETE_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean deleteFunctionsDeleteGraph()
    {
        waitForElementExists(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_GRAPH);
        openContextMenu(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_GRAPH,DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_DELETE_GRAPH);
        boolean windowAppeared = switchToWindow(DeleteNodePageElements.DELETE_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public void deleteFunctionsUndelete()
    {
        openContextMenu(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS,DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_UNDELETE);
        switchToWindow(UndeleteNodePageElements.UNDELETE_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void deleteFunctionsUndeleteGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_GRAPH,DispositionDerivationContextMenuElements.DELETE_FUNCTIONS_UNDELETE_GRAPH);
        switchToWindow(UndeleteNodePageElements.UNDELETE_PAGE_TITLE);
        enterTheInnerFrame();
    }

    public boolean editContent()
    {
        openContextMenu(DispositionDerivationContextMenuElements.EDIT_CONTENT);
        boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return editorWindowAppeared;
    }

    public boolean editContentGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.EDIT_CONTENT_GRAPH);
        boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return editorWindowAppeared;
    }

    public boolean viewContentOld()
    {
        openContextMenu(HierarchyContextMenuElements.VIEW_CONTENT);
        boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return editorWindowAppeared;
    }

    public boolean viewContent()
    {
        openContextMenu(DispositionDerivationContextMenuElements.VIEW_CONTENT);
        boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return editorWindowAppeared;
    }

    public boolean viewContentGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.VIEW_CONTENT_GRAPH);
        boolean editorWindowAppeared = switchToWindow(EditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return editorWindowAppeared;
    }

    public void refreshSelection()
    {
        openContextMenu(DispositionDerivationContextMenuElements.REFRESH_SELECTION);
        waitForGridRefresh();
    }

    public void refreshSelectionGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.REFRESH_SELECTION_GRAPH);
        waitForGridRefresh();
    }

    public boolean updateMetadata()
    {
        openContextMenu(DispositionDerivationContextMenuElements.UPDATE_METADATA);
        boolean windowAppeared = switchToWindow(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean updateMetadataGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.UPDATE_METADATA_GRAPH);
        boolean windowAppeared = switchToWindow(UpdateMetadataPageElements.UPDATE_METADATA_PAGE_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean viewModifyPreviousWipVersion()
    {
        openContextMenu(DispositionDerivationContextMenuElements.VIEW_MODIFY_PREVIOUS_WIP_VERSION);
        boolean windowAppeared = switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
        waitForPageLoaded();
        return windowAppeared;
    }

    public boolean cloneBefore()
    {
        openContextMenu(DispositionDerivationContextMenuElements.CLONE_BEFORE);
        boolean windowAppeared = switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean cloneBeforeGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.CLONE_BEFORE_GRAPH);
        boolean windowAppeared = switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean cloneAfter()
    {
        openContextMenu(DispositionDerivationContextMenuElements.CLONE_AFTER);
        boolean windowAppeared = switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean cloneAfterGraph()
    {
        openContextMenu(DispositionDerivationContextMenuElements.CLONE_AFTER_GRAPH);
        boolean windowAppeared = switchToWindow(HierarchySetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        enterTheInnerFrame();
        return windowAppeared;
    }

    public boolean editRawXml()
    {
        openContextMenu(DispositionDerivationContextMenuElements.EDIT_RAW_XML);
        boolean windowAppeared = switchToWindow(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
        waitForPageLoaded();
        return windowAppeared;
    }

    public boolean viewRawXml()
    {
        openContextMenu(DispositionDerivationContextMenuElements.VIEW_RAW_XML);
        boolean windowAppeared = switchToWindow(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
        waitForPageLoaded();
        return windowAppeared;
    }

    public void publishingStatusSetPublishReady()
    {
        waitForElementExists(DispositionDerivationContextMenuElements.PUBLISHING_STATUS);
        openContextMenu(DispositionDerivationContextMenuElements.PUBLISHING_STATUS, DispositionDerivationContextMenuElements.SET_READY_STATUS);
        AutoITUtils.verifyAlertTextAndAccept(true, "Publishing status was updated.");
        waitForGridRefresh();
    }
}
