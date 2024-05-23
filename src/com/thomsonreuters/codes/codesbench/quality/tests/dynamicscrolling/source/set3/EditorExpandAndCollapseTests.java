package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractEditorExpandTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EditorExpandAndCollapseTests extends AbstractEditorExpandTests
{
    private static final String IOWA_SOURCE_RENDITION_UUID = "I71763170ADD611EBB5CE8C2E43B783F3";

    @BeforeEach
    public void openDocument()
    {
        //1. Open document (source)
        nodeName = "source.front";
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    /**
     * HALCYONST-13765: Dynamic Scrolling: Dropping text at the end of the document
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void verifyEndOfDocumentTest()
    {
        String nodesToExpand = "source.end";
        editorPage().jumpToEndOfDocument();
        editorPage().switchToEditorTextFrame();
        //Verify End of document
        Assertions.assertTrue(editorTextPage().doesElementExist(EditorTextPageElements.END_OF_DOCUMENT), "END OF DOCUMENT is found");
        editorPage().switchToEditor();
        //Verify soure end
        editorTreePage().expandEditorsTreeAndClickNode(nodesToExpand);
        boolean isEndofDocumnetvisible = editorTextPage().doesElementExist(EditorTextPageElements.END_OF_DOCUMENT);
        Assertions.assertFalse(isEndofDocumnetvisible, "text is not Highlighted");
    }
}
