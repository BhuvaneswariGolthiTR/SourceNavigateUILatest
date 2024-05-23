package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertPageForcerTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /*Insert Page Forcer TOPT/TODD/TOP/PRMS
     * 1. open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Page Forcer -> Page Forcer In Text Next Page With Running Heads (topt/todd/top/prms)
     * VERIFY: Page Forcer In Text Next Page With Running Heads is inserted
     * VERIFY: This has a mnemonic of TOPT/TODD/TOP/PRMS
     * VERIFY: This has a pubtag of NOPUB
     * VERIFY: This has a source tag of the default source tag found in the editor preferencess
     * VERIFY: This has modified by stuff
     */

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertPageForcerTOPTTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());

        String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int placeholderCountInTreeBefore =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TOPT);

        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextBefore = editorTextPage().countPlaceholderElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingPageForcerTOPT();

        //checks in the tree
        int placeholderCountInTreeAfter =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TOPT);
        boolean placeholderTOPTExists = placeholderCountInTreeAfter > placeholderCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextAfter = editorTextPage().countPlaceholderElements();

        String highlightedTOPT = EditorTextPageElements.HIGHLIGHTED_PLACEHOLDER;
        boolean blockExists = editorTextPage().doesElementExist(highlightedTOPT);
        boolean blockDescriptionExists = placeholderCountInTextAfter > placeholderCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(
                highlightedTOPT + EditorTextPageElements.mnemonics.TOPT.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedTOPT + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(
                highlightedTOPT + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                highlightedTOPT + EditorTextPageElements.SOURCE_TAG);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(placeholderTOPTExists,
                                EditorTreePageElements.treeElements.PLACEHOLDER_TOPT
                                        .getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                "Placeholder TOPT should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists,
                                "Placeholder should have a mnemonic of "
                                        + EditorTextPageElements.mnemonics.TOPT.name()),
                        () -> Assertions.assertTrue(modifiedExists,
                                "Placeholder should have a modified by tag"),
                        () -> Assertions.assertTrue(nopubExists,
                                "Placeholder should have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(sourcetagExists,
                                "Placeholder should have a default source tag")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertPageForcerTODDTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());

        String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int placeholderCountInTreeBefore =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TODD);

        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextBefore = editorTextPage().countPlaceholderElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingPageForcerTODD();

        //checks in the tree
        int placeholderCountInTreeAfter =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TODD);
        boolean placeholderTODDExists = placeholderCountInTreeAfter > placeholderCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextAfter = editorTextPage().countPlaceholderElements();

        String highlightedTODD = EditorTextPageElements.HIGHLIGHTED_PLACEHOLDER;
        boolean blockExists = editorTextPage().doesElementExist(highlightedTODD);
        boolean blockDescriptionExists = placeholderCountInTextAfter > placeholderCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(
                highlightedTODD + EditorTextPageElements.mnemonics.TODD.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedTODD + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(
                highlightedTODD + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                highlightedTODD + EditorTextPageElements.SOURCE_TAG);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(placeholderTODDExists,
                                EditorTreePageElements.treeElements.PLACEHOLDER_TODD
                                        .getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                "Placeholder TODD should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists,
                                "Placeholder should have a mnemonic of "
                                        + EditorTextPageElements.mnemonics.TODD.name()),
                        () -> Assertions.assertTrue(modifiedExists,
                                "Placeholder should have a modified by tag"),
                        () -> Assertions.assertTrue(nopubExists,
                                "Placeholder should have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(sourcetagExists,
                                "Placeholder should have a default source tag")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertPageForcerTOPTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());

        String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int placeholderCountInTreeBefore =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TOP);

        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextBefore = editorTextPage().countPlaceholderElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingPageForcerTOP();

        //checks in the tree
        int placeholderCountInTreeAfter =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_TOP);
        boolean placeholderTOPExists = placeholderCountInTreeAfter > placeholderCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextAfter = editorTextPage().countPlaceholderElements();

        String highlightedTOP = EditorTextPageElements.HIGHLIGHTED_PLACEHOLDER;
        boolean blockExists = editorTextPage().doesElementExist(highlightedTOP);
        boolean blockDescriptionExists = placeholderCountInTextAfter > placeholderCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(
                highlightedTOP + EditorTextPageElements.mnemonics.TOP.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedTOP + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(
                highlightedTOP + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                highlightedTOP + EditorTextPageElements.SOURCE_TAG);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(placeholderTOPExists,
                                EditorTreePageElements.treeElements.PLACEHOLDER_TOP
                                        .getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                "Placeholder TOP should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists,
                                "Placeholder should have a mnemonic of "
                                        + EditorTextPageElements.mnemonics.TOP.name()),
                        () -> Assertions.assertTrue(modifiedExists,
                                "Placeholder should have a modified by tag"),
                        () -> Assertions.assertTrue(nopubExists,
                                "Placeholder should have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(sourcetagExists,
                                "Placeholder should have a default source tag")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertPageForcerPRMSTargetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String modifiedBy = editorTextPage().getModifiedByXpath(this.user());

        String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();

        int placeholderCountInTreeBefore =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_PRMS);

        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextBefore = editorTextPage().countPlaceholderElements();

        // Right click and select
        editorTextPage().clickCornerpiece();
        editorTextPage().rightClickCornerpiece();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertTextSiblingPageForcerPRMS();

        //checks in the tree
        int placeholderCountInTreeAfter =
                editorTreePage().countTreeElements(EditorTreePageElements.treeElements.PLACEHOLDER_PRMS);
        boolean placeholderPRMSExists = placeholderCountInTreeAfter > placeholderCountInTreeBefore;

        //checks in text
        editorTextPage().switchToEditorTextArea();

        int placeholderCountInTextAfter = editorTextPage().countPlaceholderElements();

        String highlightedPRMS = EditorTextPageElements.HIGHLIGHTED_PLACEHOLDER;
        boolean blockExists = editorTextPage().doesElementExist(highlightedPRMS);
        boolean blockDescriptionExists = placeholderCountInTextAfter > placeholderCountInTextBefore;

        boolean mnemonicExists = editorTextPage().doesElementExist(
                highlightedPRMS + EditorTextPageElements.mnemonics.PRMS.xpath());

        boolean modifiedExists = editorTextPage().doesElementExist(highlightedPRMS + modifiedBy);

        boolean nopubExists = editorTextPage().doesElementExist(
                highlightedPRMS + EditorTextPageElements.NOPUB_MNEMONIC);

        boolean sourcetagExists = editorTextPage().doesElementExist(
                highlightedPRMS + EditorTextPageElements.SOURCE_TAG);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(placeholderPRMSExists,
                                EditorTreePageElements.treeElements.PLACEHOLDER_PRMS
                                        .getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(blockExists && blockDescriptionExists,
                                "Placeholder PRMS should be inserted"),
                        () -> Assertions.assertTrue(mnemonicExists,
                                "Placeholder should have a mnemonic of "
                                        + EditorTextPageElements.mnemonics.PRMS.name()),
                        () -> Assertions.assertTrue(modifiedExists,
                                "Placeholder should have a modified by tag"),
                        () -> Assertions.assertTrue(nopubExists,
                                "Placeholder should have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(sourcetagExists,
                                "Placeholder should have a default source tag")
                );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }

}
