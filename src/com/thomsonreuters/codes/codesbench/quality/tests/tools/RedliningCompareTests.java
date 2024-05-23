package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import java.sql.Connection;

public class RedliningCompareTests extends TestService
{
    /**
     * STORY: CODESITEMS-190 <br>
     * SUMMARY: This test edits a blueline and verifies that it does not appear in the Redlining Compare page <br>
     * USER: Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void redliningExcludeNODsFromTheListLegalTest()
    {
        //We are using USCA(Development) because Iowa(Development) is not available for redlining compare
        String contentSet = "USCA(Development)";
        String nodeUUID = "I4D3BC010897111E6BD64F5809AAF6807";
        String testText = "Test";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        boolean editMadeSuccessfully = false;

        //Searching for target node
        try
        {
            hierarchyMenu().goToNavigate();

            hierarchySearchPage().searchNodeUuid(nodeUUID);
            String volumeNumber = siblingMetadataPage().getSelectedNodeVols();

            //Inserting text into target node
            siblingMetadataPage().selectedSiblingMetadataEditContent();
            editorPage().switchDirectlyToTextFrame();
            editorTextPage().insertPhraseAtEndOfNODParagraph(testText);
            editorPage().closeAndCheckInChanges();
            editorPage().waitForEditorToClose();
            editMadeSuccessfully = hierarchyNavigatePage().switchToHierarchyEditPage();

            //Verifying that the blueline does NOT appear on the Redlining Compare page
            toolsMenu().goToRedliningCompare();
            redliningComparePage().selectAndAddSingleVolume(volumeNumber);
            redliningComparePage().clickNext();
            boolean isBluelinePresent = redliningComparePage().isExpectedNodeUUIDPresent("I4D3BC010897111E6BD64F5809AAF6807");
            Assertions.assertFalse(isBluelinePresent, "The blueline did not appear in the Redlining Compare table");
        }
        finally
        {
            //Resetting wip version
            if(editMadeSuccessfully)
            {
                Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
                String contentUUID = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUUID, connection);
                HierarchyDatabaseUtils.deleteLatestWipVersion(contentUUID, connection);
                HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
                BaseDatabaseUtils.disconnect(connection);
            }
        }
    }
}
