package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractEditorExpandTests;
import org.junit.jupiter.api.BeforeEach;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;

public class EditorExpandAndCollapseTests extends AbstractEditorExpandTests
{
    private static final String IOWA_TARGET_NODE_UUID = "I0090C62014F211DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openDocument()
    {
        //1. Open document (target)
        nodeName = "head.block";
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(IOWA_TARGET_NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    /**
     * HALCYONST-13765: Dynamic Scrolling: Dropping text at the end of the document
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void verifyEndOfDocumnetTest()
    {
        nodeName = "reference.text\"Theft, fraud, and related offenses, cons\"";
        editorPage().switchToEditor();
        editorPage().jumpToEndOfDocument();
        editorPage().switchToEditorTextFrame();
        assertThat(editorTextPage().doesElementExist(EditorTextPageElements.END_OF_DOCUMENT)).isTrue();
        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        editorTreePage().expandEditorsTreeAndClickNode(nodeName);
        boolean isEndofDocumnetvisible = editorTextPage()
                .doesElementExist(EditorTextPageElements.END_OF_DOCUMENT);
        Assertions.assertFalse(isEndofDocumnetvisible, "text is not Highlighted");
    }
}
