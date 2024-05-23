package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuModifyLegalTests extends TestService
{
    SourceDatapodObject apv;
    Connection connection;
    String renditionUuid;

    private void filterRendition(String multDupl, String year)
    {
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multDupl);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Marks a rendition as non-duplicate and checks results on grid page updates correctly <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void markAsNonDuplicateLegalTest()
    {
        // Log in and go to Source Navigate
        // Filter for the document: Validation: None, Multiple/Duplicate: Duplicate,
        // Deleted: Not Deleted, Content Set: Iowa(Development), Year: 2011
        //TODO Need to add two documents with duplicate flag
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        filterRendition("Duplicate", "2011");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //get count results of filter, mark firs rendition as non-duplicate using modify clamshell menu
        int resultsCountBefore = sourceNavigateGridPage().getResultsCount();
        logger.information("Results count before: " + resultsCountBefore);
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarModify();
        renditionTabModifyClamshellPage().clickMarkAsNonDuplicate(true, false);

        //check results count again and make sure the results after is 1 less
        int resultsCountAfter = sourceNavigateGridPage().getResultsCount();
        logger.information("Results count after: " + resultsCountAfter);
        Assertions.assertTrue(resultsCountAfter < resultsCountBefore, "Result count is correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Vetos rendition and checks veto status shows in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void vetoLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //select veto rendition option in modify clamshell menu and check status in grid
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarModify();
        renditionTabModifyClamshellPage().clickVetoRendition(true, false);
        boolean vetoStatusInGrid = sourceNavigateGridPage().getInternalEnactmentStatus().equals("VETO");
        Assertions.assertTrue(vetoStatusInGrid, "Veto status appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Assign user and date using modify clamshell menu and check they are saved <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignUserLegalTest()
    {
        String assignedUser = user().getFirstname() + " " + user().getLastname();
        // Log in and go to Source Navigate
        // Filter for the document: Validation: None, Multiple/Duplicate: None,
        // Deleted: Not Deleted, Content Set: Iowa(Development), Year: 2015
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //select assign use option in modify clamshell menu for first rendition clear and assign it to 'Jesse Battcher' and today
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        boolean assignedUserEmpty = renditionPropertiesPage().clearAssignedUser();
        boolean assignedDateEmpty = renditionPropertiesPage().clearAssignedDate();
        Assertions.assertTrue(assignedUserEmpty, "Cleared assigned user");
        Assertions.assertTrue(assignedDateEmpty, "Cleared assigned date");
        renditionPropertiesPage().clickSave();
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarModify();
        renditionTabModifyClamshellPage().clickAssignUser(true,false);
        assignUserPage().selectAssignedUserDropdown(assignedUser);
        assignUserPage().setAssignedDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        assignUserPage().clickSave();

        //open rendition properties and check assigned user and date are correct
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        boolean userCheck = assignUserPage().getAssignedUserDropdownText().equals(assignedUser);
        boolean dateCheck = renditionPropertiesPage().getAssignedUserDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(userCheck && dateCheck, "Assigned user and Date correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Omit rendition and checks omit status shows in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void omitLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //select omit rendition option in modify clamshell menu and check status in grid
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarModify();
        renditionTabModifyClamshellPage().clickOmitRendition(true,false);
        boolean omitStatusInGrid = sourceNavigateGridPage().getInternalEnactmentStatus().equals("OMIT");
        Assertions.assertTrue(omitStatusInGrid, "Omit status appears in grid");
    }

    @BeforeEach
    public void mockData()
    {
        apv = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = apv.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(apv != null)
        {
            apv.delete();
        }
        disconnect(connection);
    }
}
