package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractDeleteSubsectionsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION_UNDER_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements.PAGE_TITLE;
import static java.lang.String.format;

public class DeleteSubsectionsTests extends AbstractDeleteSubsectionsTests
{
    private static final String UUID = "I25FDBDE08DDA11E390B986CE056828B6";

    @BeforeEach
    public void openSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    @AfterEach
    public void restoreRenditionBaseline()
    {
        closeDocument();
        pendingRenditionNavigatePage().switchToWindow(PAGE_TITLE);
        sourceNavigateGridPage().rightClickFirstRendition();
        sourceNavigateGridPage().breakOutOfFrame();
        sourceNavigateContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().switchToViewBaselinesPage();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("siblingsToHighlightNumber")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteSubsectionsSpanningChunksSourceLegalTest(int siblingsToHighlightNumber)
    {
        String subsection1 = format(SUBSECTION_UNDER_NUMBER, 1) + SPAN;
        selectSubsections(subsection1, siblingsToHighlightNumber);

        List<String> deletedContent = editorTextPage().getElementsTextList(HIGHLIGHTED_PARATEXT);
        deleteSubsections(subsection1);

        assertThatThereAreNoErrorsAndWarningsInTheEditorMessagePane();
        assertThatDeletedSubsectionsAreNoLongerAppearedInTheDocument(deletedContent);

        //Check-in source rendition
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextContainsAndAccept(true,
                "Document validation failed, continue with check-in?");
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Reopen source rendition
        pendingRenditionNavigatePage().switchToWindow(PAGE_TITLE);
        sourceNavigateGridPage().firstRenditionEditContent();

        assertThatDeletedSubsectionsAreNoLongerAppearedInTheDocument(deletedContent);
    }
}
