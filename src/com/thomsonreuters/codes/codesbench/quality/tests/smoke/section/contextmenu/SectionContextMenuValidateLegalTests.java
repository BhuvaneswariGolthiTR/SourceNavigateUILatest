package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class SectionContextMenuValidateLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Validation report page is opened to verify rendition appears in validation report page and that a rendition was successfully cleared.
     *           Next validation report page is closed and test navigates to delta tab and selects all deltas in grid and resets their integration status and runs cite locate on them.
     *           Finally the test navigates to the workflow reporting system page, filters for the workflow, and verifies the workflow finishes. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkValidationLegalTest()
    {
        //TODO Needs certain data that produces a warning flag
        // TODO Austin: I don't think this test actually used Mason's source datamocking despite the before and after each having it.

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Set filter values, click first rendition, and go to section tab
        sourceNavigateFiltersAndSortsPage().setFilterValidation("Warning");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2014");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("0015");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open validation report page
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openCheckValidations();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Check if rendition appears in validation report page and if clearing a rendition is successful
        boolean reportContainsRendition = validationReportPage().validationReportContainsRendition();
        boolean renditionWasCleared = validationReportPage().clearRendition();

        //Close Validation Report page and go to delta tab
        validationReportPage().closeFromInsideIframe();
        sourceNavigateTabsPage().goToDeltaTab();

        //Select all deltas in grid, reset their integration status and run cite locate on them
        int deltaCount = sourceNavigateGridPage().getDocumentCount();
        sourceNavigateGridPage().clickFirstXRenditions(deltaCount);

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyResetIntegrationStatus();

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyCiteLocate();

        //Navigate to workflow reporting system page and filter for workflow, then verify the workflow finished
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbCiteLocate", "",
                "Cite Locate", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowFinished,  String.format("Workflow %s failed to finish in time", workflowId)),
            () -> Assertions.assertTrue(reportContainsRendition, "Report doesn't contain rendition"),
            () -> Assertions.assertTrue(renditionWasCleared,  "Rendition wasn't cleared")
        );
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
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
