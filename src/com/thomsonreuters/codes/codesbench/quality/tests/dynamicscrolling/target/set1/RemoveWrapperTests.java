package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyEditPageElements.HIERARCHY_EDIT_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.chord;

public class RemoveWrapperTests extends TestService
{
    private static final String ALERT_EXPECTED_MESSAGE = "Warning: The DTD for Target documents does not list para as a child of section."
            + " Would you like to continue with removing the wrapper?";

    /**
     * SUMMARY - (Ctrl + w) Remove Wrapper (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void removeWrapperTargetLegalTest()
    {
        String firstUUID = "IDAF2B03014F211DA8AC5CD53670E6B4E";
        String secondUUID = "IF7A6B2800EEC11DC8429A7EFE04E84BA";
        String textSpan = "Text";
        String textParaSpan = "Text Paragraph";

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(firstUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().rightClick(String.format(SPAN_BY_TEXT, textSpan));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();

        editorPage().closeEditorWithDiscardingChangesIfAppeared();
        editorPage().switchToWindow(PAGE_TITLE);
        editorPage().closeCurrentWindowIgnoreDialogue();

        editorPage().switchToWindow(HIERARCHY_EDIT_PAGE_TITLE);
        hierarchySearchPage().searchNodeUuid(secondUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().rightClick(String.format(SPAN_BY_TEXT, textSpan));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchDirectlyToTextFrame();

        String textParaSpanXpath = String.format(editorTextPage().getModifiedByXpath(user()) + ANCESTOR + SPAN_BY_TEXT, "para", textParaSpan);
        String textBodyXpath = textParaSpanXpath + String.format(ANCESTOR, "body");

        int wrapperSizeBeforeRemoving = editorTextPage().getElements(textBodyXpath).size();
        editorTextPage().click(textParaSpanXpath);
        editorTextPage().ctrlRUsingAction();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, ALERT_EXPECTED_MESSAGE);
        editorPage().switchDirectlyToTextFrame();
        int wrapperSizeAfterRemoving = editorTextPage().getElements(textBodyXpath).size();

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertNotEquals(wrapperSizeAfterRemoving, wrapperSizeBeforeRemoving, "Wrapper wasn't removed from the content");
    }
}
