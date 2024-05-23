package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CODES_HEAD;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PLACEHOLDER;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class PreviewTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-2515 <br>
     * SUMMARY - Preview with nothing selected in the tree (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void previewWithNothingSelectedInTheTreeTargetLegalTest()
    {
        String uuid = "I7A35F76014EE11DA8AC5CD53670E6B4E";
        int chunkNumber = 2;

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().preview();
        int countPreviewElements = documentPreviewPage().countElements(DocumentPreviewPageElements.CODES_HEAD)
                + documentPreviewPage().countElements(DocumentPreviewPageElements.PLACEHOLDER)
                + documentPreviewPage().countElements(DocumentPreviewPageElements.PARA);
        documentPreviewPage().closePreview();

        editorPage().switchDirectlyToTextFrame();
        int countEditorElements = editorTextPage().countElements(CODES_HEAD) + editorTextPage().countElements(PLACEHOLDER);
        editorPage().switchToEditorAndScrollToChunk(chunkNumber);
        countEditorElements += editorTextPage().countElements(PARA);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertEquals(countEditorElements, countPreviewElements, "Actual number of elements didn't match expected");
    }
}
