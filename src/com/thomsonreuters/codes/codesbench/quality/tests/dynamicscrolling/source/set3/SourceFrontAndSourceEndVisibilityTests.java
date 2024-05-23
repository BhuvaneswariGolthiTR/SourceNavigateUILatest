package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SourceFrontAndSourceEndVisibilityTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceFrontAndSourceEndVisibleByDefaultInAPVLegalTest()
    {
        String uuid = "I23E849F066F611E2AD95FB167AA1A73D";
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // check if SE is visible initially
        editorTextPage().switchToEditorTextArea();
        boolean sourceFrontIsVisibleInitially = editorTextPage().checkVisibilityOfNodeWithGivenEnglishLabel(
                EditorTextPageElements.SOURCE_FRONT_HIDDEN_CHILDREN, true);

        // check if SE is visible initially
        boolean sourceEndIsVisibleInitially = editorTextPage().checkVisibilityOfNodeWithGivenEnglishLabel(
                EditorTextPageElements.SOURCE_END_HIDDEN_CHILDREN, true);

        // hide SF and check SF is not visible
        editorTextPage().click(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();
        boolean sourceFrontIsNotVisibleAfterHiding = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_FRONT_HIDDEN_CHILDREN,
                        false);

        // show SF and check SF is visible
        editorTextPage().click(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();
        boolean sourceFrontIsVisibleAfterShowing = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_FRONT_HIDDEN_CHILDREN,
                        true);
        editorTextPage().breakOutOfFrame();
        editorPage().scrollToChunk(editorPage().getChunkAmount());

        // hide SE and check SE is not visible
        editorTextPage().click(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();
        boolean sourceEndIsNotVisibleAfterHiding = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_END_HIDDEN_CHILDREN,
                        false);

        // show SE and check SE is visible
        editorTextPage().click(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();
        boolean sourceEndIsVisibleAfterShowing = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_END_HIDDEN_CHILDREN,
                        true);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(sourceFrontIsVisibleInitially,
                        "Source Front should not be hidden by default"),
                () -> Assertions.assertTrue(sourceEndIsVisibleInitially,
                        "Source End should not be hidden by default"),
                () -> Assertions.assertTrue(sourceFrontIsNotVisibleAfterHiding,
                        "Source Front should be hidden after toggled hidden"),
                () -> Assertions.assertTrue(sourceFrontIsVisibleAfterShowing,
                        "Source Front should be shown after toggled hidden"),
                () -> Assertions.assertTrue(sourceEndIsNotVisibleAfterHiding,
                        "Source End should be hidden after toggled hidden"),
                () -> Assertions.assertTrue(sourceEndIsVisibleAfterShowing,
                        "Source End should be shown after toggled hidden")
        );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceFrontAndSourceEndVisibilityInPREPLegalTest()
    {
        String uuid = "I30487110714811EBA0B4D34B1DA9F369";
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorTextPage().switchToEditorTextArea();

        // check if SF is hidden initially
        boolean sourceFrontIsHiddenInitially = editorTextPage().checkVisibilityOfNodeWithGivenEnglishLabel(
                EditorTextPageElements.SOURCE_FRONT_HIDDEN_CHILDREN, false);

        // show SF and check SF is visible
        editorTextPage().click(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();

        boolean sourceFrontIsVisibleAfterShowHide = editorTextPage().checkVisibilityOfNodeWithGivenEnglishLabel(
                EditorTextPageElements.SOURCE_FRONT_HIDDEN_CHILDREN, true);

        editorTextPage().breakOutOfFrame();
        editorPage().scrollToChunk(editorPage().getChunkAmount());

        boolean sourceEndIsNotVisibleInitially = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_END_HIDDEN_CHILDREN,
                        false);

        editorTextPage().click(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_END_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.SHOW_HIDE);
        editorTextPage().switchToEditorTextArea();

        boolean sourceEndIsVisibleAfterShowHide = editorTextPage()
                .checkVisibilityOfNodeWithGivenEnglishLabel(EditorTextPageElements.SOURCE_END_HIDDEN_CHILDREN,
                        true);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(sourceFrontIsHiddenInitially,
                                "Source Front should be hidden by default"),
                        () -> Assertions.assertTrue(sourceFrontIsVisibleAfterShowHide,
                                "Source Front should not be hidden after toggled Show"),
                        () -> Assertions.assertTrue(sourceEndIsNotVisibleInitially,
                                "Source End should not be visible by default"),
                        () -> Assertions.assertTrue(sourceEndIsVisibleAfterShowHide,
                                "Source End should not be hidden after toggled Show")
                );
    }
}
