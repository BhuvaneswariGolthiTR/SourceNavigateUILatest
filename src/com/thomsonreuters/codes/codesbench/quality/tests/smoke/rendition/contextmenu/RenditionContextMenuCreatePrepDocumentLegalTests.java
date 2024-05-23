package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuCreatePrepDocumentLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Creates a prep document using context menu and checks it appears in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPreparationDocumentLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        // right click first rendition, open Create clamshell menu, click Create Prep Document
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().createPrepDocument();

        toolsMenu().goToWorkflowReportingSystem();
        boolean createPrepDocumentWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbCreatePrepDocuments", "", "Create Prep Document", user().getUsernameWithoutUUpperCaseC());
        Assertions.assertTrue(createPrepDocumentWorkflowFinished, "The Create Prep Document workflow finished.  See log for workflow ID.");
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        // change Rendition Status filter to 'PREP', refresh, and get status of first rendition
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        // wait for prep document to be created
        sourceNavigateFooterToolsPage().refreshTheGrid();
        String renditionStatusInGrid = sourceNavigateGridPage().getRenditionStatusFirstRow();
        boolean prepDocumentAppearsInGrid = renditionStatusInGrid.equals("PREP");
        String prepRendUuid = sourceNavigateGridPage().getUUID();

        // delete created PREP doc
        Assertions.assertTrue(prepDocumentAppearsInGrid, "A PREP document appears in grid");
        SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
        sourceMocker.deleteRendition(connection, prepRendUuid);
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
