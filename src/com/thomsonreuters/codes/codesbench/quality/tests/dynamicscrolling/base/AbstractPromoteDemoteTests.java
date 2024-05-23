package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPromoteDemoteTests extends TestService
{
    protected static final String LOADED_CHUNK_1 = format(LOADED_CHUNK, 1);

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

    public static Object[][] elementsToHighlight()
    {
        return new Object[][]
                {
                        {1},
                        {2}
                };
    }

//  ------------- Assistive methods: -------------

    protected void assertThatThereAreNoDemotedElements(String xPath)
    {
        assertThat(editorTextPage().countElements(xPath))
                .as("There should not be any demoted elements")
                .isEqualTo(0);
    }

    protected void assertThatThereAreNoPromotedElements(String xPath)
    {
        assertThat(editorTextPage().countElements(xPath))
                .as("There should not be any promoted elements")
                .isEqualTo(0);
    }

    protected void assertThatTheSpecificNumberOfDemotedElementsAppliedCorrectly(int elementsNumber, String xPath)
    {
        editorPage().switchDirectlyToTextFrame();
        assertThat(editorTextPage().countElements(xPath))
                .as(format("There should be %d demoted elements", elementsNumber))
                .isEqualTo(elementsNumber);
        editorPage().switchToEditor();
    }

    protected void assertThatTheSpecificNumberOfPromotedElementsAppliedCorrectly(int elementsNumber, String xPath)
    {
        editorPage().switchDirectlyToTextFrame();
        assertThat(editorTextPage().countElements(xPath))
                .as(format("There should be %d promoted elements", elementsNumber))
                .isEqualTo(elementsNumber);
        editorPage().switchToEditor();
    }

    protected void assertThatEditorTreeUpdatedCorrectlyAfterDemoting(int actualNumber, int numberToCheckWith)
    {
        assertThat(actualNumber)
                .as("The editor tree should be updated correctly after demoting")
                .isEqualTo(numberToCheckWith);
    }

    protected void assertThatEditorTreeUpdatedCorrectlyAfterPromoting(int actualNumber, int numberToCheckWith)
    {
        assertThat(actualNumber)
                .as("The editor tree should be updated correctly after promoting")
                .isEqualTo(numberToCheckWith);
    }

    protected void openContextMenuOnElement(String xPath)
    {
        editorTreePage().rightClick(xPath);
        editorTreePage().breakOutOfFrame();
    }

    protected void selectElementsInTheTree(String xPath, int number)
    {
        editorTreePage().click(xPath);
        editorTreePage().highlightFollowingSiblings(number);
        editorPage().switchDirectlyToTextFrame();
    }
}
