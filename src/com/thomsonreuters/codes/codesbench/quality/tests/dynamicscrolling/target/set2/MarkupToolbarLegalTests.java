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
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.MarkupValue;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MarkupToolbarLegalTests extends TestService
{
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";


        /*Markup stuff
         * CAP - verify cap conditional markup wraps the text
         * CSC - verify small caps markup wraps the text
         * Paragraph IN - verify paragraph id included markup wraps the text
         * Paragraph IG - verify paragraph id ignore markup wraps the text
         * CFL - verify character fill markup wraps the text
         * CGN - verify character generate markup wraps the text
         * PRS - print suppress
         * WLS - WL suppress
         * VIN - vendor include
         * CID - case history id
         * WTC - WTC
         * Cite Reference - manually invalid cite
         *
         * 1. Open the document
         * 2. Scroll to chunk
         * 3. Add a phrase and mark it up
         * 4. VERIFY: Added phrase is wrapped with markup tag
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
        public void markupToolbarLegalTest(MarkupValue markup)
        {
                String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";

                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
                editorPage().scrollToChunk(chunkNumber);

                // add a phrase and mark it up
                String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, chunkNumber);
                editorTextPage().breakOutOfFrame();
                editorTextPage().sendEnterToElement(String.format(
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

        /*Add and Delete markup and remove add and delete markup
         * 1. Open the document
         * 2. Scroll to chunk 2 or 3
         * 3. Insert and highlight simple text into a paragraph here
         * 4. Click the add markup button in the toolbar
         * 5. Verify: Add markup wraps the text you had highlighted
         * 6. Insert and highlight simple text into a paragraph here
         * 7. Click the delete markup button in the toolbar
         * 8. Verify: Delete markup wraps the text you had highlighted
         * 9. Click the remove add and delete markup button in the toolbar
         * 10. Verify: add and delete markup are removed - the text should still be in the content
         * 11. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void markupToolbarAddedDeletedLegalTest()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 1);
                String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

                String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 2);
                String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
                String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

                String thirdPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 3);
                String thirdParaSpan = thirdPara + EditorTextPageElements.SPAN;
                String thirdParaParatext = thirdPara + EditorTextPageElements.PARATEXT;
                String thirdParaModifiedBy = thirdPara + EditorTextPageElements.MODIFIED_BY_MNEMONIC;

                editorTextPage().click(firstParaSpan);
                editorTextPage().rightClick(firstParaSpan);
                editorPage().breakOutOfFrame();
                editorTextContextMenu().insertNewParagraphAltI();

                editorTextPage().switchToEditorTextArea();
                editorTextPage().rightClick(secondParaSpan);
                editorPage().breakOutOfFrame();
                editorTextContextMenu().insertNewParagraphAltI();
                editorTextPage().waitForElementExists(thirdParaSpan);

                // add a phrase and mark it up as Added
                String addedPhrase = "added";
                editorTextPage().switchToEditorTextArea();
                editorTextPage().insertPhraseUnderGivenLabelWithHome(addedPhrase, secondParaSpan);
                editorTextPage().highlightHelper(addedPhrase);
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickAddMarkup();
                editorTextPage().switchToEditorTextArea();


                boolean phraseGotWrappedAsAdded = editorTextPage().doesElementExist(secondParaParatext
                        + String.format(MarkupValue.ADD.getTextElement(), addedPhrase));
                String userName = this.user().getEditorModifiedByUsername();
                String deletedPhrase = "deleted";
                editorTextPage().insertPhraseUnderGivenLabelWithHome(deletedPhrase, secondParaSpan);
                editorTextPage().highlightHelper(deletedPhrase);
                editorToolbarPage().clickDelete();
                editorTextPage().switchToEditorTextArea();
                boolean phraseGotWrappedAsDeleted = editorTextPage().doesElementExist(secondParaParatext
                        + String.format(MarkupValue.DELETE.getTextElement(), deletedPhrase));

                // do some changes in the other paragraph for an additional check
                String additionalCheck = "Additional check!";
                editorTextPage().insertPhraseUnderGivenLabelWithHome(additionalCheck, thirdParaSpan);
                editorTextPage().highlightHelper(additionalCheck);
                String modifiedByText = editorTextPage().getElementsText(thirdParaModifiedBy);
                boolean modifiedByUpdated = modifiedByText.contains(userName +
                                DateAndTimeUtils.getCurrentDateMMddyyyy());

                // hit Remove Added/Deleted markup
                editorToolbarPage().clickSave();
                editorToolbarPage().clickRemoveAddDelete();
                editorTextPage().switchToEditorTextArea();

                // check results
                boolean addedAndDeletedMarkupDisappeared = !editorTextPage().doesElementExist(
                        secondParaParatext
                        + String.format(EditorTextPageElements.ADDED_TEXT, addedPhrase))
                        && !editorTextPage().doesElementExist(secondParaParatext
                        + String.format(EditorTextPageElements.DELETED_TEXT, deletedPhrase));

                boolean addedPhraseIsStillInContent = editorTextPage().getElementsText(secondParaParatext)
                        .contains(addedPhrase);

                boolean deletedPhraseIsStillInContent = editorTextPage().getElementsText(secondParaParatext)
                        .contains(deletedPhrase);

                // additional checks - HALCYONST-2978
                boolean additionalPhraseStillPresented = editorTextPage()
                        .getElementsText(thirdParaParatext)
                        .contains(additionalCheck);

                String modifiedByAfterRemoveAddDelete = editorTextPage().getElementsText(thirdParaModifiedBy);
                boolean modifiedByStillCorrect = modifiedByAfterRemoveAddDelete.contains(
                                userName + DateAndTimeUtils.getCurrentDateMMddyyyy());
                editorTextPage().breakOutOfFrame();

                // check in
                editorPage().closeAndCheckInChanges();
                editorPage().switchToEditor();

                // reopen editor and check phrase inserted
                hierarchyNavigatePage().switchToHierarchyEditPage();
                siblingMetadataPage().selectedSiblingMetadataEditContent();


                editorPage().scrollToChunk(chunkNumber);
                boolean markupPersistsAfterReopening = editorTextPage().doesElementExist(
                        secondParaParatext
                                + String.format(EditorTextPageElements.ADDED_TEXT, addedPhrase))
                        && editorTextPage().doesElementExist(secondParaParatext
                        + String.format(EditorTextPageElements.DELETED_TEXT, deletedPhrase));

                editorTextPage().click(thirdParaSpan);
                editorTextPage().rightClick(thirdParaSpan);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().delete();
                editorTextPage().switchToEditorTextArea();
                editorTextPage().waitForElementGone(thirdPara);

                editorTextPage().click(secondParaSpan);
                editorTextPage().rightClick(secondParaSpan);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().delete();
                editorTextPage().switchToEditorTextArea();
                editorTextPage().waitForElementGone(secondPara);

                editorPage().closeAndCheckInChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedAsAdded,
                                        "Added phrase should get wrapped with Added markup tag"),
                                () -> Assertions.assertTrue(phraseGotWrappedAsDeleted,
                                        "Added phrase should get wrapped with Deleted markup tag"),
                                () -> Assertions.assertTrue(modifiedByUpdated,
                                        "Modified by tag should be updated with test user name and current date"),
                                () -> Assertions.assertTrue(addedAndDeletedMarkupDisappeared,
                                        "Added and deleted markup should disappear"),
                                () -> Assertions.assertTrue(addedPhraseIsStillInContent,
                                        "Added phrase should not disappear"),
                                () -> Assertions.assertFalse(deletedPhraseIsStillInContent,
                                        "Deleted phrase should disappear"),
                                () -> Assertions.assertTrue(additionalPhraseStillPresented,
                                        "Additional phrase should be presented after Remove A/D"),
                                () -> Assertions.assertTrue(modifiedByStillCorrect,
                                        "Modified by tag should be still have test user name and current date"),
                                () -> Assertions.assertFalse(markupPersistsAfterReopening,
                                        "Added and deleted markup should not be displayed")
                        );
        }

        /*
         * WordPhrase markup
         * W&P - wordphrase (this doesn't insert the same style markup that wraps text, but it does appear on each side of the text)
         * could probably get the inner html of the text paragraph and verify the elements surround the text
         *
         * End markup
         * This markup doesn't wrap text, but it inserts a single markup tag where the cursor is placed
         * could probably get the inner html of the text paragraph and verify the element is after the text we inserted
         * EDL - end left
         * EDC - end center
         * EDR - end right
         * EDJ - end justify
         *
         * 1. Open the document
         * 2. Scroll to chunk
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
        public void wordsAndPhrasesAndEndlineMarkupsToolbarTargetLegalTest(MarkupValue markup)
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
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
         * 2. Scroll to chunk
         * 3. Add a phrase and mark it up as Bold
         * 4. Add new word to the middle of text within a Bold markup
         * 5. Highlight this word and mark it up as Italic
         * 6. VERIFY: Added word within a Bold text is marked up as Italic
         * 7. Close and discard changes
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void unnestedMarkupToolbarTargetLegalTest()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
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
                editorTextPage().breakOutOfFrame();
                editorToolbarPage().clickItalic();
                editorTextPage().switchToEditorTextArea();

                boolean phraseGotWrappedInMarkupTags = editorTextPage().doesElementExist(
                        EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT
                        + String.format(EditorTextPageElements.ITALIC_INSIDE_BOLD_TEXT, phrase));

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(phraseGotWrappedInMarkupTags,
                                        "Added phrase should get wrapped with markup tags")
                        );
        }

        /**
         * 1. Open document
         * 2. Scroll to chunk
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
        public void nestedMarkupToolbarTargetLegalTest()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
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
         * 2. Scroll to chunk
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
        public void insertMarkupToolbarTargetLegalTest()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
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
         * 2. Scroll to chunk
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
        public void applyMarkupAndEditAttributesTargetLegalTest()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                int chunkNumber = 2;
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
                String expectedNewTitle = " number of characters(9)";
                String expectedNumchar = "9";

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
