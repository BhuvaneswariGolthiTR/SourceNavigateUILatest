package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuValidateLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;

    private String createAPVDoc()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        String renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'Run Validations' from the Validate context menu.
     *           The test then verifies that a grid refresh occurs after clicking 'Run Validations'<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void runValidationsLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Right click first rendition and select 'Run Validations' from context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().validateRunValidations();

        boolean gridRefreshOccurred = sourceNavigateGridPage().waitForGridRefresh();
        Assertions.assertTrue(gridRefreshOccurred, "Grid refresh did not occur");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Rendition, the test selects 'View Validations' from the Validate context menu.
     *           The test then verifies that Validation Report Page contains this rendition<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewValidationsLegalTest()
    {
        createAPVDoc();

        SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
        sourceMocker.giveRenditionError(connection, datapodObject);

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, datapodObject.getRenditions().get(0).getRenditionUUID()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Right click first rendition and select 'View Validations' from context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().validateViewValidations();

        boolean reportContainsRendition = validationReportPage().validationReportContainsRendition();
        Assertions.assertTrue(reportContainsRendition, "Validation Report doesn't contain rendition");

        validationReportPage().closeFromInsideIframe();
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
