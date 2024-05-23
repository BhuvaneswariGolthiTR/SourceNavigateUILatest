package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPasteChildTests extends TestService
{
    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

//  ------------- Assistive methods: -------------

    private void openContextMenuOnElement(String xPath)
    {
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
    }

    protected void copy(String xPath)
    {
        openContextMenuOnElement(xPath);
        editorTextContextMenu().copyCtrlC();
    }

    protected void pasteAsChild(String xPath)
    {
        openContextMenuOnElement(xPath);
        editorTextContextMenu().pasteChildCtrlAltV();
    }

    protected int countElements(String xPath)
    {
        return editorPage().countElements(xPath);
    }

    protected void assertThatThereAreNoErrorsInTheEditorMessagePane()
    {
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();
    }
}
