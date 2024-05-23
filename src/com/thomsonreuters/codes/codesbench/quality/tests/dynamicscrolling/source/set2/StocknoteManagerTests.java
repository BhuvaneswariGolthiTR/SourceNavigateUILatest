package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class StocknoteManagerTests  extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertStocknoteTest()
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        int chunkNumber = 1;
        String stocknoteName = EditorTextPageElements.mnemonics.CPR1.name().toLowerCase();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);
        editorTextPage().click(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();
        int cornerpiecesNumber = editorTextPage().countElements(EditorTextPageElements.CORNERPIECE);

        // add a stocknote
        editorTextPage().click(EditorTextPageElements.CORNERPIECE_FROM_SOURCE_FRONT);
        editorTextPage().breakOutOfFrame();
        editorToolbarPage().clickStocknoteManager();
        stocknoteManagerFiltersPage().setFilterStocknoteName(stocknoteName);
        stocknoteManagerPage().refresh();
        stocknoteManagerGridPage().rightClickFirstStocknote();
        stocknoteManagerContextMenu().selectToBeInserted();

        boolean windowClosed = stocknoteManagerPage().checkWindowIsClosed(StocknoteManagerPageElements.PAGE_TITLE);

        // verify text content
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();

        boolean cprInsertedAsSibling = editorTextPage()
                .doesElementExist(EditorTextPageElements.CORNERPIECE_FROM_SOURCE_FRONT + "/.."
                        + EditorTextPageElements.HIGHLIGHTED_CORNERPIECE_AS_FOLLOWING_SIBLING)
                && editorTextPage().countElements(EditorTextPageElements.CORNERPIECE) == cornerpiecesNumber+1;

        boolean mnemonicIsTheSame = editorTextPage()
                .doesElementExist(EditorTextPageElements.CORNERPIECE + EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX
                        + EditorTextPageElements.mnemonics.CPR1.xpath());

        String modifiedByActual = editorTextPage().getElementsText(
                EditorTextPageElements.CORNERPIECE + EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX
                        +EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        // verify tree node
        editorTextPage().breakOutOfFrame();
        boolean newTreeNodeAppearedAsHighlighted = editorTreePage()
                .doesElementExist(String.format(EditorTreePageElements.SELECTED_NODE_WITH_NAME,
                        EditorTreePageElements.treeElements.CORNERPIECE_CPR1.getNodeName()));

        editorPage().breakOutOfFrame();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        ()->Assertions.assertTrue(windowClosed, "Stocknote manager window should close after stosknote insertion"),
                        ()->Assertions.assertTrue(cprInsertedAsSibling, "CPR should be inserted as a following sibling of selected element"),
                        ()->Assertions.assertTrue(mnemonicIsTheSame, "Mnemonic of the freshly added Cornerpiece should be CPR1"),
                        ()->Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        ()->Assertions.assertTrue(newTreeNodeAppearedAsHighlighted, "New tree node should be displayed and highlighted")
                );

    }
}
