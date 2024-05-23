package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT_WITH_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCutCopyPasteTests.Content.WHOLE_PARAGRAPH;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CutCopyPasteTests extends AbstractCutCopyPasteTests
{
    private static final String UUID = "I7B704C2014EE11DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cutCopyAndPasteBlocksOfContentTargetLegalTest(Method method)
    {
        selectTwoTextParagraphs();

        List<String> paragraphsContentList = getHighlightedParagraphsContentInChunk(0);
        //Cut previously selected text paragraphs
        cutContentVia(WHOLE_PARAGRAPH, method);
        //Assert that text paragraphs are removed from the document after cutting
        paragraphsContentList.forEach(paragraphContent ->
                assertThat(editorTextPage().doesElementExist(
                        CHUNK_0 + format(PARATEXT_WITH_TEXT, paragraphContent)))
                .as(format("The [%s] content should be removed after cutting", paragraphContent))
                .isFalse());

        //Paste previously cut text paragraphs
        pasteWholeParagraphFromBuffer(method);
        List<String> pastedParagraphsContentList = getHighlightedParagraphsContentInChunk(1);
        //Assert that previously cut text paragraphs are pasted to the document
        assertThat(paragraphsContentList)
                .as("The [%s] content should be appeared in the document after pasting", paragraphsContentList)
                .isEqualTo(pastedParagraphsContentList);

        clickToolbarUndoButton();
        clickToolbarUndoButton();

        selectTwoTextParagraphs();

        //Copy previously selected text paragraphs
        copyContentVia(WHOLE_PARAGRAPH, method);
        List<String> copiedParagraphsContentList = getHighlightedParagraphsContentInChunk(0);
        //Assert that previously copied text paragraphs are not removed from the document
        assertThat(paragraphsContentList)
                .as("The [%s] content should not be removed from the document after copying", paragraphsContentList)
                .isEqualTo(copiedParagraphsContentList);

        //Paste previously copied text paragraphs
        pasteWholeParagraphFromBuffer(method);
        pastedParagraphsContentList = getHighlightedParagraphsContentInChunk(1);
        //Assert that previously copied text paragraphs are pasted to the document
        assertThat(paragraphsContentList)
                .as("The [%s] content should be appeared in the document after pasting", paragraphsContentList)
                .isEqualTo(pastedParagraphsContentList);
    }

//  ------------- Assistive methods: -------------

    private List<String> getHighlightedParagraphsContentInChunk(int chunkNumber)
    {
        return editorTextPage().getElementsTextList(
                format(LOADED_CHUNK, chunkNumber) + PARA + CLASS_HIGHLIGHTED_POSTFIX + PARATEXT);
    }

    private void selectTwoTextParagraphs()
    {
        editorPage().selectMultipleTextParagraphs(9, 10);
        waitForParagraphsToBeHighlightedInChunk(0, 2);
    }
}
