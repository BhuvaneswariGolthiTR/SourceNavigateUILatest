package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.Markup;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.MarkupValue;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MarkupToolbarLegalTests extends TestService
{
        //String uuid = "I8B1C2100BA6111E797B6DB99DFD08357"; //for dev
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270"; //for uat
        int chunkNumber = 3;

        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and mark it up
         * 4. VERIFY: Added markup exists in the text
         * 5. Close and discard changes
         */
        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @EnumSource
                (
                        names = {"ADD", "DELETE", "BOLD", "ITALIC", "CAP_CONDITIONAL", "SMALL_CAPS",
                                "PARAGRAPH_ID_INCLUDE", "PARAGRAPH_ID_IGNORE", "CHARACTER_FILL", "CHARACTER_GENERATE",
                                "PRINT_SUPPRESS", "WESTLAW_SUPPRESS", "VENDOR_INCLUDE", "CASE_HISTORY_ID",
                                "WESTLAW_TABLE_CODE", "CITE_REFERENCE"}
                )
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void markupToolbarLegalTest(Markup markup)
        {
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().sendEnterToElement(String.format(
                        EditorToolbarPageElements.BUTTON_WITH_TEXT, markup.getButtonTitle()));
                editorTextPage().switchToEditorTextArea();
                boolean phraseGotWrappedInMarkupTag = editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT + String.format(
                                markup.getTextElement(), phrase));
                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTag,
                                                            "Added phrase should get wrapped with markup tag")
                        );
        }

        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and mark it up
         * 4. VERIFY: Regex of the paragraph with a markup matches regex
         * 5. Close and discard changes
         */
        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @EnumSource
                (
                        names = {"WORDS_AND_PHRASES", "END_LEFT", "END_CENTER", "END_RIGHT", "END_JUSTIFY"}
                )
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void wordphrasesAndEndlineMarkupsToolbarLegalTest(Markup markup)
        {
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().sendEnterToElement(String.format(
                        EditorToolbarPageElements.BUTTON_WITH_TEXT, markup.getButtonTitle()));
                editorTextPage().switchToEditorTextArea();

                // verify  markup
                boolean phraseGotWrappedInMarkupTag = RegexUtils.matchesRegex(
                        editorTextPage().getElementsInnerHTML(
                                String.format(EditorTextPageElements.TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN, phrase)),
                        String.format(markup.getTextElement(), phrase));
                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTag,
                                                            "Added phrase should get wrapped with markup tag")
                        );
        }

        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and mark it up as Bold
         * 4. Highlight phrase with a Bold markup and mark it up as Italic
         * 5. VERIFY: Text within a Bold text is marked up as Italic
         * 6. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void doubleMarkupToolbarSourceLegalTest()
        {
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickBold();
                editorTextPage().switchToEditorTextArea();

                editorTextPage().click(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                                       String.format(EditorTextPageElements.BOLD_TEXT_MARKUP_IMAGE, phrase));
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickItalic();
                editorTextPage().switchToEditorTextArea();

                boolean phraseGotWrappedInMarkupTags = editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                        String.format(EditorTextPageElements.ITALIC_INSIDE_BOLD_TEXT, phrase));

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTags,
                                                            "Added phrase should get wrapped with markup tags")
                        );
        }

        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and mark it up as Bold
         * 4. Add new word to the middle of text within a Bold markup
         * 5. Highlight this word and mark it up as Italic
         * 6. VERIFY: Added word within a Bold text is marked up as Italic
         * 7. Remove italic markup
         * 8. VERIFY: Italic markup should disappear and all phrase should become bold only
         * 9. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void doublePartialMarkupToolbarSourceLegalTest()
        {
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String boldPhrase = "BOLDBOLD";
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(boldPhrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickBold();
                editorTextPage().switchToEditorTextArea();

                String italicPhrase = " ITALIC ";
                editorTextPage().click(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                                       String.format(EditorTextPageElements.BOLD_TEXT, boldPhrase));
                editorTextPage().sendKeys(italicPhrase);
                editorTextPage().highlightHelper(italicPhrase);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickItalic();
                editorTextPage().switchToEditorTextArea();

                // verify markup
                boolean phraseGotWrappedInMarkupTags = editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                        String.format(EditorTextPageElements.ITALIC_INSIDE_BOLD_TEXT, italicPhrase));

                // remove ITALIC
                editorTextPage().rightClick(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                        String.format(EditorTextPageElements.ITALIC_TEXT_MARKUP_IMAGE, italicPhrase));
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().markupDeleteMarkup();

                // verify no ITALIC, whole phrase is BOLD
                editorTextPage().switchToEditorTextArea();
                String wholePhrase = "BOLD ITALIC BOLD";
                boolean italicDisappeared = !editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                        String.format(EditorTextPageElements.ITALIC_INSIDE_BOLD_TEXT, italicPhrase))
                                            && editorTextPage().getElementsInnerHTML(
                                                    EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT + "//bold")
                                                    .contains(wholePhrase);
                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTags,
                                                            "Added phrase should get wrapped with markup tags"),
                                () -> Assertions.assertTrue(italicDisappeared,
                                                            "Italic markup should disappear and all phrase should become bold only")
                        );
        }


        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and highlight
         * 4. Click toolbar Insert Markup button
         * 5. Select "added (centa)" and click Save
         * 6. VERIFY: Added phrase should get wrapped with Added markup tag
         * 7. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertWestMarkupSourceLegalTest()
        {
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickInsertMarkup();
                insertWestMarkupPage().selectMarkupToBeInserted(MarkupValue.ADD.getDropdownOption());
                insertWestMarkupPage().clickSave();
                editorPage().switchToEditor();
                editorPage().switchToEditorTextFrame();

                boolean phraseGotWrappedInMarkupTag = editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                        String.format(EditorTextPageElements.ADDED_TEXT, phrase));

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTag,
                                                            "Added phrase should get wrapped with Added markup tag")
                        );


        }

        /**
         * 1. Open document
         * 2. Scroll to chunk 3
         * 3. Add a phrase and mark it up as Character Generate markup
         * 4. VERIFY: Markup title should be empty initially && Markup should be without a numchar
         * 5. Edit markup attributes: set "number of characters" to 9
         * 6. VERIFY: Markup title should be updated && Markup numchar should be updated
         * 7. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void applyMarkupAndEditAttributesSourceLegalTest()
        {
                String expectedNewTitle = " number of characters(9)";
                String expectedNumchar = "9";

                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().click(EditorToolbarPageElements.toolbarCharacterGenerateMarkupButton);
                editorTextPage().switchToEditorTextArea();

                // new var to make further usage of this xpath shorter
                String markedUpPhraseXpath = EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT +
                                             String.format(EditorTextPageElements.CHARACTER_GENERATE_TEXT, phrase);
                // check if initial title of markup is empty
                String initialMarkupTitle =
                        editorTextPage().getElementsAttribute(markedUpPhraseXpath, "title");
                boolean initialMarkupNumcharIsExpected =
                        null == editorTextPage().getElementsAttribute(markedUpPhraseXpath, "numchar");
                // edit attribute
                editorTextPage().click(markedUpPhraseXpath + "/img");
                editorTextPage().rightClick(markedUpPhraseXpath + "/img");
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().markupEditAttributes();
                editAttributesPage().setFirstInput(expectedNumchar);
                editAttributesPage().clickSave();

                // check title has been updated
                editorTextPage().switchToEditorTextArea();
                String updatedMarkupTitle =
                        editorTextPage().getElementsAttribute(markedUpPhraseXpath, "title");
                String updatedMarkupNumchar =
                        editorTextPage().getElementsAttribute(markedUpPhraseXpath, "numchar");

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertEquals("", initialMarkupTitle,
                                                            "Markup title should be empty initially"),
                                () -> Assertions.assertTrue(initialMarkupNumcharIsExpected,
                                                            "Markup should be without a numchar"),
                                () -> Assertions.assertEquals(expectedNewTitle, updatedMarkupTitle,
                                                            "Markup title should be updated after attribute edit"),
                                () -> Assertions.assertEquals(expectedNumchar, updatedMarkupNumchar,
                                                              "Markup numchar should be updated after attribute edit")
                        );

        }
}
