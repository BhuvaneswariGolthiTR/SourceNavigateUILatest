package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertRulesTests extends TestService
{
    private static Stream<Arguments> provideDataForInsertRNLTargetLegalTest()
    {
        return Stream.of(
                Arguments.of(new String[] {EditorTextContextMenuElements.STATE_RULES,
                        EditorTextContextMenuElements.STATE_RULES_HEADING_NAMELINE}, "State Rules"),
                Arguments.of(new String[] {EditorTextContextMenuElements.LOCAL_RULES,
                        EditorTextContextMenuElements.NAMELINE}, "Local Rules")
        );
    }
    /* Insert Local Rule Nameline RNL
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Local Rules -> Nameline -> RNL
     *
     * VERIFY: Rule Nameline element is inserted
     * VERIFY: This has a mnemonic of RNL
     * VERIFY: This has a source tag of the default
     * VERIFY: This has a modified by tag
     * VERIFY: This will probably contain keyword, value, and heading text markup.  The keyword markup should already contain Rule
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForInsertRNLTargetLegalTest")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertLocalRulesRNLTest(String[] menuItems, String displayName)
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        int targetChunkNumber = 1;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        //checks in text
        editorTextPage().breakOutOfFrame();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int rnlCountInTreeBefore = editorTreePage()
                .countElements(EditorTreePageElements.codesHead.RNL.getXpath());
        int rnlHeadInfoCountInTreeBefore = editorTreePage()
                .countElements(EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        //checks in text
        editorTextPage().switchToEditorTextArea();
        int rnlCountInTextBefore = editorTextPage().countElements(EditorTextPageElements.RULE_NAMELINE);

        // insert RNL
        editorTextPage().click(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
                menuItems[0],
                menuItems[1],
                EditorTextContextMenuElements.RNL
        );

        //checks in the tree
        editorTextPage().breakOutOfFrame();
        int rnlCountInTreeAfter = editorTreePage().countElements(EditorTreePageElements.codesHead.RNL.getXpath());
        int rnlHeadInfoCountInTreeAfter = editorTreePage().countElements(
                EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int rnlCountInTextAfter = editorTextPage().countElements(EditorTextPageElements.RULE_NAMELINE);

        boolean blockExists = editorTextPage().doesElementExist(EditorTextPageElements.RULE_NAMELINE);

        boolean mnemonicIsRNL = editorTextPage().doesElementExist(
                EditorTextPageElements.RULE_NAMELINE + EditorTextPageElements.mnemonics.RNL.xpath());

        String modifiedBy = editorTextPage().getElementsText(
                EditorTextPageElements.RULE_NAMELINE + EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        String regex = ".*<IMG.*label_name_right_arrow.png.*" + "Rule" + ".*<IMG.*label_name_left_arrow.png.*"
                + "<tw:label.designator[^<>]+>.*</tw.label.designator>.*"
                + "<tw:headtext[^<>]+>.*>.*</tw:headtext>.*";
        boolean lineExists = RegexUtils.matchesRegex(editorTextPage().getElementsInnerHTML(
                EditorTextPageElements.RULE_NAMELINE), regex);

        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(rnlCountInTreeAfter > rnlCountInTreeBefore,
                                "RNL should be inserted in the Tree"),
                        () -> Assertions.assertTrue(rnlHeadInfoCountInTreeAfter > rnlHeadInfoCountInTreeBefore,
                                "head.info should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && rnlCountInTextAfter > rnlCountInTextBefore,
                                "Rule Nameline should be inserted"),
                        () -> Assertions.assertTrue(mnemonicIsRNL,
                                "Rule Nameline should have a mnemonic of RNL"),
                        () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(this.user()), modifiedBy,
                                "Rule Nameline should have a modified by tag"),
                        () -> Assertions.assertTrue(lineExists, "Block's inner HTML matches the regex")
                );
    }
}
