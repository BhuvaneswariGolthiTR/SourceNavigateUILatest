package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.PrintPreviewPageElements.PRINT_PREVIEW_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.SPAN_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725256: CWB Index UI Redesign - Session Law Box [HALCYONST-17482]
 * User Story 723605: Implement button to create print preview from Indexing
 */
public class PrintPreviewIndexingTests extends IndexingUiBase
{
    @BeforeEach
    public void accessIndexingUi()
    {
        findSingleApvRenditionForIndexing(UUID_NO_SUGGESTIONS);
        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("Indexing UI Session Law Box: Print Preview")
    @EDGE
    @LEGAL
    @LOG
    public void verifyPrintPreviewWorkflowStartedFromIndexingUiTest()
    {
        assertThatSeeMoreLinkIsPresent();
        indexingPage().clickPrintPreviewButton();

        String workflowId = sourceNavigateAngularPopUpPage().getWorkflowId();
        assertThat(sourceNavigateAngularPopUpPage().doesElementExist(format(WORKFLOW_ID_HYPERLINK_PATTERN, workflowId)))
                .as("The workflow ID hyperlink should be present in the \"Print Preview\" UI")
                .isTrue();

        sourceNavigateAngularPopUpPage().openWorkflowDetailsPage();
        workflowDetailsPage().waitTillWorkflowIsFinished();

        assertThat(workflowDetailsPage().doesElementExist(format(SPAN_TEXT, workflowId)))
                .as("The workflow ID should be equal to %s", workflowId)
                .isTrue();
        assertThat(workflowDetailsPage().doesElementExist(format(SPAN_TEXT, "CwbTabularPrintPreview")))
                .as("The workflow name should be CwbTabularPrintPreview")
                        .isTrue();

        workflowDetailsPage().expandWorkflowProperties();
        assertThat(workflowPropertiesPage().getWorkflowPropertyValueByName("renditionUuids"))
                .as("The rendition UUID should be equal to %s", UUID_NO_SUGGESTIONS)
                .isEqualTo(UUID_NO_SUGGESTIONS);

        workflowDetailsPage().closeWorkflowDetailPageAndSwitchToSourceNavigate();
        sourceNavigateAngularPopUpPage().closeUi(PRINT_PREVIEW_PAGE_TITLE);
    }
}
