package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractEditorExpandTests extends TestService
{
    private static final String HIGHLIGHTED_BACKGROUND_COLOR = "rgba(49, 106, 197, 1)";
    protected String nodeName;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editorExpandAndCollapseAndTreeRefreshTest(TestInfo testInfo)
    {
        //2. Select and expand parts of the tree
        editorTreePage().expandEditorsTreeAndClickNode(nodeName);

        //VERIFY:
        // The tree expands as you do this
        // Nodes you select in the tree are highlighted and selected in the editor
        assertThatNodeIsHighlighted(nodeName);
        assertThatNodeIsSelected(nodeName);

        //Assert that node is expanded
        assertThat(editorTreePage().isNodeExpanded(nodeName))
                .as(String.format("The [%s] node should be expanded", nodeName))
                .isTrue();

        //3. Select and collapse parts of the tree
        editorTreePage().collapseEditorsTreeByNode(nodeName);

        //VERIFY:
        // The tree collapses as you do this
        // Nodes you select in the tree are highlighted and selected in the editor
        assertThatNodeIsHighlighted(nodeName);
        assertThatNodeIsSelected(nodeName);

        //Assert that node is collapsed
        assertThat(editorTreePage().isNodeExpanded(nodeName))
                .as(String.format("The [%s] node should be collapsed", nodeName))
                .isFalse();

        //5. Expand multiple elements in the tree, right click the top level element in the tree
        editorTreePage().expandEditorsTreeAndClickNode(nodeName);
        editorTreePage().rightClickTreeRootForTargetNodes();

        //6. Select Refresh or Close All
        editorTreeContextMenu().closeAll();

        //7. VERIFY:
        // The tree is collapsed
        assertThatWholeTreeIsCollapsed();

        if(testInfo.getTestClass().toString().contains(".source.")) {
            //8. Expand source.body/source.section 1
            editorTreePage().expandEditorsTreeAndClickNode("source.body", "source.section \"1\"");

            //9. VERIFY expand and collapse button not present in effective date block
            verifyExpandCollapseButtonForTheNode(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, "effective.date.block") + EditorTreePageElements.PRECEDING_EXPAND_NODE_LINK);
            verifyExpandCollapseButtonForTheNode(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, "effective.date.block") + EditorTreePageElements.PRECEDING_COLLAPSE_NODE_LINK);

            //10. Select and collapse parts of the tree
            editorTreePage().collapseEditorsTreeByNode("source.body");
        }

        // (HALCYONST-2512/3686) – Try to refresh the tree multiple times and verify the tree is collapsed as expected.
        // This function will rebuild the entire tree.
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().closeAll();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().closeAll();
        assertThatWholeTreeIsCollapsed();
    }

    @AfterEach
    public void closeAllOpenedDocuments()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

//  ------------- Assertions: -------------

    private void assertThatNodeIsHighlighted(String nodeName)
    {
        assertThat(editorTreePage().getTreeNodeBackgroundColor(nodeName))
                .as(String.format("The [%s] node should be highlighted i.e. must have '%s' background color value",
                        nodeName, HIGHLIGHTED_BACKGROUND_COLOR))
                .isEqualTo(HIGHLIGHTED_BACKGROUND_COLOR);
    }

    private void assertThatNodeIsSelected(String nodeName)
    {
        assertThat(editorTreePage().isTreeNodeSelected(nodeName))
                .as(String.format("The [%s] node should be selected", nodeName))
                .isTrue();
    }

    private void assertThatWholeTreeIsCollapsed()
    {
        assertThat(editorTreePage().isWholeTreeCollapsed())
                .as("The whole tree should be collapsed")
                .isTrue();
    }

    public void verifyExpandCollapseButtonForTheNode(String xpath)
    {
        assertThat(editorTreePage().isElementDisplayed(xpath))
                .as(String.format("The expand button should not be present"))
                .isFalse();
    }
}
