package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements.LOGIN_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class HotkeyNavigationLegalTests extends TestService {
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void ctrlHomeCtrlEndHotkeysTargetLegalTest() {

        String uuid = "I1B68F41059B211E2837CC3F6FE9FB3FE"; // rend id, this doc has 4 chunks
        String firstChunk = String.format(EditorTextPageElements.LOADED_CHUNK, 0) + "/*";

        int targetChunkNumber = 5; // we need to scroll to the middle chunk

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
        // scroll to the middle
        int chunkAmount = editorPage().getChunkAmount();
        String lastChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkAmount - 1) + "/*";

        editorPage().scrollToChunk(targetChunkNumber);
        editorPage().switchDirectlyToTextFrame();

        // we should expect first and last chunks not to be loaded before hitting hotkeys
        if (editorPage().doesElementExist(firstChunk))
        {
            editorPage().waitForElementGone(firstChunk);
        }
        if (editorPage().doesElementExist(lastChunk))
        {
            editorPage().waitForElementGone(lastChunk);
        }
        boolean firstAndLastChunkLoaded =
                editorPage().doesElementExist(firstChunk)
                        | editorPage().doesElementExist(lastChunk);

        // ctrl+end to jump to the end
        editorPage().jumpToEndOfDocument();
        boolean lastChunkLoaded = editorPage()
                .doesElementExist(lastChunk);

        // ctrl+home to jump to the beginning
        editorPage().sendKeys(Keys.HOME);
        editorPage().jumpToBeginningOfDocument();
        boolean firstChunkLoaded = editorPage().doesElementExist(firstChunk);

        // close editor
        editorTextPage().breakOutOfFrame();
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll("ctrlHomeCtrlEndHotkeysTargetLegalTest",
                () -> Assertions.assertFalse(firstAndLastChunkLoaded, "Need first and last chunks not to be loaded before hitting hotkeys. Check chunk amount and target chunk!"),
                () -> Assertions.assertTrue(lastChunkLoaded, "Last chunk should be loaded after hitting Ctrl+End"),
                () -> Assertions.assertTrue(firstChunkLoaded, "First chunk should be loaded after hitting Ctrl+Home")
        );
    }

    /*
    ADO 353434: Dynamic Scrolling - Hotkey remapping for Ctrl-W and Ctrl-T
    */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void ctrlWCtrlTHotkeysTest() {
        String uuid = "I1B68F41059B211E2837CC3F6FE9FB3FE";
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();
        //To open new window
        editorTextPage().ctrlTUsingAction();
        boolean windowOpened = editorPage().switchToWindowWithNoTitle(2);
        editorPage().switchToWindowWithNoTitle(1);

        //To close the current window
        editorTextPage().ctrlWUsingAction();
        boolean windowClosed = editorPage().waitForElementGone(EditorToolbarPageElements.CLOSE_DOC);
        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(windowOpened, "There is no New tab"),
                        () -> Assertions.assertTrue(windowClosed, "Current window  not closed")
                );
    }
}
