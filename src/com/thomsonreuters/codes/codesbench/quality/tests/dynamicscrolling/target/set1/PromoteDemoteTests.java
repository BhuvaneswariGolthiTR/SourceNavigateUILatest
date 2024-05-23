package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

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
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HISTORICAL_NOTE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HISTORICAL_NOTE_BLOCK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HISTORICAL_NOTE_BODY;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.treeElements.AMENDMENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.treeElements.HIST_NOTE_BODY;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;

public class PromoteDemoteTests extends AbstractPromoteDemoteTests
{
    private static final String UUID = "I9491434014F211DA8AC5CD53670E6B4E";
    private static final String DEMOTED_HISTORICAL_NOTES = HISTORICAL_NOTE_BODY + "/hist.note/hist.note";
    private static final String PROMOTED_HISTORICAL_NOTES = HISTORICAL_NOTE_BLOCK + "/hist.note";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("elementsToHighlight")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void promoteAndDemoteInTheEditorTargetLegalTest(int elementsToHighlight)
    {
        String historicalNoteAcrossChunkBorderByNumber = HISTORICAL_NOTE + "[%d]/span";
        String historicalNoteFive = format(historicalNoteAcrossChunkBorderByNumber, 5);
        int siblingsToHighlight = elementsToHighlight - 1;

        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        editorPage().switchToEditor();

        //Select elements to demote
        editorPage().scrollToChunk(2);
        selectElementsInTheEditor(historicalNoteFive, siblingsToHighlight);
        assertThatThereAreNoDemotedElements(DEMOTED_HISTORICAL_NOTES);

        //Demote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforeDemoting = countTreeElements();
        editorToolbarPage().clickDemote();
        editorTextPage().waitForPageLoaded();
        assertThatTheSpecificNumberOfDemotedElementsAppliedCorrectly(elementsToHighlight, DEMOTED_HISTORICAL_NOTES);
        assertThatEditorTreeUpdatedCorrectlyAfterDemoting(countTreeElements(),
                treeElementsNumberBeforeDemoting + elementsToHighlight);
        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorTextPage().waitForPageLoaded();


        //Select elements to promote
        String elementXpath = siblingsToHighlight == 0
                ? LOADED_CHUNK_1 + format(historicalNoteAcrossChunkBorderByNumber, 2) : historicalNoteFive;

        editorPage().jumpToBeginningOfDocument();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(2);
        selectElementsInTheEditor(elementXpath, siblingsToHighlight);
        assertThatThereAreNoPromotedElements(PROMOTED_HISTORICAL_NOTES);

        //Promote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforePromoting = countTreeElements();
        editorToolbarPage().clickPromote();
        editorTextPage().waitForPageLoaded();
        assertThatTheSpecificNumberOfPromotedElementsAppliedCorrectly(elementsToHighlight, PROMOTED_HISTORICAL_NOTES);
        assertThatEditorTreeUpdatedCorrectlyAfterPromoting(countTreeElements(),
                treeElementsNumberBeforePromoting - elementsToHighlight);
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("elementsToHighlight")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void promoteAndDemoteInTheTreeTargetLegalTest(int elementsToHighlight)
    {
        String historicalNoteAcrossChunkBorderByNumber = AMENDMENT.getXpath() + "[%d]"
                + format("/a[text()='%s']", AMENDMENT.getNodeName());
        String historicalNoteSix = format(historicalNoteAcrossChunkBorderByNumber, 6);
        String historicalNoteFive = format(historicalNoteAcrossChunkBorderByNumber, 5);
        int siblingsToHighlight = elementsToHighlight - 1;

        //Select elements to demote
        editorTreePage().expandEditorsTreeAndClickNode("annotations", "hist.note.block");
        editorTreePage().click(HIST_NOTE_BODY.getXpath()
                + format("[2]/a[text()='%s']", HIST_NOTE_BODY.getNodeName()));
        editorTreePage().click(historicalNoteSix);
        selectElementsInTheTree(historicalNoteFive, siblingsToHighlight);
        assertThatThereAreNoDemotedElements(DEMOTED_HISTORICAL_NOTES);

        //Demote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforeDemoting = countTreeElements();
        openContextMenuOnElement(historicalNoteFive);
        editorTreeContextMenu().demote();
        assertThatTheSpecificNumberOfDemotedElementsAppliedCorrectly(elementsToHighlight, DEMOTED_HISTORICAL_NOTES);
        assertThatEditorTreeUpdatedCorrectlyAfterDemoting(countTreeElements(),
                treeElementsNumberBeforeDemoting + elementsToHighlight);

        editorToolbarPage().clickUndo();

        //Select elements to promote
        String elementXpath = siblingsToHighlight == 0 ? historicalNoteSix : historicalNoteFive;
        selectElementsInTheTree(elementXpath, siblingsToHighlight);
        assertThatThereAreNoPromotedElements(PROMOTED_HISTORICAL_NOTES);

        //Promote selected elements
        editorPage().switchToEditor();
        int treeElementsNumberBeforePromoting = countTreeElements();
        openContextMenuOnElement(elementXpath);
        editorTreeContextMenu().promote();
        assertThatTheSpecificNumberOfPromotedElementsAppliedCorrectly(elementsToHighlight, PROMOTED_HISTORICAL_NOTES);
        assertThatEditorTreeUpdatedCorrectlyAfterPromoting(countTreeElements(),
                treeElementsNumberBeforePromoting - elementsToHighlight);
    }

//  ------------- Assistive methods: -------------

    private int countTreeElements()
    {
        return editorTreePage().countElements(AMENDMENT.getXpath() + "/img");
    }

    private void selectElementsInTheEditor(String xPath, int number)
    {
        editorTextPage().click(xPath);
        editorTextPage().waitForElement(HISTORICAL_NOTE + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().highlightFollowingSiblingsUsingRobotF7(number);
    }
}
