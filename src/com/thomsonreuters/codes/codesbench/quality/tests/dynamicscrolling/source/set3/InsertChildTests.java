package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.EditorPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertChildTests extends TestService
{
    /*Insert as child italic note wisc
     * 1. Open document
     * 2. Scroll to chunk 2 or 3 (or maybe it will be best to scroll to the Text element)
     * 3. Select and right click a Source Section english label
     * 4. Go to Insert Text (child) -> Miscellaneous -> Italic Note (wisc)
     * VERIFY: Italic Note element is inserted as a child to the Source Section english label in both the editor content area and tree
     * VERIFY: The Italic Note has a mnemonic of WISC
     * VERIFY: The Italic Note doesn't have a pubtag
     * VERIFY: The Italic Note doesn't have a source tag
     * VERIFY: The Italic Note has a modified by tag with your name and date
     *
     * 5. When we insert text into this note
     * VERIFY: This text have italic formatting
     */

    /*Insert as child italic note subsection deferred
     * 1. Open document
     * 2. Scroll to chunk 2 or 3 (or maybe it will be best to scroll to the Text element)
     * 3. Select and right click a Source Section english label
     * 4. Go to Insert Text (child) -> Miscellaneous -> Note Paragraph (gnp)
     * VERIFY: Note Paragraph element is inserted as a child to the Source Section english label in both the editor content area and tree
     * VERIFY: The Note Paragraph has a mnemonic of GNP
     * VERIFY: The Note Paragraph doesn't have a pubtag
     * VERIFY: The Note Paragraph doesn't have a source tag
     * VERIFY: The Note Paragraph has a modified by tag with your name and date
     */

    String uuid = "I065A5EB162B111E99FF7D4DA2B993A38";
    //String uuid = "IF9AB57F034F411E7ACDACFC8F254A8C8"; // for dev

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertChildItalicNoteSourceTest()
    {
        int targetChunkNumber = 2;
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        EditorTreePageElements.treeElements treeEl = EditorTreePageElements.treeElements.PARA_WISC;
        EditorTreePageElements.treeElements treeText = EditorTreePageElements.treeElements.PARATEXT_INSERT_TEXT;
        String elementDescription = "Italic Note";
        String elementXpath = EditorTextPageElements.ITALIC_NOTE;
        EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.WISC;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().waitForPageLoaded();
        // scroll
        editorPage().scrollToChunk(targetChunkNumber);
        editorPage().waitForPageLoaded();
        int elementsCountInTreeBefore = editorTreePage().countTreeElements(treeEl);
        int elementsTextCountInTreeBefore = editorTreePage().countTreeElements(treeText);
        editorPage().scrollToElement(EditorTextPageElements.SOURCE_SECTION);
        // Right click and select
        editorPage().jumpToBeginningOfDocument();
        editorPage().click(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorPage().breakOutOfFrame();
        editorPage().openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                EditorTextContextMenuElements.MISCELLANEOUS, EditorTextContextMenuElements.ITALIC_NOTE_WISC);
        //checks in the tree
        int elementsCountInTreeAfter = editorPage().countElements(treeEl.getXpath());
        int elementsTextCountInTreeAfter = editorPage().countElements(treeText.getXpath());
        String section = EditorTextPageElements.SOURCE_SECTION;
        editorPage().switchDirectlyToTextFrame();
        boolean elementExists = editorPage().doesElementExist(section + elementXpath);
        boolean mnemonicExists = editorPage().doesElementExist(section + elementXpath + mnemonic.xpath());
        boolean modifiedExists = editorPage().doesElementExist(
                section + elementXpath + modifiedBy);
        boolean nopubExists = editorPage().doesElementExist(
                section + elementXpath +
                        EditorTextPageElements.NOPUB_MNEMONIC);
        boolean sourcetagExists = editorPage().doesElementExist(
                section + elementXpath +
                        EditorTextPageElements.SOURCE_TAG);
        editorPage().switchDirectlyToTextFrame();
        boolean italicNoteParatextAppeared = editorPage().doesElementExist(EditorTextPageElements.ITALIC_NOTE +
                EditorTextPageElements.PARATEXT_ITALIC);
        // close
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(elementsCountInTreeAfter > elementsCountInTreeBefore,
                                treeEl.getNodeName() + String.format(" should be inserted in the Tree. " +
                                        "Count Before %d, Count After %d", elementsCountInTreeAfter, elementsCountInTreeBefore)),
                        () -> Assertions.assertTrue(elementsTextCountInTreeAfter > elementsTextCountInTreeBefore,
                                treeText.getNodeName() + String.format(" should be inserted in the Tree. " +
                                        "Count Before %d, Count After %d", elementsTextCountInTreeAfter, elementsTextCountInTreeBefore)),
                        () -> Assertions.assertTrue(elementExists, elementDescription + " should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists, elementDescription + " should have a mnemonic of " + mnemonic.name()),
                        () -> Assertions.assertTrue(modifiedExists, elementDescription + " should have a modified by tag"),
                        () -> Assertions.assertFalse(nopubExists, elementDescription + " should have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(sourcetagExists, elementDescription + " should have a default source tag"),
                        () -> Assertions.assertTrue(italicNoteParatextAppeared, "Italic Note Paratext didn't appear")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertChildNoteParagraphGNPSourceTest()
    {
        int targetChunkNumber = 2;
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        EditorTreePageElements.treeElements treeEl = EditorTreePageElements.treeElements.PARA_GNP;
        EditorTreePageElements.treeElements treeText = EditorTreePageElements.treeElements.PARATEXT_INSERT_TEXT;
        String elementDescription = "Note Paragraph";
        String elementXpath = EditorTextPageElements.NOTE_PARAGRAPH;
        EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.GNP;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        // scroll
        editorPage().scrollToChunk(targetChunkNumber);
        int elementsCountInTreeBefore = editorTreePage().countTreeElements(treeEl);
        int elementsTextCountInTreeBefore = editorTreePage().countTreeElements(treeText);
        editorPage().scrollToElement(EditorTextPageElements.SOURCE_SECTION);
        // Right click and select
      editorTextPage().waitForElement(EditorTextPageElements.SOURCE_SECTION_SPAN);
      editorPage().jumpToBeginningOfDocument();
       editorPage().click(EditorTextPageElements.SOURCE_SECTION);
       editorPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
       editorPage().breakOutOfFrame();
        editorPage().openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                EditorTextContextMenuElements.MISCELLANEOUS, EditorTextContextMenuElements.NOTE_PARAGRAPH_GNP);
        //checks in the tree
        int elementsCountInTreeAfter = editorPage().countElements(treeEl.getXpath());
        int elementsTextCountInTreeAfter = editorPage().countElements(treeText.getXpath());
        String section = EditorTextPageElements.SOURCE_SECTION;
        editorPage().switchDirectlyToTextFrame();
        boolean elementExists = editorPage().doesElementExist(section + elementXpath);
        boolean mnemonicExists = editorPage().doesElementExist(section + elementXpath + mnemonic.xpath());
        boolean modifiedExists = editorPage().doesElementExist(
                section + elementXpath + modifiedBy);
        boolean nopubExists = editorPage().doesElementExist(
                section + elementXpath +
                        EditorTextPageElements.NOPUB_MNEMONIC);
        boolean sourcetagExists = editorPage().doesElementExist(
                section + elementXpath +
                        EditorTextPageElements.SOURCE_TAG);
        editorPage().switchDirectlyToTextFrame();
        // close
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(elementsCountInTreeAfter > elementsCountInTreeBefore,
                                treeEl.getNodeName() + String.format(" should be inserted in the Tree. " +
                                        "Count Before %d, Count After %d", elementsCountInTreeAfter, elementsCountInTreeBefore)),
                        () -> Assertions.assertTrue(elementsTextCountInTreeAfter > elementsTextCountInTreeBefore,
                                treeText.getNodeName() + String.format(" should be inserted in the Tree. " +
                                        "Count Before %d, Count After %d", elementsTextCountInTreeAfter, elementsTextCountInTreeBefore)),
                        () -> Assertions.assertTrue(elementExists, elementDescription + " should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists, elementDescription + " should have a mnemonic of " + mnemonic.name()),
                        () -> Assertions.assertTrue(modifiedExists, elementDescription + " should have a modified by tag"),
                        () -> Assertions.assertFalse(nopubExists, elementDescription + " should have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(sourcetagExists, elementDescription + " should have a default source tag")
                );
    }
}
