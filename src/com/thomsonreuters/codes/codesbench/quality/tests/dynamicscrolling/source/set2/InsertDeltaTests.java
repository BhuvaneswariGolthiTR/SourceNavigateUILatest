package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InsertDeltaTests extends TestService
{
    public enum DeltaInsertMethod
    {
        DELTA_ADD_SECTION(EditorTextPageElements.DELTA_ADD_SECTION, "add", "section", "Add Section"),
        DELTA_DELETE_SECTION(EditorTextPageElements.DELTA_DELETE_SECTION, "delete", "section", "Delete Section"),
        DELTA_AMEND_SECTION(EditorTextPageElements.DELTA_AMEND_SECTION, "amend", "section", "Amend Section"),
        DELTA_ADD_SUBSECTION(EditorTextPageElements.DELTA_ADD_SUBSECTION, "add", "subsection", "Add Subsection"),
        DELTA_ADD_GRADE(EditorTextPageElements.DELTA_ADD_GRADE, "add", "grade", "Add Grade");

        private final String xpath;
        private final String deltaAction;
        private final String deltaLevel;
        private final String description;

        DeltaInsertMethod(String xpath, String deltaAction, String deltaLevel, String description)
        {
            this.xpath = xpath;
            this.deltaAction = deltaAction;
            this.deltaLevel = deltaLevel;
            this.description = description;
        }

        public String getXpath()
        {
            return xpath;
        }

        public String getDeltaAction()
        {
            return deltaAction;
        }

        public String getDeltaLevel()
        {
            return deltaLevel;
        }

        public String getDescription()
        {
            return description;
        }
    }

    /**
     * Insert Delta Subsection Add
     * 1. Open document
     * 2. Scroll down to a Source Section element
     * 3. Select and right click the source section
     * 4. Select Insert Delta
     * VERIFY: Insert Delta Window appears
     * 5. Set action
     * 6. Set level
     * 7. Set Target Location: Section
     * 8. Click Ok
     * VERIFY: Insert Delta window no longer appears
     * VERIFY: Delta "action" "level" was inserted
     * VERIFY: Target Location element is a child of Delta "action" "level"
     * This might have to be shown as selected after insertion
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"DELTA_ADD_SECTION", "DELTA_DELETE_SECTION", "DELTA_AMEND_SECTION", "DELTA_ADD_SUBSECTION",
                          "DELTA_ADD_GRADE"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertDeltaSourceLegalTest(DeltaInsertMethod deltaInsertMethod)
    {
        String uuid = "I56B71CE0BB2311E7AE1DDC37F9621E15";
        int chunkNumber = 3;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        String section = EditorTextPageElements.SOURCE_SECTION;
        String insertedSectionXpath = section + deltaInsertMethod.getXpath();
        String insertedTargetLocationXpath = section + deltaInsertMethod.getXpath() +
                EditorTextPageElements.TARGET_LOCATION;

        editorPage().scrollToElement(EditorTextPageElements.SOURCE_SECTION);

        int sectionQuantityBefore = editorTextPage().countElements(insertedSectionXpath);
        int targetQuantityBefore = editorTextPage().countElements(insertedTargetLocationXpath);

        //add delta
        editorTextPage().scrollToView(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().breakOutOfFrame();

        //insert delta
        boolean windowAppeared = editorTextContextMenuPage().insertDelta();
        Assertions.assertTrue(windowAppeared, "Content Editorial System Window should appear");
        insertDeltaPage().setAction(deltaInsertMethod.getDeltaAction());
        insertDeltaPage().setLevel(deltaInsertMethod.getDeltaLevel());
        insertDeltaPage().setTargetLocationCode(false);
        insertDeltaPage().setTargetLocationSection(true);
        insertDeltaPage().setTargetLocationSubsection(false);
        insertDeltaPage().clickSave();

        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();

        int sectionQuantityAfter = editorTextPage().countElements(insertedSectionXpath);
        int targetQuantityAfter = editorTextPage().countElements(insertedTargetLocationXpath);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(sectionQuantityAfter,
                                sectionQuantityBefore + 1, String.format("Delta %s should be added",
                                        deltaInsertMethod.getDescription())),
                        () -> Assertions.assertEquals(targetQuantityAfter,
                                targetQuantityBefore + 1, String.format(
                                        "Delta Target Location should be added and should be a child of Delta %s",
                                        deltaInsertMethod.getDescription()))
                );
    }

    // HALCYONST-2231
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changeDeltasActionSourceLegalTest()
    {
        String uuid = "I141F23F07A5711EA91DDA15824C9FC6E";
        int chunkNumber = 2;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        // change delta's action
        editorTextPage().click(EditorTextPageElements.DELTA_AMEND_SUBSECTION_LABEL);
        editorTextPage().rightClick(EditorTextPageElements.DELTA_AMEND_SUBSECTION_LABEL);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().editAttributes();
        editAttributesPage().setFirstInput("add");
        editAttributesPage().clickSave();

        // check
        editorPage().switchToEditorTextFrame();
        boolean labelChanged = editorTextPage().doesElementExist(EditorTextPageElements.DELTA_AMEND_SUBSECTION_LABEL);

        editorPage().switchToEditor();
        boolean warnsOrErrorsAppeared = editorMessagePage().doesElementExist(EditorMessagePageElements.WARN_SPAN)
                || editorTextPage().doesElementExist(EditorMessagePageElements.ERROR_SPAN);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(labelChanged, "Label should be changed correctly"),
                        () -> Assertions.assertFalse(warnsOrErrorsAppeared, "No warns or errors should appear")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pasteAsFeatureChildSourceLegalTest()
    {
        String uuid = "IC4C0277E6B5F11E49A3ADD5E7AC5244D";
        String textToPaste = String.format("Test %s", System.currentTimeMillis());
        String sourceSectionNumber = "Sec. 105";
        int chunkNumber = 1;

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        editorPage().scrollToChunk(chunkNumber);

        String deltaAmendSubsection = String.format(EditorTextPageElements.SECTION_BY_NUMBER, sourceSectionNumber)
                + EditorTextPageElements.DELTA_AMEND_SUBSECTION_LABEL;
        editorTextPage().click(deltaAmendSubsection);
        editorTextPage().rightClick(deltaAmendSubsection);
        ClipboardUtils.setSystemClipboard(textToPaste);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(
                EditorTextContextMenuElements.PASTE_AS,
                EditorTextContextMenuElements.FEATURE_CHILD,
                EditorTextContextMenuElements.HISTORICAL_NOTE,
                EditorTextContextMenuElements.CHANGE_OF_NAME);
        editorPage().switchToEditor();

        List<String> logInfoElementsTextContentList = new ArrayList<>();
        editorTextPage().getElements(EditorTextPageElements.LOG_INFO_XPATH).forEach(element ->
                logInfoElementsTextContentList.add(element.getText()));

        editorPage().switchDirectlyToTextFrame();
        String historicalNoteParaTextContent = editorTextPage().getHistoricalNoteParaTextContent();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                () -> logInfoElementsTextContentList.forEach(textContent -> Assertions.assertEquals("info", textContent,
                        "Expected value doesn't match actual")),
                () -> Assertions.assertTrue(historicalNoteParaTextContent.contains(textToPaste),
                        String.format("Historical note para text content doesn't contain [%s]", textToPaste)));
    }

    /**
     * HALCYONST-12878/13075: Nav pane display of target location value when Delta starts in a different chunk/
     * If Target Location block is selected in the text, nothing is highlighted in the tree (Source)
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void verifyDeltaAddNoteAcrossChunksTest()
    {
        String uuid = "IF93A5BE27B9F11EBA0B4D34B1DA9F369";
        String nodeName = "source.body";
        String[] nodesToExpand = {"source.body", "source.section \"1\"", "(delta note.classified add)136.181"};
        String section = EditorTextPageElements.SOURCE_SECTION;
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        assertThat(editorTreePage().isNodeExpanded(nodeName))
                .as(String.format("The [%s] node should be expanded", nodeName))
                .isFalse();
        editorTreePage().expandEditorsTreeAndClickNode(nodesToExpand);
        boolean isDeltaHighlighted = editorTextPage()
                .doesElementExist(EditorTextPageElements.HIGHLIGHTED_DELTA);
        Assertions.assertFalse(isDeltaHighlighted, "There should be no highlighting");
    }
}
