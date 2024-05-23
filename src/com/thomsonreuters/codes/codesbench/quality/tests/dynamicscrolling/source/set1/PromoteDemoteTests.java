package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPromoteDemoteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PRECEDING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_CONTAINS_TEXT;
import static java.lang.String.format;

public class PromoteDemoteTests extends AbstractPromoteDemoteTests
{
    private static final String UUID = "I604F93F0F66311E3B148A5B04553906B";
    private static final String DEMOTED_SUBSECTIONS = "//part/subsection/subsection";
    private static final String PROMOTED_SUBSECTIONS = "//delta/subsection";

    @BeforeEach
    public void openSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("elementsToHighlight")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void promoteAndDemoteInTheEditorSourceLegalTest(int elementsToHighlight)
    {
        String subsectionAcrossChunkBorderByNumber = SUBSECTION + "[%d]/span";
        int siblingsToHighlight = elementsToHighlight - 1;

        //Select elements to demote
        editorPage().scrollToChunk(1);
        selectElementsInTheEditor(format(subsectionAcrossChunkBorderByNumber, 3), siblingsToHighlight);
        assertThatThereAreNoDemotedElements(DEMOTED_SUBSECTIONS);

        //Demote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforeDemoting = countTreeElements();
        editorToolbarPage().clickDemote();
        assertThatTheSpecificNumberOfDemotedElementsAppliedCorrectly(elementsToHighlight, DEMOTED_SUBSECTIONS);
        assertThatEditorTreeUpdatedCorrectlyAfterDemoting(countTreeElements(),
                treeElementsNumberBeforeDemoting + elementsToHighlight);

        editorToolbarPage().clickUndo();

        //Select elements to promote
        editorPage().scrollToChunk(1);
        editorPage().switchDirectlyToTextFrame();
        String elementXpath = siblingsToHighlight == 0
                ? LOADED_CHUNK_1 + format(subsectionAcrossChunkBorderByNumber, 2)
                : LOADED_CHUNK_1 + format(subsectionAcrossChunkBorderByNumber, 1);
        selectElementsInTheEditor(elementXpath, siblingsToHighlight);
        assertThatThereAreNoPromotedElements(PROMOTED_SUBSECTIONS);

        //Promote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforePromoting = countTreeElements();
        editorToolbarPage().clickPromote();
        assertThatTheSpecificNumberOfPromotedElementsAppliedCorrectly(elementsToHighlight, PROMOTED_SUBSECTIONS);
        assertThatEditorTreeUpdatedCorrectlyAfterPromoting(countTreeElements(),
                treeElementsNumberBeforePromoting - elementsToHighlight);
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("elementsToHighlight")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void promoteAndDemoteInTheTreeSourceLegalTest(int elementsToHighlight)
    {
        String subsectionThree = format(TREE_NODE_CONTAINS_TEXT ,"subsection \"3\"");
        String subsectionFour = format(TREE_NODE_CONTAINS_TEXT ,"subsection \"4\"");
        int siblingsToHighlight = elementsToHighlight - 1;

        //Select elements to demote
        editorTreePage().expandEditorsTreeAndClickNode("source.body", "source.section \"sect; ensp;12\"");
        editorTreePage().click(format(TREE_NODE_CONTAINS_TEXT , "delta section amend"));
        editorTreePage().click(format(TREE_NODE_CONTAINS_TEXT , "part \"Text\""));
        editorTreePage().click(subsectionFour);
        selectElementsInTheTree(subsectionThree, siblingsToHighlight);
        assertThatThereAreNoDemotedElements(DEMOTED_SUBSECTIONS);

        //Demote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforeDemoting = countTreeElements();
        openContextMenuOnElement(subsectionThree);
        editorTreeContextMenu().demote();
        assertThatTheSpecificNumberOfDemotedElementsAppliedCorrectly(elementsToHighlight, DEMOTED_SUBSECTIONS);
        assertThatEditorTreeUpdatedCorrectlyAfterDemoting(countTreeElements(),
                treeElementsNumberBeforeDemoting + elementsToHighlight);

        editorToolbarPage().clickUndo();

        //Select elements to promote
        String elementXpath = siblingsToHighlight == 0
                ? format(TREE_NODE_CONTAINS_TEXT ,"subsection \"5\"") : subsectionFour;
        selectElementsInTheTree(elementXpath, siblingsToHighlight);
        assertThatThereAreNoPromotedElements(PROMOTED_SUBSECTIONS);

        //Promote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforePromoting = countTreeElements();
        openContextMenuOnElement(elementXpath);
        editorTreeContextMenu().scrollToView(EditorTreeContextMenuElements.PROMOTE);
        editorTreeContextMenu().promote();
        assertThatTheSpecificNumberOfPromotedElementsAppliedCorrectly(elementsToHighlight, PROMOTED_SUBSECTIONS);
        assertThatEditorTreeUpdatedCorrectlyAfterPromoting(countTreeElements(),
                treeElementsNumberBeforePromoting - elementsToHighlight);
    }

//  ------------- Assistive methods: -------------

    private int countTreeElements()
    {
        return editorTreePage().countElements(
                format(TREE_NODE_CONTAINS_TEXT, "subsection") + format(PRECEDING_SIBLING, "img"));
    }

    private void selectElementsInTheEditor(String xPath, int number)
    {
        editorTextPage().click(xPath);
        editorTextPage().waitForElement(SUBSECTION + CLASS_HIGHLIGHTED_POSTFIX);
        editorTreePage().highlightFollowingSiblings(number);
    }
}
