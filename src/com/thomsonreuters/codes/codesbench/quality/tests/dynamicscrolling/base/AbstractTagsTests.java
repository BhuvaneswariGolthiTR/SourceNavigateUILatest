package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION_SPAN_UNDER_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public abstract class AbstractTagsTests extends TestService
{
    private static final String TARGET_NODE_UUID = "I26A3FBB0139811DCAF1D99E7A2F55F85";
    protected static final String HIGHLIGHTED_PARA = PARA + CLASS_HIGHLIGHTED_POSTFIX;

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

    protected void selectSubsectionElementsUnder(int subsectionNumber)
    {
        editorTextPage().click(String.format(SUBSECTION_SPAN_UNDER_NUMBER, subsectionNumber));
        editorTreePage().highlightFollowingSiblings(3);
    }

    protected int countHighlightedParagraphs()
    {
        return editorTextPage().countElements(HIGHLIGHTED_PARA);
    }

    protected void softAssertThatAllChangesMadeSuccessfully()
    {
        SoftAssertions softAssertions = new SoftAssertions();

        editorPage().switchDirectlyToTextFrame();
        int selectedParagraphs = countHighlightedParagraphs();
        softAssertions.assertThat(editorTextPage().countElements(editorTextPage().getModifiedByXpath(user())))
                .as("Each selected paragraph should include 'modified.by' tag")
                .isEqualTo(selectedParagraphs);

        editorPage().switchToEditor();
        softAssertions.assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();

        softAssertions.assertThat(editorToolbarPage().isToolbarUndoButtonDisabled())
                .as("The toolbar Undo button should be enabled")
                .isFalse();

        softAssertions.assertAll();
    }

    protected void openTargetNodeInDsEditorAndSwitchToEditorTextFrame()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(TARGET_NODE_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
    }
}
