package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuSyncLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Sync on first rendition using clamshell menu and verify workflow finishes  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void syncLegalTest()
    {
        //TODO workflow fails, needs BEIT mnemonic

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2019");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("260");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //run Sync -> Sync through clamshell for first rendition and click ok on the sync page
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarSync();
        boolean syncWindowAppears = renditionTabSyncClamshellPage().clickSync(true, false);
        Assertions.assertTrue(syncWindowAppears, "Sync window appears");

        syncPage().clickOkButton();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSessionLawPublishing", "SOS.IAT", "Session Law enhanced Load", user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowId != null && workflowCompleted, String.format("Sync workflow: %s completed", workflowId));
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
