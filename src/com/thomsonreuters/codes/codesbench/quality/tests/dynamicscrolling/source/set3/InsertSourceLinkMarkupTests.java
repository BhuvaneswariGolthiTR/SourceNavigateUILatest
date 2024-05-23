package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSourceCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import static java.lang.String.format;

public class InsertSourceLinkMarkupTests extends TestService
{
    /*Insert source link
     * Open the document
     * Scroll to chunk 2 or 3
     * Insert a simple word and highlight
     * Right click and select Markup -> Insert Source Link Markup
     * Click Locate Source
     * Select one of the renditions from the grid and right click and select source
     * - Pull the data from whatever rendition you plan to select to verify the data in the UI gets populated
     * Verify: The data in the UI is populated with the data from the rendition selected
     * Click Save
     * Verify: cite reference markup surrounds the text you had highlighted
     * Verify: cite reference markup has manual edit = yes
     * Verify: cite reference markup has westlaw display = go
     * Verify: cite reference markup has a non empty doc family uuid
     *
     * Please see existing tests to cross reference verifications
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertSourceLinkMarkupSourceRiskTest()
    {
        String uuid = "I5ACFF5E23C9911E6AE74A75C58FEB5C9";
        int chunkNumber = 2;
        String phrase = "TEST";

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        // add a phrase and mark it up
        editorTextPage().click(format(TEXT_PARAGRAPH_FROM_CHUNK, 1, 1)+SPAN);
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.CONTEXT_MENU, format(TEXT_PARAGRAPH_FROM_CHUNK, 1, 1)+SPAN);
        editorTextPage().waitForElementExists(format(TEXT_PARAGRAPH_FROM_CHUNK, 1, 1)+format(FOLLOWING_SIBLING, "para[1]"));
        editorTextPage().click(EditorTextPageElements.HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
        editorTextPage().sendKeys(phrase);
        editorTextPage().highlightHelper(phrase);
        editorTextPage().rightClick(String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN, phrase));
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.MARKUP,
                EditorTextContextMenuElements.INSERT_SOURCE_LINK_MARKUP);

        //locate source for source link
        editorTextPage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();
        insertSourceCiteReferencePage().clickLocateSource();
        pendingRenditionNavigatePage().rightClick(PendingRenditionNavigatePageElements.RENDITIONS);
        pendingRenditionNavigatePage().breakOutOfFrame();
        pendingRenditionNavigatePage().openContextMenu(PendingRenditionNavigatePageElements.SELECT_SOURCE_FOR_LINK);
        pendingRenditionNavigatePage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();
        insertSourceCiteReferencePage().clickSave();

        // verify markup
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();
        boolean markupAdded = editorTextPage().doesElementExist(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT
                + String.format(EditorTextPageElements.SOURCE_LINK_TEXT, phrase));

        // close editor
        editorPage().closeDocumentAndDiscardChanges();

         Assertions.assertTrue(markupAdded, "Added phrase should get wrapped with Source Link markup tag");

    }
}
