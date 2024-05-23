package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION_UNDER_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.TEXAS_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.ENSP;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.THINSP;
import static org.assertj.core.api.Assertions.assertThat;

public class CopyAndPastingSpecialCharactersTests extends TestService
{
    private static final String TEXAS_NODE_UUID = "I18F8FFE0571911E7B505AC77B1646E6B";
    private static final String HIGHLIGHTED_BACKGROUND_COLOR = "rgba(49, 106, 197, 1)";
    private static final String PARA_XPATH = LOADED_CHUNK + SUBSECTION_UNDER_NUMBER + PARA;
    private static final String SPAN_ENTITY_XPATH = "//paratext//span[@entity='%s']";
    private static final String SPECIAL_CHARACTER_SPAN_XPATH = PARA_XPATH + SPAN_ENTITY_XPATH;
    private static final String HIGHLIGHTED_NODE_XPATH = "//a[@id='snavTree2_19']";

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPastingSpecialCharactersTest()
    {
        //Edit node UUID I18F8FFE0571911E7B505AC77B1646E6B in the DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(TEXAS_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(TEXAS_NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();

        //Find a special character, such as a 2-space character in a Text Paragraph
        //Put your cursor just before the special character
        editorTextPage().click(String.format(SPECIAL_CHARACTER_SPAN_XPATH, 0, 1, ENSP.getEntity()));

        //VERIFY: The element in the tree is highlighted (HALCYONST-10918)
        assertThatNodeIsHighlighted();

        //Highlight the special character
        editorTextPage().highlightHelper(1);

        //VERIFY: The element in the tree is highlighted (HALCYONST-10918)
        assertThatNodeIsHighlighted();

        //Hit Ctrl+C
        editorTextPage().ctrlCUsingAction();

        //Scroll down and look for a different special character,
        //such as a 1-space character in a Text Paragraph
        editorTextPage().click(String.format(SPECIAL_CHARACTER_SPAN_XPATH, 0, 2, THINSP.getEntity()));

        //Highlight this different special character and hit Ctrl+V
        //to paste the previous special character over this one
        editorTextPage().highlightHelper(1);
        editorTextPage().ctrlVUsingAction();

        //Close and check-in changes
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Reopen the node in the DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();

        //VERIFY: The pasted special character still exists
        assertThat(editorPage().doesElementExist(String.format(SPECIAL_CHARACTER_SPAN_XPATH, 0, 2, THINSP.getEntity())))
                .as("The pasted character should exist after re-opening the node")
                .isTrue();

        //Close the document and restore the origina WIP version
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataPage().breakOutOfFrame();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().restoreOriginalWIPVersion();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
    }

//  ------------- Assertions: -------------

    private void assertThatNodeIsHighlighted()
    {
        editorPage().switchToEditor();
        assertThat(editorTreePage().getElement(HIGHLIGHTED_NODE_XPATH).getCssValue("background-color"))
                .as(String.format("The [%s] node should be highlighted i.e. must have '%s' background color value",
                        HIGHLIGHTED_NODE_XPATH, HIGHLIGHTED_BACKGROUND_COLOR))
                .isEqualTo(HIGHLIGHTED_BACKGROUND_COLOR);
        editorPage().switchDirectlyToTextFrame();
    }
}
