package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractInsertNewParagraphTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Keys;

import java.awt.*;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.PARATEXT_NODE_WITH_TEXT_SELECTED;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertNewParagraphTests extends AbstractInsertNewParagraphTests
{
    private static final String IOWA_SOURCE_RENDITION_UUID = "I2B6D1EE043EA11E8B306BDCAB99DF270";
//    private static final String IOWA_SOURCE_RENDITION_UUID = "I8B1C2100BA6111E797B6DB99DFD08357"; // for dev

    @BeforeEach
    public void openRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
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
     * VERIFY: it will not have a NOPUB tag
     * VERIFY: it will not have a source tag
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
     * VERIFY: it will not have a NOPUB tag
     * VERIFY: it will not have a source tag
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
     * VERIFY: it will not have a NOPUB tag
     * VERIFY: it will not have a source tag
     * VERIFY: The Text Paragraph has a modified by tag
     */

    /**
     * STORY/BUG - HALCYONST-10038 <br>
     * SUMMARY - Insert new paragraph (Source) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource(ParagraphInsertMethods.class)
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    void insertNewParagraphSourceLegalTest(ParagraphInsertMethods insertMethod)
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
        assertThat(editorPage().doesElementExist(format(PARATEXT_NODE_WITH_TEXT_SELECTED, phrase)))
                .as(format("New tree node should be displayed and highlighted. Tree node text is '%s'", phrase))
                .isTrue();
        // assert that new text para is appeared
        editorPage().switchToEditorTextFrame();
        assertThat(editorPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT))
                .as("New Text Paragraph should appear")
                .contains(phrase);
        assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
        assertThatModifiedByTagContainsRightNameAndDate();
        // assert that new text para has no default source tag
        assertThat(editorPage().doesElementExist(HIGHLIGHTED_PARA + SOURCE_TAG))
                .as("New Text Para should not have a source tag")
                .isFalse();
        // assert that new text para has no pub tag
        assertThat(editorPage().doesElementExist(HIGHLIGHTED_PARA + ANY_PUBTAG_IN_METADATA_BLOCK))
                .as("New Text Para should not have a pub tag")
                .isFalse();
        assertThatEditorMessagePaneHasNoErrors();
    }

    /*
     * Insert new paragraph via hotkeys multiple times: critical bug HALCYONST-8998
     *
     * 1. Open document
     * 2. Scroll to chunk 2
     * 3. Block select any paragraph (click on english label to highlight a paragraph)
     * 4. Select Alt-I.
     * VERIFY: A new paragraph is inserted and block-selected
     * 5. Select Alt-I again
     * VERIFY: A new paragraph should be inserted and the prior insert should be deselected and the new paragraph should be block-selected.
     *
     * 1. Open document
     * 2. Scroll to chunk 2
     * 3. Place cursor in any paragraph text (paragraph is not highlighted)
     * 4. Select Alt-I.
     * VERIFY: A paragraph is inserted and block-selected
     * 5. Select Alt-I again
     * VERIFY: A new paragraph should be inserted and the prior insert should be deselected and the new paragraph should be block-selected.
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @ValueSource(strings = {
            TEXT_PARAGRAPH_LABEL,
            PARATEXT
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertNewParagraphViaHotkeysMultipleTimesTest(String element) throws AWTException {
        // add first paragraph
        addParagraph(ParagraphInsertMethods.HOTKEY, FIRST_PARA + element);

        // asserts for the first para
        assertThatOnlyNewParagraphIsHighlighted(SECOND_PARA);
        assertThatParagraphHasCorrectText();
        assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
        assertThatModifiedByTagContainsRightNameAndDate();

        // add second paragraph
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.HOTKEY);
        editorTextPage().waitForElementExists(THIRD_PARA);

        // asserts for the second para
        assertThatOnlyNewParagraphIsHighlighted(THIRD_PARA);
        assertThatParagraphHasCorrectText();
        assertThatMnemonicOfFreshlyAddedParagraphIsSameToExistedParagraphMnemonic();
        assertThatModifiedByTagContainsRightNameAndDate();
        assertThatEditorMessagePaneHasNoErrors();
    }
    private void placeCursorAtTheBeginningOfTextParagraph()
    {
        editorTextPage().click(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().sendKeys(Keys.ARROW_DOWN);
        editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().sendKeys(Keys.HOME);
    }
}
