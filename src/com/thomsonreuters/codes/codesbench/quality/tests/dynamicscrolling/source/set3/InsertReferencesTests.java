package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertReferencesTests extends TestService
{
    String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
    /**
     * Insert USCA Reference
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
    public void insertUSCAReferenceSourceLegalTest()
    {
        int targetChunk = 1;
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        editorPage().scrollToChunk(targetChunk);

        EditorTreePageElements.treeElements treeDeltaNoteClassified = EditorTreePageElements.treeElements.DELTA;

        String section = EditorTextPageElements.SOURCE_SECTION;

        int deltaNoteClassifiedInTreeBefore = editorTreePage().countTreeElements(treeDeltaNoteClassified);
        int deltaFeatureInTreeBefore = editorTreePage().countElements(EditorTreePageElements.USCA_REFERENCE);

        editorPage().scrollToElement(section);

        //add delta
        editorTextPage().click(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                EditorTextContextMenuElements.REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCE,
                EditorTextContextMenuElements.USCA_REFERENCES);
        editorPage().switchToEditor();

        //checks in the tree"
        int deltaNoteClassifiedInTreeAfter = editorTreePage().countTreeElements(treeDeltaNoteClassified);
        int deltaFeatureInTreeAfter = editorTreePage().countElements(EditorTreePageElements.USCA_REFERENCE);

        //checks in text
        editorTextPage().switchToEditorTextArea();
        
        boolean deltaFeatureExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_FEATURE );
        boolean deltaTargetLocationExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.TARGET_LOCATION );

        boolean USCAReferenceBlockExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_BLOCK );
        boolean USCAReferenceHeadingExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING );
        boolean USCAReferenceLineExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_LINE );

        String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

        boolean USCAReferenceHeadingHasAMnemonicOfXUSA = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.mnemonics.XUSA.xpath() );
        boolean USCAReferenceHeadingHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceHeadingHasADefaultSourceTag = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceHeadingHasAModifiedByTag = editorTextPage().getElementsText(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceHeadingHasContentOfHeadingTextMarkup = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_HEADING +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        boolean USCAReferenceLineHasAMnemonicOfCRL = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.mnemonics.CRL.xpath() );
        boolean USCAReferenceLineHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceLineHasADefaultSourceTag = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceLineHasAModifiedByTag = editorTextPage().getElementsText(
                section + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceLineHasContentOfUSCARef = editorTextPage().doesElementExist(
                section + EditorTextPageElements.USCA_REFERENCE_LINE +
                EditorTextPageElements.USCA_REF );

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(deltaNoteClassifiedInTreeAfter > deltaNoteClassifiedInTreeBefore,
                                treeDeltaNoteClassified.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeatureInTreeAfter > deltaFeatureInTreeBefore,
                                "Usca.Reference should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeatureExists, "Delta Feature should be added"),
                        () -> Assertions.assertTrue(deltaTargetLocationExists,
                                "Delta Target Location should be added"),
                        () -> Assertions.assertTrue(USCAReferenceBlockExists,
                                "USCA Reference Block should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingExists,
                                "USCA Reference Heading should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceLineExists,
                                "USCA Reference Line should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAMnemonicOfXUSA,
                                "The USCA Reference Heading should have a mnemonic of XUSA"),
                        () -> Assertions.assertFalse(USCAReferenceHeadingHasAPubtagOfNOPUB,
                                "The USCA Reference Heading should not have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(USCAReferenceHeadingHasADefaultSourceTag,
                                "The USCA Reference Heading should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceHeadingHasAModifiedByTag,
                                "The USCA Reference Heading should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasContentOfHeadingTextMarkup,
                                "The USCA Reference Heading should have content of heading text markup"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAMnemonicOfCRL,
                                "The USCA Reference Line should have a mnemonic of CRL"),
                        () -> Assertions.assertFalse(USCAReferenceLineHasAPubtagOfNOPUB,
                                "The USCA Reference Line should not have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(USCAReferenceLineHasADefaultSourceTag,
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
    public void insertHistoricalNoteReferenceSourceLegalTest()
    {
        int targetChunk = 1;
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        editorPage().scrollToChunk(targetChunk);

        EditorTreePageElements.treeElements treeDeltaNoteClassified = EditorTreePageElements.treeElements.DELTA;

        String section = EditorTextPageElements.SOURCE_SECTION;

        int deltaNoteClassifiedInTreeBefore = editorTreePage().countTreeElements(treeDeltaNoteClassified);
        int deltaFeatureInTreeBefore = editorTreePage().countElements(EditorTreePageElements.HIST_NOTE);

        editorPage().scrollToElement(section);

        //add delta
        editorTextPage().click(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(
                EditorTextContextMenuElements.INSERT_DELTA_WITH_FEATURE,
                EditorTextContextMenuElements.HISTORICAL_NOTE,
                EditorTextContextMenuElements.LAW_NOTE);
        editorPage().switchToEditor();

        //checks in the tree"
        int deltaNoteClassifiedInTreeAfter = editorTreePage().countTreeElements(treeDeltaNoteClassified);
        int deltaFeatureInTreeAfter = editorTreePage().countElements(EditorTreePageElements.HIST_NOTE);

        //checks in text
        editorTextPage().switchToEditorTextArea();

        boolean deltaFeatureExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.DELTA_FEATURE );
        boolean deltaTargetLocationExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.TARGET_LOCATION );

        boolean USCAReferenceBlockExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_BODY );
        boolean USCAReferenceHeadingExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING );
        boolean USCAReferenceLineExists = editorTextPage().doesElementExist(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL );

        String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

        boolean USCAReferenceHeadingHasAMnemonicOfXUSA = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.mnemonics.GNPC.xpath() );
        boolean USCAReferenceHeadingHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceHeadingHasADefaultSourceTag = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceHeadingHasAModifiedByTag = editorTextPage().getElementsText(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceHeadingHasContentOfHeadingTextMarkup = editorTextPage().doesElementExist(
                section + EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        boolean USCAReferenceLineHasAMnemonicOfCRL = editorTextPage().doesElementExist(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.mnemonics.GNPQ.xpath() );
        boolean USCAReferenceLineHasAPubtagOfNOPUB = editorTextPage().doesElementExist(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean USCAReferenceLineHasADefaultSourceTag = editorTextPage().doesElementExist(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.SOURCE_TAG);
        String USCAReferenceLineHasAModifiedByTag = editorTextPage().getElementsText(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean USCAReferenceLineHasContentOfUSCARef = editorTextPage().doesElementExist(
                section + EditorTextPageElements.LAW_NOTE_HISTORICAL +
                "//paratext[text()='Acts 2012 (84 G.A.) H.F., ']" );

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(deltaNoteClassifiedInTreeAfter > deltaNoteClassifiedInTreeBefore,
                                treeDeltaNoteClassified.getNodeName() + " should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeatureInTreeAfter > deltaFeatureInTreeBefore,
                                "Hist Note should be inserted in the Tree"),
                        () -> Assertions.assertTrue(deltaFeatureExists, "Delta Feature should be added"),
                        () -> Assertions.assertTrue(deltaTargetLocationExists,
                                "Delta Target Location should be added"),
                        () -> Assertions.assertTrue(USCAReferenceBlockExists,
                                "Historical Note Body should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingExists,
                                "Historical Note Subheading should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceLineExists,
                                "Law Note Historical Note should be inserted"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasAMnemonicOfXUSA,
                                "The Historical Note Subheading should have a mnemonic of GNPC"),
                        () -> Assertions.assertFalse(USCAReferenceHeadingHasAPubtagOfNOPUB,
                                "The Historical Note Subheading should not have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(USCAReferenceHeadingHasADefaultSourceTag,
                                "The Historical Note Subheading should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceHeadingHasAModifiedByTag,
                                "The Historical Note Subheading should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceHeadingHasContentOfHeadingTextMarkup,
                                "The Historical Note Subheading should have content of heading text markup"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasAMnemonicOfCRL,
                                "The Historical Note Paragraph Quoted should have a mnemonic of GNPQ"),
                        () -> Assertions.assertFalse(USCAReferenceLineHasAPubtagOfNOPUB,
                                "The Historical Note Paragraph Quoted should not have a pub tag of NOPUB"),
                        () -> Assertions.assertFalse(USCAReferenceLineHasADefaultSourceTag,
                                "The Historical Note Paragraph Quoted should not have a default source tag"),
                        () -> Assertions.assertEquals(modifiedByTag, USCAReferenceLineHasAModifiedByTag,
                                "The Historical Note Paragraph Quoted should have a modified by tag"),
                        () -> Assertions.assertTrue(USCAReferenceLineHasContentOfUSCARef,
                                "The Historical Note Paragraph Quoted should have content of U.S.C.A Ref")
                );
    }
}
