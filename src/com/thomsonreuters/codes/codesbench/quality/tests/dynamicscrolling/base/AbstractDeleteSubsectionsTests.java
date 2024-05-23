package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDeleteSubsectionsTests extends TestService
{
    protected static final String HIGHLIGHTED_PARATEXT = PARATEXT + CLASS_HIGHLIGHTED_POSTFIX;
    protected static final String SPAN = "/span";

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    public static Object[][] siblingsToHighlightNumber()
    {
        return new Object[][]
                {
                        {0},
                        {1}
                };
    }

//  ------------- Assistive methods: -------------

    protected void closeDocument()
    {
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();
    }

    protected void selectSubsections(String xPath, int number)
    {
        editorPage().scrollToChunk(1);
        editorTextPage().click(xPath);
        editorTextPage().waitForElement(HIGHLIGHTED_PARATEXT);
        editorTreePage().highlightFollowingSiblings(number);
    }

    protected void deleteSubsections(String xPath)
    {
        editorTextPage().scrollToView(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();
    }

    protected void assertThatThereAreNoErrorsAndWarningsInTheEditorMessagePane()
    {
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no any errors in the editor message pane")
                .isFalse();
        assertThat(editorMessagePage().checkForWarningInMessagePane())
                .as("There should be no any warnings in the editor message pane")
                .isFalse();
    }

    protected void assertThatDeletedSubsectionsAreNoLongerAppearedInTheDocument(List<String> deletedSubsections)
    {
        editorPage().scrollToChunk(2);
        List<String> existingSubsectionsContent = editorTextPage().getElementsTextList(SUBSECTION + PARA + PARATEXT);
        deletedSubsections.forEach(deletedSubsection ->
                assertThat(existingSubsectionsContent.stream().anyMatch(existingSubsection ->
                        existingSubsection.equals(deletedSubsection)))
                        .as("Deleted subsection(s) should not be appeared in the document")
                        .isFalse());
    }
}
