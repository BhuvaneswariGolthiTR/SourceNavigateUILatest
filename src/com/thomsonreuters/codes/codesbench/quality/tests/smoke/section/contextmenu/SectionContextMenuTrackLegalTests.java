package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.contextmenu;

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

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class SectionContextMenuTrackLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Images Sent Out' from the Track context menu.
     *           Next the test enters the Section Properties, and opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Images Sent Out' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesSentOutLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open context menu and click 'Images Sent Out' from the Track submenu
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().clickTrackImagesSentOut();

        //Open Section Properties and the Proposed/Approved Tracking Info tab
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openProposedApprovedTrackingInfoTab();

        //Verify the 'Images Sent Out' date is today's date
        boolean imagesSentOutDateIsToday = sectionPropertiesPage().getImagesSentOut().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(imagesSentOutDateIsToday, "Images Sent Out Date is not today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Images Completed' from the Track context menu.
     *           Next the test enters the Section Properties, and opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Images Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void imagesCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open context menu and click 'Images Completed' from the Track submenu
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().clickTrackImagesCompleted();

        //Open Section Properties and the Proposed/Approved Tracking Info tab
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openProposedApprovedTrackingInfoTab();

        //Verify the 'Images Completed' date is today's date
        boolean imagesCompletedDateIsToday = sectionPropertiesPage().getImagesCompleted().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(imagesCompletedDateIsToday, "Images Completed Date is not today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Tabular Requested' from the Track context menu.
     *           Next the test enters the Section Properties, and opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Requested' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularRequestedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open context menu and click 'Tabular Requested' from the Track submenu
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().clickTrackTabularRequested();

        //Open Section Properties and the Proposed/Approved Tracking Info tab
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openProposedApprovedTrackingInfoTab();

        //Verify the 'Tabular Requested' date is today's date
        boolean tabularRequestedDateIsToday = sectionPropertiesPage().getTabularRequested().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(tabularRequestedDateIsToday, "Tabular Requested Date is not today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Tabular Started' from the Track context menu.
     *           Next the test enters the Section Properties, and opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Started' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularStartedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open context menu and click 'Tabular Started' from the Track submenu
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().clickTrackTabularStarted();

        //Open Section Properties and the Proposed/Approved Tracking Info tab
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openProposedApprovedTrackingInfoTab();

        //Verify the 'Tabular Started' date is today's date
        boolean getTabularStartedDateIsToday = sectionPropertiesPage().getTabularStarted().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(getTabularStartedDateIsToday, "Tabular Started Date is not today's date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test selects 'Tabular Completed' from the Track context menu.
     *           Next the test enters the Section Properties, and opens the Proposed/Approved Tracking Information tab.
     *           Finally, the test verifies the 'Tabular Completed' date is today's date<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularCompletedLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open context menu and click 'Tabular Completed' from the Track submenu
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().clickTrackTabularCompleted();

        //Open Section Properties and the Proposed/Approved Tracking Info tab
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();
        sectionPropertiesPage().openProposedApprovedTrackingInfoTab();

        //Verify the 'Tabular Completed' date is today's date
        boolean tabularCompletedDateIsToday = sectionPropertiesPage().getTabularCompleted().equals(currentDate);
        sectionPropertiesPage().clickCancel();

        Assertions.assertTrue(tabularCompletedDateIsToday, "Tabular Completed Date is not today's date");
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
