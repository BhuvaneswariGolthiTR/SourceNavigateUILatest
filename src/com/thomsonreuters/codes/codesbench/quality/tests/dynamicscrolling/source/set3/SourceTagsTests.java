package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceTagsTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dynamicScrollingSourcetagsSourceLegalTest()
    {
        String uuid = "I96B00550089B11E68A36EF7DB9569661";
        //String correlationID = "I0AA1D37134FC11E7ACDACFC8F254A8C8";
        int targetChunkNumber = 1;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().switchToEditor();
        editorPage().scrollToChunk(targetChunkNumber);

        boolean sourceTagExists = editorTextPage().checkForExistanceOfSourceTag();
        Assertions.assertFalse(sourceTagExists, "There should be no source tags in a source document");

        // close editor
        editorPage().closeDocumentWithNoChanges();
    }
}