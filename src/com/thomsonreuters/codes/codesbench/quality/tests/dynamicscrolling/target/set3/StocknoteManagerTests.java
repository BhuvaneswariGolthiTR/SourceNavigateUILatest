package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_CORNERPIECE_AS_FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.MD_MNEM_MNEMONIC;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.MODIFIED_BY_MNEMONIC;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.mnemonics.CPL1;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.mnemonics.CPR1;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.SELECTED_NODE_WITH_NAME;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.treeElements.CORNERPIECE_CPR1;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class StocknoteManagerTests extends TestService
{
    private static final String ASSERT_EQUALS_ASSERTION_MESSAGE = "Actual %s doesn't match expected";
    private static final String NOPUB_PUBTAG = "NOPUB";

    /*
     * Insert Stocknote Test
     * 1. Open document
     * 2. Select the Cornerpiece English label in the content area
     * 3. Click Stocknote Manager in the toolbar
     * Verify: Stocknote Manager window opens and loads
     * 5. Select and right click the Cornerpiece stocknote with name "cpr1"
     * - probs want to create a method that will let us select any stocknote we want based off the category and name
     * 6. Click Select to be Inserted in the context menu
     * Verify: The Cornerpiece stocknote is inserted as a sibling to the cornerpiece we selected
     * Verify: This appears in both the content and tree
     * Verify: The mnemonic is CPR1
     * Verify: The Pubtag is NOPUB
     * Verify: The source tag is the default source tag
     * Verify: There is a modified by tag with the appropriate information
     * Verify: The stocknote window no longer appears
     *
     * 7. Close and discard changes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertStocknoteLegalTargetTest()
    {
        String uuid = "I09CFC99014F011DA8AC5CD53670E6B4E";
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().scrollToChunk(1);

        int cornerpiecesNumber = editorTextPage().countElements(CORNERPIECE);
        editorTextPage().click(CORNERPIECE_SPAN);
        editorTextPage().waitForElement(HIGHLIGHTED_CORNERPIECE);
        editorTextPage().breakOutOfFrame();
        String defaultSourceTag = editorPreferencesPage().grabDefaultSourceTag();

        // add a stocknote
        editorToolbarPage().clickStocknoteManager();
        stocknoteManagerFiltersPage().setFilterStocknoteName(CPR1.name().toLowerCase());
        stocknoteManagerGridPage().rightClickFirstStocknote();
        stocknoteManagerContextMenu().selectToBeInserted();

        boolean windowClosed = stocknoteManagerPage().checkWindowIsClosed(StocknoteManagerPageElements.PAGE_TITLE);
        Assertions.assertTrue(windowClosed, "Stocknote manager window should close after stosknote insertion");

        // verify text content
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();

        boolean cprInsertedAsSibling = editorTextPage().doesElementExist(CORNERPIECE + HIGHLIGHTED_CORNERPIECE_AS_FOLLOWING_SIBLING)
                && editorTextPage().countElements(CORNERPIECE) == cornerpiecesNumber + 1;
        boolean mnemonicIsTheSame = editorTextPage().doesElementExist(CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX + CPR1.xpath());

        String modifiedBy = editorTextPage().getElementsText(CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX + MODIFIED_BY_MNEMONIC);
        String sourceTag = editorTextPage().getElementsText(CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX + SOURCE_TAG);
        String pubTagDisplayed = editorTextPage().getElementsText(CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX + ANY_PUBTAG_IN_METADATA_BLOCK);

        // verify tree node
        editorTextPage().breakOutOfFrame();
        boolean newTreeNodeAppearedAsHighlighted = editorTreePage().doesElementExist(
                String.format(SELECTED_NODE_WITH_NAME, CORNERPIECE_CPR1.getNodeName()));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(newTreeNodeAppearedAsHighlighted,
                                "New tree node should be displayed and highlighted"),
                        () -> Assertions.assertTrue(cprInsertedAsSibling,
                                "CPR should be inserted as a following sibling of selected element"),
                        () -> Assertions.assertTrue(mnemonicIsTheSame,
                                "Mnemonic of the freshly added Cornerpiece should be CPR1"),
                        () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(this.user()), modifiedBy,
                                "Modified By tag should contain right name and date"),
                        () -> Assertions.assertEquals(defaultSourceTag, sourceTag,
                                "New Cornerpiece should have a default source tag"),
                        () -> Assertions.assertEquals(NOPUB_PUBTAG, pubTagDisplayed,
                                "New Cornerpiece should have a NOPUB pub tag")
                );
    }

    /**
     * STORY/BUG - n/a <br>
     * SUMMARY - Stocknote Manager Additional Stocknote Data Inserted (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void additionalStocknoteDataInsertedTargetLegalTest()
    {
        String uuid = "I3833A96014EF11DA8AC5CD53670E6B4E";
        String cornerpieceSpanText = "Cornerpiece Non-Live Identifier";
        String cornerpiece = "cornerpiece";

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(CORNERPIECE_SPAN);
        editorTextPage().waitForElement(HIGHLIGHTED_CORNERPIECE);
        editorTextPage().breakOutOfFrame();
        String defaultSourceTag = editorPreferencesPage().grabDefaultSourceTag();

        editorToolbarPage().clickStocknoteManager();
        stocknoteManagerFiltersPage().setFilterStocknoteName(CPL1.name().toLowerCase());
        stocknoteManagerGridPage().rightClickFirstStocknote();
        stocknoteManagerContextMenu().selectToBeInserted();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().click(CORNERPIECE_SPAN);
        editorTextPage().rightClick(CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertTextSiblingCornerpieceCpl1();
        editorPage().switchDirectlyToTextFrame();

        boolean areCornerpieceBlocksInsertedAsSiblings = editorTextPage().countElements(String.format(
                CORNERPIECE + "[1]" + FOLLOWING_SIBLING, cornerpiece)) == 2;

        String cornerpieceXpath = String.format(SPAN_BY_TEXT + ANCESTOR, cornerpieceSpanText, cornerpiece);
        List<String> actualMnemonicList = editorTextPage().getElementsTextList(cornerpieceXpath + MD_MNEM_MNEMONIC);
        List<String> actualPubTagList = editorTextPage().getElementsTextList(cornerpieceXpath + ANY_PUBTAG_IN_METADATA_BLOCK);
        List<String> actualSourceTagList = editorTextPage().getElementsTextList(cornerpieceXpath + SOURCE_TAG);
        List<String> actualModifiedByMnemonicList = editorTextPage().getElementsTextList(cornerpieceXpath + MODIFIED_BY_MNEMONIC);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(areCornerpieceBlocksInsertedAsSiblings, "Cornerpiece blocks weren't inserted as siblings"),
                () -> actualMnemonicList.forEach(actualMnemonic -> Assertions.assertEquals(CPL1.name(), actualMnemonic,
                        String.format(ASSERT_EQUALS_ASSERTION_MESSAGE, "mnemonic"))),
                () -> actualPubTagList.forEach(actualPubTag -> Assertions.assertEquals(NOPUB_PUBTAG, actualPubTag,
                        String.format(ASSERT_EQUALS_ASSERTION_MESSAGE, "pub tag"))),
                () -> actualSourceTagList.forEach(actualSourceTag -> Assertions.assertEquals(defaultSourceTag, actualSourceTag,
                        String.format(ASSERT_EQUALS_ASSERTION_MESSAGE, "source tag"))),
                () -> actualModifiedByMnemonicList.forEach(actualModifiedByMnemonic -> Assertions.assertEquals(
                        editorTextPage().getModifiedByTag(user()), actualModifiedByMnemonic,
                        String.format(ASSERT_EQUALS_ASSERTION_MESSAGE, "modified by mnemonic")))
        );
    }
}
