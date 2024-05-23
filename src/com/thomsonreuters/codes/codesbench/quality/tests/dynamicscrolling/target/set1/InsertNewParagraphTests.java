package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractInsertNewParagraphTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_LABEL;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.PARATEXT_WITH_TEXT_GIVEN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.SELECTED_PARATEXT_NODE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertNewParagraphTests extends AbstractInsertNewParagraphTests
{
        private static final String IOWA_TARGET_NODE_UUID = "ID70F2B70160711DC9BE4FF24192CDF0B";
        private String defaultSourceTag;

        @BeforeEach
        public void openNodeInDsEditor()
        {
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
                hierarchySearchPage().searchNodeUuid(IOWA_TARGET_NODE_UUID);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchToEditor();
                // grab default source tag
                defaultSourceTag = editorPreferencesPage().grabDefaultSourceTag();
                editorPage().scrollToChunk(2);
        }

        /*
         * Insert new paragraph via context menu
         * 1. Open document
         * 2. Scroll to chunk 2 or 3
         * 3. Select and right click a text paragraph here
         * 4. Select Insert New Paragraph
         * 5. enter text into this paragraph
         * VERIFY: a new paragraph is inserted as a sibling to the one you selected
         * VERIFY: it should have the same mnemonic as the one selected
         * VERIFY: it will have a NOPUB tag
         * VERIFY: it will have a source tag that matches the default Session default text source tag found in the Editor Preferences
         * VERIFY: it has modified by tag
         * VERIFY: the text inserted appears
         *
         * Insert new paragraph via hotkeys
         * 1. Open document
         * 2. Scroll to chunk 2 or 3
         * 3. Select and a text paragraph here
         * 4. Hit alt + i to insert a new paragraph
         * 5. enter text into this paragraph
         * VERIFY: a new paragraph is inserted as a sibling to the one you selected
         * VERIFY: it should have the same mnemonic as the one selected
         * VERIFY: it will have a NOPUB tag
         * VERIFY: it will have a source tag that matches the default Session default text source tag found in the Editor Preferences
         * VERIFY: it has modified by tag
         * VERIFY: the text inserted appears
         *
         * Insert new paragraph as sibling
         * 1. Open document
         * 2. Scroll to chunk 2 or 3
         * 3. Select and right click a Text Paragraph
         * 4. Go to Insert Text (sibling) -> Text Paragraph -> Text Paragraph (smp)
         * VERIFY: Text Paragraph is inserted as a direct sibling to the Text Paragraph before it
         * VERIFY: The Text Paragraph has a mnemonic of SMP
         * VERIFY: The Text Paragraph has a pubtag of NOPUB
         * VERIFY: The Text Paragraph has a source tag of the default source tag
         * VERIFY: The Text Paragraph has a modified by tag
         */

        /**
         * STORY/BUG - HALCYONST-10038 <br>
         * SUMMARY - Insert new paragraph (Target) <br>
         * USER - LEGAL <br>
         */
        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @EnumSource(ParagraphInsertMethods.class)
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertNewParagraphTargetLegalTest(ParagraphInsertMethods insertMethod)
        {
                addParagraph(insertMethod, FIRST_PARA + SPAN);

                assertThatOnlyNewParagraphIsHighlighted(SECOND_PARA);
                assertThatParagraphHasCorrectText(insertMethod);

                // enter text to the new highlighted paragraph
                String phrase = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
                switch(insertMethod)
                {
                        case AS_SIBLING:
                                editorTextPage().typeTextIntoGivenParagraphassibiling(HIGHLIGHTED_PARA + PARATEXT, phrase);
                                break;
                        default:
                                editorTextPage().typeTextIntoGivenParagraph(HIGHLIGHTED_PARA + PARATEXT, phrase);
                }

                // highlight this node
                editorPage().click(SECOND_PARA + SPAN);

                // assert the tree node
                editorPage().switchToEditor();
                String expectedText = insertMethod.equals(ParagraphInsertMethods.AS_SIBLING) ? phrase : phrase + " ";
                String treeNodeText = editorPage().getElementsText(SELECTED_PARATEXT_NODE);
                assertThat(treeNodeText.equals(format(PARATEXT_WITH_TEXT_GIVEN, expectedText))
                        || treeNodeText.equals(format(PARATEXT_WITH_TEXT_GIVEN, expectedText + "i")))
                        .as("New tree node should be displayed and highlighted")
                        .isTrue();
                // assert that new text para is appeared
                editorPage().switchToEditorTextFrame();
                String paraText = editorPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT);
                assertThat(paraText.equals(expectedText) || paraText.equals(expectedText + "i"))
                        .as("New Text Para should appear")
                        .isTrue();
                assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
                assertThatModifiedByTagContainsRightNameAndDate();
                assertThatNewTextParaHasDefaultSourceTag();
                assertThatNewTextParaHasPubTag();
                assertThatEditorMessagePaneHasNoErrors();
        }

        /*
         * Insert new paragraph via hotkeys multiple times: critical bug HALCYONST-8998
         *
         * 1. Open document
         * 2. Scroll to chunk 2
         * 3. Block select the first SMP para in the Text body (click on english label to highlight a paragraph)
         * 4. Select Alt-I.
         * VERIFY: An SMP para is inserted and block-selected
         * 5. Select Alt-I again
         * VERIFY: A new SMP para should be inserted and the prior insert should be deselected and the new para should be block-selected.
         *
         * 1. Open document
         * 2. Scroll to chunk 2
         * 3. Place cursor in the first para text (paragraph is not highlighted)
         * 4. Select Alt-I.
         * VERIFY: A paragraph is inserted and block-selected
         * 5. Select Alt-I again
         * VERIFY: A new paragraph should be inserted and the prior insert should be deselected and the new para should be block-selected.
         */
        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @ValueSource(strings = {TEXT_PARAGRAPH_LABEL, PARATEXT})
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertNewParagraphViaHotkeysMultipleTimesTest(String element) throws AWTException {
                // add first paragraph
                addParagraph(ParagraphInsertMethods.HOTKEY, FIRST_PARA + element);

                // asserts for the first para
                editorTextPage().waitForPageLoaded();
                editorTextPage().waitForElement(HIGHLIGHTED_PARA);
                assertThatOnlyNewParagraphIsHighlighted(SECOND_PARA);
                assertThatParagraphHasCorrectText();
                assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
                assertThatModifiedByTagContainsRightNameAndDate();
                assertThatNewTextParaHasDefaultSourceTag();
                assertThatNewTextParaHasPubTag();

                // add second paragraph
                editorPage().insertNewParagraphViaHotkey();
                editorTextPage().waitForElementExists(THIRD_PARA);

                // asserts for the second para
                assertThatOnlyNewParagraphIsHighlighted(THIRD_PARA);
                assertThatParagraphHasCorrectText();
                assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
                assertThatModifiedByTagContainsRightNameAndDate();
                assertThatNewTextParaHasDefaultSourceTag();
                assertThatNewTextParaHasPubTag();
                assertThatEditorMessagePaneHasNoErrors();
        }

// ---------- Assistive methods ----------

        private void assertThatNewTextParaHasDefaultSourceTag()
        {
                assertThat(editorPage().getElementsText(HIGHLIGHTED_PARA + SOURCE_TAG))
                        .as("New Text Para should have a default source tag")
                        .isEqualTo(defaultSourceTag);
        }

        private void assertThatNewTextParaHasPubTag()
        {
                assertThat(editorPage().getElementsText(HIGHLIGHTED_PARA + ANY_PUBTAG_IN_METADATA_BLOCK))
                        .as("New Text Para should have a pub tag")
                        .isEqualTo("NOPUB");
        }
}
