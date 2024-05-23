package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

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
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;

public class DeleteSubsectionsTests extends AbstractDeleteSubsectionsTests
{
    private static final String UUID = "I9B656A50157611DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    @AfterEach
    public void restoreTargetNodeOriginalWipVersion()
    {
        closeDocument();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataPage().breakOutOfFrame();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().restoreOriginalWIPVersion();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("siblingsToHighlightNumber")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteSubsectionsSpanningChunksTargetLegalTest(int siblingsToHighlightNumber)
    {
        String subsection11 = format(SUBSECTION_UNDER_NUMBER, 11) + SPAN;
        selectSubsections(subsection11, siblingsToHighlightNumber);

        List<String> deletedContent = editorTextPage().getElementsTextList(HIGHLIGHTED_PARATEXT);
        deleteSubsections(subsection11);

        assertThatThereAreNoErrorsAndWarningsInTheEditorMessagePane();
        assertThatDeletedSubsectionsAreNoLongerAppearedInTheDocument(deletedContent);

        //Check-in target node
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextContainsAndAccept(true,
                "Document validation failed, continue with check-in?");
        AutoITUtils.verifyAlertTextAndAccept(true,
                    "Subsection validation failed, continue with check-in?");
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Reopen target node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        assertThatDeletedSubsectionsAreNoLongerAppearedInTheDocument(deletedContent);
    }
}
