package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Keys;

import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class AbstractInteractionsWithMarkupsViaContextMenuTests extends TestService
{
    protected static final String MARKUP_UNDER_NUMBER = "(//%s)[%s]";
    protected static final String TEST_PHRASE = "TEST" +
            DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
    protected static final String FOOTNOTE_REF_VALUE = "31415926";
    protected static final String TEXT_EQUALS_PATTERN = "[text()='%s']";
    protected static final String TEXT_IN_MARKUPS = TEXT_PARAGRAPH_PARATEXT + TEXT_EQUALS_PATTERN;
    private static final String MARKUP_IMAGE = "//%s" + MARKUP_IMAGE_LABEL;

    protected final SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    protected void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    protected void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /*
     * Highlight some text in a Text Paragraph, and apply bold markup to it
     * Directly next to, but outside the markup images, highlight additional text, and apply italic markup
     * VERIFY: At this point, you should have bold and italic markup back-to-back.
     * VERIFY: There should be no special characters, spaces, etc. inserted in between.
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void applyBoldMarkupAndDirectlyNextToItItalicMarkupTest()
    {
        String bold = "Bold";
        String italic = "Italic";
        String xpathToItalicTextPrecedingSibling = format(ITALIC_TEXT, italic.toUpperCase()) + PRECEDING_SIBLING;

        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        insertTextAndHighlightFromLeftToRight(bold.toUpperCase());
        applyMarkup(bold, bold.toLowerCase());

        editorTextPage().sendKeys(Keys.ARROW_RIGHT);

        editorTextPage().sendKeys(italic.toUpperCase());
        editorTextPage().highlightHelper(italic.toUpperCase());
        applyMarkup(italic, italic.toLowerCase().substring(0, 4));

        assertThat(editorTextPage()
                .doesElementExist(format(xpathToItalicTextPrecedingSibling, bold.toLowerCase())))
                .as("Both the BOLD and ITALIC markups should exist")
                .isTrue();

        assertThat(editorTextPage()
                .doesElementExist(format(xpathToItalicTextPrecedingSibling, "text()")))
                .as("No text between markups should exist")
                .isFalse();

        assertThat(editorTextPage()
                .doesElementExist(format(xpathToItalicTextPrecedingSibling, "span")))
                .as("No special characters between markups should exist")
                .isFalse();
    }

    /*
     * Highlight some text in a Text Paragraph, and apply bold or italic markup to it
     * Left-Click and then Right-Click on the markup and select 'Delete' from the context menu
     * VERIFY: The markup and the text inside are deleted
     */

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("getMarkupTestData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteWrappedInMarkupTextViaContextMenuTest(String markupNameButton, String markupNameXpath)
    {
        String textToWrapInMarkups = TEST_PHRASE;

        typeTextAndWrapItInMarkups(textToWrapInMarkups, markupNameButton, markupNameXpath);
        openContextMenuAndSelectAction(format(MARKUP_IMAGE, markupNameXpath), DELETE);

        softAssertThatMarkupIsGone("//" + markupNameXpath, markupNameButton);
        softAssertThatTextIsGone(format(TEXT_IN_MARKUPS, textToWrapInMarkups));

        softAssertions.assertAll();
    }

    /*
     * Highlight some text in a Text Paragraph, and apply bold or italic markup to it
     * Left-Click and then Right-Click on the markup and select 'Cut (Ctrl+X)' from the context menu
     * Put your cursor to another part of text and try to paste it from the context menu
     * VERIFY: The markup is cut and pasted to other part of text
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("getMarkupTestData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cutAndPasteWrappedInMarkupTextViaContextMenuTest(String markupNameButton, String markupNameXpath)
    {
        String textToWrapInMarkups = TEST_PHRASE;

        typeTextAndWrapItInMarkups(textToWrapInMarkups, markupNameButton, markupNameXpath);
        openContextMenuAndSelectAction(format(MARKUP_IMAGE, markupNameXpath), CUT_CTRL_X);

        softAssertThatMarkupIsGone("//" + markupNameXpath, markupNameButton);
        softAssertThatTextIsGone(format(TEXT_IN_MARKUPS, textToWrapInMarkups));

        editorTextPage().click(TEXT_PARAGRAPH_PARATEXT);
        editorTextPage().sendKeys("777");

        openContextMenuAndSelectAction(format(TEXT_PARAGRAPH_PARA_UNDER_NUMBER_GIVEN, 1), PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted("//" + markupNameXpath, textToWrapInMarkups);

        softAssertions.assertAll();
    }

    /*
     * Filter for the document and edit it via Dynamic Scrolling editor
     * Highlight some text in a Text Paragraph, and apply bold or italic markup to it
     * Left-Click and then Right-Click on the markup and select 'Copy (Ctrl+C)' from the context menu
     * Put your cursor to another part of text and try to paste it from the context menu
     * VERIFY: The markup is copied and pasted to other part of text
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("getMarkupTestData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteWrappedInMarkupTextViaContextMenuTest(String markupNameButton, String markupNameXpath)
    {
        String textToWrapInMarkups = TEST_PHRASE;

        typeTextAndWrapItInMarkups(textToWrapInMarkups, markupNameButton, markupNameXpath);
        openContextMenuAndSelectAction(format(MARKUP_IMAGE, markupNameXpath), COPY_CTRL_C);

        assertThatTextAndMarkupsStayInTextAfterCopying("//" + markupNameXpath, textToWrapInMarkups);

        openContextMenuAndSelectAction(format(TEXT_PARAGRAPH_PARA_UNDER_NUMBER_GIVEN, 1), PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted("//" + markupNameXpath, textToWrapInMarkups);
        assertThatCopiedTextIsEqualToPastedText(markupNameXpath, textToWrapInMarkups);
    }

    // ---------- Data Providers ----------

    static Stream<Arguments> getMarkupTestData()
    {
        return Stream.of(
                arguments("Bold", "bold"),
                arguments("Italic", "ital"),
                arguments("Add", "added.material"),
                arguments("Delete", "deleted.material"));
    }

    // ---------- Assertions ----------

    protected void softAssertThatMarkupIsGone(String xpathToMarkup, String markupName)
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(xpathToMarkup))
                .as(format("'%s' markup should disappear", markupName))
                .isFalse();
    }

    protected void softAssertThatTextIsGone(String xpathToText)
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(xpathToText))
                .as("The wrapped text should disappear.")
                .isFalse();
    }

    protected void assertThatMarkupsAndTextAreCopiedToClipboard()
    {
        editorPage().switchToEditor();
        assertThat(editorMessagePage()
                .checkMessagePaneForText("info: Copying to clipboard initiated...completed"))
                .as("There should be a message confirming copying in the editor message pane")
                .isTrue();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("No errors should appear in the editor message pane")
                .isFalse();
        editorPage().switchDirectlyToTextFrame();
    }

    protected void assertThatBothTextAndMarkupsArePasted(String markupNameXpath, String textToWrapInMarkups)
    {
        assertThat(editorTextPage()
                .doesElementExist(markupNameXpath + format(TEXT_EQUALS_PATTERN, textToWrapInMarkups)))
                .as("There are both text and markups should appear.")
                .isTrue();
    }

    protected void assertThatTextAndMarkupsStayInTextAfterCopying(String markupNameXpath, String textToWrapInMarkups)
    {
        assertThat(editorTextPage()
                .doesElementExist(markupNameXpath + format(TEXT_EQUALS_PATTERN, textToWrapInMarkups)))
                .as("Both text and markup should stay in the paragraph after copying")
                .isTrue();
        assertThatMarkupsAndTextAreCopiedToClipboard();
    }

    protected void assertThatCopiedTextIsEqualToPastedText(String markupNameXpath, String textToWrapInMarkups)
    {
        assertThat(editorTextPage().getElementsText(format(MARKUP_UNDER_NUMBER, markupNameXpath, 1)))
                .as("The text in both the fragments must be identical and equal to ", textToWrapInMarkups)
                .isEqualTo(editorTextPage().getElementsText(format(MARKUP_UNDER_NUMBER, markupNameXpath, 2)));
    }

    protected void assertThatFootnoteAndReferenceAreInsertedCorrectly(String xpathToReference,
                                                                      String xpathToPreviousBlock)
    {
        assertThat(editorTextPage()
                .doesElementExist(xpathToReference + FOOTNOTE_REFERENCE))
                .as("The footnote reference should be inserted into the specified block")
                .isTrue();
        assertThat(editorTextPage().doesElementExist(FOOTNOTE + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The Footnote should be highlighted after inserting")
                .isTrue();
        assertThat(editorTextPage()
                .doesElementExist(xpathToPreviousBlock))
                .as("The footnote should be inserted after Credit (for Target) or Delta Feature (for Source) blocks")
                .isTrue();
    }

    protected void assertThatReferenceValuesInTextAndFootnoteAreEqual(String xpathToReference, String xpathToFootnote)
    {
        assertThat(editorTextPage().getElementsText(xpathToReference))
                .as("Text of the reference should be equal to the footnote text.")
                .isEqualTo(editorTextPage().getElementsText(xpathToFootnote));
    }

    protected void assertThatReferenceMarkupsAreHighlightedAfterPressingFindFootnotesReferences(String xpath)
    {
        assertThat(editorTextPage().doesElementExist(xpath + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The footnote reference should be highlighted in the paragraph.")
                .isTrue();
    }

    // ---------- Assistive methods ----------

    protected void placeCursorBeforeTextOfSpecifiedTextParagraph(String xpathToParagraphEnglishLabel)
    {
        editorTextPage().click(xpathToParagraphEnglishLabel);
        editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().sendKeys(Keys.ARROW_DOWN);
        editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
        editorTextPage().sendKeys(Keys.HOME);
    }

    private void insertTextAndHighlightFromLeftToRight(String phrase)
    {
        editorTextPage().sendKeys(phrase);
        editorTextPage().sendKeys(Keys.HOME);
        editorTextPage().highlightHelper(phrase.length());
    }

    private void applyMarkup(String buttonName, String buttonNameXpath)
    {
        editorPage().breakOutOfFrame();
        editorToolbarPage().click(format("//a[@title='%s']", buttonName));
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElement(format(MARKUP_IMAGE, buttonNameXpath));
    }

    protected void openContextMenuAndSelectAction(String elementToInteractWith, String contextMenuElement)
    {
        editorTextPage().click(elementToInteractWith);
        editorTextPage().rightClick(elementToInteractWith);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(contextMenuElement);
        editorTextPage().switchToEditorTextArea();
    }

    private void typeTextAndWrapItInMarkups(String testText, String markupNameButton, String markupNameXpath)
    {
        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        insertTextAndHighlightFromLeftToRight(testText);
        applyMarkup(markupNameButton, markupNameXpath);
    }

    protected void insertFootnoteWithReferenceValue()
    {
        editorPage().openInsertFootnoteDialog();
        insertFootnotePage().setFootnoteReference(FOOTNOTE_REF_VALUE);
        insertFootnotePage().clickSaveFootnote();
        editorPage().switchDirectlyToTextFrame();
    }
}