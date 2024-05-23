package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OpenAndCheckInTests extends TestService {

    /*Open and save changes
     * 1. Open the document
     * 2. Verify: The document contains content in the tree
     * 3. Verify: The document contains content in the editor content area
     * 4. Verify: The document contains information in the message pane
     * 5. Scroll to chunk 2 or 3
     * 6. Insert simple text into a paragraph here
     * 6.1. Verify: Modified by tags appear
     * 7. Click the Save button in the toolbar
     * 7.1. Verify: The content change saves (verify message appears in message pane)
     * 8. Close the current window via the upper right close button
     * 8.a. When we first open a document, a lock is set on the document and nobody can access it but the person that locked it.
     * When we save and just close the editor, we're keeping the lock on the document so the user can go back in to make additional changes later on
     * 9. Re-open the document
     * 10. Scroll to chunk 2 or 3
     * 11. Verify: The change is there
     * 12. Close and discard changes
     */

    String uuid = "I72850F400AD711E99F0DFCA8425EDB3F";

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openAndSaveChangesSourceLegalTest()
    {
        //String uuid = "I46CD65F0F48511E2947EC10AE072603D"; // for dev
        String word = "Banana";
        int targetChunk = 2;
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // check tree, textarea, message pane
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        // scroll
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);

        // insert a word
        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaHighlighted = String.format(EditorTextPageElements.HIGHLIGHTED_PARA_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        editorTextPage().insertPhraseUnderGivenLabelWithHome(word, firstParaSpan);
        editorPage().click(firstParaSpan);

        // check modified by
        String modifiedByActual = editorPage().getElementsText(firstParaHighlighted +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        // init save
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickSave();

        // check message about saving in message pane
        boolean saveMessageAppearedInMessagePane = editorPage().waitForElementExists(EditorMessagePageElements.SAVE_MESSAGE);

        // reopen editor and check phrase inserted
        editorPage().closeEditorWindowAndReopenRenditionForEdit();
        editorPage().scrollToChunk(targetChunk);
        boolean wordIsStillHere = editorPage().getElementsText(firstPara + EditorTextPageElements.PARATEXT)
                .contains(word);
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActual,
                                "Modified By tag should contain right name and date"),
                        () -> Assertions.assertTrue(saveMessageAppearedInMessagePane,
                                "Message Pane should present a message about save initiated"),
                        () -> Assertions.assertTrue(wordIsStillHere,
                                "Inserted word should be presented in the text after reopening")
                );
    }

    /**
     * STORY/BUG - ???? <br>
     * SUMMARY - A test to verify that changes have been saved after check-in (Source->Navigate. Rendition level. DS editor). <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openSaveChangesAndCheckInOfRenditionLevelTest()
    {
        int targetChunk = 1;

        //go to Source Navigate
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();

        // open DS editor
        sourceNavigateGridPage().firstRenditionEditContent();

        // get initial text
        editorPage().scrollToChunk(targetChunk);

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        //check-in
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().firstRenditionEditContent();
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        //discard changes and clos DS
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(phrase, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

    /**
     * STORY/BUG - ???? <br>
     * SUMMARY - A test to verify that changes have been saved after check-in (Source->Navigate. Section level. DS editor). <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openSaveChangesAndCheckInOfSectionLevelTest()
    {
        int targetChunk = 2;

        //go to Source Navigate
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToSectionTab();

        // open DS editor
        sourceNavigateGridPage().firstSectionEditContent();

        // get initial text
        editorPage().scrollToChunk(targetChunk);
        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        //check-in
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        sourcePage().switchToSectionNavigatePage();
        sourceNavigateGridPage().firstSectionEditContent();
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        //discard changes and clos DS
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(phrase, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

    /**
     * STORY/BUG - ???? <br>
     * SUMMARY - A test to verify that changes have been saved after check-in (Source->Navigate. Delta level. DS editor). <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openSaveChangesAndCheckInOfDeltaLevelTest()
    {
        int targetChunk = 2;

        //go to Source Navigate
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToSectionTab();
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();

        // open DS editor
        sourceNavigateGridPage().firstDeltaEditContent();
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        // get initial text
        editorPage().scrollToChunk(targetChunk);
        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        //check-in
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateGridPage().firstDeltaEditContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        //discard changes and clos DS
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(phrase, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

    /**
     * STORY/BUG - ???? <br>
     * SUMMARY - A test to verify that changes have been saved after check-in (Source->Navigate. Section Group level. DS editor). <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openSaveChangesAndCheckInOfSectionGroupLevelTest()
    {
        int targetChunk = 2;
        String group = "group";

        //go to Source Navigate
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().rightClickFirstItem();
        renditionContextMenu().modifySectionGrouping();
        sectionGroupingToolsPage().removeGroup(group);
        sectionGroupingToolsPage().createGroup(group);
        sourceNavigateGridPage().selectSectionsInRange(1, 2);
        sourceNavigateGridPage().moveSelectedSectionsToGroup(group);
        sectionGroupingToolsPage().clickApplyAndClose();
        sourcePage().switchToSourceNavigatePage();
        homePage().goToHomePage();
        sourcePage().goToSourcePage();
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToSectionGroupTab();

        // open DS editor
        sourceNavigateGridPage().rightClickItemBySectionGroupName(group);
        sourceNavigateGridPage().sectionGroupEditContent();
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        // get initial text
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);
        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        //check-in
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        sourcePage().switchToSectionNavigatePage();
        sourceNavigateGridPage().rightClickItemBySectionGroupName(group);
        sourceNavigateGridPage().sectionGroupEditContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        //discard changes and clos DS
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(phrase, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

    /**
     * STORY/BUG - ???? <br>
     * SUMMARY - A test to verify that changes have been saved after check-in (Source->Navigate. Section Delta level. DS editor). <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openSaveChangesAndCheckInOfDeltaGroupLevelTest()
    {
        int targetChunk = 2;
        String group = "group";

        //go to Source Navigate
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().rightClickFirstItem();
        renditionContextMenu().modifyDeltaGrouping();
        deltaGroupingToolsPage().removeGroup(group);
        deltaGroupingToolsPage().createGroup(group);
        sourceNavigateGridPage().selectDeltasInRange(1, 2);
        sourceNavigateGridPage().moveSelectedDeltasToGroup(group);
        deltaGroupingToolsPage().clickApplyAndClose();
        sourcePage().switchToSourceNavigatePage();
        homePage().goToHomePage();
        sourcePage().goToSourcePage();
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaGroupTab();

        // open DS editor
        sourceNavigateGridPage().rightClickItemByDeltaGroupName(group);
        sourceNavigateGridPage().deltaGroupEditContent();

        // get initial text
        editorPage().scrollToChunk(targetChunk);
        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        sourcePage().switchToDeltaGroupNavigatePage();
        sourceNavigateGridPage().rightClickItemByDeltaGroupName(group);
        sourceNavigateGridPage().deltaGroupEditContent();
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        //discard changes and clos DS
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals(phrase,
                                paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

}
