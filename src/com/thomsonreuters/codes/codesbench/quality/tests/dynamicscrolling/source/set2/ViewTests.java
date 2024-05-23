package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ViewTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewLegalTest()
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRendition();

        editorPage().switchToEditorTextFrame();
        boolean readOnly = editorTextPage().isElementReadOnly(EditorTextPageElements.BODY_TAG);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(readOnly, "Editor should be in Read-only mode")
                );
    }
}
