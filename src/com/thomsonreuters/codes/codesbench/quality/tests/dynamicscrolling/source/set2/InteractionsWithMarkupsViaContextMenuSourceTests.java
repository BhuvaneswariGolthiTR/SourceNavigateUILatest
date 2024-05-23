package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractInteractionsWithMarkupsViaContextMenuTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static java.lang.String.format;

public class InteractionsWithMarkupsViaContextMenuSourceTests extends AbstractInteractionsWithMarkupsViaContextMenuTests
{
    private static final String IOWA_SOURCE_RENDITION_UUID = "I9EDFDDD01C3411EAACEADA33E1A31C61";
    private static final String DELTA_PART_SUBSECTION_PARA = DELTA_AMEND_SECTION + "/part[%s]/subsection" + PARA;
    private static final String DELTA_FEATURE_FOOTNOTE = DELTA_FEATURE + FOOTNOTE.substring(1);
    private static final String DELTA_PARATEXT = format(DELTA_PART_SUBSECTION_PARA, "%s") + PARATEXT;

    @BeforeEach
    public void openSourceRenditionWithUuidInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndDeleteReferenceTest()
    {
        scrollToChunkInsertFootnoteInTextAndPerformAction(DELETE);

        softAssertThatBothTextAndFootnoteReferencesAreRemoved();
        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndCutPasteReferenceTest()
    {
        scrollToChunkInsertFootnoteInTextAndPerformAction(CUT_CTRL_X);

        softAssertThatBothTextAndFootnoteReferencesAreRemoved();
        assertThatMarkupsAndTextAreCopiedToClipboard();

        openContextMenuAndSelectAction(format(DELTA_PART_SUBSECTION_PARA, 3) + PARATEXT, PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted(SUPER, FOOTNOTE_REF_VALUE);

        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndCopyPasteReferenceTest()
    {
        scrollToChunkInsertFootnoteInTextAndPerformAction(COPY_CTRL_C);

        assertThatTextAndMarkupsStayInTextAfterCopying(FOOTNOTE_REFERENCE_SUPER, FOOTNOTE_REF_VALUE);
        assertThatMarkupsAndTextAreCopiedToClipboard();

        openContextMenuAndSelectAction(format(DELTA_PARATEXT, 3), PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted(format(MARKUP_UNDER_NUMBER, SUPER.substring(2), 2), FOOTNOTE_REF_VALUE);

        assertThatCopiedTextIsEqualToPastedText(SUPER.substring(2), FOOTNOTE_REF_VALUE);
    }

    // ---------- Assistive Methods ----------

    private void scrollToChunkInsertFootnoteInTextAndPerformAction(String action)
    {
        editorPage().scrollToChunk(5);
        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(DELTA_PART_SUBSECTION_PARA, 3) +
                TEXT_PARAGRAPH_LABEL);
        editorTextPage().sendKeys(TEST_PHRASE);

        insertFootnoteWithReferenceValue();
        assertThatFootnoteAndReferenceAreInsertedCorrectly(format(DELTA_PARATEXT, 3), DELTA_FEATURE_FOOTNOTE);

        openContextMenuAndSelectAction(
                DELTA_FEATURE_FOOTNOTE + SPAN + format(CONTAINS_TEXT_POSTFIX, FOOTNOTE.substring(3)),
                FIND_FOOTNOTES_REFERENCES
        );
        assertThatReferenceValuesInTextAndFootnoteAreEqual(
                format(DELTA_PARATEXT, 3) + FOOTNOTE_REFERENCE_SUPER,
                DELTA_FEATURE_FOOTNOTE + PARA + PARATEXT + SUPER);
        assertThatReferenceMarkupsAreHighlightedAfterPressingFindFootnotesReferences(
                format(DELTA_PARATEXT, 3) + FOOTNOTE_REFERENCE
        );

        openContextMenuAndSelectAction(FOOTNOTE_REFERENCE_MARKUP, action);
    }

    private void softAssertThatBothTextAndFootnoteReferencesAreRemoved()
    {
        String referenceValue = format(TEXT_EQUALS_PATTERN, FOOTNOTE_REF_VALUE);
        softAssertThatMarkupIsGone(format(DELTA_PARATEXT, 3) +
                        FOOTNOTE_REFERENCE_SUPER +
                        MARKUP_IMAGE_LABEL +
                        referenceValue,
                "Footnote Reference");
        softAssertThatTextIsGone(format(DELTA_PARATEXT, 3) + referenceValue);
    }
}
