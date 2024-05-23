package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ContentEditorialSystemPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertMiscellaneousTests extends TestService
{

    /**
     *  Insert Miscellaneous Citation Equivalent
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Miscellaneous -> Additional Misc Placeholders Popup
     * VERIFY: Content Editorial System window appears.  This will allow you to select a mnemonic to insert
     * 4. Select 'ce' from the mnemonic dropdown
     * VERIFY: The English Label field populates with Citation Equivalent
     * 5. Click Save
     * VERIFY: Citation Equivalent element is inserted
     * VERIFY: This has a mnemonic of CE
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default
     * VERIFY: This has a modified by tag
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertMiscellaneousSourceTest()
    {

        String uuid = "I23E849F066F611E2AD95FB167AA1A73D";
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());
        int chunkNumber = 1;

        String[] menuItems = new String[] {
                EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
                EditorTextContextMenuElements.MISCELLANEOUS,
                EditorTextContextMenuElements.ADDITIOANL_MISC_PARAGRAPHS_POPUP
        };

        EditorTreePageElements.treeElements treeEl = EditorTreePageElements.treeElements.PARA_CE;
        EditorTreePageElements.treeElements treeText = EditorTreePageElements.treeElements.PARATEXT_INSERT_TEXT;
        EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.CE;
        String blockDescription = "Citation Equivalent";
        String blockXpath = EditorTextPageElements.CITATION_EQUIVALENT;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        // Right click and select
        editorPage().switchToEditorTextFrame();
        editorTextPage().click(EditorTextPageElements.CORNERPIECE);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(menuItems);

        boolean windowAppeared = editorPage().switchToWindow(ContentEditorialSystemPageElements.PAGE_TITLE);
        contentEditorialSystemPage().waitForPageLoaded();
        contentEditorialSystemPage().enterTheInnerFrame();
        contentEditorialSystemPage().selectMnemonic( mnemonic.name().toLowerCase() );
        contentEditorialSystemPage().clickSave();
        editorPage().switchToEditor();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        //checks in text
        editorPage().switchToEditorTextFrame();
        boolean blockExists = editorTextPage().doesElementExist(blockXpath);
        boolean mnemonicExists = editorTextPage().doesElementExist(blockXpath + mnemonic.xpath());
        String modifiedByActual = editorTextPage().getElementsText(blockXpath + EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        boolean nopubExists = editorTextPage().doesElementExist(blockXpath +
                        EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(blockXpath +
                        EditorTextPageElements.SOURCE_TAG);

        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(windowAppeared, "Content Editorial System Window should appear"),
                        () -> Assertions.assertTrue(cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore,
                                treeEl.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore,
                                treeText.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists, blockDescription + " should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists, blockDescription + " should have a mnemonic of " + mnemonic.name()),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        () -> Assertions.assertFalse(nopubExists, blockDescription + " should have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(sourcetagExists, blockDescription + " should have a default source tag")
                );
    }

    /**
     * Insert Miscellaneous Analysis Section Level Generation
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Miscellaneous -> Signature Block (sl)
     *
     * VERIFY: Analysis Generation element is inserted
     * VERIFY: This has a mnemonic of ANGEN
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default
     * VERIFY: This has a modified by tag
     * VERIFY: This will contain 'SA' text
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertMiscellaneousSignatureBlockSLSourceTest()
    {
        String uuid = "I23E849F066F611E2AD95FB167AA1A73D";
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());
        int chunkNumber = 1;

        String[] menuItems = new String[] {
                EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
                EditorTextContextMenuElements.MISCELLANEOUS,
                EditorTextContextMenuElements.SIGNATURE_BLOCK_SL
        };

        EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.SL;
        EditorTreePageElements.treeElements treeEl = EditorTreePageElements.treeElements.SIGNATURE_BLOCK;
        EditorTreePageElements.treeElements treeText = EditorTreePageElements.treeElements.SIGNATURE_LINE;
        String blockDescription = "Signature Block ";
        String blockXpath = EditorTextPageElements.SIGNATURE_BLOCK;
        String elementDescription = "Signature Line ";
        String elementXpath = EditorTextPageElements.SIGNATURE_LINE;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        // Right click and select
        editorPage().switchToEditorTextFrame();
        int blockCountInTextBefore = editorTextPage().countElements(blockXpath);
        int elementCountInTextBefore = editorTextPage().countElements(elementXpath);
        editorTextPage().click(EditorTextPageElements.CORNERPIECE);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(menuItems);

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        //checks in text
        editorPage().switchToEditorTextFrame();
        int blockCountInTextAfter = editorTextPage().countElements(blockXpath);
        int elementCountInTextAfter = editorTextPage().countElements(elementXpath);

        boolean blockExists = editorTreePage().doesElementExist(blockXpath);
        boolean elementExists = editorTreePage().doesElementExist(elementXpath);
        boolean mnemonicExists = editorTreePage().doesElementExist(elementXpath + mnemonic.xpath());
        String modifiedByActual = editorTextPage().getElementsText(elementXpath + EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean nopubExists = editorTreePage().doesElementExist(elementXpath +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean sourcetagExists = editorTreePage().doesElementExist(elementXpath +
                EditorTextPageElements.SOURCE_TAG);
        boolean sectionSymbolExists = editorTreePage().doesElementExist(
                elementXpath +
                        "//*[contains(text(), 'add text; also add additional sl paras by selecting alt-i and changing the mnemonics, if needed')]"
        );

        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore,
                                treeEl.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore,
                                treeText.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && blockCountInTextAfter > blockCountInTextBefore, blockDescription + " should be inserted"),
                        () -> Assertions.assertTrue(elementExists && elementCountInTextAfter > elementCountInTextBefore, elementDescription + " should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists, elementDescription + " should have a mnemonic of " + mnemonic.name()),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        () -> Assertions.assertFalse(nopubExists, elementDescription + " should have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(sourcetagExists, elementDescription + " should have a default source tag"),
                        () -> Assertions.assertTrue(sectionSymbolExists,
                                "a section symbol should have a text 'add text; also add additional sl paras by selecting alt-i and changing the mnemonics, if needed'")
                );
    }

}
