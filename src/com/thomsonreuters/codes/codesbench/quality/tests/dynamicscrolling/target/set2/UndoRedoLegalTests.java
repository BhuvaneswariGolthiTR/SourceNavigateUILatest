package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class UndoRedoLegalTests extends TestService
{
    String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";

    /*Undo and redo changes
     *NOTE: Undo and redo depend on savepoints in the document, so we have to make sure to click the save icon in the toolbar
     * 1. Open document
     * 2. Scroll to chunk 2 or 3
     * VERIFY: Since we have made any changes yet, the undo and redo buttons should be disabled
     * 3. Insert text and apply markup to it in an existing text paragraph
     * VERIFY: Since we made a change, the undo button should be enabled
     * 3.1. Click the save button
     * 4. Insert text in a different existing text paragraph
     * 5. Maybe even insert a new paragraph
     * 6. Click the save icon in the toolbar
     * 7. Click the undo button
     * VERIFY: The changes made in between the first and second savepoint are now removed, but the marked up text still appears
     * VERIFY: The redo button is enabled
     * 8. Click Redo
     * VERIFY: The changes made reappear and the marked up text is still there
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void undoRedoTargetLegalTest()
    {
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        int targetChunk = 1;

        // verify Undo-Redo disabled now
        boolean undoRedoDisabledBeforeChanges = editorToolbarPage().isElementDisabled(
                EditorToolbarPageElements.UNDO)
                && editorToolbarPage().isElementDisabled(EditorToolbarPageElements.REDO);

        // scroll to chunk
        editorPage().switchToEditorTextFrame();

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;
        String firstParaParatext = firstPara + EditorTextPageElements.PARATEXT;

        // add a phrase and mark it up
        String phrase1 = "BeforeFirstSavePoint";
        // insert a misspelled word
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase1, firstParaSpan);
        editorTextPage().highlightHelper(phrase1);
        editorToolbarPage().clickAddMarkup();

        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.UNDO);
        boolean undoEnabledAfterChange = !editorToolbarPage().isElementDisabled(
                EditorToolbarPageElements.UNDO);

        // init savepoint
        editorToolbarPage().clickSave();
        
        // add another phrase
        editorPage().switchToEditorTextFrame();
        String phrase2 = "AfterFirstSavePoint";
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase2, firstParaSpan);

        // init new savepoint
        editorToolbarPage().clickSave();

        // hit Undo
        editorToolbarPage().clickUndo();
        editorPage().switchToEditorTextFrame();
        boolean afterUndoPhrase2RemovedPhrase1Persists = editorTextPage().doesElementExist(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && !editorTextPage().getElementsText(firstParaParatext).contains(phrase2);

        // redo
        editorPage().breakOutOfFrame();
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.REDO);
        boolean redoEnabledAfterUndo = !editorToolbarPage().isElementDisabled(
                EditorToolbarPageElements.REDO);
        editorToolbarPage().clickRedo();
        editorPage().switchToEditorTextFrame();
        boolean afterRedoSecondPhraseIsBeingDisplayedAgain = editorTextPage().doesElementExist(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && editorTextPage().getElementsText(firstParaParatext).contains(phrase2);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(undoRedoDisabledBeforeChanges,
                        "Undo and Redo buttons should be disabled before any changes done"),
                () -> Assertions.assertTrue(undoEnabledAfterChange, 
                        "Undo button should be enabled after change done"),
                () -> Assertions.assertTrue(afterUndoPhrase2RemovedPhrase1Persists,
                        "After Undo second phrase should disappear and first phrase should be displayed"),
                () -> Assertions.assertTrue(redoEnabledAfterUndo, "Redo button should be enabled after undo"),
                () -> Assertions.assertTrue(afterRedoSecondPhraseIsBeingDisplayedAgain,
                        "After Redo second phrase should appear again")
        );
    }

    /*Undo and redo via hotkeys (ctrl + z, and ctrl + y)
     * follow similar steps as above, but don't need to save the document in between
     * 1. Open document
     * 2. Scroll to chunk 2 or 3
     * 3. Insert text and apply markup to it in an existing text paragraph
     * VERIFY: The text and markup appears in that paragraph
     * 4. Ctrl + z to perform undo
     * VERIFY: The text and markup no longer appears in that paragraph
     * 5. Ctrl + y to perform redo
     * VERIFY: The text and markup appears in that paragraph
     * 6. Close and discard changes
     */

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void undoRedoHotkeysTargetLegalTest()
    {
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        int targetChunk = 1;

        // verify Undo-Redo disabled now
        boolean undoRedoDisabledBeforeChanges =
                editorToolbarPage().isElementDisabled(EditorToolbarPageElements.UNDO)
                && editorToolbarPage().isElementDisabled(EditorToolbarPageElements.REDO);

        // scroll to chunk
        editorPage().switchToEditorTextFrame();

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;
        String firstParaParatext = firstPara + EditorTextPageElements.PARATEXT;

        // add a phrase and mark it up
        String phrase1 = "BeforeFirstSavePoint";
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase1, firstParaSpan);
        editorTextPage().highlightHelper(phrase1);
        editorToolbarPage().clickAddMarkup();
        editorPage().switchToEditorTextFrame();
        editorTextPage().waitForElement(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1));

        // init savepoint
        editorTextPage().ctrlSUsingAction();
        editorTextPage().waitForElement(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1));
        editorPage().breakOutOfFrame();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.UNDO);
        boolean undoEnabledAfterChange = !editorToolbarPage().isElementDisabled(
                EditorToolbarPageElements.UNDO);

        // add another phrase
        editorPage().switchToEditorTextFrame();
        String phrase2 = "AfterFirstSavePoint";
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase2, firstParaSpan);
        editorTextPage().waitForElement(firstPara
                + String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN, phrase2));
        // init new savepoint
        editorTextPage().ctrlSUsingAction();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        // hit Undo
        editorTextPage().ctrlZUsingAction();

        boolean afterUndoPhrase2RemovedPhrase1Persists = editorTextPage().doesElementExist(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && !editorTextPage().getElementsText(firstParaParatext).contains(phrase2);

        // redo
        editorPage().breakOutOfFrame();
        editorToolbarPage().waitForElementToBeEnabled(EditorToolbarPageElements.REDO);
        boolean redoEnabledAfterUndo =
                !editorToolbarPage().isElementDisabled(EditorToolbarPageElements.REDO);
        editorTextPage().ctrlYUsingAction();

        editorPage().switchToEditorTextFrame();
        boolean afterRedoSecondPhraseIsBeingDisplayedAgain = editorTextPage().doesElementExist(firstParaParatext
                + String.format(EditorTextPageElements.ADDED_TEXT, phrase1))
                && editorTextPage().getElementsText(firstParaParatext).contains(phrase2);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(undoRedoDisabledBeforeChanges,
                        "Undo and Redo buttons should be disabled before any changes done"),
                () -> Assertions.assertTrue(undoEnabledAfterChange,
                        "Undo button should be enabled after change done"),
                () -> Assertions.assertTrue(afterUndoPhrase2RemovedPhrase1Persists,
                        "After Undo second phrase should disappear and first phrase should be displayed"),
                () -> Assertions.assertTrue(redoEnabledAfterUndo, "Redo button should be enabled after undo"),
                () -> Assertions.assertTrue(afterRedoSecondPhraseIsBeingDisplayedAgain,
                        "After Redo second phrase should appear again")
        );
    }
}
