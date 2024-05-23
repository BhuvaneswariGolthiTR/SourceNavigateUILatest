package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuTransferLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Transfer To BTS' from the Transfer context menu.
     *           Then the test navigates to the workflow reporting system page and verifies the workflow finished<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void transferToBtsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().transferToBTS();

        //Navigate to workflow reporting system page and filter for workflow, then verify the workflow finished
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbBtsLoad", "",
                "BTS Load", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished, String.format("Transfer to BTS workflow: %s successfully finished", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Transfer To LMS' from the Transfer context menu.
     *           Then the test navigates to the workflow reporting system page and verifies the workflow finished<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void transferToLmsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().transferToLMS();

        //Navigate to workflow reporting system page and filter for workflow, then verify the workflow finished
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbLmsTransfer", "",
                "LMS Transfer", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished,  String.format("Transfer to LMS workflow: %s successfully finished", workflowId));
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
