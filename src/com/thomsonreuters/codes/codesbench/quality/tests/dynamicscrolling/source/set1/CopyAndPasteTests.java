package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractCopyAndPasteTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_CONTAINS_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditAttributesPageElements.ATTRIBUTE_EDITOR_TITLE;
import static java.lang.String.format;

public class CopyAndPasteTests extends AbstractCopyAndPasteTests
{
    private static final String UUID = "IC4105FD0F67911E3B148A5B04553906B";
    private static final String PARA_SPAN_XPATH = PARA_XPATH + "[1]" + SPAN + "[1]";
    private static final String COPY_PARA_XPATH = format(PARA_SPAN_XPATH, 1, 5);
    private static final String PASTE_PARA_XPATH = format(PARA_SPAN_XPATH, 0, 1);
    private static final String PASTED_PARA_XPATH = PASTE_PARA_XPATH + FOLLOWING_SIBLING_PARA;
    private static final int FIRST_CHUNK = 1;
    public static final String FOLLOWING_SIBLING = "/following-sibling::%s";
    private static final String FOLLOWING_SIBLING_PARATEXT = format(FOLLOWING_SIBLING, "paratext");


    @BeforeEach
    public void openSourceRenditionInDsEditor(TestInfo testInfo)
    {
        String uuid = "I1022D802CECE11E29D26F444B19FDB40";
        if(!(testInfo.getDisplayName().equals("deltaNoteIdUpdateOnCopyAndPasteSourceLegalTest()")))
        {
            uuid = UUID;
        }
            openSourceRenditionInDsEditor(uuid);
            editorPage().scrollToChunk(SECOND_CHUNK);
    }

