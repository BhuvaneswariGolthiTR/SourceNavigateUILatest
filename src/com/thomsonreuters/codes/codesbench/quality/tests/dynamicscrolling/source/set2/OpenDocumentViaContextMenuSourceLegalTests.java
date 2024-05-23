package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class OpenDocumentViaContextMenuSourceLegalTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openDocumentViaContextMenuSourceLegalTest()
    {
        int targetChunk = 2;
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        //String uuid = "I46CD65F0F48511E2947EC10AE072603D"; // for dev
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        // check tree, textarea, message pane
        boolean treeContentIsPresented = editorPage()
                .doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage()
                .doesElementExist(EditorMessagePageElements.INITIAL_LOAD_MESSAGE, 10);
        // scroll
        editorPage().scrollToChunk(targetChunk);
        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS);
        // close
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertAll(
                () -> Assertions.assertTrue(treeContentIsPresented, "Tree should present some content"),
                () -> Assertions.assertTrue(messagePaneContentIsPresented, "Message Pane should present some content"),
                () -> Assertions.assertTrue(textAreaContentIsPresented, "Text Area should present some content")
        );
    }
}
