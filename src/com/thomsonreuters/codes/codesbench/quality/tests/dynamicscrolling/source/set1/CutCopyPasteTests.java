package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT_WITH_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests.Content.WHOLE_PARAGRAPH;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CutCopyPasteTests extends AbstractCutCopyPasteTests
{
    private static final String UUID = "I3F576F70107D11E6B1DA8617B6232F4C";

    @BeforeEach
    public void openSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cutCopyAndPasteBlocksOfContentSourceLegalTest(Method method)
    {
        selectOneTextParagraph();

        String paragraphContent = getHighlightedParagraphContentInChunk(0);
        //Cut previously selected text paragraph
        cutContentVia(WHOLE_PARAGRAPH, method);
        //Assert that text paragraph is removed from the document after cutting
        assertThat(editorTextPage().doesElementExist(
                CHUNK_0 + format(PARATEXT_WITH_TEXT, paragraphContent)))
                .as(format("The [%s] content should be removed after cutting", paragraphContent))
                .isFalse();

        //Paste previously cut text paragraph
        pasteWholeParagraphFromBuffer(method);
        String pastedParagraphContent = getHighlightedParagraphContentInChunk(1);
        //Assert that previously cut text paragraph is pasted to the document
        assertThat(paragraphContent)
                .as("The [%s] content should be appeared in the document after pasting", paragraphContent)
                .isEqualTo(pastedParagraphContent);

        clickToolbarUndoButton();
        clickToolbarUndoButton();

        selectOneTextParagraph();

        //Copy previously selected text paragraph
        copyContentVia(WHOLE_PARAGRAPH, method);
        String copiedParagraphContent = getHighlightedParagraphContentInChunk(0);
        //Assert that previously copied text paragraph is not removed from the document
        assertThat(paragraphContent)
                .as("The [%s] content should not be removed from the document after copying", paragraphContent)
                .isEqualTo(copiedParagraphContent);

        //Paste previously copied text paragraph
        pasteWholeParagraphFromBuffer(method);
        pastedParagraphContent = getHighlightedParagraphContentInChunk(1);
        //Assert that previously copied text paragraph is pasted to the document
        assertThat(paragraphContent)
                .as("The [%s] content should be appeared in the document after pasting", paragraphContent)
                .isEqualTo(pastedParagraphContent);
    }

//  ------------- Assistive methods: -------------

    private String getHighlightedParagraphContentInChunk(int chunkNumber)
    {
        return editorTextPage().getElementsText(
                format(LOADED_CHUNK, chunkNumber) + PARA + CLASS_HIGHLIGHTED_POSTFIX + PARATEXT);
    }

    private void selectOneTextParagraph()
    {
        editorPage().click(CHUNK_0_TEXT_PARA_SPAN_1);
        waitForParagraphsToBeHighlightedInChunk(0, 1);
    }
}
