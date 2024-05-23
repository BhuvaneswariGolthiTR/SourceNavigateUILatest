package com.thomsonreuters.codes.codesbench.quality.tests.smoke.delta.contextmenu;

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

public class DeltaContextMenuTrackLegalTests extends TestService
{
    private String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Images Sent Out date is updated correctly using context menu <br>
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

        //select images sent out from context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToTrackImagesSentOut();

        //check images sent out date is today
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarEdit();
        deltaTabEditClamshellPage().clickProperties(true, false);
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getImageSentOutDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Images Sent Out date is correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Images Completed date is updated correctly using context menu <br>
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

        //select images completed from context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToTrackImagesCompleted();

        //check images completed date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getImagesCompletedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Images Completed date is correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Requested date is updated correctly using context menu <br>
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

        //select tabular requested from context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToTrackTabularRequested();

        //check tabular requested date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularRequestedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Requested date is correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Started date is updated correctly using context menu <br>
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

        //select tabular started from context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToTrackTabularStarted();

        //check tabular started date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularStartedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Started date is correct");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Tabular Completed date is correct using context menu <br>
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

        //select Tabular Completed from context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToTrackTabularCompleted();

        //check Tabular Completed date is today
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToDeltaProperties();
        deltaPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean dateIsToday = deltaPropertiesPage().getTabularCompletedDate().equals(currentDate);
        deltaPropertiesPage().clickCancel();
        Assertions.assertTrue(dateIsToday, "Tabular Completed date is correct");
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