package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

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
import org.junit.jupiter.api.Test;

public class InsertReferencesTests extends TestService
{
    String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
    /**
     * 1. Open document
     * 2. Scroll to the annotations wrapper (Will probably be easier to click it from the tree)
     * 3. Select and Right click the Annotations wrapper
     * 4. Select Insert Feature -> Reference -> USCA Reference -> usca.references
     *
     * VERIFY: USCA Reference Block is inserted with USCA Reference Heading and USCA Reference Line as children
     *
     * VERIFY: The USCA Reference Heading has a mnemonic of XUSA
     * VERIFY: The USCA Reference Heading has a pub tag of NOPUB
     * VERIFY: The USCA Reference Heading has a default source tag
     * VERIFY: The USCA Reference Heading has a modified by tag
     * VERIFY: The USCA Reference Heading has content of heading text markup
     *
     * VERIFY: The USCA Reference Line has a mnemonic of CRL
     * VERIFY: The USCA Reference Line has a pub tag of NOPUB
     * VERIFY: The USCA Reference Line has a default source tag
     * VERIFY: The USCA Reference Line has a modified by tag
     * VERIFY: The USCA Reference Line has content of U.S.C.A Ref
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertUSCAReferenceTargetLegalTest()
    {
        int targetChunk = 2;
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(targetChunk);

        EditorTreePageElements.treeElements treeUscaReferenceBlock = EditorTreePageElements.treeElements.USCA_REFERENCE_BLOCK;
        EditorTreePageElements.treeElements treeCodesHeadXusa = EditorTreePageElements.treeElements.CODES_HEAD_XUSA;
        EditorTreePageElements.treeElements treeUscaReferenceCrl = EditorTreePageElements.treeElements.USCA_REFERENCE_CRL;

        int uscaReferenceBlockInTreeBefore = editorTreePage().countTreeElements(treeUscaReferenceBlock);
        int codesHeadUSCAInTreeBefore = editorTreePage().countTreeElements(treeCodesHeadXusa);
        int uscaReferenceCrlInTreeBefore = editorTreePage().countTreeElements(treeUscaReferenceCrl);

        editorPage().scrollToElement(EditorTextPageElements.ANNOTATIONS);

        //add delta
        editorTextPage().rightClick(EditorTextPageElements.ANNOTATIONS_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_FEATURE,
                EditorTextContextMenuElements.REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCES);
        editorPage().switchToEditor();

        //checks in the tree
        int uscaReferenceBlockInTreeAfter = editorTreePage().countTreeElements(treeUscaReferenceBlock);
        int codesHeadUSCAInTreeAfter = editorTreePage().countTreeElements(treeCodesHeadXusa);
        int uscaReferenceCrlInTreeAfter = editorTreePage().countTreeElements(treeUscaReferenceCrl);

        //checks in text
        editorTextPage().switchToEditorTextArea();

        boolean USCAReferenceBlockExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_BLOCK );
        boolean USCAReferenceHeadingExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING );
        boolean USCAReferenceLineExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE );

        boolean USCAReferenceHeadingHasAMnemonicOfXUSA = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.mnemonics.XUSA.xpath()/*XUSA_MNEMINIC*/ );
        boolean USCAReferenceHeadingHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceHeadingHasADefaultSourceTag = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.SOURCE_TAG);
        String modifiedByTag = editorTextPage().getModifiedByTag(this.user());
        String USCAReferenceHeadingHasAModifiedByTag = editorTextPage().getElementsText(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceHeadingHasContentOfHeadingTextMarkup = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        boolean USCAReferenceLineHasAMnemonicOfCRL = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.mnemonics.CRL.xpath()/*CRL_MNEMINIC*/ );
        boolean USCAReferenceLineHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceLineHasADefaultSourceTag = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceLineHasAModifiedByTag = editorTextPage().getElementsText(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceLineHasContentOfUSCARef = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.USCA_REF );

        editorPage().breakOutOfFrame();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(uscaReferenceBlockInTreeAfter > uscaReferenceBlockInTreeBefore,
                                treeUscaReferenceBlock.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(codesHeadUSCAInTreeAfter > codesHeadUSCAInTreeBefore,
                                treeCodesHeadXusa.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(uscaReferenceCrlInTreeAfter > uscaReferenceCrlInTreeBefore,
                                treeUscaReferenceCrl.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(USCAReferenceBlockExists,
                                "USCA Reference Block should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingExists,
                                "USCA Reference Heading should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceLineExists,
                                "USCA Reference Line should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAMnemonicOfXUSA,
                                "The USCA Reference Heading should have a mnemonic of XUSA"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAPubtagOfNOPUB,
                                "The USCA Reference Heading should not have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasADefaultSourceTag,
                                "The USCA Reference Heading should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceHeadingHasAModifiedByTag,
                                "The USCA Reference Heading should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasContentOfHeadingTextMarkup,
                                "The USCA Reference Heading should have content of heading text markup"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAMnemonicOfCRL,
                                "The USCA Reference Line should have a mnemonic of CRL"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAPubtagOfNOPUB,
                                "The USCA Reference Line should not have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasADefaultSourceTag,
                                "The USCA Reference Line should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceLineHasAModifiedByTag,
                                "The USCA Reference Line should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasContentOfUSCARef,
                                "The USCA Reference Line should have content of U.S.C.A Ref")
                );
    }

    /**
     * Insert Historical Note Reference
     * 1. Open document
     * 2. Scroll to the annotations wrapper (Will probably be easier to click it from the tree)
     * 3. Select and Right click the Annotations wrapper
     * 4. Select Insert Feature -> Historical Note -> Law Note - law.note
     *
     * VERIFY: Historical Note Body is inserted with Historical Note Subheading and Law Note Historical Note as children.
     * The Law Note Historical Note has a Historical Note Paragraph Quoted child
     *
     * VERIFY: The Historical Note Subheading has a mnemonic of GNPC
     * VERIFY: The Historical Note Subheading has a pub tag of NOPUB
     * VERIFY: The Historical Note Subheading has a default source tag
     * VERIFY: The Historical Note Subheading has a modified by tag
     * VERIFY: The Historical Note Subheading has content of heading text markup (I don't think the content of it matter, but mine was 2010 Legislation)
     *
     * VERIFY: The Historical Note Paragraph Quoted has a mnemonic of GNPQ
     * VERIFY: The Historical Note Paragraph Quoted has a pub tag of NOPUB
     * VERIFY: The Historical Note Paragraph Quoted has a default source tag
     * VERIFY: The Historical Note Paragraph Quoted has a modified by tag
     * VERIFY: The Historical Note Paragraph Quoted has content of 'Acts 2010 (83 G.A) H.F.,  , provides' (I don't think this content matters either)
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertHistoricalNoteReferenceTargetLegalTest()
    {
        int targetChunk = 2;
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(targetChunk);

        EditorTreePageElements.treeElements treeHistNoteBody = EditorTreePageElements.treeElements.HIST_NOTE_BODY;
        EditorTreePageElements.treeElements treeCodesHeadGnpc = EditorTreePageElements.treeElements.CODES_HEAD_GNPC;
        EditorTreePageElements.treeElements treeHistNoteLawNote = EditorTreePageElements.treeElements.HIST_NOTE_LAW_NOTE;

        int el1InTreeBefore = editorTreePage().countTreeElements(treeHistNoteBody);
        int el2InTreeBefore = editorTreePage().countTreeElements(treeCodesHeadGnpc);
        int el3InTreeBefore = editorTreePage().countTreeElements(treeHistNoteLawNote);

        editorPage().scrollToElement(EditorTextPageElements.ANNOTATIONS);

        //add delta
        editorTextPage().rightClick(EditorTextPageElements.ANNOTATIONS_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_FEATURE,
                EditorTextContextMenuElements.HISTORICAL_NOTE,
                EditorTextContextMenuElements.LAW_NOTE);
        editorPage().switchToEditor();


        //checks in the tree
        int el1InTreeAfter = editorTreePage().countTreeElements(treeHistNoteBody);
        int el2InTreeAfter = editorTreePage().countTreeElements(treeCodesHeadGnpc);
        int el3InTreeAfter = editorTreePage().countTreeElements(treeHistNoteLawNote);

        //checks in text
        editorTextPage().switchToEditorTextArea();

        boolean USCAReferenceBlockExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_BODY );
        boolean USCAReferenceHeadingExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING );
        boolean USCAReferenceLineExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL );

        boolean USCAReferenceHeadingHasAMnemonicOfXUSA = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.mnemonics.GNPC.xpath() );
        boolean USCAReferenceHeadingHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceHeadingHasADefaultSourceTag = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.SOURCE_TAG);
        String modifiedByTag = editorTextPage().getModifiedByTag(this.user());
        String USCAReferenceHeadingHasAModifiedBytag = editorTextPage().getElementsText(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceHeadingHasContentOfHeadingTextMarkup = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        boolean USCAReferenceLineHasAMnemonicOfCRL = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.mnemonics.GNPQ.xpath() );
        boolean USCAReferenceLineHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceLineHasADefaultSourceTag = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceLineHasAModifiedByTag = editorTextPage().getElementsText(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceLineHasContentOfUSCARef = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.PARATEXT);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(el1InTreeAfter > el1InTreeBefore,
                                treeHistNoteBody.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(el2InTreeAfter > el2InTreeBefore,
                                treeCodesHeadGnpc.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(el3InTreeAfter > el3InTreeBefore,
                                treeHistNoteLawNote.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(USCAReferenceBlockExists,
                                "Historical Note Body should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingExists,
                                "Historical Note Subheading should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceLineExists,
                                "Law Note Historical Note should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAMnemonicOfXUSA,
                                "The Historical Note Subheading should have a mnemonic of GNPC"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAPubtagOfNOPUB,
                                "The Historical Note Subheading should not have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasADefaultSourceTag,
                                "The Historical Note Subheading should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceHeadingHasAModifiedBytag,
                                "The Historical Note Subheading should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasContentOfHeadingTextMarkup,
                                "The Historical Note Subheading should have content of heading text markup"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAMnemonicOfCRL,
                                "The Historical Note Paragraph Quoted should have a mnemonic of CRL"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAPubtagOfNOPUB,
                                "The Historical Note Paragraph Quoted should not have a pub tag of NOPUB"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasADefaultSourceTag,
                                "The Historical Note Paragraph Quoted should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceLineHasAModifiedByTag,
                                "The Historical Note Paragraph Quoted should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasContentOfUSCARef,
                                "The Historical Note Paragraph Quoted should have content of U.S.C.A Ref")
                );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertingReferencesBouncesCursorToTheTopOfThePageTargetLegalTest()
    {
        int targetChunk = 2;
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(targetChunk);
        editorPage().scrollToElement(EditorTextPageElements.ANNOTATIONS);

        //add delta
        editorTextPage().rightClick(EditorTextPageElements.ANNOTATIONS_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_FEATURE,
                EditorTextContextMenuElements.REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCES);
        editorTextPage().switchToEditorTextArea();

        boolean USCAReferenceBlockExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_BLOCK);
        boolean USCAReferenceHeadingExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_HEADING);
        boolean USCAReferenceLineExists = editorTextPage().doesElementExist(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.USCA_REFERENCE_LINE);

        editorTextPage().breakOutOfFrame();
        editorPage().scrollToChunk(targetChunk);
        editorPage().scrollToElement(EditorTextPageElements.ANNOTATIONS);

        int historicalNotesBefore = editorTextPage().countElements(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL);

        //add delta
        editorTextPage().scrollToView(EditorTextPageElements.ANNOTATIONS_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.ANNOTATIONS_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_FEATURE,
                EditorTextContextMenuElements.HISTORICAL_NOTE,
                EditorTextContextMenuElements.LAW_NOTE);
        editorTextPage().switchToEditorTextArea();

        int historicalNotesAfter = editorTextPage().countElements(
                EditorTextPageElements.ANNOTATIONS + EditorTextPageElements.LAW_NOTE_HISTORICAL);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(USCAReferenceBlockExists,
                                "USCA Reference Block should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingExists,
                                "USCA Reference Heading should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceLineExists,
                                "USCA Reference Line should be inserted"),
                        () -> Assertions.assertEquals(historicalNotesBefore + 1, historicalNotesAfter,
                                " Law Note Historical Note should be added")
                );
    }
}
