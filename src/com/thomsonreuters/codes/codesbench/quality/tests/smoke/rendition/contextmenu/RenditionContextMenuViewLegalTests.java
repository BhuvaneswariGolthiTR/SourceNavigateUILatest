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

public class RenditionContextMenuViewLegalTests extends TestService
{
    SourceDatapodObject prep;
    SourceDatapodObject apv;
    Connection connection;

    private String createAPVDoc()
    {
        apv = SourceDataMockingNew.Iowa.Small.APV.insert();
        String renditionUuid = apv.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    private String createPrepDoc()
    {
        prep = SourceDataMockingNew.Iowa.Small.PREP.insert();
        String renditionUuid = prep.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);

        return renditionUuid;
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Opens Instruction Notes page from the context menu and verifies the page is read only. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewRenditionNotesLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //View rendition notes and verify notes is opened in read only
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionNotes();

        boolean renditionNotesTextBoxIsReadOnly = instructionsNotesPage().isRenditionNotesReadOnly();
        Assertions.assertTrue(renditionNotesTextBoxIsReadOnly, "Text box is not read only");

        instructionsNotesPage().clickCancel();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Source Front' from the context menu and verifies the editor opens. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceFrontLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Select 'Source Front' from view context menu and verify the editor opens
        sourceNavigateGridPage().rightClickFirstRendition();

        boolean editorWindowAppeared = renditionContextMenu().viewSourceFront();
        Assertions.assertTrue(editorWindowAppeared, "Editor did not appear");

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Rendition XML' from the context menu and verifies the XML editor opens and validates <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewXmlLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Right click rendition and select 'Rendition XML' from the view context menu
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean editorWindowAppeared = renditionContextMenu().viewRenditionXML();
        Assertions.assertTrue(editorWindowAppeared, "Raw XML editor window didn't appear");

        boolean renditionValidated = sourceRawXmlEditorPage().clickValidate();
        Assertions.assertTrue(renditionValidated, "Rendition doesn't validate");

        sourceRawXmlEditorPage().clickClose();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Tabular WL Preview' from the context menu and verifies a cwbTabularWestlawErender workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularWlPreviewLegalTest()
    {
        //TODO needs a BEIT mnemonic paragraph

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2012");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("524");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewTabularWLPreview();
        tabularWLPreviewPage().clickOKButton();

        //Navigate to workflow reporting system page and filter for workflow, then verify the workflow finished
        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbTabularWestlawErender",
                "Iowa (Development)", "", user().getUsername().toUpperCase());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished,  String.format("Tabular WL Preview workflow: %s successfully finished", workflowId));
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'View Rendition' from the context menu and verifies the editor opens. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewRenditionLegalTest()
    {
        String renditionUuid = createPrepDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //View rendition and verify notes is opened in read only
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean editorWindowAppeared = renditionContextMenu().viewRendition();
        Assertions.assertTrue(editorWindowAppeared, "Editor window did not appear");

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Counts delta count of first item in the grid then selects 'Deltas Affecting Same Target' from the context menu and verifies
     *           the amount of deltas in the Delta Affecting Same Target tab matches the delta count in the grid. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltasAffectingSameTargetLegalTest()
    {
        //TODO Needs two renditions with deltas affecting the same target
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Validation: None
        //MultipleDuplicate: None
        //Deleted: Not Deleted
        //ContentSet: Iowa(Development)
        //Year: 2012
        //Status: PREP
        //DocNumber: 451
        //ContentType: LAW
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2012");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("451");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("LAW");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Get delta count of first item and verify that value matches the amount of deltas affecting same target
        int deltaCountInGrid = sourceNavigateGridPage().getDeltaCountOfFirstItem();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewDeltasAffectingSameTarget();

        int deltaCountByRows = sourceNavigateGridPage().getNumberOfRenditions();

        Assertions.assertEquals(deltaCountInGrid, deltaCountByRows, "Delta count given by grid was not the same as deltas located in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Source End' from the context menu and verifies the editor opens. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceEndLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Select 'Source End' from view context menu and verify the editor opens
        sourceNavigateGridPage().rightClickFirstRendition();

        boolean editorWindowAppeared = renditionContextMenu().viewSourceEnd();
        Assertions.assertTrue(editorWindowAppeared, "Editor did not appear");

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Rendition Print Preview' from the context menu and verifies a cwbTabularPrintPreview workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void printPreviewLegalTest()
    {
        String renditionUuid = createAPVDoc();
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Right click rendition and select 'Print Preview' then click the workflow that appears
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewPrintPreview();
        printPreviewPage().clickWorkflowIDLink();

        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "Rendition Print Preview workflow didn't successfully finished");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Selects 'Tabular Print Preview' from the context menu and verifies a cwbTabularPrintPreview workflow finishes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void tabularPrintPreviewLegalTest()
    {
        String renditionUuid = createAPVDoc();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Right click rendition and select 'Tabular Print Preview' then click the workflow that appears
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewTabularPrintPreview();
        tabularPrintPreviewPage().clickWorkflowIDLink();

        boolean workflowFinished = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(workflowFinished, "Tabular Print Preview workflow didn't successfully finished");
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(prep != null)
        {
            prep.delete();
        }
        if(apv != null)
        {
            apv.delete();
        }
    }
}
