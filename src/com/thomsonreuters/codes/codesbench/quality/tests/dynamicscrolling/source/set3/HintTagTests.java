package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractHintTagTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HEADTEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HINT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PART_CORNERPIECE_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PART_SECTION_NAMELINE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class HintTagTests extends AbstractHintTagTests
{
    private static final String UUID = "I947B7240E0A311E9A8ADCDFA631F881A";

    @BeforeEach
    public void openSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    /*
    1. Paste a marker to the text paragraph
    2. Highlight and copy it
    3. Right click Part Cornerpiece and select Insert Text As Part - Nameline - snl
    4.  VERIFY: Part Section Name Line Hint should be added
    5. Insert copied text
    6.  VERIFY: The inserted text should be equal to the copied
    7.  VERIFY: There are no Error messages on Message Pane
     */

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void hintTagsTest()
    {
        int targetChunkNumber = 3;
        String loadedChunk = format(LOADED_CHUNK, targetChunkNumber - 1);
        String text = format(TEXT, DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String partSectionNameline = loadedChunk + PART_SECTION_NAMELINE;
        String partSectionNamelineHint = partSectionNameline + HINT;

        //Insert marker to the text paragraph, highlight and copy it
        editorPage().scrollToChunk(targetChunkNumber);
        editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(text, targetChunkNumber);
        editorTextPage().copyContent();

        //Insert Part Section Nameline
        openContextMenuOnElement(loadedChunk + PART_CORNERPIECE_SPAN);
        editorTextContextMenu().insertTextAsPartNamelineSnl();
        editorPage().switchDirectlyToTextFrame();

        //Assert that Part Section Nameline hint is inserted
        assertThat(editorPage().doesElementExist(partSectionNamelineHint))
                .as("Part Section Nameline hint should be inserted")
                .isTrue();

        //Insert copied text to hint
        editorTextPage().insertCopiedText(partSectionNamelineHint);

        //Assert that inserted text is equal to the copied
        assertThat(editorTextPage().getElementsText(partSectionNameline + HEADTEXT))
                .as("The inserted text should be equal to the copied")
                .isEqualTo(text);

        //Assert that there are no errors in the editor message pane
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();

        editorPage().closeDocumentAndDiscardChanges();
    }

    /**
     * STORY/BUG - HALCYONST-13708 <br>
     * SUMMARY - Information placed in Hint tags save during check-in (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void informationPlacedInHintTagsSaveDuringCheckInSourceLegalTest()
    {
        String text = format(TEXT, System.currentTimeMillis());

        //Insert Part Section Nameline
        editorPage().switchDirectlyToTextFrame();
        openContextMenuOnElement(PART_CORNERPIECE_SPAN);
        editorTextContextMenu().insertTextAsPartNamelineSnl();
        editorPage().switchDirectlyToTextFrame();

        insertTextToElementHint(text, PART_SECTION_NAMELINE);

        //Check-in source rendition and reopen it
        editorPage().closeAndCheckInChanges();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        //Assert that information placed in hint tags is saved after check-in
        assertThatInformationPlacedInHintTagsIsSaved(PART_SECTION_NAMELINE, text);

        //Delete Part Section Nameline and check-in changes
        deleteElement(PART_SECTION_NAMELINE);
        editorPage().closeAndCheckInChanges();
    }
}