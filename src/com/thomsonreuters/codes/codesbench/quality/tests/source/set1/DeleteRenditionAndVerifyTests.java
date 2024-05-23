package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteRenditionAndVerifyTests extends TestService
{

    SourceDatapodObject datapodObject;

    String renditionUuid;
    String username;

    /**
     * STORY:  <br>
     * SUMMARY: Deletes a rendition<br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteRenditionAndVerifyTest()
    {
        // TODO: This test only works for Renditions that are not synced to Westlaw.
        //  In the future, we need a test that also checks a workflow for Renditions synced to Westlaw
        String docNumber = datapodObject.getRenditions().get(0).getDocNumber() + "";

        username = user().getWorkflowUsername().toUpperCase();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
        Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window did appear.");

        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();

        boolean renditionWasDeleted = sourceNavigateGridPage().isRenditionDeleted(1);

        Assertions.assertTrue(renditionWasDeleted, "Rendition was deleted");
    }

    @BeforeEach
    public void insertDatapod()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
