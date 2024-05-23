package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

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
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;

public class InteractionsWithMarkupsViaContextMenuTargetTests extends AbstractInteractionsWithMarkupsViaContextMenuTests
{
    private static final String IOWA_TARGET_NODE_UUID = "I97E80F6014F211DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openTargetNodeDocumentWithUuidInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(IOWA_TARGET_NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndDeleteReferenceTest()
    {
        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        editorTextPage().sendKeys(TEST_PHRASE);

        insertFootnoteInTextAndPerformAction(DELETE);

        softAssertThatBothTextAndFootnoteReferencesAreRemoved();
        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndCutPasteReferenceTest()
    {
        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        editorTextPage().sendKeys(TEST_PHRASE);

        insertFootnoteInTextAndPerformAction(CUT_CTRL_X);

        softAssertThatBothTextAndFootnoteReferencesAreRemoved();
        assertThatMarkupsAndTextAreCopiedToClipboard();

        openContextMenuAndSelectAction(PARATEXT_SPAN, PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted(SUPER, FOOTNOTE_REF_VALUE);

        softAssertions.assertAll();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnoteAndCopyPasteReferenceTest()
    {
        editorPage().switchDirectlyToTextFrame();
        placeCursorBeforeTextOfSpecifiedTextParagraph(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
        editorTextPage().sendKeys(TEST_PHRASE);

        insertFootnoteInTextAndPerformAction(COPY_CTRL_C);

        assertThatTextAndMarkupsStayInTextAfterCopying(FOOTNOTE_REFERENCE_SUPER, FOOTNOTE_REF_VALUE);
        assertThatMarkupsAndTextAreCopiedToClipboard();

        openContextMenuAndSelectAction(format(TEXT_PARAGRAPH_PARA_UNDER_NUMBER_GIVEN, 1), PASTE_CTRL_V);

        assertThatBothTextAndMarkupsArePasted(format(MARKUP_UNDER_NUMBER, SUPER.substring(2), 2), FOOTNOTE_REF_VALUE);

        assertThatCopiedTextIsEqualToPastedText(SUPER.substring(2), FOOTNOTE_REF_VALUE);
    }

    // ---------- Assistive Methods ----------

    private void insertFootnoteInTextAndPerformAction(String action)
    {
        insertFootnoteWithReferenceValue();
        assertThatFootnoteAndReferenceAreInsertedCorrectly(TEXT_PARAGRAPH_PARATEXT, FOOTNOTE_UNDER_CREDIT);

        openContextMenuAndSelectAction(
                FOOTNOTE + SPAN + format(CONTAINS_TEXT_POSTFIX, FOOTNOTE.substring(2)),
                FIND_FOOTNOTES_REFERENCES
        );
        assertThatReferenceValuesInTextAndFootnoteAreEqual(
                FOOTNOTE_REFERENCE_SUPER,
                FOOTNOTE + PARA + PARATEXT + SUPER);
        assertThatReferenceMarkupsAreHighlightedAfterPressingFindFootnotesReferences(FOOTNOTE_REFERENCE);

        openContextMenuAndSelectAction(FOOTNOTE_REFERENCE_MARKUP, action);
    }

    private void softAssertThatBothTextAndFootnoteReferencesAreRemoved()
    {
        softAssertThatMarkupIsGone(FOOTNOTE_REFERENCE, "Footnote Reference");
        softAssertThatTextIsGone(format(TEXT_IN_MARKUPS, FOOTNOTE_REF_VALUE));
    }
}
