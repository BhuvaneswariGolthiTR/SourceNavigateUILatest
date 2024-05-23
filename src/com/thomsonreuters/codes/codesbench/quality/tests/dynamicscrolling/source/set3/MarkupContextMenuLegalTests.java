package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.MarkupValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MarkupContextMenuLegalTests extends TestService
{
    //String uuid = "I8B1C2100BA6111E797B6DB99DFD08357"; //for dev
    String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";

    /**
     * 1. Open document
     * 2. Scroll to chunk
     * 3. Add a phrase and highlight
     * 4. Mark added text as Bold
     * 5. Right click on the markup
     * 6. Open Markup > Delete Markup context menu
     * 7. VERIFY: There should be no bold phrase anymore
     * 8. VERIFY: Added phrase should be displayed still
     * 9. VERIFY: There should be no highlighting
     * 10. Close and discard changes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteMarkupContextMenuSourceLegalTest()
    {
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        int chunkNumber = 2;
        editorPage().scrollToChunk(chunkNumber);

        // add a phrase and mark it up
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
        editorTextPage().breakOutOfFrame();
        editorToolbarPage().clickBold();
        editorTextPage().switchToEditorTextArea();

        editorTextPage().click(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT
                + String.format(EditorTextPageElements.BOLD_TEXT_MARKUP_IMAGE, phrase));

        // right click and choose Delete Markup
        editorTextPage().rightClick(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT
                + String.format(EditorTextPageElements.BOLD_TEXT_MARKUP_IMAGE, phrase));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().markupDeleteMarkup();

        // verify markup being removed
        editorTextPage().switchToEditorTextArea();
        boolean markupRemoved = !editorTextPage().doesElementExist(EditorTextPageElements
                .TEXT_PARAGRAPH_PARATEXT + String.format(EditorTextPageElements.BOLD_TEXT, phrase));

        String firstParaParatext = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                chunkNumber - 1, 1) + EditorTextPageElements.PARATEXT;
        boolean phrasePersists = editorTextPage()
                .getElementsText(firstParaParatext).contains(phrase);

        // HALCYONST-2024
        boolean isParagraphHighlighted = editorTextPage()
                .doesElementExist(EditorTextPageElements.HIGHLIGHTED_PARA);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(markupRemoved, "There should be no bold phrase anymore"),
                        () -> Assertions.assertTrue(phrasePersists, "Added phrase should be displayed still"),
                        () -> Assertions.assertFalse(isParagraphHighlighted, "There should be no highlighting")
                );
    }

    /**
     * 1. Open document
     * 2. Scroll to chunk
     * 3. Add a phrase and highlight
     * 4. Open Markup > Insert Markup context menu
     * 5. Select a markup and click Save
     * 6. VERIFY: Added phrase should get wrapped with the markup tag
     * 7. Close and discard changes
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"ADD", "QUOTE", "BOLD", "CAP_CONDITIONAL", "CASE_HISTORY_ID",
                            "CHARACTER_FILL", "CHARACTER_GENERATE", "CITE_REFERENCE",
                            "CITE_TREATMENT", "CONDITIONAL_CASE", "CROSSHATCH_VETOED"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void markupContextMenuSourceLegalTest(MarkupValue markup)
    {
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        int chunkNumber = 2;
        editorPage().scrollToChunk(chunkNumber);

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                chunkNumber - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                chunkNumber - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;
        editorTextPage().click(firstParaSpan);

        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");


        // add a phrase and mark it up
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);
        editorTextPage().highlightHelper(phrase);
        editorTextPage().rightClick(
                String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN, phrase));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().markupInsertMarkup();

        insertWestMarkupPage().selectMarkupToBeInserted(markup.getDropdownOption());
        insertWestMarkupPage().clickSave();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        editorPage().waitForPageLoaded();

        boolean phraseGotWrappedInMarkupTag = editorTextPage().doesElementExist(secondParaParatext
                + String.format(markup.getTextElement(), phrase));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(phraseGotWrappedInMarkupTag,
                                "Added phrase should get wrapped with Added markup tag")
                );
    }
}
