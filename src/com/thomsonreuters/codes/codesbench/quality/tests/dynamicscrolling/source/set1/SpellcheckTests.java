package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SpellcheckPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class SpellcheckTests extends TestService
{
    String uuid = "I510E9830AACF11E3A8A3E164A51A7FE0";

    /*
     * Experiment with verifying spellchecker doesn't appear during check-in when we know there are no misspelled words
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void noSpellCheckAppearsDuringCheckInWithNoMisspelledWordsSourceLegalTest()
    {
        //String uuid = "I3FF5CD5018B911E8820588494B6E0205"; // for dev
        String correctWord = "Banana";
        int targetChunk = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        // define target paragraph's xpath
        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;
        String firstParaParatext = firstPara + EditorTextPageElements.PARATEXT;

        // check if data is corrupted. If corrupted - remove the word, check-in and reopen the rendition.
        editorPage().scrollToChunk(targetChunk);
        boolean fileIsCorrupted = editorPage().getElementsText(firstParaParatext).startsWith(correctWord);
        if (fileIsCorrupted)
        {
            editorTextPage().deletePhraseInTheBeginningOfTextParagraph(correctWord, firstParaSpan);
            editorPage().closeAndCheckInChanges();
            sourcePage().switchToSourceNavigatePage();
            sourceNavigateGridPage().firstRenditionEditContent();
            editorPage().scrollToChunk(targetChunk);
        }
        editorTextPage().insertPhraseUnderGivenLabelWithHome(correctWord, firstParaSpan);
        // toggle spell check
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickCheckSpelling();
        boolean noMisspelledWordsSpellCheckMessageAppeared = editorMessagePage().checkLastInfoMessageSpellcheckOk();
        // check in
        editorPage().closeAndCheckInChanges();
        boolean noSpellCheckAppearedDuringCheckIn = editorPage().verifySpellcheckWindowDisappearsByItselfWhenNoErrorsFound();
        // reopen editor and delete inserted word
        editorPage().closeEditorWindowAndReopenRenditionForEdit();
        editorPage().scrollToChunk(targetChunk);
        editorTextPage().deletePhraseInTheBeginningOfTextParagraph(correctWord, firstParaSpan);
        editorPage().closeAndCheckInChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(noMisspelledWordsSpellCheckMessageAppeared, "No misspelled words spell check message should appeared in the log"),
                () -> Assertions.assertTrue(noSpellCheckAppearedDuringCheckIn, "No spell check should appear during Check in")
        );
    }

    /*Correct misspelled word during check in
     * 1. Open document
     * 2. Scroll to chunk 2 or 3
     * 3. In a paragraph, insert a misspelled word
     * 3.1. Grab the text of this paragraph because we'll need to verify it appears in the spellchecker
     * 4. Close and check in changes
     * 5. Verify: Spellcheck window appears
     * 6. Verify: Our misspelled word is flagged
     * 7. Verify: The text paragraph is the only content that appears in the content pane
     * 8. Replace the misspelled word with a correctly spelled word
     * 9. Verify: The text paragraph on the right contains the replaced word
     * 10. Click commit changes and close
     * 11. The editor will check in the content at this point if there are no errors in the document
     * 12. Re-open the document
     * 13. Verify: The text paragraph from before contains the replaced word and no other content
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void spellcheckCorrectionDuringCheckInSourceLegalTest()
    {
        //String uuid = "I3FF5CD5018B911E8820588494B6E0205"; // for dev
        String misspelledWord = "Etobanany";
        int targetChunk = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        // define target paragraph's xpath
        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;
        String firstParaParatext = firstPara + EditorTextPageElements.PARATEXT;

        // check if data is corrupted. If corrupted - remove the word, check-in and reopen the rendition.
        editorPage().scrollToChunk(targetChunk);
        boolean fileIsCorrupted = editorPage().getElementsText(firstParaParatext).startsWith(misspelledWord);
        if (fileIsCorrupted)
        {
            editorTextPage().deletePhraseInTheBeginningOfTextParagraph(misspelledWord, firstParaSpan);
            editorPage().closeAndCheckInChanges();
            sourcePage().switchToSourceNavigatePage();
            sourceNavigateGridPage().firstRenditionEditContent();
            editorPage().scrollToChunk(targetChunk);
        }
        String initialText = editorTextPage().getElementsText(firstParaParatext).trim();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(misspelledWord, firstParaSpan);
        String textWithMisspelledWord = editorTextPage().getElementsText(firstParaParatext);
        // check in
        editorPage().closeAndCheckInChanges();
        // switch to spell check window
        boolean spellcheckAppeared = editorPage().switchToWindow(SpellcheckPageElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        // check info in spell check window
        boolean misspelledWordFound = editorTextPage().getElementsText(SpellcheckPageElements.WORDS_FOUND_BY_SPELLCHECK).equals(misspelledWord);
        boolean textIsTheSameAsInTextframe = spellcheckPage().grabAllParatextsContent().equals(textWithMisspelledWord);
        editorPage().breakOutOfFrame();
        String replacement = editorPage().getElementsAttribute(SpellcheckPageElements.REPLACE_WITH_TEXTBOX, "value");
        // replace misspelled word with suggested word
        spellcheckPage().clickReplace();
        editorPage().switchToWindow(SpellcheckPageElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        boolean textNowHaveWordReplaced = spellcheckPage()
                .grabAllParatextsContent().equals(textWithMisspelledWord.replace(misspelledWord, replacement));
        editorPage().breakOutOfFrame();
        editorPage().click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
        editorPage().waitForWindowGoneByTitle(SpellcheckPageElements.PAGE_TITLE);
        // reopen editor
        editorPage().closeEditorWindowAndReopenRenditionForEdit();
        editorPage().scrollToChunk(targetChunk);
        // check text paragraph's state
        boolean textAfterCheckInIsAsExpected = editorPage().getElementsText(firstParaParatext)
                .equals(textWithMisspelledWord.replace(misspelledWord, replacement));
        // delete inserted word
        editorTextPage().deletePhraseInTheBeginningOfTextParagraph(replacement, firstParaSpan);
        boolean textIsBackTOInitialState = editorPage().getElementsText(firstParaParatext).trim().equals(initialText);
        editorPage().closeAndCheckInChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(spellcheckAppeared, "Spell Check window should appear"),
                () -> Assertions.assertTrue(misspelledWordFound, "Spell Check should found misspelled word"),
                () -> Assertions.assertTrue(textIsTheSameAsInTextframe, "Spell Check should contain only text of text paragraph with inserted word"),
                () -> Assertions.assertTrue(textNowHaveWordReplaced, "Misspelled word should be replaced in Spell Check window"),
                () -> Assertions.assertTrue(textAfterCheckInIsAsExpected, "Text paragraph should contain replaced word and no unexpected entities after Check In"),
                () -> Assertions.assertTrue(textIsBackTOInitialState, "Text paragraph should contain text as it was before this test")
        );
    }

    //HALCYONST-1970, 1937, possibly another one from Matt

    /*Correct misspelled word via spell check editor toolbar, and check in
     * 1. Open document
     * 2. Scroll to chunk 2 or 3
     * 3. In a paragraph, insert a misspelled word
     * 3.1. Grab the text of this paragraph because we'll need to verify it appears in the spellchecker
     * 4. Run spell check from the toolbar
     * 5. Verify: Spellcheck window appears
     * 6. Verify: Our misspelled word is flagged
     * 7. Verify: The text paragraph is the only content that appears in the content pane
     * 8. Replace the misspelled word with a correctly spelled word
     * 9. Verify: The text paragraph on the right contains the replaced word
     * 10. Click commit changes and close
     * 10.1. Verify: The text paragraph from before contains the replaced word and no other content
     * 10.2. Close and check-in changes
     * 11. The editor will check in the content at this point if there are no errors in the document
     * 12. Re-open the document
     * 13. Verify: The text paragraph from before contains the replaced word and no other content
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void spellcheckCorrectionViaToolbarSourceLegalTest()
    {
        //String uuid = "I3FF5CD5018B911E8820588494B6E0205"; //for dev
        String misspelledWord = "Etobanany";
        int targetChunk = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        // insert a misspelled word
        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;
        String firstParaParatext = firstPara + EditorTextPageElements.PARATEXT;

        // check if data is corrupted. If corrupted - remove the word, check-in and reopen the rendition.
        editorPage().scrollToChunk(targetChunk);
        boolean fileIsCorrupted = editorPage().getElementsText(firstParaParatext).startsWith(misspelledWord);
        if (fileIsCorrupted)
        {
            editorTextPage().deletePhraseInTheBeginningOfTextParagraph(misspelledWord, firstParaSpan);
            editorPage().closeAndCheckInChanges();
            sourcePage().switchToSourceNavigatePage();
            sourceNavigateGridPage().firstRenditionEditContent();
            editorPage().scrollToChunk(targetChunk);
        }

        // insert a misspelled word
        String initialText = editorTextPage().getElementsText(firstParaParatext).trim();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(misspelledWord, firstParaSpan);
        String textWithMisspelledWord = editorTextPage().getElementsText(firstParaParatext);
        // toggle spell check
        editorPage().switchToEditor();
        editorToolbarPage().clickCheckSpelling();
        editorPage().waitForWindowByTitle(SpellcheckPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
        boolean spellcheckAppeared = editorPage().switchToWindow(SpellcheckPageElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        // check info in spell check window
        boolean misspelledWordFound = editorPage().getElementsText(SpellcheckPageElements.WORDS_FOUND_BY_SPELLCHECK)
                .equals(misspelledWord);
        boolean textIsTheSameAsInTextframe = spellcheckPage().grabAllParatextsContent()
                .equals(textWithMisspelledWord);
        editorPage().breakOutOfFrame();
        String replacement = editorPage().getElementsAttribute(SpellcheckPageElements.REPLACE_WITH_TEXTBOX, "value");
        // replace misspelled word with suggested word
        spellcheckPage().clickReplace();
        editorPage().checkAlert();
        editorPage().switchToWindow(SpellcheckPageElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        boolean textNowHaveWordReplaced = spellcheckPage().grabAllParatextsContent()
                .equals(textWithMisspelledWord.replace(misspelledWord, replacement));
        editorPage().breakOutOfFrame();
        spellcheckPage().clickCommitChangesAndClose();
        editorPage().waitForWindowGoneByTitle(SpellcheckPageElements.PAGE_TITLE);
        // check misspelled word got replaced with spell check suggestion
        editorPage().switchToEditor();
        editorPage().scrollToChunk(targetChunk - 1);
        editorPage().switchToEditor();
        editorPage().scrollToChunk(targetChunk);
        editorPage().click(firstParaSpan);
        boolean misspelledWordReplaced = editorPage().getElementsText(firstParaParatext)
                .equals(textWithMisspelledWord.replace(misspelledWord, replacement));
        // check in
        editorPage().closeAndCheckInChanges();
        // reopen editor
        editorPage().closeEditorWindowAndReopenRenditionForEdit();
        editorPage().scrollToChunk(targetChunk);
        // check text paragraph's state
        boolean textAfterCheckInIsAsExpected = editorPage().getElementsText(firstParaParatext)
                .equals(textWithMisspelledWord.replace(misspelledWord, replacement));
        // delete inserted word
        editorTextPage().deletePhraseInTheBeginningOfTextParagraph(replacement, firstParaSpan);
        boolean textIsBackTOInitialState = editorPage().getElementsText(firstParaParatext).trim().equals(initialText);
        editorPage().closeAndCheckInChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(spellcheckAppeared, "Spell Check window should appear"),
                () -> Assertions.assertTrue(misspelledWordFound, "Spell Check should found misspelled word"),
                () -> Assertions.assertTrue(textIsTheSameAsInTextframe, "Spell Check should contain only text of text paragraph with inserted word"),
                () -> Assertions.assertTrue(textNowHaveWordReplaced, "Misspelled word should be replaced in Spell Check window"),
                () -> Assertions.assertTrue(misspelledWordReplaced, "Misspelled word should be replaced"),
                () -> Assertions.assertTrue(textAfterCheckInIsAsExpected, "Text paragraph should contain replaced word and no unexpected entities after Check In"),
                () -> Assertions.assertTrue(textIsBackTOInitialState, "Text paragraph should contain text as it was before this test")
        );
    }
}
