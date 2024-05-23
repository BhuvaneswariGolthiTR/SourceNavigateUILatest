package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

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

public class RenditionClamshellMenuCreatePrepDocumentLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Creates a prep document using clamshell menu and checks it appears in grid <br>
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

        // click first rendition, open Create clamshell menu, click Create Prep Document
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarCreate();
        renditionTabCreateClamshellPage().clickCreatePrepDocument(true, false);

        toolsMenu().goToWorkflowReportingSystem();
        boolean createPrepDocumentWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbCreatePrepDocuments", "", "Create Prep Document", user().getUsernameWithoutUUpperCaseC());
        Assertions.assertTrue(createPrepDocumentWorkflowFinished, "The Create Prep Document workflow finished.  See log for workflow ID.");
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        // change Rendition Status filter to 'PREP' and refresh
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        String prepRendUuid = sourceNavigateGridPage().getUUID();

        // delete created PREP doc
        String renditionStatusInGrid = sourceNavigateGridPage().getRenditionStatusFirstRow();
        SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
        sourceMocker.deleteRendition(connection, prepRendUuid);
        Assertions.assertEquals(renditionStatusInGrid, "PREP", "A PREP document appears in grid");
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
            //sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);

            // TODO: Figure out why this delete hangs and never finishes
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
