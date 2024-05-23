package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HEADTEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HINT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHintTagTests extends TestService
{
    protected static final String TEXT = " TEST %s";

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

//  ------------- Assistive methods: -------------

    protected void openContextMenuOnElement(String xPath)
    {
        editorTextPage().click(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
    }

    protected void deleteElement(String xPath)
    {
        openContextMenuOnElement(xPath + SPAN);
        editorTextContextMenu().delete();
    }

    protected void assertThatInformationPlacedInHintTagsIsSaved(String xPath, String textToCheck)
    {
        assertThat(editorTextPage().getElementsText(xPath + HEADTEXT))
                .as("Information placed in hint tags should be saved after check-in")
                .contains(textToCheck);
    }

    protected void insertTextToElementHint(String textToInsert, String xPath)
    {
        editorTextPage().click(xPath + HINT);
        editorTextPage().sendKeys(textToInsert);
    }
}
