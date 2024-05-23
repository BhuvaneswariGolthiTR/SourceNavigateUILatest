package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_CONTAINS_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class EditorTreeToggleLockTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * SUMMARY - Editor Tree Toggle Lock (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editorTreeToggleLockTargetLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String editorTree = "Editor Tree";
        String grayColor = "rgba(204, 204, 204, 1)";

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorTreePage().toggleLockEditorTree();

        boolean isEditorTreeLocked = editorTreePage().doesElementExist(String.format(TREE_NODE_CONTAINS_TEXT, "locked"));
        boolean isEditorTreeGrayedOut = editorTreePage().getEditorTreeBackgroundColor().equals(grayColor);

        editorTreePage().toggleLockEditorTree();

        boolean isEditorTreeUnlocked = editorTreePage().doesElementExist(String.format(TREE_NODE_CONTAINS_TEXT, "unlocked"));
        boolean isEditorTreeNoLongerGrayedOut = !editorTreePage().getEditorTreeBackgroundColor().equals(grayColor);
        boolean areAnyErrorsInMessagePane = editorMessagePage().checkForErrorInMessagePane();

        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(isEditorTreeLocked, String.format("%s wasn't locked", editorTree)),
              () -> Assertions.assertTrue(isEditorTreeGrayedOut, String.format("%s wasn't grayed out", editorTree)),
              () -> Assertions.assertTrue(isEditorTreeUnlocked, String.format("%s is still locked", editorTree)),
              () -> Assertions.assertTrue(isEditorTreeNoLongerGrayedOut, String.format("%s is still grayed out", editorTree)),
              () -> Assertions.assertFalse(areAnyErrorsInMessagePane, "There are errors in message pane")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
