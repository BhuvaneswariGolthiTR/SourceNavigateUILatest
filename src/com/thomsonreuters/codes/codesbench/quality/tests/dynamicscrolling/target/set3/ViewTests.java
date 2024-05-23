package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.BLUE_BAR_BY_WIP_VERSION_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.BODY_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_WITH_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.PreviousWipVersionsPageElements.closeButton;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewTests extends TestService
{
    private static final String TEXT_PARAGRAPH_SPAN = String.format(SPAN_BY_TEXT, "Text Paragraph");
    private static final String TEXT_PARAGRAPH_CONTENT = TEXT_PARAGRAPH_SPAN + String.format(FOLLOWING_SIBLING, "paratext");

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewLegalTest()
    {
        String uuid = "I2A7CDCA014F511DA8AC5CD53670E6B4E";
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);

        siblingMetadataPage().selectedSiblingMetadataViewContent();

        editorPage().switchToEditorTextFrame();

        editorTextPage().waitForElementExists(BODY_TAG);
        boolean readOnly = editorTextPage().isElementReadOnly(BODY_TAG);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(readOnly, "Editor should be in Read-only mode")
                );
    }

    /**
     * STORY/BUG - HALCYONST-10980 <br>
     * SUMMARY - View Dynamic Scrolling on WIP version list <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewDynamicScrollingOnWipVersionListTargetLegalTest()
    {
        String targetNodeUUID = "I26BB1DE014EE11DA8AC5CD53670E6B4E";
        String siblingNodeUUID = "I26BFD8D014EE11DA8AC5CD53670E6B4E";
        String wipVersionIndex = "1";

        //Go to Hierarchy page, login and open target node in the DS editor mode
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(targetNodeUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Return to Hierarchy page and open view/modify previous wip version on the sibling node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().searchNodeUuid(siblingNodeUUID);
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        //Open the latest wip version of the sibling node in the DS view mode
        previousWipVersionsPage().clickWipVersionByIndex(wipVersionIndex);
        String wipVersion = previousWipVersionsPage().getWipVersionOfSelectedWip();
        previousWipVersionsPage().rightClickWipVersionByIndex(wipVersionIndex);
        previousWipVersionsContextMenu().viewContent();
        previousWipVersionsPage().switchToPreviousWipVersionsPage();
        previousWipVersionsPage().sendEnterToElement(closeButton);
        editorPage().switchToEditor();
        //Assert that the specific WIP version selected in the WIP Versions window appears in the blue bar of the editor frame
        assertThat(editorPage().doesElementExist(String.format(BLUE_BAR_BY_WIP_VERSION_NUMBER, wipVersion)))
                .as("Specific Wip Version should appear in the blue bar of the editor frame")
                .isTrue();
        editorPage().switchToEditorTextFrameByIndex(3);
        //Assert that editor opened in the view mode
        assertThat(editorTextPage().isElementReadOnly(BODY_TAG))
                .as("The Dynamic Scrolling Editor should be opened in view mode")
                .isTrue();

        //Copy Text Paragraph from the sibling node opened in the DS view mode
        String copiedTextContent = editorTextPage().getElementsText(TEXT_PARAGRAPH_CONTENT);
        editorTextPage().rightClick(TEXT_PARAGRAPH_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copy();

        //Set the focus to the second document window to get it closed properly
        editorPage().switchToEditorTextFrameByIndex(5);
        editorPage().closeDocumentWithNoChanges();

        //Paste copied previously Text Paragraph to the target node opened in the DS editor mode
        editorPage().switchToEditorTextFrame();
        editorTextPage().rightClick(TEXT_PARAGRAPH_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToEditorTextFrame();
        String pastedTextContent = getPastedTextContent();
        //Assert that pasted Text Paragraph is appeared in the target node opened in the DS editor mode
        assertThat(pastedTextContent)
                .as("Pasted Text Paragraph should appear in the document and be the same as copied")
                .isEqualTo(copiedTextContent);

        //Close target node and check in changes
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextContainsAndAccept(true,
                "Document validation failed, continue with check-in?");
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Reopen previously checked in target node
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().searchNodeUuid(targetNodeUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        String pastedTextContentAfterReopening = getPastedTextContent();
        //Assert that previously pasted Text Paragraph is still appeared in the reopened target node
        assertThat(pastedTextContentAfterReopening)
                .as("Pasted Text Paragraph should appear in the document after reopening")
                .isEqualTo(pastedTextContent);

        //Restore to a previous baseline
        editorTextPage().rightClick(String.format(TEXT_PARAGRAPH_WITH_NUMBER, 2));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();
        editorPage().closeAndCheckInChanges();
    }

//  ------------- Assistive methods: -------------

    private String getPastedTextContent()
    {
        return editorTextPage().getElementsText(editorTextPage().getElements(TEXT_PARAGRAPH_CONTENT).get(1));
    }
}
