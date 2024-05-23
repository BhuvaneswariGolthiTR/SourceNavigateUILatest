package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertHeadingNameLineTests extends TestService
{
    /*Insert Heading Nameline SRNL
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Text Heading -> Jurisdicitonal Text Headings -> Heading - Nameline -> srnl
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
    public void insertHeadingNameLineSRNLTest()
    {
        String uuid = "I12AD46C0A74111EAA9A8E4A1EA1EA4AE";
        int chunkNumber = 1;
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        editorTextPage().click(EditorTextPageElements.CORNERPIECE);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertTextSiblingTextHeadingJurisdictionalTextHeadingNamelineSrnl();

        editorTextPage().switchToEditorTextArea();
        boolean nonLiveSectionNamelineExists = editorTextPage().doesElementExist(EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT);
        boolean hasamnemonicofSRNL = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.mnemonics.SRNL.xpath() );
        boolean hasapubtagofNOPUB = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean hasadefaultsourcetag = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.SOURCE_TAG);
        String modifiedByActual = editorTextPage().getElementsText( EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT+EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean hascontentofheadingtextmarkup = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_SECTION_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        editorPage().breakOutOfFrame();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        ()->Assertions.assertTrue(nonLiveSectionNamelineExists, "Non-Live Section Nameline should be inserted below the cornerpiece and highlighted"),
                        ()->Assertions.assertTrue(hasamnemonicofSRNL, "The Non-Live Section Nameline should have a mnemonic of SRNL"),
                        ()->Assertions.assertFalse(hasapubtagofNOPUB, "The Non-Live Section Nameline should not have a pub tag of NOPUB"),
                        ()->Assertions.assertFalse(hasadefaultsourcetag, "The Non-Live Section Nameline should not have a default source tag"),
                        ()->Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        ()->Assertions.assertTrue(hascontentofheadingtextmarkup, "The Non-Live Section Nameline should have content of heading text markup")
                );
    }


    /*Insert Heading Nameline SRNL
     * 1. Open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Text (sibling) -> Text Heading -> Jurisdicitonal Text Headings -> Heading - Nameline -> rrnl
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
    public void insertHeadingNameLineRRNLTest()
    {
        String uuid = "I12AD46C0A74111EAA9A8E4A1EA1EA4AE";
        int chunkNumber = 1;
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        editorTextPage().click(EditorTextPageElements.CORNERPIECE);
        editorTextPage().rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertTextSiblingTextHeadingJurisdictionalTextHeadingNamelineRrnl();

        editorTextPage().switchToEditorTextArea();
        boolean nonLiveSectionNamelineExists = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT );
        boolean hasamnemonicofSRNL = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.mnemonics.RRNL.xpath() );
        boolean hasapubtagofNOPUB = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.NOPUB_MNEMONIC);
        boolean hasadefaultsourcetag = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.SOURCE_TAG);
        String modifiedByActual = editorTextPage().getElementsText( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT+EditorTextPageElements.MODIFIED_BY_MNEMONIC);
        boolean hascontentofheadingtextmarkup = editorTextPage().doesElementExist( EditorTextPageElements.NON_LIVE_RULE_NAMELINE_HIGHLIGHT +
                EditorTextPageElements.HEADING_TEXT_LABEL);

        editorPage().breakOutOfFrame();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        ()->Assertions.assertTrue(nonLiveSectionNamelineExists, "Non-Live Rule Nameline should be inserted below the cornerpiece and highlighted"),
                        ()->Assertions.assertTrue(hasamnemonicofSRNL, "The Non-Live Rule Namelinee should have a mnemonic of RRNL"),
                        ()->Assertions.assertFalse(hasapubtagofNOPUB, "The Non-Live Rule Nameline should not have a pub tag of NOPUB"),
                        ()->Assertions.assertFalse(hasadefaultsourcetag, "The Non-Live Rule Nameline should not have a default source tag"),
                        ()->Assertions.assertEquals(modifiedByExpected, modifiedByActual, String.format("A modified by tag is '%s' instead of '%s'",modifiedByExpected,modifiedByActual)),
                        ()->Assertions.assertTrue(hascontentofheadingtextmarkup, "The Non-Live Rule Nameline should have content of heading text markup")
                );
    }
}
