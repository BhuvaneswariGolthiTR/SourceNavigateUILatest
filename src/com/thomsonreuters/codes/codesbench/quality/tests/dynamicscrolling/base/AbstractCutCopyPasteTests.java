package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests.Content.PARAGRAPH_PART;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests.Content.WHOLE_PARAGRAPH;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests.Method.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCutCopyPasteTests extends TestService
{
    private static final String PARA_TEXT = "paratext";
    protected static final String CHUNK_0 = format(LOADED_CHUNK, 0);
    protected static final String CHUNK_1 = format(LOADED_CHUNK, 1);
    protected static final String CHUNK_0_TEXT_PARA_SPAN_1 = CHUNK_0 + FIRST_TEXT_PARAGRAPH;
    private static final String CHUNK_1_TEXT_PARA_SPAN_1 = CHUNK_1 + format(TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1) + SPAN;
    private static final String CHUNK_0_TEXT_PARAGRAPH_1 = CHUNK_0_TEXT_PARA_SPAN_1 + format(FOLLOWING_SIBLING, PARA_TEXT);
    private static final String CHUNK_1_TEXT_PARAGRAPH_1 = CHUNK_1_TEXT_PARA_SPAN_1 + format(FOLLOWING_SIBLING, PARA_TEXT);
    private static final String CHUNK_0_HIGHLIGHTED_PARA_SPAN = CHUNK_0 + PARA + CLASS_HIGHLIGHTED_POSTFIX + SPAN;
    private static final String EXPECTED_MESSAGE = "Copying to clipboard initiated...completed";
    private static final String NO_SUCH_METHOD_MESSAGE = "No such method!";

    public enum Method
    {
        TOOLBAR,
        CONTEXT_MENU,
        HOTKEY
    }

    public enum Content
    {
        PARAGRAPH_PART,
        WHOLE_PARAGRAPH
    }

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    public static Object[][] testData()
    {
        return new Object[][]
                {
                        {TOOLBAR},
                        {CONTEXT_MENU},
                        {HOTKEY}
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cutCopyAndPasteTextLegalTest(Method method)
    {
        String phrase = format("Autotest%s ", System.currentTimeMillis());

        insertTextIntoTextParagraphAndHighlightIt(phrase);
        //Assert that inserted text is appeared in text paragraph
        assertThatTextParagraphContainsTextByXpath(CHUNK_0_TEXT_PARAGRAPH_1, phrase,
                "The [%s] text should be appeared in the paragraph after inserting");

        //Cut highlighted text
        cutContentVia(PARAGRAPH_PART, method);
        //Assert that inserted previously text is removed from text paragraph
        assertThat(editorTextPage().getElementsText(CHUNK_0_TEXT_PARAGRAPH_1))
                .as(format("The [%s] text should be removed from the paragraph after cutting", phrase))
                .doesNotContain(phrase);

        //Paste previously cut text
        pasteParagraphPartFromBuffer(method);

        //Assert that pasted text is appeared in text paragraph
        assertThatTextParagraphContainsTextByXpath(CHUNK_1_TEXT_PARAGRAPH_1, phrase,
                "The [%s] text should be appeared in the paragraph after pasting");

        clickToolbarUndoButton();

        //Insert a phrase in Text Paragraph for copying
        phrase = format("Autotest%s ", System.currentTimeMillis());
        insertTextIntoTextParagraphAndHighlightIt(phrase);

        //Assert that inserted text is appeared in text paragraph
        assertThatTextParagraphContainsTextByXpath(CHUNK_0_TEXT_PARAGRAPH_1, phrase,
                "The [%s] text should be appeared in the paragraph after inserting new phrase");

        //Copy highlighted text
        copyContentVia(PARAGRAPH_PART, method);

        //Assert that the previously copied text is not removed from the text paragraph
        assertThatTextParagraphContainsTextByXpath(CHUNK_0_TEXT_PARAGRAPH_1, phrase,
                "The [%s] text should not be removed from the paragraph after copying");

        //Paste previously copied text
        pasteParagraphPartFromBuffer(method);

        //Assert that pasted text is appeared in text paragraph
        assertThatTextParagraphContainsTextByXpath(CHUNK_1_TEXT_PARAGRAPH_1, phrase,
                "The [%s] text should be appeared in the paragraph after pasting");
    }

//  ------------- Assistive methods: -------------

    private void insertTextIntoTextParagraphAndHighlightIt(String text)
    {
        editorPage().switchToEditorAndScrollToChunk(1);
        editorTextPage().insertPhraseUnderGivenLabel(text, CHUNK_0_TEXT_PARA_SPAN_1);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        editorTextPage().highlightHelper(text);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    protected void pasteParagraphPartFromBuffer(Method method)
    {
        editorPage().switchToEditorAndScrollToChunk( 1);
        editorPage().scrollToTop();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        editorTextPage().scrollToView(format(LOADED_CHUNK, 1) + FIRST_TEXT_PARAGRAPH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        editorTextPage().click(CHUNK_1 + FIRST_TEXT_PARAGRAPH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        editorTextPage().click(CHUNK_1 + TEXT_PARAGRAPH_PARA + PARATEXT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        pasteContentVia(PARAGRAPH_PART, method);
    }

    protected void pasteWholeParagraphFromBuffer(Method method)
    {
        int chunkNumber = 1;
        editorPage().switchToEditorAndScrollToChunk(chunkNumber + 1);
        editorTextPage().click(CHUNK_1 + FIRST_TEXT_PARAGRAPH);
        waitForParagraphsToBeHighlightedInChunk(chunkNumber, 1);
        pasteContentVia(WHOLE_PARAGRAPH, method);
    }

    protected void clickToolbarUndoButton()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatExpectedMessageIsAppearedInTheEditorMessagePane(String... messages)
    {
        assertThat(editorMessagePage().checkMessage(messages))
                .as(format("There should be [%s] message(s) in the editor message pane", Arrays.toString(messages)))
                .isTrue();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatTextParagraphContainsTextByXpath(String xPath, String text, String message)
    {
        assertThat(editorTextPage().getElementsText(xPath))
                .as(format(message, text))
                .contains(text);
    }

    protected void cutContentVia(Content content, Method method)
    {
        switch (method)
        {
            case TOOLBAR:
                editorPage().switchToEditor();
                editorToolbarPage().clickCut();
                break;
            case CONTEXT_MENU:
                switch (content)
                {
                    case PARAGRAPH_PART:
                        editorTextPage().click(CHUNK_0 + TEXT_PARAGRAPH_PARA + PARATEXT);
                        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                        editorTextPage().ctrlAUsingAction();
                        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                        editorTextPage().rightClick(CHUNK_0 + TEXT_PARAGRAPH_PARA + PARATEXT);
                        break;
                    case WHOLE_PARAGRAPH:
                    default:
                        editorTextPage().rightClick(CHUNK_0_HIGHLIGHTED_PARA_SPAN);
                }
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().cutCtrlX();
                break;
            case HOTKEY:
                editorTextPage().ctrlXUsingAction();
                editorPage().switchToEditor();
                break;
            default:
                throw new IllegalArgumentException(NO_SUCH_METHOD_MESSAGE);
        }
        assertThatExpectedMessageIsAppearedInTheEditorMessagePane(EXPECTED_MESSAGE);
    }

    protected void copyContentVia(Content content, Method method)
    {
        switch (method)
        {
            case TOOLBAR:
                editorPage().switchToEditor();
                editorToolbarPage().clickCopy();
                break;
            case CONTEXT_MENU:
                switch (content)
                {
                    case PARAGRAPH_PART:
                        editorTextPage().rightClick(CHUNK_0 + TEXT_PARAGRAPH_PARA + PARATEXT);
                        break;
                    case WHOLE_PARAGRAPH:
                    default:
                        editorTextPage().rightClick(CHUNK_0_HIGHLIGHTED_PARA_SPAN);
                }
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().copyCtrlC();
                break;
            case HOTKEY:
                editorTextPage().ctrlCUsingAction();
                editorPage().switchToEditor();
                break;
            default:
                throw new IllegalArgumentException(NO_SUCH_METHOD_MESSAGE);
        }
        assertThatExpectedMessageIsAppearedInTheEditorMessagePane(EXPECTED_MESSAGE, EXPECTED_MESSAGE);
    }

    protected void pasteContentVia(Content content, Method method)
    {
        switch (method)
        {
            case TOOLBAR:
                editorPage().switchToEditor();
                editorToolbarPage().clickPaste();
                break;
            case CONTEXT_MENU:
                switch (content)
                {
                    case PARAGRAPH_PART:
                        String id = editorTextPage().getElementsAttribute(CHUNK_1 + TEXT_PARAGRAPH_PARA + PARATEXT , "id");
                        editorTextPage().holdFocusAndRightClickOnElement(id);
                        editorTextPage().breakOutOfFrame();
                        editorTextContextMenu().pasteCtrlV();
                        break;
                    case WHOLE_PARAGRAPH:
                    default:
                        editorTextPage().rightClick(CHUNK_1_TEXT_PARA_SPAN_1);
                        editorTextPage().breakOutOfFrame();
                        editorTextContextMenu().pasteSiblingCtrlV();
                }
                break;
            case HOTKEY:
                editorTextPage().ctrlVUsingAction();
                editorPage().switchToEditor();
                break;
            default:
                throw new IllegalArgumentException(NO_SUCH_METHOD_MESSAGE);
        }
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();
        editorPage().switchDirectlyToTextFrame();
    }

    protected void waitForParagraphsToBeHighlightedInChunk(int chunkNumber, int paragraphsNumber)
    {
        new FluentWait<>(paragraphsNumber)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .until(f -> editorTextPage().getElements(
                        format(LOADED_CHUNK, chunkNumber) + PARA + CLASS_HIGHLIGHTED_POSTFIX).size() == paragraphsNumber);
    }
}
