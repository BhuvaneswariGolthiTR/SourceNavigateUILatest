package com.thomsonreuters.codes.codesbench.quality.tests.smoke.delta.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class DeltaClamshellMenuModifyLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks integration status can be reset properly <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void resetIntegrationStatusLegalTest()
    {
        String status = "Needs cite locate";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //get/set integration status of first delta
        sourceNavigateGridPage().clickFirstDelta();
        if(sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status))
        {
            sourceNavigateGridPage().clickFirstDelta();
            clamshellPage().openSideBarModify();
            deltaTabModifyClamshellPage().clickRunCiteLocate(true,false);
        }
        boolean integrationStatus =  sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status);
        Assertions.assertFalse(integrationStatus, status + "appears in grid before testing");

        //refresh integration status and verify in grid
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarModify();
        deltaTabModifyClamshellPage().clickResetIntegrationStatus(true,false);
        integrationStatus = sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status);
        Assertions.assertTrue(integrationStatus, status + " appears in Grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks running cite locate updates integration status in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void citeLocateLegalTest()
    {
        String status = "Needs cite locate";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //get/reset integration status of first delta
        sourceNavigateGridPage().clickFirstDelta();
        boolean integrationStatus = sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status);
        if(!integrationStatus)
        {
            sourceNavigateGridPage().rightClickFirstDelta();
            deltaContextMenu().goToModifyResetIntegrationStatus();
        }
        integrationStatus = sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status);
        Assertions.assertTrue(integrationStatus, status + "appears in grid before testing");

        //run cite locate and verify proper integration status in grid
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarModify();
        deltaTabModifyClamshellPage().clickRunCiteLocate(true,false);
        integrationStatus = sourceNavigateGridPage().getIntegrationStatusForSelected().equals(status);
        Assertions.assertFalse(integrationStatus, status + " appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Assigned User and date can be changed and update properly <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignUserLegalTest()
    {
        String assignee = user().getFirstname() + " " + user().getLastname();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //Add assigned user and assigned date
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarModify();
        deltaTabModifyClamshellPage().clickAssignUser(true,false);
        assignUserPage().selectAssignedUserDropdown(assignee);
        assignUserPage().setAssignedDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        assignUserPage().clickSave();

        //check assigned user and assigned date are correct
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarEdit();
        deltaTabEditClamshellPage().clickProperties(true, false);
        boolean userCheck = assignUserPage().getAssignedUserDropdownText().equals(assignee);
        boolean dateCheck = deltaPropertiesPage().getAssignedUserDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(userCheck && dateCheck, "Assigned user and date are correct");
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
