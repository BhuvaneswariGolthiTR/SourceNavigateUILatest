package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCopyAndPasteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class CopyAndPasteTests extends AbstractCopyAndPasteTests
{
    private static final String UUID = "I7B704C2014EE11DA8AC5CD53670E6B4E";
    private static final String NOPUB_PUB_TAG = "NOPUB";
    private static final String PARA_SPAN_XPATH = PARA_XPATH + "[1]" + SPAN;
    private static final String COPY_PARA_XPATH = String.format(PARA_SPAN_XPATH, 0, 5);
    private static final String PASTE_PARA_XPATH = String.format(PARA_SPAN_XPATH, 1, 1);
    private static final String PASTED_PARA_XPATH = PASTE_PARA_XPATH + FOLLOWING_SIBLING_PARA;

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        //Go to Hierarchy page and open target node in DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
    }

    /**
     * STORY/BUG - HALCYONST-2470 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Text Paragraph (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToTextParagraphTargetLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().scrollToView(COPY_PARA_XPATH);
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, 1);

        int paragraphsSizeBeforePasting = countTextParagraphs();
        pasteAsTextParagraphSmp(PASTE_PARA_XPATH);

        softAssertThatTextParagraphIsPasted(paragraphsSizeBeforePasting, countTextParagraphs());
        softAssertThatTextParagraphIsPastedAsSibling(PASTED_PARA_XPATH);
        softAssertThatMnemonicTagIsAppeared(PASTED_PARA_XPATH);

        //Assert that Pub tag is appeared
        softAssertions.assertThat(editorTextPage().getElementsText(PASTED_PARA_XPATH + ANY_PUBTAG_IN_METADATA_BLOCK))
                .as(String.format("[%s] pub tag should be appeared", NOPUB_PUB_TAG))
                .isEqualTo(NOPUB_PUB_TAG);

        String actualSourceTag = editorTextPage().getElementsText(PASTED_PARA_XPATH + SOURCE_TAG);

        softAssertThatModifiedByTagIsAdded();

        //Assert that appeared Source tag is the same as default
        editorPage().switchToEditor();
        softAssertions.assertThat(actualSourceTag)
                .as("Appeared Source tag should be the same as default")
                .isEqualTo(editorPreferencesPage().grabDefaultSourceTag());

        softAssertions.assertAll();
    }

    /**
     * STORY/BUG - HALCYONST-2470 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Text Paragraph (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToTextParagraphMultipleSelectionTargetLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().selectMultipleTextParagraphs(9, 10);
        editorPage().scrollToView(COPY_PARA_XPATH);
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, 1);

        int paragraphsSizeBeforePasting = countTextParagraphs();
        pasteAsTextParagraphSmp(PASTE_PARA_XPATH);

        softAssertThatTextParagraphsArePasted(paragraphsSizeBeforePasting, countTextParagraphs());
        softAssertThatTextParagraphsArePastedAsSiblings(PASTED_PARA_XPATH);
        softAssertThatMnemonicTagsAreAppeared(PASTED_PARA_XPATH);

        //Assert that Pub tags are appeared
        softAssertions.assertThat(editorTextPage().getElementsTextList(PASTED_PARA_XPATH + ANY_PUBTAG_IN_METADATA_BLOCK))
                .as(String.format("[%s] pub tags should be appeared", NOPUB_PUB_TAG))
                .allMatch(pubTag -> pubTag.equals(NOPUB_PUB_TAG));

        List<String> actualSourceTagList = editorTextPage().getElementsTextList(PASTED_PARA_XPATH + SOURCE_TAG);

        softAssertThatModifiedTagsAreAdded();

        //Assert that appeared Source tags are the same as default
        editorPage().switchToEditor();
        String defaultSourceTag = editorPreferencesPage().grabDefaultSourceTag();
        softAssertions.assertThat(actualSourceTagList)
                .as("Appeared Source tags should be the same as default")
                .allMatch(sourceTag -> sourceTag.equals(defaultSourceTag));

        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteElementsWithF7TargetLegalTest()
    {
        int countOfSiblingsToHighlight = 1;

        //Click on Text Paragraph, highlight following siblings and copy selected content
        String textParaXpath = String.format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 9);
        editorPage().scrollToView(textParaXpath);
        highlightTextParagraphsAndCopyThem(textParaXpath, countOfSiblingsToHighlight);

        assertThatSelectedContentIsCopied();
        assertThatCopiedContentIsStillSelectedAfterCopying(countOfSiblingsToHighlight);

        paste(textParaXpath);

        assertThatWarningMessageIsAppearedInMessagePane();

        editorPage().switchToEditorTextFrame();

        //Paste previously copied content
        textParaXpath = String.format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 12);
        int paragraphsBeforePasting = editorPage().countElements(TEXT_PARAGRAPH_SPAN);
        editorPage().scrollToView(textParaXpath);
        pasteAndSwitchToEditorTextFrame(textParaXpath);
        int paragraphsAfterPasting = editorPage().countElements(TEXT_PARAGRAPH_SPAN);

        assertThatPastedContentIsHighlighted(paragraphsBeforePasting, paragraphsAfterPasting, countOfSiblingsToHighlight);
    }

    /**
     * STORY/BUG - HALCYONST-5804 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Feature (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToFeatureTargetLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().scrollToView(COPY_PARA_XPATH);
        String copiedParagraphTextContent = getParagraphsContent(COPY_PARA_XPATH);
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, 1);

        pasteAsFeatureTextEditorialNote(PASTE_PARA_XPATH);

        softAssertThatTextEditorialNoteIsInsertedAsAChildTo(ANNOTATIONS);
        softAssertThatMiscellaneousTextNoteHeadingIsInsertedAsAChildToTextEditorialNote(ANNOTATIONS);
        softAssertThatItalicNoteIsInsertedAsAChildToTextEditorialNote(ANNOTATIONS);
        softAssertThatItalicNoteHasTheSameTextContentAsCopiedParagraph(ANNOTATIONS, copiedParagraphTextContent);

        softAssertions.assertAll();
    }

    /**
     * STORY/BUG - HALCYONST-5804 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Feature - multiple selection (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToFeatureMultipleSelectionTargetLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().selectMultipleTextParagraphs(9, 10);
        List<String> copiedParagraphsTextContent = getSelectedParagraphsContentList();
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, 1);

        pasteAsFeatureTextEditorialNote(PASTE_PARA_XPATH);

        softAssertThatTextEditorialNoteIsInsertedAsAChildTo(ANNOTATIONS);
        softAssertThatMiscellaneousTextNoteHeadingIsInsertedAsAChildToTextEditorialNote(ANNOTATIONS);
        softAssertThatTheNumberOfItalicNotesIsEqualToTheNumberOfCopiedParagraphs(ANNOTATIONS);
        softAssertThatItalicNotesHaveTheSameTextContentAsCopiedParagraphs(ANNOTATIONS, copiedParagraphsTextContent);

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private int countTextParagraphs()
    {
        return editorTextPage().countElements(String.format(PARA_XPATH, 1, 1));
    }
}
