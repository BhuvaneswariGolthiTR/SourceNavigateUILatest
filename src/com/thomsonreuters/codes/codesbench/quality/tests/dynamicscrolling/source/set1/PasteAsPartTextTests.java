package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class PasteAsPartTextTests extends TestService
{
    /*
    1. Paste a marker to the text paragraph
    2. Highlight and copy it
    3. Right click Part Cornerpiece and select Paste As - Part Text - Nameline - snl
    4.  VERIFY: Part Section Name Line should be added
    5.  VERIFY: The inserted text should be equal to the copied
    6.  VERIFY: There are no Error messages on Message Pane
     */

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pasteSNLAsPartText()
    {
        String uuid = "I947B7240E0A311E9A8ADCDFA631F881A";
        //String uuid = "IFC2120F2752811E6BBAF9A886F6D9C70"; // for dev

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();

        int targetChunkNumber = 3;
        editorPage().scrollToChunk(targetChunkNumber);
        editorPage().switchDirectlyToTextFrame();

        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseInFirstTextParagraphOfChunkAndHighlight(phrase, targetChunkNumber);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        editorTextPage().copyContent();

        String partCornerpieceSpan = String.format(EditorTextPageElements.LOADED_CHUNK, targetChunkNumber - 1)
                + EditorTextPageElements.PART_CORNERPIECE + EditorTextPageElements.SPAN;
        String partSectionNameline = String.format(EditorTextPageElements.LOADED_CHUNK, targetChunkNumber - 1)
                + EditorTextPageElements.PART_SECTION_NAMELINE;
        String partSectionNamelineHeadtext = partSectionNameline + EditorTextPageElements.HEADTEXT;

        editorTextPage().click(partCornerpieceSpan);
        editorTextPage().rightClick(partCornerpieceSpan);
        editorTextPage().breakOutOfFrame();
        /*
        Current test method (uuid I947B7240E0A311E9A8ADCDFA631F881A) sometimes fails with
        EditorTextContextMenuElements.PASTE_AS locator - in this case helps "//li/a[@href='#prepPartPasteAsSubMenu']" locator
         */
        editorTextPage().openContextMenu(
                "//li/a[@href='#prepPartPasteAsSubMenu']",
                EditorTextContextMenuElements.PART_TEXT,
                EditorTextContextMenuElements.NAMELINE,
                EditorTextContextMenuElements.SNL);

        editorPage().switchToEditorTextFrame();
        boolean partSNLExist = editorPage().doesElementExist(partSectionNameline);

        String insertedData = editorTextPage().getElementsText(partSectionNamelineHeadtext);

        editorTextPage().breakOutOfFrame();
        boolean errorMessageShowedUp = editorMessagePage().checkMessagePaneForText("error");

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(partSNLExist, "Part Section Name Line should be added"),
                () -> Assertions.assertEquals(phrase, insertedData, "The inserted text should be equal to the copied"),
                () -> Assertions.assertFalse(errorMessageShowedUp, "There are Error messages on Message Pane")
        );
    }
}
