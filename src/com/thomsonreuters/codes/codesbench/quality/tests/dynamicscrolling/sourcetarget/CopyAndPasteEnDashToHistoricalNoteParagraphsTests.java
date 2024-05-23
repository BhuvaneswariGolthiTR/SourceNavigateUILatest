package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.sourcetarget;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.HYPHEN;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.NDASH;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CopyAndPasteEnDashToHistoricalNoteParagraphsTests extends AbstractSourceTargetTests
{

    private static final String IOWA_TARGET_NODE_UUID_1 = "I0BBB4370158011DA8AC5CD53670E6B4E";
    private static final String IOWA_TARGET_NODE_UUID_2 = "I0BB68880158011DA8AC5CD53670E6B4E";
    private static final String IOWA_SOURCE_RENDITION_UUID = "I9EDFDDD01C3411EAACEADA33E1A31C61";

    private static final String HIST_NOTE_PARA_SPAN_WITH_INSERTED_EN_DASH = HISTORICAL_NOTE_PARATEXT + "/span";
    private static final String HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH = PARA + "[contains(@class,' ')]" + PARATEXT_SPAN;

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteEnDashFromExistedHistBlockToCreatedHistBlockInTargetDocTest()
    {
        // Go to Hierarchy -> Navigate
        // Filter for Target Doc and edit it via the DS editor
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_UUID_1);
        editorPage().switchDirectlyToTextFrame();

        // Insert EN dash symbol to the Historical Note Paragraph
        placeCursorInHistoricalNoteParagraph();
        insertEnDash();

        assertThatEnDashIsInsertedIntoText(HIST_NOTE_PARA_SPAN_WITH_INSERTED_EN_DASH);

        // Highlight inserted EN dash symbol along with the text and copy highlighted fragment
        highlightAndCopyEnDashAlongWithText();
        // Paste it in another location within the document
        editorTextPage().altAUsingAction();
        insertHistoricalNoteBlockWithHotkeysAndPasteCopiedText();

        assertThatEnDashIsInsertedIntoText(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);

        assertThatEnDashIsNotChangedToHyphen(HIST_NOTE_PARA_SPAN_WITH_INSERTED_EN_DASH);
        assertThatEnDashIsNotChangedToHyphen(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteEnDashInHistBlocksBetweenTwoTargetDocsTest()
    {
        // Go to Hierarchy -> Navigate
        // Filter for Target Docs 1 & 2 and edit them via the DS editor
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_UUID_1);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_UUID_2);

        // Insert EN dash symbol to the Historical Note Paragraph of the Target Doc 1
        switchToFirstDocument();
        placeCursorAtTheBeginningOfTextParagraph();
        placeCursorInHistoricalNoteParagraph();
        insertEnDash();

        assertThatEnDashIsInsertedIntoText(HIST_NOTE_PARA_SPAN_WITH_INSERTED_EN_DASH);

        // Copy inserted EN dash from the Hist Note Para
        highlightAndCopyEnDashAlongWithText();

        // Paste EN dash to the Hist Note Para of the Target Doc 2
        switchToSecondDocument();
        placeCursorInHistoricalNoteParagraph();
        RobotUtils.altAUsingRobot();
        insertHistoricalNoteBlockWithHotkeysAndPasteCopiedText();

        assertThatEnDashIsInsertedIntoText(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);

        assertThatEnDashIsNotChangedToHyphen(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);
        switchToFirstDocument();
        assertThatEnDashIsNotChangedToHyphen(HIST_NOTE_PARA_SPAN_WITH_INSERTED_EN_DASH);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteEnDashFromSourceDocToCreatedHistBlockInTargetDocTest()
    {
        // Go to Source Navigate
        // Filter for Source Doc and edit it via the DS editor
        openSourceRenditionInDsEditor(IOWA_SOURCE_RENDITION_UUID);

        // Go to Hierarchy Navigate
        // Filter for Target Doc and edit it via the DS editor
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_UUID_1);

        // insert EN dash and copy it
        switchToFirstDocument();

        placeCursorAtTheBeginningOfTextParagraph();
        insertEnDash();
        highlightAndCopyEnDashAlongWithText();

        assertThatEnDashIsInsertedIntoText(PARATEXT_SPAN);

        // Insert Historical Note Body with Alt+A and paste text from the clipboard
        switchToSecondDocument();
        placeCursorInHistoricalNoteParagraph();
        RobotUtils.altAUsingRobot();
        insertHistoricalNoteBlockWithHotkeysAndPasteCopiedText();

        assertThatEnDashIsInsertedIntoText(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);

        assertThatEnDashIsNotChangedToHyphen(HIST_NOTE_PARA_SPAN_WITH_PASTED_EN_DASH);
        switchToFirstDocument();
        assertThatEnDashIsNotChangedToHyphen(PARATEXT_SPAN);
    }

    // ---------- Assertions ----------

    private void assertThatEnDashIsInsertedIntoText(String xpath)
    {
        assertThat(editorTextPage()
                .getElement(xpath)
                .getAttribute("entity"))
                .as(format("There should be %s entity", NDASH.getEntity()))
                .isEqualTo(NDASH.getEntity());
    }

    private void assertThatEnDashIsNotChangedToHyphen(String xpath)
    {
        assertThat(editorTextPage()
                .getElement(xpath)
                .getAttribute("entity"))
                .as(format("There should be %s entity", NDASH.getEntity()))
                .isNotEqualTo(HYPHEN.getEntity());
    }

    // ---------- Assistive methods ----------

    private void openTargetNodeInDsEditorWithUuid(String nodeUuid)
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    private void placeCursorInHistoricalNoteParagraph()
    {
        editorPage().click(EditorTextPageElements.HISTORICAL_NOTE_PARATEXT);
        editorTextPage().waitForEditorElementSelected(EditorTextPageElements.HISTORICAL_NOTE_PARATEXT);

        RobotUtils.pressHomeUsingRobot();
    }

    private void insertEnDash()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickInsertSpecialCharacter();
        insertSpecialCharacterPage().sendTextInXmlEntity(NDASH.getHtml());
        insertSpecialCharacterPage().clickInsert();
        editorPage().switchDirectlyToTextFrame();
    }

    private void highlightAndCopyEnDashAlongWithText()
    {
        editorTextPage().sendKeys(Keys.ARROW_LEFT);
        editorTextPage().highlightHelper(20);
        editorTextPage().ctrlCUsingAction();
        editorTextPage().waitForPageLoaded();
    }

    private void insertHistoricalNoteBlockWithHotkeysAndPasteCopiedText()
    {
        editorTextPage().waitForElement(editorTextPage().getElement("//span[contains(text(),'Historical Note Paragraph')]"));
        editorTextPage().click("//span[contains(text(),'Historical Note Paragraph')]");
        editorTextPage().waitForEditorElementSelected(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        RobotUtils.pressDownArrowUsingRobot();
        RobotUtils.pressHomeUsingRobot();
        editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().ctrlVUsingAction();
    }

    private void placeCursorAtTheBeginningOfTextParagraph()
    {

        editorPage().click(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT);
        editorTextPage().waitForEditorElementSelected(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT);

        RobotUtils.pressHomeUsingRobot();
    }
}
