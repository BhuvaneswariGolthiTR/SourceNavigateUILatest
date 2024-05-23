package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class OfficialNoteBlockTests extends TestService
{
    private static final String ADMIN_CODE_REFERENCE_BLOCK_ASSERTION_MESSAGE = "Admin Code Reference Block wasn't pasted as sibling";
    private static final String ERRORS_IN_MESSAGE_PANE_ASSERTION_MESSAGE = "There are errors in Message pane";
    private static final String WARNINGS_IN_MESSAGE_PANE_ASSERTION_MESSAGE = "There are warnings in Message pane";

    /**
     * STORY/BUG - HALCYONST-8899 <br>
     * SUMMARY - Dynamic Scrolling: Official Note blocks copy and paste <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void officialNoteBlocksCopyAndPasteTargetLegalTest()
    {
        String primaryUUID = "I9DB27A40AC5911DE828DFD19DEB34D83";
        String secondaryUUID = "I9DA3AD30AC5911DE828DFD19DEB34D83";
        int chunkNumber = 2;

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.MONTANA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.MONTANA_DEVELOPMENT);
        hierarchyTreePage().setDateOfTree("05/31/2023");
        hierarchySearchPage().searchNodeUuid(primaryUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        String adminCodeReferenceBlockXpath = EditorTextPageElements.ANNOTATIONS
                + EditorTextPageElements.ADMINISTRATIVE_CODE_REFERENCE_BLOCK + EditorTextPageElements.SPAN;
        editorTextPage().click(adminCodeReferenceBlockXpath);
        editorTextPage().rightClick(adminCodeReferenceBlockXpath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchToEditorTextFrame();

        String followingSiblingXpath = String.format(EditorTextPageElements.FOLLOWING_SIBLING, "admin.code.reference.block");
        String historicalNoteBlockSiblingXpath = EditorTextPageElements.ANNOTATIONS
                + EditorTextPageElements.HISTORICAL_NOTE_BLOCK + followingSiblingXpath;

        int adminCodeReferenceBlockSizeBeforePastingPrimary = editorTextPage().getElements(historicalNoteBlockSiblingXpath).size();

        String historicalNoteBlockXpath = EditorTextPageElements.ANNOTATIONS
                + EditorTextPageElements.HISTORICAL_NOTE_BLOCK + EditorTextPageElements.SPAN;
        editorTextPage().click(historicalNoteBlockXpath);
        editorTextPage().rightClick(historicalNoteBlockXpath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToEditorTextFrame();

        int adminCodeReferenceBlockSizeAfterPastingPrimary = editorTextPage().getElements(historicalNoteBlockSiblingXpath).size();

        editorPage().switchToEditor();
        boolean areAnyErrorsAppearInMessagePanePrimary = editorMessagePage().checkForErrorInMessagePane();
        boolean areAnyWarningsAppearInMessagePanePrimary = editorMessagePage().checkForWarningInMessagePane();

        editorPage().closeDocumentAndDiscardChanges();
        editorPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().closeCurrentWindowIgnoreDialogue();

        editorPage().switchToWindow(HierarchyEditPageElements.HIERARCHY_EDIT_PAGE_TITLE);
        hierarchyTreePage().setDateOfTree("05/31/2020");
        hierarchySearchPage().searchNodeUuid(secondaryUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        String crossReferenceBlockSiblingXpath = EditorTextPageElements.ANNOTATIONS
                + EditorTextPageElements.CROSS_REFERENCE_BLOCK + followingSiblingXpath;

        int adminCodeReferenceBlockSizeBeforePastingSecondary = editorTextPage().getElements(crossReferenceBlockSiblingXpath).size();

        String crossReferenceBlockXpath = EditorTextPageElements.ANNOTATIONS
                + EditorTextPageElements.CROSS_REFERENCE_BLOCK + EditorTextPageElements.SPAN;
        editorTextPage().click(crossReferenceBlockXpath);
        editorTextPage().rightClick(crossReferenceBlockXpath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToEditorTextFrame();

        int adminCodeReferenceBlockSizeAfterPastingSecondary = editorTextPage().getElements(crossReferenceBlockSiblingXpath).size();

        editorPage().switchToEditor();
        boolean areAnyErrorsAppearInMessagePaneSecondary = editorMessagePage().checkForErrorInMessagePane();
        boolean areAnyWarningsAppearInMessagePaneSecondary = editorMessagePage().checkForWarningInMessagePane();

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(adminCodeReferenceBlockSizeAfterPastingPrimary, adminCodeReferenceBlockSizeBeforePastingPrimary,
                        ADMIN_CODE_REFERENCE_BLOCK_ASSERTION_MESSAGE),
                () -> Assertions.assertFalse(areAnyErrorsAppearInMessagePanePrimary, ERRORS_IN_MESSAGE_PANE_ASSERTION_MESSAGE),
                () -> Assertions.assertFalse(areAnyWarningsAppearInMessagePanePrimary, WARNINGS_IN_MESSAGE_PANE_ASSERTION_MESSAGE),
                () -> Assertions.assertNotEquals(adminCodeReferenceBlockSizeAfterPastingSecondary, adminCodeReferenceBlockSizeBeforePastingSecondary,
                        ADMIN_CODE_REFERENCE_BLOCK_ASSERTION_MESSAGE),
                () -> Assertions.assertFalse(areAnyErrorsAppearInMessagePaneSecondary, ERRORS_IN_MESSAGE_PANE_ASSERTION_MESSAGE),
                () -> Assertions.assertFalse(areAnyWarningsAppearInMessagePaneSecondary, WARNINGS_IN_MESSAGE_PANE_ASSERTION_MESSAGE)
        );
    }
}
