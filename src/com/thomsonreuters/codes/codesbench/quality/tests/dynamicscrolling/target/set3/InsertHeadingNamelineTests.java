package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InsertHeadingNamelineTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * Insert Heading Nameline SRNL
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Heading Nameline -> Additional Namelines Popup
     * VERIFY: Select Nameline window appears (title might actually be Content Editorial System)
     * 4. Select srnl from the mnemonic dropdown
     * VERIFY: the english label field populates with Non-Live Section Nameline
     * 5. Click Save
     * VERIFY: Non-Live Section Nameline is inserted below the cornerpiece
     * VERIFY: This has a mnemonic of SRNL
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default specified in the editor content preferences
     * VERIFY: This has a modified by tag
     * VERIFY: This contains heading text markup with a hint telling the user to insert text
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertHeadingNamelineSRNLTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //checks in the tree
        int codesHeadSRNLInTreeBefore = editorTreePage().countElements(EditorTreePageElements.codesHead.SRNL.getXpath());
        int headInfoInTreeBefore = editorTreePage().countElements(
                EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        editorTextPage().switchToEditorTextArea();
        int nonLineSectionInTextBefore = editorTextPage().countElements(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT);

        editorTextPage().click(EditorTextPageElements.CORNERPIECE);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        boolean windowAppeared = editorTextContextMenu().insertTextSiblingHeadingNamelineAdditionalNamelinesPopup();
        contentEditorialSystemPage().selectMnemonic(EditorTreePageElements.codesHead.SRNL.getMnemonic().toLowerCase());
        boolean englishLabelForSRNL = contentEditorialSystemPage().englishLabelTextEquals("Non-Live Section Nameline");
        contentEditorialSystemPage().clickSave();

        editorTextPage().breakOutOfFrame();
        //checks in the tree
        int codesHeadSRNLInTreeAfter = editorTreePage().countElements(EditorTreePageElements.codesHead.SRNL.getXpath());
        int headInfoInTreeAfter = editorTreePage().countElements(
                EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        editorTextPage().switchToEditorTextArea();

        int nonLineSectionInTextAfter = editorTextPage().countElements(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT);

        boolean blockExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT);

        boolean mnemonicExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT
                        + EditorTextPageElements.mnemonics.SRNL.xpath());

        String modifiedTag = editorTextPage().getElementsText(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT
                        + EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        boolean nopubExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.SOURCE_TAG);

        boolean headTextExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.HEADING_TEXT_LABEL);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(windowAppeared,
                      "Content Editorial System Window should appear"),
              () -> Assertions.assertTrue(englishLabelForSRNL,
                      "English label should be populated with correct text for srnl"),
              () -> Assertions.assertEquals(codesHeadSRNLInTreeAfter, codesHeadSRNLInTreeBefore + 1,
                      "SRNL should be inserted in the Tree"),
              () -> Assertions.assertEquals(headInfoInTreeAfter, headInfoInTreeBefore + 1,
                      "head.info should be inserted in the Tree"),
              () -> Assertions.assertTrue(
                      blockExists && nonLineSectionInTextAfter == nonLineSectionInTextBefore + 1,
                      "Non-Live Section Nameline should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists,
                      "Non-Live Section Nameline should have a mnemonic of SRNL"),
              () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(this.user()), modifiedTag,
                      "Non-Live Section Nameline should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists,
                      "Non-Live Section Nameline should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists,
                      "Non-Live Section Nameline should have a default source tag"),
              () -> Assertions.assertTrue(headTextExists,
                      "Non-Live Section Nameline should have a heading text")
        );
    }

    /**
     * Insert Heading Nameline Jurisdictional RRNL
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Heading Nameline -> Jurisdictional Namelines -> rrnl
     * VERIFY: Non-Live Rule Nameline is inserted below the cornerpiece
     * VERIFY: This has a mnemonic of RRNL
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default specified in the editor content preferences
     * VERIFY: This has a modified by tag
     * VERIFY: This contains <keyword>Rule<keyword> <value> <value> <heading text>Repealed by court order date<heading text>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertHeadingNamelineRRNLTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //checks in the tree
        int codesHeadRRNLInTreeBefore = editorTreePage().countElements(EditorTreePageElements.codesHead.RRNL.getXpath());
        int headInfoInTreeBefore = editorTreePage().countElements(
                EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        editorTextPage().switchToEditorTextArea();

        int nonLineSectionInTextBefore = editorTreePage().countElements(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT);

        // Right click and select
        editorTreePage().click(EditorTextPageElements.CORNERPIECE);
        editorTreePage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTreePage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.INSERT_TEXT_SIBLING,
                EditorTextContextMenuElements.HEADING_NAMELINE,
                EditorTextContextMenuElements.JURISDICTIONAL_NAMELINE,
                EditorTextContextMenuElements.RRNL);

        //checks in the tree
        int codesHeadRRNLInTreeAfter = editorTreePage().countElements(EditorTreePageElements.codesHead.RRNL.getXpath());
        int headInfoInTreeAfter = editorTreePage().countElements(
                EditorTreePageElements.treeElements.HEAD_INFO.getXpath());

        editorTextPage().switchToEditorTextArea();

        int nonLineSectionInTextAfter = editorTextPage().countElements(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT);

        boolean blockExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT);

        boolean mnemonicExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.mnemonics.RRNL.xpath());

        String modifiedTag = editorTextPage().getElementsText(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT
                        + EditorTextPageElements.MODIFIED_BY_MNEMONIC);

        boolean nopubExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                        EditorTextPageElements.SOURCE_TAG);

        String phrase = "Rule";
        String regex = ".*<IMG.*label_name_right_arrow.png.*" + phrase + ".*<IMG.*label_name_left_arrow.png.*"
                + "<tw:label.designator[^<>]+>.*</tw.label.designator>.*"
                + "<tw:headtext[^<>]+>.*>Repealed by court order date.*</tw:headtext>.*";
        boolean lineExists = RegexUtils.matchesRegex(editorTextPage().getElementsInnerHTML(
                EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT), regex);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
              () -> Assertions.assertEquals(codesHeadRRNLInTreeAfter, codesHeadRRNLInTreeBefore + 1,
                      "RRNL should be inserted in the Tree"),
              () -> Assertions.assertEquals(headInfoInTreeAfter, headInfoInTreeBefore + 1,
                      "head.info should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && nonLineSectionInTextAfter ==
                              nonLineSectionInTextBefore + 1,
                      "Non-Live Rule Nameline should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists,
                      "Non-Live Rule Nameline should have a mnemonic of RRNL"),
              () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(this.user()), modifiedTag,
                      "Non-Live Rule Nameline should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists,
                      "Non-Live Rule Nameline should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists,
                      "Non-Live Rule Nameline should have a default source tag"),
              () -> Assertions.assertTrue(lineExists, "This contains <keyword>Rule<keyword><value> " +
                      "<value> <heading text>Repealed by court order date<heading text>")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
