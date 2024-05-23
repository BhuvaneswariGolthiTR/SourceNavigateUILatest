package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;

public abstract class AbstractPasteAndPasteAsTests extends TestService
{
    private static final String ASSERTION_MESSAGE = "Actual %s doesn't match expected";
    private static final String COUNT_MESSAGE = String.format(ASSERTION_MESSAGE, "count of pasted para");
    private static final String TEXT_VALUE_MESSAGE = String.format(ASSERTION_MESSAGE, "text value in pasted para");
    private static final String HIGHLIGHTED_PARATEXT = PARA + CLASS_HIGHLIGHTED_POSTFIX + PARATEXT;
    private SoftAssertions softAssertions;

    @BeforeEach
    protected abstract void beforeEachTestMethod();

    @AfterEach
    protected void afterEachTestMethod()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /**
     * STORY/BUG - HALCYONST-8476/HALCYONST-9001 <br>
     * SUMMARY - Paste and Paste As support for line breaks <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pasteAndPasteAsSupportForLineBreaksLegalTest()
    {
        softAssertions = new SoftAssertions();
        String paraSpanText = "Text Paragraph";
        String paraSpan = String.format(SPAN_BY_TEXT, paraSpanText);

        //String with line breaks
        String clipboardText = "This is line 1\nThis is line 2\nThis is line 3";
        ClipboardUtils.setSystemClipboard(clipboardText);

        //Paste string with line breaks as sibling
        editorPage().switchToEditorTextFrame();
        editorTextPage().scrollToView(paraSpan);
        editorTextPage().rightClick(paraSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchDirectlyToTextFrame();

        //Assert that string with line breaks pasted as single paragraph
        softAssertPastedParagraphCountIsEqualTo(1);
        //Assert that text in pasted paragraph is the same as string with line breaks
        softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARATEXT))
                .as(TEXT_VALUE_MESSAGE)
                .isEqualTo(clipboardText.replace("\n", " "));

        //Paste string with line breaks as text paragraph (smp)
        editorTextPage().click(paraSpan);
        editorTextPage().rightClick(paraSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteAsTextParagraphSmp();
        editorPage().switchDirectlyToTextFrame();

        //Assert that string with line breaks pasted as multiple paragraphs
        softAssertPastedParagraphCountIsEqualTo(3);
        //Assert that text in pasted paragraphs is the same as string with line breaks
        softAssertions.assertThat(editorTextPage().getElementsTextList(HIGHLIGHTED_PARATEXT))
                .as(TEXT_VALUE_MESSAGE)
                .isEqualTo(Arrays.asList(clipboardText.split("\n")));

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void softAssertPastedParagraphCountIsEqualTo(int expectedCount)
    {
        softAssertions.assertThat(editorTextPage().countElements(HIGHLIGHTED_PARATEXT))
                .as(COUNT_MESSAGE)
                .isEqualTo(expectedCount);
    }
}
