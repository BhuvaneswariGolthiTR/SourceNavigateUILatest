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

public class DeltaClamshellMenuTrackLegalTests extends TestService
{
    private String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Images Sent Out date can be set <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesSentOutLegalTests()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //select images sent out from clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarTrack();
        deltaTabTrackClamshellPage().clickImagesSentOut(true,false);

        //check images sent out date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getImageSentOutDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Images Sent Out date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Images Completed date can be set <br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesCompletedLegalTests()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //select Images Completed from clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarTrack();
        deltaTabTrackClamshellPage().clickImagesCompleted(true,false);

        //check Images Completed date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getImagesCompletedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Images Completed date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Requested date can be set <br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularRequestedLegalTests()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //select Tabular Requested from clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarTrack();
        deltaTabTrackClamshellPage().clickTabularRequested(true,false);

        //check tabular requested date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularRequestedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Requested date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Started date can be set using <br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularStartedLegalTests()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //select tabular started from clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarTrack();
        deltaTabTrackClamshellPage().clickTabularStarted(true,false);

        //check tabular started date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularStartedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Started date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Completed date can be set using <br>
     * USER - Legal <br>
     */

    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularCompletedLegalTests()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //select Tabular Completed from clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarTrack();
        deltaTabTrackClamshellPage().clickTabularCompleted(true,false);

        //check Tabular Completed date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularCompletedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Completed date is today");
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
