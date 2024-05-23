package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractEditorCopyAndPasteTests;
import org.junit.jupiter.api.BeforeEach;

public class EditorTreeCopyAndPasteTests extends AbstractEditorCopyAndPasteTests
{
    private static final String IOWA_SOURCE_RENDITION_UUID = "I71763170ADD611EBB5CE8C2E43B783F3";

    @BeforeEach
    public void openDocument()
    {
        //1. Open document (source)
        nodesToExpand = new String[]{"source.body"};
        firstSourceNode = "source.section \"1\"";
        secondSourceNode = "source.section \"2\"";
        destinationNode = "source.section \"5\"";
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }
}
