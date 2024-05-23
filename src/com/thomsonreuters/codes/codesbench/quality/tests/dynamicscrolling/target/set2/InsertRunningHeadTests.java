package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertRunningHeadTests extends TestService
{

    public static Object[][] runningHead()
    {
        return new Object[][]
                {
                        {
                                "Legend Line as Sibling test",
                                EditorTextPageElements.CORNERPIECE_SPAN,
                                new String[] {
                                        EditorTextContextMenuElements.INSERT_TEXT_AS_SIBLING_SUBMENU,
                                        EditorTextContextMenuElements.RUNNING_HEAD_SIBLING_SUBMENU,
                                        EditorTextContextMenuElements.RUNNING_HEAD_LEGEND_LINE
                                },
                                EditorTextPageElements.LEGEND_LINE_EVEN,
                                EditorTextPageElements.LEGEND_LINE_ODD,
                                EditorTextPageElements.mnemonics.LEGL,
                                EditorTextPageElements.mnemonics.LEGR,
                                EditorTreePageElements.treeElements.LEGEND_EVEN,
                                EditorTreePageElements.treeElements.LEGEND_ODD
                        },
                        {
                                "Running Head as Child test",
                                EditorTextPageElements.SECTION_SPAN,
                                new String[] {
                                        EditorTextContextMenuElements.INSERT_TEXT_AS_CHILD_SUBMENU,
                                        EditorTextContextMenuElements.RUNNING_HEAD_CHILD_SUBMENU,
                                        EditorTextContextMenuElements.RUNNING_HEAD_RUNNING_HEAD
                                },
                                EditorTextPageElements.RUNNING_HEAD_EVEN,
                                EditorTextPageElements.RUNNING_HEAD_ODD,
                                EditorTextPageElements.mnemonics.RHL,
                                EditorTextPageElements.mnemonics.RHR,
                                EditorTreePageElements.treeElements.RUNHEAD_EVEN,
                                EditorTreePageElements.treeElements.RUNHEAD_ODD
                        }
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("runningHead")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertRunningHeadTargetTest
            (
                    String testName,
                    String targetElementLabel,
                    String[] contextMenuItems,
                    String evenNodeInText,
                    String oddNodeInText,
                    EditorTextPageElements.mnemonics evenNodeMnemonic,
                    EditorTextPageElements.mnemonics oddNodeMnemonic,
                    EditorTreePageElements.treeElements evenNodeInTree,
                    EditorTreePageElements.treeElements oddNodeInTree
            )
    {
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
        int targetChunkNumber = 1;
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        String blockXpath = EditorTextPageElements.RUNNING_HEAD_BLOCK;
        EditorTreePageElements.treeElements blockNodeInTree = EditorTreePageElements.treeElements.RUNHEAD_BLOCK;
        String blockDescription = "Running Head Block";

        // open DS editor
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        // grab default source tag
        String defaultSourceTag = editorPreferencesPage().grabDefaultSourceTag();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        // remember things for future checks
        int runningHeadCountInTreeBefore = editorTreePage().countElements(blockNodeInTree.getXpath());
        int runningHeadCountInTextBefore = editorTextPage().countElements(blockXpath);

        // insert running head
        editorTextPage().click(targetElementLabel);
        editorTextPage().rightClick(targetElementLabel);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(contextMenuItems);

        //checks in the tree
        boolean runningHeadCountInTreeIncreased = editorTreePage()
                .countElements(blockNodeInTree.getXpath()) - 1 == runningHeadCountInTreeBefore;

        boolean evenNodeInTreeExists = editorTreePage()
                .doesElementExist(evenNodeInTree.getXpath());

        boolean oddNodeInTreeExists = editorTreePage()
                .doesElementExist(oddNodeInTree.getXpath());

        //checks in text
        editorPage().switchToEditorTextFrame();

        boolean runningHeadCountInTextIncreased = editorTextPage()
                .countElements(blockXpath) - 1 == runningHeadCountInTextBefore;

        boolean evenNodeMnemonicIsCorrect = editorTextPage().doesElementExist(evenNodeInText + evenNodeMnemonic.xpath());

        boolean oddNodeMnemonicIsCorrect = editorTextPage().doesElementExist(oddNodeInText + oddNodeMnemonic.xpath());

        String modifiedByActualEvenNode = editorTextPage().getElementsText(evenNodeInText +
                        EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        String modifiedByActualOddNode = editorTextPage().getElementsText(oddNodeInText +
                        EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        boolean evenNodePubtagIsNopub = editorTextPage().doesElementExist(evenNodeInText
                        + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean oddNodePubtagIsNopub = editorTextPage().doesElementExist(oddNodeInText
                        + EditorTextPageElements.NOPUB_MNEMONIC);

        String evenNodeSourceTag = editorTextPage().getElementsText(evenNodeInText + EditorTextPageElements.SOURCE_TAG);

        String oddNodeSourceTag = editorTextPage().getElementsText(oddNodeInText + EditorTextPageElements.SOURCE_TAG);

        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        // asserts
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(runningHeadCountInTextIncreased, blockDescription + " should appear in the text"),
                        () -> Assertions.assertTrue(runningHeadCountInTreeIncreased, blockDescription + " should exist in the tree"),
                        () -> Assertions.assertTrue(evenNodeInTreeExists, blockDescription + " Even node should exist in the tree"),
                        () -> Assertions.assertTrue(oddNodeInTreeExists, blockDescription + " Odd node should exist in the tree"),
                        () -> Assertions.assertTrue(evenNodeMnemonicIsCorrect, "Even node should have correct mnemonic in the text"),
                        () -> Assertions.assertTrue(oddNodeMnemonicIsCorrect, "Odd node should have correct mnemonic in the text"),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActualEvenNode, String.format("A modified by tag for Even Node is '%s' instead of '%s'",modifiedByExpected,modifiedByActualEvenNode)),
                        () -> Assertions.assertEquals(modifiedByExpected, modifiedByActualOddNode, String.format("A modified by tag for Odd Node is '%s' instead of '%s'",modifiedByExpected,modifiedByActualOddNode)),
                        () -> Assertions.assertTrue(evenNodePubtagIsNopub, "Even node should have correct Pubtag in the text"),
                        () -> Assertions.assertTrue(oddNodePubtagIsNopub, "Odd node should have correct Pubtag in the text"),
                        () -> Assertions.assertEquals(defaultSourceTag, evenNodeSourceTag, String.format("Even node has '%s' SourceTag instead of '%s'", evenNodeSourceTag, defaultSourceTag)),
                        () -> Assertions.assertEquals(defaultSourceTag, oddNodeSourceTag, String.format("Odd node has '%s' SourceTag instead of '%s'", oddNodeSourceTag, defaultSourceTag))
                );

    }
}
