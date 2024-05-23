package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class UndoRedoLegalTests extends TestService
{
    String uuid = "IC4105FD0F67911E3B148A5B04553906B";

    /* Undo and redo via hotkeys (ctrl + z, and ctrl + y)
     * 1. Open document
     * 2. Scroll to target chunk
     * VERIFY: Since we have made any changes yet, the undo and redo buttons should be disabled
     * 3. Insert text and apply markup to it in an existing text paragraph, click ctrl+s
     * VERIFY: Undo button should be enabled after change done
     * 4. Add another phrase
     * 5. Ctrl + z to perform undo
     * VERIFY: After Undo second phrase should disappear and first phrase should be displayed
     * VERIFY: Redo button should be enabled after undo
     * 6. Ctrl + y to perform redo
     * VERIFY: After Redo second phrase should appear again
     * 7. Close and discard changes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void undoRedoHotkeysSourceLegalTest()
    {
        //String uuid = "I8B1C2100BA6111E797B6DB99DFD08357"; // for dev
        int targetChunkNumber = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        String targetPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunkNumber - 1, 1);
        String targetParaSpan = targetPara + EditorTextPageElements.SPAN;
        String targetParaParatext = targetPara + EditorTextPageElements.PARATEXT;

        String phrase1 = "BeforeFirstSavePoint ";
        //scroll to chuck and check if file is corrupted
        editorPage().tryToDiscardChangesAndReopenCorruptedFile(targetParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1), targetChunkNumber);
        // verify Undo-Redo disabled now
        editorTextPage().breakOutOfFrame();
        boolean undoRedoDisabledBeforeChanges = editorPage().isElementDisabled(EditorToolbarPageElements.UNDO)
                && editorPage().isElementDisabled(EditorToolbarPageElements.REDO);
        // add a phrase and mark it up
        editorTextPage().switchToEditorTextArea();
        editorTextPage().insertPhraseUnderGivenLabel(phrase1, targetParaSpan);
        editorTextPage().highlightHelper(phrase1);
        editorToolbarPage().clickAddMarkup();
        // init savepoint
        editorPage().switchToEditor();
        editorPage().createSavepointViaHotkeys();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.UNDO);
        boolean undoEnabledAfterChange = !editorPage().isElementDisabled(EditorToolbarPageElements.UNDO);
        // add another phrase
        editorPage().switchToEditorTextFrame();
        String phrase2 = "AfterFirstSavePoint ";
        editorTextPage().insertPhraseUnderGivenLabel(phrase2, targetParaSpan);
        // init new savepoint
        editorPage().switchToEditor();
        editorPage().createSavepointViaHotkeys();
        // hit Undo
        editorPage().switchToEditor();
        editorPage().undoChangesViaHotkeys();
        editorPage().switchToEditorTextFrame();
        editorTextPage().click(targetParaSpan);
        boolean afterUndoPhrase2RemovedPhrase1Persists = editorPage().doesElementExist(targetParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && !editorPage().getElementsText(targetParaParatext).contains(phrase2);
        // redo
        editorPage().switchToEditor();
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.REDO);
        boolean redoEnabledAfterUndo = !editorPage().isElementDisabled(EditorToolbarPageElements.REDO);
        editorPage().redoChangesViaHotkeys();
        editorPage().switchToEditorTextFrame();
        boolean afterRedoSecondPhraseIsBeingDisplayedAgain = editorPage().doesElementExist(targetParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && editorPage().getElementsText(targetParaParatext).contains(phrase2);
        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(undoRedoDisabledBeforeChanges, "Undo and Redo buttons should be disabled before any changes done"),
                () -> Assertions.assertTrue(undoEnabledAfterChange, "Undo button should be enabled after change done"),
                () -> Assertions.assertTrue(afterUndoPhrase2RemovedPhrase1Persists,
                "After Undo second phrase should disappear and first phrase should be displayed"),
                () -> Assertions.assertTrue(redoEnabledAfterUndo, "Redo button should be enabled after undo"),
                () -> Assertions.assertTrue(afterRedoSecondPhraseIsBeingDisplayedAgain, "After Redo second phrase should appear again")
        );
    }

    /*
     * 1. Open document
     * 2. Scroll to target chunk
     * VERIFY: Since we have made any changes yet, the undo and redo buttons should be disabled
     * 3. Insert text and apply markup to it in an existing text paragraph. Click Save toolbar button
     * VERIFY: Since we made a change, the undo button should be enabled
     * 4. Add another phrase and click Save toolbar button
     * 5. Click Undo toolbar button
     * VERIFY: After Undo second phrase should disappear and first phrase should be displayed
     * VERIFY: Redo button should be enabled after undo
     * 6. Click Redo toolbar button
     * VERIFY: After Redo second phrase should appear again
     * 7. Close and discard changes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void undoRedoToolbarSourceLegalTest()
    {
        //String uuid = "I8B1C2100BA6111E797B6DB99DFD08357"; // for dev
        int targetChunkNumber = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        String targetPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunkNumber - 1, 1);
        String targetParaSpan = targetPara + EditorTextPageElements.SPAN;
        String targetParaParatext = targetPara + EditorTextPageElements.PARATEXT;

        String phrase1 = "BeforeFirstSavePoint ";
        //scroll to chuck and check if file is corrupted
        editorPage().tryToDiscardChangesAndReopenCorruptedFile(targetParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1), targetChunkNumber);
        // verify Undo-Redo disabled now
        editorTextPage().breakOutOfFrame();
        boolean undoRedoDisabledBeforeChanges = editorPage().isElementDisabled(EditorToolbarPageElements.UNDO)
                && editorPage().isElementDisabled(EditorToolbarPageElements.REDO);
        // add a phrase and mark it up
        editorTextPage().switchToEditorTextArea();
        editorTextPage().insertPhraseUnderGivenLabel(phrase1, targetParaSpan);
        editorTextPage().highlightHelper(phrase1);
        editorToolbarPage().clickAddMarkup();
        // init savepoint
        editorToolbarPage().clickSave();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.UNDO);
        boolean undoEnabledAfterChange = !editorPage().isElementDisabled(EditorToolbarPageElements.UNDO);
        // add another phrase
        editorPage().switchToEditorTextFrame();
        String phrase2 = "AfterFirstSavePoint ";
        editorTextPage().insertPhraseUnderGivenLabel(phrase2, targetParaSpan);
        // init new savepoint
        editorToolbarPage().clickSave();
        // hit Undo
        editorToolbarPage().clickUndo();
        editorPage().switchToEditorTextFrame();
        boolean afterUndoPhrase2RemovedPhrase1Persists = editorPage().doesElementExist(targetParaParatext +
                String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && !editorPage().getElementsText(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT).contains(phrase2);
        // redo
        editorPage().switchToEditor();
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.REDO);
        boolean redoEnabledAfterUndo = !editorPage().isElementDisabled(EditorToolbarPageElements.REDO);
        editorToolbarPage().clickRedo();
        editorPage().switchToEditorTextFrame();
        boolean afterRedoSecondPhraseIsBeingDisplayedAgain = editorPage().doesElementExist(targetParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && editorPage().getElementsText(targetParaParatext).contains(phrase2);
        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(undoRedoDisabledBeforeChanges, "Undo and Redo buttons should be disabled before any changes done"),
                () -> Assertions.assertTrue(undoEnabledAfterChange, "Undo button should be enabled after change done"),
                () -> Assertions.assertTrue(afterUndoPhrase2RemovedPhrase1Persists,
                "After Undo second phrase should disappear and first phrase should be displayed"),
                () -> Assertions.assertTrue(redoEnabledAfterUndo, "Redo button should be enabled after undo"),
                () -> Assertions.assertTrue(afterRedoSecondPhraseIsBeingDisplayedAgain,
                "After Redo second phrase should appear again")
        );
    }
}
