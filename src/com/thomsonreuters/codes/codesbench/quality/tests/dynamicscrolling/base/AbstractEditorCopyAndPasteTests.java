package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.awt.event.KeyEvent;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.FOLLOWING_TREE_NODE_WITH_TEXT_LINK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractEditorCopyAndPasteTests extends TestService
{
    protected String[] nodesToExpand;
    protected String firstSourceNode;
    protected String secondSourceNode;
    protected String destinationNode;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeAllOpenedDocuments()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editorTreeElementsCopyAndPasteTest()
    {
        //Expand the body of the tree
        editorTreePage().expandEditorsTreeAndClickNode(nodesToExpand);

        //Shift+click select a group of elements in the tree. This can be several paragraphs,
        //sections, deltas, etc. Try with Ctrl+click too
        editorTreePage().selectNodesViaAction(KeyEvent.VK_SHIFT, firstSourceNode);
        editorTreePage().selectNodesViaAction(KeyEvent.VK_CONTROL, secondSourceNode);

        //Assert that these nodes are selected
        assertThatNodeIsSelected(firstSourceNode);
        assertThatNodeIsSelected(secondSourceNode);

        //Press Ctrl+c to copy the selected elements
        editorTreePage().sendKeys(Keys.chord(Keys.LEFT_CONTROL, "c"));

        //Select a different part of the body and press ctrl+v to paste the previously copied elements
        editorTreePage().expandEditorsTreeAndClickNode(destinationNode);
        editorTreePage().sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v"));

        //Assert that the copied elements are pasted as a sibling to the selected element
        assertThatNodeIsPastedAsSiblingToSelectedElement(destinationNode, firstSourceNode);
        assertThatNodeIsPastedAsSiblingToSelectedElement(destinationNode, secondSourceNode);
    }

//  ------------- Assertions: -------------

    private void assertThatNodeIsSelected(String nodeName)
    {
        assertThat(editorTreePage().isTreeNodeSelected(nodeName))
                .as(String.format("The [%s] node should be selected", nodeName))
                .isTrue();
    }
    
    private void assertThatNodeIsPastedAsSiblingToSelectedElement(String selectedElement, String pastedNode)
    {
        assertThat(editorTreePage().doesElementExist(String.format(
                TREE_NODE_WITH_TEXT_LINK, selectedElement, FOLLOWING_TREE_NODE_WITH_TEXT_LINK, pastedNode)))
                .as(String.format("The [%s] node should be pasted as sibling to [%s] node", pastedNode, selectedElement))
                .isTrue();
    }
}
