package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertSectionInstructionTests  extends TestService
{
    /**
     * Insert Source Section Note
     * 1. Open document
     * 2. Scroll to a Source Section element
     * 3. select and right click this source section element
     * 4. go to Insert Section Instruction -> Template Note
     * VERIFY: Section Instruction element was inserted
     * 5. Enter some text into this Section Instruction
     * VERIFY: Your text appears
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertSectionInstructionSourceTest()
    {
        String uuid = "I9E7B6241F51611E5AF0B8B9371769F64";
        String[] menuItems = new String[] {
                EditorTextContextMenuElements.INSERT_SECTION_INSTRUCTION,
                EditorTextContextMenuElements.TEMPLATE_NOTE
        };
        EditorTreePageElements.treeElements treeEl = EditorTreePageElements.treeElements.SOURCE_NOTE;
        EditorTreePageElements.treeElements treeText = EditorTreePageElements.treeElements.TEXT;
        int targetChunkNumber = 2;
        String mark = "Test" + new Date().getTime();
        String firstPhrase = SpecialCharacters.NBSP.getHtml();
        String secondPhrase = SpecialCharacters.NBSP.getCharacter() + " " + mark + " ";

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        // Right click and select
        editorTextPage().click( EditorTextPageElements.SOURCE_SECTION_SPAN );
        editorTextPage().rightClick( EditorTextPageElements.SOURCE_SECTION_SPAN );
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(menuItems);

        //checks in text
        editorTextPage().waitForPageLoaded();
        editorTextPage().switchToEditorTextArea();
        boolean sectionInstructionExists = editorTextPage().doesElementExist(EditorTextPageElements.SECTION_INSTRUCTION);
        editorTextPage().click(EditorTextPageElements.SECTION_INSTRUCTION);

        //checks in the tree
        editorTextPage().breakOutOfFrame();
        boolean treeElementExistsBeforeAdd = editorTreePage().doesElementExist(treeEl.getXpath());
        boolean treeTextElementExistsBeforeAdd = editorTreePage().doesElementExist(String.format(treeText.getXpath(), firstPhrase));

        //check adding text
        editorTextPage().switchToEditorTextArea();
        editorTextPage().insertPhraseInTheEndOfLine(mark, EditorTextPageElements.SECTION_INSTRUCTION);
        boolean sectionInstructionWithTextExists = editorTextPage().doesElementExist(String.format(EditorTextPageElements.SECTION_INSTRUCTION_WITH_TEXT, mark));

        //checks in the tree
        editorTextPage().click(EditorTextPageElements.SECTION_INSTRUCTION);
        editorTextPage().breakOutOfFrame();
        boolean treeElementExistsAfterAdd = editorTreePage().doesElementExist(treeEl.getXpath());
        boolean treeTextElementExistsAfterAdd = editorTreePage().doesElementExist(String.format(treeText.getXpath(), secondPhrase));

        editorPage().breakOutOfFrame();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        ()->Assertions.assertTrue(sectionInstructionExists, "Section Instruction should be inserted"),
                        ()->Assertions.assertTrue(treeElementExistsBeforeAdd, treeEl.getNodeName() + " should be inserted in the Tree (before add)"),
                        ()->Assertions.assertTrue(treeTextElementExistsBeforeAdd, String.format(treeText.getNodeName(),firstPhrase) + " should be inserted in the Tree (before add)"),
                        ()->Assertions.assertTrue(sectionInstructionWithTextExists, "Text should be inserted"),
                        ()->Assertions.assertTrue(treeElementExistsAfterAdd, treeEl.getNodeName() + " should be inserted in the Tree (after add)"),
                        ()->Assertions.assertTrue(treeTextElementExistsAfterAdd,  String.format(treeText.getNodeName(),secondPhrase) +" should be inserted in the Tree (after add)")
                );
    }
}
