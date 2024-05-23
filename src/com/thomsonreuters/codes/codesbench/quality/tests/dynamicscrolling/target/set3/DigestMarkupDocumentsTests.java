package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DigestMarkupDocumentsTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void removeElementFromDocumentWithDigestMarkupTargetCarswellTest()
    {
        String uuid = "ID79D01902D1B11EBA719E384E2D2BED9";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().rightClick(EditorTextPageElements.NOD_NOTE_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();
        editorPage().switchToEditorTextFrame();

        boolean isNoteOfDecisionElementDeleted = !editorTextPage().doesElementExist(EditorTextPageElements.NOD_NOTE_SPAN);

        editorPage().switchToEditor();
        boolean areAnyErrorsAppearedAfterDeletion = editorMessagePage().checkForErrorInMessagePane();

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(isNoteOfDecisionElementDeleted, "Note of Decision element wasn't deleted"),
                () -> Assertions.assertFalse(areAnyErrorsAppearedAfterDeletion, "There are errors in message pane")
        );
    }
}
