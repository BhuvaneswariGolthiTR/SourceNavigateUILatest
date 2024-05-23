package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.google.common.collect.Multimap;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyDateSearchMultipleDocumentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsxFileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.google.common.collect.ArrayListMultimap;

public class HierarchyDateSearchMultipleDocumentsRiskTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-4002,4496/JETS-23453,23454 <br>
     * SUMMARY - This test goes to Hierarchy -> Reports -> Hierarchy Date Search (Multiple Documents) and verifies that if we click submit
     * without any other input that a "Report name mustn't be empty" alert appears  <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void hierarchyDateSearchClickSubmitNoDataInputRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        boolean didHierarchyDateSearchMultipleDocumentsPageAppear = hierarchyMenu().goToHierarchyReportsDateSearchMultipleDocuments();
        Assertions.assertTrue(didHierarchyDateSearchMultipleDocumentsPageAppear, "The Date Search Multiple Documents Page did not appear when it should have");

        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppear = AutoITUtils.verifyAlertTextAndAccept(true, "Report name mustn't be empty.\n" +
                "Nothing has been selected, please make a selection and resubmit.");
        Assertions.assertTrue(didExpectedAlertAppear, "The expected alert appeared");
    }

    /**
     * STORY/BUG - HALCYONST-4002,4496/JETS-23453,23454 <br>
     * SUMMARY - This test goes to Hierarchy -> Reports -> Hierarchy Date Search (Multiple Documents) and verifies that the File/Report Name length limit. Verifying that it is 50 characters <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void hierarchyDateSearchFileReportNameLengthLimitRiskTest()
    {
        String fileReportName = "teststest1teststest2teststest3teststest4teststest5teststest6";
        String expectedFileReportName = "teststest1teststest2teststest3teststest4teststest5";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean didHierarchyDateSearchMultipleDocumentsPageAppear = hierarchyMenu().goToHierarchyReportsDateSearchMultipleDocuments();
        Assertions.assertTrue(didHierarchyDateSearchMultipleDocumentsPageAppear, "The Date Search Multiple Documents Page did not appear when it should have");

        hierarchyDateSearchMultipleDocumentsPage().setFileReportName(fileReportName);
        AutoITUtils.highlightAndCopyText();
        String copiedFileReportName = ClipboardUtils.getSystemClipboard();
        Assertions.assertEquals(expectedFileReportName, copiedFileReportName, "The file report name was not shortened to the expected length");

        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppear = AutoITUtils.verifyAlertTextAndAccept(true, "Nothing has been selected, please make a selection and resubmit.");
        Assertions.assertTrue(didExpectedAlertAppear, "The expected alert appeared");
    }

    /**
     * STORY/BUG - HALCYONST-4002,4496/JETS-23453,23454 <br>
     * SUMMARY - This test goes to Hierarchy -> Reports -> Hierarchy Date Search (Multiple Documents) and verifies that after selecting one of the radio buttons
     * and leaving the associated field blank and clicking submit, the appropriate alert appears <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void hierarchyDateSearchSetRadioButtonNoInputRiskTest()
    {
        String radioButtonSelected = "Report name mustn't be empty.\n%s";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean didHierarchyDateSearchMultipleDocumentsPageAppear = hierarchyMenu().goToHierarchyReportsDateSearchMultipleDocuments();
        Assertions.assertTrue(didHierarchyDateSearchMultipleDocumentsPageAppear, "The Date Search Multiple Documents Page did not appear when it should have");

        //Verification for Start date single entry alert
        hierarchyDateSearchMultipleDocumentsPage().selectStartDateSingleEntryRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForStartDateSingleEntry = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Start Date (single entry) is not valid."));

        //Verification for End Date single entry alert
        hierarchyDateSearchMultipleDocumentsPage().selectEndDateSingleEntryRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForEndDateSingleEntry = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "End Date (single entry) is not valid."));

        //Verification for Date Range Start Date left field alert
        hierarchyDateSearchMultipleDocumentsPage().selectDateRangeStartDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForDateRangeStartDateLeftField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Date range: Start Date (left date) is not valid."));

        //Verification for Date Range Start Date right field alert
        hierarchyDateSearchMultipleDocumentsPage().selectDateRangeStartDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().setDateRangeStartDateEntryFields(DateAndTimeUtils.getCurrentDateMMddyyyy(), "");
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForDateRangeStartDateRightField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Date range: Start Date (right date) is not valid."));
        hierarchyDateSearchMultipleDocumentsPage().clearTextEntryField(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeStartDateFromDateEntryField);

        //Verification for Date Range End Date left field alert
        hierarchyDateSearchMultipleDocumentsPage().selectDateRangeEndDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForDateRangeEndDateLeftField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Date range: End Date: (left date) is not valid."));

        //Verification for Date Range End Date right field alert
        hierarchyDateSearchMultipleDocumentsPage().selectDateRangeEndDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().setDateRangeEndDateEntryFields(DateAndTimeUtils.getCurrentDateMMddyyyy(), "");
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForDateRangeEndDateRightField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Date range: End Date: (right date) is not valid."));
        hierarchyDateSearchMultipleDocumentsPage().clearTextEntryField(HierarchyDateSearchMultipleDocumentsPageElements.dateRangeEndDateFromDateEntryField);

        //Verification for Modified By Date left field alert
        hierarchyDateSearchMultipleDocumentsPage().selectModifiedByDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForModifiedDateLeftField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Modified By Date (left date) is not valid."));

        //Verification for Modified By Date right field alert
        hierarchyDateSearchMultipleDocumentsPage().selectModifiedByDateRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().setModifiedByDateEntryFields(DateAndTimeUtils.getCurrentDateMMddyyyy(), "");
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForModifiedDateRightField = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "Modified By Date (right date) is not valid."));
        hierarchyDateSearchMultipleDocumentsPage().clearTextEntryField(HierarchyDateSearchMultipleDocumentsPageElements.modifiedByDateFromDateEntryField);

        //Verification for Modified By User alert
        hierarchyDateSearchMultipleDocumentsPage().selectModifiedByUserRadioButton();
        hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
        boolean didExpectedAlertAppearForModifiedByUser = AutoITUtils.verifyAlertTextContainsAndAccept(true, String.format(radioButtonSelected, "User ID mustn't be less than 5 characters."));

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(didExpectedAlertAppearForStartDateSingleEntry, "The alert for Start Date single Entry appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForEndDateSingleEntry, "The alert for End Date single entry appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForDateRangeStartDateLeftField, "The alert for Date Range Start Date left field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForDateRangeStartDateRightField, "The alert for Date Range Start Date right field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForDateRangeEndDateLeftField, "The alert for Date Range End Date left field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForDateRangeEndDateRightField, "The alert for Date Range End Date right field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForModifiedDateLeftField, "The alert for Modified Date left field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForModifiedDateRightField, "The alert for Modified Date right field appeared"),
            () -> Assertions.assertTrue(didExpectedAlertAppearForModifiedByUser, "The alert for Modified By User appeared")
        );
    }

    /**
     * STORY/BUG - HALCYONST-3088, HALCYONST-4201, JETS-23773, JETS-23452 <br>
     * SUMMARY - This test updates specific metadata values across multiple content sets,
     * and verifies that the changes to the data are reflected correctly in the Hierarchy Date Search (Multiple Documents) report results <br>
     * USER - RISK <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
        (
            {
                "Start Date",
                "End Date",
                "Date range: Start Date",
                "Date range: End Date",
                "Modified Date",
                "Modified By"
            }
        )
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void hierarchyDateSearchingMultipleContentSetsReportRiskTest(String metadata)
    {
        Multimap<Integer, String> nodeUuids = ArrayListMultimap.create();
        //Crown Dependencies Nodes
        nodeUuids.put(1, "IA79FE94029A011E6A46DB8CA3AB007EB");
        nodeUuids.put(1, "IA79FE94129A011E6A46DB8CA3AB007EB");
        //FINRA Nodes
        nodeUuids.put(2, "IA7762050705011E692C1B8CA3AB007EB");
        nodeUuids.put(2, "IA778DF70705011E692C1B8CA3AB007EB");

        String[] jurisdictionArray = {"Crown Dependencies", "FINRA"};
        String[] jurisdictionAbbreviationArray = {"RB.CDRb", "RBFINRA"};
        Boolean[] reportResultsArray = new Boolean[jurisdictionArray.length];
        String reportType = "Hierarchy Searching Report";
        String reportIdentifier = metadata + " Report Test" + DateAndTimeUtils.getCurrentDateAndTimeddHHmmss();
        String reportFileName = "hierarchy-searching-report.xlsx";
        String contentUuid;
        String metadataUpdate;
        String updatedDate = "";
        LinkedList<String> originalMetadataValues = new LinkedList<>();
        XSSFWorkbook reportDownload = null;

        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        //Getting original metadata values to be restored at the end of the test, and mocking new metadata values to be verified later in the test
        try
        {
            for(Map.Entry<Integer, String> node : nodeUuids.entries())
            {
                switch (metadata)
                {
                    case "Start Date":
                    case "Date range: Start Date":
                        originalMetadataValues.add(DateAndTimeUtils.convertDateToFormat(HierarchyDatabaseUtils.getLegisEffectiveStartDate(node.getValue(), connection),
                                DateAndTimeUtils.ddDashMMMdashYY_DATE_TIME_FORMAT));
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
                        metadataUpdate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();
                        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid, metadataUpdate, connection);
                        break;
                    case "End Date":
                    case "Date range: End Date":
//                        originalMetadataValues.add(DateAndTimeUtils.convertDateToFormat(HierarchyDatabaseUtils.getLegisEffectiveEndDate(node.getValue(), connection),
//                                DateAndTimeUtils.ddDashMMMdashYY_DATE_TIME_FORMAT));
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
                        metadataUpdate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();
                        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid, metadataUpdate, connection);
                        break;
                    case "Modified Date":
                        if(originalMetadataValues.isEmpty())
                        {
                            updatedDate = "1" + DateAndTimeUtils.getCurrentDateddMMMYYYY().substring(2) + " 1:00:00";
                        }
                        originalMetadataValues.add(HierarchyDatabaseUtils.getModifiedDate(node.getValue(), connection));
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
                        HierarchyDatabaseUtils.updateModifiedDate(contentUuid, updatedDate, connection);
                        break;
                    case "Modified By":
                        originalMetadataValues.add(HierarchyDatabaseUtils.getModifiedBy(node.getValue(), connection));
                        HierarchyDatabaseUtils.updateModifiedBy(node.getValue(), prodLegalUser().getUsername(), connection);
                        break;
                    default:
                        Assertions.fail("Given parameter is invalid");
                }
            }

            //Metadata updates are complete and main part of test begins here
            homePage().goToHomePage();
            loginPage().logIn();
            boolean didHierarchyDateSearchMultipleDatesPageAppear = hierarchyMenu().goToHierarchyReportsDateSearchMultipleDocuments();
            Assertions.assertTrue(didHierarchyDateSearchMultipleDatesPageAppear, "The Hierarchy Date Search (Multiple Documents) page did not appear when it should have");

            hierarchyDateSearchMultipleDocumentsPage().setFileReportName(reportIdentifier);
            switch (metadata)
            {
                case "Start Date":
                    hierarchyDateSearchMultipleDocumentsPage().selectStartDateSingleEntryRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setStartDateSingleEntryDateEntryField(DateAndTimeUtils.getCurentDateMDYYYY());
                    break;
                case "End Date":
                    hierarchyDateSearchMultipleDocumentsPage().selectEndDateSingleEntryRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setEndDateSingleEntryDateEntryField(DateAndTimeUtils.getCurentDateMDYYYY());
                    break;
                case "Date range: Start Date":
                    hierarchyDateSearchMultipleDocumentsPage().selectDateRangeStartDateRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setDateRangeStartDateEntryFields(DateAndTimeUtils.getXdaysFromCurrentDateMMddyyy(1), DateAndTimeUtils.getCurrentDateMMddyyyy());
                    break;
                case "Date range: End Date":
                    hierarchyDateSearchMultipleDocumentsPage().selectDateRangeEndDateRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setDateRangeEndDateEntryFields(DateAndTimeUtils.getXdaysFromCurrentDateMMddyyy(1), DateAndTimeUtils.getCurrentDateMMddyyyy());
                    break;
                case "Modified Date":
                    //Note: "/01/" and "/02/" are being statically used to ensure that the report is being created to pull in any documents modified between the 1st and 2nd dates of any given month and year this test is run in
                    //Working in parallel with the way we updated our metadata for modified date
                    hierarchyDateSearchMultipleDocumentsPage().selectModifiedByDateRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setModifiedByDateEntryFields(DateAndTimeUtils.getCurrentMonthMM() + "/01/" + DateAndTimeUtils.getCurrentYearyyyy(),
                            DateAndTimeUtils.getCurrentMonthMM() + "/02/" + DateAndTimeUtils.getCurrentYearyyyy());
                    break;
                case "Modified By":
                    hierarchyDateSearchMultipleDocumentsPage().selectModifiedByUserRadioButton();
                    hierarchyDateSearchMultipleDocumentsPage().setModifiedByUserEntryField(prodLegalUser().getUsername().toLowerCase());
                    break;
                default:
                    Assertions.fail("Given parameter is invalid");
            }

            hierarchyDateSearchMultipleDocumentsPage().clickMultipleJurisdictionsCheckbox();
            hierarchyDateSearchMultipleDocumentsPage().selectMultipleContentSets(jurisdictionArray);
            hierarchyDateSearchMultipleDocumentsPage().clickSubmitButton();
            hierarchyDateSearchMultipleDocumentsPage().waitForPageLoaded();
            ArrayList<String> workflowIDs = hierarchyDateSearchMultipleDocumentsPage().getWorkflowIDs();

            int i = 0;
            for(String workflowID : workflowIDs)
            {
                //Changing content sets to see other reports
                homePage().switchToPage();
                if(i > 0)
                {
                    homePage().setMyContentSet(jurisdictionArray[i]);
                }
                toolsMenu().goToWorkflowReportingSystem();
                workflowSearchPage().setWorkflowID(workflowID);
                workflowSearchPage().clickFilterButton();
                boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
                Assertions.assertTrue(workflowFinished, String.format("The workflow with id: %s did NOT finish", workflowID));

                workflowSearchPage().openFirstWorkflow();
                workflowDetailsPage().expandWorkflowProperties();
                String workflowContentSetAbbreviation = workflowPropertiesPage().getWorkflowPropertyValueByName("contentSetAbbreviation");
                Assertions.assertEquals(jurisdictionAbbreviationArray[i], workflowContentSetAbbreviation,"The content abbreviation in the workflow properties is incorrect");

                workflowSearchPage().closeCurrentWindowIgnoreDialogue();
                homePage().switchToPage();
                auditsMenu().goToReportCentral();
                reportCentralFiltersPage().setReportType(reportType);
                reportCentralFiltersPage().setReportIdentifier(reportIdentifier);
                reportCentralFiltersPage().setRequestersName(user().getReportCentralRequestersUsername());
                reportCentralPage().clickRefresh();
                boolean reportAppears = reportCentralGridPage().waitForExistenceOfWorkflow(reportType, reportIdentifier, (user().getLastname() + ", " + user().getFirstname()));
                Assertions.assertTrue(reportAppears, "The expected Hierarchy Searching Report did NOT appear");
                boolean reportCentralworkflowFinished = reportCentralGridPage().verifyFirstWorkflowFinishes();
                Assertions.assertTrue(reportCentralworkflowFinished,"The workflow finished");

                try
                {
                    reportCentralGridPage().clickReportIdentifierLink(reportIdentifier);
                    AutoITUtils.clickSaveOnIEPopupWithAutoIT();
                    //Giving the computer time to download the file
                    FileUtils.waitForFileToExistByFileNameRegex(FileUtils.DOWNLOAD_FOLDER_PATH, "\\" + reportFileName, DateAndTimeUtils.ONE_AND_A_HALF_SECONDS);
                    //Creating a worksheet object that we will parse to verify the expected node uuid values appear
                    reportDownload = XlsxFileUtils.openFileAsWorkbook(FileUtils.DOWNLOAD_FOLDER_PATH + "\\" + reportFileName);
                    Assertions.assertNotNull(reportDownload, "The excel report was downloaded successfully");

                    XSSFSheet excelReport = reportDownload.getSheetAt(0);
                    List<String> nodeUuidsInReport = XlsxFileUtils.getAllValuesInColumnByIndex(excelReport, 0);
                    reportResultsArray[i] = nodeUuidsInReport.containsAll(nodeUuids.get(i + 1));
                    reportCentralGridPage().closeCurrentWindowIgnoreDialogue();
                    i++;
                }
                finally
                {
                    //Cleaning up the file after we are done with it
                    XlsxFileUtils.closeAndDeleteDocument(reportDownload, reportFileName);
                }
            }
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(reportResultsArray[0], String.format("Report for %s did not include all expected nodes", jurisdictionArray[0])),
                () -> Assertions.assertTrue(reportResultsArray[1], String.format("Report for %s did not include all expected nodes", jurisdictionArray[1]))
            );
        }
        //Cleanup starts here
        finally
        {
            //Database cleanup steps
            for(Map.Entry<Integer, String> node : nodeUuids.entries())
            {
                //Cleaning up Start Date updates
                switch (metadata)
                {
                    //Cleaning up Start Date updates
                    case "Start Date":
                    case "Date range: Start Date":
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
                        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid, originalMetadataValues.remove(), connection);
                        break;
                    //Cleaning up End Date updates
                    case "End Date":
                    case "Date range: End Date":
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
//                        HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid, originalMetadataValues.remove(), connection);
                        break;
                    //Cleaning up Modified Date updates
                    case "Modified Date":
                        contentUuid = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(node.getValue(), connection);
                        HierarchyDatabaseUtils.updateModifiedDate(contentUuid, originalMetadataValues.remove(), connection);
                        break;
                    //Cleaning up Modified By updates
                    case "Modified By":
                        HierarchyDatabaseUtils.updateModifiedBy(node.getValue(), originalMetadataValues.remove(), connection);
                        break;
                    default:
                        Assertions.fail("If you get this to print out you are a wizard");
                }
            }
            BaseDatabaseUtils.disconnect(connection);
        }
    }
}