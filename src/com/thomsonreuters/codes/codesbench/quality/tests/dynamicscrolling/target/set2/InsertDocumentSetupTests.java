package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class InsertDocumentSetupTests extends TestService
{

        /**
         * Insert Document Setup SRNL 1. Open document 2. Select and right click the
         * Cornerpiece 3. Go to Insert Text (sibling) -> Document Setup -> Copy of Copy of grade - chapter
         * This SRNL stocknote might only be set up in UAT VERIFY: Non-Live Section
         * Nameline is inserted VERIFY: This has a mnemonic of SRNL VERIFY: This has a
         * pubtag of NOPUB VERIFY: This has a source tag of the default specified in the
         * editor preferences VERIFY: The content is "<value> <value> to <value>
         * <value>. <heading text> Repealed by ... <heading text>
         *
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertDocumentSetupTargetTest()
        {
                String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
                String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());

                hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
                loginPage().logIn();
                hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();
                editorPage().switchToEditor();


                //It's difficult to find the document with the needed element,
                //but it's necessary to check the work of scrolling,
                //this is the result of scrolling to the second chunk and then it comes back
        /*        int targetChunkNumber = 1;
                editorPage().scrollToChunk(targetChunkNumber + 1);
                editorPage().switchToEditor();
                editorPage().scrollToChunk(targetChunkNumber);
                editorPage().switchToEditor();*/

                int headBlocksCountBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.HEAD_BLOCK);
                int codesHeadHG3CountBefore = editorTreePage().getElements(EditorTreePageElements.codesHead.HG3.getXpath()).size();
                int codesHeadHG3CCountBefore = editorTreePage().getElements(EditorTreePageElements.codesHead.HG3C.getXpath()).size();

                // Right click and select Insert Text (sibling) -> Document Setup -> Copy of Copy of grade - chapter
                editorPage().switchDirectlyToTextFrame();
                editorTextPage().clickCornerpiece();
                editorTextPage().rightClickCornerpiece();
                editorPage().breakOutOfFrame();
                editorTextContextMenu().insertDocumentSetupCopyOfGradeChapter();

                editorPage().acceptAlert();

                // checks in the tree
                int headBlocksCountAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.HEAD_BLOCK);
                int codesHeadHG3CountAfter = editorTreePage().getElements(EditorTreePageElements.codesHead.HG3.getXpath()).size();
                int codesHeadHG3CCountAfter = editorTreePage().getElements(EditorTreePageElements.codesHead.HG3C.getXpath()).size();
                boolean headBlockInserted = headBlocksCountAfter == headBlocksCountBefore + 1;
                boolean hg3Inserted = codesHeadHG3CountAfter == codesHeadHG3CountBefore + 1;
                boolean hg3cInserted = codesHeadHG3CCountAfter == codesHeadHG3CCountBefore + 1;


                // checks in text
                String blockDescription = "Grade Heading Block";
                String gradeHeadingBlock = EditorTextPageElements.GRADE_HEADING_BLOCK;

                String headingDescription1 = "Grade Heading";
                String gradeHeading = EditorTextPageElements.GRADE_HEADING;
                EditorTextPageElements.mnemonics hg3 = EditorTextPageElements.mnemonics.HG3;

                String headingDescription2 = "Grade Heading";
                String gradeHeadingContinued = EditorTextPageElements.GRADE_HEADING_CONTINUED;
                EditorTextPageElements.mnemonics hg3c = EditorTextPageElements.mnemonics.HG3C;

                String section = EditorTextPageElements.SECTION;
                editorTextPage().switchToEditorTextArea();

                boolean blockExists = editorPage().doesElementExist(section + gradeHeadingBlock);

                boolean headingExists1 = editorPage().doesElementExist(section + gradeHeadingBlock + gradeHeading);

                boolean mnemonicExists1 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeading + hg3.xpath());

                boolean modifiedExists1 = editorPage().doesElementExist(section + gradeHeadingBlock + gradeHeading + modifiedBy);

                boolean nopubExists1 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeading + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourcetagExists1 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeading + EditorTextPageElements.SOURCE_TAG);

                //--------------

                boolean headingExists2 = editorPage().doesElementExist(section + gradeHeadingBlock + gradeHeadingContinued);

                boolean mnemonicExists2 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeadingContinued + hg3c.xpath());

                boolean modifiedExists2 = editorPage().doesElementExist(section + gradeHeadingBlock + gradeHeadingContinued + modifiedBy);

                boolean nopubExists2 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeadingContinued + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourcetagExists2 = editorPage()
                        .doesElementExist(section + gradeHeadingBlock + gradeHeadingContinued + EditorTextPageElements.SOURCE_TAG);

                editorPage().closeEditorWithDiscardingChangesIfAppeared();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(headBlockInserted, EditorTreePageElements.treeElements.HEAD_BLOCK.getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(hg3Inserted, EditorTreePageElements.codesHead.HG3.getMnemonic() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(hg3cInserted, EditorTreePageElements.codesHead.HG3C.getMnemonic() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists, blockDescription + " should be inserted"),
                                () -> Assertions.assertTrue(headingExists1, headingDescription1 + " should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists1, headingDescription1 + " should have a mnemonic of " + hg3.name()),
                                () -> Assertions.assertTrue(modifiedExists1, headingDescription1 + " should have a modified by tag"),
                                () -> Assertions.assertTrue(nopubExists1, headingDescription1 + " should have a pub tag of NOPUB"),
                                () -> Assertions.assertTrue(sourcetagExists1, headingDescription1 + " should have a default source tag"),
                                () -> Assertions.assertTrue(headingExists2, headingDescription2 + " should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists2, headingDescription2 + " should have a mnemonic of " + hg3c.name()),
                                () -> Assertions.assertTrue(modifiedExists2, headingDescription2 + " should have a modified by tag"),
                                () -> Assertions.assertTrue(nopubExists2, headingDescription2 + " should have a pub tag of NOPUB"),
                                () -> Assertions.assertTrue(sourcetagExists2, headingDescription2 + " should have a default source tag")
                        );
        }
}
