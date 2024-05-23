package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SrcContentType;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class EditYearOrSessionTests extends TestService
{
    Connection connection;
    List<SourceDatapodObject> datapodObjects = new ArrayList<>();

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies Year/Session button is disabled when selecting two documents with different content types. Valid for LAW, RES, or UNCHAPTERED APV invalid for not LAW, RES, or UNCHAPTERED APV <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void disabledYearOrSessionForMultipleRenditionsDifferentContentTypeTest()
    {

        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.BILL);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.UNCHAPTERED);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        String docNumberFilter = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "," + datapodObjects.get(1).getRenditions().get(0).getDocNumber();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared, "Source Navigate window appeared");

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumberFilter);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        List<String> contentTypes = sourceNavigateGridPage().getAllContentTypes();
        Assertions.assertTrue(contentTypes.contains("BILL"), "Rendition with content type 'BILL' did not appear");
        Assertions.assertTrue(contentTypes.contains("UNCHAPTERED"),"Rendition with content type 'UNCHAPTERED' did not appear");

        sourceNavigateFooterToolsPage().selectAllOnPage();
        sourceNavigateGridPage().rightClickRenditions();

        boolean isEditYearOrSessionEnabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.YearOrSession);
        Assertions.assertTrue(isEditYearOrSessionEnabled, "The edit year/session context menu element is disabled");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies update year/session is disabled when different rendition statuses are selected
     *  This test filters for LAW content type documents with rendition statuses APV and PREP <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void disabledYearOrSessionForMultipleRenditionsDifferentRenditionStatusesTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());

        String docNumberFilter = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "," + datapodObjects.get(1).getRenditions().get(0).getDocNumber();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumberFilter);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        List<String> renditionStatuses = sourceNavigateGridPage().getAllRenditionStatuses();
        Assertions.assertTrue(renditionStatuses.contains("APV"), "Rendition with rendition status APV did not appear");
        Assertions.assertTrue(renditionStatuses.contains("PREP"),"Rendition with rendition status PREP did not appear");
        sourceNavigateFooterToolsPage().selectAllOnPage();

        sourceNavigateGridPage().rightClickRenditions();
        renditionContextMenu().openEditSubMenu();
        boolean yearOrSessionIsDisabled = sourceNavigateContextMenu().isElementDisabled(RenditionContextMenuElements.YearOrSession);
        Assertions.assertTrue(yearOrSessionIsDisabled, "Year or session is disabled");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies the update year or session is disabled when multiple documents with different rendition statuses and content types are selected
     * This test filters for two documents with content types BILL and UNCHAPTERED and rendition statuses of APV and IN <br>
     * USER: LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void disabledYearOrSessionForMultipleRenditionsDifferentContentTypesAndRenditionStatusesTest()
    {

        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.BILL);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.UNCHAPTERED);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());

        String docNumberFilter = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "," + datapodObjects.get(1).getRenditions().get(0).getDocNumber();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumberFilter);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        List<String> renditionStatuses = sourceNavigateGridPage().getAllRenditionStatuses();
        Assertions.assertTrue(renditionStatuses.contains("APV"), "Rendition with rendition status APV did not appear");
        Assertions.assertTrue(renditionStatuses.contains("PREP"),"Rendition with rendition status PREP not appear");

        List<String> contentTypes = sourceNavigateGridPage().getAllContentTypes();
        Assertions.assertTrue(contentTypes.contains("UNCHAPTERED"), "Rendition with content type UNCHAPTERED did not appear");
        Assertions.assertTrue(contentTypes.contains("BILL"),"Rendition with content type BILL did not appear");
        sourceNavigateFooterToolsPage().selectAllOnPage();

        sourceNavigateGridPage().rightClickRenditions();
        renditionContextMenu().openEditSubMenu();
        boolean yearOrSessionIsDisabled = sourceNavigateContextMenu().isElementDisabled(RenditionContextMenuElements.YearOrSession);
        Assertions.assertTrue(yearOrSessionIsDisabled, "Year or session should be disabled for rendition");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies Year or Session is disabled for single Selected APV Renditions with a content type not LAW, RES, or UNCHAPTERED <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void disabledYearOrSessionSingleSelectedAPVRenditionsCRTTypeTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.CRT);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openEditSubMenu();
        boolean yearOrSessionIsDisabled = sourceNavigateContextMenu().isElementDisabled(RenditionContextMenuElements.YearOrSession);
        Assertions.assertTrue(yearOrSessionIsDisabled, "Year or session should be disabled for rendition");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies year or session is disabled for a single non APV rendition with content type LAW, RES, or UNCHAPTERED <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void disabledYearOrSessionSingleSelectedRenditionsNonAPVTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openEditSubMenu();
        boolean yearOrSessionIsDisabled = sourceNavigateContextMenu().isElementDisabled(RenditionContextMenuElements.YearOrSession);
        Assertions.assertTrue(yearOrSessionIsDisabled, "Year or session should be disabled");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies an alert appears when year and session fields are cleared for an APV rendition with content type LAW, RES, or UNCHAPTERED <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void alertAppearsForEmptyYearOrSessionTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");

        updateYearOrSessionPage().setSessionDropdown("");
        updateYearOrSessionPage().setYearDate("");
        updateYearOrSessionPage().clickSave();

        boolean verifyAlert = AutoITUtils.verifyAlertTextAndAccept(true, "Please enter/choose a Year and/or Session value or click");
        Assertions.assertTrue(verifyAlert, "An alert should appear when year and session fields are cleared");
    }


    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies an alert appears when the year field is set to a 3 digit year <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void alertAppearsForThreeDigitYearTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");

        updateYearOrSessionPage().setYearDate("420");
        updateYearOrSessionPage().clickSave();

        boolean verifyAlertAppearsForThreeDigit = AutoITUtils.verifyAlertTextAndAccept(true, "Year value must be 4 numerical digits");
        Assertions.assertTrue(verifyAlertAppearsForThreeDigit, "year must be 4 numerical digits alert should have appeared for a 3 digit year");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies an alert appears when the year field is set to a 5 digit year <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void alertAppearsForFiveDigitYearTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");

        updateYearOrSessionPage().setYearDate("12345");
        updateYearOrSessionPage().clickSave();

        boolean verifyAlertAppearsForFiveDigit = AutoITUtils.verifyAlertTextAndAccept(true, "Year value must be 4 numerical digits");
        Assertions.assertTrue(verifyAlertAppearsForFiveDigit, "year must be 4 numerical digits alert should have appeared for a 5 digit year");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies that an alert appears when entering a non 4 digit Numerical combination
     * this includes NOT being able to enter alphabetical entries <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void alertAppearsAlphabeticalEntryForYearTest()
    {
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean yearOrSessionPopupAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(yearOrSessionPopupAppeared, "pop-up page for Year or session should have appeared");

        updateYearOrSessionPage().setYearDate("S&Pisbest");
        updateYearOrSessionPage().clickSave();

        boolean verifyAlert = AutoITUtils.verifyAlertTextAndAccept(true, " Year value must be 4 numerical digits");
        Assertions.assertTrue(verifyAlert, "year must be 4 numerical digits alert should appear");

        boolean updateYearOrSessionPageDisappeared = updateYearOrSessionPage().clickCancel();
        Assertions.assertTrue(updateYearOrSessionPageDisappeared,"The update year/session window closed");
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies the update year/session doesn't update the year or session of a rendition if a PREP doc is created from the rendition <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createdPrepAndNotSyncedToWestLawAPVDoesNotUpdateYearOrSessionTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        String prepUuid = null;

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"Source Navigate window appeared");

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        try
        {
            //create PREP document and verify not synced to Westlaw alert appears
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean notSyncedToWestlawAlertAppears = renditionContextMenu().createPrepDocumentNotSyncedToWestlaw();
            Assertions.assertTrue(notSyncedToWestlawAlertAppears,"Not synced alert appeared");

            //verify create CwbCreatePrepDocuments workflow finished
            toolsMenu().goToWorkflowReportingSystem();
            boolean createPrepDocumentWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbCreatePrepDocuments", "", "Create Prep Document", user().getUsernameWithoutUUpperCaseC());
            Assertions.assertTrue(createPrepDocumentWorkflowFinished, "The create prep document workflow finished");

            workflowSearchPage().openFirstWorkflow();
            workflowDetailsPage().expandWorkflowProperties();
            prepUuid = workflowPropertiesPage().getWorkflowPropertyValueByName("prepUuids");
            workflowSearchPage().closeWorkflowSearchPage();

            pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
            Assertions.assertEquals(sourceNavigateFooterToolsPage().getResults(), 2,"The prep new PREP document is in the grid");

            //edit year/session
            sourceNavigateGridPage().rightClickFirstRendition();
            boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
            Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");
            updateYearOrSessionPage().setYearDate("2019");
            updateYearOrSessionPage().setSessionDropdown("1RG");
            updateYearOrSessionPage().clickSave();
            updateYearOrSessionPage().clickWorkflowLink();

            //verify PREP Created is in failed documents
            workflowDetailsPage().expandWorkflowProperties();
            boolean prepCreatedInFailedDocuments = workflowPropertiesPage().getWorkflowPropertyValueByName("failedDocuments").contains("PREP Created");
            Assertions.assertTrue(prepCreatedInFailedDocuments, "PREP Created shows in failed documents on the workflow report");

            //verify year and session change
            workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
            pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
            updateYearOrSessionPage().closeCurrentWindowIgnoreDialogue();
            sourceNavigateGridPage().refreshPage();
            List<String> years = sourceNavigateGridPage().getAllYears();
            List<String> sessions = sourceNavigateGridPage().getAllSessions();

            Assertions.assertAll
            (
                () -> Assertions.assertEquals("2020", years.get(0),"The year was updated when it should not have"),
                () -> Assertions.assertEquals("2RG", sessions.get(0),"The session was updated when it should not have")
            );
        }
        finally
        {
            if(prepUuid != null)
            {
                SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
                sourceMocker.deleteRendition(connection, prepUuid);
            }
        }
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies a synced to westlaw doc cannot have its year/session updated <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void prepNotCreatedAndSyncedToWestlawAPVDoesNotUpdateYearOrSessionTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"Source Navigate window appeared");

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //sync created rendition
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().sync();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();

        //verify sync workflow finished
        toolsMenu().goToWorkflowReportingSystem();
        boolean syncWorkflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSyncProcessing", "", "", user().getUsernameWithoutULowerCaseC());
        Assertions.assertTrue(syncWorkflowFinished, "The sync workflow finished");
        workflowSearchPage().closeWorkflowSearchPage();

        //edit year/session
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");
        updateYearOrSessionPage().setYearDate("2019");
        updateYearOrSessionPage().setSessionDropdown("1RG");
        updateYearOrSessionPage().clickSave();
        updateYearOrSessionPage().clickWorkflowLink();

        //verify Sync workflow to error
        workflowDetailsPage().expandWorkflowProperties();
        String failedDocuments = workflowPropertiesPage().getWorkflowPropertyValueByName("failedDocuments");
        boolean syncInFailedDocuments = failedDocuments.contains("<reason>Sync to Westlaw started</reason>");
        Assertions.assertTrue(syncInFailedDocuments,"Sync to Westlaw started shows in failed documents on the workflow ");

        //verify 'Sync to Westlaw started' is in failed documents on workflow
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        updateYearOrSessionPage().closeCurrentWindowIgnoreDialogue();
        sourceNavigateGridPage().refreshPage();

        //verify year/session are not updated
        List<String> years = sourceNavigateGridPage().getAllYears();
        List<String> sessions = sourceNavigateGridPage().getAllSessions();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals("2020",years.get(0),"The year was updated when it should not have"),
            () -> Assertions.assertEquals("2RG",sessions.get(0),"The session was updated when it should not have")
        );
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies update year/session changes the year and session, also the LINEAGE UUID, CORRELATION UUID, and DOCUMENT UUID for multiple APV renditions <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changedYearAndSessionOnMultipleRenditionsTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        String renditionUuid = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID();

        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        String renditionUuid_2 = datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();

        String docNumber = datapodObjects.get(0).getRenditions().get(0).getDocNumber() + "";
        String docNumber_2 = datapodObjects.get(1).getRenditions().get(0).getDocNumber() + "";
        String docNumberFilter = String.format("%s, %s", docNumber, docNumber_2);

        //get document, lineage, and correlation uuids using the rendition uuid
        String originalDocumentUuid_2 = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid_2);
        String originalLineageUuid_2 = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid_2);
        String originalCorrelationUuid_2 = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, originalLineageUuid_2);

        String originalDocumentUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid);
        String originalLineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
        String originalCorrelationUuid = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, originalLineageUuid);

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared, "Source Navigate window appeared");

        //filter for both doc numbers
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumberFilter);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        Assertions.assertEquals(2, sourceNavigateGridPage().getResultsCount(),"There are not 2 renditions in the grid");

        //select both and update year/session
        sourceNavigateFooterToolsPage().selectAllOnPage();
        sourceNavigateGridPage().rightClickRenditions();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");
        updateYearOrSessionPage().setYearDate("2019");
        updateYearOrSessionPage().setSessionDropdown("1RG");
        updateYearOrSessionPage().clickSave();
        boolean workflowDetailsPageAppeared = updateYearOrSessionPage().clickWorkflowLink();
        Assertions.assertTrue(workflowDetailsPageAppeared,"The workflow details page appeared");

        //verify update year/session workflow finished
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "The update year/session workflow finished");
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        updateYearOrSessionPage().closeCurrentWindowIgnoreDialogue();

        //get rendition, document, lineage, and correlation  uuids after year/session is updated
        String newRenditionUuid = sourceNavigateGridPage().getUUID();
        String newDocumentUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid);
        String newLineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
        String newCorrelationUuid = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, newLineageUuid);

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber_2);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        String newRenditionUuid_2 = sourceNavigateGridPage().getUUID();
        String newDocumentUuid_2 = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid_2);
        String newLineageUuid_2 = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid_2);
        String newCorrelationUuid_2 = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, newLineageUuid_2);

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumberFilter);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        List<String> years = sourceNavigateGridPage().getAllYears();
        List<String> sessions = sourceNavigateGridPage().getAllSessions();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals("2019", years.get(0),"The year was not updated"),
            () -> Assertions.assertEquals("2019", years.get(1),"The year was not updated"),
            () -> Assertions.assertEquals("1RG", sessions.get(0),"The session not updated"),
            () -> Assertions.assertEquals("1RG", sessions.get(1),"The session not updated"),
            () -> Assertions.assertEquals(newRenditionUuid, renditionUuid, "The new rendition Uuid is not the same"),
            () -> Assertions.assertNotEquals(newDocumentUuid, originalDocumentUuid, "The new document Uuid is the same"),
            () -> Assertions.assertNotEquals(newLineageUuid, originalLineageUuid, "The new lineage Uuid is the same"),
            () -> Assertions.assertNotEquals(newCorrelationUuid, originalCorrelationUuid, "The new correlation Uuid is the same"),
            () -> Assertions.assertEquals(newRenditionUuid_2, renditionUuid_2, "The new rendition Uuid is not the same"),
            () -> Assertions.assertNotEquals(newDocumentUuid_2, originalDocumentUuid_2, "The new document Uuid is the same"),
            () -> Assertions.assertNotEquals(newLineageUuid_2, originalLineageUuid_2, "The new lineage Uuid is the same"),
            () -> Assertions.assertNotEquals(newCorrelationUuid_2, originalCorrelationUuid_2, "The new correlation Uuid is the same")
        );
    }

    /**
     * STORY: HALCYONST-6916/2687/6531 <br>
     * SUMMARY: This test verifies update year/session changes the year, session, LINEAGE UUID, CORRELATION UUID, and DOCUMENT_UUID change for a single APV rendition <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changedYearAndSessionOnSingleRenditionTest()
    {
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        String renditionUuid = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID();

        String originalYear = SourceDatabaseUtils.getYear(connection, renditionUuid);
        String originalSession = SourceDatabaseUtils.getSession(connection, renditionUuid);
        //get UUIDs from mocked rendition
        String originalDocumentUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid);
        String originalLineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
        String originalCorrelationUuid = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, originalLineageUuid);

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared, "Source Navigate window appeared");

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //update year/session
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared,"The update year/session window appeared");
        updateYearOrSessionPage().setYearDate("2019");
        updateYearOrSessionPage().setSessionDropdown("1RG");
        updateYearOrSessionPage().clickSave();
        boolean workflowDetailsPageAppeared = updateYearOrSessionPage().clickWorkflowLink();
        Assertions.assertTrue(workflowDetailsPageAppeared,"The workflow details page appeared");

        //verify year/session workflow finished
        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "The update year/session workflow finished");
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        updateYearOrSessionPage().closeCurrentWindowIgnoreDialogue();

        //get UUIDs and verify only the rendition UUID stays the same
        String newRenditionUuid = sourceNavigateGridPage().getUUID();
        String newDocumentUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, newRenditionUuid);
        String newLineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, newRenditionUuid);
        String newCorrelationUuid = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, newLineageUuid);

        //verify year/session changes
        sourceNavigateGridPage().waitForPageLoaded();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        List<String> years = sourceNavigateGridPage().getAllYears();
        List<String> sessions = sourceNavigateGridPage().getAllSessions();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals("2019", years.get(0),"The year was not updated"),
            () -> Assertions.assertEquals("1RG", sessions.get(0), "The session was not updated"),
            () -> Assertions.assertEquals(newRenditionUuid, renditionUuid, "The new rendition Uuid is not the same"),
            () -> Assertions.assertNotEquals(newDocumentUuid, originalDocumentUuid, "The new document Uuid is the same"),
            () -> Assertions.assertNotEquals(newLineageUuid, originalLineageUuid, "The new lineage Uuid is the same"),
            () -> Assertions.assertNotEquals(newCorrelationUuid, originalCorrelationUuid, "The new correlation Uuid is the same")
        );

        //reset year/session
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean updateYearOrSessionWindowAppeared_2 = renditionContextMenu().updateYearSession();
        Assertions.assertTrue(updateYearOrSessionWindowAppeared_2,"The update year/session window appeared");
        updateYearOrSessionPage().setYearDate(originalYear);
        updateYearOrSessionPage().setSessionDropdown(originalSession);
        updateYearOrSessionPage().clickSave();
        updateYearOrSessionPage().clickWorkflowLink();

        //verify year/session workflow finished
        boolean secondWorkflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(secondWorkflowFinished, "The the update year/session workflow finished");
        workflowDetailsPage().closeCurrentWindowIgnoreDialogue();
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        updateYearOrSessionPage().closeCurrentWindowIgnoreDialogue();

        //verify year/session is reset
        sourceNavigateGridPage().waitForPageLoaded();
        sourceNavigateGridPage().refreshPage();
        List<String> year = sourceNavigateGridPage().getAllYears();
        List<String> session = sourceNavigateGridPage().getAllSessions();

        //get UUIDs and verify only the rendition UUID stays the same
        String resetRenditionUuid = sourceNavigateGridPage().getUUID();
        String resetDocumentUuid = SourceDatabaseUtils.getDocumentUuidFromRenditionUuid(connection, renditionUuid);
        String resetLineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
        String resetCorrelationUuid = SourceDatabaseUtils.getCorrelationUuidFromLineageUuid(connection, resetLineageUuid);

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(originalYear, year.get(0),"The year was not changed back to the original"),
            () -> Assertions.assertEquals(originalSession, session.get(0),"The session was not changed back to the original"),
            () -> Assertions.assertEquals(resetRenditionUuid, renditionUuid, "The original rendition Uuid is not the same"),
            () -> Assertions.assertNotEquals(resetDocumentUuid, originalDocumentUuid, "The original document Uuid is the same"),
            () -> Assertions.assertNotEquals(resetLineageUuid, originalLineageUuid, "The original lineage Uuid is the same"),
            () -> Assertions.assertNotEquals(resetCorrelationUuid, originalCorrelationUuid, "The original correlation Uuid is the same")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        try
        {
            for(SourceDatapodObject datapodObject : datapodObjects)
            {
                datapodObject.delete();
            }
            datapodObjects.clear();
        }
        finally
        {
            disconnect(connection);
        }
    }

    @BeforeEach
    public void connectToDatabase()
    {
        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }
}
