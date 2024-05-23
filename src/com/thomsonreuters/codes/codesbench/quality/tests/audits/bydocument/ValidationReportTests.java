package com.thomsonreuters.codes.codesbench.quality.tests.audits.bydocument;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMockingConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.desktop.DesktopUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.sikuli.SikuliUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sikuli.script.*;

import java.io.File;
import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils.closePDF;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.AUDIT_SINGLE_PAGE_LOCATION_PDF;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
/**
 * This class is used to test the validation report and validation flag in content summary.
 */
public class ValidationReportTests extends TestService
{

    File auditPDF;

    final String volumeNumber = "9999";

    String auditIdentifier;

    Connection connection;

    boolean dataMockedUp = false;

    String modifiedFrom;

    String iowaContentNumber = ContentSets.IOWA_DEVELOPMENT.getCode();

    HierarchyDatapodObject datapodObject = null;

    File fileToDelete;

    int pageOfValidationReportSection = 1;

    int pageOfDocumentSummary = 0;


    @BeforeEach
    public void auditMockUpBuild()
    {
        if(!dataMockedUp)
        {
            dataMockedUp = true;
            String testInformation = TestSetupEdge.getDisplayName().split("Arguments: ")[1];
            connection = CommonDataMocking.connectToDatabase(environmentTag);
            String userName = user().getUsername();
            datapodObject = AuditsDataMocking.mockUpForValidationFlagTests(connection, userName, testInformation);
            auditIdentifier = "automatedAudit" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
            processAuditHelperContentOnly(auditIdentifier,testInformation);
            auditPDF = new File(String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
            PDFUtils.createPDDocument(auditPDF);
            logger.information("Saving audit file at path: " + String.format(PDFElements.AUDIT_SAVING_LOCATION_PDF, auditIdentifier));
        }
    }

    /**
     * STORY/BUG -  HALCYONST-13924,10436 <br>
     * SUMMARY -  Verifies that the validation flag in the validation report section is displaying correctly in the audit pdf <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Clean", "Error", "Warning", "Deleted", "Info", "DTD Validation Error", "Data Validation Error"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationFlagInValidationReportSectionTest(String validationFlag)
    {
        Screen s = new Screen();
        boolean doesValidationFlagMatch = false;
        String actualTextFromValidationSection;
        String expectedTextFromValidationSection = "";
        double minimumSimilarityForPattern = 0.2;
        PDFUtils.saveFileWithOnlyOnePageFromPDDocument(pageOfValidationReportSection);
        fileToDelete = auditPDF;
        auditPDF = new File(AUDIT_SINGLE_PAGE_LOCATION_PDF);
        closePDF(); //close previous PDF
        PDFUtils.createPDDocument(auditPDF);
        if (DesktopUtils.isDesktopSupported())
        {
            DesktopUtils.openFileOnMonitor(auditPDF);
            RobotUtils.delayFiveSeconds();
            PDFUtils.switchToPage(1);
            actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
            Match validationFlagMatch;
            Pattern validationFlagPattern = null;
            switch (validationFlag)
            { //if you change the way mocked up nodes are created, expectedTextFromValidationSection might change
                case "Clean":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.CLEAN_VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nClean\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToCleanValidationFlagInNodeSection));
                    break;
                case "Error":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nError\r\nparent's start effective date is after child's and there is no previous parent sibling \r\nfound.\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToErrorValidationFlagInNodeSection));
                    break;
                case "Warning":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nWarning\r\nretro data has current version for hid not found in shs. node marked as extra. verify \r\ndates.\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToWarningValidationFlagInNodeSection));
                    break;
                case "Deleted":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.DELETED_VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\n Node is deleted\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToDeletedValidationFlagInNodeSection));
                    break;
                case "Info":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nInfo\r\neither there is no sequence number or the sequence number is \r\nzero.\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToInfoValidationFlagInNodeSection));
                    break;
                case "Data Validation Error":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nError data validation failed with error\r\nWarning\r\ncontent check in was unable to extract keyword value for target \r\nnode\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToDataValidationErrorFlagInNodeSection));
                    break;
                case "DTD Validation Error":
                    actualTextFromValidationSection = PDFUtils.getTextFromArea(PDFElements.VALIDATION_REPORT_TEXT_AREA);
                    expectedTextFromValidationSection = "Validation Report\r\n \r\nError\r\nthe content is not valid against the current \r\ndtd\r\n";
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToDTDValidationErrorFlagInNodeSection));
                    break;
                default:
                    Assertions.fail("Unsupported validation flag case. Supported cases: Clean, Error, Warning, Deleted, Info,Data Validation Error, DTD Validation Error");
            }
            validationFlagPattern.similar(minimumSimilarityForPattern);
            validationFlagMatch = s.exists(validationFlagPattern, SikuliUtils.TEN_SECOND_TIMEOUT);
            double validationFlagMatchScore = validationFlagMatch.getScore();
            boolean doesValidationFlagTextMatch = (expectedTextFromValidationSection.equals(actualTextFromValidationSection));
            if(validationFlagMatchScore >= SikuliUtils.NINETY_PERCENT_MINIMUM_SIMILARITY_SCORE ||
                    (doesValidationFlagTextMatch && validationFlagMatchScore >= SikuliUtils.FIFTY_PERCENT_MINIMUM_SIMILARITY_SCORE))
            {
                doesValidationFlagMatch = true;
            }
            else
            {
                logger.warning("Score of Validation Flag Section image verification: " + validationFlagMatchScore);
                logger.warning("Does actual text from validation flag area equal expected text from validation flag are :" + doesValidationFlagTextMatch);
                doesValidationFlagMatch = false;
            }
            Assertions.assertTrue(doesValidationFlagMatch, "The image verification score is not over 0.9 or the text may not be equal to the expected text and the score is not over 0.5");

        }
    }

    /**
     * STORY/BUG -  HALCYONST-13923 <br>
     * SUMMARY -  Verifies that the validation flag in the Document Summary Section is displaying correctly in the audit pdf <br>
     * USER - Legal  <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource(value = {
            "Clean", "Error", "Warning", "Deleted", "Info"
    })
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationFlagInDocumentSummarySectionTest(String validationFlag)
    {
        Screen s = new Screen();
        PDFUtils.saveFileWithOnlyOnePageFromPDDocument(pageOfDocumentSummary);
        fileToDelete = auditPDF;
        auditPDF = new File(AUDIT_SINGLE_PAGE_LOCATION_PDF);
        closePDF(); //close previous PDF
        PDFUtils.createPDDocument(auditPDF);
        if (DesktopUtils.isDesktopSupported())
        {
            DesktopUtils.openFileOnMonitor(auditPDF);
            RobotUtils.delayFiveSeconds();
            Match validationFlagMatch;
            Pattern validationFlagPattern = null;
            switch (validationFlag)
            {
                case "Clean":
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToCleanValidationFlagInSummary));
                    break;
                case "Error":
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToErrorValidationFlagInSummary));
                    break;
                case "Warning":
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToWarningValidationFlagInSummary));
                    break;
                case "Deleted":
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToDeletedValidationFlagInSummary));
                    break;
                case "Info":
                    validationFlagPattern = new Pattern(FileUtils.getAbsoluteFilePath(AuditsDataMockingConstants.pathToInfoValidationFlagInSummary));
                    break;
                default:
                    Assertions.fail("Unsupported validation flag case. Supported cases: Clean, Error, Warning, Deleted, Info");
            }
            validationFlagMatch = s.exists(validationFlagPattern,SikuliUtils.TEN_SECOND_TIMEOUT);
            Assertions.assertTrue(validationFlagMatch.getScore() >= SikuliUtils.NINETY_PERCENT_MINIMUM_SIMILARITY_SCORE);
        }
    }

    public File processAuditHelperContentOnly(String auditIdentifier, String validationFlag)
    {
        String userName = user().getReportCentralRequestersUsername();
        String nodeUUID = AuditsDataMocking.getNodeUUID(1);
        String contentUUID = HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUUID,connection);
        switch(validationFlag)
        {
            case "Clean":
                HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "Error":
                HierarchyDatabaseUtils.setNodeToErrorValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "Warning":
                HierarchyDatabaseUtils.setNodeToWarningValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "Deleted":
                HierarchyDatabaseUtils.setNodeToDeletedValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "Info":
                HierarchyDatabaseUtils.setNodeToInfoValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "Data Validation Error":
                HierarchyDatabaseUtils.setNodeToDataErrorValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            case "DTD Validation Error":
                HierarchyDatabaseUtils.setNodeToDTDErrorValidationFlag(contentUUID,iowaContentNumber,connection);
                break;
            default:
                Assertions.fail("Unsupported validation flag case. Supported cases: Clean, Error, Warning, Deleted, Info,Data Validation Error, DTD Validation Error");
        }
        homePage().goToHomePage();
        loginPage().logIn();
        auditsMenu().goToAuditByDocument();

        auditByDocumentPage().selectVolumeByVolumeNumber(volumeNumber);
        auditByDocumentPage().clickAddOneDocumentButton();

        //This section is subject to change based on what kind of report you want to run
        auditByDocumentPage().checkContentAudit();
        auditByDocumentPage().uncheckHierarchyContext();
        auditByDocumentPage().uncheckVersionsAuditStarting();
        modifiedFrom = auditByDocumentPage().getCutOffDate();
        auditByDocumentPage().checkValidations();
        auditByDocumentPage().uncheckSources();
        auditByDocumentPage().uncheckNODReport();
        auditByDocumentPage().setAuditIdentifier(auditIdentifier);
        auditByDocumentPage().selectShortSameParagraphsRadioButton();

        auditByDocumentPage().selectClassificationRadioButton();

        auditByDocumentPage().clickNextButton();
        auditByDocumentPage().waitForPageLoaded();

        String keyword = HierarchyDatabaseUtils.getKeywordDefaultDisplayName(connection, nodeUUID);
        String valueOfNode = HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(0).getContentUUID(), ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String legisStartDate = HierarchyDatabaseUtils.getLegisStartEffectiveDate(connection, nodeUUID);

        auditByDocumentPage().selectDocumentByKeywordAndValueAndLegisEffectiveStartDate(keyword, valueOfNode, legisStartDate);
        auditByDocumentPage().clickAddOneDocumentButton();
        auditByDocumentPage().selectMostRecentWIPDocumentRadioButton();
        auditByDocumentPage().uncheckResearchReferencesCheckbox();
        auditByDocumentPage().clickProcessAuditButton();

        reportCentralFiltersPage().setReportIdentifier(auditIdentifier);
        reportCentralFiltersPage().setRequestersName(userName);

        reportCentralGridPage().waitForExistenceOfWorkflow("Audit Report", auditIdentifier, userName);
        reportCentralGridPage().verifyFirstWorkflowFinishes();

        String auditUUID = reportCentralGridPage().getReportUUID();

        File auditPDF = PDFUtils.savePDFWithName(auditUUID, auditIdentifier);
        reportCentralGridPage().deleteReport();
        return auditPDF;
    }

    @AfterEach
    public void clearMockup()
    {
        closePDF();
        AuditsDataMocking.deleteDatapod(connection);
        /*FileUtils.deleteIfExists(auditPDF);
        FileUtils.deleteIfExists(fileToDelete);*/
    }
}