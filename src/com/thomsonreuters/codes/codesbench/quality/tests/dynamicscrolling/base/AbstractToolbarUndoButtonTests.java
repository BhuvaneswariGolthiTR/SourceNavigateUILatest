package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;

public abstract class AbstractToolbarUndoButtonTests extends TestService
{
    private static final String TEXT_PARAGRAPH = "Text Paragraph";
    private static final String PARATEXT_XPATH = String.format(SPAN_BY_TEXT + FOLLOWING_SIBLING, TEXT_PARAGRAPH, "paratext");
    private SoftAssertions softAssertions;

    @BeforeEach
    protected abstract void beforeEachTestMethod();

    @AfterEach
    protected void afterEachTestMethod()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /**
     * STORY/BUG - n/a / HALCYONST-9951 <br>
     * SUMMARY - Toolbar Undo button is enabled after splitting, joining, removing text paragraphs and saving <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void toolbarUndoButtonIsEnabledAfterSplittingJoiningRemovingTextParagraphAndSavingLegalTest()
    {
        softAssertions = new SoftAssertions();
        editorPage().switchToEditorTextFrame();

        //Split Text Paragraph
        int paragraphsBeforeSplitting = countParagraphs();
        editorPage().splitParagraphViaContextMenu(PARATEXT_XPATH);
        int paragraphsAfterSplitting = countParagraphs();
        //Assert that Text Paragraph is splitted from one into two after splitting
        softAssertions.assertThat(paragraphsAfterSplitting == paragraphsBeforeSplitting + 1)
                .as(String.format("%s is not splitted", TEXT_PARAGRAPH))
                .isTrue();
        //Assert that Toolbar Undo button is enabled after splitting of the Text Paragraph
        softAssertThatToolbarUndoButtonIsEnabledAfter("splitting paragraph");
        //Click Toolbar Undo button and assert that it is disabled after undoing changes
        softAssertThatToolbarUndoButtonIsDisabledAfterUndoingChanges();
        //Assert that changes restored after clicking Toolbar Undo button
        softAssertThatChangesRestoredAfterClickingToolbarUndoButton(paragraphsBeforeSplitting);

        //Select two Text Paragraphs and join them
        editorPage().switchToEditor();
        editorToolbarPage().clickRedo();
        editorPage().switchToEditorTextFrame();
        int paragraphsBeforeJoining = countParagraphs();
        editorPage().selectMultipleTextParagraphs(1, 2);
        editorPage().joinParagraphsVia("ContextMenu", PARATEXT_XPATH);
        int paragraphsAfterJoining = countParagraphs();
        //Assert that two Text Paragraphs joined into one after joining
        softAssertions.assertThat(paragraphsAfterJoining == paragraphsBeforeJoining - 1)
                .as(String.format("%ss are not joined", TEXT_PARAGRAPH))
                .isTrue();
        //Assert that Toolbar Undo button is enabled after joining of the Text Paragraphs
        softAssertThatToolbarUndoButtonIsEnabledAfter("joining paragraphs");
        //Click Toolbar Undo button
        editorToolbarPage().clickUndo();
        //Assert that changes restored after clicking Toolbar Undo button
        softAssertThatChangesRestoredAfterClickingToolbarUndoButton(paragraphsBeforeJoining);
        //Assert that Toolbar Undo button is disabled after undoing changes
        editorPage().switchToEditor();
        softAssertThatToolbarUndoButtonIsDisabledAfterUndoingChanges();

        //Remove Text Paragraph
        editorPage().switchToEditorTextFrame();
        int paragraphsBeforeRemoving = countParagraphs();
        editorTextPage().scrollToView(String.format(SPAN_BY_TEXT, TEXT_PARAGRAPH));
        editorTextPage().rightClick(String.format(SPAN_BY_TEXT, TEXT_PARAGRAPH));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();
        editorPage().switchToEditorTextFrame();
        int paragraphsAfterRemoving = countParagraphs();
        //Assert that Text Paragraph is removed
        softAssertions.assertThat(paragraphsAfterRemoving < paragraphsBeforeRemoving)
                .as(String.format("%s is not removed", TEXT_PARAGRAPH))
                .isTrue();
        //Assert that Toolbar Undo button is enabled after removing of the Text Paragraph
        softAssertThatToolbarUndoButtonIsEnabledAfter("removing of the Text Paragraph");

        //Click Toolbar Save button
        editorToolbarPage().clickSave();
        //Assert that Toolbar Undo button is still enabled after saving
        softAssertThatToolbarUndoButtonIsEnabledAfter("clicking Toolbar Save button");
        //Click Toolbar Undo button and assert that it is disabled after undoing changes
        softAssertThatToolbarUndoButtonIsDisabledAfterUndoingChanges();
        //Assert that changes restored after clicking Toolbar Undo button
        softAssertThatChangesRestoredAfterClickingToolbarUndoButton(paragraphsBeforeRemoving);

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void softAssertThatToolbarUndoButtonIsEnabledAfter(String message)
    {
        editorPage().switchToEditor();
        softAssertions.assertThat(editorToolbarPage().isToolbarUndoButtonDisabled())
                .as(String.format("Toolbar Undo button should be enabled after %s", message))
                .isFalse();
    }

    private void softAssertThatToolbarUndoButtonIsDisabledAfterUndoingChanges()
    {
        editorToolbarPage().clickUndo();
        softAssertions.assertThat(editorToolbarPage().isToolbarUndoButtonDisabled())
                .as("Toolbar Undo button should be disabled after clicking on it")
                .isTrue();
    }

    private void softAssertThatChangesRestoredAfterClickingToolbarUndoButton(int valueToCompareWith)
    {
        editorPage().switchToEditorTextFrame();
        softAssertions.assertThat(countParagraphs() == valueToCompareWith)
                .as("Changes should be restored after clicking Toolbar Undo button")
                .isTrue();
    }

    private int countParagraphs()
    {
        return editorTextPage().countElements(PARATEXT_XPATH);
    }
}