    /**
     * STORY/BUG - HALCYONST-2470/10582 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Text Paragraph (Source) and verify there is no warning and errors  <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToTextParagraphSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorTextPage().click(COPY_PARA_XPATH);
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, FIRST_CHUNK);

        editorPage().jumpToBeginningOfDocument();
        int paragraphsSizeBeforePasting = countTextParagraphs();
        editorPage().scrollToView(PASTE_PARA_XPATH);
        pasteAsTextParagraphSmp(PASTE_PARA_XPATH);

        softAssertThatTextParagraphIsPasted(paragraphsSizeBeforePasting, countTextParagraphs());
        softAssertThatTextParagraphIsPastedAsSibling(PASTED_PARA_XPATH);
        softAssertThatMnemonicTagIsAppeared(PASTED_PARA_XPATH);
        softAssertThatModifiedByTagIsAdded();
        editorPage().switchToEditor();
        softAssertThatThereIsNoErrorMessageInMessagePane();
        softAssertThatThereIsNoWarningMessageInMessagePane();

        softAssertions.assertAll();
    }

    /**
     * STORY/BUG - HALCYONST-2470 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Text Paragraph (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToTextParagraphMultipleSelectionSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().selectMultipleTextParagraphs(7, 8);
        copyTextParagraphAndScrollToChunk(format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 7), FIRST_CHUNK);

        editorPage().jumpToBeginningOfDocument();
        int paragraphsSizeBeforePasting = countTextParagraphs();
        editorPage().scrollToView(PASTE_PARA_XPATH);
        pasteAsTextParagraphSmp(PASTE_PARA_XPATH);

        softAssertThatTextParagraphsArePasted(paragraphsSizeBeforePasting, countTextParagraphs());
        softAssertThatTextParagraphsArePastedAsSiblings(PASTED_PARA_XPATH);
        softAssertThatMnemonicTagsAreAppeared(PASTED_PARA_XPATH);
        softAssertThatModifiedTagsAreAdded();

        softAssertions.assertAll();
    }

    /**
     * STORY/BUG - HALCYONST-11526 <br>
     * SUMMARY - Delta Note ID update on copy and paste, should not result in DTD error (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaNoteIdUpdateOnCopyAndPasteSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        String deltaExcludeNote = "Delta Exclude Note";
        String exclude = "exclude";
        String idAttribute = "id";
        String firstDeltaSpan = format(DELTA_BY_NUMBER_SPAN, 1);
        editorPage().scrollToTop();

        //Copy first delta
        openContextMenuOnElement(firstDeltaSpan);
        editorTextContextMenu().copyCtrlC();
        editorPage().switchToEditorTextFrame();

        //Paste copied delta
        editorTextPage().click(format(DELTA_BY_NUMBER_SPAN, 2));
        editorTextPage().rightClick(format(DELTA_BY_NUMBER_SPAN, 2));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToEditorTextFrame();

        //Open Edit Attributes popup window
        editorTextPage().click(firstDeltaSpan);
        openContextMenuOnElement(firstDeltaSpan);
        editorTextContextMenu().editAttributes();

        //Assert that Edit Attributes window is opened
        softAssertions.assertThat(editAttributesPage().getTitle())
                .as("Edit Attributes window should be opened")
                .isEqualTo(ATTRIBUTE_EDITOR_TITLE);

        //Fill in necessary value and click save
        editAttributesPage().setFirstInput(exclude);
        editAttributesPage().clickSave();
        editorPage().switchToEditorTextFrame();

        //Assert that Edit Attributes window is closed
        softAssertions.assertThat(editorTextPage().getTitle())
                .as("Edit Attributes window should be closed")
                .isNotEqualTo(ATTRIBUTE_EDITOR_TITLE);

        //Assert that Delta English Label updated
        softAssertions.assertThat(editorTextPage().getElementsText(firstDeltaSpan))
                .as(format("[%s] should be appeared in the English Label", deltaExcludeNote))
                .isEqualTo(deltaExcludeNote);

        //Assert that Editor tree is updated
        editorPage().switchToEditor();
        softAssertions.assertThat(editorTreePage().doesElementExist(format(TREE_NODE_CONTAINS_TEXT, exclude)))
                .as(format("[%s] should appear in the tree", exclude))
                .isTrue();

        //Assert that there are no errors in message pane
        editorToolbarPage().clickValidate();
        softAssertions.assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in message pane")
                .isFalse();

        //Assert that there are no warnings in message pane
        softAssertions.assertThat(editorMessagePage().checkForWarningInMessagePane())
                .as("There should be no warnings in message pane")
                .isFalse();

        //Assert that Delta blocks have different ids
        editorPage().switchToEditorTextFrame();
        softAssertions.assertThat(editorTextPage().getElementsAttribute(format(DELTA_BY_NUMBER, 3), idAttribute))
                .as("Delta blocks should have different ids")
                .isNotEqualTo(editorTextPage().getElementsAttribute(format(DELTA_BY_NUMBER, 1), idAttribute));

        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteElementsWithF7SourceLegalTest()
    {
        int countOfSiblingsToHighlight = 1;
        int chunkNumber = 1;

        //Click on Text Paragraph, highlight following siblings and copy selected content
        String textParaXpath = format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 7);
        highlightTextParagraphsAndCopyThem(textParaXpath, countOfSiblingsToHighlight);

        assertThatSelectedContentIsCopied();
        assertThatCopiedContentIsStillSelectedAfterCopying(countOfSiblingsToHighlight);

        editorPage().scrollToView(format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 5));
        editorTextPage().rightClick((format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 5) + FOLLOWING_SIBLING_PARATEXT));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteCtrlV();

        assertThatWarningMessageIsAppearedInMessagePane();

        editorPage().scrollToChunk(FIRST_CHUNK);

        //Paste previously copied content
        textParaXpath = format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 1);
        int paragraphsBeforePasting = countTextParagraphsInLoadedChunkWithNumber(chunkNumber);
        pasteAndSwitchToEditorTextFrame(textParaXpath);
        int paragraphsAfterPasting = countTextParagraphsInLoadedChunkWithNumber(chunkNumber);

        assertThatPastedContentIsHighlighted(paragraphsBeforePasting, paragraphsAfterPasting, countOfSiblingsToHighlight);
    }

    /**
     * STORY/BUG - HALCYONST-5804 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Feature (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToFeatureSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        String copiedParagraphTextContent = getParagraphsContent(COPY_PARA_XPATH);
        editorTextPage().click(COPY_PARA_XPATH);
        copyTextParagraphAndScrollToChunk(COPY_PARA_XPATH, FIRST_CHUNK);

        editorPage().jumpToBeginningOfDocument();
        editorPage().scrollToView(PASTE_PARA_XPATH);
        pasteAsFeatureTextEditorialNote(PASTE_PARA_XPATH);

        softAssertThatTextEditorialNoteIsInsertedAsAChildTo(DELTA_FEATURE);
        softAssertThatMiscellaneousTextNoteHeadingIsInsertedAsAChildToTextEditorialNote(DELTA_FEATURE);
        softAssertThatItalicNoteIsInsertedAsAChildToTextEditorialNote(DELTA_FEATURE);
        softAssertThatItalicNoteHasTheSameTextContentAsCopiedParagraph(DELTA_FEATURE, copiedParagraphTextContent);

        softAssertions.assertAll();
    }

    /**
     * STORY/BUG - HALCYONST-5804 <br>
     * SUMMARY - Copy and Paste as Text Paragraph to Feature - multiple selection (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteAsTextParagraphToFeatureMultipleSelectionSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        editorPage().selectMultipleTextParagraphs(7, 8);
        List<String> copiedParagraphsTextContent = getSelectedParagraphsContentList();
        copyTextParagraphAndScrollToChunk(format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 7), FIRST_CHUNK);

        editorPage().jumpToBeginningOfDocument();
        editorPage().scrollToView(PASTE_PARA_XPATH);
        pasteAsFeatureTextEditorialNote(PASTE_PARA_XPATH);

        softAssertThatTextEditorialNoteIsInsertedAsAChildTo(DELTA_FEATURE);
        softAssertThatMiscellaneousTextNoteHeadingIsInsertedAsAChildToTextEditorialNote(DELTA_FEATURE);
        softAssertThatTheNumberOfItalicNotesIsEqualToTheNumberOfCopiedParagraphs(DELTA_FEATURE);
        softAssertThatItalicNotesHaveTheSameTextContentAsCopiedParagraphs(DELTA_FEATURE, copiedParagraphsTextContent);

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void openSourceRenditionInDsEditor(String uuid)
    {
        //Go to Source page and open source rendition in DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    private int countTextParagraphs()
    {
        return editorTextPage().countElements(format(PARA_XPATH, 0, 1));
    }
}
