package com.thomsonreuters.codes.codesbench.quality.tests.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.QueryNoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class QueryNoteReportAngularEditResolveDeleteTests extends TestService
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
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
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
        if(connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }

    /**
     * Story: ADO-289566
     * Summary: Verify that editing a query note from the Query Note Report angular page updates the query note in the table correctly
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editQueryNoteTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        TableColumns queryTextColumn = TableColumns.QUERY_NOTE_REPORT_QUERY_TEXT;
        String volsFilterValue = "9999";
        String textToAdd = "coolness";
        int rowToTest = 0;

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Filter for the query note to test
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
        gridHeaderFiltersPage().setFilterValue(volsFilterValue);

        //Grab table data, then open edit query note page for the selected row
        List<String> dataFromTable = queryNoteReportAngularTablePage().getValuesForPageByRow(rowToTest);
        queryNoteReportAngularTablePage().clickCellTextByRowAndColumn(rowToTest, volsColumn);
        queryNoteReportAngularTablePage().openContextMenuForRow(rowToTest);
        boolean isQueryNoteEditModalLoaded = queryNoteReportAngularContextMenu().editQueryNote();
        Assertions.assertTrue(isQueryNoteEditModalLoaded, "Query Note Edit popup doesn't appear when it should");

        //Check values in page against table values to check that correct data is shown
        boolean volsIsExpected = dataFromTable.get(2).equals(queryNoteReportAngularEditPage().getVols()); //Type
        boolean codeIsExpected = dataFromTable.get(3).equals(queryNoteReportAngularEditPage().getCode()); //Code
        boolean keywordIsExpected = dataFromTable.get(5).equals(queryNoteReportAngularEditPage().getKeyword()); //Keyword
        boolean valueIsExpected = dataFromTable.get(6).equals(queryNoteReportAngularEditPage().getValue()); //Value
        boolean startDateIsExpected = dataFromTable.get(9).equals(queryNoteReportAngularEditPage().getStartDate()); //Start Date
        boolean endDateIsExpected =queryNoteReportAngularResolvePage().getEndDate().equals("no date") ; //End Date
        boolean queryNoteStatusIsExpected = dataFromTable.get(1).equals(queryNoteReportAngularEditPage().getQueryNoteStatus()); //Query Note Status
        boolean resolvedByIsExpected = dataFromTable.get(16).equals(queryNoteReportAngularEditPage().getResolvedBy());
        //Resolved By
        boolean resolvedDateIsExpected =queryNoteReportAngularEditPage().getResolvedDate().contains(dataFromTable.get(15));//Resolved Date
        boolean resolvedCommentsIsExpected = dataFromTable.get(17).equals(queryNoteReportAngularEditPage().getResolvedComment()); //Resolved Comments
        boolean createdDateIsExpected = dataFromTable.get(7).equals(queryNoteReportAngularEditPage().getCreatedDate()); //Created Date
        String queryNoteTextValue = dataFromTable.get(14);
        boolean queryNoteTextIsExpected = queryNoteTextValue.equals(queryNoteReportAngularEditPage().getQueryNoteText()); //Query Note Text

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(volsIsExpected, "vols in the page is not the same as in the table"),
            () -> Assertions.assertTrue(codeIsExpected, "code in the page is not the same as in the table"),
            () -> Assertions.assertTrue(keywordIsExpected, "keyword in the page is not the same as in the table"),
            () -> Assertions.assertTrue(valueIsExpected, "value in the page is not the same as in the table"),
            () -> Assertions.assertTrue(startDateIsExpected, "startDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(endDateIsExpected, "endDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteStatusIsExpected, "queryNoteStat in the page is not the same as in the table"),
            () -> Assertions.assertTrue(resolvedByIsExpected, "resolvedBy in the page is not the same as in the table"),
            () -> Assertions.assertTrue(resolvedDateIsExpected, "resolvedDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(resolvedCommentsIsExpected, "resolvedComments in the page is not the same as in the table"),
            () -> Assertions.assertTrue(createdDateIsExpected, "createdDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteTextIsExpected, "query note text in the page is not the same as in the table")
        );

        queryNoteReportAngularEditPage().clearAndSendKeysToQueryNoteTextbox("coolness");
        queryNoteReportAngularEditPage().clickSaveButton();
        String textValueAfterEdit = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest, queryTextColumn);
        boolean isQueryTextUpdated = textValueAfterEdit.equals(textToAdd);
        Assertions.assertTrue(isQueryTextUpdated, "Query note text was not updated correctly");
    }

    /**
     * Story: ADO-289566
     * Summary: Verify that resolving a query note from the Query Note Report angular page works as expected
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void resolveQueryNoteTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        TableColumns statusColumn = TableColumns.QUERY_NOTE_REPORT_STATUS;
        TableColumns resolvedCommentColumn = TableColumns.QUERY_NOTE_REPORT_RESOLVED_COMMENT;
        String volsFilterValue = "9999";
        String queryNoteStatus = "RESOLVED";
        String queryNoteResolutionComment = "Deleted Doc";
        int rowToTest = 0;

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Filter for the query note to test
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
        gridHeaderFiltersPage().setFilterValue(volsFilterValue);

        //grab table data, then open resolve query note page for the selected row
        List<String> dataFromTable = queryNoteReportAngularTablePage().getValuesForPageByRow(rowToTest);
        queryNoteReportAngularTablePage().clickCellTextByRowAndColumn(rowToTest, volsColumn);
        queryNoteReportAngularTablePage().openContextMenuForRow(rowToTest);
        boolean isQueryNoteResolveModalLoaded = queryNoteReportAngularContextMenu().resolveQueryNote();
        Assertions.assertTrue(isQueryNoteResolveModalLoaded, "Query Note Resolve popup doesn't appear when it should");

        //Check values in page against table values to check that correct data is shown
        boolean typeIsExpected = dataFromTable.get(0).equals(queryNoteReportAngularResolvePage().getType()); //Type
        boolean volsIsExpected = dataFromTable.get(2).equals(queryNoteReportAngularResolvePage().getVols()); //Vols
        boolean codeIsExpected = dataFromTable.get(3).equals(queryNoteReportAngularResolvePage().getCode()); //Code
        boolean keywordIsExpected = dataFromTable.get(5).equals(queryNoteReportAngularResolvePage().getKeyword()); //Keyword
        boolean valueIsExpected = dataFromTable.get(6).equals(queryNoteReportAngularResolvePage().getValue()); //Value
        boolean startDateIsExpected = dataFromTable.get(9).equals(queryNoteReportAngularResolvePage().getStartDate());//Start Date
        boolean endDateIsExpected = queryNoteReportAngularResolvePage().getEndDate().equals("no date"); //End Date
        boolean queryNoteTextIsExpected = (dataFromTable.get(14)).equals(queryNoteReportAngularResolvePage().getQueryNoteText()); //Query Note Text
        boolean actionDateIsExpected = dataFromTable.get(8).equals(queryNoteReportAngularResolvePage().getActionDate()); //Action Date
        boolean queryNoteStatusIsExpected = dataFromTable.get(1).equals(queryNoteReportAngularResolvePage().getQueryNoteStatus()); //Query Note Status
        boolean createdDateIsExpected = dataFromTable.get(7).equals(queryNoteReportAngularResolvePage().getCreatedDate()); //Created Date

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(typeIsExpected, "type in the page is not the same as in the table"),
            () -> Assertions.assertTrue(volsIsExpected, "vols in the page is not the same as in the table"),
            () -> Assertions.assertTrue(codeIsExpected, "code in the page is not the same as in the table"),
            () -> Assertions.assertTrue(keywordIsExpected, "keyword in the page is not the same as in the table"),
            () -> Assertions.assertTrue(valueIsExpected, "value in the page is not the same as in the table"),
            () -> Assertions.assertTrue(startDateIsExpected, "startDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(endDateIsExpected, "endDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteTextIsExpected, "query note text in the page is not the same as in the table"),
            () -> Assertions.assertTrue(actionDateIsExpected, "action date in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteStatusIsExpected, "queryNoteStatus in the page is not the same as in the table"),
            () -> Assertions.assertTrue(createdDateIsExpected, "created date in the page is not the same as in the table")
        );

        //Update query note text, verify the table is updated accordingly
        queryNoteReportAngularResolvePage().selectQueryNoteStatus(queryNoteStatus);
        queryNoteReportAngularResolvePage().selectQueryNoteResolutionCommentFromDropdown(queryNoteResolutionComment);
        queryNoteReportAngularEditPage().clickSaveButton();
        String statusAfterResolve = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest, statusColumn);
        boolean isStatusUpdated = statusAfterResolve.equals(queryNoteStatus);
        queryNoteReportAngularTablePage().scrollToRight("1000");
        String resolvedCommentAfterResolve = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest, resolvedCommentColumn);
        boolean isResolvedCommentUpdated = resolvedCommentAfterResolve.equals(queryNoteResolutionComment);
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isStatusUpdated, "Status column should be updated to RESOLVED"),
            () -> Assertions.assertTrue(isResolvedCommentUpdated, "Resolved Comment column should be updated to Deleted Doc")
        );
    }

    /**
     * Story: ADO-289566
     * Summary: Verify that deleting a query note from the Query Note Report angular page works as expected
     * User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void deleteQueryNoteTest()
    {
        TableColumns volsColumn = TableColumns.QUERY_NOTE_REPORT_VOLS;
        TableColumns statusColumn = TableColumns.QUERY_NOTE_REPORT_STATUS;
        String volsFilterValue = "9999";
        String queryNoteStatus = "DELETED";
        int rowToTest = 0;

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Filter for the query note to test
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(volsColumn);
        gridHeaderFiltersPage().setFilterValue(volsFilterValue);

        //grab table data, then open delete query note page for the selected row
        List<String> dataFromTable = queryNoteReportAngularTablePage().getValuesForPageByRow(rowToTest);
        queryNoteReportAngularTablePage().clickCellTextByRowAndColumn(rowToTest, volsColumn);
        queryNoteReportAngularTablePage().openContextMenuForRow(rowToTest);
        boolean isQueryNoteDeleteModalLoaded = queryNoteReportAngularContextMenu().deleteQueryNote();
        Assertions.assertTrue(isQueryNoteDeleteModalLoaded, "Query Note Delete popup doesn't appear when it should");

        //Check values in page against table values to check that correct data is shown
        boolean typeIsExpected = dataFromTable.get(0).equals(queryNoteReportAngularResolvePage().getType()); //Type
        boolean volsIsExpected = dataFromTable.get(2).equals(queryNoteReportAngularResolvePage().getVols()); //Vols
        boolean codeIsExpected = dataFromTable.get(3).equals(queryNoteReportAngularResolvePage().getCode()); //Code
        boolean keywordIsExpected = dataFromTable.get(5).equals(queryNoteReportAngularResolvePage().getKeyword()); //Keyword
        boolean valueIsExpected = dataFromTable.get(6).equals(queryNoteReportAngularResolvePage().getValue()); //Value
        boolean startDateIsExpected = dataFromTable.get(9).equals(queryNoteReportAngularResolvePage().getStartDate()); //Start Date
        boolean endDateIsExpected = queryNoteReportAngularResolvePage().getEndDate().equals("no date"); //End Date
        boolean queryNoteTextIsExpected = (dataFromTable.get(14)).equals(queryNoteReportAngularResolvePage().getQueryNoteText()); //Query Note Text
        boolean actionDateIsExpected = dataFromTable.get(8).equals(queryNoteReportAngularResolvePage().getActionDate()); //Action Date
        boolean queryNoteStatusIsExpected = dataFromTable.get(1).equals(queryNoteReportAngularResolvePage().getQueryNoteStatus()); //Query Note Status
        boolean createdDateIsExpected = dataFromTable.get(7).equals(queryNoteReportAngularResolvePage().getCreatedDate()); //Created Date

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(typeIsExpected, "type in the page is not the same as in the table"),
            () -> Assertions.assertTrue(volsIsExpected, "vols in the page is not the same as in the table"),
            () -> Assertions.assertTrue(codeIsExpected, "code in the page is not the same as in the table"),
            () -> Assertions.assertTrue(keywordIsExpected, "keyword in the page is not the same as in the table"),
            () -> Assertions.assertTrue(valueIsExpected, "value in the page is not the same as in the table"),
            () -> Assertions.assertTrue(startDateIsExpected, "startDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(endDateIsExpected, "endDate in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteTextIsExpected, "query note text in the page is not the same as in the table"),
            () -> Assertions.assertTrue(actionDateIsExpected, "action date in the page is not the same as in the table"),
            () -> Assertions.assertTrue(queryNoteStatusIsExpected, "queryNoteStatus in the page is not the same as in the table"),
            () -> Assertions.assertTrue(createdDateIsExpected, "created date in the page is not the same as in the table")
        );

        //Update query note text, verify the table is updated accordingly
        queryNoteReportAngularDeletePage().clickDeleteButton();
        String statusAfterDelete = queryNoteReportAngularTablePage().getCellTextByRowAndColumn(rowToTest, statusColumn);
        boolean isStatusUpdated = statusAfterDelete.equals(queryNoteStatus);
        Assertions.assertTrue(isStatusUpdated, "Status column should be updated to DELETED");
    }
}
