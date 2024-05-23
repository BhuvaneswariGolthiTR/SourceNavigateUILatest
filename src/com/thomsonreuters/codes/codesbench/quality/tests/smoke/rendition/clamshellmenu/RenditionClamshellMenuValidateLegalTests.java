package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuValidateLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Validations on first rendition using clamshell menu, checks Validation Report window appears, and rendition shows up in validation report <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void runValidationsLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Validate -> Run Validations in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarValidate();
        renditionTabValidateClamshellPage().clickRunValidations(true, false);

        //click Validate -> View Validations in clamshell for first rendition and check rendition is shown
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarValidate();
        boolean validationReportWindowAppears = renditionTabValidateClamshellPage().clickViewValidations(true, false);
        boolean validationReportShowsRendition = validationReportPage().validationReportContainsRendition();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(validationReportWindowAppears, "Validation Report Window appears"),
            () -> Assertions.assertTrue(validationReportShowsRendition, "Rendition appears in Validation Report window appears")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Runs Validate and Update Links on first rendition using clamshell menu and checks workflow finishes  <br>
     * USER - Legal <br>sour
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validateAndUpdateLinksLegalTest()
    {
        // Log in and go to Source Navigate
        //TODO RENDITION STATUS DRA or find what data allows for validating and update links
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2011");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("S");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("SB3001");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click Validate -> Run Validations in clamshell for first rendition
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarValidate();
        String workflowId = renditionTabValidateClamshellPage().clickValidateAndUpdateLinks(true, false);
        Assertions.assertFalse(workflowId.isEmpty(),"Workflow ID is empty");

        //verify workflow finishes
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowTimeAndStatus();
        Assertions.assertTrue(workflowFinished, String.format("Workflow: %s finished successfully", workflowId));
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
