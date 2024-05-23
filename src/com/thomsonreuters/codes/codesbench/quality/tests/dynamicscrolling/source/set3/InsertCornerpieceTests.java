package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InsertCornerpieceTests extends TestService
{
        String uuid1 = "I7146D5A02F4611E29538B629ABD39661"; //Iowa
        /*Insert Cornerpiece as sibling
         * 1. Open document
         * 2. Select and right click the Cornerpiece
         * 3. Go to Insert Text (sibling) -> Cornerpiece -> cornerpiece cfr
         * VERIFY: A similar Cornerpiece element will be entered as a sibling
         * VERIFY: This new cornerpiece will have a CP mnemonic, a NOPUB pubtag, and a matching source tag to what the default is set in the editor preferences
         * VERIFY: This new cornerpiece has a modified by tag
         * VERIFY: This new cornerpiece contains: a hint to insert the title, a section symbol, and a hint to insert the section information
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertCornerpieceCFRAsSiblingSourceTest()
        {
                //String uuid = "IF9AB57F034F411E7ACDACFC8F254A8C8"; // for dev
                sourcePage().goToSourcePageWithRenditionUuids(uuid1);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int cornerpieceCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
                int cornerpieceTextCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

                //It's difficult to find the document with the needed element,
                //but it's necessary to check the work of scrolling,
                //this is the result of scrolling to the second chunk and then it comes back
                int targetChunkNumber = 1;
                editorPage().scrollToChunk(targetChunkNumber + 1);
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(targetChunkNumber);

                int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

                // Right click and select
                editorTextPage().clickCornerpiece();
               editorTextPage().rightClickCornerpiece();
                editorTextPage().breakOutOfFrame();
                editorTextContextMenuPage().insertTextSiblingCornerpieceCfr();

                //checks in the tree
                int cornerpieceCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
                int cornerpieceTextCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
                boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
                boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

                //checks in text
                editorTextPage().switchToEditorTextArea();
                int cornerpieceCountInTextAfter =
                        editorTextPage().getElements(EditorTextPageElements.CORNERPIECE).size();

                String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
                boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
                boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

                editorPage().scrollToElement(highlightedCP);
                boolean mnemonicExists = editorTextPage()
                        .doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CP.xpath());

                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
                boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

                boolean nopubExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SOURCE_TAG);

                boolean sectionSymbolExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SECTION_SIGN);

                boolean defaultTitleSourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT,
                                                      "insert CFR title here"));
                boolean defaultSectionSourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT,
                                                      "Insert section information here"));
                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(cornerpieceCpExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_CP
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(cornerpieceTextExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_TEXT
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                                            "Cornerpiece should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists,
                                                            "Cornerpiece should have a mnemonic of "
                                                            + EditorTextPageElements.mnemonics.CP.name()),
                                () -> Assertions.assertTrue(modifiedExists,
                                                            "Cornerpiece should have a modified by tag"),
                                () -> Assertions.assertFalse(nopubExists,
                                                             "Cornerpiece should not have a pub tag of NOPUB"),
                                () -> Assertions.assertFalse(sourceTagExists,
                                                             "Cornerpiece should not have a default source tag"),
                                () -> Assertions.assertTrue(sectionSymbolExists,
                                                            "a section symbol should have a default source tag"),
                                () -> Assertions.assertTrue(defaultTitleSourceTagExists,
                                                            "a hint to insert the title should have a default source tag"),
                                () -> Assertions.assertTrue(defaultSectionSourceTagExists,
                                                            "a hint to insert the section information should have a default source tag")
                        );
        }

        /*Insert Cornerpiece CPL1
         * 1. Open document
         * 2. Select and right click the Cornerpiece
         * 3. Go to Insert Text (sibling) -> Cornerpiece -> cornerpiece cpl 1
         * VERIFY: A Cornerpiece Non-Live Identifier element will be entered as a sibling
         * VERIFY: This new cornerpiece will have a CPL1 mnemonic, a NOPUB pubtag, and a matching source tag to what the default is set in the editor preferences
         * VERIFY: This new cornerpiece has a modified by tag
         * VERIFY: This new cornerpiece (might) contain: a hint to insert the title, a section symbol, and a hint to insert the section information
         *
         * might contain the hints, and might not.  If not, it's not an actual bug, it's because the stocknote data used to generate the element needs to have hints added to it
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertCornerpieceCPL1AsSiblingSourceTest()
        {
                //String uuid = "IF9AB57F034F411E7ACDACFC8F254A8C8"; // for dev
                sourcePage().goToSourcePageWithRenditionUuids(uuid1);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int cornerpieceCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPL1);
                int cornerpieceTextCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

                //It's difficult to find the document with the needed element,
                //but it's necessary to check the work of scrolling,
                //this is the result of scrolling to the second chunk and then it comes back
                int targetChunkNumber = 1;
                editorPage().scrollToChunk(targetChunkNumber + 1);
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(targetChunkNumber);

                int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

                // Right click and select
                editorTextPage().clickCornerpiece();
                editorTextPage().rightClickCornerpiece();
                editorTextPage().breakOutOfFrame();
                editorTextContextMenuPage().insertTextSiblingCornerpieceCpl1();

                //checks in the tree
                int cornerpieceCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPL1);
                int cornerpieceTextCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
                boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
                boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

                //checks in text
                editorTextPage().switchToEditorTextArea();
                int cornerpieceCountInTextAfter =
                        editorTextPage().getElements(EditorTextPageElements.CORNERPIECE).size();

                String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
                boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
                boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

                editorPage().scrollToElement(highlightedCP);
                boolean mnemonicExists = editorTextPage()
                        .doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPL1.xpath());

                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
                boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

                boolean nopubExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SOURCE_TAG);

                boolean sectionSymbolExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SECTION_SIGN);

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(cornerpieceCpExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_CPL1
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(cornerpieceTextExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_TEXT
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                                            "Cornerpiece should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists,
                                                            "Cornerpiece should have a mnemonic of "
                                                            + EditorTextPageElements.mnemonics.CPL1.name()),
                                () -> Assertions.assertTrue(modifiedExists,
                                                            "Cornerpiece should have a modified by tag"),
                                () -> Assertions.assertFalse(nopubExists,
                                                             "Cornerpiece should not have a pub tag of NOPUB"),
                                () -> Assertions.assertFalse(sourceTagExists,
                                                             "Cornerpiece should not have a default source tag"),
                                () -> Assertions.assertTrue(sectionSymbolExists,
                                                            "a section symbol should have a default source tag")
                        );
        }

        /*Insert Cornerpiece CPR1-Renumbered
         * This option might be setup for multiple cornerpieces, so the context menu option might contain addition stuff
         * and this might also insert additional cornerpieces...  but we can ignore those
         *
         * 1. Open document
         * 2. Select and right click the Cornerpiece
         * 3. Go to Insert Text (sibling) -> Cornerpiece -> cornerpiece cpr1 renumbered
         * VERIFY: A Cornerpiece Non-Live Identifier element will be entered as a sibling
         * VERIFY: This new cornerpiece will have a CPR1 mnemonic, a NOPUB pubtag, and a matching source tag to what the default is set in the editor preferences
         * VERIFY: This new cornerpiece has a modified by tag
         * VERIFY: This new cornerpiece contains "Renumbered" text
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertCornerpieceCPR1RenumberedAsSiblingSourceTest()
        {
                //String uuid2 = "IBA13A8E0B40B11E79C4185E87FAD0B80"; // for dev
                sourcePage().goToSourcePageWithRenditionUuids(uuid1);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int cornerpieceCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
                int cornerpieceTextCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

                //It's difficult to find the document with the needed element,
                //but it's necessary to check the work of scrolling,
                //this is the result of scrolling to the second chunk and then it comes back
                int targetChunkNumber = 1;
                editorPage().scrollToChunk(targetChunkNumber + 1);
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(targetChunkNumber);

                int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

                // Right click and select
               editorTextPage().clickCornerpiece();
              //  editorTextPage().rightClickCornerpiece();

               editorTextPage().scrollToView(EditorTextPageElements.CORNERPIECE_SPAN);
            editorTextPage().rightClickCornerpiece();
               // editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenuPage().insertTextSiblingCornerpieceRenumbered();

                //checks in the tree
                int cornerpieceCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
                int cornerpieceTextCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
                boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
                boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

                //checks in text
                editorTextPage().switchToEditorTextArea();
                int cornerpieceCountInTextAfter =
                        editorTextPage().getElements(EditorTextPageElements.CORNERPIECE).size();

                String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
                boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
                boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

                editorPage().scrollToElement(highlightedCP);
                boolean mnemonicExists = editorTextPage()
                        .doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPR1.xpath());

                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
                boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

                boolean nopubExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SOURCE_TAG);

                boolean sectionSymbolExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SECTION_SIGN);

                boolean cpContainsRenumberedText = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.CORNERPIECE_TEXT_WITH_TEXT,
                                                      "Renumbered"));

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(cornerpieceCpExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_CPR1
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(cornerpieceTextExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_TEXT
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                                            "Cornerpiece should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists,
                                                            "Cornerpiece should have a mnemonic of "
                                                            + EditorTextPageElements.mnemonics.CPR1.name()),
                                () -> Assertions.assertTrue(modifiedExists,
                                                            "Cornerpiece should have a modified by tag"),
                                () -> Assertions.assertFalse(nopubExists,
                                                             "Cornerpiece should not have a pub tag of NOPUB"),
                                () -> Assertions.assertFalse(sourceTagExists,
                                                             "Cornerpiece should not have a default source tag"),
                                () -> Assertions.assertTrue(sectionSymbolExists,
                                                            "a section symbol should have a default source tag"),
                                () -> Assertions.assertTrue(cpContainsRenumberedText,
                                                            "new cornerpiece should contain \"Renumbered\" text")
                        );
        }

        /*Insert Cornerpiece CPR1-Repealed
         * This option might be setup for multiple cornerpieces, so the context menu option might contain addition stuff
         * and this might also insert additional cornerpieces...  but we can ignore those
         *
         * 1. Open document
         * 2. Select and right click the Cornerpiece
         * 3. Go to Insert Text (sibling) -> Cornerpiece -> cornerpiece cpr1 repealed
         * VERIFY: A Cornerpiece Non-Live Identifier element will be entered as a sibling
         * VERIFY: This new cornerpiece will have a CPR1 mnemonic, a NOPUB pubtag, and a matching source tag to what the default is set in the editor preferences
         * VERIFY: This new cornerpiece has a modified by tag
         * VERIFY: This new cornerpiece contains "Repealed" text
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertCornerpieceCPR1RepealAsSiblingSourceTest()
        {
                //String uuid2 = "IBA13A8E0B40B11E79C4185E87FAD0B80"; // for dev
                sourcePage().goToSourcePageWithRenditionUuids(uuid1);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int cornerpieceCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
                int cornerpieceTextCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

                //It's difficult to find the document with the needed element,
                //but it's necessary to check the work of scrolling,
                //this is the result of scrolling to the second chunk and then it comes back
                int targetChunkNumber = 1;
                editorPage().scrollToChunk(targetChunkNumber + 1);
                editorPage().breakOutOfFrame();
                editorPage().scrollToChunk(targetChunkNumber);

                int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

                // Right click and select
                editorTextPage().clickCornerpiece();
                editorTextPage().scrollToView(EditorTextPageElements.CORNERPIECE_SPAN);
                editorTextPage().rightClickCornerpiece();
                editorTextPage().breakOutOfFrame();
                editorTextContextMenuPage().insertTextSiblingCornerpieceRepeal();

                //checks in the tree
                int cornerpieceCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
                int cornerpieceTextCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
                boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
                boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

                //checks in text
                editorTextPage().switchToEditorTextArea();
                int cornerpieceCountInTextAfter =
                        editorTextPage().getElements(EditorTextPageElements.CORNERPIECE).size();

                String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
                boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
                boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

                editorPage().scrollToElement(highlightedCP);
                boolean mnemonicExists = editorTextPage()
                        .doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPR1.xpath());

                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
                boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

                boolean nopubExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourceTagExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SOURCE_TAG);

                boolean sectionSymbolExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SECTION_SIGN);

                boolean cpContainsRepealedText = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.CORNERPIECE_TEXT_WITH_TEXT,
                                                      "Repealed"));

                editorPage().closeDocumentAndDiscardChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(cornerpieceCpExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_CPR1
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(cornerpieceTextExists,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_TEXT
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                                            "Cornerpiece should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists,
                                                            "Cornerpiece should have a mnemonic of "
                                                            + EditorTextPageElements.mnemonics.CPR1.name()),
                                () -> Assertions.assertTrue(modifiedExists,
                                                            "Cornerpiece should have a modified by tag"),
                                () -> Assertions.assertFalse(nopubExists,
                                                             "Cornerpiece should not have a pub tag of NOPUB"),
                                () -> Assertions.assertFalse(sourceTagExists,
                                                             "Cornerpiece should not have a default source tag"),
                                () -> Assertions.assertTrue(sectionSymbolExists,
                                                            "a section symbol should have a default source tag"),
                                () -> Assertions.assertTrue(cpContainsRepealedText,
                                                            "new cornerpiece should contain \"Repealed\" text")
                        );
        }

        /**
         * Insert Cornerpiece as child
         * 1. Open document
         * 2. Select and right click the Section
         * 3. Go to Insert Text (child) -> Cornerpiece -> cornerpiece cfr
         * VERIFY: A Cornerpiece element will be entered as a child
         * VERIFY: This new cornerpiece will have a CP mnemonic, a NOPUB pubtag, and a matching source tag to what the default is set in the editor preferences
         * VERIFY: This new cornerpiece has a modified by tag
         * VERIFY: This new cornerpiece contains: a hint to insert the title, a section symbol, and a hint to insert the section information
         */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertCornerpieceCFRAsChildSourceTest()
        {
                //String uuid = "IF9AB57F034F411E7ACDACFC8F254A8C8"; // for dev
                sourcePage().goToSourcePageWithRenditionUuids(uuid1);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int targetChunkNumber = 2;

                editorPage().scrollToChunk(targetChunkNumber);
                int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

                String firstSectionWithinChunk = String.format(EditorTextPageElements.LOADED_CHUNK, targetChunkNumber - 1)
                        + EditorTextPageElements.SOURCE_SECTION + "/span";
                editorTextPage().click(firstSectionWithinChunk);
                editorPage().breakOutOfFrame();
                int cornerpieceCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
                int cornerpieceTextCountInTreeBefore =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
                editorPage().switchToEditorTextFrame();
                editorTextPage().rightClick(firstSectionWithinChunk);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenuPage().insertTextChildCornerpieceCfr();

                //checks in the tree
                int cornerpieceCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
                int cornerpieceTextCountInTreeAfter =
                        editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

                //checks in text
                EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.CP;

                editorPage().switchDirectlyToTextFrame();

                int cornerpieceCountInTextAfter =
                        editorPage().getElements(EditorTextPageElements.CORNERPIECE).size();

                String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
                boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
                boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

                boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + mnemonic.xpath());

                String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
                boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

                boolean nopubExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

                boolean sourcetagExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SOURCE_TAG);

                boolean sectionSymbolExists = editorTextPage().doesElementExist(
                        highlightedCP + EditorTextPageElements.SECTION_SIGN);

                boolean insertCRFTitleHereExists = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT,
                                                      "insert CFR title here"));

                boolean insertSectionInformationHereExists = editorTextPage().doesElementExist(
                        highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT,
                                                      "Insert section information here"));
                editorPage().closeEditorWithDiscardingChangesIfAppeared();

                Assertions.assertAll
                        (
                                () -> Assertions.assertEquals(cornerpieceCountInTreeBefore + 1, cornerpieceCountInTreeAfter,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_CP
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertEquals(cornerpieceTextCountInTreeBefore + 1, cornerpieceTextCountInTreeAfter,
                                                            EditorTreePageElements.treeElements.CORNERPIECE_TEXT
                                                                    .getNodeName() + " should be inserted in the Tree"),
                                () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                                            "Cornerpiece should be inserted"),
                                () -> Assertions.assertTrue(mnemonicExists,
                                                            "Cornerpiece should have a mnemonic of " + mnemonic
                                                                    .name()),
                                () -> Assertions.assertTrue(modifiedExists,
                                                            "Cornerpiece should have a modified by tag"),
                                () -> Assertions.assertFalse(nopubExists,
                                                            "Cornerpiece should not have a pub tag of NOPUB"),
                                () -> Assertions.assertFalse(sourcetagExists,
                                                            "Cornerpiece should not have a default source tag"),
                                () -> Assertions.assertTrue(sectionSymbolExists,
                                                            "a section symbol should have a default source tag"),
                                () -> Assertions.assertTrue(insertCRFTitleHereExists,
                                                            "a hint to insert the title should have a default source tag"),
                                () -> Assertions.assertTrue(insertSectionInformationHereExists,
                                                            "a hint to insert the section information should have a default source tag")
                        );
        }
}
