package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertTextHeadingTests extends TestService
{
    /* Insert Text Heading GNH
     * 1. open document
     * 2. Select and right click the Section
     * 3. Go to Insert Text (child) -> Text Heading -> Jurisdictional Text Headings -> gnh
     *
     * VERIFY: Research Reference Subheading is inserted
     * VERIFY: This has a mnemonic of GNH
     * VERIFY: This has no pubtag of NOPUB
     * VERIFY: This has no source tag of the default source tag
     * VERIFY: This has a modified by tag
     * This will probably contain heading text markup, so that should be verified as well
     * Verify: New tree node is highlighted
     */

    /* Insert Text Heading CLP
     * 1. open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Text Heading -> Text Heading Narrow Centered (clp)
     *
     * VERIFY: Text Narrow Centered is inserted
     * VERIFY: This has a mnemonic of CLP
     * VERIFY: This has no pubtag of NOPUB
     * VERIFY: This has no source tag of the default source tag
     * VERIFY: This has a modified by tag
     * This will probably contain heading text markup, so that should be verified as well
     * Verify: New tree node is highlighted
     */

    public static Object[][] textHeading()
    {
        return new Object[][]
                {
                        {new String[]
                                {
                                        EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                                        EditorTextContextMenuElements.TEXT_HEADING,
                                        EditorTextContextMenuElements.JURISDICTIONAL_TEXT_HEADINGS,
                                        EditorTextContextMenuElements.GNH_RESEARCH_REFERENCE_SUBHEADING
                                }, "GNH", EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING, EditorTextPageElements.GNH_MNEMONIC},
                        {new String[]
                                {
                                        EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                                        EditorTextContextMenuElements.TEXT_HEADING,
                                        EditorTextContextMenuElements.TEXT_HEADING_NARROW_CENTERED_CLP
                                }, "CLP", EditorTextPageElements.TEXT_HEADING_NARROW_CENTERED_CLP, EditorTextPageElements.CLP_MNEMONIC}
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("textHeading")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertTextHeadingTest(String[] contextMenu, String mnemonic, String mainAddedNodeXpath, String mnemonicXpath)
    {
        String uuid = "I56B71CE0BB2311E7AE1DDC37F9621E15";
        int targetChunkNumber = 2;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        // add heading
        editorTextPage().scrollToView(EditorTextPageElements.SECTION_SOURCE_SECTION);
        editorTextPage().rightClick(EditorTextPageElements.SECTION_SOURCE_SECTION);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(contextMenu);

        // check tree
        editorTextPage().breakOutOfFrame();
        boolean selectedTreeNodeExists = editorTreePage()
                .doesElementExist(String.format(EditorTreePageElements.CODES_HEAD_NODE_SELECTED, mnemonic));

        // verify mnemonics and other information
        editorTextPage().switchToEditorTextArea();
        boolean researchReferenceSubheadingExists = editorTextPage().doesElementExist(mainAddedNodeXpath);

        boolean gnhMnemonicExists = editorTextPage().doesElementExist(mainAddedNodeXpath + mnemonicXpath);

        boolean noPubMnemonicExists = editorTextPage().doesElementExist(mainAddedNodeXpath + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourceTagExists = editorTextPage().doesElementExist(mainAddedNodeXpath + EditorTextPageElements.SOURCE_TAG);

        String modifiedByActual = editorTextPage().getElement(
                mainAddedNodeXpath + EditorTextPageElements.MODIFIED_BY_MNEMONIC).getText();

        boolean headtextTagExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath + EditorTextPageElements.HEADING_TEXT_LABEL);


        // close editor
        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(selectedTreeNodeExists, "New tree node should be displayed and highlighted"),
                        () -> Assertions.assertTrue(researchReferenceSubheadingExists, mnemonic + " node should be displayed in text"),
                        () -> Assertions.assertTrue(gnhMnemonicExists, mnemonic + " mnemonic should be displayed"),
                        () -> Assertions.assertFalse(noPubMnemonicExists, "NOPUB mnemonic should not be displayed"),
                        () -> Assertions.assertFalse(sourceTagExists, "Source tag should not be displayed"),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        () -> Assertions.assertTrue(headtextTagExists, "Headtext tag should be displayed")
                );
    }
}
