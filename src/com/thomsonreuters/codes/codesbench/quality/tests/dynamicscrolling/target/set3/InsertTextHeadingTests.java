package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class InsertTextHeadingTests extends TestService
{
    /* Insert Text Heading GNH
     * 1. open document
     * 2. Select and right click the Section
     * 3. Go to Insert Text (child) -> Text Heading -> Jurisdictional Text Headings -> gnh
     *
     * VERIFY: Research Reference Subheading is inserted
     * VERIFY: This has a mnemonic of GNH
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default source tag
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
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default source tag
     * VERIFY: This has a modified by tag
     * This will probably contain heading text markup, so that should be verified as well
     * Verify: New tree node is highlighted
     */
    private static Stream<Arguments> provideDataForInsertTextHeadingTargetLegalTest()
    {
        return Stream.of(
                Arguments.of(
                        new String[]
                                {
                                        EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                                        EditorTextContextMenuElements.TEXT_HEADING,
                                        EditorTextContextMenuElements.JURISDICTIONAL_TEXT_HEADINGS,
                                        EditorTextContextMenuElements.GNH_RESEARCH_REFERENCE_SUBHEADING
                                },
                        "GNH",
                        EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING,
                        EditorTextPageElements.GNH_MNEMONIC
                ),
                Arguments.of(
                        new String[]
                                {
                                        EditorTextContextMenuElements.INSERT_TEXT_CHILD,
                                        EditorTextContextMenuElements.TEXT_HEADING,
                                        EditorTextContextMenuElements.TEXT_HEADING_NARROW_CENTERED_CLP
                                },
                        "CLP",
                        EditorTextPageElements.TEXT_HEADING_NARROW_CENTERED_CLP,
                        EditorTextPageElements.CLP_MNEMONIC
                )
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForInsertTextHeadingTargetLegalTest")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertTextHeadingTargetLegalTest(String[] contextMenu, String mnemonic, String mainAddedNodeXpath, String mnemonicXpath)
    {
        String uuid = "I09CFC99014F011DA8AC5CD53670E6B4E";
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        
        // add heading
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(contextMenu);
        editorPage().switchToEditor();

        // verify mnemonics and other information
        boolean selectedTreeNodeExists = editorTreePage()
                .doesElementExist(String.format(EditorTreePageElements.CODES_HEAD_NODE_SELECTED, mnemonic));

        editorTextPage().switchToEditorTextArea();

        boolean researchReferenceSubheadingExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath);

        boolean gnhMnemonicExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath + mnemonicXpath);

        boolean nopubMnemonicExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourceTagExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath + EditorTextPageElements.SOURCE_TAG);

        String modifiedByMnemonicExists = editorTextPage()
                .getElementsText(mainAddedNodeXpath + EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        boolean headtextTagExists = editorTextPage()
                .doesElementExist(mainAddedNodeXpath + EditorTextPageElements.HEADING_TEXT_LABEL);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(selectedTreeNodeExists,
                                "New tree node should be displayed and highlighted"),
                        () -> Assertions.assertTrue(researchReferenceSubheadingExists,
                                mnemonic + " node should be displayed in text"),
                        () -> Assertions.assertTrue(gnhMnemonicExists, mnemonic + " mnemonic should be displayed"),
                        () -> Assertions.assertTrue(nopubMnemonicExists, "NOPUB mnemonic should be displayed"),
                        () -> Assertions.assertTrue(sourceTagExists, "Source tag should be displayed"),
                        () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(this.user()),
                                modifiedByMnemonicExists, "Modidifed by should be displayed"),
                        () -> Assertions.assertTrue(headtextTagExists, "Headtext tag should be displayed")
                );
    }
}
