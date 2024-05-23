package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSourceCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    public void insertSourceLinkMarkupTargetRiskTest()
    {
        String uuid = "I0289882129A111E6BDEEB8CA3AB007EB";
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        int chunkNumber = 2;

        editorPage().scrollToChunk(chunkNumber);

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                chunkNumber - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                chunkNumber - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorPage().click(firstParaSpan);
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.CONTEXT_MENU, firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        // add a phrase and mark it up
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);
        editorTextPage().highlightHelper(phrase);
        editorTextPage().rightClick(
                String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN, phrase));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.MARKUP,
                EditorTextContextMenuElements.INSERT_SOURCE_LINK_MARKUP);

        editorTextPage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();
        insertSourceCiteReferencePage().clickLocateSource();
        pendingRenditionNavigatePage().rightClick(PendingRenditionNavigatePageElements.RENDITIONS);
        pendingRenditionNavigatePage().click(PendingRenditionNavigatePageElements.SELECT_SOURCE_FOR_LINK);
        pendingRenditionNavigatePage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();
        insertSourceCiteReferencePage().clickSave();

        // verify markup
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();
        boolean markupAdded = editorTextPage().doesElementExist(secondPara + EditorTextPageElements.PARATEXT
                + String.format(EditorTextPageElements.SOURCE_LINK_TEXT, phrase));

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(markupAdded,
                                "Added phrase should get wrapped with Source Link markup tag")
                );
    }
}
