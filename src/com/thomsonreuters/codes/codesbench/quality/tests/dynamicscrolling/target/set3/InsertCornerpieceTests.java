package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InsertCornerpieceTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    //NOTE: We will not be required to scroll to a different chunk for these tests because we need to work with cornerpiece
    //stuff at the top of the document.
    //These tests also rely on the proper stocknotes to be created in the first place, which can be content set specific
    //I believe USCA(Development) has more stocknotes setup than Iowa (Development) in DEV.  They might be similar in UAT.

    //If it's not mentioned in the first line of the comment to insert as a child or sibling, the test will
    //focus on inserting as a sibling

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
    public void insertCornerpieceCFRAsSiblingTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingCornerpieceCfr();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
        boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
        boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextAfter = editorTextPage().countCornerpieceElements();

        String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
        boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
        boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CP.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SOURCE_TAG);

        boolean sectionSymbolExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SECTION_SIGN);

        boolean defaultTitleSourceTagExists = editorTextPage().doesElementExist(highlightedCP +
                String.format(EditorTextPageElements.HINT_WITH_TEXT, "insert CFR title here"));
        boolean defaultSectionSourceTagExists = editorTextPage().doesElementExist(highlightedCP +
                String.format(EditorTextPageElements.HINT_WITH_TEXT, "Insert section information here"));

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cornerpieceCpExists, EditorTreePageElements.treeElements.CORNERPIECE_CP.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(cornerpieceTextExists, EditorTreePageElements.treeElements.CORNERPIECE_TEXT.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && blockDescriptionExists, "Cornerpiece should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists, "Cornerpiece should have a mnemonic of " + EditorTextPageElements.mnemonics.CP.name()),
              () -> Assertions.assertTrue(modifiedExists, "Cornerpiece should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists, "Cornerpiece should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists, "Cornerpiece should have a default source tag"),
              () -> Assertions.assertTrue(sectionSymbolExists, "a section symbol should have a default source tag"),
              () -> Assertions.assertTrue(defaultTitleSourceTagExists, "a hint to insert the title should have a default source tag"),
              () -> Assertions.assertTrue(defaultSectionSourceTagExists, "a hint to insert the section information should have a default source tag")
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
    public void insertCornerpieceCPL1AsSiblingTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPL1);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingCornerpieceCpl1();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPL1);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
        boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
        boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextAfter = editorTextPage().countCornerpieceElements();

        String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
        boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
        boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPL1.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SOURCE_TAG);

        boolean sectionSymbolExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SECTION_SIGN);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cornerpieceCpExists, EditorTreePageElements.treeElements.CORNERPIECE_CP.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(cornerpieceTextExists, EditorTreePageElements.treeElements.CORNERPIECE_TEXT.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && blockDescriptionExists, "Cornerpiece should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists, "Cornerpiece should have a mnemonic of " + EditorTextPageElements.mnemonics.CPL1.name()),
              () -> Assertions.assertTrue(modifiedExists, "Cornerpiece should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists, "Cornerpiece should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists, "Cornerpiece should have a default source tag"),
              () -> Assertions.assertTrue(sectionSymbolExists, "a section symbol should have a default source tag")
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
    public void insertCornerpieceCPR1RenumberedAsSiblingTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingCornerpieceRenumbered();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
        boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
        boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextAfter = editorTextPage().countCornerpieceElements();

        String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
        boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
        boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPR1.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SOURCE_TAG);

        boolean sectionSymbolExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SECTION_SIGN);

        boolean cpContainsRenumberedText = editorTextPage().doesElementExist(highlightedCP + String.format(EditorTextPageElements.CORNERPIECE_TEXT_WITH_TEXT, "Renumbered"));

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cornerpieceCpExists, EditorTreePageElements.treeElements.CORNERPIECE_CP.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(cornerpieceTextExists, EditorTreePageElements.treeElements.CORNERPIECE_TEXT.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && blockDescriptionExists, "Cornerpiece should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists, "Cornerpiece should have a mnemonic of " + EditorTextPageElements.mnemonics.CPR1.name()),
              () -> Assertions.assertTrue(modifiedExists, "Cornerpiece should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists, "Cornerpiece should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists, "Cornerpiece should have a default source tag"),
              () -> Assertions.assertTrue(sectionSymbolExists, "a section symbol should have a default source tag"),
              () -> Assertions.assertTrue(cpContainsRenumberedText, "new cornerpiece should contain \"Renumbered\" text")
        );
    }

    /*Insert Cornerpiece CPR1-Repeal
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
    public void insertCornerpieceCPR1RepealAsSiblingTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingCornerpieceRepeal();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CPR1);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
        boolean cornerpieceCpExists = cornerpieceCountInTreeAfter > cornerpieceCountInTreeBefore;
        boolean cornerpieceTextExists = cornerpieceTextCountInTreeAfter > cornerpieceTextCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextAfter = editorTextPage().countCornerpieceElements();

        String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
        boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
        boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.mnemonics.CPR1.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SOURCE_TAG);

        boolean sectionSymbolExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SECTION_SIGN);

        boolean cpContainsRepealedText = editorTextPage().doesElementExist(highlightedCP + String.format(EditorTextPageElements.CORNERPIECE_TEXT_WITH_TEXT, "Repealed"));

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cornerpieceCpExists, EditorTreePageElements.treeElements.CORNERPIECE_CP.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(cornerpieceTextExists, EditorTreePageElements.treeElements.CORNERPIECE_TEXT.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && blockDescriptionExists, "Cornerpiece should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists, "Cornerpiece should have a mnemonic of " + EditorTextPageElements.mnemonics.CPR1.name()),
              () -> Assertions.assertTrue(modifiedExists, "Cornerpiece should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists, "Cornerpiece should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists, "Cornerpiece should have a default source tag"),
              () -> Assertions.assertTrue(sectionSymbolExists, "a section symbol should have a default source tag"),
              () -> Assertions.assertTrue(cpContainsRepealedText, "new cornerpiece should contain \"Repealed\" text")
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
    public void insertCornerpieceCFRAsChildTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int cornerpieceCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeBefore = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);

        editorTextPage().switchToEditorTextArea();

        int cornerpieceCountInTextBefore = editorTextPage().countCornerpieceElements();

        // Right click and select
        editorTextPage().click(EditorTextPageElements.SECTION_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SECTION_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextChildCornerpieceCfr();

        //checks in the tree
        int cornerpieceCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_CP);
        int cornerpieceTextCountInTreeAfter = editorTreePage().countTreeElements(EditorTreePageElements.treeElements.CORNERPIECE_TEXT);
        boolean cornerpieceCpExists = cornerpieceCountInTreeAfter == cornerpieceCountInTreeBefore + 1;
        boolean cornerPieceTextExists = cornerpieceTextCountInTreeAfter == cornerpieceTextCountInTreeBefore + 1;

        //checks in text
        EditorTextPageElements.mnemonics mnemonic = EditorTextPageElements.mnemonics.CP;

        editorPage().switchDirectlyToTextFrame();

        int cornerpieceCountInTextAfter = editorPage().getElements(EditorTextPageElements.CORNERPIECE).size();

        String highlightedCP = EditorTextPageElements.HIGHLIGHTED_CORNERPIECE;
        boolean blockExists = editorTextPage().doesElementExist(highlightedCP);
        boolean blockDescriptionExists = cornerpieceCountInTextAfter > cornerpieceCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(highlightedCP + mnemonic.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedCP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SOURCE_TAG);

        boolean sectionSymbolExists = editorTextPage().doesElementExist(highlightedCP + EditorTextPageElements.SECTION_SIGN);

        boolean insertCRFTitleHereExists = editorTextPage().doesElementExist(highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT, "insert CFR title here"));

        boolean insertSectionInformationHereExists = editorTextPage().doesElementExist(highlightedCP + String.format(EditorTextPageElements.HINT_WITH_TEXT, "Insert section information here"));

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(cornerpieceCpExists, EditorTreePageElements.treeElements.CORNERPIECE_CP.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(cornerPieceTextExists, EditorTreePageElements.treeElements.CORNERPIECE_TEXT.getNodeName() + " should be inserted in the Tree"),
              () -> Assertions.assertTrue(blockExists && blockDescriptionExists, "Cornerpiece should be inserted"),
              () -> Assertions.assertTrue(mnemonicExists, "Cornerpiece should have a mnemonic of " + mnemonic.name()),
              () -> Assertions.assertTrue(modifiedExists, "Cornerpiece should have a modified by tag"),
              () -> Assertions.assertTrue(nopubExists, "Cornerpiece should have a pub tag of NOPUB"),
              () -> Assertions.assertTrue(sourcetagExists, "Cornerpiece should have a default source tag"),
              () -> Assertions.assertTrue(sectionSymbolExists, "a section symbol should have a default source tag"),
              () -> Assertions.assertTrue(insertCRFTitleHereExists, "a hint to insert the title should have a default source tag"),
              () -> Assertions.assertTrue(insertSectionInformationHereExists, "a hint to insert the section information should have a default source tag")
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