package com.thomsonreuters.codes.codesbench.quality.tests.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.PropertiesElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.QueryNoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsxFileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class QueryNoteReportAngularMenuOptionsTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    String noteUuid;

    @BeforeEach
    public void setUp()
    {
        String initialText = "testing text";
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getChapters().get(0).getNodeUUID();
        QueryNoteDatabaseUtils.insertQueryNote(connection, user().getUsername(), nodeUuid, ContentSets.IOWA_DEVELOPMENT.getCode());
        noteUuid = QueryNoteDatabaseUtils.getNoteUuid(nodeUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        QueryNoteDatabaseUtils.updateQueryNoteText(connection, noteUuid, initialText);
    }

    @AfterEach
    public void cleanUp()
    {
        if (noteUuid != null)
        {
            QueryNoteDatabaseUtils.deleteQueryNote(connection, noteUuid);
        }
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }

    /**
     * Story: N/A
     * Summary: Verify that find document in hierarchy menu option brings the user to the correct node in hierarchy navigate
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void findInHierarchyTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        TableColumns targetValueColumn = TableColumns.QUERY_NOTE_REPORT_VALUE;
        String volsFilterValue = "9999";
        int rowToTest = 0;

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Filter for the query note to test and grab the note value
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
        gridHeaderFiltersPage().setFilterValue(volsFilterValue);
        String noteValue = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest, targetValueColumn);

        //Click find document in hierarchy from the context menu for the mocked up note, verify it brings to the correct note
        queryNoteReportAngularTablePage().clickCellTextByRowAndColumn(rowToTest, volsColumn);
        queryNoteReportAngularTablePage().openContextMenuForRow(rowToTest);
        boolean isHierarchyPageLoaded = queryNoteReportAngularContextMenu().findDocumentInHierarchy();
        Assertions.assertTrue(isHierarchyPageLoaded, "Hierarchy page doesn't appear when it should");
        boolean broughtToNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(noteValue);
        Assertions.assertTrue(broughtToNodeAfterFindDocumentInHierarchy, "Brought to node after Find Document in Hierarchy");
    }

    /**
     * Story: ADO-289569
     * Summary: Verify that exporting a query note to excel creates an excel file populated with the query note data
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void exportQueryNoteToExcelTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        String volsFilterValue = "9999";
        int rowToTest = 0;

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Filter for the query note to test
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
        gridHeaderFiltersPage().setFilterValue(volsFilterValue);

        //Grab table data, then open export to excel note page for the selected row
        List<String> dataFromTable = queryNoteReportAngularTablePage().getValuesForPageByRow(rowToTest);
        String breadCrumbColumnFormatted = dataFromTable.get(4).replaceAll("--", "-");
        dataFromTable.set(4, breadCrumbColumnFormatted);
        queryNoteReportAngularTablePage().clickCellTextByRowAndColumn(rowToTest, volsColumn);
        queryNoteReportAngularTablePage().openContextMenuForRow(rowToTest);
        boolean isQueryNoteExportToExcelModalLoaded = queryNoteReportAngularContextMenu().exportToExcel();
        Assertions.assertTrue(isQueryNoteExportToExcelModalLoaded, "Export to Excel popup doesn't appear when it should");

        //Open workflow link and grab excel file url
        queryNoteReportAngularExportPage().clickWorkflowLink();
        boolean didWorkflowFinish = workflowDetailsPage().verifyWorkflowFinished();
        Assertions.assertTrue(didWorkflowFinish, "Workflow didn't finish");
        workflowPropertiesPage().clickWorkflowPropertiesButton();
        String queryNoteReportExcelLink = workflowPropertiesPage().getPropertyValue(PropertiesElements.Property.QUERY_NOTE_REPORT_LINK);
        workflowPropertiesPage().closeCurrentWindowIgnoreDialogue();

        //Get query note data from excel sheet and compare to the query note page data
        File excelFile = FileUtils.loadFileFromUrl(queryNoteReportExcelLink);
        XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFile);
        XSSFSheet excelReport = workbook.getSheetAt(0);
        List<String> excelNodeValues = XlsxFileUtils.getAllValuesInRowByIndex(excelReport, rowToTest + 1);
        boolean excelValuesSameAsQnrGridValues = dataFromTable.equals(excelNodeValues);
        Assertions.assertTrue(excelValuesSameAsQnrGridValues, "Excel file should appear with the same cell values as the query note grid page, but doesn't");
    }

    /**
     * Story: Query Note: Edit Action Date
     * Summary: Verify that the 'edit action date' context menu option opens the Edit Action Date modal and
     * correctly updates the action date for the selected query notes on submit
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editActionDatesTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        TableColumns actionDateColumn = TableColumns.QUERY_NOTE_REPORT_ACTION_DATE;
        String initialText = "testing text";
        String volsFilterValue = "9999";
        String newActionDate = "09092099";
        String expectedActionDate = "09/09/2099";
        int rowToTest1 = 0;
        int rowToTest2 = 1;

        //Mock up a second query note to use
        String nodeUuid2 = datapodObject.getSections().get(0).getNodeUUID();
        QueryNoteDatabaseUtils.insertQueryNote(connection, user().getUsername(), nodeUuid2, ContentSets.IOWA_DEVELOPMENT.getCode());
        String noteUuid2 = QueryNoteDatabaseUtils.getNoteUuid(nodeUuid2, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        QueryNoteDatabaseUtils.updateQueryNoteText(connection, noteUuid2, initialText);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            toolsMenu().goToQueryNoteReportAngular();

            //Filter for the query note to test
            queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
            gridHeaderFiltersPage().setFilterValue(volsFilterValue);

            //Open the action date model for the mocked up notes and set a new action date, save the changes
            queryNoteReportAngularTablePage().selectGivenRowsInTable(volsColumn, rowToTest1, rowToTest2);
            queryNoteReportAngularTablePage().rightClickCellTextByRowAndColumn(rowToTest1, volsColumn);
            queryNoteReportAngularContextMenu().editActionDates();
            queryNoteReportAngularEditActionDatesPage().clearAndSendKeysToNewActionDateTextbox(newActionDate);
            queryNoteReportAngularEditActionDatesPage().clickEditAndSaveButton();

            //Verify the action dates for the selected notes are updated in the QNR grid page
            String actionDate1Updated = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest1, actionDateColumn);
            String actionDate2Updated = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest2, actionDateColumn);
            boolean wasActionDate1Updated = actionDate1Updated.equals(expectedActionDate);
            boolean wasActionDate2Updated = actionDate2Updated.equals(expectedActionDate);
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(wasActionDate1Updated, "The action date of the first note was not updated when it should have been"),
                () -> Assertions.assertTrue(wasActionDate2Updated, "The action date of the second note was not updated when it should have been")
            );
        }
        finally
        {
            QueryNoteDatabaseUtils.deleteQueryNote(connection, noteUuid2);
        }
    }
}